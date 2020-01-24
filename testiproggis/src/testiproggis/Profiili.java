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
public class Profiili {
    int[] statsit;
    boolean vapaa = true;
    Profiili kihlattu = null;
    Profiili suosikit[];
    
    public Profiili() {
        this.statsit = new int[10];
        Random rand = new Random(); 
        for (int i=0; i < 10; i++) {
            statsit[i] = rand.nextInt(5); 
        }

    }
}
