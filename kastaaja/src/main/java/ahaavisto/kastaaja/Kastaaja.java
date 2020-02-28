/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahaavisto.kastaaja;

import java.io.File;
import static java.lang.Integer.parseInt;
import java.util.HashMap;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author arkkis
 */
public class Kastaaja extends Application{

    static Lista<Profiili> vapaat = new Lista<>();
    static File hahmotiedosto;
    static File pelaajatiedosto;
    
    public static Integer getVapaidenMaara() {
        return vapaat.size();
    }
    
    /**
     * Tämä funktio on olemassa vain testaamista varten
     * @param listaVapaista ne hahmot, jotka ovat vapaana (aluksi)
     */
    public static void setVapaat(Lista<Profiili> listaVapaista) {
        vapaat = listaVapaista;
    }
    
    /**
     * Ohjelman koko käyttöliittymä on atm tässä
     * @param ikkuna 
     */
    @Override
    public void start(Stage ikkuna) {
        ikkuna.setTitle("Kastaaja-app");

        FileChooser valitsin = new FileChooser();

        Label opaste = new Label("Valitse lähdetiedostot (.csv)");
        Button hahmonappi = new Button("Valitse hahmolista-tiedosto");
        Button pelaajanappi = new Button("Valitse pelaajalista-tiedosto");
        Button aloitusnappi = new Button("Aja algoritmi");
        TextArea tulos = new TextArea("Algoritmin tulos tulee tähän");
        
        hahmonappi.setOnAction(e -> {
            hahmotiedosto = valitsin.showOpenDialog(ikkuna);
            hahmonappi.setText("Hahmolistaksi valittu " + hahmotiedosto.getName());
        });
        
        pelaajanappi.setOnAction(e -> {
            pelaajatiedosto = valitsin.showOpenDialog(ikkuna);
            pelaajanappi.setText("Pelaajalistaksi valittu " + pelaajatiedosto.getName());
        });
        
        aloitusnappi.setOnAction(e -> {     
            if (onkoTiedostotValittu()) {
                Lista<Profiili> hahmot = luoHahmotJaPelaajat();
                algoritminYdin(hahmot);        
                tulos.setText(tulostaParit(hahmot));
            }
        }); 
        
        VBox komponenttiryhma = new VBox();
        komponenttiryhma.getChildren().add(opaste);
        komponenttiryhma.getChildren().add(hahmonappi);
        komponenttiryhma.getChildren().add(pelaajanappi);
        komponenttiryhma.getChildren().add(aloitusnappi);
        komponenttiryhma.getChildren().add(tulos);

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
    public static void luo_lista_suosikeista(Profiili profiili, Lista<Profiili> verrattavat) {
        HashMap<Profiili, Integer> preferenssit = new HashMap<>();
        for (int i = 0; i < verrattavat.size(); i++) {
            Profiili verrattava = verrattavat.get(i);
            int erotus = 0;
            for (int j = 0; j < profiili.getStatsit().length; j++) {
                erotus += Math.abs(profiili.getStatsit()[j] - verrattava.getStatsit()[j]);
            }
            preferenssit.put(verrattava, erotus);
        }
        Lista<Profiili> tulevat_suosikit = new Lista(verrattavat);//verrattavat
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
    public static void kuplajarjestaminen(Profiili profiili, Lista<Profiili> suosikit, HashMap<Profiili, Integer> preferenssit) {
        boolean eiValmis = true;
        while (eiValmis) {
            eiValmis = false;
            for (int i = 0; i < suosikit.size() - 1; i++) {
                if (onko_isompi(suosikit.get(i), suosikit.get(i + 1), preferenssit)) {
                    suosikit.swap(i, i+1);
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
    public static void Kihlaus(Profiili kosija, Profiili kosittava) {
        if (kosittava.getKihlattu() != null) { //puretaan vanha kihlaus
            Profiili ex = kosittava.getKihlattu();
            ex.setKihlattu(null);
            vapaat.add(ex);
        }
        kosittava.setKihlattu(kosija);
        kosija.setKihlattu(kosittava);
        
        Lista<Profiili> hylatyt = poistetaanHuonommatKuinNykyinen(kosittava, kosija);

        poistetaanTurhatToiveet(hylatyt, kosittava);
    }
    
    /**
     * Algoritmin perusidean mukaisesti, poistetaan "kihloihin" päätyneen hahmon
     * listalta kaikki nykyistä huonommat vaihtoehdot
     * @param profiili hahmo, jonka suosikkeja käsitellään
     * @param kosija pelaaja, jonka kanssa ollaan nyt "kihloissa"
     * @return lista hylätyistä pelaajista; näitä käsitellään seuraavaksi poistetaanTurhatToiveet-funktiossa
     */
    public static Lista<Profiili> poistetaanHuonommatKuinNykyinen(Profiili profiili, Profiili kosija) {
        Lista<Profiili> hylatyt_suosikit = new Lista<>();
        Lista<Profiili> suosikit = profiili.getSuosikit();
        for (int i = 0; i < suosikit.size(); i++) {
            if (suosikit.get(i) == kosija) {
                if (i + 1 <= suosikit.size() - 1) {
                    hylatyt_suosikit = suosikit.subList(i+1, suosikit.size());
                }    
                profiili.setSuosikit(suosikit.subList(0, i+1));   
                break;
            }
        }
        return hylatyt_suosikit;
    }
    
    /**
     * Poistetaan kunkin kyseisen hahmon hylkäämien pelaajien suosikkilistasta tuo hahmo,
     * sillä algoritmin mukaan niistä ei voi tulla enää paria
     * @param hylatyt_suosikit pelaajat, jotka ovat käsiteltävän hahmon 
     * suosikkilistassa alempana kuin nykyinen kihlattu, eli eivät voi tulla valituksi
     * @param hylkaajaHahmo hahmo jota käsitellään
     */
    public static void poistetaanTurhatToiveet(Lista<Profiili> hylatyt_suosikit, Profiili hylkaajaHahmo) {
        for (int i = 0; i < hylatyt_suosikit.size(); i++) {
            Profiili hylatty = hylatyt_suosikit.get(i);            
            for (int j = 0; j < hylatty.getSuosikit().size(); j++) {
                if (hylatty.getSuosikit().get(j).equals(hylkaajaHahmo)) {
                    hylatty.getSuosikit().remove(j);
                }
            }
        }
    }
    
    /**
     * Tulostaa hahmo-pelaaja -parit debuggausta varten; jos hahmolla ei ole vielä pelaajaa,
     * sen kanssa tulostuu 4everalone-teksti
     * @param hahmot käsiteltävät hahmot
     */
    public static String tulostaParit (Lista<Profiili> hahmot) {
        String tulostettava = "";
        tulostettava += "Hahmo-pelaaja -parit:\n";
        for (int i = 0; i < hahmot.size(); i++) {
            Profiili hahmo = hahmot.get(i);
            if (hahmo.getKihlattu() != null) {
            tulostettava += hahmo.getNimi() + " + " + hahmo.getKihlattu().getNimi() + "\n";
            } else {
                tulostettava += hahmo.getNimi() + " 4everalone\n";
            }
        }
        return tulostettava;
    }
    
    /**
     * Datan lukeminen lähdetiedostoista Profiili-olioita listaaviksi listoiksi
     * @param profiilit hahmot/pelaajalista, joka tässä funktiossa täytetään tiedostosta saadulla datalla
     * @param tiedosto hahmo/pelaajalista tiedostomuodossa
     * @return valmis hahmo/pelaajalista
     */
    public static Lista<Profiili> lueData(Lista<Profiili> profiilit, File tiedosto) {       
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
    
    /**
     * Varsinainen hahmojen ja pelaajien mätsäys. Käydään läpi niin kauan, kunnes kaikilla
     * hahmoilla on pelaaja.
     * @param hahmot 
     */
    public static void algoritminYdin(Lista<Profiili> hahmot) {
        vapaat.addAll(hahmot);
        while (vapaat.size() > 0) {
            Profiili kosija = vapaat.get(0);
            vapaat.remove(0); //poistetaan kosiomatkalle lähtijä
            Profiili kosittava = kosija.getSuosikit().get(0);
            Kihlaus(kosija, kosittava);
        }
    }
    
    /**
     * Luodaan hahmot, pelaajat ja niille kullekin suosikkilistat
     * @return luodut hahmot eli algoritmin ytimen tarvitsema lähtödata
     */
    public static Lista<Profiili> luoHahmotJaPelaajat() {
        Lista<Profiili> hahmot = lueData(new Lista<>(), hahmotiedosto);
        Lista<Profiili> pelaajat = lueData(new Lista<>(), pelaajatiedosto);
        
        for (int i = 0; i < hahmot.size(); i++) {
            luo_lista_suosikeista(hahmot.get(i), pelaajat);
        }
        for (int i = 0; i < pelaajat.size(); i++) {
            luo_lista_suosikeista(pelaajat.get(i), hahmot);
        }
        return hahmot;
    }

    public static void main(String[] args) {
        launch();        
        /*
        Lista<Profiili> hahmot = luoHahmotJaPelaajat();
        algoritminYdin(hahmot);
        
        tulostaParit(hahmot);
        */
    }
}
