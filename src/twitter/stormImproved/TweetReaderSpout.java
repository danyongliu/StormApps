package twitter.stormImproved;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.LinkedList;
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
	private boolean 				endOfFile = false;				// �ж��Ƿ����ļ�ĩβ
	private String 					filePathIn;
	private File					fileDir;						// Ŀ���ļ����ڵ��ļ���
	private File[]					fileArray;						// �ļ����������ļ����б�
	private LinkedList<File>		fileList;						// �ļ����������ļ����б�
	private int						fileIndex;						// �ļ����и����ļ�������
	
	private FileReader 	fReader;
	
	long starttime;
	long tweetNum;

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
				_collector.emit("signal", new Values("endOfFile"));
				Thread.sleep(20*1000);
				
			} catch (InterruptedException e) {
				// DO nothing
			}
		}
		
		/*
		 * ���ж�ȡ�ļ�����ÿ��ȡһ�м�����һ��Bolt��������
		 */
		if(fileIndex < fileList.size()){
			
			try {
				System.err.println("File name: " + fileList.get(fileIndex).getName());
				
				fReader = new FileReader(new File(fileList.get(fileIndex).getAbsolutePath()));
				BufferedReader bReader = new BufferedReader(fReader); 				// Open the BufferedReader
				while(bReader.ready()){
					
					String str_line = bReader.readLine().trim();				// Read a line from the file
					tweetNum ++;
					
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
				
				_collector.emit("signal", new Values("endOfFile"));
				System.out.println("Finished to process the " + fileIndex + " file." + 
						"Cost time: " + (System.currentTimeMillis() - starttime) + " ms.\n" + 
						"Total tweet number: " + tweetNum);
				
				Utils.sleep(10);
				fileIndex ++;
				tweetNum = 0;
				starttime = System.currentTimeMillis();
				
			} catch (FileNotFoundException e) {

				throw new RuntimeException("Spout task_ " + _context.getThisTaskIndex() 
										 + " : Error reading a source file: " + filePathIn);
			}catch(Exception e){
				throw new RuntimeException("Spout task_ " + _context.getThisTaskIndex() 
						 + " : Error reading a line.");
			}finally{
//				endOfFile = true;
			}
		}else{
			
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
		filePathIn = "//10.107.18.201/data/";
		fileDir = new File(filePathIn);
		fileArray = fileDir.listFiles();
		fileList = new LinkedList<File>();
		
		/*
		 * ��֤���ļ�����˳������ȡ�ļ�
		 */
		for(File f: fileArray){
			fileList.add(f);
		}
		Collections.sort(fileList);
		
		fileIndex = 0;
		
		starttime = System.currentTimeMillis();
		tweetNum = 0L;
	}

	/**
	 * Declare the output filed, i.e., the output stream
	 */
	public void declareOutputFields(OutputFieldsDeclarer declarer) {

		declarer.declareStream("tweet-stream", new Fields("tweet"));
		declarer.declareStream("signal", new Fields("endOfFile"));

	}

}
