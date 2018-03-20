package mx.gob.sat.siat.cob.seguimiento.dao.rfcampliado.sql;

public interface RfccCatalogoSQL {
    
    String BUSCAR_REGION_ALR="SELECT a.CLAVEREGION,a.CODIGOREGION,a.DESCRIPCION FROM RFCC_REGION a , RFCC_CATALOGO  b\n" + 
    "WHERE  a.IDTIPOREGION = b.IDCATALOGO \n" + 
    "AND b.CLAVEELEMENTO = 'ALR'    AND  b.TIPO='TIPOREGION'\n" + 
    "AND   ( SYSDATE BETWEEN  a.FECHAINICIOVIGENCIA AND a.FECHAFINVIGENCIA  \n" + 
    "            OR  ( SYSDATE >= a.FECHAINICIOVIGENCIA AND a.FECHAFINVIGENCIA IS NULL) ) \n" + 
    "AND a.descripcion    like 'ALR%'   \n" + 
    "order by a.codigoRegion\n";

}
