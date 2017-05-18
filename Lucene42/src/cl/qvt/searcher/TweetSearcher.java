/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.qvt.searcher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 *
 * @author yolo
 */
public class TweetSearcher {
    /**
     * 
     * @param reader
     * @param Term es la query
     * @param label es la etiqueta que es basicamente en que field del tweet se 
     * hara la consulta (usuario, tweet, id,etc)
     * @param maxHits cantidad maxima de resultados
     * @return una lista de documentos cuya unica utilidad es extraer su id, 
     * para luego extraer mediante una busqueda, sus atributos
     * @throws IOException
     * @throws ParseException 
     */
    public List<ScoreDoc> search(IndexReader reader,String Term,String label,int maxHits) throws IOException, ParseException{
        String[] args = null;
        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_42);
        Query q = new QueryParser(Version.LUCENE_42, label, analyzer).parse(Term);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopScoreDocCollector collector = TopScoreDocCollector.create(maxHits, true);
        searcher.search(q, collector);
        List<ScoreDoc> result=new ArrayList<ScoreDoc>();
        ScoreDoc[] hits = collector.topDocs().scoreDocs;
        for(int i=0;i<hits.length;++i) {
            result.add(hits[i]);
        }
        /*int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get("username") + "\t" + d.get("tweet"));
        */
    
    // reader can only be closed when there
    // is no need to access the documents any more.
        return result;
    }
    /**
     * idem al anterior
     * @param reader
     * @param Term
     * @param label
     * @param maxHits
     * @return devuelve una lista de int que representa los id de los documentos para
     * facilitar su busqueda
     * @throws IOException
     * @throws ParseException 
     */
    public List<Integer> searchDocIds(IndexReader reader,String Term,String label,int maxHits) throws IOException, ParseException{
        List<ScoreDoc> tweets=search(reader,Term,label,maxHits);
        List<Integer> result=new ArrayList<Integer>();
        for (Iterator iter = tweets.iterator(); iter.hasNext();){
            ScoreDoc elem = (ScoreDoc) iter.next();
            result.add(elem.doc);
        }
        return result;
    }
    /**
     * idem al anterior
     * @param reader
     * @param Term
     * @param label
     * @param maxHits
     * @return devuelve una lista de int que representan los id de los tweets para
     * facilitar la consulta por mongoDB
     * @throws IOException
     * @throws ParseException 
     */
    public List<String> searchTweetIds(IndexReader reader,String Term,String label,int maxHits) throws IOException, ParseException{
        List<Integer> docs=searchDocIds(reader,Term,label,maxHits);
        IndexSearcher searcher = new IndexSearcher(reader);
        List<String> result=new ArrayList<String>();
        int i=0;
        for (Iterator iter = docs.iterator(); iter.hasNext();) {
            int docId =(Integer) iter.next();
            Document d = searcher.doc(docId);
            result.add(d.get("id"));
            i++;
        }
        return result;
    }
    
}
