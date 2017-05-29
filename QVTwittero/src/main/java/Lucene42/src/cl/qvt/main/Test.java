package Lucene42.src.cl.qvt.main;

import Lucene42.src.cl.qvt.nlp.NLPTools;
import Lucene42.src.cl.qvt.searcher.TweetSearcher;
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
            TwitterConnection conexion=new TwitterConnection();
            long id=Long.parseLong("867184045872095232");
            System.out.println(conexion.getMencionesbyID(id));         
            
        }
}

