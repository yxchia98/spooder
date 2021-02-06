package Spooding.Spooder;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Example program to list links from a URL.
 */
public class App {
    public static void main(String[] args) throws IOException {
        String url = "https://www.reddit.com/search/?q=gamestop";
        print("Fetching %s...", url);

        Document doc = Jsoup.connect(url).maxBodySize(0).get();
//        Elements links = doc.select("a[href]");
//        Elements media = doc.select("[src]");
//        Elements imports = doc.select("link[href]");
        Elements threadLinks = doc.select("a[data-click-id='body']");
        
        for (Element thread : threadLinks) {
        	System.out.println(thread.text() + ": " + thread.attr("abs:href"));
        }

//        print("\nMedia: (%d)", media.size());
//        for (Element src : media) {
//            if (src.normalName().equals("img"))
//                print(" * %s: <%s> %sx%s (%s)",
//                        src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
//                        trim(src.attr("alt"), 20));
//            else
//                print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
//        }

//        print("\nImports: (%d)", imports.size());
//        for (Element link : imports) {
//            print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"), link.attr("rel"));
//        }

//        print("\nLinks: (%d)", links.size());
//        for (Element link : links) {
//            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
//        }
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
}
