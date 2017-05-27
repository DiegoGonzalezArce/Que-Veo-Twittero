/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
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
@Table(name="Tweet_Keyword")
@NamedQuery(name="Tweet_Keyword.findAll", query="SELECT f FROM Tweet_Keyword f")
public class Tweet_Keyword implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="idTweet_Keyword", unique=true, nullable=false)
    private int id_Tweet_Keyword;

    @Column(name="Keyword2_id",nullable=false)
    private int Keyword_id;

    @Column(name="Tweet_id", nullable=false)
    private long Tweet_id;
    
    public Tweet_Keyword() {
    }

    public int getId_Tweet_Keyword() {
        return id_Tweet_Keyword;
    }

    public void setId_Tweet_Keyword(int id_Tweet_Keyword) {
        this.id_Tweet_Keyword = id_Tweet_Keyword;
    }

    public long getTweet_id() {
        return Tweet_id;
    }

    public void setTweet_id(long Tweet_id) {
        this.Tweet_id = Tweet_id;
    }

    public int getKeyword_id() {
        return Keyword_id;
    }

    public void setKeyword_id(int Keyword_id) {
        this.Keyword_id = Keyword_id;
    }
    
}
