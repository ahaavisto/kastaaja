
/**
 *
 * @author arkkis
 */

import static org.junit.Assert.*;
import org.junit.Test;
import ahaavisto.kastaaja.Lista;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class TestLista {
    @Rule
  public final ExpectedException exception = ExpectedException.none();
    
    static Lista<Integer> nrolista;
    
    public void addSeveral() {
        nrolista = new Lista<>();
        nrolista.add(39);
        nrolista.add(40);
        nrolista.add(41);
        nrolista.add(42);
    }

    @Test
    public void testAdd() {
        Lista<Integer> lista = new Lista<>();
        lista.add(42);
        assertEquals("listan pituus ", 1, lista.size());
    }
    
    @Test
    public void testAddSeveral() {
        addSeveral();
        assertEquals("listan pituus ", 4, nrolista.size());
    }
    
    @Test
    public void testAddToIndexInsideList() {
        addSeveral();
        nrolista.add(1, 100);
        assertEquals("listan pituus ", 4, nrolista.size());
        assertEquals("lista sisältää ", true, nrolista.contains(100));
        assertEquals("lista ei sisällä poistettua ", false, nrolista.contains(40));
    }
    
    @Test
    public void testAddToIndexInTheEnd() {
        addSeveral();
        nrolista.add(4, 100);
        assertEquals("listan pituus ", 5, nrolista.size());
        assertEquals("lista sisältää ", true, nrolista.contains(100));
    }

    @Test
    public void testAddTooBigIndex() {
        addSeveral();
        exception.expect(IndexOutOfBoundsException.class);
        nrolista.add(100, 100);
    }
    
    @Test
    public void testAddTooSmallIndex() {
        addSeveral();
        exception.expect(IndexOutOfBoundsException.class);
        nrolista.add(-1, 100);
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
        addSeveral();
        assertEquals("listalla on ekana", (Integer)39, nrolista.get(0));
        assertEquals("listalla on tokana", (Integer)40, nrolista.get(1));
        assertEquals("listalla on 3.", (Integer)41, nrolista.get(2));
        assertEquals("listalla on 4.", (Integer)42, nrolista.get(3));
    }
    
    @Test
    public void testGetTooBigIndex() {
        addSeveral();
        exception.expect(IndexOutOfBoundsException.class);
        nrolista.add(100, 100);
    }
    
    @Test
    public void testContains() {
        Lista<Integer> lista = new Lista<>();
        lista.add(42);
        assertEquals("listalta löytyy sinne lisätty", true, lista.contains(42));
        assertEquals("listalta ei löydy olemattomia", false, lista.contains(22));
    }
    
    @Test
    public void testRemovePoistaa() {
        Lista<String> lista = new Lista<String>();
        lista.add("41");
        lista.add("42");
        lista.remove(0);
        assertEquals("Listan pituus on ", 1, lista.size());
        assertEquals("Poistettu poistunut listalta", false, lista.contains("41"));
    }
    
    @Test
    public void testRemoveMuuttaaIndeksit() {
        Lista<String> lista = new Lista<String>();
        lista.add("40");
        lista.add("41");
        lista.add("42");
        lista.remove(1);
        assertEquals("Eka alkio ennallaan ", "40", lista.get(0));
        assertEquals("Indeksi muuttunut oikein", "42", lista.get(1));
    }
    
    @Test
    public void testSwap() {
        addSeveral();
        nrolista.swap(1, 2);
        assertEquals("Listan pituus on ", (Integer)4, (Integer)nrolista.size());
        assertEquals("sijainti vaihtunut ", (Integer)40, nrolista.get(2));
        assertEquals("sijainti vaihtunut ", (Integer)41, nrolista.get(1));
    }
    
    @Test
    public void testSwapForIllegalIndex() {
        addSeveral();
        exception.expect(IndexOutOfBoundsException.class);
        nrolista.swap(1,100);
    }
    
    @Test
    public void testKasvataKapasiteettia() {
        Lista<String> lista = new Lista<String>();
        int kokoAluksi = lista.getKapasiteetti();
        for (int i = 0; i < kokoAluksi+1; i++) {
            lista.add("asdf");
        }
        assertEquals("Listan kapasiteetti kasvanut", 20, lista.getKapasiteetti());
    }
    
    @Test
    public void testKonstruktorinKuormitus() {
        Lista<String> lista = new Lista<String>();
        lista.add("asdf");
        Lista<String> lista2 = new Lista<String>(lista);
        assertEquals("Listan pituus on ", 1, lista2.size());
        assertEquals("Lista sisältää ", true, lista2.contains("asdf"));
    }
    
    @Test
    public void testSubList() {
        Lista<String> lista = new Lista<String>();
        for (int i = 0; i < 8; i++) {
            lista.add("" + i);
        }
        lista = lista.subList(1, 3);
        assertEquals("Listan pituus on ", 2, lista.size());
        assertEquals("Listalla ekana oikea alkio", "1", lista.get(0));
        assertEquals("Listalla vikana oikea alkio", "2", lista.get(1));
    }
  
    
}
