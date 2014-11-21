CREATE FUNCTION [SIGEVEN].[FC_SOMENTE_LETRAS] ( @TEXTO VARCHAR (5000))
RETURNS VARCHAR (5000)
AS
BEGIN
DECLARE @LETRAS    VARCHAR(30)
DECLARE @QTD_TEXTO INT
DECLARE @CONTADOR  INT
DECLARE @QTD       INT
DECLARE @CONT      INT
DECLARE @CONT_C    INT
DECLARE @LETRA_ANT CHAR(1)
DECLARE @LETRA_T   CHAR(1)
DECLARE @LETRA_C   CHAR(1)
DECLARE @RESULTADO VARCHAR(5000)

SET @LETRAS = 'ABC�DEFGHIJKLMNOPQRSTUVXZWY ';

SET @QTD_TEXTO = (SELECT LEN(@TEXTO)) + 1
SET @CONTADOR  = 0
SET @RESULTADO = ''
SET @LETRA_ANT = (SELECT SUBSTRING(@TEXTO,1,1))
INICIO:
WHILE @CONTADOR < @QTD_TEXTO
BEGIN
  SET @CONTADOR = @CONTADOR + 1
  SET @LETRA_T = (SELECT SUBSTRING(@TEXTO,@CONTADOR,1))
  SET @CONT = (SELECT LEN(@LETRAS)) + 1
  SET @QTD = 0
  WHILE @QTD < @CONT
  BEGIN
     SET @QTD = @QTD + 1
     SET @LETRA_C = (SELECT SUBSTRING(@LETRAS,@QTD,1))
     IF @LETRA_C = @LETRA_T
     BEGIN
        IF @LETRA_ANT = ' '
           AND @LETRA_T = ' '
        BEGIN
           GOTO INICIO
        END
        ELSE
        BEGIN
           SET @RESULTADO = @RESULTADO + (SELECT SUBSTRING(@LETRAS,@QTD,1))
           SET @LETRA_ANT = @LETRA_T
           GOTO INICIO
        END
     END
  END
END

RETURN ( UPPER(@RESULTADO) )

END