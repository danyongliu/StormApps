package netease.tools;

public class ArgsNetease {

	public static String ACCESS_LOG = "access_log"; 			// ԭʼ����־�ļ�
	public static String PV_LOG = "statPV.csv"; 				// PV��ͳ�ƽ��
	public static String UV_LOG = "statUV.csv"; 				// UV��ͳ�ƽ��
	public static String STATUS_LOG = "statStatus.csv"; 		// Status Distribution��ͳ�ƽ��
	public static String SPIDER_LOG = "statSpider.csv"; 		// Spider Distribution��ͳ�ƽ��
	
	public static int maxTaskPara = 100;						// ��������̲߳��жȣ�Ĭ��Ϊ10���ò���ֻ�ڱ���ģʽ�����ã�
	public static int numWorkers = 10;							// ����Worker����Ŀ��Ĭ��Ϊ5
	public static int numAckers = 0;							// ����Acker����Ŀ��Ĭ��Ϊ0
	
	public static int paraSpout = 1;							// ����Spout���жȣ�Ĭ��Ϊ1
	public static int paraBolt1 = 20;							// ���õ�һ��Bolt�Ĳ��жȣ�Ĭ��Ϊ3
	public static int paraBolt2 = 1;							// ���õڶ���Bolt�Ĳ��жȣ�Ĭ��Ϊ1
	public static int numTask = 1;								// ����ÿ��executor����ٸ�task��Ĭ��Ϊ1
	
	public static int archivePeriod = 10*1000;					// ����LogAnalyzerBolt��MergerBolt���;ֲ�ͳ�����ݵ����ڣ��Լ�MergerBolt��ͳ�ƽ���鵵�����ڣ�Ĭ��Ϊ10s
	
	public static boolean isVaring = false;						// �Ƿ��Ա仯���ʷ�����������Ĭ��Ϊfalse�����Ժ㶨���ʷ���������
	public static int varingPeriod = 120*1000;					// �������������ʷ����仯�����ڣ�Ĭ��Ϊ20��
	
	public static int streamInterval = 10;						// ���ı���ȡ���ݲ����͵����ڣ�Ĭ��Ϊ1 elements per second
	
}
