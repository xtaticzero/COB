package mx.gob.sat.siat.cob.background.service.carga.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.cob.background.service.carga.CargaOmisosService;
import mx.gob.sat.siat.cob.background.test.ContextLoaderTest;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.BaseDocumentoDAO;
import mx.gob.sat.siat.cob.seguimiento.dao.stdcob.SgttResolucionDocDAO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.AdministrativoNivelCargo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.Documento;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.MultaAprobarGrupo;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.NumeroControlDTO;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.EstadoMultaEnum;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.enums.NivelEmisionEnum;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author christian.ventura
 */
public class CargaOmisosServiceImplTest extends ContextLoaderTest {

    @Test
    @Ignore
    public void getNumeroResolucionesTest() throws Exception {
        BaseDocumentoDAO d = (BaseDocumentoDAO) getContext().getBean("baseDocumentoDAOImpl");
        DecimalFormat df1 = new DecimalFormat("0000");
        List<Documento> documentos=new ArrayList<Documento>();
        Documento doc=new Documento();
        for(int i=0;i<100;){
            doc=new Documento();
            doc.setBoid("204490721684549901917528110760");
            doc.setNumeroControl("11061213000"+df1.format(i++)+"C42102");
            documentos.add(doc);
            doc=new Documento();
            doc.setBoid("1028801258014836012526187110137");
            doc.setNumeroControl("11061213000"+df1.format(i++)+"C42102");
            documentos.add(doc);
            doc=new Documento();
            doc.setBoid("1028802568562327577342326022045");
            doc.setNumeroControl("11061213000"+df1.format(i++)+"C42102");
            documentos.add(doc);
            doc=new Documento();
            doc.setBoid("1028803716980232556818599020352");
            doc.setNumeroControl("11061213000"+df1.format(i++)+"C42102");
            documentos.add(doc);
            doc=new Documento();
            doc.setBoid("1028806064481538323678469096548");
            doc.setNumeroControl("11061213000"+df1.format(i++)+"C42102");
            documentos.add(doc);
            doc=new Documento();
            doc.setBoid("1028806856552122787473058005612");
            doc.setNumeroControl("11061213000"+df1.format(i++)+"C42102");
            documentos.add(doc);
            doc=new Documento();
            doc.setBoid("1028807357014631379018679022374");
            doc.setNumeroControl("11061213000"+df1.format(i++)+"C42102");
            documentos.add(doc);
            doc=new Documento();
            doc.setBoid("1028808160907164864647263013092");
            doc.setNumeroControl("11061213000"+df1.format(i++)+"C42102");
            documentos.add(doc);
            doc=new Documento();
            doc.setBoid("1028809057123490711377314189697");
            doc.setNumeroControl("11061213000"+df1.format(i++)+"C42102");
            documentos.add(doc);
            doc=new Documento();
            doc.setBoid("1028809511987053075807442249650");
            doc.setNumeroControl("11061213000"+df1.format(i++)+"C42102");
            documentos.add(doc);
        }
        Long inicio=System.currentTimeMillis();
        d.generaNumeroResoluciones(documentos, "RESOLMOTIVO_INCUMPLIMIENTO", new Integer(1));
        System.out.println(", tiempo:"+(System.currentTimeMillis()-inicio)+"///////////////////");

        //Assert.assertEquals("igual","110811130313470C42102", numeroControl);
    }
    
    @Test
    @Ignore
    public void getNumeroControlTest() throws Exception {
        BaseDocumentoDAO d = (BaseDocumentoDAO) getContext().getBean("baseDocumentoDAOImpl");

        Long inicio=System.currentTimeMillis();
        NumeroControlDTO numeroControl;

        numeroControl = d.getNumeroControl("204490721684549901917528110760", 2, 1);
        System.out.println(", tiempo:"+(System.currentTimeMillis()-inicio)+"///////////////////numeroControl:" + numeroControl);
        System.out.println("numeroControl:" + numeroControl);

        inicio=System.currentTimeMillis();
        numeroControl = d.getNumeroResolucion("204490721684549901917528110760", "RESOLMOTIVO_INCUMPLIMIENTO");
        System.out.println(", tiempo:"+(System.currentTimeMillis()-inicio)+"///////////////////numeroControl:" + numeroControl);
        System.out.println("NumeroResolucion:" + numeroControl);
    }
    
    @Test
    @Ignore
    public void getListas() throws Exception {
        SgttResolucionDocDAO d = (SgttResolucionDocDAO) getContext().getBean("sgttResolucionDocDAOImpl");
        SimpleDateFormat sdf=new SimpleDateFormat("yyy-MM-dd");
        MultaAprobarGrupo multaAprobarGrupo=new MultaAprobarGrupo();
        multaAprobarGrupo.setIdMedioEnvio(3);
        multaAprobarGrupo.setIdTipoFirma(1);
        multaAprobarGrupo.setIdTipoMulta("RESOLMOTIVO_INCUMPLIMIENTO");
        multaAprobarGrupo.setFechaEmision(sdf.parse("2014-08-15"));
        AdministrativoNivelCargo administrativoNivelCargo=new AdministrativoNivelCargo();
        administrativoNivelCargo.setNivelEmision(NivelEmisionEnum.LOCAL);
        administrativoNivelCargo.setIdCargoAdministrativo(5L);
        administrativoNivelCargo.setIdAdministacionLocal("07");
        
        Long inicio=System.currentTimeMillis();
        d.actualizarAdmonLocal(multaAprobarGrupo, EstadoMultaEnum.PENDIENTE_DE_PROCESAR, administrativoNivelCargo);
        System.out.println("tiempo:"+(System.currentTimeMillis()-inicio)/1000/60+"/////");

    }

    @Test
    @Ignore
    public void ejecutarCargaOmisosTest() {
        CargaOmisosService c = (CargaOmisosService) getContext().getBean("cargaOmisosServiceImpl");
        c.ejecutarCargaOmisos();
        System.out.println("termino.....");
    }
    
    @Test
    @Ignore
    public void truncateTAbleTest() throws Exception {
        BaseDocumentoDAO d = (BaseDocumentoDAO) getContext().getBean("baseDocumentoDAOImpl");

        Long inicio=System.currentTimeMillis();
        System.out.println(", tiempo:"+(System.currentTimeMillis()-inicio)+"//////////////////");
        
        d.borrarTablaNumeroResolucion();
        
        inicio=System.currentTimeMillis();
        System.out.println(", tiempo:"+(System.currentTimeMillis()-inicio)+"//////////////////");

    }
}
