package cl.irweb.structure;

import org.apache.lucene.search.ScoreDoc;

public class CompanyHit {

	ScoreDoc sd;
	String companyName;



	public CompanyHit(ScoreDoc sd, String companyName) {
		super();
		this.sd = sd;
		this.companyName = companyName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public ScoreDoc getSd() {
		return sd;
	}

	public void setSd(ScoreDoc sd) {
		this.sd = sd;
	}



}
