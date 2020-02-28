
package ahaavisto.kastaaja;

import java.util.List;
import java.util.Objects;
//import java.util.Random; 

/**
 * Tämä luokka kuvaa yksittäistä hahmoa/pelaajaa; ne ovat algoritmin kannalta identtisiä, joten yksi luokka riittää
 * @author arkkis
 */
public class Profiili {
    private int[] statsit; // hahmon/pelaajan sarjaksi lukuja parametrisoidut ominaisuudet, joiden perusteella hahmot ja pelaajat yhdistetään
    private Profiili kihlattu = null; //mihin profiiliin yhdistetty (jos on)
    private Lista<Profiili> suosikit; //preferenssijärjestys toisen profiililuokan hahmoille
    private String nimi; //"id"

    public Profiili(String nimi) {
        this.statsit = new int[10];
        this.nimi = nimi;
    }  
    public Lista<Profiili> getSuosikit() {
        return suosikit;
    }
    
    public Profiili getKihlattu() {
        return kihlattu;
    }
    
    public void setKihlattu(Profiili kihlattu) {
        this.kihlattu = kihlattu;
    }
    
    public String getNimi () {
        return nimi;
    }
    
    public void setSuosikit(Lista<Profiili> suosikit) {
        this.suosikit = suosikit;
    }
    
    public void setStatsit(int[] statsit) {
        this.statsit = statsit;
    }
    
    public int[] getStatsit() {
        return this.statsit;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.nimi);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Profiili other = (Profiili) obj;
        if (!Objects.equals(this.nimi, other.nimi)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return this.nimi;
    }
    
    
    
}
