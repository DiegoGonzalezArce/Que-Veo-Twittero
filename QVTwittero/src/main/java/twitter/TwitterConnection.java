/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twitter;


import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author nikonegima
 */
public class TwitterConnection {

    private Twitter twitter;
    
    public TwitterConnection(){
        ConfigurationBuilder cf = new ConfigurationBuilder();
        cf.setDebugEnabled(true)
              .setOAuthConsumerKey("z7FuJknCKV5vp2l9y4MfsD4od")
              .setOAuthConsumerSecret("yOuYqNvkMlPpb4skl5Il3nt2OzAj5ZxBM2VjUmJ0nlwia5rmZa")
              .setOAuthAccessToken("452625136-HO0NCkkzwTOocixlFmyaKOrpYnBTnEFQLKxbacJZ")
              .setOAuthAccessTokenSecret("PYUOk04qyLUTdycSwQfd9btcli9oxFAGkRdo2PUv8qPLs");
      this.twitter = new TwitterFactory(cf.build()).getInstance();

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
    
    public int getMencionesbyID(Long id) throws TwitterException{
        Status status = this.twitter.showStatus(id);
        int RTcontador = status.getRetweetCount();
        int Likecontador = status.getFavoriteCount();
        return RTcontador + Likecontador+1;
    }
}