package cl.irweb.structure;

public class PostCompany {
	

	public String company;
	public String autorRating;
	public String reviewNumber;	
	public String date;
	public String feelings;
	public String overalfeelings;
	public String comments;
	public String workLifeBalance;
	public String seniorLeadership;
	public String cultureAndValues;
	public String compensationAndBenefits;
	public String careerOpportunities;
	private String commentsMatch;
	/*
	 * Se debe revisar que tipo es el mejor para las variables numericas y categoricas.	
	 */
	
			

	public PostCompany(	String cmp,	String rat,String review,String dat,String feel,String over, String com,String work,String senior,String culture,String compensation,String career){
		this.company = cmp;
		this.autorRating = rat;
		this.reviewNumber = review;	
		this.date= dat;
		this.feelings = feel;
		this.overalfeelings=over;
		this.comments = com;
		this.workLifeBalance = work;
		this.seniorLeadership = senior;
		this.cultureAndValues = culture;
		this.compensationAndBenefits = compensation;
		this.careerOpportunities = career;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAutorRating() {
		return autorRating;
	}

	public void setAutorRating(String autorRating) {
		this.autorRating = autorRating;
	}

	public String getReviewNumber() {
		return reviewNumber;
	}

	public void setReviewNumber(String reviewNumber) {
		this.reviewNumber = reviewNumber;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFeelings() {
		return feelings;
	}

	public void setFeelings(String feelings) {
		this.feelings = feelings;
	}

	public String getOveralfeelings() {
		return overalfeelings;
	}

	public void setOveralfeelings(String overalfeelings) {
		this.overalfeelings = overalfeelings;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getWorkLifeBalance() {
		return workLifeBalance;
	}

	public void setWorkLifeBalance(String workLifeBalance) {
		this.workLifeBalance = workLifeBalance;
	}

	public String getSeniorLeadership() {
		return seniorLeadership;
	}

	public void setSeniorLeadership(String seniorLeadership) {
		this.seniorLeadership = seniorLeadership;
	}

	public String getCultureAndValues() {
		return cultureAndValues;
	}

	public void setCultureAndValues(String cultureAndValues) {
		this.cultureAndValues = cultureAndValues;
	}

	public String getCompensationAndBenefits() {
		return compensationAndBenefits;
	}

	public void setCompensationAndBenefits(String compensationAndBenefits) {
		this.compensationAndBenefits = compensationAndBenefits;
	}

	public String getCareerOpportunities() {
		return careerOpportunities;
	}

	public void setCareerOpportunities(String careerOpportunities) {
		this.careerOpportunities = careerOpportunities;
	}

	public String getCommentsMatch() {
		return commentsMatch;
	}

	public void setCommentsMatch(String commentsMatch) {
		this.commentsMatch = commentsMatch;
	}

	
	
	
}
