/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import facade.AbstractFacade;
import facade.Tweet_KeywordFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Tweet_Keyword;

/**
 *
 * @author yolo
 */
@Stateless
public class Tweet_KeywordFacadeEJB extends AbstractFacade<Tweet_Keyword> implements Tweet_KeywordFacade{
    @PersistenceContext(unitName = "qvtPU")
    private EntityManager em;
	
    public Tweet_KeywordFacadeEJB() {
	super(Tweet_Keyword.class);
    }

    @Override
    protected EntityManager getEntityManager() {
	return this.em;
    }
    
    @Override
    public List<Tweet_Keyword> findRepeated(int idKeyword,long idTweet) {
    return em.createQuery(
        "SELECT tk FROM Tweet_Keyword tk WHERE tk.Keyword_id="+idKeyword+" AND tk.Tweet_id="+idTweet+"")
        .getResultList();
    }
}
