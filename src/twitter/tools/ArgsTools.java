package twitter.tools;

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
			Args4Twitter.numWorkers = Integer.parseInt(argsParse.getArgs("numWorkers"));
		if(argsParse.isContains("numAckers"))
			Args4Twitter.numAckers = Integer.parseInt(argsParse.getArgs("numAckers"));
		
		/*
		 * Spout���жȣ��Լ���һ�����ڶ�����������Bolt�Ĳ��ж�
		 */
		if(argsParse.isContains("paraSpout"))
			Args4Twitter.paraSpout = Integer.parseInt(argsParse.getArgs("paraSpout"));
		if(argsParse.isContains("paraBolt1"))
			Args4Twitter.paraBolt1 = Integer.parseInt(argsParse.getArgs("paraBolt1"));
		if(argsParse.isContains("paraBolt2"))
			Args4Twitter.paraBolt2 = Integer.parseInt(argsParse.getArgs("paraBolt2"));
		if(argsParse.isContains("paraBolt3"))
			Args4Twitter.paraBolt3 = Integer.parseInt(argsParse.getArgs("paraBolt3"));
		
		/*
		 * LogAnalyzerBolt��MergerBolt���;ֲ�ͳ�����ݵ�����
		 * MergerBolt�����̨��ӡͳ�ƽ��������
		 * MergerBolt��ͳ�ƽ���鵵������
		 */
		if(argsParse.isContains("clearPeriod"))
			Args4Twitter.clearPeriod = Integer.parseInt(argsParse.getArgs("clearPeriod"));
		if(argsParse.isContains("archivePeriod"))
			Args4Twitter.archivePeriod = Integer.parseInt(argsParse.getArgs("archivePeriod"));
		
		/*
		 * ���ı���ȡ���ݲ����͵�����
		 */
		if(argsParse.isContains("streamInterval"))
			Args4Twitter.streamInterval = Integer.parseInt(argsParse.getArgs("streamInterval"));
	}
}
