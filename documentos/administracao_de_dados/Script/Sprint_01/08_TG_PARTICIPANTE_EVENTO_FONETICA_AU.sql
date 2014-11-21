CREATE TRIGGER SIGEVEN.TG_PARTICIPANTE_EVENTO_FONETICA_AU
   ON  SIGEVEN.PARTICIPANTE_EVENTO 
   AFTER UPDATE AS 
BEGIN
DECLARE @nome_inserido VARCHAR(5000);
SET @nome_inserido = (SELECT NM_PARTICIPANTE FROM INSERTED)
DECLARE @sq_inserido INT;
SET @sq_inserido = (SELECT SQ_PARTICIPANTE_EVENTO FROM INSERTED)

DECLARE @nome_fonetico VARCHAR(5000);
SET @nome_fonetico = (SELECT SIGEVEN.FC_FONETIZAR(@nome_inserido, 0))
   UPDATE SIGEVEN.PARTICIPANTE_EVENTO SET SQ_FONETICO = @nome_fonetico WHERE SQ_PARTICIPANTE_EVENTO = @sq_inserido
END