
/**
 *
 * @author arkkis
 */

import static org.junit.Assert.*;
import org.junit.Test;
import ahaavisto.kastaaja.Lista;

public class TestLista {

    @Test
    public void testAdd() {
        Lista<Integer> lista = new Lista<>();
        lista.add(42);
        assertEquals("listan pituus ", 1, lista.size());
    }
    
    @Test
    public void testAddSeveral() {
        Lista<Integer> lista = new Lista<>();
        lista.add(39);
        lista.add(40);
        lista.add(41);
        lista.add(42);
        assertEquals("listan pituus ", 4, lista.size());
    }
    
    @Test
    public void testAddAll() {
        Lista<Integer> lista = new Lista<>();
        lista.add(41);
        lista.add(42);
        Lista<Integer> lista2 = new Lista<>();
        lista2.addAll(lista);
        assertEquals("listan pituus ", 2, lista2.size());
        assertEquals("lista sisältää ", true, lista2.contains(41));
        assertEquals("lista sisältää ", true, lista2.contains(42));
    }
    
    @Test
    public void testGet() {
        Lista<String> lista = new Lista<>();
        lista.add("42");
        assertEquals("listalla on ekana", "42", lista.get(0));
    }
    
    @Test
    public void testGetSeveral() {
        Lista<String> lista = new Lista<String>();
        lista.add("39");
        lista.add("40");
        lista.add("41");
        lista.add("42");
        assertEquals("listalla on ekana", "39", lista.get(0));
        assertEquals("listalla on tokana", "40", lista.get(1));
        assertEquals("listalla on 3.", "41", lista.get(2));
        assertEquals("listalla on 4.", "42", lista.get(3));
    }
    
    @Test
    public void testContains() {
        Lista<Integer> lista = new Lista<>();
        lista.add(42);
        assertEquals("listalta löytyy sinne lisätty", true, lista.contains(42));
        assertEquals("listalta ei löydy olemattomia", false, lista.contains(22));
    }
    
}
