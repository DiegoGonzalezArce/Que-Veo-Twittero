package cl.irweb.main;

public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
SearchAndRankServiceBean sr = new SearchAndRankServiceBean();
sr.updateIndex();
sr.setContent("good");
//sr.searchAndRank();
sr.searchAndRankByCompany();
System.out.println("SALIDA-->"+sr.getSearchResults());
		
		
	}

}
