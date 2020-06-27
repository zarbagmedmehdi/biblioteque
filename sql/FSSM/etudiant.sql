--rechercher etudiant par cne
CREATE OR REPLACE FUNCTION findEtudiantByCNE(p_cne VARCHAR2)
    RETURN types.ref_cursor
AS
    etudiant_cursor types.ref_cursor;
BEGIN
    OPEN etudiant_cursor FOR
        SELECT id_etu,cne, nom, adresse,universite, cursus, nb_emprunts, sanctione
        FROM etudiants WHERE cne LIKE CONCAT('%',CONCAT(p_cne,'%'));
    RETURN etudiant_cursor;
END;
/

--trigger pour synchroniser la duplication de la table etudiant au niveau de la modification
CREATE OR REPLACE TRIGGER prop_req_etudiant
    BEFORE UPDATE ON etudiant_emprunt
    FOR EACH ROW
BEGIN
    IF UPDATING THEN
        IF :NEW.jeton = 0 THEN
            UPDATE etudiant_emprunt@FST1 SET cne=:NEW.cne, nb_emprunts=:NEW.nb_emprunts, sanctione=:NEW.sanctione, jeton=1 WHERE id_etu=:NEW.id_etu;
        END IF;
    END IF;
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        DBMS_OUTPUT.PUT_LINE('Violation de la clé primaire.');
END;
/