/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import java.util.List;
import javax.ejb.Local;
import model.Programa;
import model.Tweet;

/**
 *
 * @author yolo
 */
@Local
public interface TweetFacade {
    public void create(Tweet entity);

    public void edit(Tweet entity);

    public void remove(Tweet entity);

    public Tweet find(Object id);

    public List<Tweet> findAll();

    public List<Tweet> findRange(int[] range);

    public int count();
}
