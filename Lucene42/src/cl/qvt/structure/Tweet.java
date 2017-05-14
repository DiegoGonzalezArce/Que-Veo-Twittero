package cl.qvt.structure;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author diego
 */

/**
 * clase que es el modelo de los tweeter en la bd
 */

public class Tweet {
    public String _id;
    public String id;
    public String tweet;
    public String username;
    public String day;
    public String mouth;
    public String hour;
    public String RTcount;
    public String LIKEcount;

    public Tweet(String _id, String id, String tweet, String username, String day, String mouth, String hour, String RTcount, String LIKEcount) {
        this._id = _id;
        this.id = id;
        this.tweet = tweet;
        this.username = username;
        this.day = day;
        this.mouth = mouth;
        this.hour = hour;
        this.RTcount = RTcount;
        this.LIKEcount = LIKEcount;
    }

    public String get_Id() {
        return _id;
    }

    public void set_Id(String _id) {
        this._id = _id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMouth() {
        return mouth;
    }

    public void setMouth(String mouth) {
        this.mouth = mouth;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getRTcount() {
        return RTcount;
    }

    public void setRTcount(String RTcount) {
        this.RTcount = RTcount;
    }

    public String getLIKEcount() {
        return LIKEcount;
    }

    public void setLIKEcount(String LIKEcount) {
        this.LIKEcount = LIKEcount;
    }
    public void showInfo(){
        System.out.println("_id:"+_id);
        System.out.println("id:"+id);
        System.out.println("tweet:"+tweet);
        System.out.println("username:"+username);
        System.out.println("day:"+day);
        System.out.println("month:"+mouth);
        System.out.println("hour:"+hour);
        System.out.println("RTcount:"+RTcount);
        System.out.println("LIKEcount"+LIKEcount);
        System.out.println("");
    }
}
