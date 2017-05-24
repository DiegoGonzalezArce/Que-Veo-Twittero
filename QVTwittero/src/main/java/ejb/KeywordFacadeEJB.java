/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author nikonegima
 */
package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.AbstractFacade;
import facade.KeywordFacade;
import model.Keyword;

@Stateless
public class KeywordFacadeEJB extends AbstractFacade<Keyword> implements KeywordFacade {

    @PersistenceContext(unitName = "qvtPU")
    private EntityManager em;

    public KeywordFacadeEJB() {
        super(Keyword.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

}
