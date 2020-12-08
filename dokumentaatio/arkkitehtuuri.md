# Arkkitehtuurikuvaus

## Rakenne
Korkealla tasolla ohjelma jakautuu ohjelman käynnistävää pääohjelmaa lukuunottamatta kolmeen pakkaukseen: käyttöliittymä UI on riippuvainen sovelluslogiikasta vastaavasta domainista. Tämä on puolestaan riippuvainen tietokantaan pääsyn tarjoavasta DAO:sta.

Korkean tason rakennetta voi koittaa hahmottaa luokkakaaviosta, jossa _LottokoneService_ ja _User_ kuuluvat domain-pakkaukseen.

![luokkakaavio](luokkakaavio.png)

Tässä sekvenssikaaviossa DAO-pakkaus ei esiinny, sillä LottokoneService on kirjautumisen yhteydessä jo hakenut käyttäjäolion DAO:lta. Merkintä _User_ vasemmalla ei viittaa User-olioon vaan ohjelmaa käyttävään henkilöön, joka antaa ohjelmalle syötteitä ja lukee tulosteita.

![sekvenssikaavio](sekvenssikaavio.png)


## Sovelluslogiikka
Sovelluslogiikka järjestetään LottokoneService-luokassa, joka tarjoaa toimintoja käyttöliittymälle, ja käsittelee DAO:n kautta haetun User-olion tietoja.


