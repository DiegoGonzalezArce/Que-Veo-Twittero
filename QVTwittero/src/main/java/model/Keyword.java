/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author nikonegima
 */
package model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "Keyword")
@NamedQuery(name = "Keyword.findAll", query = "SELECT f FROM Keyword f")
public class Keyword implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_keyword", unique = true, nullable = false)
    private int keywordId;

    @Column(name = "keyword", nullable = false, length = 45)
    private String keyword;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    public Keyword() {
    }

    public int getKeywordId() {
        return this.keywordId;
    }

    public void setKeywordId(int keywordId) {
        this.keywordId = keywordId;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescription(String descripcion) {
        this.descripcion = descripcion;
    }
}
