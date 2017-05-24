/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;
import Lucene42.src.cl.qvt.main.LuceneServiceBean;
import Lucene42.src.cl.qvt.searcher.TweetSearcher;
import facade.Programa_CategoriaFacade;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Iterator;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


import model.Programa_Categoria;
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
@Path("/TestLucene")
public class TestLuceneService {
    //localhost:8080/QVTwittero/TestLucene/test
    @GET
    @Path("/test2")
    public String juan2() throws IOException, ParseException{
        LuceneServiceBean sr = new LuceneServiceBean();
        sr.updateIndex();
        return "logrado";
    }
    @GET
    @Path("/test")
    public String juan() throws IOException, ParseException{
        TweetSearcher ts = new TweetSearcher();
        Directory index = FSDirectory.open(new File("QVT/Index"));
        IndexReader reader = DirectoryReader.open(index);
        //String input= "master";
        List<String> input=new ArrayList<String>();
        input.add("master");
        input.add("chef");
        List<Integer> a=ts.searchDocIds(reader, input, "tweet", 5000);
        IndexSearcher searcher = new IndexSearcher(reader);
        int i=0;
        String result="";
        for (Iterator iter = a.iterator(); iter.hasNext();) {
            int docId =(Integer) iter.next();
            Document d = searcher.doc(docId);
            result=result+(i + 1) + ". Id:"+d.get("id") +"\t user:"+ d.get("username") + "\t tweet:" + d.get("tweet")+"\n";
            //System.out.println((i + 1) + ". Id:"+d.get("id") +"\t user:"+ d.get("username") + "\t tweet:" + d.get("tweet"));
            //sr.addTweetScore(d);
            i++;
        }
        return result;/*
        try{
            PrintWriter writer = new PrintWriter("QVT/Index/the-file-name.txt", "UTF-8");
            writer.println("The first line");
            writer.println("The second line");
            writer.close();
        } catch (IOException e) {
        // do something
        }
        try{
            PrintWriter writer = new PrintWriter("QVT/the-file-name.txt", "UTF-8");
            writer.println("The first line");
            writer.println("The second line");
            writer.close();
        } catch (IOException e) {
        // do something
        }
        try{
            PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
            writer.println("The first line");
            writer.println("The second line");
            writer.close();
        } catch (IOException e) {
        // do something
        }
        return "hola";*/
    }
}
