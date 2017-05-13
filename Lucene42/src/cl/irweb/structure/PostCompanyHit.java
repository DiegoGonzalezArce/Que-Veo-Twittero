package cl.irweb.structure;



public class PostCompanyHit {
	
	PostCompany pc;
	String companyName;
	
	
	
	public PostCompanyHit(PostCompany pc, String companyName) {
		super();
		this.pc = pc;
		this.companyName = companyName;
	}
	public PostCompany getPc() {
		return pc;
	}
	public void setPc(PostCompany pc) {
		this.pc = pc;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	

}
