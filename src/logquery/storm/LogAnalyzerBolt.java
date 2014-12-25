package logquery.storm;

import java.util.HashMap;
import java.util.Map;

import util.Constants;
import util.MapTools;

import logquery.tools.*;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class LogAnalyzerBolt extends BaseRichBolt {
	
	private static final long serialVersionUID = 8963886289234350642L;
	
	private TopologyContext _context;
	private OutputCollector _collector;
	
	private long timeClock; // ��ʼֵΪ��task�״�ִ�е�ʱ�䣬���ÿ����MergerBolt�������ݺ������Ϊ��ǰʱ��
	
	/*
	 * PV ����ͳ�Ƶ����
	 * 		HashMap<String, Long>��StringΪ��СʱΪ���ȵ�ʱ�����LongΪ����� 
	 * UV ����ͳ�Ʋ�ͬ��IP��ַ������24Сʱ��ͬһ��IP��ַֻ��һ��
	 * 		HashMap<String, HashMap<String, Long>>
	 *		���Map��KeyΪ����Ϊ���ȵ�ʱ������ڲ�Map��keyΪIP��ַ��valueΪIP��ַ���ֵĴ���
	 * status ����ͳ��״̬��ֲ�
	 * 		HashMap<String, HashMap<String, Long>>
	 * 		���Map��KeyΪ��СʱΪ���ȵ�ʱ������ڲ�Map��keyΪ״̬�룬valueΪ��״̬����ֵĴ���
	 * spider ����ͳ�������֩������ķֲ�
	 * 		HashMap<String, HashMap<String, Long>>
	 * 		���Map��KeyΪ��СʱΪ���ȵ�ʱ������ڲ�Map��keyΪspider��valueΪ��spider���ֵĴ���
	 * 
	 * ʱ���Timestamp�ĸ�ʽ���磺2013-11-35������Ϊday����2013-11-25:23������Ϊhour��
	 * IP��ַ��ʽΪ��157.55.34.183
	 * ״̬��Ϊ��1xx��������Ϣ��2xx������ɹ���3xx�������ض���4xx���ͻ��˴���5xx������������
	 * spider������googlebot/baiduspider/baidugame/msnbot/bingbot/ahrefsbot/360spider/nutch/sosospider/"sogou web spider
	 */
	private HashMap<String, Long> PV_map; 
	private HashMap<String, HashMap<String, Long>> UV_map;
	private HashMap<String, HashMap<String, Long>> status_map;
	private HashMap<String, HashMap<String, Long>> spider_map;
	
	private SpiderSite spiderSite;								// SpiderSite�г�ʼ���洢��10�г�������������֩��

	/**
	 * The bolt will receive log records from the spout and process them to Normalize these lines
	 * The normalize will put the the logRecords to lower case
	 * and split the logRecord to get all 10 attributes of a single log record
	 */
	public void execute(Tuple input) {

		/**
		 * �������Ľ�����Ϣ���ǿ��Ҳ�Ϊ{}�����д�����Ҫͳ��4��ͳ�������ֱ�PV��UV��status��spider agent��
		 * �ֱ�洢��PV_map��UV_map��status_map��spider_map��
		 */
		String record_str = input.getStringByField("logRecord");// ��������spout�����������ݣ�FiledNameΪlogRecord
		if(record_str == null){
			System.out.println("logAnalyzer bolt - " + _context.getThisTaskIndex() 
					+ " : Got a null message from the spout.");
			
		}else{
			LogRecords record = new LogRecords(record_str);		// �������յ����ַ����������ݸ��ַ�������һ��LogRecords�Ķ���record
			String stamp = record.timePrecision(3);				// ����LogRecords�����е�ʱ�������ȡ����Ĭ��ΪСʱ��stamp
			
			/*
			 * ��СʱΪ���ȣ�ͳ��һ��֮�� ÿ��Сʱ��PV��Page View; 
			 * һ��24Сʱ֮�ڣ�ÿ��һ����¼���ͳ��һ��
			 */
			if(PV_map.containsKey(stamp)){
				long pv_count = PV_map.get(stamp);
				pv_count ++;
				PV_map.put(stamp, pv_count);
			}else{
				PV_map.put(stamp, 1L);
			}
			
			/*
			 * ��СʱΪ���ȣ�ͳ��һ��֮�� ÿ��Сʱ��״̬��ֲ���status; 
			 * һ��24Сʱ֮�ڣ�ÿ��һ��������ͳ��һ��
			 */
			String httpstate = record.getHttpstate();
			HashMap<String, Long> httpstate_map = new HashMap<String, Long>();
			
			if(status_map.containsKey(stamp)){
				httpstate_map = status_map.get(stamp);
				if(httpstate_map.containsKey(httpstate)){
					long status_count = httpstate_map.get(httpstate);
					status_count ++;
					httpstate_map.put(httpstate, status_count);
					
				}else{
					httpstate_map.put(httpstate, 1L);
				}
				status_map.put(stamp, httpstate_map);
				
			}else{
				httpstate_map.put(httpstate, 1L);
				status_map.put(stamp, httpstate_map);
			}
			
			/*
			 * ��СʱΪ���ȣ�ͳ��һ��֮�� ÿ��Сʱ����������֩��ķֲ�
			 * 
			 * �����ж����agent�ǲ�����������֩�룬
			 * ���spiderName == null����ʾ����֩�룬DO NOTHING��
			 * ���spiderName != null����ʾ��agent��֩�룬�޸�spider_map����
			 */
			String agent = record.getAgent();
			String spiderName = spiderSite.containsSpider(agent);
			
			if(spiderName == null){
				// Do nothing
			}else{
				agent = spiderName;
				HashMap<String, Long> agent_map = new HashMap<String, Long>();
				
				if(spider_map.containsKey(stamp)){
					agent_map = spider_map.get(stamp);
					if(agent_map.containsKey(agent)){
						long spider_count = agent_map.get(agent);
						spider_count ++;
						agent_map.put(agent, spider_count);
						
					}else{
						agent_map.put(agent, 1L);
					}
					spider_map.put(stamp, agent_map);
					
				}else{
					agent_map.put(agent, 1L);
					spider_map.put(stamp, agent_map);
				}
			}
			
			/*
			 * ����Ϊ���ȣ�ͳ��һ��֮�ڵ�UV��Unique Vistor; 
			 * һ��24Сʱ֮�ڣ�������ͬ��IPֻ��ͳ��һ��
			 */
			stamp = record.timePrecision(2);	// ����LogRecords�����е�ʱ�������ȡ����Ϊday��stamp
			String ip = record.getIP();
			HashMap<String, Long> ip_map = new HashMap<String, Long>();
			
			if(UV_map.containsKey(stamp)){
				ip_map = UV_map.get(stamp);
				if(ip_map.containsKey(ip)){
					long uv_count = ip_map.get(ip);
					uv_count ++;
					ip_map.put(ip, uv_count);
				}else{
					ip_map.put(ip, 1L);
				}
				UV_map.put(stamp, ip_map);
				
			}
			else{
				ip_map.put(ip, 1L);
				UV_map.put(stamp, ip_map);
			}
		}
		
		/**
		 * ��һ����ʱ�����ȣ��Ѿֲ�ͳ�ƽ�����͸���һ��MergerBolt
		 * Ĭ��ʱ������Ϊ5���ӣ��ҷ��͹��󣬻Ὣ���ص�4��ͳ������գ�PV_map��UV_map��status_map��spider_map
		 * 
		 * ��һ��starttime��Ϊ����task��ʼִ�е�ʱ�䣬Ȼ��ÿ�ν��յ�һ����¼��ִ��һ��execute����
		 * ���жϵ�ǰʱ����starttime֮���ʱ����Ƿ�ﵽ�������ڣ�����ﵽ���ŷ��ͣ����ͺ�����starttime
		 * 
		 */
		if((System.currentTimeMillis() - timeClock) >= ArgsLogquery.transPeriod){
			
			if(!PV_map.isEmpty()){
				String pvStr = MapTools.mapToString(PV_map);
				_collector.emit("pv-stream", new Values(pvStr));			// ����������
				PV_map.clear();
			}else{
				System.err.println("logAnalyzer bolt task - " + _context.getThisTaskIndex() 
						+ " : The PV_map is empty now.");
			}
			
			if(!UV_map.isEmpty()){
				String uvStr = MapTools.mapToString2(UV_map);
				_collector.emit("uv-stream", new Values(uvStr));			// ����������
				UV_map.clear();
			}else{
				System.err.println("logAnalyzer bolt task - " + _context.getThisTaskIndex() 
						+ " : The UV_map is empty now.");
			}
			
			if(!status_map.isEmpty()){
				String statusStr = MapTools.mapToString2(status_map);
				_collector.emit("status-stream", new Values(statusStr));	// ����������
				status_map.clear();
			}else{
				System.err.println("logAnalyzer bolt task - " + _context.getThisTaskIndex() 
						+ " : The status_map is empty now.");
			}
			
			if(!spider_map.isEmpty()){
				String spiderStr = MapTools.mapToString2(spider_map);
				_collector.emit("spider-stream", new Values(spiderStr));	// ����������
				spider_map.clear();
			}else{
				System.err.println("logAnalyzer bolt task - " + _context.getThisTaskIndex() 
						+ " : The spider_map is empty now.");
			}
			
			timeClock = System.currentTimeMillis();			// ���ö�ʱ���͵���ʼʱ��
		}else{
			// Do nothing 
		}
	}


	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		
		ArgsTools.parseArgs(Constants.CONFIG_DIRECTORY, Constants.TOPOLOGY_ARGS);		// parse args and reset the Constants class
		/*
		 * ������ʼ��
		 */
		_context = context;
		_collector = collector;
		
		this.PV_map = new HashMap<String, Long>();
		this.UV_map = new HashMap<String, HashMap<String, Long>>();
		this.status_map = new HashMap<String, HashMap<String, Long>>();
		this.spider_map = new HashMap<String, HashMap<String, Long>>();
		
		this.spiderSite = new SpiderSite();
		
		this.timeClock = System.currentTimeMillis();
	}

	/**
	 * Declare the output filed, i.e., the output stream
	 */
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
		declarer.declareStream("pv-stream", new Fields("pv"));
		declarer.declareStream("uv-stream", new Fields("uv"));
		declarer.declareStream("status-stream", new Fields("status"));
		declarer.declareStream("spider-stream", new Fields("spider"));
	}

}
