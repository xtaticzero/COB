package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.impl.mapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ConsultaVigilancia;
import org.springframework.jdbc.core.RowMapper;

    /**
 *
 * @author root
 */
public class VigilanciaMapper implements RowMapper {

        /**
     *
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
        public Object mapRow(ResultSet resultSet, int i) throws SQLException {
            ConsultaVigilancia vig = new ConsultaVigilancia();
            vig.setIdVigilancia(resultSet.getInt("ID"));
            vig.setIdTipoDocumento(resultSet.getInt("MODALIDAD_DOC"));
            vig.setNivelEmision(resultSet.getInt("IDNIVELEMISION"));
            vig.setIdTipoMedio(resultSet.getInt("IDTIPOMEDIO"));
            vig.setDescripcion(resultSet.getString("DESCRIPCION"));
            vig.setFechaCarga(resultSet.getString("FECHACARGAARCHIVOS"));
            ResultSetMetaData rsMetaData = resultSet.getMetaData();
            int numberOfColumns = rsMetaData.getColumnCount();
            for (int j = 1; j < numberOfColumns + 1; j++) {
                String columnName = rsMetaData.getColumnName(j);
                if ("NUMEMPLEADOUSUARIO".equals(columnName)) {
                    vig.setNumEmpleadoUsuario(resultSet.getString("NUMEMPLEADOUSUARIO"));
                }
            }
            return vig;
        }
    }