# Lottokone

Sovelluksella voidaan pelata lottopeliä.

## Dokumentaatio

[Käyttöohje](dokumentaatio/kayttoohje.md)

[Arkkitehtuurikuvaus](dokumentaatio/arkkitehtuuri.md)

[Vaatimusmäärittely](dokumentaatio/vaatimusmaarittely.md)

[Työaikakirjanpito](dokumentaatio/tyoaikakirjanpito.md)

## Julkaisut

[Viikko 6](https://github.com/kukaan/ot-harjoitustyo/releases/tag/viikko6)

[Viikko 5](https://github.com/kukaan/ot-harjoitustyo/releases/tag/viikko5)

## Komentorivitoiminnot

### Testaus
Testit suoritetaan komennolla
```
mvn test
```

Testikattavuusraportti _target/site/jacoco/index.html_ luodaan komennolla
```
mvn jacoco:report
```

Checkstyle-raportti _target/site/checkstyle.html_ luodaan komennolla
```
mvn jxr:jxr checkstyle:checkstyle
```

### Suoritettavan jarin generointi

Suoritettava tiedosto _target/Lottokone-1.0-SNAPSHOT.jar_ luodaan komennolla
```
mvn package
```

### JavaDoc

JavaDoc-dokumentaatiotiedosto _target/site/apidocs/index.html_ luodaan komennolla
```
mvn javadoc:javadoc
```
