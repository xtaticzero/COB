package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;

import java.sql.SQLException;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaPendienteDTO;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoDocumentoEnum;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.SituacionIcepEnum;

import org.springframework.jdbc.core.RowMapper;

public class MultaPendienteMapper implements RowMapper<MultaPendienteDTO>{
    public MultaPendienteMapper() {
        super();
    }

    @Override
    public MultaPendienteDTO mapRow(ResultSet rs, int i) throws SQLException {
        MultaPendienteDTO multa=new MultaPendienteDTO();
        multa.setRfc(rs.getString("rfc"));
        multa.setNumeroControl(rs.getString("numerocontrol"));
        multa.setObligacionRequerida(rs.getString("descobligacion"));
        multa.setPeriodoVigilado(rs.getString("descperiodo"));
        multa.setDescripcionVigilancia(rs.getString("descvig"));
        multa.setNotificacionReq(rs.getDate("FECHANOTIFICACION"));
        multa.setVencimientoReq(rs.getDate("FECHAVENCIMIENTODOCTO"));
        multa.setPresentacionObligacion(rs.getDate("fechapresentacionobligacion"));
       
        
        StringBuilder periodoDeclarado=new StringBuilder();
        periodoDeclarado.append(rs.getString("mesinicio"))
            .append("/")
            .append(rs.getString("ejercicio"))
            .append("-")
            .append(rs.getString("mesfin"))
            .append("/")
            .append(rs.getString("ejercicio"));
        multa.setPeriodoDeclarado(periodoDeclarado.toString());
        
        int estadoDocumento=rs.getInt("estadodocumento");
        int estadoICEP=rs.getInt("estadoicep");
        
        boolean icepOmiso=estadoICEP==SituacionIcepEnum.INCUMPLIDO.getValor();
        boolean regCumplimientoAntesFechaNotif=false;
        boolean regCumplimientoAntesFechaVenc=false;
        
        if( multa.getPresentacionObligacion()!=null && multa.getNotificacionReq()!=null){
           regCumplimientoAntesFechaNotif=multa.getPresentacionObligacion().before(multa.getNotificacionReq());
        }
        if(multa.getVencimientoReq()!=null && multa.getPresentacionObligacion()!=null){
           regCumplimientoAntesFechaVenc=multa.getPresentacionObligacion().before(multa.getVencimientoReq());
        }
        
        if(estadoDocumento==EstadoDocumentoEnum.PENDIENTE_DE_NOTIFICAR.getValor()|| 
                (estadoDocumento==EstadoDocumentoEnum.SOLVENTADO_PARCIAL.getValor()&& multa.getNotificacionReq()==null && icepOmiso)){
            multa.setPendienteNotificar(true);   
        }
        if(estadoDocumento==EstadoDocumentoEnum.NOTIFICADO.getValor()|| 
            (estadoDocumento==EstadoDocumentoEnum.SOLVENTADO_PARCIAL.getValor()&& multa.getNotificacionReq()!=null && icepOmiso)){
            multa.setReqProximoVencer(true);    
        }
        if((estadoICEP==SituacionIcepEnum.CUMPLIDO.getValor() && regCumplimientoAntesFechaNotif)|| 
            (estadoICEP==SituacionIcepEnum.CUMPLIDO.getValor() && multa.getNotificacionReq()==null)){
            multa.setCumplioAntesEfectosNotificacion(true);            
        }
        if(estadoICEP==SituacionIcepEnum.CUMPLIDO.getValor() && !regCumplimientoAntesFechaNotif && regCumplimientoAntesFechaVenc){
            multa.setCumplioDentroPlazo15Dias(true);
        }
        if(estadoICEP==SituacionIcepEnum.CUMPLIDO.getValor() &&  !regCumplimientoAntesFechaVenc){
            multa.setCumplioFueraPlazo15Dias(true);    
        }
        if((estadoDocumento==EstadoDocumentoEnum.VENCIDO.getValor() || estadoDocumento==EstadoDocumentoEnum.VENCIDO_PARCIAL.getValor()) && icepOmiso){
            multa.setRequerimientoVencido(true);
        }
            
        
        return multa;
    }
}
