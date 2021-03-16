package Spooding.Spooder;

//Stanford NLP libraries
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
//OpenCSV reader library
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

import org.bson.Document;

/**
 * Sentimental Analysis Class using NLP
 */
public class SentimentalAnalysis {
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
	public void Analyze(String CSVFileName, String dataset)
			throws IOException, CsvValidationException, InterruptedException {

		FileReader reader = new FileReader(CSVFileName, StandardCharsets.UTF_8);
		@SuppressWarnings("resource")
		CSVReader csvReader = new CSVReader(reader);

		pipelineProps.setProperty("annotators", "parse, sentiment");
		pipelineProps.setProperty("parse.binaryTrees", "true");
		pipelineProps.setProperty("enforceRequirements", "false");
		tokenizerProps.setProperty("annotators", "tokenize ssplit");

		StanfordCoreNLP tokenizer = new StanfordCoreNLP(tokenizerProps);
		StanfordCoreNLP pipeline = new StanfordCoreNLP(pipelineProps);

		// String testLine = "I love programming";
		System.out.println("Sentimental Analysis from " + dataset);
		while ((nextRecord = csvReader.readNext()) != null) {
			sentimentCounter++;
			tempRecord = nextRecord.clone();
			tempRecord[0] = tempRecord[0].replaceAll("[^A-Za-z0-9]"," ");
			Annotation annotation = tokenizer.process(tempRecord[0]);
			pipeline.annotate(annotation);

			for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
				
				String output = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
				System.out.println(nextRecord[0]);
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

				SentimentPost post = new SentimentPost(nextRecord[0], output, dataset);
				data.add(post);
			}
		}

		Thread.sleep(1000);
		System.out.println("\nSentiment count from " + CSVFileName + "\n");
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
