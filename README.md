A program beüzemeléséhez szükséges:
-Jetbrains IntellJ IDEA, az alábbi linken letölthető: https://www.jetbrains.com/idea/download/#section=windows
-Docker Desktop, az alábbi linken letölthető: https://www.docker.com/get-started/
-SQL Developer, az alábbi linken elérhető: https://www.oracle.com/tools/downloads/sqldev-downloads.html
- Windows Power Shell/ egyéb terminálok
- Oracle 12c docker image: https://hub.docker.com/r/truevoly/oracle-12c

A program beüzemelése
1. Nyissuk meg az alábbi alkalmazásokat: Intellj IDEA, Docker, SQLDeveloper
2. A terminálba/Power Shellbe írjuk be az alábbi parancsot
docker pull truevoly/oracle-12c
ez letölti nekünk a program működéséhez szükséges imaget
3. Az imgaesnél menjünk rá a runra és adjuk meg a ports-nál a local host szövegdoboz alatta az 1521-es portot,
kattintsunk a plusz jelre és alá pedig írjuk be a 8080-asat. Adjunk meg neki egy tetszőleges nevet
4. Futtasunk a containersnél
5. Hozzunk létre egy új connectiont az sqldeveloper felületén. Adjunk meg egy tetszőleges nevet, username-nek a systemet
passwordnak pedig az oracle -t ezek után a tábla létrehozó scriptet nyissuk meg és másoljuk ki az egészet és illesszük be a worksheet felületre,
futtassuk a scriptet a zöld háromszög melletti gombbal (ajánlatos kétszer futtatni a scriptet egymás után, hogy véletlen se maradjon ki semmi)
6. Az IntellJ-n belül nyissuk meg a projektunket (Plane project), töltsük le az inttelj által felkínálat a program futtatásához szükséges dolgokat
7. Jobb oldalt található egy Maven fülecske arra kattintsunk rá, nyissuk le a pluginst.
8. Kattintsunk a cleanre és ott a clean:clean-re ezek után pedig a javafx-et a pluginok között és ott a javafx:run-ra kattintani.
9. Működésre bírtuk a programunkat :)
