/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import twitter.TwitterConnection;
import Lucene42.src.cl.qvt.main.LuceneServiceBean;
import Lucene42.src.cl.qvt.searcher.TweetSearcher;
import facade.KeywordFacade;
import facade.TweetFacade;
import facade.Tweet_KeywordFacade;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import model.Keyword;
import model.Programa;
import model.Tweet;
import model.Tweet_Keyword;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import twitter4j.TwitterException;

/**
 *
 * @author yolo
 */
@Path("/tweets")
public class TweetService {

    @EJB
    TweetFacade TweetFacadeEJB;

    @EJB
    Tweet_KeywordFacade Tweet_KeywordFacadeEJB;

    @EJB
    KeywordFacade keywordFacadeEJB;

    Logger logger = Logger.getLogger(TweetService.class.getName());

    @GET
    @Produces({"application/xml", "application/json"})
    public List<Tweet> findAll() {
        return TweetFacadeEJB.findAll();
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Tweet find(@PathParam("id") Integer id) {
        return TweetFacadeEJB.find(id);
    }

    @GET
    @Path("/luceneupdate")
    public String index() throws IOException, ParseException {
        LuceneServiceBean sr = new LuceneServiceBean();
        if(sr.updateIndex())return "logrado";
        return "fracaso";
    }

    @GET
    @Path("{keywords}")
    public String search(@PathParam("keywords") String keyword) throws IOException, ParseException {
        String result = "";
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
            result = result + (i + 1) + ". Id:" + d.get("id") + "\t user:" + d.get("username") + "\t tweet:" + d.get("tweet") + "\n";
            //System.out.println((i + 1) + ". Id:"+d.get("id") +"\t user:"+ d.get("username") + "\t tweet:" + d.get("tweet"));
            //sr.addTweetScore(d);
            i++;
        }
        return result;
    }

    private List<Tweet> create(String keyword) throws IOException, ParseException, TwitterException {
        TwitterConnection tc = new TwitterConnection();
        LuceneServiceBean sr = new LuceneServiceBean();
        List<Tweet> entity = new ArrayList<Tweet>();
        String result = "";
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
            //System.out.println("id:" + d.get("id") + " tweet:" + d.get("tweet") + " user:" + d.get("username") + " time:" + d.get("year") + "-" + d.get("month") + "-" + d.get("day") + " " + d.get("hour") + " RTs:" + d.get("RTcount") + " Likes:" + d.get("LIKEcount") + " score:" + sr.addTweetScore(d));
            tweet.setId_Tweet(Long.valueOf(d.get("id")).longValue());
            tweet.setComment(d.get("tweet"));
            tweet.setUsername(d.get("username"));
            tweet.setDate(Timestamp.valueOf(d.get("year") + "-" + d.get("month") + "-" + d.get("day") + " " + d.get("hour")));
            tweet.setMenciones(Integer.parseInt(d.get("RTcount") + d.get("LIKEcount")));
            //tweet.setMenciones(tc.getMencionesbyID(Long.parseLong(d.get("id"))));
            tweet.setAnalisis(sr.addTweetScore(d));
            i++;
            Tweet found =TweetFacadeEJB.find(tweet.getId_Tweet());
            try{
                if(found.getId_Tweet()==tweet.getId_Tweet()){
                    //System.out.println("ya existe");          
                }
                else {
                    try {
                        TweetFacadeEJB.create(tweet);
                        entity.add(tweet);
                    } catch (EJBException e) {
                }
                }
            }catch (NullPointerException error){
                try {
                        TweetFacadeEJB.create(tweet);
                        entity.add(tweet);
                } catch (EJBException e) {
                }
            }
        }
        return entity;
    }

    @GET
    @Path("/store")
    public String test() throws IOException, ParseException, TwitterException {
        List<Keyword> keywords = keywordFacadeEJB.findAll();
        if (keywords.isEmpty()) {
            return "error";
        }
        for (Keyword keyword : keywords) {
            String word = keyword.getKeyword();
            List<Tweet> tweets = create(word);
            for (Tweet tweet : tweets) {
                if (Tweet_KeywordFacadeEJB.findRepeated(keyword.getKeywordId(), tweet.getId_Tweet()).isEmpty()) {
                    Tweet_Keyword tweet_keyword = new Tweet_Keyword();
                    tweet_keyword.setKeyword_id(keyword.getKeywordId());
                    tweet_keyword.setTweet_id(tweet.getId_Tweet());
                    try {
                        Tweet_KeywordFacadeEJB.create(tweet_keyword);
                    } catch (EJBException e) {
                    }
                }
            }
        }
        return "logrado";
    }

    @GET
    @Path("/update")
    public String update() throws IOException, ParseException, TwitterException {
        List<Tweet> tweets = TweetFacadeEJB.findAll();
        TwitterConnection conexion = new TwitterConnection();
        if (tweets.isEmpty()) {
            return "error";
        }
        for (Tweet tweet : tweets) {
            long id = tweet.getId_Tweet();
            if(tweet.getMenciones()==0){
                try {
                    tweet.setMenciones(conexion.getMencionesbyID(id));
                    try {
                        TweetFacadeEJB.edit(tweet);
                    } catch (EJBException e) {
                    }
                } catch (TwitterException te) {
                    tweet.setMenciones(1);
                    try {
                        TweetFacadeEJB.edit(tweet);
                    } catch (EJBException e) {
                    }
                }
            }

        }
        return "logrado";
    }
    @GET
    @Path("/update2")
    public String update2() throws IOException, ParseException, TwitterException {
        List<Tweet> tweets = TweetFacadeEJB.findAll();
        TwitterConnection conexion = new TwitterConnection();
        if (tweets.isEmpty()) {
            return "error";
        }
        for (Tweet tweet : tweets) {
            long id = tweet.getId_Tweet();
            if(tweet.getMenciones()==1){
                try {
                    tweet.setMenciones(conexion.getMencionesbyID(id));
                    try {
                        TweetFacadeEJB.edit(tweet);
                    } catch (EJBException e) {
                    }
                } catch (TwitterException te) {
                    tweet.setMenciones(1);
                    try {
                        TweetFacadeEJB.edit(tweet);
                    } catch (EJBException e) {
                    }
                }
            }

        }
        return "logrado";
    }

    @GET
    @Path("/mongoexport")
    public String mongoExport() throws IOException {
        String command = "mongoexport -u root -p root --authenticationDatabase admin "
                + "-d test -c myTestCollection -o QVT/exp.out";
        try {
            Process p = Runtime.getRuntime().exec(command);
        } catch (IOException ioe) {
            return "Fallo en la exportación";
        }
        return "Logrado";
    }
}
