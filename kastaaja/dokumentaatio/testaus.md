#Testaus

Arviointi-luokasta löytyvät muut kuin JUnit-testit, ja ne voidaan suorittaa pääohjelman kautta.

## Yksikkötestaus
[Testikattavuusraportti Githubissa](https://github.com/ahaavisto/kastaaja/blob/master/kastaaja/dokumentaatio/jacoco/index.html)

Jos olet ladannut repositorion koneellesi, löytyy raportti sijainnista: dokumentaatio/jacoco/index.html

Ohjelmaa on testattu automatisoidusti junit-yksikkötesteillä. Testit ovat siten toistettavissa tavanomaisella aja testit -komennolla. Listalla on yli 90 % testikattavuus, Algoritmilla ja Profiililla yli 50 %. Pääluokan käyttöliittymää en testannut, ja Arviointi-luokalle en ehtinyt tekemään montaakaan testiä, joten sen osalta kattavuus jäi alhaiseksi.

## Suoritusajan testaus

Testasin algoritmin todellista suoritusaikaa 10, 100 ja 1000 pituisella syötteellä (ks. testausdokumentti). Kyseinen testifunktio ajoi kullakin syötteellä algoritmin kymmenen kertaa, sen jälkeen laskin keskiarvot suoritusajoista ja piirsin niistä kuvaajan (alla). Keskimääräinen algoritmin suoritusaika oli vastaavasti n. 0,5 ms, 14 ms ja 8400 ms: kuvaajan akselit ovat logaritmisia.

![alt text](https://github.com/ahaavisto/kastaaja/blob/master/kastaaja/dokumentaatio/aika_kaytannossa.png "Suoritusajan keskiarvo")

Suoritusajat käytännössä ovat varsin kohtuulliset, ja näyttävät jäävän paljon O(n³)-aikavaativuudesta. Testidatassani on niin paljon mahdollista vaihdannaisuutta, että tämä oli odotettavissa.

## Muu testaus

Ohjelmaa on testattu koko sen elinkaaren ajan luonnollisesti myös käsin. Testausta varten on tehty käsin kolmen hahmon ja kolmen pelaajan miniversio, jotta oli helppo hahmottaa, missä algoritmia rakentaessa ilmenneet bugit sijaitsivat.

Algoritmin käytännön sovellusten kannalta on mielenkiintoista, kuinka paljon algoritmin tuottamaan hahmojen ja pelaajien mätsäykseen vaikuttaa se, missä järjestyksessä syötedata on. Algoritmi suosii listan alkupäätä, sillä kihloihin päätyvät aina ensimmäinen sopiva hahmo+pelaaja -pari. Täten jos jonkin parin h1+p1 yhteensopivuus ovat vaihdannaiset eli tasoissa (_indifferent_) parin h1+p2 siten että myös h2+p1 ovat vaihdannaiset, päätyvät yhteen ne hahmo ja pelaaja, jotka ovat syötteessä ensimmäisinä.

Testasin tätä 10 ja 100 pituisilla syötteillä. 10 pituisella syötteellä ilmeni, että kyseisellä testidatalla kaksi paria on vaihdannaisia keskenään, joten 8 pareista tuli muodostettua aina samoin, mutta syötteen järjestys vaikutti kahden pelaajan ja kahden hahmon kohdalla.

100 pituisella syötteellä - algoritmin käytännön sovellusten kannalta varsin realistisessa tilanteessa - vaihdannaisuutta esiintyi luonnollisesti paljon enemmän (kyyhkyslakkaperiaate). Ajoin kirjoittamani testin 10 kertaa vaihdellen syötteen järjestystä, ja muuttumattomina kahden ajon välillä pysyi 61-77 pareista. Tämän pienen testauksen tuloksena vaikuttaa siis siltä, että yli 20 % pareista osalta löytyi siis jokin muukin yhtä hyvä paritus kuin nykyinen. Sellaisessa tilanteessa ei algoritmin kannalta ole mitään vikaa, mutta koska todellisessa tilanteessa kastaukseen vaikuttavat aina myös ei-numerisoitavat parametrit (esimerkiksi vapaasti sanallisesti ilmaistut toiveet), voi käytännössä noiden algoritmin tuottamien eri vaihtoehtojen sopivuuden välillä olla iso ero.

Testasin myös, kuinka moni hahmoista ja pelaajista tulee paritettua ykkösvaihtoehdolleen. 100 hahmon ja pelaajan testidatalla tiedoston järjestystä käytettäessä 64 hahmoista sai ykkösvaihtoehtonsa ja pelaajista 59. Koska algoritmi suosii hahmoja (eikä niinkään pelaajia), tulos oli yllättävänkin tasainen ja siten algoritmi on todelliseen käyttöön sopiva. 51 tapauksessa sekä hahmo että pelaaja saivat 1. vaihtoehtonsa.

Yritin testata myös sitä, vaikuttaako tulokseen se, ovatko valitsijoina ("kosijoina") hahmot vai pelaajat. Testini mukaan tämä ei vaikuta tulokseen lainkaan, vaan tulos riippuu ainoastaan profiilien järjestyksestä lähdetiedostossa. Tämä vaikuttaa epäuskottavalta.
