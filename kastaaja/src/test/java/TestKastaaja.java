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
        pelaajat.add(pelaaja1);
        pelaaja2 = new Profiili("eve");
        pelaajat.add(pelaaja2);
        
        hahmo1.setSuosikit(pelaajat);
        hahmo2.setSuosikit(pelaajat);
        pelaaja1.setSuosikit(hahmot);
        pelaaja2.setSuosikit(hahmot);
        
        /*Kastaaja.luo_lista_suosikeista(hahmo1, pelaajat);
        Kastaaja.luo_lista_suosikeista(hahmo2, pelaajat);
        Kastaaja.luo_lista_suosikeista(pelaaja1, pelaajat);
        Kastaaja.luo_lista_suosikeista(pelaaja2, pelaajat);
        */
    }
    
    
    @Test
    public void testOnko_isompi() {
        HashMap<Profiili, Integer> suosikit = new HashMap<>();
        suosikit.put(hahmo1, 1);
        suosikit.put(hahmo2, 2);        
        assertEquals("Hahmojen järjestys", Kastaaja.onko_isompi(hahmo1, hahmo2, suosikit), false);
    }
    
    @Test
    public void testKuplajarjestaminen() {
        luo_profiilit();
        HashMap<Profiili, Integer> preferenssit = new HashMap<>();
        preferenssit.put(hahmo1, 1);
        preferenssit.put(hahmo2, 2);
        Kastaaja.kuplajarjestaminen(pelaaja1, hahmot, preferenssit);
        List<Profiili> testilista = new ArrayList<>();
        testilista.add(hahmo1);
        testilista.add(hahmo2);
        assertEquals("hahmojen järjestys on", testilista, pelaaja1.getSuosikit());
    }
    
    @Test
    public void testPoistetaanTurhatToiveet() {
        luo_profiilit();
        ArrayList<Profiili> hylatyt = new ArrayList<>();
        hylatyt.add(pelaaja1);        
        Kastaaja.poistetaanTurhatToiveet(hylatyt, hahmo1);
        assertTrue("Poistamaton yhä listalla", pelaaja1.getSuosikit().contains(hahmo2));
        assertFalse("Poistettu ei ole enää listalla", pelaaja1.getSuosikit().contains(hahmo1));
    }
    
    @Test
    public void testPoistetaanHuonommatKuinNykyinen() {
        luo_profiilit();
        Kastaaja.poistetaanHuonommatKuinNykyinen(hahmo1, pelaaja1);
        assertTrue("Poistamaton yhä listalla", hahmo1.getSuosikit().contains(pelaaja1));
        assertFalse("Poistettu ei ole enää listalla", hahmo1.getSuosikit().contains(pelaaja2));
    }
    
    
//    
//    int[] arr = {4, 3, 2, 1};
//        StaticRMQ rmq = new StaticRMQ(arr);
//        assertEquals("Wrong minimum for whole array", 1, rmq.query(0, 3));
//assertEquals("Wrong minimum for first element", 4, rmq.query(0, 0));
}
