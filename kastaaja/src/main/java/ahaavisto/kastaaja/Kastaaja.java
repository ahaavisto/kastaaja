
package ahaavisto.kastaaja;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Pääluokka, joka sisältää lähinnä käyttöliittymän
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
     * Algoritmin graafinen käyttöliittymä
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
                Lista<Profiili> hahmot = Algoritmi.luoHahmotJaPelaajat(hahmotiedosto, pelaajatiedosto);
                Algoritmi.algoritminYdin(hahmot);        
                tulos.setText(Algoritmi.tulostaParit(hahmot));
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

    

    public static void main(String[] args) {
        hahmotiedosto = new File("assets/hahmot.csv"); //Arviointi-luokan käyttämät tiedostot
        pelaajatiedosto = new File("assets/pelaajat.csv");

        //Aja varsinainen ohjelma:
        launch();
        
        //Aja suorituskyky- ja indifference-testaus:
        
        Arviointi a = new Arviointi();
        a.testaaKuinkaMoniSaaSuosikkinsa(hahmotiedosto, pelaajatiedosto);
        a.testaaProfiilienEriJarjestyksilla(hahmotiedosto, pelaajatiedosto);
        a.testaaEriEtusijaisuudella(hahmotiedosto, pelaajatiedosto);
        a.testaaSuoritusaikaa();
        
    }
}
