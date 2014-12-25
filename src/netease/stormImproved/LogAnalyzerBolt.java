package netease.stormImproved;

import java.util.HashMap;
import java.util.Map;

import netease.tools.ArgsNetease;
import netease.tools.ArgsTools;
import netease.tools.SpiderSite;

import org.json.simple.*;

import util.Constants;
import util.MapTools;

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
	
	private SpiderSite 	spiderSite;				// SpiderSite�г�ʼ���洢��10�г�������������֩��
	
	private long 		countPV;				// ͳ�Ʒ���������ҳ��ˢ�µĴ��������ж��������ʼ�¼
	private HashMap<String, Long> spider_map;	// ͳ�Ʋ�ͬSpider�ķֲ� 
	private HashMap<String, Long> status_map;	// ͳ�Ʋ�ͬ״̬��ķֲ� 

	private long 		timeClock; 				// ��ʼֵΪ��task�״�ִ�е�ʱ�䣬���ÿ����MergerBolt�������ݺ������Ϊ��ǰʱ��

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
		String record_str = input.getStringByField("logRecord");// ��������spout�����������ݣ�FieldNameΪlogRecord
		if(record_str == null){
			System.out.println("logAnalyzer bolt - " + _context.getThisTaskIndex() 
					+ " : Got a null message from the spout.");
			
		}else{
			
			/*
			 * �����յ����ַ�������ΪJSON���󲢷�������
			 */
			Object obj = JSONValue.parse(record_str);
			JSONObject record = (JSONObject)obj;
			
			// ͳ��ÿ��ʱ����ڵ�PV��
			countPV ++;
			
			// ͳ��ÿ��ʱ����ڵ�spider������ͳ�Ʋ�ͬSpider���ֵĴ���
			String agent = (String)record.get("agent");
			String spiderName = spiderSite.containsSpider(agent);
			if(spiderName != null){

				if(spider_map.containsKey(spiderName)){
					long value = spider_map.get(spiderName);
					value ++;
					spider_map.put(spiderName, value);
				}else{
					spider_map.put(spiderName, 1L);
				}
			}
			
			// ���ȹ淶��ÿ��״̬��Ϊ1xx��2xx��3xx��4xx��5xx��Ȼ��ͳ��ÿ��ʱ����ڲ�ͬ״̬��ķֲ�
			String httpstate = (String)record.get("httpstate");
			if(httpstate.startsWith("1")){
				httpstate = "1xx";
			}else if(httpstate.startsWith("2")){
				httpstate = "2xx";
			}else if(httpstate.startsWith("3")){
				httpstate = "3xx";
			}else if(httpstate.startsWith("4")){
				httpstate = "4xx";
			}else if(httpstate.startsWith("5")){
				httpstate = "5xx";
			}
			
			if(status_map.containsKey(httpstate)){
				long value = status_map.get(httpstate);
				value ++;
				status_map.put(httpstate, value);
			}else{
				status_map.put(httpstate, 1L);
			}
		}
		
		/*
		 * Ĭ��ÿConstants.archivePeriod���,LogAnalyzerBolt����MergerBolt����һ���м�ͳ�ƽ��
		 * ÿ�η��������ݺ�����timeClockΪ��ǰʱ�䣬
		 * �����ͳ����
		 */
		if((System.currentTimeMillis() - timeClock) >= ArgsNetease.archivePeriod){
			
			String spiderStr = "";
			String statusStr = "";

			if(!spider_map.isEmpty())
				spiderStr = MapTools.mapToString(spider_map);
			
			if(!status_map.isEmpty())
				statusStr = MapTools.mapToString(status_map);
			
			_collector.emit("stat-stream", new Values(countPV, spiderStr, statusStr));
			
			countPV = 0L;
			spider_map.clear();
			status_map.clear();
			timeClock = System.currentTimeMillis();
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
		
		this.spiderSite = new SpiderSite();

		this.countPV = 0L;
		this.spider_map = new HashMap<String, Long>();
		this.status_map = new HashMap<String, Long>();
		
		this.timeClock = System.currentTimeMillis();
	}

	/**
	 * Declare the output filed, i.e., the output stream
	 */
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
		declarer.declareStream("stat-stream", new Fields("pv", "spider", "status"));
	}

}
