package logquery.tools;

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
			ArgsLogquery.numWorkers = Integer.parseInt(argsParse.getArgs("numWorkers"));
		if(argsParse.isContains("numAckers"))
			ArgsLogquery.numAckers = Integer.parseInt(argsParse.getArgs("numAckers"));
		
		/*
		 * Spout���жȣ��Լ���һ�����ڶ�����������Bolt�Ĳ��ж�
		 */
		if(argsParse.isContains("paraSpout"))
			ArgsLogquery.paraSpout = Integer.parseInt(argsParse.getArgs("paraSpout"));
		if(argsParse.isContains("paraBolt1"))
			ArgsLogquery.paraBolt1 = Integer.parseInt(argsParse.getArgs("paraBolt1"));
		if(argsParse.isContains("paraBolt2"))
			ArgsLogquery.paraBolt2 = Integer.parseInt(argsParse.getArgs("paraBolt2"));
		if(argsParse.isContains("paraBolt3"))
			ArgsLogquery.paraBolt3 = Integer.parseInt(argsParse.getArgs("paraBolt3"));
		
		/*
		 * LogAnalyzerBolt��MergerBolt���;ֲ�ͳ�����ݵ�����
		 * MergerBolt�����̨��ӡͳ�ƽ��������
		 * MergerBolt��ͳ�ƽ���鵵������
		 */
		if(argsParse.isContains("transPeriod"))
			ArgsLogquery.transPeriod = Integer.parseInt(argsParse.getArgs("transPeriod"));
		if(argsParse.isContains("printPeriod"))
			ArgsLogquery.printPeriod = Integer.parseInt(argsParse.getArgs("printPeriod"));
		if(argsParse.isContains("archivePeriod"))
			ArgsLogquery.archivePeriod = Integer.parseInt(argsParse.getArgs("archivePeriod"));
		
		/*
		 * ���ı���ȡ���ݲ����͵�����
		 */
		if(argsParse.isContains("streamInterval"))
			ArgsLogquery.streamInterval = Integer.parseInt(argsParse.getArgs("streamInterval"));
	}
}
