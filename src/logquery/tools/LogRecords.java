package logquery.tools;

import java.util.HashMap;

public class LogRecords {
	
	private String response;	// ����������Ӧʱ�䣬��0.000
	private String cookie;		// �����cookie����ezq05FI1HecstEorPZPJAg==
	private String timestamp;	// �ô������ʱ�������25/Nov/2013:18:26:33
	private String request;		// ����վ��ͨ�����ַ�ʽ��ȡ����Щ��Ϣ��������¼�����url��httpЭ��
	private String hostname;	// �����ߵ�����������xk.2QxD.gr5.XxO
	private String httpstate;	// ״̬�룬����������Ӧ״̬(1xx��������Ϣ��2xx������ɹ���3xx�������ض���4xx���ͻ��˴���5xx������������)
	private String referer;		// ��Դҳ�棬������¼���Ǹ�ҳ�����ӷ��ʹ����ģ�����Ϊ��-��
	private String agent;		// ��¼�ͻ�������������Ϣ������Ϊ��-��
	private String size;		// �ô�������һ��������ֽ�������1690����0
	private String ip;			// �����ߵ�ip��ַ����116.112.113.50
	
	
	public LogRecords(){}
	
	/**
	 * ����һ���ַ�������һ��LogRecord�࣬
	 * @param record_str
	 */
	public LogRecords(String record_str){
		
		HashMap<String, String> record_map = new HashMap<String, String>();
		record_map = this.formatLogRecord(record_str);							// ��ʽ�����ַ�����������һ��map
		/*
		 * �����map��ȡ��������ʽ���ļ�ֵ�ԣ������ڹ����LogRecord��
		 */
		if(record_map.containsKey("response")){
			this.response = record_map.get("response");
		}else{
			this.response = null;
			System.err.println("Key is not found: response" );
		}
		
		if(record_map.containsKey("cookie")){
			this.cookie = record_map.get("cookie");
		}else{
			this.cookie = null;
			System.err.println("Key is not found: cookie" );
		}
		
		if(record_map.containsKey("timestamp")){
			this.timestamp = record_map.get("timestamp");
		}else{
			this.timestamp = null;
			System.err.println("Key is not found: timestamp" );
		}

		if(record_map.containsKey("request")){
			this.request = record_map.get("request");
		}else{
			this.request = null;
			System.err.println("Key is not found: request" );
		}
		
		if(record_map.containsKey("hostname")){
			this.hostname = record_map.get("hostname");
		}else{
			this.hostname = null;
			System.err.println("Key is not found: hostname" );
		}
		
		if(record_map.containsKey("httpstate")){
			this.httpstate = record_map.get("httpstate");
		}else{
			this.httpstate = null;
			System.err.println("Key is not found: httpstate" );
		}
		
		if(record_map.containsKey("referer")){
			this.referer = record_map.get("referer");
		}else{
			this.referer = null;
			System.err.println("Key is not found: referer" );	
		}
		
		if(record_map.containsKey("agent")){
			this.agent = record_map.get("agent");
		}else{
			this.agent = null;
			System.err.println("Key is not found: agent" );		
		}
		
		if(record_map.containsKey("size")){
			this.size = record_map.get("size");
		}else{
			this.size = null;
			System.err.println("Key is not found: size" );
		}
		
		if(record_map.containsKey("ip")){
			this.ip = record_map.get("ip");
		}else{
			this.ip = null;
			System.err.println("Key is not found: ip" );		
		}
	}
	
	
	
	/**
	 * �ַ�����ʽ��:����ȡ��ÿһ����־��¼���ַ���������ʽ������,���ַ����Ǵ���־Դ�ļ�access_log�ж�ȡ���У�����
	 * 
	 * {"response":"0.033","cookie":"-","timestamp":"25/Nov/2013:18:26:33",
	 * "request":"GET /2QxD/kv9vuX/aararrgeSaBg5largBSla5alr/ HTTP/1.1",
	 * "hostName":"JoDKggB.2QxD.gr5.XxO","httpstate":"200","referer":"-",
	 * "agent":"-","size":"57094","ip":"110.241.20.188"}
	 * 
	 * @param record_str:
	 * @return ������ʽ������ļ�ֵ�Է���һ��map��
	 */
	public HashMap<String, String> formatLogRecord(String record_str){
		
		HashMap<String, String> record_map = new HashMap<String, String>();		// ���ڴ洢��ʽ��������Լ�ֵ��
		
		/*
		 * ȥ����ĩβ�������հ׷���Tab����,ȥ����־��¼�ַ�����β�Ļ����ţ�"{"��"}"
		 * �Զ���","�з��ַ���
		 */
		record_str = record_str.trim().toLowerCase();
		record_str = record_str.substring(1, (record_str.length()-1)); 
		String [] key_value = record_str.split("(?<=\")(\\s*),(\\s*)(?=\")");
		
		/*
		 * ��ð��":"�ָ�ÿ�����Եļ�ֵ�ԣ�
		 * �ֱ�ȥ��key_str��value_str�е���β�հ׷�����β˫����
		 */
		for(String key_value_str: key_value){
			
			String[] str_tmp = key_value_str.split("(?<=\")(\\s*):(\\s*)(?=\")"); 
			String key_str = str_tmp[0].trim();									
			String value_str = str_tmp[1].trim();
			key_str = key_str.substring(1, key_str.length()-1);
			value_str = value_str.substring(1, value_str.length()-1);
			
			// �Ѿ�������ļ�ֵ�Է���map��
			if(!record_map.containsKey(key_str)){
				record_map.put(key_str, value_str);
			}else{
				System.out.println("This key-value pair is already put into the map.");
			}
		}
		return record_map;
	}
	
	
	
	/**
	 * ���ݲ����ֱ��ȡ��ͬ���ȵ�ʱ����ַ�����
	 * @param precision ����ʾ���ȣ�����Ϊ0��1��2��3��4��5ʱ��������Ϊ���ꡢ�¡��պ�Сʱ�����ӡ���
	 * @return ����һ�����ȵ�ʱ����ַ���
	 */
	public String timePrecision(int precision){
		
		TimeStamps ts = new TimeStamps(this.timestamp);		//��ʽ��ʱ����ַ���������һ��TimeStamps��Ķ���
		String time = "";
		
		switch(precision){
		case 0:
			time = ts.getYear() + "";
			break;
		case  1:
			time = ts.getYear() + "-" + ts.getMonth();
			break;
		case 2:
			time = ts.getYear() + "-" + ts.getMonth() + "-" + ts.getDay();
			break;
		case 3:
			time = ts.getYear() + "-" + ts.getMonth() + "-" + ts.getDay() + ":" + ts.getHour();
			break;
		case 4:
			time = ts.getYear() + "-" + ts.getMonth() + "-" + ts.getDay() + ":" + ts.getHour()
					+ ":" + ts.getMinute();
			break;
		case 5:
			time = ts.getYear() + "-" + ts.getMonth() + "-" + ts.getDay() + ":" + ts.getHour()
					+ ":" + ts.getMinute() + ":" + ts.getSecond();
			break;
		default:
			System.err.println("The input precision is invalid. Please specify a number in {0,1,2,3,4,5}.");
			break;
		}
		
		return time;
	}
		
	/*
	 * The getter and setter of all Class Members
	 */
	public void setResponse(String response) {
		this.response = response;
	}
	public String getResponse() {
		return response;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	public String getCookie() {
		return cookie;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getRequest() {
		return request;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHttpstate(String httpstate) {
		this.httpstate = httpstate;
	}
	public String getHttpstate() {
		return httpstate;
	}
	public void setReferer(String referer) {
		this.referer = referer;
	}
	public String getReferer() {
		return referer;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public String getAgent() {
		return agent;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getSize() {
		return size;
	}
	public void setIP(String ip) {
		this.ip = ip;
	}
	public String getIP() {
		return ip;
	}
	
}
