package cl.irweb.searcher;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import org.apache.lucene.document.Document;

import org.apache.lucene.search.IndexSearcher;

import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import cl.irweb.structure.CompanyHit;
import cl.irweb.structure.PostCompany;
import cl.irweb.structure.PostCompanyHit;
import cl.irweb.structure.RankedCompany;

public class Ranker {

	private int COMMENTS_WEIGHT = 100;
	private int DATE_WEIGHT = 5;
	private int FEELING_WEIGHT = 100;
	private int OVERALLFEELING_WEIGHT = 200;
	private int AUTOR_RATING_WEIGHT = 20;

	public TopDocs Rank(TopDocs td, IndexSearcher indexSearcher)
			throws Exception {
		ScoreDoc[] hits = td.scoreDocs;
		hits = groupByCompany(hits, indexSearcher);
		ScoreDoc[] rankedHits = new ScoreDoc[hits.length];
		ArrayList<RankedObject> docsToRank = new ArrayList<RankedObject>();
		ArrayList<RankedObject> ranking = new ArrayList<RankedObject>();
		for (int i = 0; i < hits.length; i++) {
			int docId = hits[i].doc;
			Document d = indexSearcher.doc(docId);
			docsToRank
					.add(new RankedObject(hits[i], scoreFunction(hits[i], d)));
		}

		ranking = sortDocuments(docsToRank);
		for (int i = 0; i < ranking.size(); i++) {
			rankedHits[i] = ranking.get(i).getSd();
		}
		td.scoreDocs = rankedHits;
		return td;
	}

	private float scoreFunction(ScoreDoc sd, Document d) throws ParseException {

		Calendar currentDate = Calendar.getInstance();
		Calendar postDate = Calendar.getInstance();
		postDate.set(Integer.valueOf(d.get("date").substring(6, 10)),
				Integer.valueOf(d.get("date").substring(3, 5)),
				Integer.valueOf(d.get("date").substring(0, 2)));
		long currentMill = currentDate.getTimeInMillis();
		long postMill = postDate.getTimeInMillis();
		long diff = currentMill - postMill;
		long diffDays = diff / (24 * 60 * 60 * 1000);

		float total = 0f;
		float commentsMatch = sd.score * COMMENTS_WEIGHT;
		float autorRating = Float.valueOf(d.get("autorRating"))
				* AUTOR_RATING_WEIGHT;
		float days = Float.valueOf(diffDays) * DATE_WEIGHT;
		float feelings = Float.valueOf(d.get("feelings")) * FEELING_WEIGHT;
		float overallfeelings = ((Float.valueOf(d.get("overalfeelings")) + 323) / (3042 + 323))
				* OVERALLFEELING_WEIGHT; // Max 3042 Min -323 - Quedan todos en
											// positivo, entre 0 y 1

		total = commentsMatch + autorRating + feelings + overallfeelings - days; // days
																					// en
																					// negativo,
																					// mientras
																					// más
																					// antiguo
																					// el
																					// post,
																					// menor
																					// valor.

		return total;
	}

	public ArrayList<RankedObject> sortDocuments(ArrayList<RankedObject> ranking) {

		Collections.sort(ranking, new Comparator<RankedObject>() {
			public int compare(RankedObject o1, RankedObject o2) {
				if (o1.getScore() > o2.getScore())
					return -1;
				if (o1.getScore() < o2.getScore())
					return 1;
				return 0;

			}
		});

		return ranking;
	}

	public ArrayList<RankedCompany> sortCompanies(
			ArrayList<RankedCompany> ranking) {

		Collections.sort(ranking, new Comparator<RankedCompany>() {
			public int compare(RankedCompany o1, RankedCompany o2) {
				if (o1.getScore() > o2.getScore())
					return -1;
				if (o1.getScore() < o2.getScore())
					return 1;
				return 0;

			}
		});

		return ranking;
	}

	public ScoreDoc[] groupByCompany(ScoreDoc[] hits,
			IndexSearcher indexSearcher) throws IOException {

		ArrayList<CompanyHit> newHits = new ArrayList<CompanyHit>();

		for (int i = 0; i < hits.length; i++) {
			int docId = hits[i].doc;
			Document d = indexSearcher.doc(docId);
			if (!hasHit(newHits, d.get("company"))) {

				newHits.add(new CompanyHit(hits[i], d.get("company")));
			}
		}

		hits = new ScoreDoc[newHits.size()];
		for (int i = 0; i < newHits.size(); i++) {
			hits[i] = newHits.get(i).getSd();

		}
		return hits;
	}

	private boolean hasHit(ArrayList<CompanyHit> newHits, String companyName) {
		boolean result = false;
		if (!newHits.isEmpty()) {
			for (CompanyHit ch : newHits) {
				if (ch != null) {
					if (ch.getCompanyName().equals(companyName)) {
						result = true;
					}
				}
			}
		}

		return result;
	}

	private boolean hasPostCompanyHit(ArrayList<PostCompanyHit> newHits,
			String companyName) {
		boolean result = false;
		if (!newHits.isEmpty()) {
			for (PostCompanyHit ch : newHits) {
				if (ch != null) {
					if (ch.getPc().getCompany().equals(companyName)) {
						result = true;
					}
				}
			}
		}

		return result;
	}

	public ArrayList<PostCompany> groupByPostCompany(ScoreDoc[] hits,
			IndexSearcher indexSearcher) throws IOException, ParseException {

		ArrayList<PostCompanyHit> newHits = new ArrayList<PostCompanyHit>();
		ArrayList<PostCompany> result = new ArrayList<PostCompany>();
		PostCompany pc = null;
		for (int i = 0; i < hits.length; i++) {
			int docId = hits[i].doc;
			Document d = indexSearcher.doc(docId);
			if (!hasPostCompanyHit(newHits, d.get("company"))) {
				pc = new PostCompany(d.get("company"), d.get("autorRating"),
						d.get("reviewNumber"), d.get("date"),
						d.get("feelings"), d.get("overalfeelings"),
						d.get("comments"), d.get("workLifeBalance"),
						d.get("seniorLeadership"), d.get("cultureAndValues"),
						d.get("compensationAndBenefits"),
						d.get("careerOpportunities"));
				pc.setCommentsMatch(Float.toString(hits[i].score));
				newHits.add(new PostCompanyHit(pc, d.get("company")));
			}

			else {
				newHits = mergeHits(newHits, d, hits[i]);
			}

		}

		for (int i = 0; i < newHits.size(); i++) {
			result.add(newHits.get(i).getPc());
		}
		return result;
	}

	private ArrayList<PostCompanyHit> mergeHits(
			ArrayList<PostCompanyHit> newHits, Document d, ScoreDoc hits) throws ParseException {
		// TODO Auto-generated method stub
		Double autorRating = 0d;
		Double feelings = 0d;
		Calendar docPostDate = Calendar.getInstance();
		Calendar hitStoreDate = Calendar.getInstance();
		SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
		
		docPostDate.setTime(format1.parse(d.get("date").trim()));
		
		String date = "";
		Float commentsMatch=0f;
		if (!newHits.isEmpty()) {
			for (PostCompanyHit ch : newHits) {
				if (ch != null) {
					if (ch.getPc().getCompany().equals(d.get("company"))) {
						// getting and merging data to add into the grouped
						// information.
						autorRating = (Double.parseDouble(ch.getPc()
								.getAutorRating())
								+ Double.parseDouble(d.get("autorRating")))/2;// Average
																				// Rating
						feelings = (Double.parseDouble(ch.getPc().getFeelings())
								+ Double.parseDouble(d.get("feelings")))/2;
						
						hitStoreDate.setTime(format1.parse(ch.getPc().getDate()));

						//hitDateMill = (DocDateMill + hitStoreDate.getTimeInMillis()) / 2;// Average of Dates
						if(docPostDate.compareTo(hitStoreDate)>0){	
						
							date=format1.format(docPostDate.getTime()).trim();
							ch.getPc().setComments(d.get("comments"));// Adding comments
						}
						else{
							date=format1.format(hitStoreDate.getTime()).trim();
						}
						commentsMatch=((Float.valueOf(ch.getPc().getCommentsMatch())+Float.valueOf(hits.score))/2);

						ch.getPc().setAutorRating(autorRating.toString());// fusionando
																			// autor
																			// rating
						ch.getPc().setFeelings(feelings.toString());// fusionando
																	// feelings
						ch.getPc().setDate(date);// promediando fechas de posts.
						
						// overall feeling remains due is the same for all the
						// comments related to one company
						
						ch.getPc().setCommentsMatch(Float.toString(commentsMatch));//Average of lucene Match of words.
					}
				}
			}
		
		}
		return newHits;
	}

	private float scoreFunctionByCompany(PostCompany pc) throws ParseException {

		Calendar currentDate = Calendar.getInstance();
		Calendar postDate = Calendar.getInstance();
		postDate.set(Integer.valueOf(pc.getDate().substring(6, 10)),
				Integer.valueOf(pc.getDate().substring(3, 5)),
				Integer.valueOf(pc.getDate().substring(0, 2)));
		long currentMill = currentDate.getTimeInMillis();
		long postMill = postDate.getTimeInMillis();
		long diff = currentMill - postMill;
		long diffDays = diff / (24 * 60 * 60 * 1000);

		float total = 0f;
		float commentsMatch = Float.valueOf(pc.getCommentsMatch())* COMMENTS_WEIGHT;// Agregar match de comentarios
												// entregado por lucene.
		float autorRating = Float.valueOf(pc.getAutorRating())
				* AUTOR_RATING_WEIGHT;
		float days = Float.valueOf(diffDays) * DATE_WEIGHT;
		float feelings = Float.valueOf(pc.getFeelings()) * FEELING_WEIGHT;
		float overallfeelings = ((Float.valueOf(pc.getOveralfeelings()) + 323) / (3042 + 323))
				* OVERALLFEELING_WEIGHT; // Max 3042 Min -323 - Quedan todos en
											// positivo, entre 0 y 1

		total = commentsMatch + autorRating + feelings + overallfeelings - days; // days
																					// en
																					// negativo,
																					// mientras
																					// más
																					// antiguo
																					// el
																					// post,
																					// menor
																					// valor.

		return total;
	}

	public ArrayList<RankedCompany> RankByCompany(TopDocs td,
			IndexSearcher indexSearcher) throws Exception {
		ScoreDoc[] hits = td.scoreDocs;
		ArrayList<PostCompany> pcList = new ArrayList<PostCompany>();
		pcList = groupByPostCompany(hits, indexSearcher);
		ArrayList<RankedCompany> docsToRank = new ArrayList<RankedCompany>();
		for (int i = 0; i < pcList.size(); i++) {
			docsToRank.add(new RankedCompany(pcList.get(i),
					scoreFunctionByCompany(pcList.get(i))));
		}
		docsToRank = sortCompanies(docsToRank);

		return docsToRank;
	}
	
	private boolean newest(long date1, long date2){
		
		if(date1>=date2){
		return	true;
		}
		else
			return false;
	}
}
