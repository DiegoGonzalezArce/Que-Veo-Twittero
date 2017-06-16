/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yolo
 */
@XmlRootElement
public class GTorta {
    List<GTortaElem> resultados;

    public GTorta() {
    }

    public GTorta(List<GTortaElem> resultados) {
        this.resultados = resultados;
    }

    public List<GTortaElem> getResultados() {
        return resultados;
    }

    public void setResultados(List<GTortaElem> resultados) {
        this.resultados = resultados;
    }
    
}
