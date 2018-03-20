/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.NivelEmisionEnum;
import mx.gob.sat.siat.cob.seguimiento.util.Utilerias;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class AdministrativoNivelCargoMapper implements RowMapper<AdministrativoNivelCargo> {

    @Override
    public AdministrativoNivelCargo mapRow(ResultSet rs, int i) throws SQLException {
        AdministrativoNivelCargo administrativo = new AdministrativoNivelCargo();
        administrativo.getEventoCarga().setIdEventoCarga(rs.getLong("ideventocarga"));
        administrativo.setIdAdministacionLocal(rs.getString("idadmonlocal"));
        administrativo.setIdCargoAdministrativo(rs.getLong("idcargoadmtvo"));
        administrativo.setNivelEmision(convertirAEnum(rs.getLong("idnivelemision")));
        administrativo.setNumeroEmpleado(rs.getString("numeroempleado"));

        if (Utilerias.existeColumna(rs, "nombrefuncionario")) {
            administrativo.setNombreFuncionario(rs.getString("nombrefuncionario"));
        }
        if (Utilerias.existeColumna(rs, "correoelectronico")) {
            administrativo.setCorreoElectronico(rs.getString("correoelectronico"));
        }
        if (Utilerias.existeColumna(rs, "correoelectronicoalterno")) {
            administrativo.setCorreoElectronicoAlterno(rs.getString("correoelectronicoalterno"));
        }

        return administrativo;
    }

    private NivelEmisionEnum convertirAEnum(long nivel) {
        for (NivelEmisionEnum n : NivelEmisionEnum.values()) {
            if (n.getIdNivelEmision() == nivel) {
                return n;
            }
        }
        return null;
    }
}
