package cl.citiaps.twitter.streaming;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.MongoCredential;
import com.mongodb.MongoClientOptions;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import java.util.Arrays;
import com.mongodb.Block;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.lang.Long;

import org.apache.commons.io.IOUtils;

import twitter4j.FilterQuery;
import twitter4j.IDs;
import twitter4j.OEmbed;
import twitter4j.OEmbedRequest;
import twitter4j.ResponseList;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.UploadedMedia;



public class TwitterStreaming{

	private final TwitterStream twitterStream;
	private Set<String> keywords;
        
	private TwitterStreaming() {
		this.twitterStream = new TwitterStreamFactory().getInstance();
		this.keywords = new HashSet<>();
		loadKeywords();
	}

	private void loadKeywords() {
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			keywords.addAll(IOUtils.readLines(classLoader.getResourceAsStream("words.dat"), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void init() {


		StatusListener listener = new StatusListener() {

			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
			}

			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
			}

			public void onScrubGeo(long userId, long upToStatusId) {
				System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
			}

			public void onException(Exception ex) {
				ex.printStackTrace();
			}

			@Override
			public void onStallWarning(StallWarning arg0) {

			}

			@Override
			public void onStatus(Status status) {

				//Identificacion de mongoDB (Si se siguio el tutorial, estos deberian ser las identificaciones.)
				
                                //char[] password = {'a','d','m','i','n','1','2','3'};
				char[] password = {'r','o','o','t'};
				
				if(status.getLang().equals("es")){
                                    
                                        //Obtencion de la fecha del tweet
                                        Calendar cale = Calendar.getInstance();
                                        String  anio = Integer.toString(cale.get(Calendar.YEAR));
                                        String  mes = Integer.toString(cale.get(Calendar.MONTH));
                                        String  dia = Integer.toString(cale.get(Calendar.DAY_OF_MONTH));
                                        //Se guarda como solo un string.
                                        String fecha = dia+"/"+mes+"/"+anio;  
                                        
                                        String  hora = Integer.toString(cale.get(Calendar.HOUR_OF_DAY));
                                        int minutoI = cale.get(Calendar.MINUTE);
					int segundoI = cale.get(Calendar.SECOND);
                                        String minuto;
                                        if (minutoI < 10){
                                            minuto = "0" + Integer.toString(minutoI);
                                        }
                                        else{
                                             minuto = Integer.toString(minutoI);
                                        }
					String segundo;
					if (segundoI < 10){
                                            segundo = "0" + Integer.toString(segundoI);
                                        }
                                        else{
                                             segundo = Integer.toString(segundoI);
                                        }
                                   
                            
					System.out.println("\n");
					System.out.println("\n");
					System.out.println(status.getId());
					System.out.println(status.getText());
					System.out.println(status.getUser().getName());
					System.out.println(status.getRetweetCount());
					System.out.println(status.getFavoriteCount());
                                        System.out.println("Fecha: "+dia+"/"+mes+"/"+anio);
                                        System.out.println("Hora: "+hora+":"+minuto+":"+segundo);
					System.out.println("\n");
					System.out.println("\n");				

					//Idenfiticacion
					//MongoCredential credential = MongoCredential.createCredential("admin", "admin", password);
					MongoCredential credential = MongoCredential.createCredential("root", "admin", password);
					MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017), Arrays.asList(credential));

					//Se crea la BD
					MongoDatabase database = mongoClient.getDatabase("test");
					//Crea la colleccion
					MongoCollection<Document> coll = database.getCollection("myTestCollection");
					//database.createCollection("cappedCollection");
					//Crea un documento
					Document doc = new Document("id", status.getId())
									    .append("tweet", status.getText())
									    .append("username", status.getUser().getName())
									    .append("day", dia)
                                                                            .append("month", mes)
                                                                            .append("anio", anio)
                                                                            .append("hour",hora+":"+minuto+":"+segundo)
                                                                            .append("RTcount", status.getRetweetCount())    
									    .append("LIKEcount", status.getFavoriteCount());
                                                                            
					//Lo inserta en la colleccteion MyTestCollection de la BD test.
					coll.insertOne(doc);
					//Cierro el cliente:
					mongoClient.close();
				}
			}
		};
				

				

		FilterQuery fq = new FilterQuery();

		fq.track(keywords.toArray(new String[0]));

		this.twitterStream.addListener(listener);
		this.twitterStream.filter(fq);
	}
	
	public static void main(String[] args) throws TwitterException {
		new TwitterStreaming().init();
	}

        
        

            
        
}
