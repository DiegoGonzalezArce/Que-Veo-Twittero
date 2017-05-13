package cl.irweb.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import cl.irweb.structure.PostCompany;






public class PostParser {

	public Vector parsearArchivoDat(String archivoDat, String nodoBuscarPosts,
			String nodoBuscarComments) {
		try {			
			Vector vectorPosts = new Vector();
			String delims = "\t";
			List<String> list = getContent(archivoDat);
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				String elem = (String) iter.next();
				StringTokenizer st = new StringTokenizer(elem, delims);
				String company = st.nextElement().toString();
				String autorRating = st.nextElement().toString();
				String reviewNumber = st.nextElement().toString();
				String date = st.nextElement().toString();
				String feelings = st.nextElement().toString();
				String overalfeelings = st.nextElement().toString();
				String comments = st.nextElement().toString();
				String workLifeBalance = st.nextElement().toString();
				String seniorLeadership = st.nextElement().toString();
				String cultureAndValues = st.nextElement().toString();
				String compensationAndBenefits = st.nextElement().toString();
				String careerOpportunities = st.nextElement().toString();
				PostCompany postadd = new PostCompany( 	company,
														autorRating,
														reviewNumber,
														date,
														feelings,
														overalfeelings,
														comments,
														workLifeBalance,
														seniorLeadership,
														cultureAndValues,
														compensationAndBenefits,
														careerOpportunities	); 
				vectorPosts.add(postadd);
			}
			return vectorPosts;
		} catch (Exception es) {
			System.out.println("ERROR: " + es.getMessage());
		}
		return null;
	}

	public List<String> getContent(String filePath)
			throws FileNotFoundException {
		List<String> records = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		try {

			String line = br.readLine();

			while (line != null) {
				records.add(line);
				line = br.readLine();
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return records;
	}

}
