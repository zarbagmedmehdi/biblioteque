--procedure qui verifie si un etudiant non sanctioné a depassé la date limite pour rendre un ouvrage si oui elle le sanctionne
CREATE OR REPLACE PROCEDURE sanctionner
    IS
    CURSOR cur_etudiant_pret IS SELECT p.id_etu,count(*) AS nb_empr_non_rendu FROM pret p, etudiant_emprunt e WHERE p.id_etu=e.id_etu AND e.sanctione=0 AND p.date_retour IS NULL AND p.date_retour_limite < SYSDATE GROUP BY p.id_etu;
BEGIN
    FOR etudiant_pret IN cur_etudiant_pret LOOP
            IF etudiant_pret.nb_empr_non_rendu > 0 THEN
                UPDATE etudiant_emprunt SET sanctione=1 WHERE id_etu=etudiant_pret.id_etu;
            END IF;
        END LOOP;
END;
/


--procedure qui verifie si un etudiant sanctioné a rendu l'ouvrage apres avoir depasser la date limite . si oui elle retire la sanction
CREATE OR REPLACE PROCEDURE retirer_sanction
    IS
    CURSOR cur_etudiant_pret IS SELECT p.id_etu,count(*) AS nb_empr_non_rendu FROM pret p, etudiant_emprunt e WHERE p.id_etu=e.id_etu  AND e.sanctione=1 AND p.date_retour IS NOT NULL AND p.date_retour_limite < SYSDATE GROUP BY p.id_etu;
BEGIN
    FOR etudiant_pret IN cur_etudiant_pret LOOP
            IF etudiant_pret.nb_empr_non_rendu > 0 THEN
                UPDATE etudiant_emprunt SET sanctione=0 WHERE id_etu=etudiant_pret.id_etu;
            END IF;
        END LOOP;
END;
/

--execution de la creation du job responsable du lancement des procedure sanctionner et retirer sanction tout les jours a 18h
DECLARE
    num_job NUMBER;
BEGIN
    DBMS_JOB.SUBMIT(num_job,'sanctionner;',trunc(SYSDATE)+18/24, 'SYSDATE+ 1');
END;
/

DECLARE
    num_job NUMBER;
BEGIN
    DBMS_JOB.SUBMIT(num_job,'retirer_sanction;',trunc(SYSDATE)+18.1/24, 'SYSDATE+ 1');
END;
/

--il faut faire un commit pour prendre en consideration les jobs crées .
commit;

-- pour voir la liste des job : select * FROM USER_JOBS;
--pour supprimer un job : EXEC DBMS_JOB.REMOVE(le numero du job);
-- pour voir la date et timestamp du serveur : SELECT SYSDATE, CURRENT_TIMESTAMP  FROM DUAL;