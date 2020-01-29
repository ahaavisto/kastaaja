/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author arkkis
 */

import ahaavisto.kastaaja.Kastaaja;
import ahaavisto.kastaaja.Profiili;
import static org.junit.Assert.*;
import org.junit.Test;

public class TestKastaaja {
    static Profiili hahmo1, hahmo2, pelaaja1, pelaaja2;
            
    static void luo_profiilit () {
        Profiili hahmot[] = {hahmo1, hahmo2};
        Profiili pelaajat[] = {pelaaja1, pelaaja2};
        Kastaaja.luo_lista_suosikeista(hahmo1, pelaajat);
    }
    
    
    @Test
    public void testNothing() {
        assertTrue(true);
    }
    
    @Test
    public void testOnkoIsompi() {
        
    }
    
//    
//    int[] arr = {4, 3, 2, 1};
//        StaticRMQ rmq = new StaticRMQ(arr);
//        assertEquals("Wrong minimum for whole array", 1, rmq.query(0, 3));
//assertEquals("Wrong minimum for first element", 4, rmq.query(0, 0));
}
