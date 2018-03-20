package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

public interface AdministracionUsuariosSQL {
    
    String CONSULTA_EMPLEADOS="SELECT ad.*, ca.DESCRIPCION as cadesc, em.DESCRIPCION as emdesc , ev.DESCRIPCION as evdesc FROM sgta_admtvonivlcgo ad " + 
    " inner join sgtc_cargoadmtvo ca on ca.idcargoadmtvo=ad.idcargoadmtvo" + 
    " inner join sgtc_nivelemision em on em.idnivelemision=ad.idnivelemision" + 
    " inner join sgtc_eventocarga ev on ev.ideventocarga=ad.ideventocarga order by ad.numeroempleado";
    
    String ACTUALIZAR_FUNCIONARIO="update sgta_admtvonivlcgo set IDCARGOADMTVO=?, IDNIVELEMISION=?, IDEVENTOCARGA=?, ESCC=?, IDADMONLOCAL=? where numeroempleado=?";
    
    String HABILITAR_FUNCIONARIO="update sgta_admtvonivlcgo set fechafin=null where numeroempleado='{0}'";
    
    String ELIMINAR_FUNCIONARIO="update sgta_admtvonivlcgo set fechafin=sysdate where numeroempleado='{0}'";
    
    String INSERTAR_FUNCIONARIO="INSERT INTO sgta_admtvonivlcgo values ({0},'{1}','{2}',{3},{4},{5},{6},{7})";
    
    String BUSCAR_CARGOS_ADMINISTRATIVOS="select idcargoadmtvo as ID,descripcion as DESCRIPCION from sgtc_cargoadmtvo";
    
    String BUSCAR_NIVELES_EMISION="select idnivelemision as ID,descripcion as DESCRIPCION from sgtc_nivelemision";
    
    String BUSCAR_EVENTO_CARGA="select ideventocarga as ID, descripcion as DESCRIPCION from sgtc_eventocarga";
    
    
    
    String BUSCAR_REGION_ALR="SELECT a.CLAVEREGION,a.CODIGOREGION,a.DESCRIPCION FROM RFCC_REGION a , RFCC_CATALOGO  b\n" + 
    "WHERE  a.IDTIPOREGION = b.IDCATALOGO \n" + 
    "AND b.CLAVEELEMENTO = 'ALR'    AND  b.TIPO='TIPOREGION'\n" + 
    "AND   ( SYSDATE BETWEEN  a.FECHAINICIOVIGENCIA AND a.FECHAFINVIGENCIA  \n" + 
    "            OR  ( SYSDATE >= a.FECHAINICIOVIGENCIA AND a.FECHAFINVIGENCIA IS NULL) ) \n" + 
    "AND a.descripcion    like 'ALR%'   \n" + 
    "order by a.codigoRegion\n";
    
    String BUSCAR_CARGO_POR_NIVEL_EMISION="select cargo.idcargoadmtvo as ID,cargo.descripcion as DESCRIPCION from sgtc_cargoadmtvo cargo inner join sgtc_nivelemision nivel on cargo.idnivelemision=nivel.idnivelemision where nivel.idnivelemision=?";
}
