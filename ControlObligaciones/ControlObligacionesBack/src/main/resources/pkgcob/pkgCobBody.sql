create or replace 
PACKAGE BODY PKG_COB AS

  /*
  ******************************************************************************
  procedimiento para generar un conjunto de numeros de resoluciones y guardarlos en una tabla temporal
  */
  procedure COBS_GENAGRNUMRES (V_RETORNO OUT varchar2,RESOLMOTIVO in varchar2,
  flgCommit number,boids in TIPO_LISTA_DATOS, numerosControl in TIPO_LISTA_DATOS) AS

  v_identificador varchar2(50 CHAR);
  v_contador PLS_INTEGER;
  v_sql varchar2(150 CHAR);
  v_datosResolucion TIPO_LISTA_PARAM;
 
  BEGIN
    v_sql := 'insert into sgtt_numresolucion (numerocontrol,numeroresolucion) VALUES (:1, :2)';
    v_contador:=0;
    if boids.count > 0 and numerosControl.count > 0 then
        --recorre para cada conjunto de boid y numero control para generar su
        --numero de resolucion
        for i in 1..numeroscontrol.count loop
          v_identificador := PKG_COB.COBF_GENRESOLUCION(P_BOID => boids(i),P_RESOLMOTIVO => RESOLMOTIVO);
          v_datosResolucion := PKG_COB.COBF_SPLITSTR(v_identificador,'-');
          execute immediate v_sql using numerosControl(i), v_datosResolucion(1);
          V_CONTADOR:=V_CONTADOR+1;
          if MOD(V_CONTADOR,1000) = 0 then
            if FLGCOMMIT = 1 then
              commit;
            end if;
          END IF;
        end LOOP;
        if FLGCOMMIT = 1 then
          commit;
        end if;
    end if;
    v_retorno:=v_contador;
    exception
      when others then
          rollback;
          raise;
          v_retorno:=v_contador;
  END COBS_GENAGRNUMRES;
  
  /*
  ******************************************************************************
  procedimiento para generar un conjunto de numeros de control y guardarlos en una tabla temporal
  */
  PROCEDURE COBS_GENAGRNUMCON (V_RETORNO OUT VARCHAR2,
  boids in TIPO_LISTA_DATOS, TIPODOCUMENTO IN NUMBER, etapavigilancia in number) AS

  v_contador PLS_INTEGER;
  type varchar_t is table of sgtt_numresolucion%rowtype;
  v varchar_t;
  v_low PLS_INTEGER;
  v_hight PLS_INTEGER;
  v_interval PLS_INTEGER := 10000;
  
  BEGIN
    v := varchar_t();
    v_contador:=0;
    if boids.count > 0 then
      --dbms_output.put_line(TO_CHAR(SYSTIMESTAMP, 'HH24:MI:SS.FF4'));
        for i in 1..boids.count LOOP
          v.extend;
          v(i).numerocontrol := PKG_COB.COBF_GENCONTROL(
              P_BOID => BOIDS(I),
              P_TIPODOCUMENTO => TIPODOCUMENTO,
              P_ETAPAVIGILANCIA => ETAPAVIGILANCIA);
          v(i).numeroresolucion := BOIDS(I);
        END LOOP;
        --dbms_output.put_line(TO_CHAR(SYSTIMESTAMP, 'HH24:MI:SS.FF4'));
        
        v_low:=1;
        v_hight:=v_interval;
        if v_hight > boids.count then
            v_hight:=boids.count;
        end if;
        LOOP
          FORALL i in v_low..v_hight
            insert /*+ APPEND_VALUES */ into sgtt_numresolucion values v(i);
          commit;
          if v_hight = boids.count then
            exit;
          end if;
          
          v_low := v_low + v_interval;
          v_hight := v_hight + v_interval;
          if v_hight > boids.count then
            v_hight:=boids.count;
          end if;
          --dbms_output.put_line('v_low:'||v_low||', v_hight:'||v_hight);
        END LOOP;
        v_contador := v_hight;
        --dbms_output.put_line(TO_CHAR(SYSTIMESTAMP, 'HH24:MI:SS.FF4'));
    end if;
    v_retorno:=v_contador;
    exception
      when others then
          rollback;
          raise;
          V_RETORNO:=V_CONTADOR;
  END COBS_GENAGRNUMCON;
  
  
  /*
  ******************************************************************************
  procedimiento para lanzar jobs de COBS_GENAGRNUMCON y que sea rapido el procesamiento de boids
  */
  PROCEDURE COBS_GENNUMCTRLLT (V_RETORNO OUT VARCHAR2,
  boids in TIPO_LISTA_DATOS, TIPODOCUMENTO IN NUMBER, etapavigilancia in number) AS

  jobnum1 number;
  cadena varchar2(4000 CHAR);
  v_contador PLS_INTEGER:=0;
  v_interval PLS_INTEGER := 115;
  
  BEGIN
    if boids.count > 0 then
        --dbms_output.put_line(TO_CHAR(SYSTIMESTAMP, 'HH24:MI:SS.FF4'));
        loop
            v_contador:=v_contador+1;
            if v_contador > boids.count then
                exit;
            end if;
            cadena:= cadena||''''||boids(v_contador)||''',';
            if mod(v_contador,v_interval) = 0 then
                cadena:=substr(cadena,1,length(cadena)-1);
                DBMS_JOB.SUBMIT(jobnum1,
                'declare retorno varchar2(8);begin pkg_cob.COBS_GENAGRNUMCON(retorno, TIPO_LISTA_DATOS('||cadena||'),'||TIPODOCUMENTO||','||etapavigilancia||');END;');
                COMMIT;
                cadena:='';
            end if;
        end loop;
        if length(cadena)>0 then
              cadena:=substr(cadena,1,length(cadena)-1);
              DBMS_JOB.SUBMIT(jobnum1, 
              'declare retorno varchar2(8);begin pkg_cob.COBS_GENAGRNUMCON(retorno,TIPO_LISTA_DATOS('||cadena||'),'||TIPODOCUMENTO||','||etapavigilancia||');END;');
              COMMIT;
              cadena:='';
        end if;
        --dbms_output.put_line(TO_CHAR(SYSTIMESTAMP, 'HH24:MI:SS.FF4'));
    end if;
    v_retorno:=1;
    exception
      when others then
          rollback;
          raise;
          V_RETORNO:=-1;
  END COBS_GENNUMCTRLLT;
  
  
  /*
  ******************************************************************************
  procedimiento donde se guarda un documento y sus detalles y regresa el estatus si fue correcto o incorrecto
  */
  procedure COBS_GENDOCDETDOC (V_RETORNO OUT varchar2, P_DOC in TIPO_LISTA_PARAM, P_DOCDET in TIPO_LISTA_PARAM) AS
  I INTEGER;
  v_datosDoc TIPO_LISTA_PARAM;
  v_datosDet TIPO_LISTA_PARAM;
  v_datosUbica TIPO_LISTA_PARAM;
  v_numeroControl varchar2(40 CHAR);
  v_sqlInsertDoc VARCHAR2(500 CHAR);
  v_sqlInsertDet VARCHAR2(500 CHAR);
  v_detalleError VARCHAR2(300 CHAR);
  
begin
  V_SQLINSERTDOC:='INSERT /*+ APPEND_VALUES */ INTO SGTT_DOCUMENTO (NUMEROCONTROL,NUMEROCONTROLPADRE,FECHANOTIFICACION,FECHAIMPRESION,RFC,BOID,FECHAVENCIMIENTODOCTO,IDETAPAVIGILANCIA,FECHANOLOCALIZADOCONTRIBUYENTE,IDVIGILANCIA,ESULTIMOGENERADO,ULTIMOESTADO,FECHAREGISTRO,CONSIDERARENUENCIA, IDADMONLOCAL, CODIGOPOSTAL, IDCRH, IDENTIDADFEDERATIVA, IDTIPOPERSONA) VALUES (:1, :2, :3, :4, :5, :6, :7, :8, :9, :10, :11, :12, sysdate, :13, :14, :15, :16, :17, :18)';
  V_SQLINSERTDET:='INSERT /*+ APPEND_VALUES */ INTO SGTT_DETALLEDOC (NUMEROCONTROL,CLAVEICEP,IDOBLIGACION,EJERCICIO,IDPERIODO,FECHAVENCIMIENTOOBLIGACION,FECHACUMPLIMIENTO,IDPERIODICIDAD,IDSITUACIONICEP,IDREGIMEN,IMPORTEPAGAR,IDTIPODECLARACION,TIENEMULTAEXTEMPORANEIDAD,TIENEMULTACOMPLEMENTARIA, ESTADOICEPEC ) VALUES (:1,:2,:3,:4,:5,:6,:7,:8,:9,:10,:11,:12,:13,:14, NULL)';
  v_datosDoc:=PKG_COB.COBF_SPLITSTR(P_DOC(1),',');
  if v_datosDoc.COUNT>0 then
      FOR I IN 1..v_datosDoc.COUNT LOOP
          if v_datosDoc(I)='null' then
              v_datosDoc(I):=null;
          end if;
      END LOOP;
      
      SELECT /*+INDEX_SS(nr) FIRST_ROWS(1) */ nr.numerocontrol into v_numeroControl
      FROM sgtt_numresolucion nr WHERE nr.numeroresolucion = v_datosDoc(5) and rownum=1;
      
      if substr(v_numeroControl,17,length(v_numeroControl))='00000' then
          v_detalleError:='errorUbicacion';
      else
          v_datosUbica:=PKG_COB.COBF_SPLITSTR(v_numeroControl,'-');
          FOR I IN 1..v_datosUbica.COUNT LOOP
              if v_datosUbica(I)='null' then
                  v_datosUbica(I):=null;
              end if;
          END LOOP;
          begin
              execute immediate V_SQLINSERTDOC using v_datosUbica(1),v_datosDoc(1),v_datosDoc(2),v_datosDoc(3),v_datosDoc(4)
                ,to_number(v_datosDoc(5)),v_datosDoc(6),to_number(v_datosDoc(7)),v_datosDoc(8),to_number(v_datosDoc(9)),to_number(v_datosDoc(10)),to_number(v_datosDoc(11)),to_number(v_datosDoc(12))
                ,v_datosUbica(2),v_datosUbica(3),to_number(v_datosUbica(4)),to_number(v_datosUbica(5)),v_datosUbica(6);
              FOR I IN 1..P_DOCDET.COUNT LOOP
                  v_datosDet:=PKG_COB.COBF_SPLITSTR(P_DOCDET(I),',');
                  FOR I IN 1..v_datosDet.COUNT LOOP
                      if v_datosDet(I)='null' then
                          v_datosDet(I):=null;
                      end if;
                  END LOOP;
                  execute immediate V_SQLINSERTDET using v_datosUbica(1),v_datosDet(1),v_datosDet(2),to_number(v_datosDet(3)),to_number(v_datosDet(4))
                    ,v_datosDet(5),v_datosDet(6),v_datosDet(7),to_number(v_datosDet(8)),to_number(v_datosDet(9)),v_datosDet(10),v_datosDet(11),to_number(v_datosDet(12))
                    ,to_number(v_datosDet(13));
              end loop;
              commit;
              v_detalleError:='ok';
              exception
              when others then
                  v_detalleError:='Error: '||SUBSTR(SQLERRM, 1, 100);
                  rollback;
          end;
      end if;
      V_RETORNO:=v_detalleError;
  end if;
end COBS_GENDOCDETDOC;

  /*
  ******************************************************************************
  procedimiento para borrar los datos de la tabla sgtt_numresolucion y liberar alamcenamiento
  */
  PROCEDURE COBS_TRUNCATETABLERESOL AS
  BEGIN
    execute immediate 'TRUNCATE TABLE sgtt_numresolucion REUSE STORAGE';
  END COBS_TRUNCATETABLERESOL;

  /*
  ******************************************************************************
  procedimiento para borrar los datos de la tabla sgtt_cumplimiento y liberar alamcenamiento
  */
  PROCEDURE SGTSP_BORRACUMPLIMIENTOS AS
  BEGIN
    execute immediate 'TRUNCATE TABLE sgtt_cumplimiento REUSE STORAGE';
  END SGTSP_BORRACUMPLIMIENTOS;

  /*
  ******************************************************************************
  procedimiento donde genera numero de control y se guarda un documento y sus detalles y regresa el estatus si fue correcto o incorrecto
  */
  procedure COBS_GENDOCUMDET (V_RETORNO OUT varchar2, P_DOC in TIPO_LISTA_PARAM, P_DOCDET in TIPO_LISTA_PARAM
  , TIPODOCUMENTO IN NUMBER, etapavigilancia in number) AS
  I INTEGER;
  v_datosDoc TIPO_LISTA_PARAM;
  v_datosDet TIPO_LISTA_PARAM;
  v_datosUbica TIPO_LISTA_PARAM;
  v_numeroControl varchar2(40 char);
  v_sqlInsertDoc VARCHAR2(500 CHAR);
  v_sqlInsertDet VARCHAR2(500 CHAR);
  v_sqlInsertBit VARCHAR2(500 CHAR);
  v_detalleError VARCHAR2(300 CHAR);
  
begin
  V_SQLINSERTDOC:='INSERT /*+ APPEND_VALUES */ INTO SGTT_DOCUMENTO (NUMEROCONTROL,NUMEROCONTROLPADRE,FECHANOTIFICACION,FECHAIMPRESION,RFC,BOID,FECHAVENCIMIENTODOCTO,IDETAPAVIGILANCIA,FECHANOLOCALIZADOCONTRIBUYENTE,IDVIGILANCIA,ESULTIMOGENERADO,ULTIMOESTADO,FECHAREGISTRO,CONSIDERARENUENCIA, IDADMONLOCAL, CODIGOPOSTAL, IDCRH, IDENTIDADFEDERATIVA, IDTIPOPERSONA) VALUES (:1, :2, :3, :4, :5, :6, :7, :8, :9, :10, :11, :12, sysdate, :13, :14, :15, :16, :17, :18)';
  V_SQLINSERTDET:='INSERT /*+ APPEND_VALUES */ INTO SGTT_DETALLEDOC (NUMEROCONTROL,CLAVEICEP,IDOBLIGACION,EJERCICIO,IDPERIODO,FECHAVENCIMIENTOOBLIGACION,FECHACUMPLIMIENTO,IDPERIODICIDAD,IDSITUACIONICEP,IDREGIMEN,IMPORTEPAGAR,IDTIPODECLARACION,TIENEMULTAEXTEMPORANEIDAD,TIENEMULTACOMPLEMENTARIA, ESTADOICEPEC ) VALUES (:1,:2,:3,:4,:5,:6,:7,:8,:9,:10,:11,:12,:13,:14, NULL)';
  V_SQLINSERTBIT:='INSERT /*+ APPEND_VALUES */ INTO SGTB_SGTDOCUMENTO (NUMEROCONTROL,IDESTADODOCTO,FECHAMOVIMIENTO) VALUES (:1,:2,sysdate)';
  v_datosDoc:=PKG_COB.COBF_SPLITSTR(P_DOC(1),',');
  if v_datosDoc.COUNT>0 then
      FOR I IN 1..v_datosDoc.COUNT LOOP
          if v_datosDoc(I)='null' then
              v_datosDoc(I):=null;
          end if;
      END LOOP;
      
      v_numeroControl:=PKG_COB.COBF_GENCONTROL(
          P_BOID => v_datosDoc(5),
          P_TIPODOCUMENTO => TIPODOCUMENTO,
          P_ETAPAVIGILANCIA => ETAPAVIGILANCIA);
      
      if substr(v_numeroControl,17,length(v_numeroControl))='00000' then
          v_detalleError:='errorUbicacion';
      else
          v_datosUbica:=PKG_COB.COBF_SPLITSTR(v_numeroControl,'-');
          FOR I IN 1..v_datosUbica.COUNT LOOP
              if v_datosUbica(I)='null' then
                  v_datosUbica(I):=null;
              end if;
          END LOOP;
          begin
              execute immediate V_SQLINSERTDOC using v_datosUbica(1),v_datosDoc(1),v_datosDoc(2),v_datosDoc(3),v_datosDoc(4)
                ,to_number(v_datosDoc(5)),v_datosDoc(6),to_number(v_datosDoc(7)),v_datosDoc(8),to_number(v_datosDoc(9)),to_number(v_datosDoc(10)),to_number(v_datosDoc(11)),to_number(v_datosDoc(12))
                ,v_datosUbica(2),v_datosUbica(3),to_number(v_datosUbica(4)),to_number(v_datosUbica(5)),v_datosUbica(6);
              execute immediate V_SQLINSERTBIT using v_datosUbica(1), to_number(v_datosDoc(11));
              FOR I IN 1..P_DOCDET.COUNT LOOP
                  v_datosDet:=PKG_COB.COBF_SPLITSTR(P_DOCDET(I),',');
                  FOR I IN 1..v_datosDet.COUNT LOOP
                      if v_datosDet(I)='null' then
                          v_datosDet(I):=null;
                      end if;
                  END LOOP;
                  execute immediate V_SQLINSERTDET using v_datosUbica(1),v_datosDet(1),v_datosDet(2),to_number(v_datosDet(3)),to_number(v_datosDet(4))
                    ,v_datosDet(5),v_datosDet(6),v_datosDet(7),to_number(v_datosDet(8)),to_number(v_datosDet(9)),v_datosDet(10),v_datosDet(11),to_number(v_datosDet(12))
                    ,to_number(v_datosDet(13));
              end loop;
              commit;
              v_detalleError:='ok';
              exception
              when others then
                  v_detalleError:='Error: '||SUBSTR(SQLERRM, 1, 100);
                  rollback;
          end;
      end if;
      V_RETORNO:=v_detalleError;
  end if;
end COBS_GENDOCUMDET;

  /*
  ******************************************************************************
  funcion para generar un numero de resolucion
  */
  FUNCTION COBF_GENRESOLUCION 
  (
  p_boid in varchar2,
  p_resolmotivo IN VARCHAR2  
  ) RETURN VARCHAR2 AS 

  v_return varchar2(50 char);
  v_identificador varchar2(10 char);
  v_clavealr varchar2(10 char);
  V_CLAVECRH varchar2(10 char);
  V_RFC varchar2(15 char);
  B_EXISTE BOOLEAN:=false;
  
  cursor cur_ubicacion is
    SELECT
     Z.CODIGOREGION AS ClaveALR,
     a.CODIGOPOSTAL as CODIGOPOSTAL,
     X.DESCRIPCION as CLAVECRH,
     C.NUMEROENTIDAD as CVEENTIDADFED
     FROM
     RFCP_DOMICILIO@RFCD_DBLINK a,
     RFCC_ENTIDADFEDERA@RFCD_DBLINK C,
     RFCC_REGION@RFCD_DBLINK X,
     (select CLAVEREGION,CODIGOREGION,DESCRIPCION
     FROM RFCC_REGION@RFCD_DBLINK 
     WHERE IDTIPOREGION IN
     (select IDCATALOGO
     from RFCC_CATALOGO@RFCD_DBLINK
     WHERE TIPO='TIPOREGION' and CLAVEELEMENTO='ALR' and 
     FECHAINICIOVIGENCIA<=SYSDATE AND (FECHAFINVIGENCIA>SYSDATE OR FECHAFINVIGENCIA IS NULL))
     ) Z
     where A.IDPERSONA=p_boid AND
     a.IDTIPODOMICILIO= '32'
     AND (A.FECHAFINVIGENCIA > SYSDATE OR A.FECHAFINVIGENCIA IS NULL)
     AND A.FECHAINICIOVIGENCIA <= SYSDATE
     AND C.CLAVEENTIDADFED(+) = A.CLAVEENTIDADFED -- Estado
     AND X.IDREGION = A.IDCRH
     and X.IDTIPOREGION in (select IDCATALOGO
         from RFCC_CATALOGO@RFCD_DBLINK
         WHERE TIPO='TIPOREGION' and CLAVEELEMENTO='CRH' and 
         FECHAINICIOVIGENCIA<=SYSDATE AND (FECHAFINVIGENCIA>SYSDATE OR FECHAFINVIGENCIA IS NULL))
     and Z.CLAVEREGION = X.CLAVEPADRE
    AND (A.FECHACREACION = 
    (SELECT MAX (B2S1.FECHACREACION)
    FROM RFCP_DOMICILIO@RFCD_DBLINK B2S1
    WHERE B2S1.IDPERSONA = A.IDPERSONA
    AND B2S1.IDTIPODOMICILIO = '32'
    AND B2S1.FECHAINICIOVIGENCIA <= SYSDATE
    AND (B2S1.FECHAFINVIGENCIA > SYSDATE OR B2S1.FECHAFINVIGENCIA IS NULL)) OR A.FECHAMODIFICACION IS NULL)
     and rownum=1;
 
  registro_ubicacion cur_ubicacion%ROWTYPE;
  
begin
  --obtener el id de etapa de tipo de documento
  select to_char(nvl(idmultacob,0),'00') into v_identificador
  from sgtc_multacob
  where constanteresolmotivo = p_resolmotivo and FECHAFIN is null;
  
  --se concatena el identificador de etapa de documento
  v_Return := v_Return || v_identificador;

  --se concatena la fecha actual
  v_Return := v_Return || to_char(sysdate,'DDMMYY');

  --se concatena la secuencia
  v_Return := v_Return || trim(to_char(SGTQ_NUMEROCONTROL.NEXTVAL,'0000000'));

  --se concatena C
  v_Return := v_Return || 'C';

  --obtencion de datos de ubicacion
  open cur_ubicacion;
  FETCH cur_ubicacion into registro_ubicacion;
  b_existe := cur_ubicacion%NOTFOUND;
  
  select
  TRIM(TO_CHAR(NVL(registro_ubicacion.ClaveALR,0),'00')),
  trim(to_char(nvl(registro_ubicacion.clavecrh,0),'000')) into v_clavealr,v_clavecrh
  from DUAL;
  CLOSE cur_ubicacion;
  
  if B_EXISTE then
    DBMS_OUTPUT.PUT_LINE('en caso que el anterior query no tenga datos');
  
     select
      TRIM(TO_CHAR(NVL(Z.CODIGOREGION,0),'00')),
      trim(to_char(nvl(X.DESCRIPCION,0),'000')) into v_clavealr,v_clavecrh
     from
     RFCP_DOMICILIO@RFCD_DBLINK A,
     RFCC_ENTIDADFEDERA@RFCD_DBLINK C,
     RFCC_REGION@RFCD_DBLINK X,
     (select CLAVEREGION,CODIGOREGION,DESCRIPCION
     FROM RFCC_REGION@RFCD_DBLINK 
     WHERE IDTIPOREGION IN
     (select IDCATALOGO
     from RFCC_CATALOGO@RFCD_DBLINK
     WHERE TIPO='TIPOREGION' and CLAVEELEMENTO='ALR' and 
     FECHAINICIOVIGENCIA<=SYSDATE AND (FECHAFINVIGENCIA>SYSDATE OR FECHAFINVIGENCIA IS NULL))
     ) Z
     where A.IDPERSONA=p_boid AND
     a.IDTIPODOMICILIO= '32'
     AND (A.FECHAFINVIGENCIA > SYSDATE OR A.FECHAFINVIGENCIA IS NULL)
     and a.FECHAINICIOVIGENCIA <= sysdate
     AND A.FECHAMODIFICACION = -- Domicilio más reciente
         (select max (B2S1.FECHAMODIFICACION)
         FROM RFCP_DOMICILIO@RFCD_DBLINK B2S1
         WHERE B2S1.IDPERSONA = A.IDPERSONA
         AND B2S1.IDTIPODOMICILIO = '32'
         and B2S1.FECHAINICIOVIGENCIA <= sysdate
         AND (B2S1.FECHAFINVIGENCIA > SYSDATE OR B2S1.FECHAFINVIGENCIA IS NULL))
     AND C.CLAVEENTIDADFED(+) = A.CLAVEENTIDADFED
     AND X.IDREGION = A.IDCRH
     and X.IDTIPOREGION in (select IDCATALOGO
         from RFCC_CATALOGO@RFCD_DBLINK
         WHERE TIPO='TIPOREGION' and CLAVEELEMENTO='CRH' and 
         FECHAINICIOVIGENCIA<=SYSDATE AND (FECHAFINVIGENCIA>SYSDATE OR FECHAFINVIGENCIA IS NULL))
     and Z.CLAVEREGION = X.CLAVEPADRE
    AND (A.FECHACREACION = 
    (SELECT MAX (B2S1.FECHACREACION)
    FROM RFCP_DOMICILIO@RFCD_DBLINK B2S1
    WHERE B2S1.IDPERSONA = A.IDPERSONA
    AND B2S1.IDTIPODOMICILIO = '32'
    AND B2S1.FECHAINICIOVIGENCIA <= SYSDATE
    AND (B2S1.FECHAFINVIGENCIA > SYSDATE OR B2S1.FECHAFINVIGENCIA IS NULL)) OR A.FECHAMODIFICACION IS NULL)
     and rownum=1;
  END IF;

  --obtener rfc
  select nvl(trim(pf.rfc),trim(pm.rfc)) into V_RFC
  from rfcp_persona@RFCD_DBLINK person 
  left join RFCP_PERSONAFISICA@RFCD_DBLINK pf on pf.idpersona=person.idpersona
  left join RFCP_PERSONAMORAL@RFCD_DBLINK pm on pm.idpersona=person.idpersona
  where  person.idPersona = p_boid;

  v_return := v_return || v_clavealr || v_clavecrh||'-'||V_RFC;
  
  --DBMS_OUTPUT.PUT_LINE('v_Return = ' || v_Return);
  return trim(v_return);
  exception
    when no_data_found
       then return trim(v_return||'00000');
END COBF_GENRESOLUCION;

/*
******************************************************************************
funcion para generar un numero de control
*/
FUNCTION COBF_GENCONTROL 
(
  P_BOID in varchar2  
, P_TIPODOCUMENTO in number  
, P_ETAPAVIGILANCIA in number  
) return varchar2 as 

  v_return varchar2(50 char);
  v_identificador varchar2(10 char);
  v_clavealr varchar2(10 char);
  v_codigoPostal VARCHAR2(10 char);
  v_clavecrh varchar2(10 char);
  V_ENTIDADFED VARCHAR2(10 char);
  v_tipoPersona varchar2(5 char);
  B_EXISTE BOOLEAN:=false;
  
  cursor cur_ubicacion is
    SELECT /*+ FIRST_ROWS(1) */
     Z.CODIGOREGION AS ClaveALR,
     a.CODIGOPOSTAL as CODIGOPOSTAL,
     X.DESCRIPCION AS clavecrh,
     C.NUMEROENTIDAD as CVEENTIDADFED
     FROM
     RFCP_DOMICILIO@RFCD_DBLINK a,
     RFCC_ENTIDADFEDERA@RFCD_DBLINK C,
     RFCC_REGION@RFCD_DBLINK X,
     (select CLAVEREGION,CODIGOREGION,DESCRIPCION
     FROM RFCC_REGION@RFCD_DBLINK 
     WHERE IDTIPOREGION IN
     (select IDCATALOGO
     from RFCC_CATALOGO@RFCD_DBLINK
     WHERE TIPO='TIPOREGION' and CLAVEELEMENTO='ALR' and 
     FECHAINICIOVIGENCIA<=SYSDATE AND (FECHAFINVIGENCIA>SYSDATE OR FECHAFINVIGENCIA IS NULL))
     ) Z
     where A.IDPERSONA=P_BOID AND
     a.IDTIPODOMICILIO= '32'
     AND (A.FECHAFINVIGENCIA > SYSDATE OR A.FECHAFINVIGENCIA IS NULL)
     AND A.FECHAINICIOVIGENCIA <= SYSDATE
     AND C.CLAVEENTIDADFED(+) = A.CLAVEENTIDADFED -- Estado
     AND X.IDREGION = A.IDCRH
     and X.IDTIPOREGION in (select IDCATALOGO
         from RFCC_CATALOGO@RFCD_DBLINK
         WHERE TIPO='TIPOREGION' and CLAVEELEMENTO='CRH' and 
         FECHAINICIOVIGENCIA<=SYSDATE AND (FECHAFINVIGENCIA>SYSDATE OR FECHAFINVIGENCIA IS NULL))
     and Z.CLAVEREGION = X.CLAVEPADRE
     AND (A.FECHACREACION = 
    (SELECT MAX (B2S1.FECHACREACION)
    FROM RFCP_DOMICILIO@RFCD_DBLINK B2S1
    WHERE B2S1.IDPERSONA = A.IDPERSONA
    AND B2S1.IDTIPODOMICILIO = '32'
    AND B2S1.FECHAINICIOVIGENCIA <= SYSDATE
    AND (B2S1.FECHAFINVIGENCIA > SYSDATE OR B2S1.FECHAFINVIGENCIA IS NULL)) OR A.FECHAMODIFICACION IS NULL)
     and rownum=1;
 
  registro_ubicacion cur_ubicacion%ROWTYPE;

begin
  --obtener el id de etapa de tipo de documento
  select to_char(nvl(idtipodocetapa,0),'00') into v_identificador
  from sgta_tipodocetapa te
  where TE.IDTIPODOCUMENTO = P_TIPODOCUMENTO
  and te.idetapavigilancia = P_ETAPAVIGILANCIA;
  
  --se concatena el identificador de etapa de documento
  v_Return := v_Return || v_identificador;

  --se concatena la fecha actual
  v_Return := v_Return || to_char(sysdate,'DDMMYY');

  --se concatena la secuencia
  v_Return := v_Return || trim(to_char(SGTQ_NUMEROCONTROL.NEXTVAL,'0000000'));

  --se concatena C
  v_Return := v_Return || 'C';

  --obtencion de datos de ubicacion
  open cur_ubicacion;
  FETCH cur_ubicacion into registro_ubicacion;
  b_existe := cur_ubicacion%NOTFOUND;
  
  select
    trim(to_char(nvl(registro_ubicacion.ClaveALR,0),'00')),
    registro_ubicacion.CODIGOPOSTAL,
    TRIM(TO_CHAR(NVL(registro_ubicacion.clavecrh,0),'000')),
    registro_ubicacion.CVEENTIDADFED into V_CLAVEALR,V_CODIGOPOSTAL,V_CLAVECRH,v_entidadFed
  from DUAL;
  CLOSE cur_ubicacion;
  
  if B_EXISTE then
    --DBMS_OUTPUT.PUT_LINE('en caso que el anterior query no tenga datos');
     select /*+ FIRST_ROWS(1) */
      trim(to_char(nvl(Z.CODIGOREGION,0),'00')),
      a.CODIGOPOSTAL,
      TRIM(TO_CHAR(NVL(X.DESCRIPCION,0),'000')),
      C.NUMEROENTIDAD into V_CLAVEALR,V_CODIGOPOSTAL,V_CLAVECRH,v_entidadFed
     from
     RFCP_DOMICILIO@RFCD_DBLINK A,
     RFCC_ENTIDADFEDERA@RFCD_DBLINK C,
     RFCC_REGION@RFCD_DBLINK X,
     (select CLAVEREGION,CODIGOREGION,DESCRIPCION
     FROM RFCC_REGION@RFCD_DBLINK 
     WHERE IDTIPOREGION IN
     (select IDCATALOGO
     from RFCC_CATALOGO@RFCD_DBLINK
     WHERE TIPO='TIPOREGION' and CLAVEELEMENTO='ALR' and 
     FECHAINICIOVIGENCIA<=SYSDATE AND (FECHAFINVIGENCIA>SYSDATE OR FECHAFINVIGENCIA IS NULL))
     ) Z
     where A.IDPERSONA=P_BOID AND
     a.IDTIPODOMICILIO= '32'
     AND (A.FECHAFINVIGENCIA > SYSDATE OR A.FECHAFINVIGENCIA IS NULL)
     and a.FECHAINICIOVIGENCIA <= sysdate
     AND A.FECHAMODIFICACION = -- Domicilio más reciente
         (select max (B2S1.FECHAMODIFICACION)
         FROM RFCP_DOMICILIO@RFCD_DBLINK B2S1
         WHERE B2S1.IDPERSONA = A.IDPERSONA
         AND B2S1.IDTIPODOMICILIO = '32'
         and B2S1.FECHAINICIOVIGENCIA <= sysdate
         AND (B2S1.FECHAFINVIGENCIA > SYSDATE OR B2S1.FECHAFINVIGENCIA IS NULL))
     AND C.CLAVEENTIDADFED(+) = A.CLAVEENTIDADFED
     AND X.IDREGION = A.IDCRH
     and X.IDTIPOREGION in (select IDCATALOGO
         from RFCC_CATALOGO@RFCD_DBLINK
         WHERE TIPO='TIPOREGION' and CLAVEELEMENTO='CRH' and 
         FECHAINICIOVIGENCIA<=SYSDATE AND (FECHAFINVIGENCIA>SYSDATE OR FECHAFINVIGENCIA IS NULL))
     and Z.CLAVEREGION = X.CLAVEPADRE
     AND (A.FECHACREACION = 
    (SELECT MAX (B2S1.FECHACREACION)
    FROM RFCP_DOMICILIO@RFCD_DBLINK B2S1
    WHERE B2S1.IDPERSONA = A.IDPERSONA
    AND B2S1.IDTIPODOMICILIO = '32'
    AND B2S1.FECHAINICIOVIGENCIA <= SYSDATE
    AND (B2S1.FECHAFINVIGENCIA > SYSDATE OR B2S1.FECHAFINVIGENCIA IS NULL)) OR A.FECHAMODIFICACION IS NULL)
     and rownum=1;
  END IF;
  
  v_return := v_return || v_clavealr || v_clavecrh;
  
  SELECT CLAVEELEMENTO into v_tipoPersona
  FROM RFCC_CATALOGO@RFCD_DBLINK A,
     rfcp_persona@RFCD_DBLINK b
  WHERE A.IDCATALOGO = B.IDTIPOPERSONA
  AND A.TIPO = 'TIPOPERSONA'
  and b.idpersona = P_BOID;
  
  --se agrega la informacion de ubicacion
  v_return := v_return ||'-'|| v_clavealr||'-'||v_codigopostal||'-'||v_clavecrh||'-'||v_entidadFed||'-'||v_tipoPersona;
  
  --DBMS_OUTPUT.PUT_LINE('v_Return = ' || v_Return);
  return trim(v_return);
  exception
    when no_data_found
       then return trim(v_return||'00000');
end COBF_GENCONTROL;

/*
******************************************************************************
funcion que regresa un array a partir de una cadena separada por un caracter
*/
function COBF_SPLITSTR (P_STR IN VARCHAR2, P_SEPARADOR IN VARCHAR2) return TIPO_LISTA_PARAM as
  MY_ARRAY TIPO_LISTA_PARAM:=TIPO_LISTA_PARAM();
  v_contador PLS_INTEGER:=1;
BEGIN
  FOR CURRENT_ROW IN (
    with test as 
      (select P_STR from dual)
      select regexp_substr(P_STR, '[^'||P_SEPARADOR||']+', 1, rownum) SPLIT
      from test
      connect by level <= length (regexp_replace(P_STR, '[^'||P_SEPARADOR||']+'))  + 1)
  LOOP
    MY_ARRAY.extend;
    MY_ARRAY(v_contador) := CURRENT_ROW.SPLIT;
    v_contador:=v_contador+1;
  END LOOP;
  return MY_ARRAY;
END COBF_SPLITSTR;


END PKG_COB;