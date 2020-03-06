# Käyttöohje

## Ohjelman tarkoitus

Kastaaja-sovellus yhdistää larppihahmot hahmojen ominaisuuksien perusteella pelaajiin siten, että lopputulos on optimaalinen siten, ettei kukaan pelaaja haluaisi vaihtaa saamaansa hahmoa päittäin toisen pelaajan kanssa, eikä myöskään mikään hahmo voisi saada sopivampaa pelaajaa vaihtamalla päittäin pelaajaa toisen hahmon kanssa ("Stable marriage").

Hahmot ja pelaajat on pelkistetty listaksi numeerisia parametreja, jotka käytännössä olisivat hahmon ominaisuuksia / pelaajan toiveita, jotka on muutettu numeerisiksi esimerkiksi tyyliin "Hahmo, jolla on paljon vaikutusvaltaa? 0 = ei juuri yhtään; 1 = vain vähän; 2 = ei paljon eikä vähän; 3 = aika paljon; 4 = hyvin paljon".

Ohjelma valitsee hahmoille mahdollisimman sopivat pelaajat, ja ratkaisusta tulee siten hahmojen ("pelin kokonaisuuden") kannalta optimaalisempi kuin pelaajien näkökulmasta. Käytännössä testatessa ilmeni, että ero on pieni.

## Ohjelman käyttö

Sovelluksen käynnistyessä näkyviin tulee graafinen käyttöliittymä, jossa voit valita lähdetiedostot. Käytä assets-kansion hahmot.csv ja pelaajat.csv tiedostoja tai muita tiedostoja, joiden datarivit ovat muotoa:
nimi,1,2,0,1,2

Kun olet valinnut tiedostot, voit "aja algoritmi"-nappia painamalla käynnistää varsinaisen algoritmin suorituksen. Algoritmin tulos eli hahmo-pelaaja-parit tulostuvat näkyviin käyttöliittymän tekstikenttään, josta ne voi kopioida talteen muualle talteen.

Kastaus toimii, kun pelaajia on yhtä paljon tai enemmän kuin hahmoja. Jos halutaan kastata tilanteessa, jossa hahmoja on tarjolla enemmän kuin pelaajia on, pitää laittaa pelaajat "hahmoiksi" eli ensisijaiseen asemaan se profiililuokka, joita on enemmän.

Algoritmin toimintaa arvioivat Arviointi-luokan testit ajetaan sen jälkeen, kun käyttäjä sulkee graafisen käyttöliittymän. Niiden tulokset tulostuvat konsoliin.
