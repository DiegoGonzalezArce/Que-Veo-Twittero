/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

/**
 *
 * @author nikonegima
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.AbstractFacade;
import facade.CategoriaFacade;
import model.Categoria;

//EJB de la Tabla Canals
@Stateless
public class CategoriaFacadeEJB extends AbstractFacade<Categoria> implements CategoriaFacade {
	
	
	@PersistenceContext(unitName = "qvtPU")
	private EntityManager em;
	
	public CategoriaFacadeEJB() {
		super(Categoria.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return this.em;
	}

}

