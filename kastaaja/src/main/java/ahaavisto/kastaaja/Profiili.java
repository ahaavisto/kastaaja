
package ahaavisto.kastaaja;

import java.util.ArrayList;
import java.util.List;
import java.util.Random; 

/**
 * Tämä luokka kuvaa yksittäistä hahmoa/pelaajaa; ne ovat algoritmin kannalta identtisiä, joten yksi luokka riittää
 * @author arkkis
 */
public class Profiili {
    int[] statsit; // hahmon/pelaajan sarjaksi lukuja parametrisoidut ominaisuudet, joiden perusteella hahmot ja pelaajat yhdistetään
    //boolean vapaa = true; //onko profiili jo yhdistetty toisen profiililuokan profiiliin
    Profiili kihlattu = null; //mihin profiiliin yhdistetty (jos on)
    List<Profiili> suosikit; //preferenssijärjestys toisen profiililuokan hahmoille
    String nimi;

    public Profiili(String nimi) {
        this.statsit = new int[10];
        this.nimi = nimi;
        Random rand = new Random(); 
        for (int i=0; i < 10; i++) { //tällä hetkellä täytetään satunnaisilla luvuilla tässä vaiheessa
            statsit[i] = rand.nextInt(5); 
        }

    }
}
