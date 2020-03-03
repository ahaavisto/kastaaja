# Toteutusdokumentti

## Ohjelman yleisrakenne

Ohjelma koostuu pääluokasta, Profiili-luokasta, joka kuvaa yksittäistä hahmoa/pelaajaa ja Lista-luokasta joka on Javan valmista ArrayListia muistuttava tietorakenne. Hahmot ja pelaajat ovat ohjelman kannalta identtisiä Profiili-olioita: niillä on id:nä toimiva nimi, statsit-taulukko joka kuvaa niiden kastaamisessa käytettävät ominaisuudet, sekä kastauksessa käytettävät suosikkilista ja "kihlattu" eli mahdollinen mätsätty hahmo/pelaaja.

Käyttöliittymän eriyttäminen on vaiheessa (tilanne 27.2.).

## Aika- ja tilavaativuudet

Käyttämäni Stable marriage -algoritmin variaation aikavaatimus on O(n²) [Wikipedia:Stable_marriage_with_indifference](https://en.wikipedia.org/wiki/Stable_marriage_with_indifference). Tämä on helppo nähdä todeksi siksi, että pahimmassa tapauksessa algoritmi käy läpi kaikki n hahmon ja n pelaajan muodostamat parit, joita on n².

Tilavaativuus samoin O(n²).

## Puutteet ja parannusehdotukset

Testatessa sitä, kuinka paljon vaihdannaisuutta algoritmin tuottama kastaus testidatalla todellisuudessa sisältää (ks. testausdokumentti), ilmeni että käytännön sovelluksia varten olisi tärkeää saada ohjelma näyttämään jossakin muodossa eri vaihtoehtoisia, algoritmisesti yhtä hyviä kastaustuloksia. Näin ihminen voisi suorittaa valinnan loppuun päättäen muiden kuin numerosoitavien parametrien osalta, mitkä hahmo+pelaaja-parit kannattaa yhdistää.

## Lähteet

Algoritmi perustuu [Stable marriage with indifference](https://en.wikipedia.org/wiki/Stable_marriage_with_indifference) -algoritmiin.

Hahmo- ja pelaajalistat generoitu Python3-ohjelmanpätkällä käyttäen Pythonin standardikirjaston Random-kirjastoa parametrien luomiseen.

Hahmojen nimet (100 nimen lista):
[Odysseus-pelin](https://drive.google.com/drive/folders/1niTz3oFzJ1N5eJWz9Jh6Y2gDT2SMjTrI) hahmojen nimet kopioituna [tästä listasta](https://docs.google.com/spreadsheets/d/1NGnezEMCPD4EtBjMlA4Tg0ZSKoH4Fuo7F0Kfos_WwIo/edit?usp=drive_web&ouid=103489600517709596079) (Huom, kyseinen Excel on varsin raskas)

Pelaajien nimet (100 nimen lista):
[Wiktionaryn lista ruotsinkielisistä etunimistä (lyhennettynä mielivaltaisesti)](https://en.wiktionary.org/wiki/Appendix:Swedish_given_names)
