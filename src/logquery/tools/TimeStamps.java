package logquery.tools;

public class TimeStamps {
	
	private int year;
	private int month;		// ֵ�ķ�ΧΪ1~12
	private int day;		// ֵ�ķ�ΧΪ1~31
	private int hour;		// ֵ�ķ�ΧΪ0~23
	private int minute;		// ֵ�ķ�ΧΪ0~59
	private int second;		// ֵ�ķ�ΧΪ0~59
	
	public TimeStamps(){}
	/**
	 * ���ݸ������ַ�������һ��TimeStamps����
	 * formatType��ʽ�磬 "25/nov/2013:23:00:26"
	 */
	public TimeStamps(String timestamp){
		
		/*
		 * �Ѹ����ַ����зֳ������֣��ֱ�Ϊ���ں�ʱ�䣻
		 * Ȼ���ٴ��з����ں�ʱ�䣬����ꡢ�¡��գ��Լ�Сʱ�����Ӻ���
		 */
		int index = timestamp.indexOf(":");
		String date = timestamp.substring(0, index);
		String time = timestamp.substring(index + 1);
		
		String date_array[] = date.split("/");			// "25/nov/2013"
		String time_array[] = time.split(":");			// "23:00:26"
		
		/*
		 * �����з�����õ��ꡢ�¡��պ�Сʱ������������õ�ֵ����TimeStamps����
		 */
		this.year = Integer.parseInt(date_array[2]);	// ������
		
		if(date_array[1].contains("jan")){				// �����·�
			this.month = 1;
		}else if(date_array[1].contains("feb")){
			this.month = 2;
		}else if(date_array[1].contains("mar")){
			this.month = 3;
		}else if(date_array[1].contains("apr")){
			this.month = 4;
		}else if(date_array[1].contains("may")){
			this.month = 5;
		}else if(date_array[1].contains("jun")){
			this.month = 6;
		}else if(date_array[1].contains("jul")){
			this.month = 7;
		}else if(date_array[1].contains("aug")){
			this.month = 8;
		}else if(date_array[1].contains("sep")){
			this.month = 9;
		}else if(date_array[1].contains("oct")){
			this.month = 10;
		}else if(date_array[1].contains("nov")){
			this.month = 11;
		}else if(date_array[1].contains("dec")){
			this.month = 12;
		}else{
			System.err.println("Error month. There is no match to the specified month string.");
		}
		
		this.day = Integer.parseInt(date_array[0]);		// ������
		this.hour = Integer.parseInt(time_array[0]);	// ����Сʱ
		this.minute = Integer.parseInt(time_array[1]);	// ��������
		this.second = Integer.parseInt(time_array[2]);	// ��������
	}
	
	/**
	 * ��һ��TimeStamps��Ķ���ת��Ϊһ����ʽ���ַ���
	 * �ַ���formatType��ʽ�磬 "2013-11-25:23:00:26"
	 * @param timestamp
	 */
	public String toString(){
		return (this.year + "-" + this.month + "-" + this.day + ":" 
				+ this.hour + ":" + this.minute + ":" + this.second);
	}
	
	/*
	 * The setters and getters of the Class Members
	 */
	public void setYear(int year) {
		this.year = year;
	}
	public int getYear() {
		return year;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getMonth() {
		return month;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getDay() {
		return day;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getHour() {
		return hour;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public int getMinute() {
		return minute;
	}
	public void setSecond(int second) {
		this.second = second;
	}
	public int getSecond() {
		return second;
	}
}
