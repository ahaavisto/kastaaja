
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
        if (koko == sisalto.length) {
            kasvataKapasiteettia();
        }
        sisalto[koko] = e;
        koko++;
    }
    
    public void add(int index, E e) {
        if (koko == sisalto.length) {
            kasvataKapasiteettia();
        }
        sisalto[index] = e;
        //TO DO tarkistus, milloin koko muuttuu tällä add-versiolla
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
    /** 
    //Print method
    @Override
    public String toString() 
    {
         StringBuilder sb = new StringBuilder();
         sb.append('[');
         for(int i = 0; i < koko ;i++) {
             sb.append(sisalto[i].toString());
             if(i<koko-1){
                 sb.append(",");
             }
         }
         sb.append(']');
         return sb.toString();
    }*
    */
     
    private void kasvataKapasiteettia() {
        int uusiKoko = sisalto.length * 2;
        sisalto = Arrays.copyOf(sisalto, uusiKoko);
    }
    
    public Lista<E> subList(int i, int j) {
        sisalto = Arrays.copyOfRange(sisalto, i, j); //TODO tarkista että toimii halutusti
        return this;
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