package Lucene42.src.cl.qvt.main;

import Lucene42.src.cl.qvt.nlp.NLPTools;
import Lucene42.src.cl.qvt.searcher.TweetSearcher;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import model.Tweet;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.flexible.core.util.StringUtils;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import twitter.TwitterConnection;
import twitter4j.TwitterException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author diego
 */
/**
 * funcion para testear el codigo, si le dan run deberia no tirar nada devuelta 
 * a menos que haya un error, luego deberia de haber creado la carpeta index 
 * con un monton de archivos
 * @author diego
 */
public class Test{
        public static void main(String[] args) throws IOException, ParseException, TwitterException{
            /*TwitterConnection conexion=new TwitterConnection();
            long id=Long.parseLong("867184045872095232");
            System.out.println(conexion.getMencionesbyID(id));         
            */
            LuceneServiceBean sr = new LuceneServiceBean();
            sr.updateIndex();
            TwitterConnection tc = new TwitterConnection();
        List<Tweet> entity = new ArrayList<Tweet>();
        String result = "";
        String keyword="master chef";
        String[] temp = keyword.split(" ");
        TweetSearcher ts = new TweetSearcher();
        Directory index = FSDirectory.open(Paths.get("QVT/Index"));
        IndexReader reader = DirectoryReader.open(index);
        //String input= "master";
        List<String> input = new ArrayList<String>();

        for (int i = 0; i < temp.length; i++) {
            input.add(temp[i]);
        }
        List<Integer> a = ts.searchDocIds(reader, input, "tweet", 5000);
        IndexSearcher searcher = new IndexSearcher(reader);
        int i = 0;
        for (Integer docId : a) {
            Document d = searcher.doc(docId);
            Tweet tweet = new Tweet();
            System.out.println("id:" + d.get("id") + " tweet:" + d.get("tweet") + " user:" + d.get("username") + " time:" + d.get("year") + "-" + d.get("month") + "-" + d.get("day") + " " + d.get("hour") + " RTs:" + d.get("RTcount") + " Likes:" + d.get("LIKEcount") + " score:" + sr.addTweetScore(d));
            tweet.setId_Tweet(Long.valueOf(d.get("id")).longValue());
            tweet.setComment(d.get("tweet"));
            tweet.setUsername(d.get("username"));
            tweet.setDate(Timestamp.valueOf(d.get("year") + "-" + d.get("month") + "-" + d.get("day") + " " + d.get("hour")));
            tweet.setMenciones(Integer.parseInt(d.get("RTcount") + d.get("LIKEcount")));
            //tweet.setMenciones(tc.getMencionesbyID(Long.parseLong(d.get("id"))));
            tweet.setAnalisis(sr.addTweetScore(d));
            i++;
            entity.add(tweet);
            
        }
            System.out.println(entity);
        }
}

