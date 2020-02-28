# Viikkoraportti 6 (28.2. asti)

(21.2. asti)
Taas oli vaikea löytää aikaa koodaamiselle. Maanantaina tein vähän lisää testejä ja refaktoroin samalla koodia, sekä testattavammaksi että toivottavasti muutenkin fiksummaksi, single responsibility -periaatteen mukaiseksi.

Tiistaina jatkoin käyttöliittymää vähän eteenpäin. Se on nyt minimal viable product, tai koska en ole erityisen innostunut näteistä graafisista käleistä (käyttäjänä tai koodaajana), niin oikeastaan jo parempikin. Pitää vaan refaktoroida se olemaan jossain fiksummassa paikassa ja jaksaa useampaan funktioon sen toiminnallisuus.

Oli varsin mukavia hetkiä koodin parissa. Pahimmat ruosteet alkavat olla ravisteltu irti, ja oli esimerkiksi hauska pikkiriikkisillä muutoksilla tehdä tulostusfunktiosta sellainen, että se konsolin sijaan printtaa käyttöliittymän textAreaan.

(28.2. asti)
Tämä viikko meni pitkälti siihen, että tein testejä ja korvasin ArrayListin omalla toteutuksella. Oli ehkä elämäni toistaiseksi hyödyllisimmät hetket testauksen parissa: testit ja testikattavuusraportin tuijottaminen auttoivat monta kertaa näkemään bugit.

Olin jollain edellisellä viikolla ihmetellyt kaverille, mikä pointti ArrayListin koodaamisessa itse on, kun netistä on helppo kopioida jonkun jo tekemä versio. Tämän viikon myötä opin syyn: netistä kopioimalla ei saa kuin raakileen. Löytämäni versio oli buginen, ja bugien korjaamisen lisäksi jouduin toteuttamaan siihen monta funktiota itse alusta asti (addAll, subList jne - sekä swap, joka yksinkertaisti pääohjelman koodia). Oli epätoivon hetkiä, mutta myös varsin palkitsevaa koodata tällä viikolla.

## Seuraavaksi

HashMap pitäisi saada korvattua, kierrän koko nykyisen hankalan hässäkän varmaankin kustomoimalla listatoteutustani. Dokumentaatiota pitää päivittää ja keksiä tapa testata suorituskykyä, koska ohjelma on nykyisellään pakko ajaa graafisen kälin kautta, ei ihan pelkkää NetBeansia tuijottamalla saa tietoa ajoajoista.

Käyttöliittymään voisi lisätä vielä jonkinlaisen "tallenna tiedostoon"-toiminnallisuuden, jotta algoritmin tulosta ei tarvitsisi käsin kopioida jonnekin.

## Ajankäyttö

10 h ohjelmointia
n. 1 h dokumentaatiota
