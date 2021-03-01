package Spooding.Spooder;

import java.io.IOException;

import twitter4j.TwitterException;

public abstract class Crawler implements DataMovement{

public abstract void crawl() throws IOException, InterruptedException, TwitterException;
}
