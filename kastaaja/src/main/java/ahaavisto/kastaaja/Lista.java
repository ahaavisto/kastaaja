
package ahaavisto.kastaaja;

import java.util.Arrays;
 
public class Lista<E> {
    private int koko = 0;
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] sisalto;

    public Lista() {
        sisalto = new Object[DEFAULT_CAPACITY];
    }
    
    public Lista(Lista<E> lista) {
        sisalto = new Object[DEFAULT_CAPACITY];
        for (int i = 0; i < lista.koko; i++) {
            this.add(lista.get(i));
        }
    }

    public void add(E e) {
        add(koko, e);
    }
    
    /**
     * Lisätään alkio tiettyyn indeksiin (kunhan indeksi on listan sisällä tai tulossa suoraa sen perään)
     */
    public void add(int index, E e) {
        if (koko == sisalto.length) {
            kasvataKapasiteettia();
        }        
        if (index < 0 || index > koko) { //ei voi lisätä niin, että listaan jäisi tyhjää
            throw new IndexOutOfBoundsException("Index: " + index + ", Size " + koko);
        } else if (index < koko) {
            sisalto[index] = e;
        } else { //kun lisätään listan viimeiseksi, koko kasvaa
            sisalto[index] = e;
            koko++;
        }   
    }
    
    /**
     * Vaihtaa annetuissa indekseissä olevat oliot keskenään
     */
    public void swap(int ekaIndex, int tokaIndex) {
        if (ekaIndex >= koko || ekaIndex < 0 || tokaIndex >= koko || tokaIndex < 0) {
            throw new IndexOutOfBoundsException("Index: " + ekaIndex + ", Size " + koko);
        }
        Object eka = sisalto[ekaIndex];
        Object toka = sisalto[tokaIndex];
        sisalto[ekaIndex] = toka;
        sisalto[tokaIndex] = eka;
    }

    @SuppressWarnings("unchecked")
    public E get(int i) {
        if (i >= koko || i < 0) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size " + koko);
        }
        return (E) sisalto[i];
    }
    
    @SuppressWarnings("unchecked")
    public E remove(int i) {
        if (i >= koko || i < 0) {
            throw new IndexOutOfBoundsException("Index: " + i + ", Size " + koko);
        }
        Object poistettava = sisalto[i];
        
        for (int j = i; j < koko-1; j++) {
            sisalto[j] = sisalto[j+1];
        }
        sisalto[koko-1] = null;
        koko--;
        return (E) poistettava;
    }
    
    public int size() {
        return koko;
    }
     
    private void kasvataKapasiteettia() {
        int uusiKoko = sisalto.length * 2;
        sisalto = Arrays.copyOf(sisalto, uusiKoko);
    }
    
    /**
     * Testaamista varten
     */
    public int getKapasiteetti() {
        return sisalto.length;
    }
    
    /**
     * Luo uuden listan joka sisältää aiemman listan annettujen indeksien väliltä
     * @param fromIndex mistä alkaen; tämä raja mukana
     * @param toIndex minne asti; tämä raja ei mukana
     * @return muokattu lista
     */
    public Lista<E> subList(int fromIndex, int toIndex) {
        // todo tarkista että j > i
        Object[] uusi = Arrays.copyOfRange(sisalto, fromIndex, toIndex); //TODO tarkista että toimii halutusti
        Lista<E> uusiLista = new Lista<>();
        for (int i = 0; i < uusi.length; i++) {
            uusiLista.add((E) uusi[i]);
        }
        return uusiLista;
    }
    
    public void addAll(Lista lista) {
        for (int i = 0; i < lista.size(); i++) {
            this.add((E) lista.get(i));
        }
    }
    
    public boolean contains(E e) {
        for (int i = 0; i < koko; i++) {
            if (this.get(i).equals(e)) {
                return true;
            }
        }
        return false;
    }
}