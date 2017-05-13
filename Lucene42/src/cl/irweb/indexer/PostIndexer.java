package cl.irweb.indexer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;



import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import cl.irweb.structure.PostCompany;





public class PostIndexer {
	
	
		IndexWriter indexWriter;
		
		public PostIndexer(String directorio) throws IOException{
			File indexDir = new File(directorio);
			
			Map<String,Analyzer> analyzerPerField = new HashMap<String,Analyzer>();
			
			/* Aca se debe poner el analyzer particular para cada campo.
			 * Si no se incluye, ser� analizado con el analyzer incluido 
			 * en el constructor de  PerFieldAnalyzerWrapper
			 */
			
			analyzerPerField.put("company", new EnglishAnalyzer(Version.LUCENE_42));
			analyzerPerField.put("comment", new EnglishAnalyzer(Version.LUCENE_42));
			
			PerFieldAnalyzerWrapper analyzer = new PerFieldAnalyzerWrapper(new EnglishAnalyzer(Version.LUCENE_42), analyzerPerField);
			
			
			deleteIndex(directorio);
	        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_42, analyzer);
			indexWriter = new IndexWriter(FSDirectory.open(indexDir), config);
		}
		
		public void fillIndexPostsCompany(Vector posts) throws IOException{
			for(Iterator iter = posts.iterator(); iter.hasNext();){
	            PostCompany postadd = (PostCompany) iter.next();
	            addDocumentPostCompany(postadd);
	        }
			
		}
		
private void addDocumentPostCompany(PostCompany postadd) throws CorruptIndexException, IOException {
			
			Document postDocument  = new Document();
			
			postDocument.add(new StringField("company", postadd.company, Field.Store.YES));			
			postDocument.add(new StringField("autorRating", postadd.autorRating, Field.Store.YES));					
			postDocument.add(new StringField("reviewNumber", postadd.reviewNumber, Field.Store.YES));
			postDocument.add(new StringField("date", postadd.date , Field.Store.YES));
			postDocument.add(new StringField("feelings", postadd.feelings , Field.Store.YES));
			postDocument.add(new StringField("overalfeelings", postadd.overalfeelings , Field.Store.YES));
			postDocument.add(new TextField("comments", postadd.comments, Field.Store.YES)); //
			postDocument.add(new StringField("workLifeBalance", postadd.workLifeBalance , Field.Store.YES));
			postDocument.add(new StringField("seniorLeadership", postadd.seniorLeadership , Field.Store.YES));
			postDocument.add(new StringField("cultureAndValues", postadd.cultureAndValues , Field.Store.YES));
			postDocument.add(new StringField("compensationAndBenefits", postadd.compensationAndBenefits , Field.Store.YES));
			postDocument.add(new StringField("careerOpportunities", postadd.careerOpportunities , Field.Store.YES));
			
			/* postDocument.add(new StringField("comments", postadd.comments, Field.Store.YES));
			 * Si el campo comments se incluye al documento como StringField, no hallar� coincidencias en la busqueda.
			 * Por eso se utiliza TextField.
			 * 
			 * REVISAR OTROS Field (para campos numericos)
			 */

			indexWriter.addDocument(postDocument);
			// TODO Auto-generated method stub
			
			
		}
		
		public void closeIndex() throws IOException{
			//indexWriter.optimize();//deprecated
			indexWriter.close();	
		}
		
		
		public void deleteIndex(String dirdelete){
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
}


