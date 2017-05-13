package cl.irweb.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.Vector;

import javax.faces.component.UICommand;
import javax.faces.component.UIForm;
import javax.faces.event.ActionEvent;




import cl.irweb.indexer.PostIndexer;
import cl.irweb.parser.PostParser;
import cl.irweb.searcher.PostSearcher;
import cl.irweb.structure.RankedCompany;

public class SearchAndRankServiceBean {
	
	  // JavaServerFaces related variables
	  private UIForm form;
	  private UIForm tableForm;
	  private UICommand addCommand;
	  
	final static String nodoBuscarPosts = "//row";
	final static String nodoBuscarComments = "//row";
	final static String dirPostsIndex = "DB\\index";
	private String content = "";
	private List<String> searchResults = null;
	private List<RankedCompany> rc=null;
	private boolean initial = true;
	// final static String ARCHIVO_DAT=
	// "C:/magister/tecnologiasWeb/proyecto/corpus/CompaniesDataBase.dat";
	final static String ARCHIVO_DAT = "DB\\DB.dat";

	// Se modifico PostCompany para que utilice la �ltima versi�n de la base.

	public String searchAndRank() {
		initial = false;
		String searchResult = null;
	
		searchResults = new ArrayList<String>();
		try {

//			PostParser postParser = new PostParser();
//			PostIndexer postsIndex = new PostIndexer(dirPostsIndex);
//			Vector postsDat = postParser.parsearArchivoDat(ARCHIVO_DAT,
//					nodoBuscarPosts, nodoBuscarComments);
//
//			postsIndex.fillIndexPostsCompany(postsDat);
//			postsIndex.closeIndex();
			PostSearcher postsSearcher = new PostSearcher();

			if (content.length() == 0) {
				searchResults = PostSearcher.companySearchList("good salary",
						dirPostsIndex);
			} else
				searchResults = PostSearcher.companySearchList(this.content,
						dirPostsIndex);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//searchResults.add(searchResult);
		return searchResult;
	}
	
	
	public String searchAndRankByCompany() {
		initial = false;
		String searchResult = null;
	
		rc = new ArrayList<RankedCompany>();
		try {

//			PostParser postParser = new PostParser();
//			PostIndexer postsIndex = new PostIndexer(dirPostsIndex);
//			Vector postsDat = postParser.parsearArchivoDat(ARCHIVO_DAT,
//					nodoBuscarPosts, nodoBuscarComments);
//
//			postsIndex.fillIndexPostsCompany(postsDat);
//			postsIndex.closeIndex();
			PostSearcher postsSearcher = new PostSearcher();

			if (content.length() == 0) {
				rc = PostSearcher.searchAndRankByCompanies("good salary",
						dirPostsIndex);
			} else
				rc = PostSearcher.searchAndRankByCompanies(this.content,
						dirPostsIndex);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//searchResults.add(searchResult);
		return searchResult;
	}

	public boolean updateIndex() {
		PostParser postParser = new PostParser();
		boolean done=false;
		try {
			PostIndexer postsIndex = new PostIndexer(dirPostsIndex);			
			Vector postsDat = postParser.parsearArchivoDat(ARCHIVO_DAT, nodoBuscarPosts, nodoBuscarComments);					
			postsIndex.fillIndexPostsCompany(postsDat);
			postsIndex.closeIndex();
			done=true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return done;
	}

	
	public void displayTable(ActionEvent event) {
	    if (event.getComponent().getId().equalsIgnoreCase("hide")) {
	      tableForm.setRendered(false);
	    } else {
	      tableForm.setRendered(true);
	    }
	  }
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getSearchResults() {
		return searchResults;
	}

	public void setSearchResults(ArrayList searchResults) {
		this.searchResults = searchResults;
	}

	public boolean isInitial() {
		return initial;
	}

	public void setInitial(boolean initial) {
		this.initial = initial;
	}

	public void setForm(UIForm form) {
		this.form = form;
	}

	public UIForm getForm() {
		return form;
	}

	public void setTableForm(UIForm tableForm) {
		this.tableForm = tableForm;
	}

	public UIForm getTableForm() {
		return tableForm;
	}

	public void setAddCommand(UICommand addCommand) {
		this.addCommand = addCommand;
	}

	public UICommand getAddCommand() {
		return addCommand;
	}


	public List<RankedCompany> getRc() {
		return rc;
	}


	public void setRc(List<RankedCompany> rc) {
		this.rc = rc;
	}


	public void setSearchResults(List<String> searchResults) {
		this.searchResults = searchResults;
	}

}
