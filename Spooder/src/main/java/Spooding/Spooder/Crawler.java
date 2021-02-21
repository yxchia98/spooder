package Spooding.Spooder;

import java.io.IOException;

public abstract class Crawler {
private String baseUrl;

public String getBaseUrl() {
	return baseUrl;
}



public void setBaseUrl(String baseUrl) {
	this.baseUrl = baseUrl;
}


public abstract void crawl() throws IOException, InterruptedException;
}
