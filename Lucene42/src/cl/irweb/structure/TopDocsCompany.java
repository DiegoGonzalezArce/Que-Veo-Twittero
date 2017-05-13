package cl.irweb.structure;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class TopDocsCompany {
	
	public String company;
	ArrayList<ScoreDoc> docsCompany;
	float scoreCompany;
	TopDocs topDoc;
	
	public TopDocsCompany(String string, ScoreDoc doc) {		
		company = string;
		docsCompany = new ArrayList<ScoreDoc>();
		docsCompany.add(doc);		
	}

	public void addDocs(ScoreDoc d) {		
		docsCompany.add(d);
	}

	public void listPost(IndexSearcher indexSearcher) throws IOException {		
		for(int j = 0;j < this.docsCompany.size() ;j++){			
			Document d = indexSearcher.doc(this.docsCompany.get(j).doc);
			System.out.println(d.get("comments"));
		}
	}
	

}
