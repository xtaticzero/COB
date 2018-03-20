package mx.gob.sat.siat.cob.seguimiento.dao.rfcampliado.sql;

public interface RfccEntidadSQL {
    
    String LISTA_COMBO_ENTIDAD = " select numeroentidad as ID, descripcion as DESCRIPCION from rfcc_entidadfedera where numeroentidad is not null order by descripcion";
}
