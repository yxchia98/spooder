package Spooding.Spooder;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
/**
 * MongoConnect Class
 * Contains all the methods needed to establish a connection as well as CRUD operations
 * customized to this application
 */
public abstract class MongoConnect {
	/**
	 * Opens a connection to the Mongo collection in the parsed in Mongo Database.
	 * @param database Mongodatabase instance
	 * @param collectionName name of collection to connect to in mongoDB atlas
	 * @return Mongo Collection instance
	 */
	private MongoCollection<Document> mongoConnectCollection(MongoDatabase database, String collectionName){
		boolean exist = false;
		//check if specified collection is in database, if not, create them.
		for (String name : database.listCollectionNames()){
			if (name.equals(collectionName)) {
				exist = true;
			}
		}
		if (!exist) {
			database.createCollection(collectionName);
			System.out.println(collectionName + " collection created.");
		}
		MongoCollection<Document> collection = database.getCollection(collectionName);
		return collection;
	}
	
	/**
	 * Connects to the reddit collection of MongoDB and extracts out individual documents into 
	 * ReddiPost objects, whereby they're stored into a array list.
	 * @return ArrayList of RedditPost objects.
	 */
	protected ArrayList<RedditPost> importRedditMongo(){
        ArrayList<RedditPost> list = new ArrayList<>();
        //connect to mongoDB atlas
        MongoClient mongoClient = MongoClients.create(
                "mongodb+srv://crawlerAdmin:spooder@cluster0.whwla.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
        MongoDatabase database = mongoClient.getDatabase("spooder");
		MongoCollection<Document> collection = mongoConnectCollection(database, "reddit");
        System.out.println("Connected to MongoDB");
        for (Document doc : collection.find()) {
            list.add(new RedditPost(doc.get("Title").toString(), (int) doc.get("Votes")));
        }
        mongoClient.close();
        return list;
    }
	
	/**
	 * Connects to the twitter collection of MongoDB and extracts out individual documents into 
	 * TwitterPost objects, whereby they're stored into a array list.
	 * @return ArrayList of TwitterPost objects.
	 */
	protected ArrayList<TwitterPost> importTwitterMongo(){
        ArrayList<TwitterPost> list = new ArrayList<>();
        //connect to mongoDB atlas
        MongoClient mongoClient = MongoClients.create(
                "mongodb+srv://crawlerAdmin:spooder@cluster0.whwla.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
        MongoDatabase database = mongoClient.getDatabase("spooder");
        //check if specified collection is in database
		MongoCollection<Document> collection = mongoConnectCollection(database, "twitter");
        System.out.println("Connected to MongoDB");
        for (Document doc : collection.find()) {
            list.add(new TwitterPost(doc.get("Title").toString(), doc.get("User").toString()));
        }
        mongoClient.close();
        return list;
    }
	
	/**
	 * Connects to the straitstimes collection of MongoDB and extracts out individual documents into 
	 * STPost objects, whereby they're stored into a array list.
	 * @return ArrayList of STPost objects.
	 */
	protected ArrayList<STPost> importSTMongo(){
        ArrayList<STPost> list = new ArrayList<>();
        //connect to mongoDB atlas
        MongoClient mongoClient = MongoClients.create(
                "mongodb+srv://crawlerAdmin:spooder@cluster0.whwla.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
        MongoDatabase database = mongoClient.getDatabase("spooder");
		MongoCollection<Document> collection = mongoConnectCollection(database, "straitstimes");
        System.out.println("Connected to MongoDB");
        for (Document doc : collection.find()) {
            list.add(new STPost(doc.get("Title").toString()));
        }
        mongoClient.close();
        return list;
    }
	
	/**
	 * Connects to the sentiment collection of MongoDB and extracts out individual documents into
	 * SentimentPost objects, whereby they're stored into a array list
	 * @return ArrayList of SentimentPost objects.
	 */
	protected ArrayList<SentimentPost> importSentimentMongo(){
        ArrayList<SentimentPost> list = new ArrayList<>();
        //connect to mongoDB atlas
        MongoClient mongoClient = MongoClients.create(
                "mongodb+srv://crawlerAdmin:spooder@cluster0.whwla.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
        MongoDatabase database = mongoClient.getDatabase("spooder");
		MongoCollection<Document> collection = mongoConnectCollection(database, "sentiment");
        System.out.println("Connected to MongoDB");
        for (Document doc : collection.find()) {
            list.add(new SentimentPost(doc.get("Title").toString(), doc.get("Sentiment").toString(), doc.getString("Source").toString()));
        }
        mongoClient.close();
        return list;
    }
	/**
	 * Connects to the sentiment collection of MongoDB and extracts out individual documents, based on their source into
	 * SentimentPost objects, whereby they're stored into a array list
	 * @param source source type to be extracted
	 * @return ArrayList of SentimentPost objects according to source type
	 */
	protected ArrayList<SentimentPost> importSentimentMongo(String source){
        ArrayList<SentimentPost> list = new ArrayList<>();
        //connect to mongoDB atlas
        MongoClient mongoClient = MongoClients.create(
                "mongodb+srv://crawlerAdmin:spooder@cluster0.whwla.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
        MongoDatabase database = mongoClient.getDatabase("spooder");
		MongoCollection<Document> collection = mongoConnectCollection(database, "sentiment");
        System.out.println("Connected to MongoDB");
        for (Document doc : collection.find(eq("Source", source))) {
            list.add(new SentimentPost(doc.get("Title").toString(), doc.get("Sentiment").toString(), doc.getString("Source").toString()));
        }
        mongoClient.close();
        return list;
    }
	
	/**
	 * Connects to the reddit collection of MongoDB and exports the parsed in array list of RedditPost objects
	 * into the MongoDB collection.
	 * @param redditList ArrayList of RedditPost objects to be exported to MongoDB
	 */
	protected void exportRedditMongo(ArrayList<RedditPost> redditList) {
		//connect to mongoDB atlas
		MongoClient mongoClient = MongoClients.create(
				"mongodb+srv://crawlerAdmin:spooder@cluster0.whwla.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
		MongoDatabase database = mongoClient.getDatabase("spooder");
		MongoCollection<Document> collection = mongoConnectCollection(database, "reddit");
		//first clear all documents in collection, to avoid duplications from multiple crawls
		BasicDBObject bdoc = new BasicDBObject();
		collection.deleteMany(bdoc);
		System.out.println("Connected to MongoDB");
		for (RedditPost post : redditList) {
			Document doc = new Document();
			doc.append("Title", post.getTitle());
			doc.append("Votes", post.getVotes());
			collection.insertOne(doc);
		}
		mongoClient.close();
	}
	
	/**
	 * Connects to the twitter collection of MongoDB and exports the parsed in array list of TwitterPost objects
	 * into the MongoDB collection.
	 * @param twitterList ArrayList of TwitterPost objects to be exported to MongoDB
	 */
	protected void exportTwitterMongo(ArrayList<TwitterPost> twitterList) {
		//connect to mongoDB atlas
		MongoClient mongoClient = MongoClients.create(
				"mongodb+srv://crawlerAdmin:spooder@cluster0.whwla.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
		MongoDatabase database = mongoClient.getDatabase("spooder");
		MongoCollection<Document> collection = mongoConnectCollection(database, "twitter");
		//first clear all documents in collection, to avoid duplications from multiple crawls
		BasicDBObject bdoc = new BasicDBObject();
		collection.deleteMany(bdoc);
		System.out.println("Connected to MongoDB");
		for (TwitterPost post : twitterList) {
			Document doc = new Document();
			doc.append("Title", post.getTitle());
			doc.append("User", post.getUser());
			collection.insertOne(doc);
		}
		mongoClient.close();
	}
	
	/**
	 * Connects to the straitstimes collection of MongoDB and exports the parsed in array list of STPost objects
	 * into the MongoDB collection.
	 * @param postArray ArrayList of STPost objects to be exported to MongoDB
	 */
	protected void exportSTMongo(ArrayList<STPost> postArray) {
		//connect to mongoDB atlas
		MongoClient mongoClient = MongoClients.create(
				"mongodb+srv://crawlerAdmin:spooder@cluster0.whwla.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
		MongoDatabase database = mongoClient.getDatabase("spooder");
		MongoCollection<Document> collection = mongoConnectCollection(database, "straitstimes");
		//first clear all documents in collection, to avoid duplications from multiple crawls
		BasicDBObject bdoc = new BasicDBObject();
		collection.deleteMany(bdoc);
		System.out.println("Connected to MongoDB");
		for (STPost post : postArray) {
			Document doc = new Document();
			doc.append("Title", post.getTitle());
			collection.insertOne(doc);
		}
		mongoClient.close();
	}
	
	/**
	 * Connects to the sentiment collection of MongoDB and exports the parsed in array list of SentimentPost objects
	 * into the MongoDB collection.
	 * @param sentimentArray ArrayList of SentimentPost objects to be exported to MongoDB
	 */
	protected void exportSentimentMongo(ArrayList<SentimentPost> sentimentArray) {
		//connect to mongoDB atlas
		MongoClient mongoClient = MongoClients.create(
				"mongodb+srv://crawlerAdmin:spooder@cluster0.whwla.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
		MongoDatabase database = mongoClient.getDatabase("spooder");
		MongoCollection<Document> collection = mongoConnectCollection(database, "sentiment");
		//first clear all documents in collection, to avoid duplications from multiple crawls
		BasicDBObject bdoc = new BasicDBObject();
		collection.deleteMany(bdoc);
		System.out.println("Connected to MongoDB");
		for (SentimentPost sentiment : sentimentArray) {
			Document doc = new Document();
			doc.append("Title", sentiment.getTitle());
			doc.append("Sentiment", sentiment.getSentiment());
			doc.append("Source", sentiment.getSource());
			collection.insertOne(doc);
		}
		mongoClient.close();
	}
	
	
}
