package cl.irweb.searcher;

import org.apache.lucene.search.ScoreDoc;

public class RankedObject {

	ScoreDoc sd;
	float score;
	
	public RankedObject(ScoreDoc sd, float score){
		
		this.sd=sd;
		this.score=score;
		
	}

	public ScoreDoc getSd() {
		return sd;
	}

	public void setSd(ScoreDoc sd) {
		this.sd = sd;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}
	

}
