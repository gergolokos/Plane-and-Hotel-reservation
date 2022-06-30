DROP TABLE felhasznalo;
DROP TABLE fizetesimod;
DROP TABLE szalloda;
DROP TABLE foglalas;
DROP TABLE jaratfigyrel;
DROP TABLE jegy;
DROP TABLE kategoria;
DROP TABLE repulogep;
DROP TABLE repuloter;
DROP TABLE utazasiosztaly;
DROP TABLE varos;
DROP TABLE orszag;
DROP TABLE jarat;
DROP TABLE figyelmeztetes;

DROP SEQUENCE UtazasiOsztaly_sequence;
DROP SEQUENCE Foglalas_sequence;
DROP SEQUENCE Jegy_sequence;
DROP SEQUENCE Kategoria_sequence;
DROP SEQUENCE FizetesiMod_sequence;
DROP SEQUENCE Felhasznalo_sequence;
DROP SEQUENCE Jarat_sequence;
DROP SEQUENCE JaratFigyRel_sequence;
DROP SEQUENCE Repulogep_sequence;
DROP SEQUENCE Repuloter_sequence;
DROP SEQUENCE Varos_sequence;
DROP SEQUENCE Orszag_sequence;
DROP SEQUENCE Figyelmeztetes_sequence;
DROP SEQUENCE szalloda_sequence;

ALTER SESSION SET NLS_DATE_LANGUAGE = ENGLISH;
ALTER SESSION SET NLS_DATE_FORMAT = 'YYYY-MON-DD HH24:MI';

CREATE TABLE UtazasiOsztaly 
    (id              	NUMBER(10) NOT NULL,
     nev                VARCHAR2(30),
  CONSTRAINT UtazasiOsztaly_PRIMARY_KEY PRIMARY KEY (id));

create sequence UtazasiOsztaly_sequence;

create trigger UtazasiOsztaly_trigger
    before insert on UtazasiOsztaly
    for each row
    begin
      select UtazasiOsztaly_sequence.nextval
         into:new.id
         from dual;
    end;
	/

insert into UtazasiOsztaly(nev) values('Turista');
insert into UtazasiOsztaly(nev) values('Business');
insert into UtazasiOsztaly(nev) values('First');



CREATE TABLE FizetesiMod 
    (id              	NUMBER(10) NOT NULL,
     nev                VARCHAR2(30),
  CONSTRAINT FizetesiMod_PRIMARY_KEY PRIMARY KEY (id));

create sequence FizetesiMod_sequence;

create trigger FizetesiMod_trigger
    before insert on FizetesiMod
    for each row
    begin
      select FizetesiMod_sequence.nextval
         into:new.id
         from dual;
    end;
	/

insert into FizetesiMod(nev) values('Hitelkártya');
insert into FizetesiMod(nev) values('Banki átutlás');
insert into FizetesiMod(nev) values('Készpénz');



CREATE TABLE Felhasznalo 
    (id              	NUMBER(10) NOT NULL,
     felh_nev           VARCHAR2(30) NOT NULL,
	 jelszo				VARCHAR2(20) NOT NULL,
	 isAdmin			NUMBER(1,0) DEFAULT 0,
	 email				VARCHAR2(50) NOT NULL,
  CONSTRAINT Felhasznalo_PRIMARY_KEY PRIMARY KEY (id));

create sequence Felhasznalo_sequence;

create trigger Felhasznalo_trigger
    before insert on Felhasznalo
    for each row
    begin
      select Felhasznalo_sequence.nextval
         into:new.id
         from dual;
    end;
	/

insert into Felhasznalo(felh_nev,jelszo,isAdmin, email) values('admin', 'admin', 1, 'admin@admin.hu');
insert into Felhasznalo(felh_nev,jelszo,isAdmin, email) values('user', 'userr', 0, 'user@user.hu');
insert into Felhasznalo(felh_nev,jelszo,isAdmin, email) values('bianka', 'bianka', 1, 'bianka@bianka.hu');
insert into Felhasznalo(felh_nev,jelszo,isAdmin, email) values('gergo', 'gergo', 1, 'gergo@gergo.hu');


CREATE TABLE Kategoria 
    (id              	NUMBER(10) NOT NULL,
     nev                VARCHAR2(30) NOT NULL,
     kedvezmeny         NUMBER(3) NOT NULL,
  CONSTRAINT Kategoria_PRIMARY_KEY PRIMARY KEY (id));

create sequence Kategoria_sequence;

create trigger Kategoria_trigger
    before insert on Kategoria
    for each row
    begin
      select Kategoria_sequence.nextval
         into:new.id
         from dual;
    end;
	/

insert into Kategoria(nev, kedvezmeny) values('Gyerek', 15);
insert into Kategoria(nev, kedvezmeny) values('Diák', 20);
insert into Kategoria(nev, kedvezmeny) values('Nyugdíjas', 20);
insert into Kategoria(nev, kedvezmeny) values('Mozgáskorlátozott', 50);
insert into Kategoria(nev, kedvezmeny) values('Felnőtt', 0);



CREATE TABLE Jegy 
    (id              	NUMBER(10) NOT NULL,
     kategoria_id       NUMBER(5),
	 utazasi_osztaly_id	NUMBER(10),
	 foglalasi_id		NUMBER(10),
  CONSTRAINT Jegy_FOREIGN_KEY FOREIGN KEY (utazasi_osztaly_id) REFERENCES UtazasiOsztaly (id),
  CONSTRAINT Jegy_FOREIGN_KEY_2 FOREIGN KEY (kategoria_id) REFERENCES Kategoria (id),
  CONSTRAINT Jegy_PRIMARY_KEY PRIMARY KEY (id) );

create sequence Jegy_sequence;

create trigger Jegy_trigger
    before insert on Jegy
    for each row
    begin
      select Jegy_sequence.nextval
         into:new.id
         from dual;
    end;
	/
	
	

CREATE TABLE Figyelmeztetes 
    (id           NUMBER(10) NOT NULL,
     uzenet       VARCHAR2(100) NOT NULL,
  CONSTRAINT Figyelmeztetes_PRIMARY_KEY PRIMARY KEY (id));

create sequence Figyelmeztetes_sequence;

create trigger Figyelmeztetes_trigger
    before insert on Figyelmeztetes
    for each row
    begin
      select Figyelmeztetes_sequence.nextval
         into:new.id
         from dual;
    end;
	/

insert into Figyelmeztetes(uzenet) values('A járat 30 percet késik.');
insert into Figyelmeztetes(uzenet) values('A járatot törölték.');

CREATE TABLE Orszag 
    (id        NUMBER(10) NOT NULL,
     nev       VARCHAR2(50) NOT NULL,
  CONSTRAINT Orszag_PRIMARY_KEY PRIMARY KEY (id));

create sequence Orszag_sequence;

create trigger Orszag_trigger
    before insert on Orszag
    for each row
    begin
      select Orszag_sequence.nextval
         into:new.id
         from dual;
    end;
	/

insert into Orszag(nev) values('Magyarország');
insert into Orszag(nev) values('Németország');
insert into Orszag(nev) values('Nagy-Britannia');
insert into Orszag(nev) values('Olaszország');
insert into Orszag(nev) values('Hollandia');
insert into Orszag(nev) values('Belgium');
insert into Orszag(nev) values('Spanyolország');
insert into Orszag(nev) values('Portugália');
insert into Orszag(nev) values('Svédország');
insert into Orszag(nev) values('Franciaország');
insert into Orszag(nev) values('Finnország');
insert into Orszag(nev) values('Seychelle-szigetek');
insert into Orszag(nev) values('Egyesült Arab Emírségek');
insert into Orszag(nev) values('Horvátország');
insert into Orszag(nev) values('Ciprus');
insert into Orszag(nev) values('Görögország');
insert into Orszag(nev) values('Norvégia');
insert into Orszag(nev) values('Egyiptom');
insert into Orszag(nev) values('Ausztrália');
insert into Orszag(nev) values('Jordánia');
insert into Orszag(nev) values('Izrael');
insert into Orszag(nev) values('Amerikai Egyesült Államok');
insert into Orszag(nev) values('Kanada');
insert into Orszag(nev) values('Mexikó');
insert into Orszag(nev) values('Dominikai Köztársaság');
insert into Orszag(nev) values('Lengyelország');
insert into Orszag(nev) values('Írország');
insert into Orszag(nev) values('Szerbia');
insert into Orszag(nev) values('Dánia');
insert into Orszag(nev) values('Csehország');



CREATE TABLE Varos 
    (id        NUMBER(10) NOT NULL,
     nev       VARCHAR2(20) NOT NULL,
     orszag_id NUMBER(10) NOT NULL,
  CONSTRAINT Varos_FOREIGN_KEY FOREIGN KEY (orszag_id) REFERENCES Orszag (id),
  CONSTRAINT Varos_PRIMARY_KEY PRIMARY KEY (id));

create sequence Varos_sequence;

create trigger Varos_trigger
    before insert on Varos
    for each row
    begin
      select Varos_sequence.nextval
         into:new.id
         from dual;
    end;
	/


insert into Varos(nev, orszag_id) values('Budapest', 1);
insert into Varos(nev, orszag_id) values('Berlin', 2);
insert into Varos(nev, orszag_id) values('Punta Cana', 25);
insert into Varos(nev, orszag_id) values('London', 3);
insert into Varos(nev, orszag_id) values('Róma', 4);
insert into Varos(nev, orszag_id) values('Miami', 22);
insert into Varos(nev, orszag_id) values('Amdszterdam', 5);
insert into Varos(nev, orszag_id) values('Brüsszel', 6);
insert into Varos(nev, orszag_id) values('New York', 22);
insert into Varos(nev, orszag_id) values('Düsseldorf', 2);
insert into Varos(nev, orszag_id) values('Rodosz', 16);
insert into Varos(nev, orszag_id) values('Melbourne', 19);
insert into Varos(nev, orszag_id) values('Madrid', 7);
insert into Varos(nev, orszag_id) values('Montréal', 23);
insert into Varos(nev, orszag_id) values('Prága', 30);
insert into Varos(nev, orszag_id) values('Nizza', 10);
insert into Varos(nev, orszag_id) values('Lisszabon', 8);
insert into Varos(nev, orszag_id) values('Bristol', 3);
insert into Varos(nev, orszag_id) values('Ammán', 20);
insert into Varos(nev, orszag_id) values('München', 2);
insert into Varos(nev, orszag_id) values('Oslo', 17);
insert into Varos(nev, orszag_id) values('Stockholm', 9);
insert into Varos(nev, orszag_id) values('Párizs', 10);
insert into Varos(nev, orszag_id) values('Perth', 19);
insert into Varos(nev, orszag_id) values('Manchester', 3);
insert into Varos(nev, orszag_id) values('Kairó', 18);
insert into Varos(nev, orszag_id) values('Zakynthos', 16);
insert into Varos(nev, orszag_id) values('Malmö', 9);
insert into Varos(nev, orszag_id) values('Helsinki', 11);
insert into Varos(nev, orszag_id) values('Cancun', 24);
insert into Varos(nev, orszag_id) values('Malaga', 8);
insert into Varos(nev, orszag_id) values('Korfu (Kerkyra)', 16);
insert into Varos(nev, orszag_id) values('Bari', 4);
insert into Varos(nev, orszag_id) values('Köln', 2);
insert into Varos(nev, orszag_id) values('Frankfurt', 2);
insert into Varos(nev, orszag_id) values('Edinburgh', 3);
insert into Varos(nev, orszag_id) values('Akaba', 20);
insert into Varos(nev, orszag_id) values('Luxor', 18);
insert into Varos(nev, orszag_id) values('Porto', 8);
insert into Varos(nev, orszag_id) values('Marseille', 10);
insert into Varos(nev, orszag_id) values('Palermo', 4);
insert into Varos(nev, orszag_id) values('Hannover', 2);
insert into Varos(nev, orszag_id) values('Koppenhága', 29);
insert into Varos(nev, orszag_id) values('Páfosz', 15);
insert into Varos(nev, orszag_id) values('Varsó', 26);
insert into Varos(nev, orszag_id) values('Dubrovnik', 14);
insert into Varos(nev, orszag_id) values('Palma de Mallorca', 7);
insert into Varos(nev, orszag_id) values('Liverpool', 3);
insert into Varos(nev, orszag_id) values('Toronto', 23);
insert into Varos(nev, orszag_id) values('Göteborg', 9);
insert into Varos(nev, orszag_id) values('Sarm es-Sejk', 18);
insert into Varos(nev, orszag_id) values('Nürnberg', 2);
insert into Varos(nev, orszag_id) values('Lyon', 10);
insert into Varos(nev, orszag_id) values('Chicago', 22);
insert into Varos(nev, orszag_id) values('Nápoly', 4);
insert into Varos(nev, orszag_id) values('Ibiza', 7);
insert into Varos(nev, orszag_id) values('Milánó', 4);
insert into Varos(nev, orszag_id) values('Tel-Aviv', 21);
insert into Varos(nev, orszag_id) values('Bournemouth', 3);
insert into Varos(nev, orszag_id) values('Belgrád', 28);
insert into Varos(nev, orszag_id) values('Velence (Treviso)', 4);
insert into Varos(nev, orszag_id) values('Poznań', 26);
insert into Varos(nev, orszag_id) values('Debrecen', 1);
insert into Varos(nev, orszag_id) values('Zadar', 14);
insert into Varos(nev, orszag_id) values('Niš', 28);
insert into Varos(nev, orszag_id) values('Bilbao', 7);
insert into Varos(nev, orszag_id) values('Nizza', 10);
insert into Varos(nev, orszag_id) values('Sydney', 19);
insert into Varos(nev, orszag_id) values('Dublin', 27);
insert into Varos(nev, orszag_id) values('Mahé', 12);
insert into Varos(nev, orszag_id) values('Hurghada', 18);
insert into Varos(nev, orszag_id) values('Dubaj', 13);
insert into Varos(nev, orszag_id) values('Athén', 16);
insert into Varos(nev, orszag_id) values('Washington', 22);



CREATE TABLE Repuloter
    (id        NUMBER(10) NOT NULL,
     nev       VARCHAR2(50) NOT NULL,
     szelesseg      FLOAT NOT NULL,
     hosszusag      FLOAT NOT NULL,
     varos_id  NUMBER(10) NOT NULL,
  CONSTRAINT Repuloter_FOREIGN_KEY FOREIGN KEY (varos_id) REFERENCES Varos (id),
  CONSTRAINT Repuloter_PRIMARY_KEY PRIMARY KEY (id));

create sequence Repuloter_sequence;

create trigger Repuloter_trigger
    before insert on Repuloter
    for each row
    begin
      select Repuloter_sequence.nextval
         into:new.id
         from dual;
    end;
	/

insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Budapest Liszt Ferenc Nemzetközi', 47.44, 19.26, 1);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Berlin-Schönefeld', 52.37, 13.52, 2);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('London-Gatwick', 51.14, 0.16, 3);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Roma-Fiumicino', 41.8, 12.24, 4);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Amszterdam-Schiphol', 52.31, 4.67, 5);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Brüsszeli', 50.9, 4.48, 6);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Madrid-Barajasi', 40.49, 3.57, 7);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Hurghada', 37.17, 33.79, 18);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Lisbon Portela', 38.77, 9.13, 8);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Stockholm-Arlanda', 59.65, 17.92, 9);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Belgrade Nikola Tesla', 44.81, 20.3, 28);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Leonardo Da Vinci', 41.8, 12.25, 4);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('O’Hare', 58.43, 23.12, 22);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Bresso', 45.54, 9.2, 4);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Paris–Orly', 48.73, 2.37, 10);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('München', 48.35, 11.78, 13);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Luxor', 25.67, 32.7, 18);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Helsinki–Vantaai', 60.32, 24.96, 11);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Rhodes', 36.4, 28.08, 16);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Treviso', 45.65, 12.19, 4);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Frankfurti', 50.04, 8.56, 12);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Köln–Bonn', 50.86, 7.14, 13);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Milano-Linatei', 45.44, 9.27, 14);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Velence Marco Polo', 45.5, 12.35, 15);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('John F. Kennedy', 73, 38.24, 22);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Sharm el-Sheikh', 27.97, 34.39, 18);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Bilbaói', 43.3, 2.91, 16);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Nizza Côte d’Azur', 43.66, 7.21, 17);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Nürnberg', 49.49, 11.08, 13);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Liverpool-John Lennon', 38.97, 15.86, 3);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Falcone-Borsellino', 38.18, 13.09, 4);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Washington Dulles', 56.40, 27.21, 22);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Sydney Kingsford Smith', 33.94, 151.17, 19);



CREATE TABLE Repulogep
    (id        NUMBER(10) NOT NULL,
     nev       VARCHAR2(20) NOT NULL,
     sebesseg  NUMBER(4) NOT NULL,
     ferohely  NUMBER(3) NOT NULL,
  CONSTRAINT Repulogep_PRIMARY_KEY PRIMARY KEY (id));

create sequence Repulogep_sequence;

create trigger Repulogep_trigger
    before insert on Repulogep
    for each row
    begin
      select Repulogep_sequence.nextval
         into:new.id
         from dual;
    end;
	/

insert into repulogep(nev, sebesseg, ferohely) values ('Airbus A300', 913, 320);
insert into repulogep(nev, sebesseg, ferohely) values ('Air Force One', 1015, 420);
insert into repulogep(nev, sebesseg, ferohely) values ('Airbus A350 XWB', 975, 150);
insert into repulogep(nev, sebesseg, ferohely) values ('ANT–20', 220, 40);
insert into repulogep(nev, sebesseg, ferohely) values ('Boeing 720', 1009, 425);
insert into repulogep(nev, sebesseg, ferohely) values ('Tupoljev Tu-134A', 850, 84);
insert into repulogep(nev, sebesseg, ferohely) values ('McDonnell Douglas MD-81', 813, 165);
insert into repulogep(nev, sebesseg, ferohely) values ('Boeing 743', 907, 660);
insert into repulogep(nev, sebesseg, ferohely) values ('Airbus A320', 861, 179);
insert into repulogep(nev, sebesseg, ferohely) values ('Antonov An-24', 450, 50);
insert into repulogep(nev, sebesseg, ferohely) values ('Lockheed L-1011 Tristar', 964, 400);
insert into repulogep(nev, sebesseg, ferohely) values ('Iljusin Il-86', 950, 350);
insert into repulogep(nev, sebesseg, ferohely) values ('Concorde', 2179, 144);
insert into repulogep(nev, sebesseg, ferohely) values ('Tupoljev Tu-144', 2500, 140);
insert into repulogep(nev, sebesseg, ferohely) values ('BAC VC-10', 915, 180);
insert into repulogep(nev, sebesseg, ferohely) values ('DHC-5 Buffalo', 467, 40);
insert into repulogep(nev, sebesseg, ferohely) values ('Fokker F28 Fellowship', 843, 85);
insert into repulogep(nev, sebesseg, ferohely) values ('Jakovlev Jak-42', 810, 120);
insert into repulogep(nev, sebesseg, ferohely) values ('BAe 146', 709, 111);
insert into repulogep(nev, sebesseg, ferohely) values ('Boeing 737', 856, 149);
insert into repulogep(nev, sebesseg, ferohely) values ('Dornier 228', 428, 19);
insert into repulogep(nev, sebesseg, ferohely) values ('Iljusin Il-18 Moszkva', 675, 85);
insert into repulogep(nev, sebesseg, ferohely) values ('McDonnell Douglas DC-10', 940, 345);



CREATE TABLE Jarat
    (id        				NUMBER(10) NOT NULL,
     felszallas_datum       DATE NOT NULL,
     repuloter_id_fel		NUMBER(10) NOT NULL,
     repuloter_id_le		NUMBER(10) NOT NULL,
     repulogep_id			NUMBER(10) NOT NULL,
     szabad_helyek			NUMBER(3) NOT NULL,
  CONSTRAINT Jarat_PRIMARY_KEY PRIMARY KEY (id));

create sequence Jarat_sequence;

create trigger Jarat_trigger
    before insert on Jarat
    for each row
    begin
      select Jarat_sequence.nextval
         into:new.id
         from dual;
    end;
	/


insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2022-DEC-25 17:30' ,2 ,1 ,1 ,320);
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2022-DEC-25 17:30' ,1 ,2 ,1 ,320);
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2022-AUG-18 14:30' ,3 ,13 ,4 ,40);
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2022-JUN-11 11:00' ,1 ,30 ,2 ,420);
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2022-MAY-14 08:00' ,19 ,3 ,10 ,42);
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2022-APR-19 10:00' ,10 ,22 ,18 ,58);
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2022-JUN-24 19:00' ,1 ,2 ,2 ,90);
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2022-DEC-10 18:00' ,21 ,2 ,3 ,81);
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2022-AUG-12 20:00' ,4 ,16 ,10 ,39);
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2022-APR-30 12:30' ,13 ,3 ,21 ,15);
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2022-JAN-25 20:30' ,27 ,5 ,14 ,40);
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2022-MAR-21 09:00' ,7 ,10 ,3 ,150);
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2022-FEB-17 10:30' ,1 ,23 ,7 ,120);
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2022-SEP-29 11:15' ,12 ,15 ,9 ,20);
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2022-NOV-01 22:45' ,2 ,4 ,12 ,30);
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2022-AUG-06 06:15' ,28 ,7 ,19 ,32);
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2022-APR-09 14:30' ,9 ,18 ,8 ,15);


CREATE TABLE Foglalas
    (id        				NUMBER(10) NOT NULL,
     felh_id		        NUMBER(10) NOT NULL,
     jarat_id				NUMBER(10) NOT NULL,
     fizetesi_mod_id		NUMBER(10) NOT NULL,
  CONSTRAINT Foglalas_PRIMARY_KEY PRIMARY KEY (id));

create sequence Foglalas_sequence;

create trigger Foglalas_trigger
    before insert on Foglalas
    for each row
    begin
      select Foglalas_sequence.nextval
         into:new.id
         from dual;
    end;
	/



CREATE TABLE JaratFigyRel
    (id              	NUMBER(10) NOT NULL,
     jarat_id       	NUMBER(10) NOT NULL,
	 figyelmeztetes_id	NUMBER(10) NOT NULL,
  CONSTRAINT JaratFigyRel_FOREIGN_KEY FOREIGN KEY (jarat_id) REFERENCES Jarat (id),
  CONSTRAINT JaratFigyRel_FOREIGN_KEY_2 FOREIGN KEY (figyelmeztetes_id) REFERENCES Figyelmeztetes (id),
  CONSTRAINT JaratFigyRel_PRIMARY_KEY PRIMARY KEY (id) );

create sequence JaratFigyRel_sequence;

create trigger JaratFigyRel_trigger
    before insert on JaratFigyRel
    for each row
    begin
      select JaratFigyRel_sequence.nextval
         into:new.id
         from dual;
    end;
	/


insert into JaratFigyRel(jarat_id, figyelmeztetes_id) values (1,1);
insert into JaratFigyRel(jarat_id, figyelmeztetes_id) values (3,2);

CREATE TABLE Szalloda 
    (id              	NUMBER(10) NOT NULL,
     nev                VARCHAR2(30) NOT NULL,
     csillagok_szama    NUMBER(1) NOT NULL,
     varos_id           NUMBER(10) NOT NULL,
  CONSTRAINT Szalloda_FOREIGN_KEY FOREIGN KEY (varos_id) REFERENCES Varos (id),
  CONSTRAINT Szalloda_PRIMARY_KEY PRIMARY KEY (id));

create sequence Szalloda_sequence;

create trigger Szalloda_trigger
    before insert on Szalloda
    for each row
    begin
      select Szalloda_sequence.nextval
         into:new.id
         from dual;
    end;
	/

insert into Szalloda(nev, csillagok_szama, varos_id) values('Hilton', 5, 1);
insert into Szalloda(nev, csillagok_szama, varos_id) values('Hard Rock Hotel Budapest', 5, 1);
insert into Szalloda(nev, csillagok_szama, varos_id) values('Stories Boutique Hotel', 4, 1);
insert into Szalloda(nev, csillagok_szama, varos_id) values('Pullman Berlin Schweizerhof', 5, 2);
insert into Szalloda(nev, csillagok_szama, varos_id) values('Hotel Berlin', 3, 2);
insert into Szalloda(nev, csillagok_szama, varos_id) values('The Savoy', 5, 3);
insert into Szalloda(nev, csillagok_szama, varos_id) values('Stanza Mare Beach Front', 4, 3);
insert into Szalloda(nev, csillagok_szama, varos_id) values('Montcalm Royal London House', 5, 4);
insert into Szalloda(nev, csillagok_szama, varos_id) values('The Londoner', 5, 4);
insert into Szalloda(nev, csillagok_szama, varos_id) values('Inhabit', 4, 4);





CREATE OR REPLACE TRIGGER jarat_szabadhely_csokkentes
AFTER INSERT
ON jegy
FOR EACH ROW
BEGIN    
    UPDATE jarat SET szabad_helyek = szabad_helyek - 1 WHERE id = (SELECT jarat_id FROM FOGLALAS WHERE id = :NEW.foglalasi_id);
END;
/

CREATE OR REPLACE TRIGGER szabad_helyek_valtozasa
AFTER UPDATE OF ferohely
ON repulogep
FOR EACH ROW
BEGIN
    UPDATE jarat akt SET szabad_helyek = :NEW.ferohely WHERE repulogep_id = :NEW.id AND (SELECT COUNT(*) FROM FOGLALAS WHERE akt.id = foglalas.jarat_id) = 0;
END;
/
