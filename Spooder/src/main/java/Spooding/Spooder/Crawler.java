package Spooding.Spooder;

import java.io.IOException;

import twitter4j.TwitterException;

public abstract class Crawler {

public abstract void crawl() throws IOException, InterruptedException, TwitterException;

public abstract void export();
}
