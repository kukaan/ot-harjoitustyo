
import com.mycompany.unicafe.Kassapaate;
import com.mycompany.unicafe.Maksukortti;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {
    Kassapaate kassa;
    int kassaAlussa = 100000;
    Maksukortti kortti;
    int korttiAlussa = 1000;
    
    public KassapaateTest() {
    }
    
    @Before
    public void setUp() {
        kassa = new Kassapaate();
        kortti = new Maksukortti(1000);
    }
    
    @Test
    public void alussa() {}
    
    @Test
    public void alussaOikeaSummaRahaa() {
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void alussaMyytyNollaEdullista() {
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void alussaMyytyNollaMaukasta() {
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void edullinenKateisellaOnnistunut() {
        assertEquals(60, kassa.syoEdullisesti(300));
        assertEquals(kassaAlussa+240, kassa.kassassaRahaa());
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void maukasKateisellaOnnistunut() {
        assertEquals(20, kassa.syoMaukkaasti(420));
        assertEquals(kassaAlussa+400, kassa.kassassaRahaa());
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void edullinenKateisellaEpaonnistunut() {
        assertEquals(239, kassa.syoEdullisesti(239));
        assertEquals(kassaAlussa, kassa.kassassaRahaa());
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void maukasKateisellaEpaonnistunut() {
        assertEquals(399, kassa.syoMaukkaasti(399));
        assertEquals(kassaAlussa, kassa.kassassaRahaa());
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void edullinenKortillaOnnistunut() {
        assertEquals(true, kassa.syoEdullisesti(kortti));
        assertEquals(kassaAlussa, kassa.kassassaRahaa());
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void maukasKortillaOnnistunut() {
        assertEquals(true, kassa.syoMaukkaasti(kortti));
        assertEquals(kassaAlussa, kassa.kassassaRahaa());
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void edullinenKortillaEpaonnistunut() {
        kortti.otaRahaa(900);
        assertEquals(false, kassa.syoEdullisesti(kortti));
        assertEquals(kassaAlussa, kassa.kassassaRahaa());
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void maukasKortillaEpaonnistunut() {
        kortti.otaRahaa(700);
        assertEquals(false, kassa.syoMaukkaasti(kortti));
        assertEquals(kassaAlussa, kassa.kassassaRahaa());
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kortinLataaminenLisaaKortinSaldoa() {
        kassa.lataaRahaaKortille(kortti, 250);
        assertEquals(1250, kortti.saldo());
    }
    
    @Test
    public void kortinLataaminenLisaaKassanRahamaaraa() {
        kassa.lataaRahaaKortille(kortti, 250);
        assertEquals(kassaAlussa+250, kassa.kassassaRahaa());
    }
    
    @Test
    public void kortilleEiVoiLadataNegatiivistaRahamaaraa() {
        kassa.lataaRahaaKortille(kortti, -200);
        assertEquals(1000, kortti.saldo());
        assertEquals(kassaAlussa, kassa.kassassaRahaa());
    }
}