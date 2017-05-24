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
import facade.Programa_KeywordFacade;
import model.Programa_Keyword;

@Stateless
public class Programa_KeywordFacadeEJB extends AbstractFacade<Programa_Keyword> implements Programa_KeywordFacade {

    @PersistenceContext(unitName = "qvtPU")
    private EntityManager em;

    public Programa_KeywordFacadeEJB() {
        super(Programa_Keyword.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

}
