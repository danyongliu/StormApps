package twitter.storm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

import twitter.tools.ArgsTools;
import twitter.tools.Args4Twitter;
import util.Constants;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

public class TweetReaderSpout extends BaseRichSpout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4566803235755654578L;
	
	private SpoutOutputCollector _collector;
	private TopologyContext _context;
	
	/*
	 * ��Դ�ļ���ȡ��ص�һЩ����
	 */
	private boolean 	endOfFile = false;							// �ж��Ƿ����ļ�ĩβ
	private String 		filePathIn;
	private FileReader 	fReader;

	/**
	 * The only thing that the method will do IS emitting each file line
	 */
	public void nextTuple() {

		/*
		 * The nextTuple() is called forever, so if we have been read the whole file,
		 * we will wait several seconds and then return
		 */
		if(endOfFile){
			try {
				Thread.sleep(10*1000);
			} catch (InterruptedException e) {
				// DO nothing
			}
			return;
		}
		
		/*
		 * ���ж�ȡ�ļ�����ÿ��ȡһ�м�����һ��Bolt��������
		 */
		BufferedReader bReader = new BufferedReader(fReader); 				// Open the BufferedReader
		try{
			while(bReader.ready()){
				String str_line = bReader.readLine().trim();				// Read a line from the file
				
				if((str_line == null)){// ȥ��null line
					
					System.err.println("Spout task_ " + _context.getThisTaskIndex() 
							+ ": Reading an null line in the source file.");
					
				}else if(str_line.equals("{}")){// ȥ��{} line
					
					System.err.println("Spout task_ " + _context.getThisTaskIndex() + ": A {} line.");
					
				}else if(str_line.startsWith("{\"created_at\":")){// �ҳ���"created_at":��ͷ��line(ȥ����{"delete":��ͷ��line)
					
					_collector.emit("tweet-stream", new Values(str_line));
				}
				
				Utils.sleep(Args4Twitter.streamInterval);						// One element per second in default
			}
			bReader.close();
			fReader.close();
		}catch(Exception e){
			throw new RuntimeException("Spout task_ " + _context.getThisTaskIndex() 
									 + " : Error reading a line.");
		}finally{
			endOfFile = true;
		}
		
	}

	/**
	 * ������ʼ��: get the collector object and create the input log file
	 */
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {

		ArgsTools.parseArgs(Constants.CONFIG_DIRECTORY, Constants.TOPOLOGY_ARGS);		// parse args and reset the Constants class
		
		_collector = collector;
		_context = context;
		
		/*
		 * �ļ���ȡ�ĳ�ʼ��
		 */
		filePathIn = Constants.RESOURCE_DIRECTORY + Args4Twitter.TWITTER_LOG;
		try {
			fReader = new FileReader(new File(filePathIn));
		} catch (FileNotFoundException e) {

			throw new RuntimeException("Spout task_ " + _context.getThisTaskIndex() 
									 + " : Error reading a source file: " + filePathIn);
		}
	}

	/**
	 * Declare the output filed, i.e., the output stream
	 */
	public void declareOutputFields(OutputFieldsDeclarer declarer) {

		declarer.declareStream("tweet-stream", new Fields("tweet"));

	}

}
