package Lucene42.src.cl.qvt.indexer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author diego
 */

import Lucene42.src.cl.qvt.structure.Tweets;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import javax.inject.Singleton;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

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
 * clase java que crea el index para la posterior busqueda
*/
public class TweetIndexer{
        IndexWriter indexWriter;  
        FSDirectory open;
        public TweetIndexer(String directorio) throws IOException{
		Path indexDir = Paths.get(directorio);
                open = FSDirectory.open(indexDir);
		Map<String,Analyzer> analyzerPerField = new HashMap<String,Analyzer>();
		analyzerPerField.put("tweet", new EnglishAnalyzer());	
		PerFieldAnalyzerWrapper analyzer = new PerFieldAnalyzerWrapper(new EnglishAnalyzer(), analyzerPerField);			
	        IndexWriterConfig config = new IndexWriterConfig(analyzer);
		indexWriter = new IndexWriter(open, config);
	}
        public void fillIndexTweets(Vector tweets) throws IOException{
            int i=0;
            for(Iterator iter = tweets.iterator(); iter.hasNext();){
	        Tweets retrievedTweet = (Tweets) iter.next();
                i++;
		indexWriter.addDocument(documentRetrievedTweet(retrievedTweet));       
	    }
            closeIndex();
        }
        
        private Document documentRetrievedTweet(Tweets retrievedTweet) throws CorruptIndexException, IOException {
			Document docRetrievedTweet  = new Document();
			docRetrievedTweet.add(new StringField("_id", retrievedTweet._id, Field.Store.YES));			
			docRetrievedTweet.add(new StringField("id", retrievedTweet.id, Field.Store.YES));					
			docRetrievedTweet.add(new TextField("tweet", retrievedTweet.tweet, Field.Store.YES));
			docRetrievedTweet.add(new StringField("username", retrievedTweet.username , Field.Store.YES));
			docRetrievedTweet.add(new StringField("day", retrievedTweet.day , Field.Store.YES));
			docRetrievedTweet.add(new StringField("month", retrievedTweet.mouth , Field.Store.YES));
			docRetrievedTweet.add(new StringField("year", retrievedTweet.anio , Field.Store.YES));
			docRetrievedTweet.add(new StringField("hour", retrievedTweet.hour , Field.Store.YES));
			docRetrievedTweet.add(new StringField("RTcount", retrievedTweet.RTcount , Field.Store.YES));
			docRetrievedTweet.add(new StringField("LIKEcount", retrievedTweet.LIKEcount , Field.Store.YES));
			
			/* docRetrievedTweet.add(new StringField("comments", retrievedTweet.comments, Field.Store.YES));
			 * Si el campo comments se incluye al documento como StringField, no hallar� coincidencias en la busqueda.
			 * Por eso se utiliza TextField.
			 * 
			 * REVISAR OTROS Field (para campos numericos)
			 */
                        return docRetrievedTweet;
			// TODO Auto-generated method stub
			
			
		}
        public void closeIndex() throws IOException{
			//indexWriter.optimize();//deprecated
                        indexWriter.flush();
                        indexWriter.deleteUnusedFiles();
			indexWriter.close();
                        //System.out.println("cerrado");
		}
}

