package wordcount.tools;

import util.ArgsParse;

public class ArgsTools {

	/**
	 * the parseArgs approach: to parse args in config file
	 * @param dir 			Ŀ���ļ����ڵ��ļ���
	 * @param config_file 	Ŀ���ļ����ļ���
	 */
	public static void parseArgs(String dir, String config_file){
		
		ArgsParse argsParse = new ArgsParse(dir, config_file);
		
		/*
		 * ���и�Topology��WorkerNum��AckerNum
		 */
		if(argsParse.isContains("numWorkers"))
			ArgsWordcount.numWorkers = Integer.parseInt(argsParse.getArgs("numWorkers"));
		if(argsParse.isContains("numAckers"))
			ArgsWordcount.numAckers = Integer.parseInt(argsParse.getArgs("numAckers"));
		
		/*
		 * Spout���жȣ��Լ���һ�����ڶ�����������Bolt�Ĳ��ж�
		 */
		if(argsParse.isContains("paraSpout"))
			ArgsWordcount.paraSpout = Integer.parseInt(argsParse.getArgs("paraSpout"));
		if(argsParse.isContains("paraBolt1"))
			ArgsWordcount.paraBolt1 = Integer.parseInt(argsParse.getArgs("paraBolt1"));
		if(argsParse.isContains("paraBolt2"))
			ArgsWordcount.paraBolt2 = Integer.parseInt(argsParse.getArgs("paraBolt2"));
		
		/*
		 * ���ı���ȡ���ݲ����͵�����
		 */
		if(argsParse.isContains("streamInterval"))
			ArgsWordcount.streamInterval = Integer.parseInt(argsParse.getArgs("streamInterval"));
	}
}
