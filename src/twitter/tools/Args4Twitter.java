package twitter.tools;

public class Args4Twitter {

	public static String TWITTER_LOG = "twitter_log"; 				// ԭʼ����־�ļ�
	public static String HashTags_LOG = "hashtags.csv"; 		// Hashtags��ͳ�ƽ��
	
	public static int maxTaskPara = 100;						// ��������̲߳��жȣ�Ĭ��Ϊ10���ò���ֻ�ڱ���ģʽ�����ã�
	public static int numWorkers = 10;							// ����Worker����Ŀ��Ĭ��Ϊ5
	public static int numAckers = 0;							// ����Acker����Ŀ��Ĭ��Ϊ0
	
	public static int paraSpout = 1;							// ����Spout���жȣ�Ĭ��Ϊ1
	public static int paraBolt1 = 5;							// ���õ�һ��Bolt�Ĳ��жȣ�Ĭ��Ϊ3
	public static int paraBolt2 = 5;							// ���õڶ���Bolt�Ĳ��жȣ�Ĭ��Ϊ1
	public static int paraBolt3 = 1;							// ���õ�����Bolt�Ĳ��жȣ�Ĭ��Ϊ1
	public static int numTask = 1;								// ����ÿ��executor����ٸ�task��Ĭ��Ϊ1
	
	public static int clearPeriod = 10*1000;					// HashtagsCounterBolt������ձ���map������
	public static int archivePeriod = 10*1000;					// ����HashtagsCounterBolt��HashtagsMergerBolt���;ֲ�ͳ�����ݵ����ڣ�
	
	public static int streamInterval = 1;						// ���ı���ȡ���ݲ����͵����ڣ�Ĭ��Ϊ1 elements per second
	
	
}
