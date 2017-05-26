/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author yolo
 */
@Entity
@Table(name="Tweet")
@NamedQuery(name="Tweet.findAll", query="SELECT f FROM Tweet f")
public class Tweet {  
    @Id
    @Column(name="id_Tweet", unique=true, nullable=false)
    private long id_Tweet;

    @Column(name="comment",nullable=false, length=250)
    private String comment;

    @Column(name="username", nullable=false, length=45)
    private String username;

    @Column(name="date", nullable=false)
    private Timestamp date;

    @Column(name="RTcount", nullable=false)
    private int RTcount;
    
    @Column(name="LIKEcount", nullable=false)
    private int LIKEcount;
    
    @Column(name="analisis", nullable=false)
    private int analisis;

    public Tweet() {
    }

    public long getId_Tweet() {
        return id_Tweet;
    }

    public void setId_Tweet(long id_Tweet) {
        this.id_Tweet = id_Tweet;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getRTcount() {
        return RTcount;
    }

    public void setRTcount(int RTcount) {
        this.RTcount = RTcount;
    }

    public int getLIKEcount() {
        return LIKEcount;
    }

    public void setLIKEcount(int LIKEcount) {
        this.LIKEcount = LIKEcount;
    }

    public int getAnalisis() {
        return analisis;
    }

    public void setAnalisis(int analisis) {
        this.analisis = analisis;
    }
    
    

}
