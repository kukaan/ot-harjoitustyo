# Lottokone

Sovelluksella voidaan pelata lottopeliä.

## Dokumentaatio

[Arkkitehtuuri](dokumentaatio/arkkitehtuuri.md)

[Vaatimusmäärittely](dokumentaatio/vaatimusmaarittely.md)

[Työaikakirjanpito](dokumentaatio/tyoaikakirjanpito.md)

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
