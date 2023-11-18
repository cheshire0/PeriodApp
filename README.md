# Házi feladat specifikáció

Információk [itt](https://viauac00.github.io/laborok/hf)

## Mobil- és webes szoftverek
### [2023.10.21.]
### [Menstruációs alkalmazás]
### [Sebők Lili] - ([XV0M8Z])
### [seboklili1@gmail.com] 
### Laborvezető: [Fosztó Ábel]

## Bemutatás

A menstruációs alkalmazás, amit eddig éveken keresztül használtam (Clue) pálforduláson esett át az utóbbi pár hónapban. Az alkalmazás, amit a kezdetekkor úgy reklámoztak, hogy nőktől a nőkért a szent cél érdekében, nem adnak ki adatokat stb. - manapság nem működik offline, gyenge wifi kapcsolattal sem és gombnyomásonként feldob egy hirdetést.
Úgy voltam vele, mobweb nagyházi, akkor csinálok magamnak egy sajátot, így a célközönség elsősorban jómagam lenne. A szokásos alapfunkciók meglennének, így a paraméteres naptárbejegyzés és ha időm engedi egy egyszerű algoritmust is írok ami a jövőbe kalkulál.

## Főbb funkciók

Az alkalmazással lehetőség van naptárbejegyzés készítésére, tűnetek felvételére és tárolására, valamint azok naptár nézetben történő megjelenítésére, illetve múltba visszamenőleg szerkesztésére. Ezen felül bizonyos statisztikákat is meg lehet jeleníteni majd pl átlagos ciklushossz, továbbá értesítést is küldene a felhasználónak, ha kalkulált valami érdekeset.

## Választott technológiák:

- (UI)
- (Intent)
- (RecyclerView)
- (Perzisztens adattárolás)


# Házi feladat dokumentáció

Az alkalmazás 3 fő képernyővel rendelkezik: MainActivity, StatisticsActivity, TrackActivity.

MainActivity: Itt található egy Material Calendar, amin a napok alatt piros pötty jelenik meg, ha aznapra van vérzésről bejegyzés. Innen lehet napot választani, majd arra a napra bejegyzést készíteni/törölni a 'Track' gombra nyomva, illetve a 'Statistics' gombbal meg lehet nézni a statisztikákat.

TrackActivity: A személyre szabott ToolBar jobb felső sarkán lévő gombra nyomva a napi bejegyzésekhez adhatunk az opciók közül bármit. A létrehozott bejegyzéseket a jobb oldali 'X'-ra nyomva lehet törölni, a ceruza ikonra nyomva pedig szerkeszteni.

StatisticsActivity: Látható az átlagos ciklushossz, menstruációhossz, illetve ezek alapján egy becslés a következő menstruálás kezdetére. A harang gombra nyomva beállíthatunk egy értesítést a következő menstruálás kezdetére.