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

    public TwitterConnection() {
        ConfigurationBuilder cf = new ConfigurationBuilder();
        cf.setDebugEnabled(true)
                .setOAuthConsumerKey("nW9sVzlumZpfz1qnVUkMuNV21")
                .setOAuthConsumerSecret("mGy8H4vyvkn4FAN3UbqyseD3B7HCrYkcP46TytOC4UzuAiYazW")
                .setOAuthAccessToken("125499213-juWguCLomynB1kHZae4pSPRG0FwnXhLxnevm3IR2")
                .setOAuthAccessTokenSecret("FzO3gHpbWwNhGO4fZY7QbqreMQn76h7tpWofX6kJjXtu8");
        this.twitter = new TwitterFactory(cf.build()).getInstance();

    }

    public int getRTCountbyID(long id) throws TwitterException {
        Status status = this.twitter.showStatus(id);
        int RTcontador = status.getRetweetCount();
        return RTcontador;
    }

    public int getLikeCountbyID(long id) throws TwitterException {
        Status status = this.twitter.showStatus(id);
        int Likecontador = status.getFavoriteCount();
        return Likecontador;
    }

    public int getMencionesbyID(long id) throws TwitterException {
        Status status = this.twitter.showStatus(id);
        int RTcontador = status.getRetweetCount();
        int Likecontador = status.getFavoriteCount();
        return RTcontador + Likecontador + 1;
    }
}
