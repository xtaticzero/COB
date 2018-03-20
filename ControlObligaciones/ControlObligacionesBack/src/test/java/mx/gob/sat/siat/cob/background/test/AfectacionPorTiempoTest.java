package mx.gob.sat.siat.cob.background.test;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.cob.background.service.afectaciones.AfectacionTiempoService;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.DetalleDocumento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EtapaVigilanciaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.TipoDocumentoEnum;
import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.afectaciones.ProcesoAfectacionTiempoService;
import mx.gob.sat.siat.cobranza.domain.ICEP;
import mx.gob.sat.siat.cobranza.domain.Persona;
import mx.gob.sat.siat.cobranza.domain.Resolucion;
import mx.gob.sat.siat.cobranza.negocio.registromasivo.api.RegistroMasivoFacade;
import mx.gob.sat.siat.cobranza.negocio.util.constante.NumerosConstante;
import mx.gob.sat.siat.cobranza.negocio.util.excepcion.FacadeNegocioException;

import org.junit.Ignore;
import org.junit.Test;

public class AfectacionPorTiempoTest extends ContextLoaderTest {

    private static int N_22 = 54;

    @Test
    @Ignore
    public void pruebaAfectacionTiempo() {
        for (String s : getContext().getBeanDefinitionNames()) {
            log.error(s);
        }
        AfectacionTiempoService afectacionTiempoService =
                (AfectacionTiempoService) getContext().getBean("afectacionTiempoService");
        try {
            afectacionTiempoService.afectacionPorTiempo();
        } catch (SGTServiceException e) {
            log.error("error " + e);
        }
    }

    @Test
    @Ignore
    public void testStoreProcedure() throws FacadeNegocioException {

        RegistroMasivoFacade registroMasivoFacade = (RegistroMasivoFacade) getContext().getBean("registroMasivoFacade");
        List<Resolucion> resoluciones = new ArrayList<Resolucion>();
        resoluciones.add(resolucion1());
        try {
            Map resols = registroMasivoFacade.insertarResolucionSP(resoluciones);
            if (resols != null) {
                Long lote = (Long) resols.get("ID_LOTE");
                log.info("### IdLote: " + lote);
                List<String> fallados = (List<String>) resols.get("RESOLUCIONES");
                log.info("### Numero de resoluciones falladas: " + fallados.size());
            } else {
                log.info("### no se guardo nada");
            }
        } catch (FacadeNegocioException e) {
            log.error(e.getMessage());
            throw new FacadeNegocioException();
        }
    }

    private Resolucion resolucion1() {
        Resolucion resolucion = new Resolucion();
        resolucion.setNumResolucionDet("resolucion10008mil");
        resolucion.setFecResolucionDet(new Date());
        resolucion.setIdClaveSir(Long.valueOf(N_22));
        resolucion.setIdResolMotivoConstante("RESOLMOTIVO_INCUMPLIMIENTO");
        resolucion.setImporteDet(new BigDecimal(640));
        resolucion.setIdFirmaTipo(Long.valueOf(NumerosConstante.N_1));
        resolucion.setFolio(new BigDecimal("122"));
        resolucion.setFecReqCob(new Date());

        ICEP icep = new ICEP();
        icep.setIdMultaDesc(1L);

        List<ICEP> iceps = new ArrayList<ICEP>();
        iceps.add(icep);
        Persona per = new Persona();
        per.setBoId("1018716446448457062980458107561");
        per.setRfc("AARE8011132M8");
        resolucion.setPersona(per);
        resolucion.setListaICEPs(iceps);
        return resolucion;
    }

    @Test
    @Ignore
    public void pruebaLiquidacion() throws SGTServiceException {
        ProcesoAfectacionTiempoService procesoAfectacion =
                (ProcesoAfectacionTiempoService) getContext().getBean("procesoAfectacionTiempoServiceImpl");

        List<Documento> documentosVencidos = new ArrayList<Documento>();
        Documento doc = new Documento();

        List<DetalleDocumento> icepsOmisosDocumento = new ArrayList<DetalleDocumento>();

        DetalleDocumento icep1 = new DetalleDocumento();
        DetalleDocumento icep2 = new DetalleDocumento();
        DetalleDocumento icep3 = new DetalleDocumento();
        DetalleDocumento icep4 = new DetalleDocumento();

        icep1.setClaveIcep(832L);
        icep1.setNumeroControl("22129");
        icep1.setIdObligacion(7);
        icep1.setIdEjercicio("2013");
        icep1.setIdPeriodo("1");
        icep1.setFechaVencimiento("19/03/2014");
        icep1.setIdPeriodicidad("M");
        icep1.setIdSituacionIcep(0);
        icep1.setTieneMultaComplementaria(0);
        icep1.setTieneMultaExtemporaneidad(0);


        icep2.setClaveIcep(833L);
        icep2.setNumeroControl("22129");
        icep2.setIdObligacion(7);
        icep2.setIdEjercicio("2013");
        icep2.setIdPeriodo("1");
        icep2.setFechaVencimiento("19/03/2014");
        icep2.setIdPeriodicidad("M");
        icep2.setIdSituacionIcep(0);
        icep2.setTieneMultaComplementaria(0);
        icep2.setTieneMultaExtemporaneidad(0);

        icep3.setClaveIcep(834L);
        icep3.setNumeroControl("22129");
        icep3.setIdObligacion(7);
        icep3.setIdEjercicio("2013");
        icep3.setIdPeriodo("1");
        icep3.setFechaVencimiento("19/03/2014");
        icep3.setIdPeriodicidad("M");
        icep3.setIdSituacionIcep(0);
        icep3.setTieneMultaComplementaria(0);
        icep3.setTieneMultaExtemporaneidad(0);

        icep4.setClaveIcep(835L);
        icep4.setNumeroControl("22129");
        icep4.setIdObligacion(7);
        icep4.setIdEjercicio("2013");
        icep4.setIdPeriodo("1");
        icep4.setFechaVencimiento("19/03/2014");
        icep4.setIdPeriodicidad("M");
        icep4.setIdSituacionIcep(0);
        icep4.setTieneMultaComplementaria(0);
        icep4.setTieneMultaExtemporaneidad(0);

        icepsOmisosDocumento.add(icep1);
        icepsOmisosDocumento.add(icep2);
        icepsOmisosDocumento.add(icep3);
        icepsOmisosDocumento.add(icep4);
        doc.setDetalles(icepsOmisosDocumento);

        doc.setBoid("854027867417392778189516");
        doc.setNumeroControl("22129");
        doc.getVigilancia().setIdTipoDocumento(4);
        doc.setIdEtapaVigilancia(3);
        doc.getVigilancia().setIdVigilancia(141L);
        doc.setRfc("MOLA830424MVZ");
        doc.setEsUltimoGenerado(0);
        doc.setFechaRegistro(new Date());
        doc.setConsideraRenuencia(0);

        documentosVencidos.add(doc);

        procesoAfectacion.generaDocumentoLiquidacion(EtapaVigilanciaEnum.ETAPA_3, TipoDocumentoEnum.REQUERIMIENTO_MULTA, documentosVencidos);

    }
}