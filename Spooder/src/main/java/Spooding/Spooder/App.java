package Spooding.Spooder;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Example program to list links from a URL.
 */
public class App {
    public static void main(String[] args) throws IOException {
    	Scanner input = new Scanner(System.in);
    	System.out.print("Enter search string:");
    	String searchString = input.next();
    	String url = "https://www.reddit.com/search/?q=" + searchString;
    	RedditCrawler redditCrawl = new RedditCrawler(url);
    	redditCrawl.crawl();
    	
    	
//        System.out.println("Fetching " + url + "...");
//        WebClient client = new WebClient();
//        client.getOptions().setCssEnabled(false);
//        client.getOptions().setJavaScriptEnabled(false);
//        HtmlPage page = client.getPage(url);
//        List<HtmlElement> itemList = page.getByXPath("//a[@data-click-id='body']");
//        for (HtmlElement item : itemList) {
////        	System.out.println(item);
////        	URL nextUrl = page.getFullyQualifiedUrl(((HtmlAnchor) item).getAttribute("href"));
//        	System.out.println(item.getTextContent());
//        	HtmlPage nextPage = ((HtmlAnchor) item).click();
//        	List<HtmlElement> nextPageList = nextPage.getByXPath("//div[@data-click-id='text']//p");
//        	String postText = "";
//        	for (HtmlElement text : nextPageList) {
//        		postText += text.getTextContent() + "\n";
//        	}
//        	System.out.println(postText);
//        }
    }
}
