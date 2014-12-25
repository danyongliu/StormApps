package netease.tools;

import util.ArgsParse;

public class ArgsTools {

	/**
	 * the parseArgs approach: to parse args in config file
	 * @param dir 			Ŀ���ļ����ڵ��ļ���
	 * @param config_file 	Ŀ���ļ����ļ���
	 */
	public static void parseArgs(String config_dir, String config_file){
		
		ArgsParse argsParse = new ArgsParse(config_dir, config_file);
		
		/*
		 * ���и�Topology��WorkerNum��AckerNum
		 */
		if(argsParse.isContains("numWorkers"))
			ArgsNetease.numWorkers = Integer.parseInt(argsParse.getArgs("numWorkers"));
		if(argsParse.isContains("numAckers"))
			ArgsNetease.numAckers = Integer.parseInt(argsParse.getArgs("numAckers"));
		
		/*
		 * Spout���жȣ��Լ���һ�����ڶ�����������Bolt�Ĳ��ж�
		 */
		if(argsParse.isContains("paraSpout"))
			ArgsNetease.paraSpout = Integer.parseInt(argsParse.getArgs("paraSpout"));
		if(argsParse.isContains("paraBolt1"))
			ArgsNetease.paraBolt1 = Integer.parseInt(argsParse.getArgs("paraBolt1"));
		if(argsParse.isContains("paraBolt2"))
			ArgsNetease.paraBolt2 = Integer.parseInt(argsParse.getArgs("paraBolt2"));
		
		/*
		 * LogAnalyzerBolt��MergerBolt���;ֲ�ͳ�����ݵ�����
		 * MergerBolt�����̨��ӡͳ�ƽ��������
		 * MergerBolt��ͳ�ƽ���鵵������
		 */
		if(argsParse.isContains("archivePeriod"))
			ArgsNetease.archivePeriod = Integer.parseInt(argsParse.getArgs("archivePeriod"));
		
		/*
		 * �Ƿ��Ա仯���ʷ���������������ǣ��������ٶȱ仯����Ϊ����
		 */
		if(argsParse.isContains("isVaring"))
			ArgsNetease.isVaring = Boolean.parseBoolean(argsParse.getArgs("isVaring"));
		if(argsParse.isContains("varingPeriod"))
			ArgsNetease.varingPeriod = Integer.parseInt(argsParse.getArgs("varingPeriod"));
		
		/*
		 * ���ı���ȡ/ֱ���������ݲ����͵�����
		 */
		if(argsParse.isContains("streamInterval"))
			ArgsNetease.streamInterval = Integer.parseInt(argsParse.getArgs("streamInterval"));
	}
}
