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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;

public class TestKastaaja {
    static Profiili hahmo1, hahmo2, pelaaja1, pelaaja2;
    static ArrayList<Profiili> hahmot, pelaajat;
            
    static void luo_profiilit () {
        hahmot = new ArrayList<>();
        hahmo1 = new Profiili("bob");
        hahmot.add(hahmo1);
        hahmo2 = new Profiili("bill");
        hahmot.add(hahmo2);
        
        pelaajat = new ArrayList<>();
        pelaaja1 = new Profiili("alice");
        pelaaja2 = new Profiili("eve");
        
        Kastaaja.luo_lista_suosikeista(hahmo1, pelaajat);
        Kastaaja.luo_lista_suosikeista(hahmo2, pelaajat);
        Kastaaja.luo_lista_suosikeista(pelaaja1, pelaajat);
        Kastaaja.luo_lista_suosikeista(pelaaja2, pelaajat);
    }
    
    
    @Test
    public void testNothing() {
        assertTrue(true);
    }
    
    @Test
    public void testKuplajarjestaminen() {
        HashMap<Profiili, Integer> preferenssit = new HashMap<>();
        preferenssit.put(hahmo1, 10);
        preferenssit.put(hahmo2, 6);
        Kastaaja.kuplajarjestaminen(pelaaja1, hahmot, preferenssit);
        List<Profiili> testilista = new ArrayList<Profiili>();
        testilista.add(hahmo2);
        testilista.add(hahmo1);
        assertEquals("hahmojen järjestys on", pelaaja1.getSuosikit(), testilista);
    }
    
//    
//    int[] arr = {4, 3, 2, 1};
//        StaticRMQ rmq = new StaticRMQ(arr);
//        assertEquals("Wrong minimum for whole array", 1, rmq.query(0, 3));
//assertEquals("Wrong minimum for first element", 4, rmq.query(0, 0));
}
