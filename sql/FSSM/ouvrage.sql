--procedure d'insertion d'ouvrage
CREATE OR REPLACE PROCEDURE insert_ouvrage
(titre ouvrage.titre%TYPE,
 editeur ouvrage.editeur%TYPE,
 annee ouvrage.annee%TYPE,
 domaine ouvrage.domaine%TYPE,
 stock ouvrage.stock%TYPE,
 site ouvrage.site%TYPE,
 id_aut number)
    IS
    site_excep EXCEPTION;
BEGIN
    IF site = 'FST' THEN
        INSERT INTO ouvrage_1 VALUES(seq_ouvrage.nextval,titre,editeur,annee,domaine,stock,id_aut);
    ELSIF site = 'FSSM' THEN
        INSERT INTO ouvrage_2 VALUES(seq_ouvrage.nextval,titre,editeur,annee,domaine,stock,id_aut);
    ELSE
        RAISE site_excep;
    END IF;
EXCEPTION
    WHEN site_excep THEN
        DBMS_OUTPUT.PUT_LINE('site inconnu');
END;
/

--creation du ref cursor
CREATE OR REPLACE PACKAGE types AS TYPE ref_cursor IS REF CURSOR; END;
/

--recherche d'ouvrage par titre
CREATE OR REPLACE FUNCTION findOeuvreByTitre(p_titre VARCHAR2)
    RETURN types.ref_cursor
AS
    oeuvre_cursor types.ref_cursor;
BEGIN
    IF p_titre IS NULL THEN
        OPEN Oeuvre_cursor FOR
            SELECT o.id_ouv, titre, editeur, annee, domaine, stock ,a.nom_auteur
            FROM ouvrage_2 o, auteur a
            WHERE o.id_aut=a.id_aut;
    ELSE
        OPEN Oeuvre_cursor FOR
            SELECT o.id_ouv, titre, editeur, annee, domaine, stock ,a.nom_auteur
            FROM ouvrage_2 o, auteur a
            WHERE o.id_aut=a.id_aut AND o.titre LIKE CONCAT('%',CONCAT(p_titre,'%'));
    END IF;
    RETURN Oeuvre_cursor;
END;
/

--supression d'ouvrage
CREATE OR REPLACE FUNCTION deleteOuvrage(p_id_ouv NUMBER)
    RETURN NUMBER
    IS
    result NUMBER :=0;
    v_count NUMBER:=0;
BEGIN
    SELECT COUNT(*) INTO v_count FROM pret WHERE id_ouv = p_id_ouv;
    IF v_COUNT = 0 THEN
        DELETE FROM ouvrage_2 WHERE id_ouv = p_id_ouv;
        result:=1;
    ELSE
        result:=-1;
    END IF;
    RETURN result;
END;
/

--modifier le stock de l'ouvrage
CREATE OR REPLACE FUNCTION updateStock(p_id_ouv NUMBER,p_nv_stock NUMBER)
    RETURN NUMBER
    IS
BEGIN
    UPDATE OUVRAGE_2 SET stock= p_nv_stock WHERE id_ouv=p_id_ouv;
    RETURN 1;
END;
/