package Lucene42.src.cl.qvt.main;


import Lucene42.src.cl.qvt.parser.TweetParser;
import Lucene42.src.cl.qvt.indexer.TweetIndexer;
import Lucene42.src.cl.qvt.nlp.NLPTools;
import java.io.IOException;
import java.util.List;
import java.util.Vector;
import javax.faces.component.UICommand;
import javax.faces.component.UIForm;
import org.apache.lucene.document.Document;

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
 * service bean de lucene
 */
public class LuceneServiceBean {
    
	  // JavaServerFaces related variables
    private UIForm form;
    private UIForm tableForm;
    private UICommand addCommand;
    final static String dirPostsIndex = "QVT/Index";
    private String content = "";
    private List<String> searchResults = null;
    private boolean initial = true;
    final static String ARCHIVO_OUT = "QVT/exp.out";
    final static String ARCHIVO_CSV = "QVT/SpanishLexicon.csv";
    
    public boolean updateIndex() {
		TweetParser tweetParser = new TweetParser();
		boolean done=false;
		try {
			TweetIndexer tweetIndex = new TweetIndexer(dirPostsIndex);
			Vector tweetsOut = tweetParser.parsearArchivoOut(ARCHIVO_OUT);					
			tweetIndex.fillIndexTweets(tweetsOut);
			tweetIndex.closeIndex();
			done=true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return done;
    }
    public void addTweetScore(Document doc) throws IOException{
        NLPTools tool=new NLPTools();
        List<String> lexicon=tool.getContent(ARCHIVO_CSV);
        String content=doc.get("tweet");
        int score=tool.cumulativeScore(content, lexicon);
        //conectar con la bd
        //agregar score al tweet
        System.out.println("el tweet:"+content+"\t posee el siguiente puntaje:"+score);
        
    }
    
}
