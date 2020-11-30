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

Testikattavuusraportti tiedostoon _target/site/jacoco/index.html_ luodaan komennolla
```
mvn jacoco:report
```

Checkstyle-raportti tiedostoon _target/site/checkstyle.html_ luodaan komennolla
```
mvn jxr:jxr checkstyle:checkstyle
```

### ...
