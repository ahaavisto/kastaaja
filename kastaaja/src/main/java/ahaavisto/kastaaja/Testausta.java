package ahaavisto.kastaaja;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static ahaavisto.kastaaja.Algoritmi.algoritminYdin;
import static ahaavisto.kastaaja.Algoritmi.luoHahmotJaPelaajat;
import static ahaavisto.kastaaja.Kastaaja.hahmotiedosto;
import static ahaavisto.kastaaja.Kastaaja.onkoTiedostotValittu;
import static ahaavisto.kastaaja.Kastaaja.pelaajatiedosto;
import java.io.File;
import java.util.Random;

/**
 *
 * @author arkkis
 */
public class Testausta {
    
    public int vertaa(Object[] eka, Object[] toka) {
        int samoja = 0;
        boolean onkoVastaavaa = false;
        for(Object o: eka) {
            for(Object verrattava: toka) {
                if (o.equals(verrattava)) {
                    samoja++;
                    onkoVastaavaa = true;
                    break;
                }
            }
            if (!onkoVastaavaa) {
                System.out.println(o);
            }
            onkoVastaavaa = false;
        }
        return samoja;
    }
    
    /**
     * Fisher-Yatesin algoritmi profiililistan sekoittamiseen testaamista varten,
     * jotta voidaan testata, vaikuttaako tulokseen, missä järjestyksessä syötteen profiilit ovat
     * @param profiilit
     * @return 
     */
    public static Lista<Profiili> sekoitaProfiilienJarjestys(Lista<Profiili> profiilit) {
        Random random = new Random();
        
        for (int i = 0; i < profiilit.size()-2; i++) {
            int j = random.nextInt(profiilit.size()-i) + i;
            profiilit.swap(i, j);
        }
        return profiilit;
    }
    
    public void testaaProfiilienEriJarjestyksilla() {
        //ekana tiedostossa oleva oletusjärjestys
        Lista<Profiili> hahmot = Algoritmi.luoHahmotJaPelaajat();
        Algoritmi.algoritminYdin(hahmot);
        //System.out.println(Kastaaja.tulostaParit(hahmot));
        
        
        //satunnainen järjestys; ei vielä toimi, koska sekoitus tapahtuu vasta ennen tulostusta oleellisesti
        Lista<Profiili> hahmot2 = Algoritmi.lueData(new Lista<>(), new File("assets/hahmot.csv"));
        Lista<Profiili> pelaajat2 = Algoritmi.lueData(new Lista<>(), new File("assets/pelaajat.csv"));       
        
        hahmot2 = sekoitaProfiilienJarjestys(hahmot2);
        pelaajat2 = sekoitaProfiilienJarjestys(pelaajat2);

        for (int i = 0; i < hahmot2.size(); i++) {
            Algoritmi.luoListaSuosikeista(hahmot2.get(i), pelaajat2);
        }
        for (int i = 0; i < pelaajat2.size(); i++) {
            Algoritmi.luoListaSuosikeista(pelaajat2.get(i), hahmot2);
        }

        Algoritmi.algoritminYdin(hahmot2);
        //System.out.println(Kastaaja.tulostaParit(hahmot2));

        String[] eka = new String[hahmot.size()];
        for (int i = 0; i < hahmot.size(); i++) {
            Profiili hahmo = hahmot.get(i);
            eka[i] = hahmo + "+" + hahmo.getKihlattu();
        }

        String[] toka = new String[hahmot2.size()];
        for (int i = 0; i < hahmot2.size(); i++) {
            Profiili hahmo = hahmot2.get(i);
            toka[i] = hahmo + "+" + hahmo.getKihlattu();
        }       

        System.out.println("samoja pareja oli: " + vertaa(toka, eka));
        
        
    }
    
    public static void testaaSuorituskykya() {
        
        hahmotiedosto = new File("assets/hahmot.csv");
        pelaajatiedosto = new File("assets/pelaajat.csv");
        for (int i = 0; i < 10; i++) {
            if (onkoTiedostotValittu()) {
                testaaAika("10+10");
            }
        }
        
        hahmotiedosto = new File("assets/hahmot100.csv");
        pelaajatiedosto = new File("assets/pelaajat100.csv");
        for (int i = 0; i < 10; i++) {
            if (onkoTiedostotValittu()) {
                testaaAika("100+100");
            }
        } 
        
        hahmotiedosto = new File("assets/hahmot1000.csv");
        pelaajatiedosto = new File("assets/pelaajat1000.csv");
        for (int i = 0; i < 10; i++) {
            if (onkoTiedostotValittu()) {
                testaaAika("1000+1000");
            }
        } 
        
    }
    
    public static void testaaAika(String syotteenKoko) {
        Lista<Profiili> hahmot = luoHahmotJaPelaajat();
        long alku = System.nanoTime();
        algoritminYdin(hahmot);
        long loppu = System.nanoTime();
        long erotus = (loppu-alku);
        double erotusMillisekunteina = (double)erotus/1000000;
        System.out.println("aikaa kului, kun syötteen koko oli "+ syotteenKoko + ": " + erotusMillisekunteina + " ms");
    }
    
}
