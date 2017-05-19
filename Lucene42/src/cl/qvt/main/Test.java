package cl.qvt.main;

import cl.qvt.nlp.NLPTools;
import cl.qvt.searcher.TweetSearcher;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.flexible.core.util.StringUtils;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

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
        public static void main(String[] args) throws IOException, ParseException{
            LuceneServiceBean sr = new LuceneServiceBean();
            sr.updateIndex();
            TweetSearcher ts = new TweetSearcher();
            Directory index = FSDirectory.open(new File("src/index"));
            IndexReader reader = DirectoryReader.open(index);
            //String input= "master";
            List<String> input=new ArrayList<String>();
            input.add("master");
            input.add("chef");
            List<Integer> a=ts.searchDocIds(reader, input, "tweet", 5000);
            IndexSearcher searcher = new IndexSearcher(reader);
            int i=0;
            for (Iterator iter = a.iterator(); iter.hasNext();) {
                int docId =(Integer) iter.next();
                Document d = searcher.doc(docId);
                //System.out.println((i + 1) + ". Id:"+d.get("id") +"\t user:"+ d.get("username") + "\t tweet:" + d.get("tweet"));
                sr.addTweetScore(d);
                i++;
            }
            /*List<String> b=ts.searchTweetIds(reader, "master", "tweet", 5000);
            for (Iterator iter = b.iterator(); iter.hasNext();) {
                System.out.println("Id:"+(String) iter.next());
            }
            */
        }
}

