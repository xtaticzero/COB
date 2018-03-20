/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.arca.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.Direccion;
import mx.gob.sat.siat.cob.seguimiento.dto.arca.Contribuyente;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Juan
 */
public class ContribuyenteMapper implements RowMapper<Contribuyente> {

    /**
     *
     */
    public ContribuyenteMapper() {
        super();
    }

    /**
     *
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public Contribuyente mapRow(ResultSet resultSet, int i) throws SQLException {
        Contribuyente contribuyente = new Contribuyente();

        contribuyente.setBoId(resultSet.getString("BoId"));
        contribuyente.setNombre(resultSet.getString("nombre"));
        contribuyente.setApPaterno(resultSet.getString("apPaterno"));
        contribuyente.setApMaterno(resultSet.getString("apMaterno"));
        contribuyente.setCurp(resultSet.getString("CURP"));
        contribuyente.setCompania(resultSet.getString("compania"));
        contribuyente.setRfc(resultSet.getString("RFC_Vigente"));

        Direccion direccion = new Direccion();

        direccion.setCalle(resultSet.getString("Calle"));
        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
        direccion.setDescripcionColonia(resultSet.getString("DescripcionColonia"));
        direccion.setDescripcionLocalidad(resultSet.getString("DescripconLocalidad"));
        direccion.setEntreCalle1(resultSet.getString("EntreCalle1"));
        direccion.setEntreCalle2(resultSet.getString("EntreCalle2"));
        direccion.setDescripcionMunicipio(resultSet.getString("DescripcionMunicipio"));
        direccion.setDescripcionEntidadFederativa(resultSet.getString("DescripcionEntidadFederativa"));

        contribuyente.setDireccion(direccion);

        return contribuyente;
    }
}
