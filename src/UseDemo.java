/**
 * @author Xin Chen
 * Created on 2009-11-11
 * Updated on 2010-08-09
 * Email:  xchen@ir.hit.edu.cn
 * Blog:   http://hi.baidu.com/爱心同盟_陈鑫
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
 * TextExtractor功能测试类.
 */

public class UseDemo {
	
	public static void main(String[] args) throws IOException {
		
		/* 
		 * 测试网站：
		 * 百度博客空间             http://hi.baidu.com/liyanhong/
		 * 新浪娱乐音乐新闻与信息	http://ent.sina.com.cn/music/roll.html
		 * 腾讯娱乐新闻与信息		http://ent.qq.com/m_news/mnews.htm
		 * 搜狐音乐新闻				http://music.sohu.com/news.shtml
		 * 哈尔滨工业大学校内信息网 http://today.hit.edu.cn/
		 * 哈尔滨工业大学校内新闻网 http://news.hit.edu.cn/
		 */


		/* 注意：本处只为展示抽取效果，不处理网页编码问题，getHTML只能接收GBK编码的网页，否则会出现乱码 */
		String content = getHTML("http://shipei.qq.com/a/games/20130428000017/GAM2013042800001700#TencentContent", "utf8");
//		String content = "今天真是忙碌的一天，不说昨夜醒了两次，从今早七点起来忙完孩子，后去医院，接着在外办事到下午三点，之后又带宝宝遛弯儿，回来哄睡后，洗菜切菜，饭后给孩子洗澡哄睡，九点开始加班到十一点，然后和领导短信沟通完，洗漱完毕到现在终于可以躺下了，怎么感觉比上班天还疲劳，好久没这么高负荷运转了。北京空气实在差，今天去医院看中医，排队前面好多人挂呼吸科挂不上，说全天满号，还有好多是呼吸科加号的。空气质量导致呼吸系统疾病越来越多了。我们的生存环境啊，怎么说呢？不知道怎么说了。对了今天宝宝自满六个月长牙之后，终于长出了第三颗牙啦。纪念一下哈哈。宝宝今天会发姐姐和妹妹的发音了，这是自从学会发爸爸妈妈爷爷奶奶之后学会的新的音节。真好玩。不活现在还只是爸爸妈妈奶奶这三个音会跟着大人有意识地叫，其他都只是发音而已。什么时候真正会说话啊。期待着。那你农夫山泉达到没有？世界卫生组织的标准具体是什么？给说说和展示一下你们的数据啊？逝去的人把所有烦恼留在了人间，安详宁静地离去，活着的人把所有的期待放在了未来，忘记了现在。短短一辈子，一天一天又一天，宝宝逐渐长大，妈妈逐渐变老。人生轮回，真有下辈子么？有时候多一事不如少一事，胶多不黏话多不甜。自己的心结解不开，别人再怎么样也无济于事，所以一定要学会放下，不要太在意别人的看法。人有时候就是总打着为你好的名义去做一些事，却不知别人实际上是很讨厌你这种做法，切记，不要自以为是。这就是公司的劣根性，底下每次征求意见的时候不说话都说挺好，一到大老板出现的场合各种声音就出来了。结果辛苦一两月出来的东西什么都定不下来。这还是公司二把手牵头的事情都这个样子。其他人牵头的还能好到哪儿去。产后抑郁确实挺让人崩溃的，大多是缺乏家人的关爱。家庭有很重要的关系。可怜了宝宝，唉。 //@我们都是好妈妈:【郑州一女子带着四个月大女婴跳楼 双双身亡】@大河报 报道，今日九点半左右，郑州丰庆路博颂路，明天花园小区，一女子带着仅四个月大的女婴跳楼，双双身亡。据跳楼女子父亲介绍，女子32岁.生孩子后我真是成长了，没有了像原来一样妈妈在身边围着我转的情景，我照顾宝宝，照顾自己，每天忙的团团转，自己的时间基本没有。即使面临诸多挑剔的眼光和话语，我也逐步学会看开了，有些事情不值得的就放下，自己锻炼身体，自己注重健康，自己寻找快乐，自己过好自己的生活。宝宝今晚哭得很厉害，尤其在我进了卧室又出去后，白天在公园也是，连外婆和爸爸都不要只要妈妈。身体什么都好好的，看了相关内容，说这么大的孩子分离焦虑是最大的时候，包括不肯睡觉因为睡着了就看不见妈妈了。@aldream 我在//@张思莱医师:回复@健康教育何超:往往孩子感染幽门螺杆菌主要是大人传给他的，例如大人嚼食喂孩子，大人用自己用过的碗等食具给孩子用，没有分歺造成家人混吃同盘菜等。不健康的喂养方式遗害无穷。 //@健康教育何超:幽门螺杆菌感染率在幼年时较高，与喂养方式不当密切相关。喂养幼儿时，不感慨做家务也得用好工具，原来每次擦镜子硬是覆盖着一层膜弄不干净，后来问了单位的保洁阿姨诀窍，原来人家用玻璃清洁剂。我太out啦。于是马上购进，顺带发现抽油烟机清洁剂一并买了。今天打扫完，镜子光亮如新，厨房干净整洁，心里这个美啊！";
		// http://ent.sina.com.cn/y/2010-04-18/08332932833.shtml
		// http://ent.qq.com/a/20100416/000208.htm
		// http://ent.sina.com.cn/y/2010-04-18/15432932937.shtml
		// http://ent.qq.com/a/20100417/000119.htm
		// http://news.hit.edu.cn/articles/2010/04-12/04093006.htm
	

		/* 
		 * 当待抽取的网页正文中遇到成块的新闻标题未剔除时，只要增大此阈值即可。
		 * 相反，当需要抽取的正文内容长度较短，比如只有一句话的新闻，则减小此阈值即可。
		 * 阈值增大，准确率提升，召回率下降；值变小，噪声会大，但可以保证抽到只有一句话的正文 
		 */
//		TextExtract.setThreshold(76); // 默认值86

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
		
		StringBuffer sb = new StringBuffer();
		for (Term term : terms) {
			
			String nstr = term.getNatrue().natureStr;
			if (("n".equals(nstr))
					&& !("是".equals(term.getName()) || "有".equals(term
							.getName()))) {
				System.out.println(term.getName() + "\t"
						+ term.getNatrue().natureStr);
				if (dic.get(term.getName()) == null) {
					dic.put(term.getName(), 1);
				} else {
					dic.put(term.getName(), (dic.get(term.getName()) + 1));
				}
			}
			sb.append(term.getName() + " ");
			
		}
		System.out.println(sb.toString());
		
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
//		按照关键词出现顺序，使用过一次以后就不用了。
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

//		按照每句出现的所有关键词权重衡量句子重要性
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
		
		String[] dot = {"\n", "。", "？", "！"};
		
		
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
