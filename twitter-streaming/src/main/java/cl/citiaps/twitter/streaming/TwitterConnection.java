/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.citiaps.twitter.streaming;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.Arrays;
import org.bson.Document;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 *
 * @author nikonegima
 */
public class TwitterConnection {
    
    private Twitter twitter;
    
    public TwitterConnection(){
        this.twitter = new TwitterFactory().getInstance();

    }
    
    public int getRTCountbyID(long id) throws TwitterException{
        Status status = this.twitter.showStatus(id);
        int RTcontador = status.getRetweetCount();
        return RTcontador;
    }
    
    public int getLikeCountbyID(long id) throws TwitterException{
        Status status = this.twitter.showStatus(id);
        int Likecontador = status.getFavoriteCount();
        return Likecontador;
    } 
    
    public ArrayList<Integer> getCountofAllTweets() throws TwitterException{
        //CONEXION
        //char[] password = {'a','d','m','i','n','1','2','3'};
        char[] password = {'r','o','o','t'};
        //MongoCredential credential = MongoCredential.createCredential("admin", "admin", password);
        MongoCredential credential = MongoCredential.createCredential("root", "admin", password);
        MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017), Arrays.asList(credential));
        
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("myTestCollection");
        
        MongoCursor<Document> cursor = collection.find().iterator();
        //FIN DE CONEXION
        
        int RTcount = 0;
        int Likecount = 0;
        
        while(cursor.hasNext()){
            //Guarda el documento
            Document doc = cursor.next();
            //Saca la id
            long id = (long) doc.get("id");
            //Obtiene el status con esa id
            Status status = this.twitter.showStatus(id);
            //Obtiene los contadores actualizadores.
            int RTtemp = status.getFavoriteCount();
            int Liketemp = status.getFavoriteCount();
            //Suma a los contadores globales
            RTcount = RTcount + RTtemp;
            Likecount = Likecount + Liketemp;
            
        }
        ArrayList <Integer> retorno = new ArrayList<>();
        retorno.add(0, RTcount);
        retorno.add(1, Likecount);
        return retorno;
    }
    
    /*
    getCountfromIDs: Recibe las IDs de los tweet de X programa como un arreglo de Longs.
                     Se actualiza la cantidad de ReTweets y de Likes(Favorite) de cada tweet.
                     Se suma en dos contadores "globales", que se guardan en estas cantidades.
                     Se repite con todos los tweets (hasta que se repasen todas las ID's)
                      
    */
    public ArrayList<Integer> getCountfromIDs(ArrayList<Long> IDs) throws TwitterException{
        ArrayList<Integer> retorno = new ArrayList<>();
        int RTcount = 0;
        int Likecount = 0;
        
        for(Long id : IDs){
            //Obtiene el status con esa id
            Status status = this.twitter.showStatus(id);
            //Obtiene los contadores actualizadores.
            int RTtemp = status.getFavoriteCount();
            int Liketemp = status.getFavoriteCount();
            //Suma a los contadores globales
            RTcount = RTcount + RTtemp;
            Likecount = Likecount + Liketemp;
            
        }
        retorno.add(0, RTcount);
        retorno.add(1, Likecount);
        
        return retorno;
    }
}
