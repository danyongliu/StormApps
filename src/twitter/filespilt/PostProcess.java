package twitter.filespilt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;


import util.Constants;

public class PostProcess {

	/**
	 * ��twitter���ݵĺ���
	 * �Ѿ���һЩ�ֲ��������hashtags.csv�У���ȡ���ļ����ۺ���ͬkey�������һ�����ļ�finalStat.csv
	 * @param args
	 */
	public static void main(String[] args) {

		long starttime = System.currentTimeMillis();
		HashMap<String, Integer> tag_map = new HashMap<String, Integer>();
		/*
		 * ��ȡ�ļ���������һ��Map
		 */
		String pathIn = Constants.RESOURCE_DIRECTORY + "hashtags1.csv";
		File fileIn = new File(pathIn);
		try {
			BufferedReader bReader = new BufferedReader(new FileReader(fileIn));
			
			String str_line = "";
			while(bReader.ready()){
				str_line = bReader.readLine();
				if((str_line != null) && (!str_line.startsWith("2014-"))){
					
					String[] marks = str_line.split(",");
					
					int value = Integer.parseInt(marks[1]);
					if(tag_map.containsKey(marks[0])){
						
						value += tag_map.get(marks[0]);
					}
					tag_map.put(marks[0], value);
				}
				
			}
			bReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Reconstruct the map: " + (System.currentTimeMillis() - starttime) + " ms.");
		starttime = System.currentTimeMillis();
		/*
		 * ��Mapд���ļ�
		 */
		String pathOut = Constants.RESOURCE_DIRECTORY + "finalStat.csv";
		File fileOut = new File(pathOut);
		try{
			BufferedWriter bWriter = new BufferedWriter(new FileWriter(fileOut));
			Set<String> keySet = tag_map.keySet();
			for(String key: keySet){
				
				String str = key + "," + tag_map.get(key);
				bWriter.write(str);
				bWriter.newLine();
				bWriter.flush();
			}
			
			bWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Write the map: " + (System.currentTimeMillis() - starttime) + " ms.");
	}

}
