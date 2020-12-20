# Testausdokumentti

## Yksikkö- ja integraatiotestaus

Sovelluslogiikkaa JUnitilla testaavat TempDaoServiceTest ja pelaamistoiminnallisuuden osalta TempDaoPlayTest. Keskeneräiseksi jäänyttä relaatiotietokannan integraatiota testaa SQLiteDaoServiceTest. Lisäksi DAO-luokkia testataan omilla testiluokillaan. Käyttöliittymäkerrosta ei testata automaattisesti.

### Testikattavuus

Kun ei lasketa käyttöliittymäkerrosta mukaan, sovelluksen testauksen rivikattavuudeksi saadaan 70% ja haarautumakattavuudeksi 68%. Suurin osa kattavuusvajeesta koskee pelaamistoiminnallisuutta.

## Järjestelmätestaus

Järjestelmätestausta on tehty ainoastaan manuaalisesti Linux-ympäristössä.

Kaikkia määrittelydokumentissa listattuja toiminnallisuuksia on testattu manuaalisesti niin valideilla kuin virheellisilläkin syötteillä, eikä virhetilanteita ole julkaisuversiossa havaittu.

### Sovellukseen jääneet laatuongelmat

Relaatiotietokannan toimintaa ei suunnitteluvirheistä johtuen saatu lainkaan hyväksyttävälle tasolle, joten se on julkaisuversiossa poistettu käytöstä Main-luokassa.
