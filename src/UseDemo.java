/**
 * @author Xin Chen
 * Created on 2009-11-11
 * Updated on 2010-08-09
 * Email:  xchen@ir.hit.edu.cn
 * Blog:   http://hi.baidu.com/����ͬ��_����
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.ansj.util.recognition.NatureRecognition;


/**
 * TextExtractor���ܲ�����.
 */

public class UseDemo {
	
	public static void main(String[] args) throws IOException {
		
		/* 
		 * ������վ��
		 * �ٶȲ��Ϳռ�             http://hi.baidu.com/liyanhong/
		 * ��������������������Ϣ	http://ent.sina.com.cn/music/roll.html
		 * ��Ѷ������������Ϣ		http://ent.qq.com/m_news/mnews.htm
		 * �Ѻ���������				http://music.sohu.com/news.shtml
		 * ��������ҵ��ѧУ����Ϣ�� http://today.hit.edu.cn/
		 * ��������ҵ��ѧУ�������� http://news.hit.edu.cn/
		 */


		/* ע�⣺����ֻΪչʾ��ȡЧ������������ҳ�������⣬getHTMLֻ�ܽ���GBK�������ҳ�������������� */
		String content = getHTML("http://www.iheima.com/archives/36362.html", "utf8");

		// http://ent.sina.com.cn/y/2010-04-18/08332932833.shtml
		// http://ent.qq.com/a/20100416/000208.htm
		// http://ent.sina.com.cn/y/2010-04-18/15432932937.shtml
		// http://ent.qq.com/a/20100417/000119.htm
		// http://news.hit.edu.cn/articles/2010/04-12/04093006.htm
	

		/* 
		 * ������ȡ����ҳ�����������ɿ�����ű���δ�޳�ʱ��ֻҪ�������ֵ���ɡ�
		 * �෴������Ҫ��ȡ���������ݳ��Ƚ϶̣�����ֻ��һ�仰�����ţ����С����ֵ���ɡ�
		 * ��ֵ����׼ȷ���������ٻ����½���ֵ��С��������󣬵����Ա�֤�鵽ֻ��һ�仰������ 
		 */
//		TextExtract.setThreshold(76); // Ĭ��ֵ86

		System.out.println("got html");
		String cont = TextExtract.parse(content);
		System.out.println(cont);
		System.out.println("=========substract=======");
//		List<Term> paser = BaseAnalysis.paser(cont) ;
//		System.out.println(paser);
		//System.out.println(sub);
//		Analysis udf = new ToAnalysis(new StringReader(cont));
//		Term term = null ;
//		while((term=udf.next())!=null){
//			System.out.print(term.getName() +" ");
//		}
		
		
//		System.out.println(getSentence(cont.trim()));
		cont = cont.trim();
		
		List<Term> terms = ToAnalysis.paser(cont);
		new NatureRecognition(terms).recognition() ;
		
		Map<String, Integer> dic = new HashMap();
		
		for (Term term : terms) {
			
			String nstr = term.getNatrue().natureStr;
			if (("n".equals(nstr))
					&& !("��".equals(term.getName()) || "��".equals(term
							.getName()))) {
				System.out.println(term.getName() + "\t"
						+ term.getNatrue().natureStr);
				if (dic.get(term.getName()) == null) {
					dic.put(term.getName(), 1);
				} else {
					dic.put(term.getName(), (dic.get(term.getName()) + 1));
				}
			}
			
		}
		
		ArrayList<Entry<String,Integer>> l = new ArrayList<Entry<String,Integer>>(dic.entrySet());  
		
		Collections.sort(l, new Comparator<Map.Entry<String, Integer>>() {

			@Override
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {    
                return (o2.getValue() - o1.getValue());    
            } 
			
		});
		
		
		for(Entry<String,Integer> e : l) {  
            System.out.println(e.getKey() + "::::" + e.getValue());  
        } 
		
		List<String> keyWord = new ArrayList();
		for(int i=0; i<Math.min(l.size(), 20);i++) {  
			Entry<String,Integer> entry = l.get(i);
			keyWord.add(entry.getKey());  
        } 
		System.out.println("keyWord : " + keyWord);
		List<String> sentence = getSentence(cont);
		List<String> rs = new ArrayList();
		int max = 5;
//		���չؼ��ʳ���˳��ʹ�ù�һ���Ժ�Ͳ����ˡ�
//		for (int i = 0; i < sentence.size(); i++) {
//			String sent = sentence.get(i);
//			String right = "";
//			for (String key : keyWord) {
//				if (sent.contains(key)) {
//					System.out.println("sent : " + sent);
//					rs.add(sent);
//					keyWord.remove(key);
//					break;
//				}
//				System.out.println("keyWord : " + keyWord);
//			}
//			
//			if (rs.size() >= max){
//				break;
//			}
//		}
//		
//		String result = "";
//		for (String string : rs) {
//			result += string;
//		}
//		System.out.println(result);

//		����ÿ����ֵ����йؼ���Ȩ�غ���������Ҫ��
		Map sentMap = new HashMap();
		for (int i = 0; i < sentence.size(); i++) {
			String sent = sentence.get(i);
					
			sentMap.put(i, calSentValue(sent, keyWord, dic));
		}
		
		
		List<Entry<Integer,Integer>> ll = new ArrayList<Entry<Integer,Integer>>(sentMap.entrySet());  
		
		Collections.sort(ll, new Comparator<Map.Entry<Integer, Integer>>() {

			@Override
			public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {    
                return (o2.getValue() - o1.getValue());    
            } 
			
		});
		
		List<Integer> rslist = new ArrayList();
		for(Entry<Integer,Integer> e : ll) {  
            System.out.println(e.getKey() + "::::" + e.getValue());  
            if (rslist.size() < max){
            	rslist.add(e.getKey());
            }
        }
		System.out.println("rslist : " + rslist);
		Collections.sort(rslist);
		System.out.println("rslist-sort : " + rslist);
		
		String rssent = "";
		for (Integer intt : rslist) {
			rssent += sentence.get(intt);
		}
		

		System.out.println("rssent : " + rssent);
		
		
		
		
	}
	
	public static int calSentValue(String sent, List<String> keyWord, Map<String, Integer> dic){
		int i = 0;
		for (String string : keyWord) {
			if (sent.contains(string)){
				i += dic.get(string);
			}
		}
		return i;
	}
	
	public static List<String> getSentence(String doc){
		List<String> rs = new ArrayList<String>();
//		String[] phase = doc.split("\n");
		
		String[] dot = {"\n", "��", "��", "��"};
		
		
		rs.add(doc);
		
		for (String d : dot) {		
			List<String> tmprs = new ArrayList();
			for (String string : rs) {
				tmprs.addAll(split(string, d));
			}
			rs = tmprs;
		}
		
		return rs;
		
		
	}
	
	public static List<String> split(String s, String dot){
		List<String> rs = new ArrayList();
		
		String[] split = s.split(dot);
		
		
		
		for (int i = 0; i < split.length; i++) {
			String string = split[i];
			if (i == (split.length - 1)){
				rs.add(string);
			}else{
				rs.add(string + dot);
			}
		}
		
		
		return rs;
	}


	public static String getHTML(String strURL, String encoding) throws IOException {
		URL url = new URL(strURL);
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), encoding));
		String s = "";
		StringBuilder sb = new StringBuilder("");
		while ((s = br.readLine()) != null) {
			sb.append(s + "\n");
		}
		return sb.toString();
	}
}
