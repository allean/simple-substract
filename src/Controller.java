import org.ansj.splitWord.analysis.ToAnalysis;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;

public class Controller {
        public static void main(String[] args) throws Exception {
                String crawlStorageFolder = "./crawl/root";
                int numberOfCrawlers = 1;

                CrawlConfig config = new CrawlConfig();
                config.setCrawlStorageFolder(crawlStorageFolder);

                config.setMaxPagesToFetch(200);
                config.setMaxDepthOfCrawling(1);
                /*
                 * Instantiate the controller for this crawl.
                 */
                WebURL wu = new WebURL();
                
                PageFetcher pageFetcher = new PageFetcher(config);
//                pageFetcher.fetchHeader()
                
                RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
                RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
                CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

                /*
                 * For each crawl, you need to add some seed urls. These are the first
                 * URLs that are fetched and then the crawler starts following links
                 * which are found in these pages
                 */
                controller.addSeed("http://shipei.qq.com/iphone/m/news/index.htm#TencentIndex");
                controller.addSeed("http://shipei.qq.com/iphone/m/finance/index.htm#TencentIndex");
                controller.addSeed("http://shipei.qq.com/iphone/m/ent/index.htm#TencentIndex");
                controller.addSeed("http://shipei.qq.com/iphone/m/sports/index.htm#TencentIndex");
                controller.addSeed("http://shipei.qq.com/iphone/m/games/index.htm#TencentIndex");
  //              controller.addSeed("http://www.ics.uci.edu/");
//                ToAnalysis.paser("test");
                /*
                 * Start the crawl. This is a blocking operation, meaning that your code
                 * will reach the line after this only when crawling is finished.
                 */
                controller.start(MyCrawler.class, numberOfCrawlers);    
        }
}