--fonction pour preter un ouvrage a un etudiant
CREATE OR REPLACE FUNCTION preterOuvrage(p_date_emprunt DATE, p_date_retour_limite DATE, p_id_etu NUMBER, p_id_ouv NUMBER, p_id_emp NUMBER)
    RETURN NUMBER
    IS
    v_sanction NUMBER:=0;
    v_nb_emprunt NUMBER:=0;
    v_stock NUMBER:=0;
    res NUMBER:=0;
BEGIN
    SELECT nb_emprunts, sanctione INTO v_nb_emprunt, v_sanction FROM etudiants WHERE id_etu=p_id_etu;
    IF v_sanction <> 0 THEN
        res := -1;
    ELSIF v_nb_emprunt >= 3 THEN
        res := -2;
    ELSE
        SELECT stock INTO v_stock FROM OUVRAGE_1 WHERE id_ouv=p_id_ouv;
        IF v_stock <= 0 THEN
            res := -3;
        ELSE
            INSERT INTO PRET_1 (id_pret, date_empreunt, date_retour, date_retour_limite, id_etu, id_ouv, id_pers)
            VALUES (seq_pret.nextval, p_date_emprunt, null, p_date_retour_limite, p_id_etu, p_id_ouv, p_id_emp);
            UPDATE OUVRAGE_1 SET stock = stock-1 WHERE id_ouv=p_id_ouv;
            UPDATE ETUDIANT_EMPRUNT SET nb_emprunts = nb_emprunts+1 WHERE id_etu = p_id_etu;
            res := 1;
        END IF;
    END IF;
    RETURN res;
END;
/

--recherche des prets par cne de l'etudiant
CREATE OR REPLACE FUNCTION findPretByEtudiant(p_id_etu NUMBER)
    RETURN types.ref_cursor
AS
    pret_cursor types.ref_cursor;
BEGIN
    OPEN pret_cursor FOR
        SELECT id_pret,date_empreunt, date_retour, date_retour_limite, o.titre as ouvrage
        FROM pret_1 p, ouvrage_1 o WHERE p.id_ouv=o.id_ouv AND id_etu=p_id_etu;
    RETURN pret_cursor;
END;
/

--fonction pour recuperer l'ouvrage emprunter
CREATE OR REPLACE FUNCTION rendreOuvrage(p_id_pret NUMBER)
    RETURN NUMBER
    IS
    res NUMBER:=0;
    v_date_retour DATE;
    v_id_etu NUMBER:=0;
    v_id_ouv NUMBER:=0;
BEGIN
    SELECT date_retour, id_etu, id_ouv INTO v_date_retour, v_id_etu, v_id_ouv FROM pret_1 WHERE id_pret=p_id_pret;
    IF v_date_retour IS NOT NULL THEN
        res:=-1;
    ELSE
        UPDATE pret_1 SET date_retour= SYSDATE WHERE id_pret=p_id_pret;
        UPDATE etudiant_emprunt SET nb_emprunts = nb_emprunts-1 WHERE id_etu=v_id_etu;
        UPDATE ouvrage_1 SET stock = stock+1 WHERE id_ouv=v_id_ouv;
        res:=1;
    END IF;
    RETURN res;
END;
/