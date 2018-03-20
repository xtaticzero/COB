/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.dao.management.sql;

/**
 *
 * @author root
 */
public interface ManagementMonitorSQL {

    String SERVER_INFO_QUERY = "SELECT SERVERID,\n"
            + "  commitedvirtualmemorysize,\n"
            + "  freephysicalmemoysize,\n"
            + "  freeswapspacesize,\n"
            + "  processcpuload,\n"
            + "  processcputime,\n"
            + "  systemcpuload,\n"
            + "  TIMESTAMP as tiempo,\n"
            + "  totalphysicalmemorysize,\n"
            + "  totalswapspacesize\n"
            + "FROM sgtt_serverinfo\n"
            + "where timestamp = (select max(serverinfo.timestamp) from sgtt_serverinfo serverinfo)";
    String PROCESS_INFO_QUERY = "SELECT processid     AS id,\n"
            + "  TIMESTAMP          AS tiempo,\n"
            + "  active             AS activo,\n"
            + "  counter            AS contador,\n"
            + "  MAX                AS MAX,\n"
            + "  maxactive          AS maxactivo,\n"
            + "  maxactivetimestamp AS tiempomaxactivo,\n"
            + "  mean               AS media,\n"
            + "  MIN                AS MIN,\n"
            + "  mintimestamp       AS mintiempo,\n"
            + "  standarddeviation  AS desviacion,\n"
            + "  total,\n"
            + "  variance  AS varianza,\n"
            + "  variancen AS varianzan\n"
            + "FROM sgtt_processinfo\n"
            + "WHERE TIMESTAMP IN\n"
            + "  (SELECT TIMESTAMP\n"
            + "  FROM\n"
            + "    (SELECT pi.processid,\n"
            + "      MAX(pi.timestamp) AS TIMESTAMP\n"
            + "    FROM sgtt_processinfo pi\n"
            + "    GROUP BY pi.processid\n"
            + "    )\n"
            + "  )\n"
            + "ORDER BY processid";
}
