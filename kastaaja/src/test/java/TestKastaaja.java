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
import ahaavisto.kastaaja.Lista;
import java.util.HashMap;
import static org.junit.Assert.*;
import org.junit.Test;

public class TestKastaaja {
    static Profiili hahmo1, hahmo2, pelaaja1, pelaaja2;
    static Lista<Profiili> hahmot, pelaajat;
            
    static void luoProfiilit () {
        hahmot = new Lista<>();
        hahmo1 = new Profiili("bob");
        hahmot.add(hahmo1);
        hahmo2 = new Profiili("bill");
        hahmot.add(hahmo2);
        
        pelaajat = new Lista<>();
        pelaaja1 = new Profiili("alice");
        pelaajat.add(pelaaja1);
        pelaaja2 = new Profiili("eve");
        pelaajat.add(pelaaja2);
        
        hahmo1.setSuosikit(pelaajat);
        hahmo2.setSuosikit(pelaajat);
        pelaaja1.setSuosikit(hahmot);
        pelaaja2.setSuosikit(hahmot);

    }
    
    static void luoProfiilitIlmanSuosikkeja () {
        hahmot = new Lista<>();
        hahmo1 = new Profiili("bob");
        hahmot.add(hahmo1);
        hahmo2 = new Profiili("bill");
        hahmot.add(hahmo2);
        
        pelaajat = new Lista<>();
        pelaaja1 = new Profiili("alice");
        pelaajat.add(pelaaja1);
        pelaaja2 = new Profiili("eve");
        pelaajat.add(pelaaja2);
        
        int[] stats1 = {1, 1};
        hahmo1.setStatsit(stats1);
        int[] stats2 = {0, 0};
        hahmo2.setStatsit(stats2);
        int[] stats3 = {0, 0};
        pelaaja1.setStatsit(stats3);

    }
    
    @Test
    public void testLuoListaSuosikeista() {
        luoProfiilitIlmanSuosikkeja();
        Kastaaja.luo_lista_suosikeista(pelaaja1, hahmot);
        Lista<Profiili> suosikit = pelaaja1.getSuosikit();
        assertEquals("suosikkien pituus on", 2, suosikit.size());
        assertEquals("ekana suosikkina on", hahmo2, suosikit.get(0));
        assertEquals("tokana suosikkina on", hahmo1, suosikit.get(1));
    }

    @Test
    public void testKihlaus() {
        luoProfiilit();
        Kastaaja.Kihlaus(hahmo1, pelaaja1);
        assertEquals("Kihloissa hahmon kanssa", pelaaja1, hahmo1.getKihlattu());
        assertEquals("Kihloissa pelaajan kanssa", hahmo1, pelaaja1.getKihlattu());
    }
    
    @Test
    public void testKihlausEroToimii() {
        luoProfiilit();
        Kastaaja.Kihlaus(hahmo1, pelaaja1);
        Kastaaja.Kihlaus(hahmo2, pelaaja1);
        assertEquals("Kihloissa hahmon kanssa", pelaaja1, hahmo2.getKihlattu());
        assertEquals("Kihloissa pelaajan kanssa", hahmo2, pelaaja1.getKihlattu());
        assertEquals("aiempi kihlaus purkautunut", null, hahmo1.getKihlattu());
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
        luoProfiilit();
        HashMap<Profiili, Integer> preferenssit = new HashMap<>();
        preferenssit.put(hahmo1, 1);
        preferenssit.put(hahmo2, 2);
        Kastaaja.kuplajarjestaminen(pelaaja1, hahmot, preferenssit);
        Lista<Profiili> testilista = new Lista<>();
        testilista.add(hahmo1);
        testilista.add(hahmo2);
        assertEquals("hahmoista ekana on", testilista.get(0), pelaaja1.getSuosikit().get(0));
        assertEquals("hahmoista tokana on", testilista.get(1), pelaaja1.getSuosikit().get(1));
    }
    
    @Test
    public void testPoistetaanTurhatToiveet() {
        luoProfiilit();
        Lista<Profiili> hylatyt = new Lista<>();
        hylatyt.add(pelaaja1);        
        Kastaaja.poistetaanTurhatToiveet(hylatyt, hahmo1);
        assertTrue("Poistamaton yhä listalla", pelaaja1.getSuosikit().contains(hahmo2));
        assertFalse("Poistettu ei ole enää listalla", pelaaja1.getSuosikit().contains(hahmo1));
    }
    
    @Test
    public void testPoistetaanHuonommatKuinNykyinen() {
        luoProfiilit();
        Kastaaja.poistetaanHuonommatKuinNykyinen(hahmo1, pelaaja1);
        assertTrue("Poistamaton yhä listalla", hahmo1.getSuosikit().contains(pelaaja1));
        assertFalse("Poistettu ei ole enää listalla", hahmo1.getSuosikit().contains(pelaaja2));
    }
    
}
