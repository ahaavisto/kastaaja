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

    
    public static boolean onko_isompi(Hahmo eka, Hahmo toka, HashMap<Hahmo, Integer> suosikit) {
        int ekan_arvo = suosikit.get(eka);
        int tokan_arvo = suosikit.get(toka);
        if (ekan_arvo > tokan_arvo) {
            return true;
        }
        return false;
    }
    
    public static boolean onko_isompi_p(Pelaaja eka, Pelaaja toka, HashMap<Pelaaja, Integer> suosikit) {
        int ekan_arvo = suosikit.get(eka);
        int tokan_arvo = suosikit.get(toka);
        if (ekan_arvo > tokan_arvo) {
            return true;
        }
        return false;
    }
    
    public static void main(String[] args) {
        
        
        System.out.println("hello world");
        
        Hahmo Hahmot[] = new Hahmo[10];
        for (int i = 0; i < 10; i++) {
            Hahmot[i] = new Hahmo();
        }
        
        Pelaaja Pelaajat[] = new Pelaaja[10];
        for (int i = 0; i < 10; i++) {
            Pelaajat[i] = new Pelaaja();
        }
        
        for (Hahmo hahmo: Hahmot) {
            HashMap<Pelaaja, Integer> preferenssit = new HashMap<Pelaaja, Integer>();
            for (Pelaaja pelaaja: Pelaajat) {
                int erotus = 0;
                for (int i = 0; i < 10; i++) {
                    erotus += Math.abs(hahmo.statsit[i] - pelaaja.statsit[i]);
                }
                preferenssit.put(pelaaja, erotus);
            }
            
            Pelaaja suosikit[] = new Pelaaja[10];
            int j = 0;
            for (Pelaaja h : preferenssit.keySet()) { //täytetään taulukko jossain järkässä
                suosikit[j] = h;
                j++;
            }
            
            //kuplajärjestetään halutut hahmot
            boolean eiValmis = true;
            while (eiValmis) {
                eiValmis = false;
                for (int i = 0; i < suosikit.length - 1; i++) {
                    if (onko_isompi_p(suosikit[i], suosikit[i + 1], preferenssit)) {
                        Pelaaja vaihto = suosikit[i];
                        suosikit[i] = suosikit[i + 1];
                        suosikit[i + 1] = vaihto;
                        eiValmis = true;
                    }
                }
            }
            hahmo.suosikit = suosikit;
            
        }
        
        for (Pelaaja pelaaja: Pelaajat) {
            HashMap<Hahmo, Integer> preferenssit = new HashMap<Hahmo, Integer>();
            
            for (Hahmo hahmo: Hahmot) {
                int erotus = 0;
                for (int i = 0; i < 10; i++) {
                    erotus += Math.abs(hahmo.statsit[i] - pelaaja.statsit[i]);
                }
                preferenssit.put(hahmo, erotus);
            }
            
            Hahmo suosikit[] = new Hahmo[10];
            int j = 0;
            for (Hahmo h : preferenssit.keySet()) { //täytetään taulukko jossain järkässä
                suosikit[j] = h;
                j++;
            }
            
            //kuplajärjestetään halutut hahmot
            boolean eiValmis = true;
            while (eiValmis) {
                eiValmis = false;
                for (int i = 0; i < suosikit.length - 1; i++) {
                    if (onko_isompi(suosikit[i], suosikit[i + 1], preferenssit)) {
                        Hahmo vaihto = suosikit[i];
                        suosikit[i] = suosikit[i + 1];
                        suosikit[i + 1] = vaihto;
                        eiValmis = true;
                    }
                }
            }
            pelaaja.suosikit = suosikit;
            

        }
        
        
        //le algo
        for (Hahmo kosija: Hahmot) { //väliaikainen pysähtymisehto, kaikki kerran läpi
            Pelaaja paras = null;
            Pelaaja kosittava = kosija.suosikit[kosija.suosikit.length-1];
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
            //ei toimi koska ??
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
