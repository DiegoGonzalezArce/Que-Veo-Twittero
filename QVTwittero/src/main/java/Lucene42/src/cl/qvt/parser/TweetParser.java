package Lucene42.src.cl.qvt.parser;


import Lucene42.src.cl.qvt.nlp.NLPTools;
import Lucene42.src.cl.qvt.structure.Tweets;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
 * clase que utiliza expresiones regulares para poder identificar los elementos 
 * del texto que contiene la base de datos de mongo, para luego transformarlos 
 * en elementos de la clase tweet
 */
public class TweetParser {
    public Vector parsearArchivoOut(String archivoDat) {
		try {			
			Vector vectorPosts = new Vector();
                        NLPTools tool=new NLPTools();
			List<String> list = tool.getContent(archivoDat);
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				String elem = (String) iter.next();
                                String str="\\{\"_id\":\\{\"\\$oid\":\"(.*)\"\\},\"id\":\\{\"\\$numberLong\":\"(.*)\"\\},\"tweet\":\"(.*)\",\"username\":\"(.*)\",\"day\":\"(.*)\",\"month\":\"(.*)\",\"anio\":\"(.*)\",\"hour\":\"(.*)\",\"RTcount\":([0-9]*),\"LIKEcount\":([0-9]*),\"Menciones\":([0-9]*),\"Latitud\":(.*),\"Longitud\":(.*)\\}";
                                Pattern p = Pattern.compile(str);
                                Matcher m = p.matcher(elem);
                                if(m.find()){
                                    String _id = m.group(1).toString();
                                    String id = m.group(2).toString();
                                    String tweet = m.group(3).toString();
                                    String username = m.group(4).toString();
                                    String day = m.group(5).toString();
                                    String mouth = m.group(6).toString();
                                    String anio = m.group(7).toString();
                                    String hour = m.group(8).toString();
                                    String RTcount = m.group(9).toString();
                                    String LIKEcount = m.group(10).toString();
                                    Tweets tweets = new Tweets(_id,id,tweet,username,day,mouth,anio,hour,RTcount,LIKEcount); 
                                    vectorPosts.add(tweets);
                                }
			}
			return vectorPosts;
		} catch (Exception es) {
			
		}
		return null;
    } 
}
