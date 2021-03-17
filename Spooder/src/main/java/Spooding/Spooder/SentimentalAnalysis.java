package Spooding.Spooder;

//Stanford NLP libraries
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;

import java.util.*;

/**
 * Sentimental Analysis Class using NLP
 * Exports data to MongoDB database
 */
public class SentimentalAnalysis extends MongoConnect {

	/* Start of Stanford NLP Variables for environment setup */
	Properties pipelineProps = new Properties();
	Properties tokenizerProps = new Properties();
	/* Start of Stanford NLP Variables for environment setup */

	SentimentData sentimentData = new SentimentData(); // SentimentData instance

	int sentimentCounter = sentimentData.getDataCount(); // Sentiment counter variable
	int positiveCounter = sentimentData.getPositiveCounter(); // Positive counter variable
	int negativeCounter = sentimentData.getNegativeCounter(); // Negative counter variable
	int neutralCounter = sentimentData.getNeutralCounter(); // Neutral counter variable
	int veryPositiveCounter = sentimentData.getVeryPositiveCounter(); // Very positive counter variable
	int veryNegativeCounter = sentimentData.getVeryNegativeCounter(); // Very negative counter variable

	String positiveString = sentimentData.getPositiveString(); // Positive string variable
	String negativeString = sentimentData.getNegativeString(); // Negative String variable
	String neutralString = sentimentData.getNeutralString(); // Neutral String variable
	String veryPositiveString = sentimentData.getVeryPositiveString(); // Very positive string variable
	String veryNegativeString = sentimentData.getVeryNegativeString(); // Very negative string variable

	ArrayList<SentimentPost> data = new ArrayList<>(); // Data arrayList to store SentimentPost objects

	/**
	 * Constructor
	 */
	public SentimentalAnalysis() {

	}

	/**
	 * Method to analyze posts and generate sentiment value
	 * @param dataList
	 * @param source
	 * @throws InterruptedException
	 */
	public void Analyze(ArrayList<SentimentPost> dataList, String source) 
			throws InterruptedException {

		/* Start of Stanford NLP Library environment set up*/
		pipelineProps.setProperty("annotators", "parse, sentiment");
		pipelineProps.setProperty("parse.binaryTrees", "true");
		pipelineProps.setProperty("enforceRequirements", "false");
		tokenizerProps.setProperty("annotators", "tokenize ssplit");

		StanfordCoreNLP tokenizer = new StanfordCoreNLP(tokenizerProps);
		StanfordCoreNLP pipeline = new StanfordCoreNLP(pipelineProps);
		/* End of Stanford NLP Library environment set up*/

		ArrayList<SentimentPost> tempList = new ArrayList<SentimentPost>(dataList); // Temporary Array list (clone of dataList arrayList) to store posts

		// String testLine = "I love programming";
		System.out.println("Sentimental Analysis from " + source);
		for (int i = 0; i < tempList.size(); i++) { //Loop through array List
			sentimentCounter++;
			String titles = tempList.get(i).getTitle(); // Set string to the post title
			titles = titles.replaceAll("[^A-Za-z0-9]"," "); // Removes sentence ending punctuations
			Annotation annotation = tokenizer.process(titles);
			pipeline.annotate(annotation);

			for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) { // Iterate through sentence
				
				String output = sentence.get(SentimentCoreAnnotations.SentimentClass.class); // String output to set the sentiment string
				System.out.println(dataList.get(i).getTitle()); // Print the relevent post
				System.out.println(sentimentCounter + " " + output); // Print the sentiment respective to the post

				// String compare to increment sentiment counters
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

				// Create sentiment post object containing post title, sentiment and source
				SentimentPost post = new SentimentPost(dataList.get(i).getTitle(), output, dataList.get(i).getSource());
				data.add(post); // Add sentiment object to data array list
			}
		}
		
		if (source.equals("All Sources")) // Only export to MongoDB when analyzing from all sources
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
