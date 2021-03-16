package Spooding.Spooder;

//Stanford NLP libraries
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;

//OpenCSV reader library
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Sentimental Analysis Class using NLP
 */
public class SentimentalAnalysis extends MongoConnect {
	String[] nextRecord;
	String[] tempRecord;

	Properties pipelineProps = new Properties();
	Properties tokenizerProps = new Properties();

	SentimentData sentimentData = new SentimentData();

	int sentimentCounter = sentimentData.getDataCount();
	int positiveCounter = sentimentData.getPositiveCounter();
	int negativeCounter = sentimentData.getNegativeCounter();
	int neutralCounter = sentimentData.getNeutralCounter();
	int veryPositiveCounter = sentimentData.getVeryPositiveCounter();
	int veryNegativeCounter = sentimentData.getVeryNegativeCounter();

	String positiveString = sentimentData.getPositiveString();
	String negativeString = sentimentData.getNegativeString();
	String neutralString = sentimentData.getNeutralString();
	String veryPositiveString = sentimentData.getVeryPositiveString();
	String veryNegativeString = sentimentData.getVeryNegativeString();

	SentimentPost post = new SentimentPost();
	ArrayList<SentimentPost> data = new ArrayList<>();

	/**
	 * Constructor
	 */
	public SentimentalAnalysis() {

	}

	/**
	 * Method to perform Sentiment Analysis
	 * 
	 * @throws IOException            Throws exception is related to Input and
	 *                                Output operations
	 * @throws CsvValidationException Exception thrown by a LineValidator or
	 *                                LineValidatorAggregator when a single line is
	 *                                invalid.
	 */
	public void Analyze(ArrayList<SentimentPost> dataList, String source)
			throws IOException, CsvValidationException, InterruptedException {


		pipelineProps.setProperty("annotators", "parse, sentiment");
		pipelineProps.setProperty("parse.binaryTrees", "true");
		pipelineProps.setProperty("enforceRequirements", "false");
		tokenizerProps.setProperty("annotators", "tokenize ssplit");

		StanfordCoreNLP tokenizer = new StanfordCoreNLP(tokenizerProps);
		StanfordCoreNLP pipeline = new StanfordCoreNLP(pipelineProps);

		ArrayList<SentimentPost> tempList = new ArrayList<SentimentPost>(dataList);
		
		String dataSource = post.getSource();

		// String testLine = "I love programming";
		System.out.println("Sentimental Analysis from " + source);
		for (int i = 0; i < tempList.size(); i++) {
			sentimentCounter++;
			String titles = tempList.get(i).getTitle();
			titles = titles.replaceAll("[^A-Za-z0-9]"," ");
			Annotation annotation = tokenizer.process(titles);
			pipeline.annotate(annotation);

			for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
				
				String output = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
				System.out.println(dataList.get(i).getTitle());
				System.out.println(sentimentCounter + " " + output);

				if (output.equals(positiveString) == true)
					positiveCounter += 1;
				if (output.equals(negativeString) == true)
					negativeCounter += 1;
				if (output.equals(neutralString) == true)
					neutralCounter += 1;
				if (output.equals(veryPositiveString) == true)
					veryPositiveCounter += 1;
				if (output.equals(veryNegativeString) == true)
					veryNegativeCounter += 1;

				SentimentPost post = new SentimentPost(dataList.get(i).getTitle(), output, dataList.get(i).getSource());
				data.add(post);
			}
		}
		
		if (source.equals("All Sources"))
			exportSentimentMongo(data);
		Thread.sleep(1000);
		System.out.println("\nSentiment count from " + source + "\n");
		printSentimentCount();
	}

	/**
	 * Method to print out Sentiment Analysis Data
	 */
	public void printSentimentCount() {
		System.out.println("Number of Positive: " + positiveCounter);
		System.out.println("Number of Negative: " + negativeCounter);
		System.out.println("Number of Neutral: " + neutralCounter);
	}

}
