package logquery.tools;

public class ArgsLogquery {

	public static String ACCESS_LOG = "access_log"; 			// ԭʼ����־�ļ�
	public static String PV_LOG = "statPV"; 					// PV��ͳ�ƽ��
	public static String UV_LOG = "statUV"; 					// UV��ͳ�ƽ��
	public static String STATUS_LOG = "statStatus"; 			// Status Distribution��ͳ�ƽ��
	public static String SPIDER_LOG = "statSpider"; 			// Spider Distribution��ͳ�ƽ��
	
	public static int maxTaskPara = 100;						// ��������̲߳��жȣ�Ĭ��Ϊ10���ò���ֻ�ڱ���ģʽ�����ã�
	public static int numWorkers = 5;							// ����Worker����Ŀ��Ĭ��Ϊ5
	public static int numAckers = 0;							// ����Acker����Ŀ��Ĭ��Ϊ0
	
	public static int paraSpout = 1;							// ����Spout���жȣ�Ĭ��Ϊ1
	public static int paraBolt1 = 10;							// ���õ�һ��Bolt�Ĳ��жȣ�Ĭ��Ϊ3
	public static int paraBolt2 = 1;							// ���õڶ���Bolt�Ĳ��жȣ�Ĭ��Ϊ1
	public static int paraBolt3 = 1;							// ���õڶ���Bolt�Ĳ��жȣ�Ĭ��Ϊ1
	
	public static int transPeriod = 10*1000;					// ����LogAnalyzerBolt��MergerBolt���;ֲ�ͳ�����ݵ����ڣ�Ĭ��Ϊ5����
	public static int printPeriod = 30*1000;					// ����MergerBolt�����̨��ӡͳ�ƽ�������ڣ�Ĭ��Ϊ5����
	public static int archivePeriod = 1*60*1000;				// ����MergerBolt��ͳ�ƽ���鵵�����ڣ�Ĭ��Ϊ15����
	public static int clearPeriod = 24*60*60*1000;				// ����LogAnalyzerBolt��MergerBolt�������map�����ڣ�Ĭ��Ϊ24Сʱ
	
	public static int streamInterval = 0;						// ���ı���ȡ���ݲ����͵����ڣ�Ĭ��Ϊ1 elements per second
}
