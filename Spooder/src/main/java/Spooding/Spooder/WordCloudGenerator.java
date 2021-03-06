package Spooding.Spooder;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.ColorPalette;
/**
 * Word Cloud Generator Class
 */
public class WordCloudGenerator extends MongoConnect {
	private String source;
	private Dimension dimension = new Dimension(600, 600);
	private final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
	/**
	 * Constructor
	 */
	public WordCloudGenerator() {
	}
	/**
	 * Get method to return source
	 * @return source
	 */
	public String getSource() {
		return source;
	}
	/**
	 * Set Method to set source
	 * @param source input string
	 */
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * Get Method to return dimension size
	 * @return dimension size
	 */
	public Dimension getDimension() {
		return dimension;
	}
	/**
	 * Method to set the size of image in pixels
	 * @param width integer value for width in pixels
	 * @param height integer value for height in pixels
	 */
	public void setDimension(int width, int height) {
		this.dimension.width = width;
		this.dimension.height = height;
	}
	/**
	 * Method to create and save the word cloud image onto disk, depending on the source and sentiment type
	 * @throws IOException Throws exception is related to Input and Output operations
	 */
	public void generateCloud() throws IOException { 
		// set word limit
        frequencyAnalyzer.setWordFrequenciesToReturn(250);
        // set word length to be at least 4
        frequencyAnalyzer.setMinWordLength(4);
        // set stop words to be excluded in the word cloud
        frequencyAnalyzer.setStopWords(loadStopWords());
        // initialise a list to store all the string of data from the source
        List<String> dataList = new ArrayList<String>();
        
        // load the sentiment data from mongodb, filter by the source
        List<SentimentPost> SentimentList = importSentimentMongo();
    	for (SentimentPost post: SentimentList) {
        	if (post.getSource().equals(this.source))
    			dataList.add(post.getTitle());
    	}
    	
        // load the data source from mongodb into the analyzer
		final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(dataList);
		// there are 2 collision mode to choose from, PIXEL_PERFECT and RECTANGLE
		final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
		
		// set word cloud configurations
		wordCloud.setPadding(2);
		wordCloud.setBackground(new CircleBackground(300));
		wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
		wordCloud.setFontScalar(new SqrtFontScalar(10, 50));
		wordCloud.build(wordFrequencies);
		wordCloud.writeToFile(String.format("wordCloud/%s_word_cloud.png", source));
	}
	/**
	 * Method to load in stop words from .txt file
	 * @return Array list of stop words
	 * @throws IOException
	 */
	private Collection<String> loadStopWords() throws IOException {
		Collection<String> stopWords;
		stopWords = Files.readAllLines(Paths.get("stopWords.txt"));
		return stopWords;
	}
}
