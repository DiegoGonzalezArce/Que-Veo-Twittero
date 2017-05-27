/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import java.util.List;
import javax.ejb.Local;
import model.Tweet_Keyword;

/**
 *
 * @author yolo
 */
@Local
public interface Tweet_KeywordFacade {
    public void create(Tweet_Keyword entity);

    public void edit(Tweet_Keyword entity);

    public void remove(Tweet_Keyword entity);

    public Tweet_Keyword find(Object id);

    public List<Tweet_Keyword> findAll();

    public List<Tweet_Keyword> findRange(int[] range);
    
    public List<Tweet_Keyword> findRepeated(int idKeyword,long idTweet);
    
    public int count();
}
