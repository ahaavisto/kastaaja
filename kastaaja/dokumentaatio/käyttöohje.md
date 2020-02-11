# Käyttöohje

Sovelluksen käynnistyessä näkyviin tulee graafinen käyttöliittymä, jossa voit valita lähdetiedostot. Käytä assets-kansion hahmot.csv ja pelaajat.csv tiedostoja tai muita tiedostoja, joiden datarivit ovat muotoa:
nimi,1,2,0,1,2

Kun olet valinnut tiedostot, varsinainen ohjelma käynnistyy automaattisesti ja tulostaa komentoriville tietoja algoritmin etenemisestä. 11. helmikuuta versiossa varsinainen algoritmi on yhä rikki ja jää ikuiseen silmukkaan. Siksi suoritus on laitettu pysähtymään 100 silmukan jälkeen, vaikka kaikki eivät olisikaan "naimisissa" (terminologia lainattu suoraa stable marriage -algoritmilta).
