/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahaavisto.kastaaja;

import java.util.HashMap;

/**
 *
 * @author arkkis
 */
public class Kastaaja {
    
    /**
     * 
     * @param eka profiili jonka sopivuutta verrataan tokaan
     * @param toka profiili johon ekan sopivuutta verrataan
     * @param suosikit map joka mäppää ekan profiilin mätsäävyysarvon kuhunkin profiiliin
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
     * Luodaan kyseiselle profiilille (eli hahmolle/pelaajalle) lista, missä järjestyksessä se preferoi toisen profiililuokan profiileita
     * @param profiili hahmo/pelaaja jolle luodaan preferenssilistaa
     * @param verrattavat ne pelaajat/hahmot jotka halutaan järjestää
     */
    public static void luo_lista_suosikeista(Profiili profiili, Profiili verrattavat[]) {
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
    
    /**
     * Luodaan preferenssijärjestys sen perusteella, kuinka paljon kunkin ominaisuuden abs(hahmo-pelaaja) erotusten yhteissumma on
     * @param profiili se jonka suosikit halutaan tallentaa järjestetyksi taulukoksi
     * @param suosikit toistaiseksi järjestämätön taulukko toisen profiililuokan profiileista
     * @param preferenssit map jossa tallessa tieto käsiteltävän profiilin preferensseista toisen profiililuokan profiileihin
     */
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
        
        //luodaan hahmot ja pelaajat:
        Profiili Hahmot[] = new Profiili[10];
        Profiili Pelaajat[] = new Profiili[10];
        for (int i = 0; i < 10; i++) {
            Hahmot[i] = new Profiili();
            Pelaajat[i] = new Profiili();
        }
        
        for (Profiili hahmo: Hahmot) {
            luo_lista_suosikeista(hahmo, Pelaajat);
        }
        
        for (Profiili pelaaja: Pelaajat) {
            luo_lista_suosikeista(pelaaja, Hahmot);
        }       
        
        //tästä tulee varsinainen algoritmi; pahasti kesken
        for (Profiili kosija: Hahmot) { //väliaikainen pysähtymisehto, kaikki kerran läpi
            Profiili kosittava = kosija.suosikit[kosija.suosikit.length-1];
            kosittava.vapaa = false;
            kosittava.kihlattu = kosija;
            kosija.vapaa = false;
            kosija.kihlattu = kosittava;
                
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

        
        
        //ei toimi koska ??, luonnostelua tiedostonluvusta
        
        /*
        try
        { 
           
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
