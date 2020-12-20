# Käyttöohje

Lataa viimeisimmän [julkaisun](https://github.com/kukaan/ot-harjoitustyo/releases) lottokone.jar-tiedosto.

## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla

```
java -jar lottokone.jar
```

## Tekstikäyttöliittymä

Ohjelman käyttöliittymä kertoo kullakin hetkellä saatavilla olevat komennot tai muut vaihtoehdot.

### Lottorivin arvonta

Syötä komento ```draw```. Huomaa varsinainen pelaamistoiminto alempana!

### Käyttäjän luominen

Syötä komento ```register```.

Syötä vielä haluamasi käyttäjänimi.

### Kirjautuminen

Syötä komento ```login```.

Syötä vielä luomasi käyttäjänimi.

### Lottorivin lisääminen

Kun olet kirjautunut sisään, syötä komento ```add```.

Syötä haluamasi lottonumerot pilkuilla erotettuina vapaassa järjestyksessä. Oletuksena lottonumeroita hyväksytään 7 per rivi väliltä 1-40. Esim. ```40,1,2,3,4,5,6```.

Kun olet lisännyt haluamasi rivit, anna vielä tyhjä syöte päästäksesi takaisin päävalikkoon.

### Pelaaminen

Kun olet luonut haluamasi lottorivit, pelaa yksi kierros komennolla ```play``` tai useita kierroksia komennolla ```autoplay```.

Syötä haluamiasi rivejä vastaavat luvut pilkuilla erotettuina, tai jos haluat pelata kaikilla riveillä, syötä ```0```.
