package Spooding.Spooder;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     * @throws IOException 
     */
    @Test
    public void shouldAnswerWithTrue() throws IOException
    {
    	String url = "https://www.reddit.com/search/?q=gamestop";
        System.out.println("Fetching " + url + "...");
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        HtmlPage page = client.getPage(url);
        List<HtmlElement> itemList = page.getByXPath("//a[@data-click-id='body']");
        for (HtmlElement item : itemList) {
//        	System.out.println(item);
        	System.out.println(item.getTextContent() + ": " + page.getFullyQualifiedUrl(((HtmlAnchor) item).getAttribute("href")));
        }
        assertTrue( true );
    }
}
