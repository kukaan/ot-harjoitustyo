package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void saldoTulostuuAlussaOikein() {
        assertEquals("saldo: 10.0",kortti.toString());
    }
    
    @Test
    public void saldoSentitAlussaOikein() {
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void saldoVaheneeOikein() {
        kortti.otaRahaa(1000);
        assertEquals(0, kortti.saldo());
    }
    
    @Test
    public void saldoEiMuutuJosEiTarpeeksiRahaa() {
        kortti.otaRahaa(1001);
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void ottoPalauttaaTrueOikein() {
        assertEquals(true, kortti.otaRahaa(1000));
    }
    
    @Test
    public void ottoPalauttaaFalseOikein() {
        assertEquals(false, kortti.otaRahaa(1001));
    }
}
