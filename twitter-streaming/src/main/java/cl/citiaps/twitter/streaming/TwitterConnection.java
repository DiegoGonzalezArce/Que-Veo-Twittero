/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.citiaps.twitter.streaming;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 *
 * @author nikonegima
 */
public class TwitterConnection {
    
    private Twitter twitter;
    
    public TwitterConnection(){
        this.twitter = new TwitterFactory().getInstance();

    }
    
    public int getRTCountbyID(long id) throws TwitterException{
        Status status = this.twitter.showStatus(id);
        int RTcontador = status.getRetweetCount();
        return RTcontador;
    }
    
    public int getLikeCountbyID(long id) throws TwitterException{
        Status status = this.twitter.showStatus(id);
        int Likecontador = status.getFavoriteCount();
        return Likecontador;
    } 
}
