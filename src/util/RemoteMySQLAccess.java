package util;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RemoteMySQLAccess {
	
	public Connection 	conn;  		// ���ڽ�����MySQL���ݿ������ 
	public Statement 	stmt;  		// ����ִ��SQL���
	SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
	
	/**
	 * RemoteMySQLAccess��Ĺ��캯��
	 */
	public RemoteMySQLAccess(){
		
		// String url="jdbc:mysql://192.168.1.2/"+ dbName + "?useUnicode=true&characterEncoding=GB2312";
		
		/*
		 * ����������
		 * URLָ��Ҫ���ʵ����ݿ���(cloud)��������Դ"jdbc:mysql://10.107.18.202:3306/storm"
		 * MySQL����ʱ���û���������
		 */
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://" + Constants.DB_SERVER + ":" + Constants.DB_PORT + "/" + Constants.DB_NAME;
		String user = Constants.USER;
		String password = Constants.PASSWORD;
		
		try {  

			Class.forName(driver).newInstance();  						// ����ConnectorJ���� ��Ҳ��дΪClass.forName(driver);
			conn = DriverManager.getConnection(url, user, password);	// ������MySQL���ݿ������ 
			
			if(!conn.isClosed()){
				System.out.println(datef.format(new Date()) + "\tSucceeded connecting to the Database!");
			}
			
			stmt = conn.createStatement();  								// statement����ִ��SQL���
		  
		}catch (Exception ex){  
		  System.err.println("Error : " + ex.toString());  
		  FileUtil.appendStrToFile(datef.format(new Date()) + "\tError when create a connection\t" + ex.toString(), 
				  Constants.RESOURCE_DIRECTORY, "mySQL.log");
		}
	}
	
	/**
	 * ʵ��ִ��һ��SQL���
	 * @param sql ��ִ�е�SQL���
	 */
	public void execute(String sql){
		try{
			
			stmt.execute(sql);
//			conn.close();
			
		}catch (Exception ex){  
		  System.err.println("Error : " + ex.toString());  
		  FileUtil.appendStrToFile(datef.format(new Date()) + "\tError when execute a sql\t" + ex.toString(), 
				  Constants.RESOURCE_DIRECTORY, "mySQL.log");
		}
	}
}
