/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.background.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.VigilanciaAdministracionLocal;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EsMultaEnum;

import org.apache.log4j.Logger;

import org.springframework.batch.item.ExecutionContext;

/**
 *
 * @author Juan
 */
public final class UtileriasBackground {

    private static Logger logger = Logger.getLogger(UtileriasBackground.class);

    /**
     * Contructor privado para clase de utileriasBackground , ya que no debe ser
     * instanciada
     */
    private UtileriasBackground() {
    }

    /**
     * @param hilos numero de hilos que se ejecutaran en paralelo, nunca debe
     * ser mayor al totalSize
     * @param totalSize variable del total de datos a procesar
     * @return
     */
    public static Map<String, ExecutionContext> ejecucionPartition(int hilos, int totalSize) {
        Map<String, ExecutionContext> result = new HashMap<String, ExecutionContext>();

        if (totalSize > hilos) {
            int pageSize = (totalSize + (totalSize % hilos)) / hilos;
            logger.debug("@@@@ PageSize:" + pageSize);
            pageSize = pageSize > 0 ? pageSize : 1;
            for (int i = 0; i < hilos; i++) {
                int from = (i * pageSize);
                int to = (from + pageSize) >= totalSize ? totalSize : (from + pageSize);
                int faltantes = totalSize - to;
                if (i == (hilos - 1) && faltantes > 0) {
                    //Si es el último thread y hay faltantes, los agregamos para su procesamiento
                    to += faltantes;
                }

                ExecutionContext thread = new ExecutionContext();
                thread.putInt("fromId", from);
                thread.putInt("toId", to - 1);
                thread.putString("name", "Partition " + i);
                logger.debug("@@@@ Thread:" + i + " From: " + from + " To:" + (to - 1));
                result.put("partition" + i, thread);
            }
        } else {
            for (int i = 0; i < totalSize; i++) {
                ExecutionContext thread = new ExecutionContext();
                thread.putInt("fromId", i);
                thread.putInt("toId", i);
                thread.putString("name", "Partition " + i);
                logger.debug("@@@@ Thread:" + i + " From: " + i + " To:" + (i));
                result.put("partition" + i, thread);
            }
        }

        return result;
    }

    /**
     *
     * @param indice valor que indica el indice del proceso
     * @param desde valor que indica el comienzo
     * @param tamLista valor del tamaño de la lista a evaluar
     * @return
     */
    public static boolean puedeRegresarValores(int indice, int desde, int tamLista) {
        if ((indice <= desde) && (indice < tamLista)) {
            return true;
        }
        return false;
    }

    public static String determinaNivelEmision(VigilanciaAdministracionLocal vigilanciaAdminLocal, EsMultaEnum esMulta) {
        String joinNivelEmison;
        joinNivelEmison = " INNER JOIN (\n"
                + "    select \n"
                + "        funcionario.nombreFuncionario, \n"
                + "        funcionario.descripcionCargo, \n"
                + "        nc.idCargoAdmtvo, \n"
                + "        nc.idNivelEmision, \n"
                + "        nc.idAdmonLocal \n"
                + "    from SGTA_AdmtvoNivlBK nc\n"
                + "        inner JOIN SGTP_FuncionarioBK funcionario \n"
                + "            on (nc.NumeroEmpleado = funcionario.numeroEmpleado \n"
                + "            and nc.idEventoCarga = 1 \n"
                + "            and nc.IdNivelemision = 1 \n"
                + "            and nc.FechaFin is null \n"
                + "            and funcionario.FechaFin is null)\n"
                + "    ) A \n"
                + "        on (vigilancia.idCargoAdmtvo = a.idCargoAdmtvo \n"
                + "        and vigilancia.idNivelEmision=a.idNivelEmision \n";
        if (esMulta == EsMultaEnum.ES_MULTA) {
            joinNivelEmison += "and multaDocto.idAdmonLocalOrigen = a.idAdmonLocal) ";
        } else {
            joinNivelEmison += "and docto.idAdmonLocal = a.idAdmonLocal) ";
        }
        return joinNivelEmison;
    }

    /**
     * Regresa el properties a utilizar.
     *
     * @param rutaArchivo
     * @return
     */
    public static Properties obtenProperties(String rutaArchivo) {
        Properties prop = null;
        InputStream fileInput = null;
        try {
            fileInput = new FileInputStream(rutaArchivo);
            prop = new Properties();
            prop.load(fileInput);
        } catch (FileNotFoundException ex) {
            logger.error("Fallo al abrir el archivo " + ex);
        } catch (IOException ex) {
            logger.error("Fallo al abrir el archivo " + ex);
        } finally {
            if (fileInput != null) {
                try {
                    fileInput.close();
                } catch (IOException ex) {
                    logger.error("Fallo al cerrar el archivo " + ex);
                    return prop;
                }
            }
        }
        return prop;
    }
}
