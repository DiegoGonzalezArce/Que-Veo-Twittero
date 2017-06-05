/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import facade.AbstractFacade;
import facade.TweetFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Tweet;

/**
 *
 * @author yolo
 */
@Stateless
public class TweetFacadeEJB extends AbstractFacade<Tweet> implements TweetFacade {

    @PersistenceContext(unitName = "qvtPU")
    private EntityManager em;

    public TweetFacadeEJB() {
        super(Tweet.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }
    @Override
    public List<String> findUsers(){
        return em.createQuery(
                "SELECT DISTINCT tk.username FROM Tweet tk")
                .getResultList();
    }
}
