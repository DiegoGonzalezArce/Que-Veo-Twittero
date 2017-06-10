/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author yolo
 */
public class Link {
    String source;
    String target;
    int value;

    public Link() {
    }

    public Link(String source, String target, String value) {
        this.source = source;
        this.target = target;
        try{
            this.value=Integer.parseInt(value);
        }catch(NumberFormatException e){
            this.value=0;
        }
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    
}
