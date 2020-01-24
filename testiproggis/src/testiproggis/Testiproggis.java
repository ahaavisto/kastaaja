/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testiproggis;

import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.HashMap;
import java.util.TreeSet;

/**
 *
 * @author arkkis
 */
public class Testiproggis {

    /**
     * 
     * @param eka profiili jonka sopivuutta verrataan tokaan
     * @param toka profiili johon ekan sopivuutta verrataan
     * @param suosikit map joka mäppää ekan profiilin mätsäävyysarvon kuhunkin profiiliin
     * @return 
     */
    public static boolean onko_isompi(Profiili eka, Profiili toka, HashMap<Profiili, Integer> suosikit) {
        int ekan_arvo = suosikit.get(eka);
        int tokan_arvo = suosikit.get(toka);
        if (ekan_arvo > tokan_arvo) {
            return true;
        }
        return false;
    }
    
    public static void laske_erotukset(Profiili profiili, Profiili verrattavat[]) {
        HashMap<Profiili, Integer> preferenssit = new HashMap<Profiili, Integer>();
            for (Profiili verrattava: verrattavat) {
                int erotus = 0;
                for (int i = 0; i < 10; i++) {
                    erotus += Math.abs(profiili.statsit[i] - verrattava.statsit[i]);
                }
                preferenssit.put(verrattava, erotus);
            }
            
            Profiili suosikit[] = new Profiili[10];
            int j = 0;
            for (Profiili h : preferenssit.keySet()) { //täytetään taulukko jossain järkässä
                suosikit[j] = h;
                j++;
            }
            
            kuplajarjestaminen(profiili, suosikit, preferenssit);
    }
    
    public static void kuplajarjestaminen(Profiili profiili, Profiili suosikit[], HashMap<Profiili, Integer> preferenssit) {
        boolean eiValmis = true;
            while (eiValmis) {
                eiValmis = false;
                for (int i = 0; i < suosikit.length - 1; i++) {
                    if (onko_isompi(suosikit[i], suosikit[i + 1], preferenssit)) {
                        Profiili vaihto = suosikit[i];
                        suosikit[i] = suosikit[i + 1];
                        suosikit[i + 1] = vaihto;
                        eiValmis = true;
                    }
                }
            }
            profiili.suosikit = suosikit;
    }
    
    public static void main(String[] args) {
    
        System.out.println("hello world");
        
        Profiili Hahmot[] = new Profiili[10];
        Profiili Pelaajat[] = new Profiili[10];
        for (int i = 0; i < 10; i++) {
            Hahmot[i] = new Profiili();
            Pelaajat[i] = new Profiili();
        }
        
        for (Profiili hahmo: Hahmot) {
            laske_erotukset(hahmo, Pelaajat);
        }
        
        for (Profiili pelaaja: Pelaajat) {
            laske_erotukset(pelaaja, Hahmot);
        }
        
        
        //le algo
        for (Profiili kosija: Hahmot) { //väliaikainen pysähtymisehto, kaikki kerran läpi
            Profiili kosittava = kosija.suosikit[kosija.suosikit.length-1];
            kosittava.vapaa = false;
            kosittava.kihlattu = kosija;
            kosija.vapaa = false;
            kosija.kihlattu = kosittava;
                
        }
                
        /* Wikipediasta algon kuvaus:
        
        while (some man m is free) do
	begin
		w := first woman on m’s list;
		m proposes, and becomes engaged, to w;
		if (some man m' is engaged to w) then
		    assign m' to be free;
		for each (successor m'' of m on w’s list) do
			delete the pair (m'', w)
        */

        
        

        
        /*
        try
        { 
            //ei toimi koska ??, luonnostelua tiedostonluvusta
            for(Scanner sc = new Scanner(new File("hahmot.csv")); sc.hasNext(); ) {
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
