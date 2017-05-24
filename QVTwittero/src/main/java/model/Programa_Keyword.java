/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author nikonegima
 */
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "Programa_Keyword")
@NamedQuery(name = "Programa_Keyword.findAll", query = "SELECT f FROM Programa_Keyword f")
public class Programa_Keyword implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_programa_keyword", unique = true, nullable = false)
    private int programaKeywordId;

    @Id
    @Column(name = "programa_id", nullable = false)
    private int programaId;

    @Id
    @Column(name = "keyword_id", nullable = false)
    private int keywordId;

    public Programa_Keyword() {
    }

    public int getProgramaKeywordId() {
        return this.programaKeywordId;
    }

    public void setProgramaKeywordId(int Id) {
        this.programaKeywordId = Id;
    }

    public int getProgramaId() {
        return this.programaId;
    }

    public void setProgramaId(int Id) {
        this.programaId = Id;
    }

    public int getKeywordId() {
        return this.keywordId;
    }

    public void setKeywordId(int Id) {
        this.keywordId = Id;
    }
}
