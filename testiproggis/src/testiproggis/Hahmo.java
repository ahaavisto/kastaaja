/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testiproggis;
import java.util.HashMap;
import java.util.Random; 

/**
 *
 * @author arkkis
 */
public class Hahmo {
    int[] statsit;
    boolean vapaa = true;
    Pelaaja kihlattu = null;
    Pelaaja suosikit[];
    
    public Hahmo() {
        this.statsit = new int[10];
        Random rand = new Random(); 
        for (int i=0; i < 10; i++) {
            statsit[i] = rand.nextInt(5); 
        }

    }
}
