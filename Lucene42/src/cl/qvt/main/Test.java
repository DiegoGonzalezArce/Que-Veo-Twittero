package cl.qvt.main;

import java.io.IOException;

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
 * funcion para testear el codigo, si le dan run deberia no tirar nada devuelta 
 * a menos que haya un error, luego deberia de haber creado la carpeta index 
 * con un monton de archivos
 * @author diego
 */
public class Test{
        public static void main(String[] args) throws IOException{
            LuceneServiceBean sr = new LuceneServiceBean();
            sr.updateIndex();
            
        }
}

