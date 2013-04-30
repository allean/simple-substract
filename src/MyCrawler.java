import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.ansj.util.recognition.NatureRecognition;
import org.apache.commons.codec.binary.StringUtils;

import love.cq.util.StringUtil;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawler extends WebCrawler {

        private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" 
                                                          + "|png|tiff?|mid|mp2|mp3|mp4"
                                                          + "|wav|avi|mov|mpeg|ram|m4v|pdf" 
                                                          + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

        /**
         * You should implement this function to specify whether
         * the given url should be crawled or not (based on your
         * crawling logic).
         */
        @Override
        public boolean shouldVisit(WebURL url) {
                String href = url.getURL().toLowerCase();
                System.out.println("href : " + href);
                return !FILTERS.matcher(href).matches() && href.startsWith("http://shipei.qq.com/");
        }

        /**
         * This function is called when a page is fetched and ready 
         * to be processed by your program.
         */
        @Override
        public void visit(Page page) {          
                String url = page.getWebURL().getURL();
                System.out.println("URL: " + url);

                if (page.getParseData() instanceof HtmlParseData) {
                        HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
                        String text = htmlParseData.getText();
                        String html = htmlParseData.getHtml();
                        List<WebURL> links = htmlParseData.getOutgoingUrls();
                        
   //                     WebURL wu = new WebURL();
                        
   //                     htmlParseData.setOutgoingUrls(outgoingUrls)
                        
                  //      System.out.println("Text length: " + text);
                 //       System.out.println("Html length: " + html);
                 //       String cont = TextExtract.parse(html).trim();
                        parseWord(html);
                //		System.out.println(cont);
                        System.out.println("Number of outgoing links: " + links);
                }
                
        }
        
        private void parseWord(String content){
        	String cont = TextExtract.parse(content);
			System.out.println("=========substract=======");
			System.out.println(cont);
			cont = cont.trim();

			if (null == cont || "".equals(cont)){
				return;
			}
			System.out.println("1");
			List<Term> terms = ToAnalysis.paser(cont);
			System.out.println("2");
			new NatureRecognition(terms).recognition();
			System.out.println("3");
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