package Spooding.Spooder;

import java.io.IOException;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class RedditCrawler extends Crawler {
	
	public RedditCrawler() {
		super();
	}
	public RedditCrawler(String url) {
		super.setBaseUrl(url);
	}

	@Override
	public void crawl() throws IOException {
		System.out.println("Fetching " + this.getBaseUrl() + "...");
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        HtmlPage page = client.getPage(this.getBaseUrl());
        List<HtmlElement> itemList = page.getByXPath("//a[@data-click-id='body']");
        for (HtmlElement item : itemList) {
//        	System.out.println(item);
//        	URL nextUrl = page.getFullyQualifiedUrl(((HtmlAnchor) item).getAttribute("href"));
        	System.out.println(item.getTextContent());
        	HtmlPage nextPage = ((HtmlAnchor) item).click();
        	List<HtmlElement> nextPageList = nextPage.getByXPath("//div[@data-click-id='text']//p");
        	String postText = "";
        	for (HtmlElement text : nextPageList) {
        		postText += text.getTextContent() + "\n";
        	}
        	System.out.println(postText);
        }
	}

}
