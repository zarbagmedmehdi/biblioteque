CREATE TABLE employe_2 (
	id_pers number(10),
	login varchar2(40),
	password varchar2(255),
	nom varchar2(40),
	adresse varchar2(100),
	statut varchar2(40)
);

CREATE TABLE etudiant_info_2 (
	id_etu number(10),
	nom varchar2(40),
	adresse varchar2(100),
	cursus varchar2(60)
);

CREATE TABLE etudiant_emprunt (
	id_etu number(10),
	cne varchar2(40),
	nb_emprunts number(1),
	sanctione number(1),
	jeton number(1)
);

CREATE TABLE ouvrage_2 (
	id_ouv number(10),
	titre varchar2(40),
	editeur varchar2(40),
	annee number(4),
	domaine varchar2(40),
	stock number(3),
	id_aut number(10)
);

CREATE TABLE auteur (
	id_aut number(10),
	nom_auteur varchar2(40),
	jeton number(1)
);

CREATE TABLE pret_2 (
	id_pret number(10),
	date_empreunt date,
	date_retour date,
	date_retour_limite date,
	id_etu number(10),
	id_ouv number(10),
	id_pers number(10)
);

ALTER TABLE employe_2 ADD CONSTRAINT employe_pk primary key (id_pers);
ALTER TABLE etudiant_info_2 ADD CONSTRAINT etudiant_info_pk primary key (id_etu);
ALTER TABLE etudiant_emprunt ADD CONSTRAINT etudiant_emprunt_pk primary key (id_etu);
ALTER TABLE ouvrage_2 ADD CONSTRAINT ouvrage_pk primary key (id_ouv);
ALTER TABLE auteur ADD CONSTRAINT auteur_pk primary key (id_aut);
ALTER TABLE pret_2 ADD CONSTRAINT pret_pk primary key (id_pret);

ALTER TABLE ouvrage_2 ADD CONSTRAINT auteur_fk foreign key (id_aut) references auteur(id_aut);
ALTER TABLE pret_2 ADD CONSTRAINT etudiant_fk foreign key (id_etu) references etudiant_emprunt(id_etu);
ALTER TABLE pret_2 ADD CONSTRAINT ouvrage_fk foreign key (id_ouv) references ouvrage_2(id_ouv);
ALTER TABLE pret_2 ADD CONSTRAINT employe_fk foreign key (id_pers) references employe_2(id_pers);

CREATE OR REPLACE SYNONYM ouvrage_1 FOR ouvrage_1@FST1;
CREATE OR REPLACE SYNONYM pret_1 FOR pret_1@FST1;

CREATE OR REPLACE SYNONYM seq_employe FOR seq_employe@FST1;
CREATE OR REPLACE SYNONYM seq_etudiant FOR seq_etudiant@FST1;
CREATE OR REPLACE SYNONYM seq_ouvrage FOR seq_ouvrage@FST1;
CREATE OR REPLACE SYNONYM seq_auteur FOR seq_auteur@FST1;
CREATE OR REPLACE SYNONYM seq_pret FOR seq_pret@FST1;









CREATE OR REPLACE VIEW ouvrage(titre,editeur,auteur,annee,domaine,stock,site) AS
SELECT titre, editeur,a.nom_auteur as auteur, annee, domaine, stock, 'FST'
FROM ouvrage_1 o, auteur a
WHERE o.id_aut=a.id_aut
UNION
SELECT titre, editeur,a.nom_auteur as auteur, annee, domaine, stock, 'FSSM'
FROM ouvrage_2 o, auteur a
WHERE o.id_aut=a.id_aut;



CREATE OR REPLACE VIEW etudiant (cne, nom, adresse, cursus, nb_emprunts, sanctione) AS
SELECT cne, nom, adresse, cursus, nb_emprunts, sanctione
FROM etudiant_emprunt , etudiant_info_2
WHERE etudiant_emprunt.id_etu=etudiant_info_2.id_etu;



CREATE OR REPLACE VIEW etudiant_pret (cne, id_ouv, titre, date_empreunt, date_retour, date_retour_limite) AS
SELECT cne, o.id_ouv, titre, date_empreunt, date_retour, date_retour_limite
FROM etudiant_emprunt ee, ouvrage_2 o, pret_2 p
WHERE p.id_etu= ee.id_etu AND p.id_ouv=o.id_ouv;






CREATE OR REPLACE VIEW pret AS
    SELECT * FROM pret_1
    UNION
    SELECT * FROM pret_2;


CREATE OR REPLACE VIEW etudiants (id_etu,cne, nom, adresse,universite, cursus, nb_emprunts, sanctione) AS
SELECT ei.id_etu, cne, nom, adresse, 'FSSM', cursus, nb_emprunts, sanctione
FROM ETUDIANT_EMPRUNT ee , ETUDIANT_INFO_2 ei WHERE ee.id_etu=ei.id_etu
UNION
SELECT ei.id_etu, cne, nom, adresse, 'FST', cursus, nb_emprunts, sanctione
FROM etudiant_emprunt ee , ETUDIANT_INFO_1@FST1 ei WHERE ee.id_etu=ei.id_etu;


