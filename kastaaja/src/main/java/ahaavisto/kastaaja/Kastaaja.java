/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahaavisto.kastaaja;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author arkkis
 */
public class Kastaaja {

    static ArrayList<Profiili> vapaat = new ArrayList<>();

    /**
     *
     * @param eka profiili jonka sopivuutta verrataan tokaan
     * @param toka profiili johon ekan sopivuutta verrataan
     * @param suosikit map joka mäppää ekan profiilin mätsäävyysarvon kuhunkin
     * profiiliin
     * @return vastaus metodin nimen esittämään kysymykseen
     */
    public static boolean onko_isompi(Profiili eka, Profiili toka, HashMap<Profiili, Integer> suosikit) {
        int ekan_arvo = suosikit.get(eka);
        int tokan_arvo = suosikit.get(toka);
        if (ekan_arvo > tokan_arvo) {
            return true;
        }
        return false;
    }

    /**
     * Luodaan kyseiselle profiilille (eli hahmolle/pelaajalle) lista, missä
     * järjestyksessä se preferoi toisen profiililuokan profiileita
     *
     * @param profiili hahmo/pelaaja jolle luodaan preferenssilistaa
     * @param verrattavat ne pelaajat/hahmot jotka halutaan järjestää
     */
    public static void luo_lista_suosikeista(Profiili profiili, ArrayList<Profiili> verrattavat) {
        HashMap<Profiili, Integer> preferenssit = new HashMap<>();
        for (Profiili verrattava : verrattavat) {
            int erotus = 0;
            for (int i = 0; i < verrattavat.size(); i++) {
                erotus += Math.abs(profiili.statsit[i] - verrattava.statsit[i]);
            }
            preferenssit.put(verrattava, erotus);
        }
        ArrayList<Profiili> tulevat_suosikit = new ArrayList(verrattavat);//verrattavat
        kuplajarjestaminen(profiili, tulevat_suosikit, preferenssit);
    }

    /**
     * Luodaan preferenssijärjestys sen perusteella, kuinka paljon kunkin
     * ominaisuuden abs(hahmo-pelaaja) erotusten yhteissumma on, ensimmäisenä paras vaihtoehto
     * 
    * @param profiili se jonka suosikit halutaan tallentaa järjestetyksi
     * taulukoksi
     * @param suosikit toistaiseksi järjestämätön taulukko toisen profiililuokan
     * profiileista
     * @param preferenssit map jossa tallessa tieto käsiteltävän profiilin
     * preferensseista toisen profiililuokan profiileihin
     */
    public static void kuplajarjestaminen(Profiili profiili, ArrayList<Profiili> suosikit, HashMap<Profiili, Integer> preferenssit) {
        boolean eiValmis = true;
        while (eiValmis) {
            eiValmis = false;
            for (int i = 0; i < suosikit.size() - 1; i++) {
                if (onko_isompi(suosikit.get(i), suosikit.get(i + 1), preferenssit)) {
                    Profiili vaihto = suosikit.get(i);
                    suosikit.remove(i);
                    suosikit.add(i+1, vaihto); // laitetaan indeksistä i tallennettu seuraavaan indeksiin
                    eiValmis = true;
                }
            }
        }
        profiili.suosikit = suosikit;
    }

    /**
     * funktio yhdistää parametrina saamansa profiilit, ja päivittää muiden
     * profiilien tarvittavat tiedot, eli purkaa mahdollisen edellisen
     * kihlauksen ja poistaa suosikkilistoista ne vaihtoehdot, jotka eivät tule
     * enää kysymykseen
     *
     * @param kosija profiili joka yhdistetään kosittavaan profiiliin
     * @param kosittava
     */
    public static void vaihda_kihlaus(Profiili kosija, Profiili kosittava) {
        System.out.println(kosittava.nimi);
        if (kosittava.kihlattu != null) { //puretaan vanha kihlaus
            Profiili ex = kosittava.kihlattu;
            ex.kihlattu = null;
            vapaat.add(ex);
        }
        kosittava.kihlattu = kosija;
        kosija.kihlattu = kosittava;

        //poistetaan kositun suosikeista nykyistä kihlattua huonommat vaihtoehdot
        //System.out.println("kosittavan suosikit aluksi, koko: " + kosittava.suosikit.size());
        
        List<Profiili> hylatyt_suosikit = new ArrayList<>();
        for (int i = 0; i < kosittava.suosikit.size(); i++) {
            if (kosittava.suosikit.get(i) == kosija) {
                if (i + 1 < kosittava.suosikit.size() - 1) {
                    hylatyt_suosikit = kosittava.suosikit.subList(i+1, kosittava.suosikit.size());
                }                            
                
                kosittava.suosikit = kosittava.suosikit.subList(0, i+1); //säästetään vain kihlattu ja paremmat        

                break;
            }
        }

        //poistetaan hylättyjen kosijoiden suosikkilistoilta äsken kihlattu
        for (Profiili hylatty_hahmokandidaatti : hylatyt_suosikit) {
            //System.out.println("--"+hylatty_hahmokandidaatti.nimi);

            for (int i = 0; i < hylatty_hahmokandidaatti.suosikit.size(); i++) {
                // System.out.println("\t"+hylatty_hahmokandidaatti.suosikit.get(i).nimi);
                if (hylatty_hahmokandidaatti.suosikit.get(i).nimi.equals(kosittava.nimi)) {
                    //System.out.println("poistettu pariskunta " + hylatty_hahmokandidaatti.nimi + " + " + kosittava.nimi);
                    hylatty_hahmokandidaatti.suosikit.remove(i);
                }
            }
        }
    }
    
    public static void tulosta_parit (List<Profiili> hahmot) {
        System.out.println("PARISKUNNAT ATM:");
        for (Profiili hahmo: hahmot) {
            if (hahmo.kihlattu != null) {
            System.out.println(hahmo.nimi + " + " + hahmo.kihlattu.nimi);
            } else {
                System.out.println(hahmo.nimi + " 4everalone");
            }
        }
    }

    public static void main(String[] args) {

        //System.out.println("Hei, olen kastaaja");

        //luodaan hahmot ja pelaajat:
        ArrayList<Profiili> hahmot = new ArrayList<>();
        ArrayList<Profiili> pelaajat = new ArrayList<>();
        String[] miehen_nimia = {"aapo", "juhani", "eero", "simeoni", "timo", "tuomas", "lauri", "uolevi", "minttupetteri", "pena"};
        String[] naisen_nimia = {"aava", "bea", "cecilia", "daniela", "eeva", "fia", "gina", "hanna", "iina", "julia"};
        for (int i = 0; i < 10; i++) {
            hahmot.add(new Profiili(miehen_nimia[i]));
            pelaajat.add(new Profiili(naisen_nimia[i]));
        }

        for (Profiili hahmo : hahmot) {
            luo_lista_suosikeista(hahmo, pelaajat);
        }

        for (Profiili pelaaja : pelaajat) {
            luo_lista_suosikeista(pelaaja, hahmot);
        }

        //varsinainen algoritmi       
        vapaat.addAll(hahmot);

        while (vapaat.size() > 1) {
        //for (int i = 0; i < 10; i++) {
            Profiili kosija = vapaat.get(0);
            vapaat.remove(0); //poistetaan kosiomatkalle lähtijä
            Profiili kosittava = kosija.suosikit.get(kosija.suosikit.size() - 1);
            vaihda_kihlaus(kosija, kosittava);
            //System.out.println(kosija.nimi + "+" + kosittava.nimi + " vapaita vielä: " + vapaat.size());
            
            tulosta_parit(hahmot);
        }

        

        /* Wikipediasta algon kuvaus muistiinpanoiksi:
        
        while (some man m is free) do
	begin
		w := first woman on m’s list;
		m proposes, and becomes engaged, to w;
		if (some man m' is engaged to w) then
		    assign m' to be free;
		for each (successor m'' of m on w’s list) do
			delete the pair (m'', w)
         */
        //tiedostonluvun basic versio, käytetään myöhemmin
        /*
        
        try
        { 
           
            for(Scanner sc = new Scanner(new File("assets/hahmot.csv")); sc.hasNext(); ) {
              String line = sc.nextLine();
              System.out.println(line);
            } 
        } 
        catch(Exception ex) 
        { 
           System.out.println("Exception is occured");  
           ex.printStackTrace();
        }
         */
    }
}
