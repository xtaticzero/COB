package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaEntidadFederativa;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionVigilanciaEnum;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;

import org.springframework.jdbc.core.RowMapper;

public class VigilanciaEntidadFederativaMapper implements RowMapper {
    
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        VigilanciaEntidadFederativa vef=new VigilanciaEntidadFederativa();
        
        vef.setNombreEntidadFederativa(resultSet.getString("nombreentidad"));
        vef.setIdVigilancia(resultSet.getLong("idvigilancia"));
        vef.setIdEstadoVigilancia(resultSet.getInt("idsituacionvigilancia"));
        vef.setDescripcionVigilancia(resultSet.getString("descripcion"));
        vef.setIdTipoDocumento(resultSet.getInt("idtipodocumento"));
        vef.setDescTipoDocumento(resultSet.getString("nombre"));
        vef.setFechaCorte(Utilerias.formatearFechaDDMMYYYY(resultSet.getDate("fechacorte")));
        vef.setDescFechaCargaArchivos(Utilerias.formatearFechaDDMMYYYY(resultSet.getDate("fechacargaarchivos")));
        vef.setIdSituacionArchivo(resultSet.getLong("idsituacionarchivo"));
        vef.setDescSituacionArchivo(resultSet.getString("situacion"));
        vef.setFechaDescarga(resultSet.getDate("fechadescarga")!=null?Utilerias.formatearFechaDDMMYYYY(resultSet.getDate("fechadescarga")):"");
        vef.setRutaArchivoOmisos(resultSet.getString("rutaarchivoomisos"));
        vef.setRutaArchivoFundamentos(resultSet.getString("rutaarchivofundamentos"));
        vef.setRutaArchivoFactura(resultSet.getString("rutaarchivofactura"));
        vef.setMostrarBtnDescarga(vef.getIdEstadoVigilancia()==SituacionVigilanciaEnum.ACEPTADA.getIdSituacion() || vef.getIdEstadoVigilancia()==SituacionVigilanciaEnum.PENDIENTE_PROCESAR.getIdSituacion());
        vef.setMostrarBtnRechazo(vef.getIdEstadoVigilancia()==SituacionVigilanciaEnum.RECHAZADA.getIdSituacion() || vef.getIdEstadoVigilancia()==SituacionVigilanciaEnum.PENDIENTE_PROCESAR.getIdSituacion());
        vef.setDescargarFundamento((vef.getIdEstadoVigilancia()==SituacionVigilanciaEnum.ACEPTADA.getIdSituacion()));
        vef.setDescargarFactura((vef.getIdEstadoVigilancia()==SituacionVigilanciaEnum.ACEPTADA.getIdSituacion()));
        vef.setIdEntidadFederativa(resultSet.getLong("IDENTIDADFEDERATIVA"));
        vef.setRfcUsuario(resultSet.getString("rfcusuario"));
        
        
        return vef;
    }
}
