
import ahaavisto.kastaaja.Arviointi;
import ahaavisto.kastaaja.Profiili;
import ahaavisto.kastaaja.Lista;
import org.junit.Test;
import static org.junit.Assert.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author arkkis
 */
public class TestArviointi {
    
    @Test
    public void testMontakoSamaa () {
        String[] taul = {"asdf", "qwerty"};
        String[] onSamoja = {"qwerty", "zxcv"};
        String[] eiSamoja = {"a", "s", "d", "f"};
        assertEquals("Montako samaa", 1, Arviointi.montakoSamaa(taul, onSamoja));
        assertEquals("Montako samaa", 0, Arviointi.montakoSamaa(taul, eiSamoja));
    }
    
    @Test
    public void testSekoitaProfiilienJarjestys() {
        Profiili eka = new Profiili("Alice");
        Profiili toka = new Profiili("Bob");
        Lista<Profiili> profiilit = new Lista<>();
        profiilit.add(eka);
        profiilit.add(toka);
        Lista<Profiili> sekoitettu = Arviointi.sekoitaProfiilienJarjestys(profiilit);
        assertEquals("Sekoitetulta listalta löytyy alkuperäiset alkiot", true, sekoitettu.contains(eka));
        assertEquals("Sekoitetulta listalta löytyy alkuperäiset alkiot", true, sekoitettu.contains(toka));
        
    }
}
