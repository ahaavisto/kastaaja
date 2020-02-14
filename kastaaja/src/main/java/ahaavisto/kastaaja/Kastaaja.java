/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahaavisto.kastaaja;

import java.io.File;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author arkkis
 */
public class Kastaaja extends Application{

    static ArrayList<Profiili> vapaat = new ArrayList<>();
    static File hahmotiedosto;
    static File pelaajatiedosto;
    
    /**
     * Tiedostonvalitsin ja sen simppeli käyttöliittymä
     * @param ikkuna 
     */
    @Override
    public void start(Stage ikkuna) {
        ikkuna.setTitle("Kastaaja-app");

        FileChooser valitsin = new FileChooser();

        Label opaste = new Label("Valitse lähdetiedostot (.csv). Kun olet valinnut molemmat, ohjelma käynnistyy.");
        Button hahmonappi = new Button("Valitse hahmolista-tiedosto");
        Button pelaajanappi = new Button("Valitse pelaajalista-tiedosto");       
        
        hahmonappi.setOnAction(e -> {
            hahmotiedosto = valitsin.showOpenDialog(ikkuna);
            hahmonappi.setText("Hahmolista valittu");
            hahmonappi.setDisable(true);
            if (onkoTiedostotValittu()) {
                ikkuna.close();
            }
        });
        
        pelaajanappi.setOnAction(e -> {
            pelaajatiedosto = valitsin.showOpenDialog(ikkuna);
            pelaajanappi.setText("Pelaajalista valittu");
            pelaajanappi.setDisable(true);
            if (onkoTiedostotValittu()) {
                ikkuna.close();
            }
        });      
        
        FlowPane komponenttiryhma = new FlowPane();
        komponenttiryhma.getChildren().add(opaste);
        komponenttiryhma.getChildren().add(hahmonappi);
        komponenttiryhma.getChildren().add(pelaajanappi);

        Scene nakyma = new Scene(komponenttiryhma);

        ikkuna.setScene(nakyma);
        ikkuna.show();
    }
    
    public static boolean onkoTiedostotValittu() {
        if (hahmotiedosto != null && pelaajatiedosto != null) {
            return true;
        }
        return false;
    }

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
        profiili.setSuosikit(suosikit);
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
        if (kosittava.kihlattu != null) { //puretaan vanha kihlaus
            Profiili ex = kosittava.kihlattu;
            ex.kihlattu = null;
            vapaat.add(ex);
        }
        kosittava.kihlattu = kosija;
        kosija.kihlattu = kosittava;

        //poistetaan kositun suosikeista nykyistä kihlattua huonommat vaihtoehdot
        List<Profiili> hylatyt_suosikit = new ArrayList<>();
        for (int i = 0; i < kosittava.suosikit.size(); i++) {
            if (kosittava.suosikit.get(i) == kosija) {
                if (i + 1 <= kosittava.suosikit.size() - 1) {
                    hylatyt_suosikit = kosittava.suosikit.subList(i+1, kosittava.suosikit.size());
                }                          
                kosittava.suosikit = kosittava.suosikit.subList(0, i+1); //säästetään vain kihlattu ja paremmat        
                break;
            }
        }
        poistetaanTurhatToiveet(hylatyt_suosikit, kosittava);
        //poistetaan hylättyjen kosijoiden suosikkilistoilta äsken kihlattu
//        for (Profiili hylatty_hahmokandidaatti : hylatyt_suosikit) {
//            for (int i = 0; i < hylatty_hahmokandidaatti.suosikit.size(); i++) {
//                if (hylatty_hahmokandidaatti.suosikit.get(i).nimi.equals(kosittava.nimi)) {
//                    hylatty_hahmokandidaatti.suosikit.remove(i);
//                }
//            }
//        }
    }
    
    public static void poistetaanTurhatToiveet(List<Profiili> hylatyt_suosikit, Profiili hylkaaja) {
        for (Profiili hylatty : hylatyt_suosikit) {
            for (int i = 0; i < hylatty.suosikit.size(); i++) {
                if (hylatty.suosikit.get(i).equals(hylkaaja)) {
                    hylatty.suosikit.remove(i);
                }
            }
        }
    }
    
    /**
     * Tulostaa hahmo-pelaaja -parit debuggausta varten; jos hahmolla ei ole vielä pelaajaa,
     * sen kanssa tulostuu 4everalone-teksti
     * @param hahmot käsiteltävät hahmot
     */
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
    
    /**
     * Datan lukeminen lähdetiedostoista Profiili-olioita listaaviksi listoiksi
     * @param profiilit hahmot/pelaajalista, joka tässä funktiossa täytetään tiedostosta saadulla datalla
     * @param tiedosto hahmo/pelaajalista tiedostomuodossa
     * @return valmis hahmo/pelaajalista
     */
    public static ArrayList<Profiili> lueData(ArrayList<Profiili> profiilit, File tiedosto) {       
        try
        {          
            for(Scanner sc = new Scanner(tiedosto); sc.hasNext(); ) {
              String line = sc.nextLine();
              String[] splitattu = line.split(",");
              Profiili uusi = new Profiili(splitattu[0]); //tiedostossa ekana nimi-string
              int[] statsit = new int[10];
                for (int i = 1; i < splitattu.length; i++) { //ja sen jälkeen int-muotoiset statsit
                    statsit[i-1] = parseInt(splitattu[i]);
                }
              uusi.setStatsit(statsit);
              profiilit.add(uusi);
            } 
        } 
        catch(Exception ex) 
        { 
           System.out.println("Tapahtui poikkeus lähdetiedoston käsittelyssä!");  
           ex.printStackTrace();
        }
        return profiilit;
    }

    public static void main(String[] args) {
        launch(); //tiedostonvalitsin
        
        ArrayList<Profiili> hahmot = new ArrayList<>();
        ArrayList<Profiili> pelaajat = new ArrayList<>();
        
        hahmot = lueData(new ArrayList<Profiili>(), hahmotiedosto);
        pelaajat = lueData(new ArrayList<Profiili>(), pelaajatiedosto);
        
        for (Profiili hahmo : hahmot) {
            luo_lista_suosikeista(hahmo, pelaajat);
        }
        for (Profiili pelaaja : pelaajat) {
            luo_lista_suosikeista(pelaaja, hahmot);
        }

        //varsinainen algoritmi       
        vapaat.addAll(hahmot);

        while (vapaat.size() > 0) {
            Profiili kosija = vapaat.get(0);
            vapaat.remove(0); //poistetaan kosiomatkalle lähtijä
            Profiili kosittava = kosija.suosikit.get(0);
            vaihda_kihlaus(kosija, kosittava);
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
    }
}
