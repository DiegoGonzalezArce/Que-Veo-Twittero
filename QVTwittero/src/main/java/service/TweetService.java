/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Lucene42.src.cl.qvt.main.LuceneServiceBean;
import Lucene42.src.cl.qvt.searcher.TweetSearcher;
import facade.TweetFacade;
import java.io.File;
import java.io.IOException;
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
import model.Programa;
import model.Tweet;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 *
 * @author yolo
 */

@Path("/tweets")
public class TweetService {
    @EJB 
    TweetFacade TweetFacadeEJB;

    Logger logger = Logger.getLogger(TweetService.class.getName());
	
    @GET
    @Produces({"application/xml", "application/json"})
    public List<Tweet> findAll(){
    	return TweetFacadeEJB.findAll();
    }

	
    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Tweet find(@PathParam("id") Integer id) {
        return TweetFacadeEJB.find(id);
    }
    @GET
    @Path("/test2")
    public String index() throws IOException, ParseException{
        LuceneServiceBean sr = new LuceneServiceBean();
        sr.updateIndex();
        return "logrado";
    }
    
    @GET
    @Path("{keywords}")
    public String search(@PathParam("keywords") String keyword) throws IOException, ParseException{
        String result="";
        String[] temp=keyword.split(" ");
        TweetSearcher ts = new TweetSearcher();
        Directory index = FSDirectory.open(new File("QVT/Index"));
        IndexReader reader = DirectoryReader.open(index);
        //String input= "master";
        List<String> input=new ArrayList<String>();
        
        for(int i=0;i<temp.length;i++){
            input.add(temp[i]);
        }
        List<Integer> a=ts.searchDocIds(reader, input, "tweet", 5000);
        IndexSearcher searcher = new IndexSearcher(reader);
        int i=0;
        for (Iterator iter = a.iterator(); iter.hasNext();) {
            int docId =(Integer) iter.next();
            Document d = searcher.doc(docId);
            result=result+(i + 1) + ". Id:"+d.get("id") +"\t user:"+ d.get("username") + "\t tweet:" + d.get("tweet")+"\n";
            //System.out.println((i + 1) + ". Id:"+d.get("id") +"\t user:"+ d.get("username") + "\t tweet:" + d.get("tweet"));
            //sr.addTweetScore(d);
            i++;
        }
        return result;
    }

    @GET
    @Path("{keywords}/test")
    @Produces({"application/xml", "application/json"})
    public List<Tweet> create(@PathParam("keywords") String keyword) throws IOException, ParseException {
        LuceneServiceBean sr = new LuceneServiceBean();
        List<Tweet> entity=new ArrayList<Tweet>();
        String result="";
        String[] temp=keyword.split(" ");
        TweetSearcher ts = new TweetSearcher();
        Directory index = FSDirectory.open(new File("QVT/Index"));
        IndexReader reader = DirectoryReader.open(index);
        //String input= "master";
        List<String> input=new ArrayList<String>();
        
        for(int i=0;i<temp.length;i++){
            input.add(temp[i]);
        }
        List<Integer> a=ts.searchDocIds(reader, input, "tweet", 5000);
        IndexSearcher searcher = new IndexSearcher(reader);
        int i=0;
        for (Iterator iter = a.iterator(); iter.hasNext();) {            
            int docId =(Integer) iter.next();
            Document d = searcher.doc(docId);
            Tweet tweet=new Tweet();
            System.out.println("id:"+d.get("id")+" tweet:"+d.get("tweet")+" user:"+d.get("username")+" time:"+d.get("year")+"-"+d.get("month")+"-"+d.get("day")+" "+d.get("hour")+" RTs:"+d.get("RTcount")+" Likes:"+d.get("LIKEcount")+" score:"+sr.addTweetScore(d));
            tweet.setId_Tweet(Long.valueOf(d.get("id")).longValue());
            tweet.setComment(d.get("tweet"));
            tweet.setUsername(d.get("username"));
            tweet.setDate(Timestamp.valueOf(d.get("year")+"-"+d.get("month")+"-"+d.get("day")+" "+d.get("hour")));
            tweet.setMenciones(Integer.parseInt(d.get("RTcount")+d.get("LIKEcount")));
            tweet.setAnalisis(sr.addTweetScore(d));
            i++;
            try{
                TweetFacadeEJB.create(tweet);
                entity.add(tweet);
            }catch (EJBException e) {
                
		// TODO Auto-generated catch block
		e.printStackTrace();
            }
        }
        return entity;
    }

}
