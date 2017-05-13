package cl.irweb.structure;

public class RankedCompany {

	PostCompany pc;
	float score;

	public RankedCompany(PostCompany pc, float score) {
		super();
		this.pc = pc;
		this.score = score;
	}

	public PostCompany getPc() {
		return pc;
	}

	public void setPc(PostCompany pc) {
		this.pc = pc;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

}
