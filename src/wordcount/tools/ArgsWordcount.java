package wordcount.tools;

public class ArgsWordcount {

	public static int maxTaskPara = 100;						// ��������̲߳��жȣ�Ĭ��Ϊ10���ò���ֻ�ڱ���ģʽ�����ã�
	public static int numWorkers = 5;							// ����Worker����Ŀ��Ĭ��Ϊ5
	public static int numAckers = 0;							// ����Acker����Ŀ��Ĭ��Ϊ0
	
	public static int paraSpout = 8;							// ����Spout���жȣ�Ĭ��Ϊ1
	public static int paraBolt1 = 10;							// ���õ�һ��Bolt�Ĳ��жȣ�Ĭ��Ϊ3
	public static int paraBolt2 = 5;							// ���õڶ���Bolt�Ĳ��жȣ�Ĭ��Ϊ1
	
	public static int streamInterval = 1;						// ���ı���ȡ���ݲ����͵����ڣ�Ĭ��Ϊ1 elements per second
}
