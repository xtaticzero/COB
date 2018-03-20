
CREATE OR REPLACE type SIAT_SGT_ADMIN.TIPO_LISTA_DATOS as VARRAY(6000000) OF VARCHAR2(32 CHAR);
/
CREATE OR REPLACE SYNONYM SIAT_SGT.TIPO_LISTA_DATOS FOR SIAT_SGT_ADMIN.TIPO_LISTA_DATOS;
GRANT EXECUTE ON siat_sgt_admin.TIPO_LISTA_DATOS TO SGTR_SIAT;

CREATE OR REPLACE type SIAT_SGT_ADMIN.TIPO_LISTA_PARAM as VARRAY(500) OF VARCHAR2(120 CHAR);
/
CREATE OR REPLACE SYNONYM SIAT_SGT.TIPO_LISTA_PARAM FOR SIAT_SGT_ADMIN.TIPO_LISTA_PARAM;
GRANT EXECUTE ON SIAT_SGT_ADMIN.TIPO_LISTA_PARAM TO SGTR_SIAT;

create or replace 
package pkg_cob as 
/*
paquete creado para control de obligaciones
*/

  /*
  procedimiento para generar un conjunto de numeros de resoluciones y guardarlos en una tabla temporal
  */
  procedure COBS_GENAGRNUMRES (V_RETORNO OUT varchar2,RESOLMOTIVO in varchar2,
  flgCommit number,BOIDS in TIPO_LISTA_DATOS, NUMEROSCONTROL in TIPO_LISTA_DATOS);
  
  /*
  procedimiento para generar un conjunto de numeros de control y guardarlos en una tabla temporal
  */
  procedure COBS_GENAGRNUMCON (V_RETORNO OUT varchar2,
  boids in TIPO_LISTA_DATOS, TIPODOCUMENTO IN NUMBER, etapavigilancia in number);
  
  /*
  procedimiento para lanzar jobs de COBS_GENAGRNUMCON y que sea rapido el procesamiento de boids
  */
  procedure COBS_GENNUMCTRLLT (V_RETORNO OUT varchar2,
  boids in TIPO_LISTA_DATOS, TIPODOCUMENTO IN NUMBER, etapavigilancia in number);
  
  /*
  procedimiento donde se guarda un documento y sus detalles y regresa el estatus si fue correcto o incorrecto
  */
  procedure COBS_GENDOCDETDOC (V_RETORNO OUT varchar2, P_DOC in TIPO_LISTA_PARAM, P_DOCDET in TIPO_LISTA_PARAM);
  
  /*
  procedimiento para borrar los datos de la tabla sgtt_numresolucion y liberar alamcenamiento
  */
  PROCEDURE COBS_TRUNCATETABLERESOL;

  /*
  procedimiento para borrar los datos de la tabla sgtt_cumplimiento y liberar alamcenamiento
  */
  procedure SGTSP_BORRACUMPLIMIENTOS;
  
  /*
  procedimiento donde genera numero de control y se guarda un documento y sus detalles y regresa el estatus si fue correcto o incorrecto
  */
  procedure COBS_GENDOCUMDET (V_RETORNO OUT varchar2, P_DOC in TIPO_LISTA_PARAM, P_DOCDET in TIPO_LISTA_PARAM, 
  TIPODOCUMENTO IN NUMBER, etapavigilancia in number);
  
  /*
  funcion para generar un numero de resolucion
  */
  FUNCTION COBF_GENRESOLUCION (p_boid in varchar2, p_resolmotivo in varchar2 ) RETURN VARCHAR2;
  
  /*
  funcion para generar un numero de control
  */
  function COBF_GENCONTROL (P_BOID IN VARCHAR2, P_TIPODOCUMENTO IN NUMBER, P_ETAPAVIGILANCIA in number) return varchar2;
  
  /*
  funcion que regresa un array a partir de una cadena separada por un caracter
  */
  function COBF_SPLITSTR (P_STR IN VARCHAR2, P_SEPARADOR IN VARCHAR2) return TIPO_LISTA_PARAM;

END PKG_COB;