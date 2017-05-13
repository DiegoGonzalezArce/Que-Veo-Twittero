package cl.irweb.searcher;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.en.EnglishAnalyzer;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;

import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import cl.irweb.structure.RankedCompany;

public class PostSearcher {

	public static Integer MAX_RESULTS=15;
	public static Integer MAX_DOCS=15000;
	public static void companySearch(String searchString, String dirPostsIndex)
	throws Exception {
Directory directory = FSDirectory.open(new File(dirPostsIndex));

DirectoryReader reader = DirectoryReader.open(directory);  
IndexSearcher indexSearcher = new IndexSearcher(reader); 
		
//Indicar s�lo campos por los cuales hace busqueda de comentarios
String[] campos = {	"company", 
					"autorRating", 
					"reviewNumber",
					"comments", 
					"date" , 
					"feelings",
					"overalfeelings",
					"workLifeBalance" ,
					"seniorLeadership",
					"cultureAndValues",
					"compensationAndBenefits",
					"careerOpportunities"};


MultiFieldQueryParser queryParser = new MultiFieldQueryParser(	Version.LUCENE_42, 
																campos,																		
																new EnglishAnalyzer(Version.LUCENE_42)); // Se usa el mismo analyzer del indice
																/*
																 * Se puede usar otro, pero los resultados del search son distintos
																 */

String searchStringFinal = searchString.replace("_", " ");
System.out.println("Searching for '" + searchStringFinal
		+ "' using MultiFieldQueryParser");
BooleanClause.Occur[] flags = { BooleanClause.Occur.SHOULD,
								BooleanClause.Occur.SHOULD, 
								BooleanClause.Occur.SHOULD,
								BooleanClause.Occur.SHOULD,
								BooleanClause.Occur.SHOULD,
								BooleanClause.Occur.SHOULD, 
								BooleanClause.Occur.SHOULD,
								BooleanClause.Occur.SHOULD,
								BooleanClause.Occur.SHOULD,
								BooleanClause.Occur.SHOULD, 
								BooleanClause.Occur.SHOULD,
								BooleanClause.Occur.SHOULD };

Query query = queryParser.parse(Version.LUCENE_42, 
								searchStringFinal,
								campos, 
								flags,										
								new EnglishAnalyzer(Version.LUCENE_42)); 	// Se usa el mismo analyzer del indice
																			/*
																			 * Se puede usar otro, pero los resultados del search son distintos
																			 */
// Query query = queryParser.parse(searchStringFinal);
System.out.println("query es: " + query);
System.out
		.println("Type of query: " + query.getClass().getSimpleName());




TopDocs topDocs = indexSearcher.search(query, 20);		

//topDocs = indexSearcher.search(query, topDocs.totalHits);		
//RankedCompany rc = new RankedCompany(topDocs,indexSearcher);		
//rc.listCompanies(indexSearcher);		

// SIN RANKER	

System.out.println("Posts encontrados: " + topDocs.totalHits);

ScoreDoc[] hits = topDocs.scoreDocs;
String objetofinal = "";
for (int i = 0; i < hits.length; i++) {
	int docId = hits[i].doc;
	Document d = indexSearcher.doc(docId);
	objetofinal = objetofinal
			+ "{"
			+ "\"score\":\""
			+ hits[i].score
			+ "\",\t"
			+ "\"company\":\""
			+ d.get("company")
					.replace("\r", "&#13;")
					.replace("\t", "&#09;").replace("\n", "&#10;")
					.replace("\"", "&#34;").replace("\\", "&#92;")
					
													/*
																 * .replace
																 * ("/",
																 * "&#47;"
																 * )
																 */
			+ "\",\t"
			+
			// "\"title\":\""+d.get("title")+"\","+
			"\"autorRating\":\""
			+ d.get("autorRating").replace("\r", "&#13;")
					.replace("\t", "&#09;").replace("\n", "&#10;")
					.replace("\"", "&#34;").replace("\\", "&#92;")/*
																 * .replace
																 * ("/",
																 * "&#47;"
																 * )
																 */
			+ "\",\t" +
			// "\"body\":\""+d.get("context")+"\","+
			"\"date\":\"" + d.get("date") + "\"" + 
			"\"comments\":\""
			+ d.get("comments").replace("\r", "&#13;")
			.replace("\t", "&#09;").replace("\n", "&#10;")
			.replace("\"", "&#34;").replace("\\", "&#92;")
			.substring(0, 50)
			+"..."/*
														 * .replace
														 * ("/",
														 * "&#47;"
														 * )
														 */
	+ "\"" +"}\n";			
}
objetofinal = objetofinal + "";
System.out.println(objetofinal);



//CON RANKER
topDocs= new Ranker().Rank(topDocs, indexSearcher);
hits = topDocs.scoreDocs;
objetofinal = "";
for (int i = 0; i < hits.length; i++) {
	int docId = hits[i].doc;
	Document d = indexSearcher.doc(docId);
	objetofinal = objetofinal
			+ "{"
			+ "\"score\":\""
			+ hits[i].score
			+ "\",\t"
			+ "\"company\":\""
			+ d.get("company")
					.replace("\r", "&#13;")
					.replace("\t", "&#09;").replace("\n", "&#10;")
					.replace("\"", "&#34;").replace("\\", "&#92;")
					
													/*
																 * .replace
																 * ("/",
																 * "&#47;"
																 * )
																 */
			+ "\",\t"
			+
			// "\"title\":\""+d.get("title")+"\","+
			"\"autorRating\":\""
			+ d.get("autorRating").replace("\r", "&#13;")
					.replace("\t", "&#09;").replace("\n", "&#10;")
					.replace("\"", "&#34;").replace("\\", "&#92;")/*
																 * .replace
																 * ("/",
																 * "&#47;"
																 * )
																 */
			+ "\",\t" +
			// "\"body\":\""+d.get("context")+"\","+
			"\"date\":\"" + d.get("date") + "\"" + 
			"\"comments\":\""
			+ d.get("comments").replace("\r", "&#13;")
			.replace("\t", "&#09;").replace("\n", "&#10;")
			.replace("\"", "&#34;").replace("\\", "&#92;")
			.substring(0, 50)
			+"..."/*
														 * .replace
														 * ("/",
														 * "&#47;"
														 * )
														 */
	+ "\"" +"}\n";			
}
objetofinal = objetofinal + "";
System.out.println(objetofinal);

}

	public static ArrayList companySearchList(String searchString, String dirPostsIndex)
			throws Exception {
		
		ArrayList<String> results= new ArrayList<String>();
		Directory directory = FSDirectory.open(new File(dirPostsIndex));

		DirectoryReader reader = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(reader);

		String[] campos = { "company", "autorRating", "comments", "date" };

		MultiFieldQueryParser queryParser = new MultiFieldQueryParser(
				Version.LUCENE_42, campos, new EnglishAnalyzer(
						Version.LUCENE_42)); // Se usa el mismo analyzer del
												// indice
		/*
		 * Se puede usar otro, pero los resultados del search son distintos
		 */

		String searchStringFinal = searchString.replace("_", " ");
		System.out.println("Searching for '" + searchStringFinal
				+ "' using MultiFieldQueryParser");
		BooleanClause.Occur[] flags = { BooleanClause.Occur.SHOULD,
				BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD,
				BooleanClause.Occur.SHOULD };

		Query query = queryParser.parse(Version.LUCENE_42, searchStringFinal,
				campos, flags, new EnglishAnalyzer(Version.LUCENE_42)); // Se
																		// usa
																		// el
																		// mismo
																		// analyzer
																		// del
																		// indice
		/*
		 * Se puede usar otro, pero los resultados del search son distintos
		 */
		// Query query = queryParser.parse(searchStringFinal);
		System.out.println("query es: " + query);
		System.out
				.println("Type of query: " + query.getClass().getSimpleName());

		TopDocs topDocs = indexSearcher.search(query, 20);

		// funcion de ranking
		topDocs = new Ranker().Rank(topDocs, indexSearcher);

		System.out.println("Posts encontrados: " + topDocs.totalHits);

		ScoreDoc[] hits = topDocs.scoreDocs;
		String objetofinal = "";
		String objeto="";
		for (int i = 0; i < hits.length; i++) {
			int docId = hits[i].doc;
			Document d = indexSearcher.doc(docId);
			objeto="{"
				+ "\"score\":\""
				+ hits[i].score
				+ "\",\t"
				+ "\"company\":\""
				+ d.get("company").replace("\r", "&#13;")
						.replace("\t", "&#09;").replace("\n", "&#10;")
						.replace("\"", "&#34;").replace("\\", "&#92;")

				/*
				 * .replace ("/", "&#47;" )
				 */
				+ "\",\t"
				+
				// "\"title\":\""+d.get("title")+"\","+
				"\"overalfeelings\":\""
				+ d.get("overalfeelings").replace("\r", "&#13;")
						.replace("\t", "&#09;").replace("\n", "&#10;")
						.replace("\"", "&#34;").replace("\\", "&#92;")/*
																	 * .replace
																	 * ("/",
																	 * "&#47;"
																	 * )
																	 */
				+ "\",\t"
				+
				// "\"body\":\""+d.get("context")+"\","+
				"\"date\":\""
				+ d.get("date")
				+ "\""
				+ "\"comments\":\""
				+ d.get("comments").replace("\r", "&#13;")
						.replace("\t", "&#09;").replace("\n", "&#10;")
						.replace("\"", "&#34;").replace("\\", "&#92;")
						.substring(0, 50) + "..."/*
												 * .replace ("/", "&#47;" )
												 */
				+ "\"" + "}\n";
			
			objetofinal = objetofinal+objeto;
			results.add(objeto);
					
		}
		//results.add(objetofinal);
//		objetofinal = objetofinal + "";
		System.out.println(objetofinal);
		return results;
	}
	
	
	public static ArrayList<RankedCompany> searchAndRankByCompanies(String searchString, String dirPostsIndex)
	throws Exception {

ArrayList<String> results= new ArrayList<String>();
Directory directory = FSDirectory.open(new File(dirPostsIndex));

DirectoryReader reader = DirectoryReader.open(directory);
IndexSearcher indexSearcher = new IndexSearcher(reader);

String[] campos = { "company", "autorRating", "comments", "date" };

MultiFieldQueryParser queryParser = new MultiFieldQueryParser(
		Version.LUCENE_42, campos, new EnglishAnalyzer(
				Version.LUCENE_42)); // Se usa el mismo analyzer del
										// indice
/*
 * Se puede usar otro, pero los resultados del search son distintos
 */

String searchStringFinal = searchString.replace("_", " ");
System.out.println("Searching for '" + searchStringFinal
		+ "' using MultiFieldQueryParser");
BooleanClause.Occur[] flags = { BooleanClause.Occur.SHOULD,
		BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD,
		BooleanClause.Occur.SHOULD };

Query query = queryParser.parse(Version.LUCENE_42, searchStringFinal,
		campos, flags, new EnglishAnalyzer(Version.LUCENE_42)); // Se
																// usa
																// el
																// mismo
																// analyzer
																// del
																// indice
/*
 * Se puede usar otro, pero los resultados del search son distintos
 */
// Query query = queryParser.parse(searchStringFinal);
System.out.println("query es: " + query);
System.out
		.println("Type of query: " + query.getClass().getSimpleName());

Integer cantDocs=0;
ArrayList<RankedCompany> rc= new ArrayList<RankedCompany>();
ArrayList<RankedCompany> rcFinalResult= new ArrayList<RankedCompany>();
while (rc.size()<MAX_RESULTS){
	cantDocs=cantDocs+250;
	TopDocs topDocs = indexSearcher.search(query, 50);
	rc = new Ranker().RankByCompany(topDocs, indexSearcher);
	if (cantDocs>MAX_DOCS){
		break;
	}
}
System.out.println("Cantidad de Documentos para alcanzar resultados -->"+cantDocs);

// funcion de ranking
if(rc.size()<MAX_RESULTS){
	rcFinalResult=rc;
}else{
	for(int i=0;i<MAX_RESULTS;i++){
		 rcFinalResult.add(rc.get(i));	 
	 }	
}
 


System.out.println("Companies Found " + rc.size());


String objetofinal = "";
String objeto="";
for (int i = 0; i <rc.size(); i++) {
	
	objeto="{"
		+ "\"RF-score\":\""
		+ rc.get(i).getScore()
		+ "\",\t"
		+ "\"company\":\""
		+ rc.get(i).getPc().getCompany().replace("\r", "&#13;")
				.replace("\t", "&#09;").replace("\n", "&#10;")
				.replace("\"", "&#34;").replace("\\", "&#92;")

		/*
		 * .replace ("/", "&#47;" )
		 */
		+ "\",\t"
		+
		// "\"title\":\""+d.get("title")+"\","+
		"\"overalfeelings\":\""
		+ rc.get(i).getPc().getOveralfeelings().replace("\r", "&#13;")
				.replace("\t", "&#09;").replace("\n", "&#10;")
				.replace("\"", "&#34;").replace("\\", "&#92;")/*
															 * .replace
															 * ("/",
															 * "&#47;"
															 * )
															 */
		+ "\",\t"
		+
		// "\"body\":\""+d.get("context")+"\","+
		"\"date\":\""
		+ rc.get(i).getPc().getDate()
		+ "\""
		+ "\"comments\":\""
		+ rc.get(i).getPc().getComments().replace("\r", "&#13;")
				.replace("\t", "&#09;")
				.replace("\"", "&#34;").replace("\\", "&#92;")
				.substring(0, 50) + "..."/*
										 * .replace ("/", "&#47;" )
										 */
		+ "\"" + "}\n";
	
	objetofinal = objetofinal+objeto;
	results.add(objeto);
			
}
//results.add(objetofinal);
//objetofinal = objetofinal + "";
System.out.println(objetofinal);
return rcFinalResult;
}
}
