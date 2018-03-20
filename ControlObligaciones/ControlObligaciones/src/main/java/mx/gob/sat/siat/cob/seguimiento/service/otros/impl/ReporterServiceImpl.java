/*	****************************************************************
 * Nombre de archivo: ReporterServiceImpl.java
 * Autores: Emmanuel Estrada Gonzalez 		 	 		   
 * Bitácora de modificaciones:
 * CR/Defecto Fecha Autor Descripción del cambio
 * ---------------------------------------------------------------------------
 * N/A 10/02/2014 <Nombre del desarrollador que está modificando el archivo>
 * ---------------------------------------------------------------------------
 */
package mx.gob.sat.siat.cob.seguimiento.service.otros.impl;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.gob.sat.siat.cob.seguimiento.dto.report.BeanReportVigilancias;
import mx.gob.sat.siat.cob.seguimiento.dto.report.ReporteMultaAprobar;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobarGrupo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobarReporteDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ReporteJasperAfectacionDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.ReporteVigilancia;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.otros.ReporterService;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import mx.gob.sat.siat.cob.seguimiento.util.report.BeanMakerMultasAprobar;
import mx.gob.sat.siat.cob.seguimiento.util.report.DataBeanMakerVigilancias;
import mx.gob.sat.siat.cob.seguimiento.util.report.Reporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.nativejdbc.C3P0NativeJdbcExtractor;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service
public class ReporterServiceImpl implements ReporterService {

    private Reporter reporter;
    private final Logger log = Logger.getLogger(ReporterServiceImpl.class);
    private byte[] fileByteArray;
    private Connection connection;
    @Autowired
    @Qualifier(ConstantsCatalogos.DATASOURCE_COB_SEG)
    private JdbcTemplate template;
    @Autowired(required = false)
    private C3P0NativeJdbcExtractor cp30NativeExtractor;

    /**
     *
     */
    public ReporterServiceImpl() {
        super();
    }

    /**
     *
     * @param jasperReportAfectacion
     * @return
     * @throws SGTServiceException
     */
    @Override
    public byte[] makeReport(ReporteJasperAfectacionDTO jasperReportAfectacion) throws SGTServiceException {

        fileByteArray = null;
        try {
            reporter = new Reporter();
            fileByteArray = reporter.makeReport(jasperReportAfectacion);
        } catch (FileNotFoundException fileNotfound) {
            log.error(fileNotfound);
            throw new SGTServiceException(fileNotfound);
        } catch (JRException jre) {
            log.error(jre.fillInStackTrace());
            throw new SGTServiceException(jre);
        }
        return fileByteArray.clone();
    }

    /**
     *
     * @param pathToFile
     * @param parametersReport
     */
    @Override
    public void makeLocalReportJASPER(String pathToFile, Map parametersReport) {
        reporter = new Reporter();
        try {
            reporter.makeLocalReportJASPER(pathToFile, parametersReport,
                    getConnection());
        } catch (SQLException ex) {
            log.error(ex);
        } catch (FileNotFoundException ex) {
            log.error(ex);
        } catch (JRException ex) {
            log.error(ex);
        }
    }
    
    /**
     * 
     * @param pathToFile
     * @param parametersReport
     * @param lstBeanDataSource 
     */
    @Override
    public void makeReportFacturaEF(String pathToFile, Map<String, 
            Object> parametersReport,JRBeanCollectionDataSource lstBeanDataSource) {
        reporter = new Reporter();
        try {
            InputStream iSReport = this.getClass().getClassLoader().getResourceAsStream(("reports/FacturaEF.jasper"));
            reporter.makeReportJASPER(iSReport, parametersReport,
                    lstBeanDataSource,pathToFile);
        } catch (FileNotFoundException ex) {
            log.error(ex);
        } catch (JRException ex) {
            log.error(ex);
        }
    }

    private Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = cp30NativeExtractor.getNativeConnection(template.getDataSource().getConnection());
        }
        return connection;
    }

    /**
     *
     * @param reporteVigilancia
     * @return
     * @throws SGTServiceException
     */
    @Override
    public byte[] makeReport(ReporteVigilancia reporteVigilancia) throws SGTServiceException {
        fileByteArray = null;
        BeanReportVigilancias beanReportVigilancias;
        try {
            reporter = new Reporter();
            DataBeanMakerVigilancias dataBeanMaker = new DataBeanMakerVigilancias();
            Map parameters = dataBeanMaker.getParameters(reporteVigilancia);

            beanReportVigilancias = new BeanReportVigilancias(
                    (dataBeanMaker.getLstAlscFormat(reporteVigilancia.getLstALSC())),
                    (dataBeanMaker.getLstEfFormat(reporteVigilancia.getLstEF())));

            List<BeanReportVigilancias> lstBean;
            lstBean = new ArrayList<BeanReportVigilancias>();

            lstBean.add(beanReportVigilancias);

            JRBeanCollectionDataSource dsAlsc = new JRBeanCollectionDataSource(lstBean);
            fileByteArray = reporter.makeReportJASPER(dataBeanMaker.getRepotStream(), parameters, dsAlsc);
        } catch (FileNotFoundException fileNotfound) {
            log.error(fileNotfound);
            throw new SGTServiceException(fileNotfound);
        } catch (JRException jre) {
            log.error(jre.fillInStackTrace());
            throw new SGTServiceException(jre);
        }
        return fileByteArray.clone();

    }

    @Override
    public byte[] makeReport(MultaAprobarGrupo multaAprobarGrupo, List<MultaAprobarReporteDTO> multasFirmadas, List<MultaAprobarReporteDTO> multasNoGeneradas, List<MultaAprobarReporteDTO> multasTrasladadas) throws SGTServiceException {
        fileByteArray = null;
        try {
            ReporteMultaAprobar reporte = new ReporteMultaAprobar(multaAprobarGrupo, multasFirmadas, multasNoGeneradas, multasTrasladadas);
            reporter = new Reporter();
            BeanMakerMultasAprobar beanMaker = new BeanMakerMultasAprobar();
            fileByteArray = reporter.makeReportJASPER(beanMaker.getRepotStream(), beanMaker.getParameters(reporte), beanMaker.getDetalleReport(reporte));
        } catch (FileNotFoundException fileNotfound) {
            log.error(fileNotfound);
            throw new SGTServiceException(fileNotfound);
        } catch (JRException jre) {
            log.error(jre.fillInStackTrace());
            throw new SGTServiceException(jre);
        }
        return fileByteArray.clone();
    }
    
    @Override
    public void makeReport(MultaAprobarGrupo multaAprobarGrupo, List<MultaAprobarReporteDTO> multasFirmadas, List<MultaAprobarReporteDTO> multasNoGeneradas, List<MultaAprobarReporteDTO> multasTrasladadas,String fileFullName ) throws SGTServiceException {
        try {
            ReporteMultaAprobar reporte = new ReporteMultaAprobar(multaAprobarGrupo, multasFirmadas, multasNoGeneradas, multasTrasladadas);
            reporter = new Reporter();
            BeanMakerMultasAprobar beanMaker = new BeanMakerMultasAprobar();
            reporter.makeReportJASPER(beanMaker.getRepotStream(), beanMaker.getParameters(reporte), beanMaker.getDetalleReport(reporte),fileFullName);
        } catch (FileNotFoundException fileNotfound) {
            log.error(fileNotfound);
            throw new SGTServiceException(fileNotfound);
        } catch (JRException jre) {
            log.error(jre.fillInStackTrace());
            throw new SGTServiceException(jre);
        }
    }
}
