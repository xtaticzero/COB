package mx.gob.sat.siat.cob.seguimiento.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.CatalogoEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.UnidadTiempo;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.mozilla.universalchardet.UniversalDetector;

import org.quartz.CronExpression;

/**
 *
 * @author Rodrigo
 */
public final class Utilerias {

    private final static int VACIO = 0;
    private static final String MAIL_PATH = "mail/";
    private static final String MAIL_EXT = ".mail";
    private static final String FOOTER = "footer";
    private static final Logger LOG = Logger.getLogger(Utilerias.class);
    private static final String UTF8_BOM = "\uFEFF";
    private static final long SECOND_MILIS = 1000;
    private static final long MINUTE_MILIS = SECOND_MILIS * 60;
    private static final long HOUR_MILIS = MINUTE_MILIS * 60;
    private static final long DAY_MILIS = HOUR_MILIS * 24;

    /**
     * Contructor privado para clase de utilerias , ya que no debe ser
     * instanciada
     */
    private Utilerias() {
    }

    /**
     *
     * @param date objeto fecha que se desea formatear
     * @return cadena con el formato de fecha yyyy-mm-dd
     */
    public static String formatearFechaAAAAMMDD(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    /**
     *
     * @param date objeto fecha que se desea formatear
     * @return cadena con el formato de fecha yyyy-mm-dd
     */
    public static String formatearFechaDDMMYYYY(Date date) {
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    /**
     *
     * @param date objeto fecha que se desea formatear
     * @return cadena con el formato de fecha dd-mm-yyyy
     */
    public static String formatearFechaDDMMAAAA(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    /**
     *
     * @param date objeto fecha que se desea formatear
     * @return cadena con el formato de fecha yyyy-mm-dd
     * @throws java.text.ParseException
     */
    public static Date formatearFechaAAAAMMDDHHMM(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
    }

    public static Date formatearFechaDDMMAAAAHHMM(String date) throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy").parse(date);
    }

    public static String formatearFechaAAAAMMDDHHMMSS(Date date) {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
    }

    public static String formatearFechaDDMMAAAAHHMMSS(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(date);
    }

    public static String formatearFechaYYYYMMDDHHMMSS(Date date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static Date formatearFechaYYYYMMDDHHMMSS(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
    }

    /**
     *
     * @return Regresa la fecha de ayer con respecto al sistema
     */
    public static Date getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }

    public static Date getDosMesesDesdeHoy(Date fechaInicio) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaInicio);
        calendar.add(Calendar.MONTH, 2);
        return calendar.getTime();
    }

    /**
     *
     * @param fecha1
     * @param fecha2
     * @return fecha que resulto mayor
     */
    public static Date obtenerFechaMayor(Date fecha1, Date fecha2) {
        return fecha1.compareTo(fecha2) < 0 ? fecha2 : fecha1;
    }

    public static CatalogoEnum obtenerPorAlias(String alias) {
        for (CatalogoEnum c : CatalogoEnum.values()) {
            if (c.getAlias().equals(alias)) {
                return c;
            }
        }
        return null;
    }

    public static String obtenerDiferenciaFechas(Date a, Date b) {
        StringBuilder diferencia = new StringBuilder();
        long diff = Math.abs(b.getTime() - a.getTime());
        long milis, secs, mins, hours, days;

        days = diff / UnidadTiempo.DIA.getMilis();
        diff = diff % UnidadTiempo.DIA.getMilis();

        hours = diff / UnidadTiempo.HORA.getMilis();
        diff = diff % UnidadTiempo.HORA.getMilis();

        mins = diff / UnidadTiempo.MINUTO.getMilis();
        diff = diff % UnidadTiempo.MINUTO.getMilis();

        secs = diff / UnidadTiempo.SEGUNDO.getMilis();
        milis = diff % UnidadTiempo.SEGUNDO.getMilis();

        if (days > 0) {
            diferencia.append(days).append(" D ");
        }

        diferencia.append(StringUtils.leftPad("" + hours, 2, "0"));
        diferencia.append(":");
        diferencia.append(StringUtils.leftPad("" + mins, 2, "0"));
        diferencia.append(":");
        diferencia.append(StringUtils.leftPad("" + secs, 2, "0"));
        diferencia.append("::");
        diferencia.append(StringUtils.leftPad("" + milis, 3, "0"));
        return diferencia.toString();
    }

    public static Integer obtenerDiferenciaMeses(Date fechaInicio, Date fechaFin) {
        Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(fechaInicio);
        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(fechaFin);

        int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        return diffYear * ConstantsCatalogos.DOCE + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

    }

    /**
     *
     * @param s Cadena con un listado entre corchetes Ejemplo: [1,2,3,4]
     * @return Cadena con un listado entre parentesis Ejemplo: (1,2,3,4)
     */
    public static String reeamplazarCorchetesPorParentesis(String s) {
        return s.replace('[', '(').replace(']', ')');
    }

    /**
     *
     * @param s Cadena con un listado entre corchetes Ejemplo: [1,2,3,4]
     * @return Cadena con un listado entre parentesis Ejemplo: 1,2,3,4
     */
    public static String separadoPorComas(String s) {
        return s.trim().replace("[", "").
                replace("]", "").
                replace(", ", ",");
    }

    /**
     *
     * @param s Cadena con un listado entre corchetes Ejemplo: [1,2,3,4]
     * @return Cadena con un listado entre parentesis Ejemplo: (1,2,3,4)
     */
    public static String formatearParaSQLIn(String s) {
        return s.replace("[", "('").
                replace("]", "')").
                replace(",", "','");

    }

    public static String formatearParaSQLIn(Set<String> items, String campo) {
        return formatearIn(items, campo, false);
    }

    public static String formatearParaSQLInNumeric(Set<String> items, String campo) {
        return formatearIn(items, campo, true);
    }

    private static String formatearIn(Set<String> items, String campo, boolean numerico) {
        String apostrofe = "'";
        if (numerico) {
            apostrofe = "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(" ").append(campo).append(" in(").append(apostrofe);
        for (String string : items) {
            builder.append(string).append(apostrofe).append(",").append(apostrofe);
        }
        int residuo = builder.length() - 3;
        if (numerico) {
            residuo = builder.length() - 1;
        }
        builder.delete(residuo, builder.length());
        builder.append(apostrofe).append(")\n");
        return builder.toString();
    }

    public static String inSQLMasDeMil(Set<String> items, String campo) {
        Set<String> mil = new HashSet<String>();
        StringBuilder builder = new StringBuilder();
        int contador = 0;
        for (String string : items) {
            mil.add(string);
            contador++;
            if (contador == ConstantsCatalogos.MIL) {
                builder.append(formatearParaSQLIn(mil, campo));
                builder.append(" OR ");
                mil.clear();
                contador = 0;
            }
        }
        if (!mil.isEmpty()) {
            builder.append(formatearParaSQLIn(mil, campo));
        } else {
            builder.delete(builder.length() - 4,
                    builder.length());
        }
        return builder.toString();
    }

    public static AccesoUsr obtenerAccesoUsrEmpleados() {
        AccesoUsr usuario = new AccesoUsr();

        usuario.setNombreCompleto("");
        usuario.setUsuario("FAVV8709154Z9");
        usuario.setMenu("");
        usuario.setTipoAuth("");
        usuario.setNumeroEmp("00000143674");
        usuario.setSessionID("");
        usuario.setSessionIDNovell("");
        usuario.setLocalidad("");
        usuario.setLocalidadOp("");
        usuario.setUsuarioOficina("");
        usuario.setLocalidadCRM("");
        usuario.setRoles("");
        usuario.setIdTipoPersona("");
        usuario.setRfcCorto("FAVV8709");

        return usuario;
    }

    /**
     *
     * @param campo valor a evaluar
     * @return boolean true, si el campo esta vacio
     */
    public static boolean isVacio(String campo) {
        if (campo != null) {
            if (!campo.isEmpty()) {
                if (campo.trim().length() > VACIO) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isExpresionCronValida(String expresionCron) {
        try {
            new CronExpression(expresionCron);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static List<Integer> charSeparatedToIntegerList(String charSeparated, String pattern, Class type) {
        List<Integer> resultado = new ArrayList<Integer>();
        if (charSeparated != null) {
            String[] elementos = charSeparated.split(pattern);
            for (String elemento : elementos) {
                resultado.add(Integer.valueOf(elemento));
            }
        }
        return resultado;
    }

    public static List<String> cadenaToList(String cadena) {
        return Arrays.asList(cadena.split("\\s*,\\s*"));
    }

    public static Date setFechaTrunk(Date fechapresicion) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechapresicion);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     *
     * @param nombreMail Nombre de la plantilla que se encuentra en el paquete
     * mail sin extension.
     * @param map Elmentos que se desean reemplazar en el correo la llave es el
     * elemento que se encuentra en la plantilla y el valor es la cadena que se
     * desea lleve el correo.
     * @return Correo con valores deseados
     */
    public static String obtenerCorreo(String nombreMail, Map<String, String> map) {
        String buffer = leerMail(MAIL_PATH + nombreMail + MAIL_EXT);
        for (Map.Entry<String, String> e : map.entrySet()) {
            buffer = buffer.replace(e.getKey(), e.getValue());
        }
        String footer = leerMail(MAIL_PATH + FOOTER + MAIL_EXT);
        return buffer + footer;
    }

    private static String leerMail(String nombreMail) {
        StringBuilder cuerpoMail = new StringBuilder();
        InputStreamReader isr = null;
        BufferedReader br = null;

        String buffer;
        try {
            isr = new InputStreamReader(
                    Utilerias.class.getClassLoader().
                    getResourceAsStream(nombreMail), "UTF-8");
            br = new BufferedReader(isr);
            while ((buffer = br.readLine()) != null) {
                cuerpoMail.append(buffer);
            }
        } catch (IOException e) {
            LOG.error("No se pudo obtener el cuerpo del correo", e);
        } finally {
            try {
                if (isr != null) {
                    isr.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                LOG.error(e);
            }
        }
        return cuerpoMail.toString();
    }

    public static String eliminarRepetidosString(String cadena) {
        return new LinkedHashSet<String>(Arrays.asList(cadena.split(","))).
                toString().replaceAll("(^\\[|\\]$)", "");
    }

    /**
     * Get a diff between two dates
     *
     * @param date1 the oldest date
     * @param date2 the newest date
     * @param timeUnit the unit in which you want the diff
     * @return the diff value, in the provided unit
     */
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    /**
     * Obtener representacion texto de duracion de un intervalo entre dos fechas
     *
     * @param date1 the oldest date
     * @param date2 the newest date
     * @return Diferencia con el formato DD hh:mm:ss
     */
    public static String obtenerDiferenciaFechasTexto(Date a, Date b) {
        long duracion = b.getTime() - a.getTime();
        StringBuilder sb = new StringBuilder("");
        long dias = duracion / DAY_MILIS;
        if (dias > 0) {
            duracion -= dias * DAY_MILIS;
            sb.append(dias).append("D ");
        }
        long horas = duracion / HOUR_MILIS;
        if (horas > 0) {
            duracion -= horas * HOUR_MILIS;
            sb.append(horas).append("H ");
        }
        long minutos = duracion / MINUTE_MILIS;
        if (minutos > 0) {
            duracion -= minutos * MINUTE_MILIS;
            sb.append(minutos).append("M ");
        }
        long segundos = duracion / SECOND_MILIS;
        if (segundos > 0) {
            duracion -= segundos * SECOND_MILIS;
            sb.append(segundos).append("S ");;
        }

        return sb.toString();
    }

    /**
     * Retorna true, si es que existe la columna
     *
     * @param rs resultSet
     * @param columnName nombre de la columna
     * @return si existe la columna
     */
    public static boolean existeColumna(ResultSet rs, String columnName) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        for (int columna = 1; columna <= metaData.getColumnCount(); columna++) {
            if (columnName.toUpperCase().equals(metaData.getColumnName(columna))) {
                return true;
            }
        }
        return false;
    }

    /**
     * christian: metodo para detectar la codificacion con la que viene un
     * archivo que se le pasa como parametro
     *
     * @param ligaRecurso
     * @return
     * @throws IOException
     */
    public static String detectaCodificacion(String ligaRecurso) throws IOException {
        byte[] buf = new byte[10240];
        java.io.FileInputStream fis = null;
        UniversalDetector detector = new UniversalDetector(null);
        int nread;
        String encoding;
        try {
            fis = new java.io.FileInputStream(ligaRecurso);
            nread = fis.read(buf);
            while (nread > 0 && !detector.isDone()) {
                detector.handleData(buf, 0, nread);
                nread = fis.read(buf);
            }
            detector.dataEnd();
            encoding = detector.getDetectedCharset();
            //Detected encoding : encoding
            if (encoding == null) {
                encoding = "UTF-8";
            } else if (encoding.equals("GB18030")) {
                encoding = "WINDOWS-1252";
            }
            detector.reset();
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        LOG.debug(encoding);
        return encoding;
    }

    /**
     * christian: metodo para detectar la codificacion con la que viene un
     * archivo que se le pasa como parametro
     *
     * @param ligaRecurso
     * @return
     * @throws IOException
     */
    public static String detectaCodificacionStream(InputStream inputStream) throws IOException {
        byte[] buf = new byte[10240];
        InputStream bis = new BufferedInputStream(inputStream);
        UniversalDetector detector = new UniversalDetector(null);
        int nread;
        String encoding;
        nread = bis.read(buf);
        while (nread > 0 && !detector.isDone()) {
            detector.handleData(buf, 0, nread);
            nread = bis.read(buf);
        }
        detector.dataEnd();
        encoding = detector.getDetectedCharset();
        //Detected encoding : encoding
        if (encoding == null) {
            encoding = "UTF-8";
        } else if (encoding.equals("GB18030")) {
            encoding = "WINDOWS-1252";
        }
        detector.reset();
        LOG.debug(encoding);
        return encoding;
    }

    /**
     * para UTF-8 con BOM, quitar el identificador
     *
     * @param s
     * @return
     */
    public static String removeUTF8BOM(String s) {
        if (s.startsWith(UTF8_BOM)) {
            return s.substring(1);
        } else {
            return s;
        }
    }

    /**
     * regresa la referecia a partir de la calle1 calle2 y referencias
     * adicionales en el formato: Entre calle1 y calle2, adicionales o
     * combinacion entre estas, como: calle1, adicionales
     *
     * @param entreCalle1
     * @param entreCalle2
     * @param adicionales
     * @return
     */
    public static String obtenerReferenciaUbicacion(String entreCalle1, String entreCalle2, String adicionales) {
        boolean calle1Empty = entreCalle1 == null || entreCalle1.trim().isEmpty();
        boolean calle2Empty = entreCalle2 == null || entreCalle2.trim().isEmpty();
        boolean adicionalesEmpty = adicionales == null || adicionales.trim().isEmpty();
        StringBuilder cadena = new StringBuilder();
        cadena.append(((!calle1Empty && !calle2Empty) ? "ENTRE " : ""));
        cadena.append((calle1Empty ? "" : entreCalle1));
        cadena.append(((!calle1Empty && !calle2Empty) ? " Y " : ""));
        cadena.append((calle2Empty ? "" : entreCalle2));
        cadena.append(((!calle1Empty || !calle2Empty) && !adicionalesEmpty ? ", " : ""));
        cadena.append((adicionalesEmpty ? "" : adicionales));
        return cadena.toString();
    }

    /**
     *
     * @param filePath
     * @param zipFile
     */
    public static void convertirArchivoAZip(String filePath, String zipFile) {
        byte[] buffer = new byte[2048];
        FileOutputStream fos;
        ZipOutputStream zos = null;
        FileInputStream in = null;
        try {
            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(fos);
            zos.setLevel(Deflater.DEFAULT_COMPRESSION);
            String file = filePath.substring(filePath.lastIndexOf('/') + 1);
            ZipEntry ze = new ZipEntry(file);
            zos.putNextEntry(ze);
            in = new FileInputStream(filePath);
            int len;
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
            zos.closeEntry();
            zos.close();
        } catch (IOException ex) {
            LOG.error(ex.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                LOG.error(ex);
            }
        }
    }

    /**
     * para remover de una cadena que tenga dentro saltos de linea
     *
     * @param pOrigen
     * @return
     */
    public static String quitarSaltosDeLinea(String pOrigen) {
        Pattern p = Pattern.compile("\n");
        Matcher m = p.matcher(pOrigen);
        if (m.find()) {
            return m.replaceAll(" ");
        } else {
            return pOrigen;
        }
    }

    /**
     * para regresar cadena codificada en UTF-8
     *
     * @param cadena
     * @return
     */
    public static String reemplazaCodificacion(String cadena) {
        try {
            return URLEncoder.encode(cadena, "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            LOG.error(e);
        }
        return null;
    }

    /**
     * Espera mientras el proceso este en ejecucion
     *
     * @param executor
     * @param threads
     *
     */
    public static void terminaProceso(ExecutorService executor, List<PoolThread> threads) throws InterruptedException {
        executor.shutdown();
        while (!executor.isTerminated() && !isProcessComplete(threads)) {
            Thread.sleep(10);
        }
    }

    /**
     * Da por terminado el proceso, en este punto ya se termino de la ejecucion
     *
     * @param executor
     *
     */
    public static void interrumpirProceso(ExecutorService executor) throws InterruptedException {
        if (!executor.isTerminated()) {
            executor.awaitTermination(0L, TimeUnit.MINUTES);
        }
    }

    /**
     * Verificamos el estado del hilo, para poder cambiar su estado a finalizado
     *
     * @param threads
     *
     */
    public static boolean isProcessComplete(List<PoolThread> threads) {
        boolean complete = true;
        for (PoolThread thread : threads) {
            if (thread.getEstado() == PoolThread.RUNNING) {
                complete = false;
            }
        }
        return complete;
    }

    /**
     * Se obtiene el detalle de la excepcion, omitiendo toda la traza del
     * mensaje
     *
     * @param ex
     *
     */
    public static void getDetalleExcepcion(Exception ex) throws SGTServiceException {
        Throwable th = ex.getCause().getCause() == null ? (ex.getCause() == null ? ex : ex.getCause()) : ex.getCause().getCause();
        throw new SGTServiceException(th.getMessage(), th);
    }

    /**
     *
     * @param <T>
     * @param origen
     * @return
     */
    public static <T> List<T> copyList(List<T> origen) {
        List<T> destino = new ArrayList<T>();
        for (T item : origen) {
            destino.add(item);
        }
        return destino;
    }
}
