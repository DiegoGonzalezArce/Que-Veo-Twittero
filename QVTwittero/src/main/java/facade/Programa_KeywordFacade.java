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

import model.Programa_Keyword;

@Local
public interface Programa_KeywordFacade {

    public void create(Programa_Keyword entity);

    public void edit(Programa_Keyword entity);

    public void remove(Programa_Keyword entity);

    public Programa_Keyword find(Object id);

    public List<Programa_Keyword> findAll();

    public List<Programa_Keyword> findRange(int[] range);

    public int count();
}
