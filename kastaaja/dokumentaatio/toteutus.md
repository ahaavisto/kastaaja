# Toteutusdokumentti

## Ohjelman yleisrakenne

Ohjelma koostuu pääluokasta, Algoritmi-luokasta, jossa sijaitsee varsinainen toiminnallisuus, Profiili-luokasta, joka kuvaa yksittäistä hahmoa/pelaajaa ja Lista-luokasta, joka on Javan valmista ArrayListia muistuttava tietorakenne. Lisäksi on ohjelman toimintaa testaavia funktioita sisältävä luokka Arviointi. Yksinkertainen graafinen käyttöliittymä sijaitsee pääluokassa.

Hahmot ja pelaajat ovat ohjelman kannalta identtisiä Profiili-olioita: niillä on id:nä toimiva nimi, statsit-taulukko joka kuvaa niiden kastaamisessa käytettävät ominaisuudet, sekä kastauksessa käytettävät suosikkilista ja "kihlattu" eli mahdollinen mätsätty hahmo/pelaaja.

Testidata muodostaa csv-tiedostoista, joissa hahmo/pelaaja(n toiveet hahmosta) on pelkistetty listaksi kokonaislukuja 0-4. Ne kuvaavat kukin jotakin hahmon ominaisuuden määrää asteikolla ei lainkaan/vähän/ei vähän eikä paljon/jonkin verran/paljon: onko hahmolla paljon vaikutusvaltaa, käyttääkö hahmo magiaa, onko hahmolla paljon salaisuuksia tms.

## Aika- ja tilavaativuudet

Käyttämäni Stable marriage -algoritmin variaation aikavaatimus on O(n²) [Wikipedia:Stable_marriage_with_indifference](https://en.wikipedia.org/wiki/Stable_marriage_with_indifference). Tämä on helppo nähdä todeksi siksi, että pahimmassa tapauksessa algoritmi käy läpi kaikki n hahmon ja n pelaajan muodostamat parit, joita on n². Tilavaativuus samoin O(n²).

Ennen varsinaista algoritmia profiileille luodaan listat toisen profiilityypin olioista, ja suosikit järjestetään kuplajärjestämisellä, jonka aikavaativuus senkin on O(n²). Näitä suosikkilistojen järjestämisiä tehdään 2n, jolloin aikavaativuudeksi tulee O(n³). Koko ohjelman aikavaatimus on siten O(n³) ja tilavaativuus O(n²).

Testasin myös todellista suoritusaikaa 10, 100 ja 1000 pituisella syötteellä (ks. testausdokumentti).

## Puutteet ja parannusehdotukset

Datasta riippuen erilaisia vakaita avioliittoja on voi olla hyvin paljon, esimerkiksi [Wikipedian mukaan](https://en.wikipedia.org/wiki/Stable_marriage_problem#Different_stable_matchings) eräällä tasaisella jakaumalla n * ln (n). Testatessa sitä, kuinka paljon vaihdannaisuutta algoritmin tuottama kastaus testidatalla todellisuudessa sisältää (ks. testausdokumentti), ilmeni että käytännön sovelluksia varten olisi tärkeää saada ohjelma näyttämään jossakin muodossa eri vaihtoehtoisia, algoritmisesti yhtä hyviä kastaustuloksia. Näin ihminen voisi suorittaa valinnan loppuun päättäen muiden kuin numerosoitavien parametrien osalta, mitkä hahmo+pelaaja-parit kannattaa yhdistää.

Yritin testata, vaikuttaako tulokseen se, ovatko valitsijoina ("kosijoina") hahmot vai pelaajat. Testini mukaan tämä ei vaikuta tulokseen lainkaan, joka vaikuttaa epäuskottavalta. Testini ei siis luultavasti toimi.

## Lähteet

Algoritmi perustuu [Stable marriage with indifference](https://en.wikipedia.org/wiki/Stable_marriage_with_indifference) -algoritmiin.

Hahmo- ja pelaajalistat generoitu kirjoittamallani pienellä Python3-ohjelmalla käyttäen Pythonin standardikirjaston Random-kirjastoa parametrien luomiseen.

Hahmojen nimet (100 nimen lista):
[Odysseus-pelin](https://drive.google.com/drive/folders/1niTz3oFzJ1N5eJWz9Jh6Y2gDT2SMjTrI) hahmojen nimet kopioituna [tästä listasta](https://docs.google.com/spreadsheets/d/1NGnezEMCPD4EtBjMlA4Tg0ZSKoH4Fuo7F0Kfos_WwIo/edit?usp=drive_web&ouid=103489600517709596079) (Huom, kyseinen Excel on varsin raskas)

Pelaajien nimet (100 nimen lista):
[Wiktionaryn lista ruotsinkielisistä etunimistä (lyhennettynä mielivaltaisesti)](https://en.wiktionary.org/wiki/Appendix:Swedish_given_names)
