/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lucene42.src.cl.qvt.nlp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author yolo
 */
public class NLPTools {
    public boolean containsIgnoreCase( String haystack, String needle ) {
    if(needle.equals(""))
        return true;
    if(haystack == null || needle == null || haystack .equals(""))
        return false; 

    Pattern p = Pattern.compile(needle,Pattern.CASE_INSENSITIVE+Pattern.LITERAL);
    Matcher m = p.matcher(haystack);
    return m.find();
    }
    public int score(String lexicon){
        if(containsIgnoreCase(lexicon,"positive"))
                return 1;
        if(containsIgnoreCase(lexicon,"negative"))
                return -1;
        else return 0;
    }
    public int cumulativeScore(String tweet,List<String> lexicon){
        int result=0;
        for (Iterator iter = lexicon.iterator(); iter.hasNext();) {
            String elem = (String) iter.next();
            String[] lex=elem.split(" ");
            if(lex.length>1)
                if(containsIgnoreCase(tweet,lex[0]))
                    result=result+score(lex[1]);
        }
        return result;
    }
    public List<String> getContent(String filePath)
			throws FileNotFoundException, IOException {
		List<String> records = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		try {

			String line = br.readLine();
			while (line != null) {
				records.add(line);
				line = br.readLine();
			}

		} catch (Exception e) {
			// TODO: handle exception
                        System.out.println("ERROR1: " + e.getMessage());
		}
                br.close();
		return records;
    }
    public void deleteDir(String dirdelete){
			File directory = new File(dirdelete);		
			File[] files = directory.listFiles();
                        for (File file : files)
			{
				if(file.isFile()){
			   // Delete each file

			   if (!file.delete())
			   {
			       // Failed to delete file

			       System.out.println("Failed to delete "+file);
			   }
				}
			}	
    }
    public List<String> getDate(Timestamp times){
        String[] timestamp=times.toString().split(" ");
        List<String> resultado=new ArrayList<String>();
        String[] date=timestamp[0].split("-");
        String[] time=timestamp[1].split(":");
        resultado.add(date[0]);
        resultado.add(date[1]);
        resultado.add(date[2]);
        resultado.add(time[0]);
        resultado.add(time[1]);
        resultado.add(time[2]);
        System.out.println(resultado);
        return resultado;
    }
}
