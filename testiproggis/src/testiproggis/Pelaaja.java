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
public class Pelaaja {
    int[] statsit;
    boolean vapaa = true;
    Hahmo kihlattu = null;
    Hahmo suosikit[];
    
    public Pelaaja() {
        this.statsit = new int[10];
        Random rand = new Random(); 
        for (int i=0; i < 10; i++) {
            statsit[i] = rand.nextInt(5); 
        }
    }
}
