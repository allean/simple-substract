/**
 * @author Xin Chen
 * Created on 2009-11-11
 * Updated on 2010-08-09
 * Email:  xchen@ir.hit.edu.cn
 * Blog:   http://hi.baidu.com/爱心同盟_陈鑫
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.ansj.util.recognition.NatureRecognition;

/**
 * TextExtractor功能测试类.
 */

public class WordGen {

	public static void main(String[] args) throws IOException {

		/*
		 * 测试网站： 百度博客空间 http://hi.baidu.com/liyanhong/ 新浪娱乐音乐新闻与信息
		 * http://ent.sina.com.cn/music/roll.html 腾讯娱乐新闻与信息
		 * http://ent.qq.com/m_news/mnews.htm 搜狐音乐新闻
		 * http://music.sohu.com/news.shtml 哈尔滨工业大学校内信息网
		 * http://today.hit.edu.cn/ 哈尔滨工业大学校内新闻网 http://news.hit.edu.cn/
		 */

		/* 注意：本处只为展示抽取效果，不处理网页编码问题，getHTML只能接收GBK编码的网页，否则会出现乱码 */

		List<String> htmls = new ArrayList();
		// 财经
//		htmls.add("http://shipei.qq.com/c/finance/20130415006394/FIN2013041500639400");
//		htmls.add("http://shipei.qq.com/c/finance/20130415006390/FIN2013041500639000");
//		htmls.add("http://shipei.qq.com/c/finance/20130415006274/FIN2013041500627400");
//		htmls.add("http://shipei.qq.com/c/finance/20130415006285/FIN2013041500628500");
//		htmls.add("http://shipei.qq.com/c/finance/20130415006263/FIN2013041500626301");
//		htmls.add("http://shipei.qq.com/c/finance/20130415006250/FIN2013041500625000");
//		htmls.add("http://shipei.qq.com/c/finance/20130415006176/FIN2013041500617600");
//		htmls.add("http://shipei.qq.com/c/finance/20130415006160/FIN2013041500616000");
//
//		// 娱乐
//		htmls.add("http://shipei.qq.com/c/zt/201304130001660/ENT201304130001660B");
//		htmls.add("http://shipei.qq.com/c/ent/20130415000034/ENT2013041500003401");
//		htmls.add("http://shipei.qq.com/c/ent/8920130415000245/ENT2013041500024501");
//		htmls.add("http://shipei.qq.com/c/ent/20130415000485/ENT2013041500048502");
//
//		// 军事
//		htmls.add("http://shipei.qq.com/c/news/20130415000942/MIL2013041500094201");
//		htmls.add("http://shipei.qq.com/c/news/20130415001173/MIL2013041500117300");
//		htmls.add("http://shipei.qq.com/c/news/20130415001177/MIL2013041500117700");
//		htmls.add("http://shipei.qq.com/c/news/20130415001171/MIL2013041500117100");
// 		htmls.add("http://shipei.qq.com/c/news/20130415001034/MIL2013041500103401");
		
		htmls.add("http://www.bbc.co.uk/news/world-europe-22348160");
		
		

		for (int i = 0; i < htmls.size(); i++) {
			String html = htmls.get(i);
			String content = getHTML(html, "utf8");

			System.out.println("got html : " + html);
			String cont = TextExtract.parse(content);
//			System.out.println("=========substract=======");
			cont = cont.trim();

			List<Term> terms = ToAnalysis.paser(cont);
			new NatureRecognition(terms).recognition();

			Map<String, Integer> dic = new HashMap();

			StringBuffer sb = new StringBuffer();
			for (Term term : terms) {
				String nstr = term.getNatrue().natureStr;
				if (isUseFull(term)) {
					sb.append(term.getName() + " ");
				}

			}
			String result = sb.toString();
			System.out.println(sb.toString());
			write("./" + "infer" + ".txt", result);
		}

	}

	private static List unUsefulWord = new ArrayList();
	static {
		unUsefulWord.add("是");
		unUsefulWord.add("有");
		unUsefulWord.add("的");
		unUsefulWord.add("了");
	}

	private static boolean isUseFull(Term term) {
		String nstr = term.getNatrue().natureStr;
		return !unUsefulWord.contains(term.getName())
				&& ("n".equals(nstr));
	}

	public static int calSentValue(String sent, List<String> keyWord,
			Map<String, Integer> dic) {
		int i = 0;
		for (String string : keyWord) {
			if (sent.contains(string)) {
				i += dic.get(string);
			}
		}
		return i;
	}

	public static List<String> getSentence(String doc) {
		List<String> rs = new ArrayList<String>();
		// String[] phase = doc.split("\n");

		String[] dot = { "\n", "。", "？", "！" };

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

	public static List<String> split(String s, String dot) {
		List<String> rs = new ArrayList();

		String[] split = s.split(dot);

		for (int i = 0; i < split.length; i++) {
			String string = split[i];
			if (i == (split.length - 1)) {
				rs.add(string);
			} else {
				rs.add(string + dot);
			}
		}

		return rs;
	}

	public static String getHTML(String strURL, String encoding)
			throws IOException {
		URL url = new URL(strURL);
		BufferedReader br = new BufferedReader(new InputStreamReader(
				url.openStream(), encoding));
		String s = "";
		StringBuilder sb = new StringBuilder("");
		while ((s = br.readLine()) != null) {
			sb.append(s + "\n");
		}
		return sb.toString();
	}

	public static void write(String path, String content) {
		String s = new String();
		String s1 = new String();
		try {
			File f = new File(path);
			if (f.exists()) {
				System.out.println("文件存在");
			} else {
				System.out.println("文件不存在，正在创建...");
				if (f.createNewFile()) {
					System.out.println("文件创建成功！");
				} else {
					System.out.println("文件创建失败！");
				}
			}
			BufferedReader input = new BufferedReader(new FileReader(f));
			while ((s = input.readLine()) != null) {
				s1 += s + "\n";
			}
			System.out.println("文件内容：" + s1);
			input.close();
			s1 += content;
			BufferedWriter output = new BufferedWriter(new FileWriter(f));
			output.write(s1);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
