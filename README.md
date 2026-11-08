# LoadMaster – plánovač nakládky nákladného vozidla

Desktopová aplikácia v Jave (JavaFX), ktorá pomáha naplánovať nakládku krabíc a paliet do nákladného vozidla.
Projekt vzniká postupne s pomocou AI (vibe coding) ako semestrálne zadanie z oblasti logistiky.

## Aktuálna verzia: v0.1
- výber typu vozidla (dodávka, nákladné auto, kamión)
- zadávanie nákladu (rozmery, hmotnosť, zastávka, či sa naň dá ukladať)
- tabuľka nákladu, odstránenie položky
- súhrn: počet kusov, hmotnosť a objem oproti kapacite vozidla, upozornenie pri preťažení

## Spustenie
Potrebné: JDK 17 alebo novší a Maven.

```
mvn javafx:run
```

V IntelliJ IDEA: otvoriť priečinok ako Maven projekt → Maven panel → Plugins → javafx → javafx:run.
