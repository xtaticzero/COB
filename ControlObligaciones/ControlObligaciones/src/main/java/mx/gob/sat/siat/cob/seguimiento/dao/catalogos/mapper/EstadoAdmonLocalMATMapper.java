package mx.gob.sat.siat.cob.seguimiento.dao.catalogos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.catalogos.EstadoAdmonLocalMAT;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.springframework.jdbc.core.RowMapper;

public class EstadoAdmonLocalMATMapper implements RowMapper<EstadoAdmonLocalMAT> {

    public EstadoAdmonLocalMATMapper() {
        super();
    }

    /**
     *
     * @param rs
     * @param rowNum
     * @return
     * @throws SQLException
     */
    @Override
    public EstadoAdmonLocalMAT mapRow(ResultSet rs, int rowNum) throws SQLException {
        EstadoAdmonLocalMAT estadoAdmonLocalMAT = new EstadoAdmonLocalMAT();

        estadoAdmonLocalMAT.setIdAdmonLocal(rs.getString("IDADMONLOCAL"));
        estadoAdmonLocalMAT.setEsOperacionMAT(rs.getInt("ESOPERACIONMAT"));
        estadoAdmonLocalMAT.setEntidadDesc(rs.getString("NOMBRE"));
        if (estadoAdmonLocalMAT.getEsOperacionMAT().equals(ConstantsCatalogos.UNO)) {
            estadoAdmonLocalMAT.setEsOperacionMATDesc("Si");
        } else {
            estadoAdmonLocalMAT.setEsOperacionMATDesc("No");
        }
        return estadoAdmonLocalMAT;
    }
}
