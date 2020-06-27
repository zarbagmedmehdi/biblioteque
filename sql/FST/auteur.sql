--procedure de création d'auteur
CREATE OR REPLACE PROCEDURE insert_auteur(nom_auteur auteur.nom_auteur%TYPE)
    IS
BEGIN
    INSERT INTO auteur VALUES (seq_auteur.nextval, nom_auteur, 0);
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        DBMS_OUTPUT.PUT_LINE(' insert_auteur Violation de la clé primaire.');
END;
/

--Trigger de synchronisation de la table auteur
CREATE OR REPLACE TRIGGER prop_req_auteur
    BEFORE INSERT OR UPDATE OR DELETE ON auteur
    FOR EACH ROW
BEGIN
    IF INSERTING THEN
        IF :NEW.jeton = 0 THEN
            INSERT INTO auteur@FST2 VALUES(:NEW.id_aut, :NEW.nom_auteur,1);
        END IF;
    ELSIF UPDATING THEN
        IF :NEW.jeton = 0 THEN
            UPDATE auteur@FST2 SET nom_auteur= :NEW.nom_auteur, jeton=1 WHERE id_aut=:NEW.id_aut;
        END IF;
    ELSIF DELETING THEN
        IF :OLD.jeton = 0 THEN
            DELETE FROM auteur@FST2 WHERE id_aut=:OLD.id_aut;
        END IF;
    END IF;
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        DBMS_OUTPUT.PUT_LINE('Violation de la clé primaire.');
END;
/

--fonction de suppression d'auteur
CREATE OR REPLACE FUNCTION deleteAuteur(p_id_aut NUMBER)
    RETURN NUMBER
    IS
    v_count NUMBER:=0;
    res NUMBER:=0;
BEGIN
    SELECT COUNT(*) INTO v_count FROM OUVRAGE WHERE id_auteur=p_id_aut;
    IF v_count = 0 THEN
        DELETE FROM auteur WHERE id_aut=p_id_aut;
        res:=1;
    ELSE
        res:=-1;
    END IF;
    RETURN res;
END;
/