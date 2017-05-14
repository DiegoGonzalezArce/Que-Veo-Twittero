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

import model.Programa;

@Local
public interface ProgramaFacade {

	public void create(Programa entity);

	public void edit(Programa entity);

	public void remove(Programa entity);

	public Programa find(Object id);

	public List<Programa> findAll();

	public List<Programa> findRange(int[] range);

	public int count();

}
