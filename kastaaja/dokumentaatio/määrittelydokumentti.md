# Määrittelydokumentti, kastaaja

Työssä käsitelty ongelma on kastata larppi onnistuneesti eli yhdistää larpin hahmot ja pelaajat siten, että kaikilla hahmoilla olisi mahdollisimman sopiva pelaaja ja kaikilla pelaajilla mahdollisimman sopiva hahmo. Sopivuus määritellään työssä siten, että hahmon ominaisuudet vastaavat pelaajan toivomia ominaisuuksia mahdollisimman hyvin. Työssä käytetään numeeriksi parametreiksi yksinkertaistettuja ominaisuuksia, jotka kaikki vaihtelevat samalla välillä. Ominaisuuksia voisivat olla esimerkiksi "hahmolla on synkkä menneisyys", "hahmo on hierarkian huipulla", "hahmo käyttää magiaa", ja parametrin arvot tarkoittaisivat 0="ei lainkaan", 1="vähän", 2="ei paljon eikä vähän", 3="melko paljon" ja 4="paljon"

Näin sekä hahmojen ominaisuudet että pelaajan toiveet hahmoista pelkistyvät sarjaksi numeroita. Jos käytetään edellä olleita ominaisuuksia samassa järjestyksessä esimerkkeinä, numerosarja 1, 4, 2 tarkoittaisi hahmoa jolla on vähän synkkä menneisyys, hierarkian huipulla ja käyttää magian jonkin verran. Jonkinlainen "ei väliä"-vaihtoehto olisi käytännössä mielekäs lisävaihtoehto pelaajan toiveisiin, mutta ei ole algoritmin toteutuksen ytimessä.

Ohjelman syötteenä toimivat siten 1) lista pelaajien toiveista ja 2) lista tarjolla olevista hahmoista, jotka molemmat ovat taulukko, jossa kokonaislukuja sisältäviä taulukoita. Taulukoiden pituus on kymmeniä, ei edes satoja alkioita. Käytännön tilannetta mielessä pitäen ominaisuuksia on järkevää olettaa olevan 5-10 ja hahmoja/pelaajia 50-100.

Ongelma palautuu Vakaa avioliitto -ongelmaan: miten yhdistetään kaksi ryhmää alkioita, kun kullakin alkiolla on preferenssijärjestys toisen ryhmän alkioista, siten että lopputulos on niin hyvä, ettei mikään vaihdos paranna tilannetta. Hahmojen ja pelaajien preferenssijärjestykset toisistaan ylläkuvatuista hahmokuvauksista voidaan tehdä laskemalla kunkin hahmo-pelaaja -parin parametrien erotusten summa. Koska parametrejä on vähemmän kuin hahmoja/pelaajia, tulee väistämättä tasapelejä kunkin hahmon ja pelaajan preferenssijärjestykseen (kyyhkyslakkaperiaate). Tällöin avioliitto-ongelman ratkaisuksi tarvitaan tasapelien kanssa pärjäävä versio, englanniksi Stable marriage with indifference. Sille on kehitetty O(n²)-ajassa toimiva variaatio tavanomaisen avioliitto-ongelman ratkaisusta (https://en.wikipedia.org/wiki/Stable_marriage_with_indifference).

Tarvittaessa harjoitustyötä voi laajentaa ottamalla huomioon pelaajan toiveita tarkemmin kuin tässä esitetyllä erotusten summa -tavalla: esimerkiksi ottamalla huomioon, ettei yhdenkään hahmo-pelaaja-parin minkään parametrin ero jää suuremmaksi kuin 1 tai 2, tai painottamalla eri ominaisuuksien parametreja eri tavoin tai lisäämällä mukaan muita rajoitteita siihen, millaisia pareja voi muodostaa.



