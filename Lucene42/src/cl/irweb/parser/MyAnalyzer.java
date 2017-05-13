package cl.irweb.parser;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.LowerCaseTokenizer;

import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

import org.apache.lucene.util.Version;


public class MyAnalyzer extends Analyzer {

	  private Version matchVersion;
	  
	  public MyAnalyzer(Version matchVersion) {
	    this.matchVersion = matchVersion;
	  }

	
	  @Override
	  protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
				  
		TokenStream myTokenStream = new SnowballFilter( new LowerCaseTokenizer(matchVersion,reader),"English");  
	    return new TokenStreamComponents(new StandardTokenizer(matchVersion, reader),myTokenStream);
	    //Falta incluir un stopwords
	  } 
	  
	 
	}
