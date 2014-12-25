package netease.tools;

import java.util.LinkedList;

public class SpiderSite {
	
	private LinkedList<String> spider_list; //�ñ������г��˳�����10����������֩��
	
	
	/**
	 * ��ʼʱ����list���г���10�г�������������֩��
	 * ����ֵΪ������ʼ������������֩������
	 */
	public SpiderSite(){
		
		LinkedList<String> spider_list = new LinkedList<String>();
		spider_list.add("googlebot");
		spider_list.add("baiduspider");
		spider_list.add("baidugame");
		spider_list.add("msnbot");
		spider_list.add("bingbot");
		spider_list.add("ahrefsbot");
		spider_list.add("360spider");
		spider_list.add("nutch");
		spider_list.add("sosospider");
		spider_list.add("sogou web spider");
		
		this.spider_list = spider_list;
	}
	
	/**
	 * ��list�����һ��֩����վ
	 * @param spider
	 */
	public void addSpider(String spider){
		if(this.spider_list.contains(spider.toLowerCase())){
			this.spider_list.add(spider.toLowerCase());
		}else{
			return;
		}
	}
	
	/**
	 * ��list��ɾ��һ��֩����վ
	 * @param spider
	 */
	public void removeSpider(String spider){
		if(this.spider_list.contains(spider.toLowerCase())){
			this.spider_list.remove(spider.toLowerCase());
		}else{
			return;
		}
	}
	
	/**
	 * �ж�һ��agent�ǲ���֩�룬���жϸ�agent�ֶ����Ƿ������֩����ֶΣ���������ˣ��򷵻�֩�����֣����򷵻�null
	 * @param agent mozilla/5.0 (compatible; bingbot/2.0; +http://www.bing.com/bingbot.htm)
	 * @return ���agent��spider���򷵻�spiderName�����򷵻�null
	 */
	public String containsSpider(String agent){
		String spiderName = "";
		agent = agent.trim().toLowerCase();
		for(String spider: this.spider_list){
			if(agent.contains(spider)){
				spiderName = spider;
				break;
			}else{
				spiderName = null;
			}
		}
		return spiderName;
	}

	/**
	 * �ж�һ��agent�ǲ���֩�룬���жϸ�agent�ֶ����Ƿ������֩����ֶΣ���������ˣ��򷵻�true�����򷵻�false
	 * @param agent
	 * @return
	 */
	public boolean contains(String agent){
		agent = agent.trim().toLowerCase();
		for(String spider: this.spider_list){
			if(agent.contains(spider)){
				return true;
			}
		}
		
		return false;
	}
	
	public void setSpiderList(LinkedList<String> spider_list) {
		this.spider_list = spider_list;
	}

	public LinkedList<String> getSpiderList() {
		return spider_list;
	}
}
