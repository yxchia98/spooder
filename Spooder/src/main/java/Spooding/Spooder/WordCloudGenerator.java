package Spooding.Spooder;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.ColorPalette;

public class WordCloudGenerator {
	private String source;
	private Dimension dimension = new Dimension(600, 600);
	
	public WordCloudGenerator(String source) {
		this.source = source;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Dimension getDimension() {
		return dimension;
	}
	
	public void setDimension(int width, int height) {
		this.dimension.width = width;
		this.dimension.height = height;
	}

	public void generateCloud() throws IOException { 
		final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
		// set word limit
        frequencyAnalyzer.setWordFrequenciesToReturn(250);
        // set word length to be at least 4
        frequencyAnalyzer.setMinWordLength(4);
        // set stop words to be excluded in the word cloud
        frequencyAnalyzer.setStopWords(loadStopWords());
        
        // load the source csv file into the analyzer
		final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(source);
		// there are 2 collision mode to choose from, PIXEL_PERFECT and RECTANGLE
		final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
		
		// set other configurations
		// set word to word spacing
		wordCloud.setPadding(2);
		wordCloud.setBackground(new CircleBackground(300));
		wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
		wordCloud.setFontScalar(new SqrtFontScalar(10, 50));
		wordCloud.build(wordFrequencies);
		wordCloud.writeToFile("wordCloud/twitter_word_cloud.png");
	}

	private Collection<String> loadStopWords() throws IOException {
		Collection<String> stopWords;
		stopWords = Files.readAllLines(Paths.get("stopWords.txt"));
		return stopWords;
	}
}