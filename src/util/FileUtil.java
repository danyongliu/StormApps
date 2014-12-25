package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class FileUtil {
	// ************************************************ file writers **********************************************
	// ************************************************************************************************************
	/**
	 *  ��һ��String���͵�����listд��dirĿ¼�µ�filename�ļ���
	 *  
	 *  @param list 		��д������� 
	 *  @param dir 			Ŀ���ļ����ڵ��ļ���
	 *  @param filename 	Ŀ���ļ����ļ���
	 */
	public static void writeListToFile(LinkedList<String> list, String dir, String filename){
		
		String filePathOut = dir + filename;
		File fileOut = new File(filePathOut);
		try{
			FileWriter fWriter = new FileWriter(fileOut);
			BufferedWriter bWriter = new BufferedWriter(fWriter);
			for(String str: list){
				bWriter.write(str);
				bWriter.newLine();
				bWriter.flush();
			}
			bWriter.close();
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	/**
	 *  ��һ��Stringд��dirĿ¼�µ�filename�ļ���
	 *  @param str 			��д���String����
	 *  @param dir 			Ŀ���ļ����ڵ��ļ���
	 *  @param filename 	Ŀ���ļ����ļ���
	 */
	public static void writeStrToFile(String str, String dir, String filename){
		
		String filePathOut = dir + filename;
		File fileOut = new File(filePathOut);
		try{
			FileWriter fWriter = new FileWriter(fileOut);
			BufferedWriter bWriter = new BufferedWriter(fWriter);
			bWriter.write(str);
			bWriter.newLine();
			bWriter.flush();
			bWriter.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * ��һ��HashMap<String, Long>���͵�map������ӵ�dirĿ¼��ָ���ļ���ĩβ
	 * @param map 			��д���map
	 * @param dir 			Ŀ���ļ����ڵ��ļ���
	 * @param filename 		Ŀ���ļ����ļ���
	 */
	public static void appendMapToFile(HashMap<String, Long> map, String dir, String filename){
		
		if(map.isEmpty()){
			System.err.println("Specified a null map.");
			return;
		}else{
			//��׷��д�ķ�ʽд�뵽�ļ�
			String filePathOut = dir + filename;
			File fileOut = new File(filePathOut);
			try{
				FileWriter fWriter = new FileWriter(fileOut, true);
				fWriter.write("\n===============" + new Date() + "===============");
				fWriter.flush();
				
				Set<String> keySet = map.keySet();
				for(String key: keySet){
					String str_temp = "\n" + key + "\t\t" + map.get(key);
					fWriter.write(str_temp);
					fWriter.flush();
				}
				fWriter.close();

			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * ��һ��HashMap<String, HashMap<String, Long>>���͵�map������ӵ�dir��ָ���ļ���ĩβ
	 * @param map 			��д���map
	 * @param dir 			Ŀ���ļ����ڵ��ļ���
	 * @param filename 		Ŀ���ļ����ļ���
	 */
	public static void appendMapToFile2(HashMap<String, HashMap<String, Long>> map, String dir, String filename){
		
		if(map.isEmpty()){
			System.err.println("Specified a null map.");
			return;
		}else{
			String filePathOut = dir + filename;
			File fileOut = new File(filePathOut);
			try{
				FileWriter fWriter = new FileWriter(fileOut, true);
				fWriter.write("\n===============" + new Date() + "===============");
				fWriter.flush();
				
				Set<String> keySet = map.keySet();
				for(String key: keySet){
					fWriter.write("\n" + key);
					fWriter.flush();
					
					HashMap<String, Long> subMap = map.get(key);
					Set<String> subSet = subMap.keySet();
					for(String subKey: subSet){
						fWriter.write("\n\t" + subKey + "\t\t" + subMap.get(subKey));
						fWriter.flush();
					}
					
				}
				
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 *  ��һ��String��׷��д�ķ�ʽд��dirĿ¼�µ�filename�ļ���
	 *  @param str 			��д���String����
	 *  @param dir 			Ŀ���ļ����ڵ��ļ���
	 *  @param filename 	Ŀ���ļ����ļ���
	 */
	public static void appendStrToFile(String str, String dir, String filename){
		
		String filePathOut = dir + filename;
		File fileOut = new File(filePathOut);
		try{
			FileWriter fWriter = new FileWriter(fileOut, true);
			BufferedWriter bWriter = new BufferedWriter(fWriter);
			bWriter.write(str);
			bWriter.newLine();
			bWriter.flush();
			bWriter.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	// ************************************************** file readers ********************************************
	// ************************************************************************************************************
	
	/**
	 *  ��dir��ָ���ļ�filename�ж�ȡnum�����ݣ�������һ��String���͵�����
	 *  
	 *  @param lineNum 				����ȡ������
	 *  @param dir 					Ŀ���ļ����ڵ��ļ���
	 *  @param filename 			Ŀ���ļ����ļ���
	 *  @return LinkedList<String> 	���ض�ȡ������
	 */
	public static LinkedList<String> getLinesFromFile(int lineNum, String dir, String filename){
		
		LinkedList<String> line_list = new LinkedList<String>();		// ���������ڴ�Ŵ��ļ��ж�ȡ������
		
		String filePathIn = dir + filename;
		File fileIn = new File(filePathIn);
		try{
			FileReader fReader = new FileReader(fileIn);
			BufferedReader bReader = new BufferedReader(fReader);
			
			int count = 0;
			String str_line = "";
			while(bReader.ready() && (count<lineNum)){
				str_line = bReader.readLine();
				if(str_line == null){									// ��������
					System.err.println("Error Read. Null line.");
				}else{
					line_list.add(str_line);
					count++;
				}
			}
			bReader.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return line_list;
	}
	
	/**
	 *  ��ָ���ļ�filename��ɾ��lineNum������
	 * 
	 * @param lineNum 	��ɾ��������
	 * @param dir 		Ŀ���ļ����ڵ��ļ���
	 * @param filename 	Ŀ���ļ����ļ���
	 */
	public static void deleteLinesFromFile(int lineNum, String dir, String filename){
		
		File fileIn = new File(dir + filename);
		try{
			if(!fileIn.isFile()){
				System.err.println(filename + " is not a file. Please check it again.");
				return;
			}else{
				File tempFile = new File(fileIn.getAbsolutePath() + ".tmp");
				BufferedReader bReader = new BufferedReader(new FileReader(fileIn));
				BufferedWriter bWriter = new BufferedWriter(new FileWriter(tempFile));
				
				String str_line = "";
				int count = 0;
				while(bReader.ready()){
					str_line = bReader.readLine();
					count++;
					if(count > lineNum){
						bWriter.write(str_line);
						bWriter.newLine();
						bWriter.flush();
					}
				}
				bReader.close();	//�ر�������
				bWriter.close();	//�ر������
				
				// Delete the original file: fileIn
				if(!fileIn.delete()){
					System.err.println("Could not delete the file");
			        return;
				}
				// Rename the tempFile to fileIn's name: filename
				if(!tempFile.renameTo(fileIn)){
					System.err.println("Could not rename the file");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ָ���ļ���ɾ��ָ�����ݵ���
	 * @param list 		��ɾ��������
	 * @param dir 		Ŀ���ļ����ڵ��ļ���
	 * @param filename	Ŀ���ļ����ļ���
	 */
	public static void deleteLinesFromFile(LinkedList<String> list, String dir, String filename){
		
		LinkedList<String> file_list = new LinkedList<String>();
		
		// ��ָ���ļ��ж�ȡ���е��в�����һ������file_list
		File fileIn = new File(dir + filename);
		if(!fileIn.exists()){
			System.err.println("File not exist. Please check again.");
			return;
		}else{
			try{
				BufferedReader bReader = new BufferedReader(new FileReader(fileIn));
				String str_line = "";
				while(bReader.ready()){
					str_line = bReader.readLine();
					if(str_line == null){
						System.err.println("Error Read. Null line.");
					}else{
						file_list.add(str_line);
					}
				}
				bReader.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		// ��file_list��ɾ��list�а���������
		for(String str: list){
			if(file_list.contains(str)){
				file_list.remove(str);
			}
		}
		// ��file_list����д���ļ��У��������ļ��е�ԭ������
		writeListToFile(file_list, dir, filename);
		
	}
	
	/**
	 *  ��ָ���ļ�filename�ж�ȡlineNum�����ݣ������ļ���ɾ���Ѷ�ȡ����
	 *  ���Ѷ�ȡ��������������String�������ʽ���أ�String�����ÿһ��Ԫ�ر�ʾ��ȡ��һ������
	 *  
	 *  @param lineNum 				����ȡ������
	 *  @param dir 					Ŀ���ļ����ڵ��ļ���
	 *  @param filename 			Ŀ���ļ����ļ���
	 *  @return LinkedList<String> 	���ض�ȡ������
	 */
	public static LinkedList<String> removeLinesFromFile(int lineNum, String dir, String filename){
		
		LinkedList<String> line_list = new LinkedList<String>();	// ���������ڴ�Ŵ��ļ��ж�ȡ������
		
		File fileIn = new File(dir + filename);
		if(!fileIn.isFile()){
			System.err.println(filename + " is not a file. Please check it again.");
			return null;
			
		}else{
			File tempFile = new File(fileIn.getAbsolutePath() + ".tmp");
			try{
				BufferedReader bReader = new BufferedReader(new FileReader(fileIn));
				BufferedWriter bWriter = new BufferedWriter(new FileWriter(tempFile));
				
				int count = 0;
				String str_line = "";						//���ļ��ж�ȡ��ÿһ������
				
				while(bReader.ready()){
					str_line = bReader.readLine();
					count ++;
					if(str_line == null){
						System.err.println("Error Read. Null line.");
					}else if(count <= lineNum){
						line_list.add(str_line);			//�Ѷ�ȡ�������ݷŵ�line_list�Ķ�Ӧλ��
					}else{
						bWriter.write(str_line);			//�Ѷ�ȡ��������д�뵽tempFile��
						bWriter.newLine();
						bWriter.flush();
					}
				}
				bReader.close();
				bWriter.close();
				
				// Delete the original file: fileIn
				if(!fileIn.delete()){
					System.err.println("Could not delete the file");
			        return null;
				}
				// Rename the tempFile to fileIn's name: filename
				if(!tempFile.renameTo(fileIn)){
					System.err.println("Could not rename the file");
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		return line_list;
	}
	
}
