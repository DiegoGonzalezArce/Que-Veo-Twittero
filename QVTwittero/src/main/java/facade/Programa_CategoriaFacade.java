/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nikonegima
 */
package facade;

import java.util.List;

import javax.ejb.Local;

import model.Programa_Categoria;

@Local
public interface Programa_CategoriaFacade {

	public void create(Programa_Categoria entity);

	public void edit(Programa_Categoria entity);

	public void remove(Programa_Categoria entity);

	public Programa_Categoria find(Object id);

	public List<Programa_Categoria> findAll();

	public List<Programa_Categoria> findRange(int[] range);

	public int count();

}
