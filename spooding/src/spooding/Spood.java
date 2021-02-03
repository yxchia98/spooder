package spooding;

import java.util.List;

import org.jsoup.Jsoup;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Spood {
	public static void main(String[] args) {
		String baseUrl = "https://news.ycombinator.com";
		WebClient client = new WebClient();
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
		try {
			HtmlPage page = client.getPage(baseUrl);
//			System.out.println(page.asXml());
			List<HtmlElement> itemList = page.getByXPath("//tr[@class='athing']");
			if(itemList.isEmpty()) {
				System.out.println("No item found");
			}
			else {
				for(HtmlElement htmlItem : itemList) {
					int position = Integer.parseInt(((HtmlElement) htmlItem.getFirstByXPath("./td/span")).asText().replace(".", ""));
					int id = Integer.parseInt(htmlItem.getAttribute("id"));
					String title = ((HtmlElement) htmlItem.getFirstByXPath("./td[not(@valign='top')][@class='title']")).asText();
					String url = ((HtmlAnchor) htmlItem.getFirstByXPath("./td[not(@valign='top')][@class='title']/a")).getHrefAttribute();
//					String author = ((HtmlElement) htmlItem.getFirstByXPath("./following-sibling::tr/td[@class='subtext']/a[\\@class='hnuser']")).asText();
//					int score = Integer.parseInt(((HtmlElement) htmlItem.getFirstByXPath("./following-sibiling::tr/td[@class='subtext']/sp\\an[@class='score']")).asText().replace(" points", ""));
					System.out.println(title);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
