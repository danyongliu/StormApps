package logquery.improvedstorm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import util.Constants;
import util.FileUtil;
import util.MapTools;
import util.Printer;

import logquery.tools.*;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

public class PrinterBolt extends BaseRichBolt {

	private static final long serialVersionUID = 6552135498175768544L;
	
	private TopologyContext _context;
	
	private long clock4Print; 		// ��ʼֵΪ��task�״�ִ�е�ʱ�䣬ÿ�������̨��ӡ��Ϣ�������Ϊ��ǰʱ��
	private long clock4Archive; 	// ��ʼֵΪ��task�״�ִ�е�ʱ�䣬ÿ�ι鵵���ݺ������Ϊ��ǰʱ��
	
	private HashMap<String, Long> PV_map; 
	private HashMap<String, HashMap<String, Long>> UV_map;
	private HashMap<String, HashMap<String, Long>> status_map;
	private HashMap<String, HashMap<String, Long>> spider_map;

	public void execute(Tuple input) {
		/*
		 * **********************************************************************************************************************
		 * ���ݽ��յ��Ĳ�ͬ�������ж���Ӧ�Ĵ����߼���
		 * �������������Ͱ����� pv-stream��uv-stream��status-stream��spider-stream
		 */
		String source = input.getSourceStreamId();
		if(source == null){
			System.err.println("Merger bolt task - " + _context.getThisTaskIndex()
					+ " : Received a null stream source.");
		}else{//if(source != null)
			/*
			 * ���PV-stream���ж��߼�
			 * ���ݽ��յ���pv-stream��pvStr��������һ��pvMap_tmp��
			 * Ȼ��pvMap_tmp�ӵ�PV_map��ȥ
			 */
			if(source.contains("pv-stream")){
				String pvStr = input.getStringByField("pv");
				if(pvStr == null){
					System.err.println("Merger bolt task - " + _context.getThisTaskIndex() 
							+ " : You got a null message of pv-stream from the logAnalyzer bolt.");
				}else{
					
					HashMap<String, Long> pvMap_tmp = new HashMap<String, Long>();
					pvMap_tmp = MapTools.strToMap(pvStr);
					
					PV_map = MapTools.addMap(PV_map, pvMap_tmp);
				}
			}
			
			/*
			 * ���UV-stream���ж��߼�
			 * ���ݽ��յ���uv-stream��uvStr��������һ��uvMap_tmp��
			 * Ȼ��uvMap_tmp�ӵ�UV_map��ȥ
			 */
			if(source.contains("uv-stream")){
				String uvStr = input.getStringByField("uv");
				if(uvStr == null){
					System.err.println("Merger bolt task - " + _context.getThisTaskIndex() 
							+ " : You got a null message of uv-stream from the logAnalyzer bolt.");
				}else{
					
					HashMap<String, HashMap<String, Long>> uvMap_tmp = new HashMap<String, HashMap<String, Long>>();
					uvMap_tmp = MapTools.strToMap2(uvStr);
					
					UV_map = MapTools.addMap2(UV_map, uvMap_tmp);
				}
			}
			
			/*
			 * ���status-stream���ж��߼�
			 * ���ݽ��յ���status-stream��statusStr��������һ��statusMap_tmp��
			 * Ȼ��statusMap_tmp�ӵ�status_map��ȥ
			 */
			if(source.contains("status-stream")){
				String statusStr = input.getStringByField("status");
				if(statusStr == null){
					System.err.println("Merger bolt task - " + _context.getThisTaskIndex() 
							+ " : You got a null message of status-stream from the logAnalyzer bolt.");
				}else{
					
					HashMap<String, HashMap<String, Long>> statusMap_tmp = new HashMap<String, HashMap<String, Long>>();
					statusMap_tmp = MapTools.strToMap2(statusStr);
					
					status_map = MapTools.addMap2(status_map, statusMap_tmp);
				}
			}
			
			/*
			 * ���spider-stream���ж��߼�
			 * ���ݽ��յ���spider-stream��spiderStr��������һ��spiderMap_tmp��
			 * Ȼ��spiderMap_tmp�ӵ�spider_map��ȥ
			 */
			if(source.contains("spider-stream")){
				String spiderStr = input.getStringByField("spider");
				if(spiderStr == null){
					System.err.println("Merger bolt task - " + _context.getThisTaskIndex() 
							+ " : You got a null message of spider-stream from the logAnalyzer bolt.");
				}else{
					
					HashMap<String, HashMap<String, Long>> spiderMap_tmp = new HashMap<String, HashMap<String, Long>>();
					spiderMap_tmp = MapTools.strToMap2(spiderStr);
					
					spider_map = MapTools.addMap2(spider_map, spiderMap_tmp);
				}
			}
		}
		
		/**
		 * ÿ��һ��ʱ���ӡһ��ͳ�ƽ����Ĭ��Ϊ5���ӣ�
		 * ÿ��һ��ʱ�佫ȫ��ͳ�ƽ���鵵һ�Σ�Ĭ��Ϊ15����
		 * �鵵�󣬻Ὣ���ص�4��ͳ������գ�PV_map��UV_map��status_map��spider_map
		 */
		if((System.currentTimeMillis() - this.clock4Print) >= ArgsLogquery.printPeriod){
			System.out.println("===================== PV@" + new Date() + " =====================");
			Printer.printMap(PV_map);
			System.out.println();
			
			System.out.println("===================== UV Distribution@" + new Date() + "=====================");
			Printer.printMap2(UV_map);
			System.out.println();
			
			System.out.println("===================== Status Distribution@" + new Date() + "=====================");
			Printer.printMap2(status_map);
			System.out.println();
			
			System.out.println("===================== SpiderSite Distribution@" + new Date() + "=====================");
			Printer.printMap2(spider_map);
			System.out.println();
			
			this.clock4Print = System.currentTimeMillis(); // ���ö�ʱ��
		}
		
		if((System.currentTimeMillis() - this.clock4Archive) >= ArgsLogquery.archivePeriod){
			
			FileUtil.appendMapToFile(PV_map, Constants.RESOURCE_DIRECTORY, ArgsLogquery.PV_LOG);
			FileUtil.appendMapToFile2(UV_map, Constants.RESOURCE_DIRECTORY, ArgsLogquery.UV_LOG);
			FileUtil.appendMapToFile2(status_map, Constants.RESOURCE_DIRECTORY, ArgsLogquery.STATUS_LOG);
			FileUtil.appendMapToFile2(spider_map, Constants.RESOURCE_DIRECTORY, ArgsLogquery.SPIDER_LOG);
			
			PV_map.clear();
			UV_map.clear();
			status_map.clear();
			spider_map.clear();
			
			this.clock4Archive = System.currentTimeMillis();
		}
		
		
		
	}

	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {

		ArgsTools.parseArgs(Constants.CONFIG_DIRECTORY, Constants.TOPOLOGY_ARGS);		// parse args and reset the Constants class
		
		_context = context;
		
		this.PV_map = new HashMap<String, Long>();
		this.UV_map = new HashMap<String, HashMap<String, Long>>();
		this.status_map = new HashMap<String, HashMap<String, Long>>();
		this.spider_map = new HashMap<String, HashMap<String, Long>>();
		
		clock4Print = System.currentTimeMillis();	// ��ʼ����ʱ��
		clock4Archive = System.currentTimeMillis();	// ��ʼ����ʱ��
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub

	}

}
