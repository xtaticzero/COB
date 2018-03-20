package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author emmanuel
 */
public interface APVigilanciasLogSQL {
    String HEDER_SELECT     ="SELECT IDVIGILANCIA,IDADMONLOCAL,FECHAREGISTRO,DESCRIPCION FROM sgtt_logaprobarvig";
    
    String BODY_1_SELECT_IDVIGILANCIA      =" WHERE IDVIGILANCIA IN (";
    String BODY_2_SELECT_IDVIGILANCIA      =" )";
    
    String BODY_1_SELECT_IDADMONLOCAL      =" AND IDADMONLOCAL != ('";
    String BODY_2_SELECT_IDADMONLOCAL      ="'";
    String BODY_3_SELECT_IDADMONLOCAL      =" )";
    
    String BODY_1_SELECT_IDADMONCENTRAL      =" AND IDADMONLOCAL = ('";
    String BODY_2_SELECT_IDADMONCENTRAL     ="'";
    String BODY_3_SELECT_IDADMONCENTRAL      =" )";
    
    String PARAMETRO_IDADMONLOCAL_1      =" AND IDADMONLOCAL = ('";
    String PARAMETRO_IDADMONLOCAL_2      ="') ";
    
    
    String FOOTER_SELECT    =" AND DESCRIPCION IS NOT NULL";
    
    String SELECT_ERROR     ="SELECT IDVIGILANCIA,IDADMONLOCAL,FECHAREGISTRO,DESCRIPCION FROM sgtt_logaprobarvig WHERE IDVIGILANCIA = ? and IDADMONLOCAL = ?";
    String INSERT_ERROR     ="INSERT INTO sgtt_logaprobarvig(IDVIGILANCIA,IDADMONLOCAL,FECHAREGISTRO,DESCRIPCION) VALUES(?,?,SYSDATE,?)";
    String UPDATE_ERROR     ="UPDATE sgtt_logaprobarvig SET FECHAREGISTRO=SYSDATE, DESCRIPCION=? WHERE IDVIGILANCIA = ? and IDADMONLOCAL = ?";
    String CLEAN_REGISTRO   ="UPDATE sgtt_logaprobarvig SET FECHAREGISTRO=SYSDATE, DESCRIPCION=NULL WHERE IDVIGILANCIA = ? and IDADMONLOCAL = ?";
    String COUNT_NUM_ERR    ="SELECT count(*) REGISTROS FROM sgtt_logaprobarvig WHERE IDVIGILANCIA = ? central";
    
}
