package Spooding.Spooder;

import java.io.IOException;

public abstract class Crawler {

public abstract void crawl() throws IOException, InterruptedException;

public abstract void export();
}
