package Spooding.Spooder;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     * @throws IOException 
     */
    @Test
    public void shouldAnswerWithTrue() throws IOException
    {
        assertTrue(true );
    }
    
    public boolean testMongo() {
    	MongoClient mongoClient = MongoClients.create(
				"mongodb+srv://crawlerAdmin:spooder@cluster0.whwla.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
		MongoDatabase database = mongoClient.getDatabase("spooder");
		for (String name : database.listCollectionNames()){
			System.out.println("Collection name: " + name);
		}
		MongoCollection<Document> collection = database.getCollection("spooder");
		System.out.println("Connected to MongoDB");
		mongoClient.close();
		return true;
    }
}
