# Viikkoraportti 2

Viikko oli aika tuskallinen, koska en pystynyt käyttämään projektiin kovinkaan paljon aikaa, mutta ennen kaikkea siksi, että aikaa kului tuloksettomaan konffaamiseen. Ubuntuläppärini alkaa luultavasti yksinkertaisesti olla niin vanha, että on työn ja tuskan takana saada ohjelmat ja kirjastot yms toimimaan yhdessä. Viikon mittaan mm. asensin uuden Netbeansin ja yritin saada sekä Mavenin että Gradlen toimimaan sekä uudella että vanhalla Netbeansilla. Gradlea en saanut asennettua kumpaankaan, enkä pyöritettyä Mavenia kummallakaan. Virheilmoitusten googlaaminen ei tuottanut tuloksia. En siis saanut viime viikolla luomaani Maven-projektia (tai mitään muutakaan Maven-projektia) buildaamaan. Tein siksi tavallisen javaprojektin ja aloin koodata sinne.

Sain koodattua jonkinlaisen, hashmappia vielä toistaiseksi käyttävän version algoritmin esiprosessoinnista. Tällä hetkellä data luodaan joka kerta ajon aluksi, sillä en saanut tiedostonlukua toimimaan helposti (koska javaskillini ovat ruosteessa) ja päätin keskittyä koodaamaan ennemmin jotain olennaisempaa. Java-taitoni ovat vielä ruosteisemmat kuin kuvittelin, ja jouduin googlaamaan ihan perus syntaksiakin. Eteneminen oli hidasta. Oli sinänsä ihan mukavaa koodata pitkästä aikaa niinä hetkinä, kun sai edettyä jopa useamman rivin googlaamatta.

Koska olin kuvitellut saavani tiedostonluvun toimimaan, tein myös pienen datasetin itselleni eli käytännössä csv-filun satunnaisia numeroita 0-4. Tein datan generoinnin pythonin random-kirjastolla, sillä se oli tutuin ja nopein tapa. Ajattelin että tässä projektissa datan tarkalla rakenteella ei ole juuri väliä, koska se on lopultakin niin simppeliä, joten ei liene väliä miten sen generoi.

## Seuraavaksi
Kiireisimmin pitäisi saada Maven tai Gradle toimimaan, jotta voisi rakentaa testausympäristön ja muutenkin luoda järkevän projektirakenteen. Myös algoritmin ytimessä riittää yhä koodattavaa, joten itse koodaaminen jatkuu sillä.

## Ajankäyttö
n. 5 h Tuloksetonta konffaamista
n. 3 h ohjelmointia
alle 1 h dokumentaatiota
