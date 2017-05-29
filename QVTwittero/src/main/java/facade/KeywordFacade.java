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

import model.Keyword;

//Facade de la tabla Keyword
@Local
public interface KeywordFacade {

    public void create(Keyword entity);

    public void edit(Keyword entity);

    public void remove(Keyword entity);

    public Keyword find(Object id);

    public List<Keyword> findAll();

    public List<Keyword> findRange(int[] range);

    public int count();
}
