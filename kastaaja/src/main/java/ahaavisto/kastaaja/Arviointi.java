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
public class Arviointi {
    
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
    
    public int getPelaajanIndeksi(Profiili pelaaja, Lista<Profiili> pelaajat) {
        for (int i = 0; i < pelaajat.size(); i++) {
            if (pelaajat.get(i).equals(pelaaja)) {
                return i;
            }
        }
        return -1;
    }
    
    public int[] laskeYkkosvaihtoehdot(Lista<Profiili> profiilit, Lista<Profiili> verrattavat, Lista<Profiili> pelaajat) {
        int ykkosvaihtoehtoja = 0;
        int pelaajienYkkosvaihtoehtoja = 0;
        for (int i = 0; i < profiilit.size(); i++) {
            Profiili lopullinenKihlattu = profiilit.get(i).getKihlattu();
            Profiili ykkosvaihtoehto = verrattavat.get(i).getSuosikit().get(0);
            Profiili pelaaja = pelaajat.get(getPelaajanIndeksi(lopullinenKihlattu, pelaajat));
            Profiili pelaajanYkkosvaihtoehto = Algoritmi.luoListaSuosikeista(pelaaja, profiilit).get(0);
            int molemmatSaaneetParhaan = 0;
            if (lopullinenKihlattu.equals(ykkosvaihtoehto)) {
                ykkosvaihtoehtoja++;
                molemmatSaaneetParhaan++;
            }
            if (pelaajanYkkosvaihtoehto.equals(profiilit.get(i))) {
                pelaajienYkkosvaihtoehtoja++;
                molemmatSaaneetParhaan++;
            }
            if (molemmatSaaneetParhaan == 2) {
                System.out.println("Molemminpuolisia ykkösvaihtoehtoja ovat: " + profiilit.get(i) + " + " + pelaaja);
            }
        }
        int[] summat = {ykkosvaihtoehtoja, pelaajienYkkosvaihtoehtoja};
        return summat;
    }
    
    
    public void testaaKuinkaMoniSaaSuosikkinsa(File ekaFilu, File tokaFilu) {
        Lista<Profiili> hahmot = Algoritmi.luoHahmotJaPelaajat(ekaFilu, tokaFilu);
        Algoritmi.algoritminYdin(hahmot);
        Lista<Profiili> hahmot2 = Algoritmi.luoHahmotJaPelaajat(ekaFilu, tokaFilu);
        
        Lista<Profiili> pelaajat = hahmot2.get(0).getSuosikit();
        int[] ykkosvaihtoehdot = laskeYkkosvaihtoehdot(hahmot, hahmot2, pelaajat);
        
        System.out.println("Alkuperäisen ykkösensä hahmoista sai:" + ykkosvaihtoehdot[0]);
        System.out.println("Alkuperäisen ykkösensä pelaajista sai:" + ykkosvaihtoehdot[1]);
    }
    
    public void testaaEriEtusijaisuudella(File ekaFilu, File tokaFilu) {
        Lista<Profiili> hahmot = Algoritmi.luoHahmotJaPelaajat(ekaFilu, tokaFilu);
        Algoritmi.algoritminYdin(hahmot);

        Lista<Profiili> hahmot2 = Algoritmi.luoHahmotJaPelaajat(tokaFilu, ekaFilu);
        Algoritmi.algoritminYdin(hahmot2);       

        String[] eka = new String[hahmot.size()];
        for (int i = 0; i < hahmot.size(); i++) {
            Profiili hahmo = hahmot.get(i);
            eka[i] = hahmo + "+" + hahmo.getKihlattu();
        }

        String[] toka = new String[hahmot2.size()];
        for (int i = 0; i < hahmot2.size(); i++) {
            Profiili hahmo = hahmot2.get(i);
            toka[i] = hahmo.getKihlattu() + "+" + hahmo;
        }       

        System.out.println("samoja pareja oli: " + vertaa(toka, eka));
        
        
    }
    
    public void testaaProfiilienEriJarjestyksilla(File hahmotied, File pelaajatied) {
        //ekana tiedostossa oleva oletusjärjestys
        Lista<Profiili> hahmot = Algoritmi.luoHahmotJaPelaajat(hahmotied, pelaajatied);
        Algoritmi.algoritminYdin(hahmot);

        Lista<Profiili> hahmot2 = Algoritmi.lueData(new Lista<>(), hahmotied);
        Lista<Profiili> pelaajat2 = Algoritmi.lueData(new Lista<>(), pelaajatied);       
        
        hahmot2 = sekoitaProfiilienJarjestys(hahmot2);
        pelaajat2 = sekoitaProfiilienJarjestys(pelaajat2);

        for (int i = 0; i < hahmot2.size(); i++) {
            Algoritmi.luoListaSuosikeista(hahmot2.get(i), pelaajat2);
        }
        for (int i = 0; i < pelaajat2.size(); i++) {
            Algoritmi.luoListaSuosikeista(pelaajat2.get(i), hahmot2);
        }

        Algoritmi.algoritminYdin(hahmot2);

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
                testaaAika("10+10", hahmotiedosto, pelaajatiedosto);
            }
        }
        
        hahmotiedosto = new File("assets/hahmot100.csv");
        pelaajatiedosto = new File("assets/pelaajat100.csv");
        for (int i = 0; i < 10; i++) {
            if (onkoTiedostotValittu()) {
                testaaAika("100+100", hahmotiedosto, pelaajatiedosto);
            }
        } 
        
        hahmotiedosto = new File("assets/hahmot1000.csv");
        pelaajatiedosto = new File("assets/pelaajat1000.csv");
        for (int i = 0; i < 10; i++) {
            if (onkoTiedostotValittu()) {
                testaaAika("1000+1000", hahmotiedosto, pelaajatiedosto);
            }
        } 
        
    }
    
    public static void testaaAika(String syotteenKoko, File eka, File toka) {
        Lista<Profiili> hahmot = luoHahmotJaPelaajat(eka, toka);
        long alku = System.nanoTime();
        algoritminYdin(hahmot);
        long loppu = System.nanoTime();
        long erotus = (loppu-alku);
        double erotusMillisekunteina = (double)erotus/1000000;
        System.out.println("aikaa kului, kun syötteen koko oli "+ syotteenKoko + ": " + erotusMillisekunteina + " ms");
    }
    
}
