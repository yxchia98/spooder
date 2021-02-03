package spooding;

import org.jsoup.Jsoup;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Spood {
	public static void main(String[] args) {
		String baseUrl = "https://news.ycombinator.com";
		WebClient client = new WebClient();
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
		try {
			HtmlPage page = client.getPage(baseUrl);
			System.out.println(page.asXml());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
