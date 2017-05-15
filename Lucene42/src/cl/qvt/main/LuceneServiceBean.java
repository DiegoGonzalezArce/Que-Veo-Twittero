package cl.qvt.main;


import cl.qvt.parser.TweetParser;
import cl.qvt.indexer.TweetIndexer;
import java.io.IOException;
import java.util.List;
import java.util.Vector;
import javax.faces.component.UICommand;
import javax.faces.component.UIForm;

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
    final static String dirPostsIndex = "src/index";
    private String content = "";
    private List<String> searchResults = null;
    private boolean initial = true;
    final static String ARCHIVO_OUT = "src/exp.out";
    
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
}
