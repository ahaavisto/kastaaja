package ahaavisto.kastaaja;

import static ahaavisto.kastaaja.Algoritmi.algoritminYdin;
import static ahaavisto.kastaaja.Algoritmi.luoHahmotJaPelaajat;
import static ahaavisto.kastaaja.Kastaaja.onkoTiedostotValittu;
import java.io.File;
import java.util.Random;

/**
 * Algoritmin toimintaa testaavia/arvioivia metodeita
 * @author arkkis
 */
public class Arviointi {
    
    /**
     * Laskee montako samaa alkiota annetuissa taulukoissa on
     * @param eka
     * @param toka
     * @return summa, montako alkioista oli samoja
     */
    public static int montakoSamaa(Object[] eka, Object[] toka) {
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
            onkoVastaavaa = false;
        }
        return samoja;
    }
    
    /**
     * Fisher-Yatesin algoritmi profiililistan sekoittamiseen testaamista varten,
     * jotta voidaan testata, kuinka paljon tulokseen vaikuttaa, missä järjestyksessä syötteen profiilit ovat
     * @param profiilit
     * @return profiilit sekoitetussa järjestyksessä
     */
    public static Lista<Profiili> sekoitaProfiilienJarjestys(Lista<Profiili> profiilit) {
        Random random = new Random();        
        for (int i = 0; i < profiilit.size()-2; i++) {
            int j = random.nextInt(profiilit.size()-i) + i;
            profiilit.swap(i, j);
        }
        return profiilit;
    }
    
    /**
     * Laskee, montako hahmoista ja pelaajista sai lopulta ykkössuosikkinsa.
     * Tulostaa parit, joissa sekä hahmo että pelaaja saivat ykkössuosikkinsa, eli maksimi-optimaaliset parit
     * @param hahmot
     * @param hahmotMuokkaamaton hahmot-lista jota ei ole algoritmia ajettaessa muokattu; tällä hetkellä tarvitaan tässä erikseen
     * @param pelaajat
     * @return taulukko, jossa ensimmäisenä kuinka moni hahmoista sai ykkösvaihtoehtonsa ja toisena pelaajista sama
     */
    public int[] laskeYkkosvaihtoehdot(Lista<Profiili> hahmot, Lista<Profiili> hahmotMuokkaamaton, Lista<Profiili> pelaajat) {
        int ykkosvaihtoehtoja = 0;
        int pelaajienYkkosvaihtoehtoja = 0;
        for (int i = 0; i < hahmot.size(); i++) {
            Profiili lopullinenKihlattu = hahmot.get(i).getKihlattu();
            Profiili ykkosvaihtoehto = hahmotMuokkaamaton.get(i).getSuosikit().get(0);
            Profiili pelaaja = lopullinenKihlattu;
            Profiili pelaajanYkkosvaihtoehto = Algoritmi.luoListaSuosikeista(pelaaja, hahmot).get(0);
            int molemmatSaaneetParhaan = 0;
            if (lopullinenKihlattu.equals(ykkosvaihtoehto)) {
                ykkosvaihtoehtoja++;
                molemmatSaaneetParhaan++;
            }
            if (pelaajanYkkosvaihtoehto.equals(hahmot.get(i))) {
                pelaajienYkkosvaihtoehtoja++;
                molemmatSaaneetParhaan++;
            }
            if (molemmatSaaneetParhaan == 2) {
                System.out.println("Molemminpuolisia ykkösvaihtoehtoja ovat: " + hahmot.get(i) + " + " + pelaaja);
            }
        }
        int[] summat = {ykkosvaihtoehtoja, pelaajienYkkosvaihtoehtoja};
        return summat;
    }
    
    /**
     * Tutkitaan, kuinka moni hahmoista ja pelaajista saa ykkösvaihtoehtonsa lopullisessa kastauksessa
     * @param hahmotiedosto
     * @param pelaajatiedosto 
     */
    public void testaaKuinkaMoniSaaSuosikkinsa(File hahmotiedosto, File pelaajatiedosto) {
        System.out.println("KUINKA MONI SAA YKKÖSVAIHTOEHTONSA");
        Lista<Profiili> hahmot = Algoritmi.luoHahmotJaPelaajat(hahmotiedosto, pelaajatiedosto);
        Lista<Profiili> hahmotMuokkaamaton = Algoritmi.luoHahmotJaPelaajat(hahmotiedosto, pelaajatiedosto);
        Lista<Profiili> pelaajat = hahmot.get(0).getSuosikit();
        Algoritmi.algoritminYdin(hahmot);
        
        int[] ykkosvaihtoehdot = laskeYkkosvaihtoehdot(hahmot, hahmotMuokkaamaton, pelaajat);
        
        System.out.println("Alkuperäisen ykkösensä hahmoista sai:" + ykkosvaihtoehdot[0]);
        System.out.println("Alkuperäisen ykkösensä pelaajista sai:" + ykkosvaihtoehdot[1]);
    }
    
    /**
     * Suoritetaan algoritmi sekä niin, että hahmot kosivat pelaajia että toisin päin,
     * ja vertaillaan, kuinka moni syntyvistä pareista on sama
     * @param hahmotiedosto 
     * @param pelaajatiedosto 
     */
    public void testaaEriEtusijaisuudella(File hahmotiedosto, File pelaajatiedosto) {
        Lista<Profiili> hahmotKosijoina = Algoritmi.luoHahmotJaPelaajat(hahmotiedosto, pelaajatiedosto);
        Algoritmi.algoritminYdin(hahmotKosijoina);

        Lista<Profiili> pelaajatKosijoina = Algoritmi.luoHahmotJaPelaajat(pelaajatiedosto, hahmotiedosto);
        Algoritmi.algoritminYdin(pelaajatKosijoina);       

        String[] eka = luoListaPareistaVertailuun(hahmotKosijoina, true);
        String[] toka = luoListaPareistaVertailuun(pelaajatKosijoina, false); //järjestys eri kuin muissa, jotta järjestys hahmo+pelaaja
        System.out.println("samoja pareja kun pelaajat kosijoina hahmojen sijaan: " + montakoSamaa(toka, eka));       
    }
    
    /**
     * Luodaan taulukko, jossa tekstimuodossa kaikki aiemmin luodut parit
     * @param lista
     * @param jarjestys ensin käsitelty profiili ja sitten sen kihlattu (true), vai toisin päin, jota tarvitaan kun
     * verrataan tulosta, jos kosijana toimivatkin pelaajat hahmojen sijaan
     * @return 
     */
    public String[] luoListaPareistaVertailuun(Lista<Profiili> lista, boolean jarjestys) {
        String[] taulukko = new String[lista.size()];        
        for (int i = 0; i < lista.size(); i++) {
            Profiili profiili = lista.get(i);
            if (jarjestys) {
                taulukko[i] = profiili + "+" + profiili.getKihlattu();
            } else {
                taulukko[i] = profiili.getKihlattu() + "+" + profiili;
            }           
        } 
        return taulukko;
    }
    
    /**
     * Tutkitaan, kuinka paljon kastauksen lopputulokseen vaikuttaa profiilien järjestys lähdetiedostoissa
     * @param hahmotied
     * @param pelaajatied 
     */
    public void testaaProfiilienEriJarjestyksilla(File hahmotied, File pelaajatied) {
        //ekana lähdetiedostossa oleva oletusjärjestys
        Lista<Profiili> hahmot = Algoritmi.luoHahmotJaPelaajat(hahmotied, pelaajatied);
        Algoritmi.algoritminYdin(hahmot);

        Lista<Profiili> hahmotSekoitettuna = Algoritmi.lueData(new Lista<>(), hahmotied);
        Lista<Profiili> pelaajatSekoitettuna = Algoritmi.lueData(new Lista<>(), pelaajatied);       
        
        hahmotSekoitettuna = sekoitaProfiilienJarjestys(hahmotSekoitettuna);
        pelaajatSekoitettuna = sekoitaProfiilienJarjestys(pelaajatSekoitettuna);

        for (int i = 0; i < hahmotSekoitettuna.size(); i++) {
            Algoritmi.luoListaSuosikeista(hahmotSekoitettuna.get(i), pelaajatSekoitettuna);
        }
        for (int i = 0; i < pelaajatSekoitettuna.size(); i++) {
            Algoritmi.luoListaSuosikeista(pelaajatSekoitettuna.get(i), hahmotSekoitettuna);
        }

        Algoritmi.algoritminYdin(hahmotSekoitettuna);
        
        String[] eka = luoListaPareistaVertailuun(hahmot, true);
        String[] toka = luoListaPareistaVertailuun(hahmotSekoitettuna, true);
        System.out.println("samoja pareja oli kahta profiilien järjestystä verratessa: " + montakoSamaa(toka, eka));
    }
    
    /**
     * Testaa, kauanko suoritukseen menee eri pituisilla syötteillä.
     */
    public static void testaaSuoritusaikaa() {
        System.out.println("SUORITUSAIKA ERI PITUISILLA SYÖTTEILLÄ");
        if (onkoTiedostotValittu()) {
            for (int i = 0; i < 10; i++) {
                testaaAika("10+10", new File("assets/hahmot.csv"), new File("assets/pelaajat.csv"));
            }
        }        
        if (onkoTiedostotValittu()) {
            for (int i = 0; i < 10; i++) {            
                testaaAika("100+100", new File("assets/hahmot100.csv"), new File("assets/pelaajat100.csv"));
            }
        } 
        for (int i = 0; i < 10; i++) {
            if (onkoTiedostotValittu()) {
                testaaAika("1000+1000", new File("assets/hahmot1000.csv"), new File("assets/pelaajat1000.csv"));
            }
        }         
    }
    
    /**
     * Ajaa algoritmin ja kertoo ajan, kauanko siinä kesti
     * @param syotteenKoko
     * @param hahmotiedosto
     * @param pelaajatiedosto 
     */
    public static void testaaAika(String syotteenKoko, File hahmotiedosto, File pelaajatiedosto) {
        Lista<Profiili> hahmot = luoHahmotJaPelaajat(hahmotiedosto, pelaajatiedosto);
        long alku = System.nanoTime();
        algoritminYdin(hahmot);
        long loppu = System.nanoTime();
        long erotus = (loppu-alku);
        double erotusMillisekunteina = (double)erotus/1000000;
        System.out.println("Syötteen koko oli "+ syotteenKoko + ": " + erotusMillisekunteina + " ms");
    }
    
}
