package mx.gob.sat.siat.cob.seguimiento.web.util.validador;

import java.text.SimpleDateFormat;

import java.util.Collections;
import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.dto.compartidos.CatalogoBase;
import mx.gob.sat.siat.cob.seguimiento.util.constante.ConstantsCatalogos;
import org.apache.commons.lang.StringUtils;


public final class ValidadorRegistro {

    private final static int NUMERO_ARGUMENTOS_VALIDOS_CADENA = 9;
    private final static int NUMERO_ARGUMENTOS_VALIDOS_CADENA_EJERCICIO_FISCAL = 7;
    private final static int ERR_NO_CORRECT_FORMAT = -4;
    private final static int ERR_NO_NUMERIC = -3;
    private final static int ERR_NO_ALFANUMERIC = -2;
    private final static int ERR_NO_ALFA = -5;
    private final static int ERR_LONGITUD = -1;
    private final static int ERR_CATALOGO = -6;
    private final static int ERR_VACIO = -7;
    public final static int EXITO = 0;

    private static List<CatalogoBase> catalogoObligacion;
    private static List<CatalogoBase> catalogoRegimen;
    private static List<CatalogoBase> catalogoEjercicioFiscal;
    private static List<CatalogoBase> catalogoPeriodo;
    private static List<CatalogoBase> catalogoPeriodicidad;
    private static List<CatalogoBase> catalogoPeriodoXPeriodicidad;
    private static List<CatalogoBase> catalogoFundamLegalesExistentes;

    public static StringBuilder valida(String registro) {
        String[] elementosCadena = registro.split("\\|");
        StringBuilder respuesta = new StringBuilder();
        if(elementosCadena.length < NUMERO_ARGUMENTOS_VALIDOS_CADENA){
            respuesta.append("|Registro con menos argumentos de los que debería|");
            return respuesta;
        }
        if(elementosCadena.length > NUMERO_ARGUMENTOS_VALIDOS_CADENA){
            respuesta.append("|Registro con más argumentos de los que debería|");
            return respuesta;
        }
        String boid = elementosCadena[0].trim();
        int respValidacion = validaBOID(boid);
        if (respValidacion == ERR_VACIO) {
            respuesta.append("|BOID campo vacio");
        } else if (respValidacion == ERR_NO_NUMERIC) {
            respuesta.append("|BOID caracteres no numéricos");
        } else if (respValidacion == ERR_LONGITUD) {
            respuesta.append("|BOID diferente de 31 caractéres");
        }

        String rfc = elementosCadena[1];
        respValidacion = validaRFC(rfc);
        if (respValidacion == ERR_VACIO) {
            respuesta.append("|RFC campo vacio");
        }
        if (respValidacion == ERR_NO_ALFANUMERIC) {
            respuesta.append("|RFC caracteres no alfanumericos");
        }
        if (respValidacion == ERR_LONGITUD) {
            respuesta.append("|RFC > 13 caractéres o < 12");
        }

        String icep = elementosCadena[2];
        respValidacion = validaICEP(icep);
        if (respValidacion == ERR_VACIO) {
            respuesta.append("|ICEP campo vacio");
        }
        if (respValidacion == ERR_NO_NUMERIC) {
            respuesta.append("|ICEP caracteres no numericos");
        }
        if (respValidacion == ERR_LONGITUD) {
            respuesta.append("|ICEP > 31 caractéres");
        }
        String icepObl = elementosCadena[ConstantsCatalogos.TRES];
        respValidacion = validaCVEOBLIGACION(icepObl);
        if (respValidacion == ERR_VACIO) {
            respuesta.append("|ICEP_CLAVE_OBLIGACION campo vacio");
        }
        if (respValidacion == ERR_NO_NUMERIC) {
            respuesta.append("|ICEP_CLAVE_OBLIGACION caractéres no numéricos");
        }
        if (respValidacion == ERR_LONGITUD) {
            respuesta.append("|El ICEP_CLAVE_OBLIGACION > 3 caractéres");
        }
        if(respValidacion == ERR_CATALOGO) {
            respuesta.append("|ICEP_CLAVE_OBLIGACION no existe en el catalogo");
        }
        String icepRegimen = elementosCadena[ConstantsCatalogos.CUATRO];
        respValidacion = validaCVEREGIMEN(icepRegimen);
        if (respValidacion == ERR_VACIO) {
            respuesta.append("|ICEP_CLAVE_REGIMEN campo vacio");
        } else if (respValidacion == ERR_NO_NUMERIC) {
            respuesta.append("|ICEP_CLAVE_REGIMEN caractéres no numéricos");
        }
        if (respValidacion == ERR_LONGITUD) {
            respuesta.append("|El ICEP_CLAVE_REGIMEN > 3 caractéres");
        }
        if(respValidacion == ERR_CATALOGO) {
            respuesta.append("|ICEP_CLAVE_REGIMEN no existe en el catalogo");
        }
        String icepEjercicio = elementosCadena[ConstantsCatalogos.CINCO];
        respValidacion = validaCVEEJERCICIO(icepEjercicio);
        if (respValidacion == ERR_VACIO) {
            respuesta.append("|ICEP_CLAVE_EJERCICIO campo vacio");
        } else if (respValidacion == ERR_NO_NUMERIC) {
            respuesta.append("El ICEP_CLAVE_EJERCICIO  caractéres no numéricos");
        }
        if (respValidacion == ERR_LONGITUD) {
            respuesta.append("|ICEP_CLAVE_EJERCICIO es diferente 4 caractéres");
        }
        if(respValidacion == ERR_CATALOGO) {
            respuesta.append("|ICEP_CLAVE_EJERCICIO no existe en el catalogo");
        }
        String icepPeriodo = elementosCadena[ConstantsCatalogos.SEIS];
        respValidacion = validaCVEPERIODO(icepPeriodo);
        if (respValidacion == ERR_VACIO) {
            respuesta.append("|ICEP_CLAVE_PERIODO campo vacio");
        } else if (respValidacion == ERR_NO_NUMERIC) {
            respuesta.append("|ICEP_CLAVE_PERIODO caractéres no numéricos");
        }
        if (respValidacion == ERR_LONGITUD) {
            respuesta.append("|ICEP_CLAVE_PERIODO > 2 caractéres o < 1");
        }
        if(respValidacion == ERR_CATALOGO) {
            respuesta.append("|ICEP_CLAVE_PERIODO no existe en el catalogo");
        }
        String icepPeriodicidad = elementosCadena[ConstantsCatalogos.SIETE];
        respValidacion = validaCVEPERIODICIDAD(icepPeriodicidad);
        if (respValidacion == ERR_VACIO) {
            respuesta.append("|ICEP_CLAVE_PERIODICIDAD campo vacio");
        } else if (respValidacion == ERR_NO_ALFA) {
            respuesta.append("|ICEP_CLAVE_PERIODICIDAD contiene números");
        }
        if (respValidacion == ERR_LONGITUD) {
            respuesta.append("|ICEP_CLAVE_PERIODICIDAD > 1 caractéres");
        }
        if(respValidacion == ERR_CATALOGO) {
            respuesta.append("|ICEP_CLAVE_PERIODICIDAD no existe en el catalogo");
        }
        if(buscarIdPeriodoXPeriodicidad(icepPeriodo+"-"+icepPeriodicidad)<0){
            respuesta.append("|ICEP_CLAVE_PERIODO_PERIODICIDAD la combinacion periodo y periodicidad no existe");
        }
        String icepFechaVencimiento = elementosCadena[ConstantsCatalogos.OCHO];
        respValidacion = validaICEPFECHAVENC(icepFechaVencimiento);
        if (respValidacion == ERR_VACIO) {
            respuesta.append("|ICEP_FECHA_VENCIMIENTO campo vacio");
        } else if (respValidacion == ERR_NO_CORRECT_FORMAT) {
            respuesta.append("|ICEP_FECHA_VENCIMIENTO el formato debe ser mm/dd/yyyy");
        }
        if (respValidacion == ERR_LONGITUD) {
            respuesta.append("|ICEP_FECHA_VENCIMIENTO el formato debe ser mm/dd/yyyy");
        }
        if(respuesta.length() > 0){
            respuesta.append("|");
            return respuesta;
        }
        respuesta.append("EXITO");
        return respuesta;
    }
    
    public static StringBuilder validaCatalogo(String registro, String catalogo) {
        String[] elementosCadena = registro.split("\\|");
        StringBuilder respuesta = new StringBuilder();
        StringBuilder strIdsDeValidacion = new StringBuilder();
        if (catalogo.equals(ConstantsCatalogos.CAT_EJERCICIO)) {
            if (elementosCadena.length < NUMERO_ARGUMENTOS_VALIDOS_CADENA_EJERCICIO_FISCAL) {
                respuesta.append("|Registro con menos argumentos de los que debería|");
                return respuesta;
            }
            if (elementosCadena.length > NUMERO_ARGUMENTOS_VALIDOS_CADENA_EJERCICIO_FISCAL) {
                respuesta.append("|Registro con más argumentos de los que debería|");
                return respuesta;
            }

            String idObligacion = elementosCadena[0];
            int respValidacion = validaCVEOBLIGACION(idObligacion);
            if (respValidacion == ERR_VACIO) {
                respuesta.append("|CLAVE_OBLIGACION campo vacio");
            } else if (respValidacion == ERR_NO_NUMERIC) {
                respuesta.append("|CLAVE_OBLIGACION caractéres no numéricos");
            }
            if (respValidacion == ERR_LONGITUD) {
                respuesta.append("|El CLAVE_OBLIGACION > 3 caractéres");
            }
            if (respValidacion == ERR_CATALOGO) {
                respuesta.append("|CLAVE_OBLIGACION no existe en el catalogo");
            }
            String idRegimen = elementosCadena[1];
            respValidacion = validaCVEREGIMEN(idRegimen);
            if (respValidacion == ERR_VACIO) {
                respuesta.append("|CLAVE_REGIMEN campo vacio");
            } else if (respValidacion == ERR_NO_NUMERIC) {
                respuesta.append("|CLAVE_REGIMEN caractéres no numéricos");
            }
            if (respValidacion == ERR_LONGITUD) {
                respuesta.append("|CLAVE_REGIMEN > 3 caractéres");
            }
            if (respValidacion == ERR_CATALOGO) {
                respuesta.append("|CLAVE_REGIMEN no existe en el catalogo");
            }
            String idEjercicio = elementosCadena[2];
            respValidacion = validaCVEEJERCICIO(idEjercicio);
            if (respValidacion == ERR_VACIO) {
                respuesta.append("|CLAVE_EJERCICIO campo vacio");
            } else if (respValidacion == ERR_NO_NUMERIC) {
                respuesta.append("La CLAVE_EJERCICIO  caractéres no numéricos");
            }
            if (respValidacion == ERR_LONGITUD) {
                respuesta.append("|CLAVE_EJERCICIO es diferente 4 caractéres");
            }
            if (respValidacion == ERR_CATALOGO) {
                respuesta.append("|CLAVE_EJERCICIO no existe en el catalogo");
            }
            String idPeriodo = elementosCadena[ConstantsCatalogos.CUATRO];
            respValidacion = validaCVEPERIODO(idPeriodo);
            if (respValidacion == ERR_VACIO) {
                respuesta.append("|CLAVE_PERIODO campo vacio");
            } else if (respValidacion == ERR_NO_NUMERIC) {
                respuesta.append("|CLAVE_PERIODO caractéres no numéricos");
            }
            if (respValidacion == ERR_LONGITUD) {
                respuesta.append("|CLAVE_PERIODO > 2 caractéres o < 1");
            }
            if (respValidacion == ERR_CATALOGO) {
                respuesta.append("|CLAVE_PERIODO no existe en el catalogo");
            }
            String periodicidad = elementosCadena[ConstantsCatalogos.TRES];
                respValidacion = validaCVEPERIODICIDAD(periodicidad);
            if (respValidacion == ERR_VACIO) {
                respuesta.append("|CLAVE_PERIODICIDAD campo vacio");
            } else if (respValidacion == ERR_NO_ALFA) {
                respuesta.append("|CLAVE_PERIODICIDAD contiene números");
            }
            if (respValidacion == ERR_LONGITUD) {
                respuesta.append("|CLAVE_PERIODICIDAD > 1 caractéres");
            }
            if (respValidacion == ERR_CATALOGO) {
                respuesta.append("|CLAVE_PERIODICIDAD no existe en el catalogo");
            }
            strIdsDeValidacion.setLength(0);
            strIdsDeValidacion.append(idPeriodo).append("-").append(periodicidad);
            if (buscarIdPeriodoXPeriodicidad(strIdsDeValidacion.toString()) < 0) {
                respuesta.append("|CLAVE_PERIODO_PERIODICIDAD la combinacion periodo y periodicidad no existe");
            }
            strIdsDeValidacion.setLength(0);
            strIdsDeValidacion.append(idObligacion).append(",");
            strIdsDeValidacion.append(idRegimen).append(",");
            strIdsDeValidacion.append(idPeriodo).append(",");
            strIdsDeValidacion.append(periodicidad).append(",");
            strIdsDeValidacion.append(idEjercicio);
            if (buscarFundamLegalesExistentes(strIdsDeValidacion.toString()) > 0) {
                respuesta.append("|CLAVES_OBLIGACION_REGIMEN_PERIODO_PERIODICIDAD_EJERCICIO la combinacion obligacion, regimen, ejercicio, periodo y periodicidad ya existe");
            }
            String fechaVencimiento = elementosCadena[ConstantsCatalogos.CINCO];
            respValidacion = validaFundLegFECHAVENC(fechaVencimiento);
            if (respValidacion == ERR_VACIO) {
                respuesta.append("|FECHA_VENCIMIENTO campo vacio");
            } else if (respValidacion == ERR_NO_CORRECT_FORMAT) {
                respuesta.append("|FECHA_VENCIMIENTO el formato debe ser dd/mm/yyyy");
            }
            if (respValidacion == ERR_LONGITUD) {
                respuesta.append("|FECHA_VENCIMIENTO el formato debe ser dd/mm/yyyy");
            }
            String fundamentoLegal = elementosCadena[ConstantsCatalogos.SEIS];
            respValidacion = validaFundamentoLegal(fundamentoLegal);
            if (respValidacion == ERR_LONGITUD) {
                respuesta.append("|FUNDAMENTO_LEGAL > a 500 caractéres");
            }
            if (respuesta.length() > 0) {
                respuesta.append("|");
                return respuesta;
            }
            respuesta.append("EXITO");
        }
        return respuesta;

    }
    
    private static int validaBOID(String argumento){
        if (argumento.isEmpty()) {
            return ERR_VACIO;
        }
        if (!StringUtils.isNumeric(argumento)) {
            return ERR_NO_NUMERIC;
        }
        if (argumento.length() > ConstantsCatalogos.TREINTAYUNO) {
            return ERR_LONGITUD;
        }
        return EXITO;
    }

    private static int validaRFC (String argumento){
        if (argumento.isEmpty()) {
            return ERR_VACIO;
        }
        if (argumento.length() > ConstantsCatalogos.TRECE || argumento.length() < ConstantsCatalogos.DOCE) {
            return ERR_LONGITUD;
        }
        return EXITO;
    }

    private static int validaICEP(String argumento) {
        if (argumento.isEmpty()) {
            return ERR_VACIO;
        }
        if (!StringUtils.isNumeric(argumento)) {
            return ERR_NO_NUMERIC;
        }
        if (argumento.length() > ConstantsCatalogos.TREINTAYUNO) {
            return ERR_LONGITUD;
        }
        return EXITO;
    }

    private static int validaCVEOBLIGACION(String argumento) {
        if (argumento.isEmpty()) {
            return ERR_VACIO;
        }
        if (!StringUtils.isNumeric(argumento)) {
            return ERR_NO_NUMERIC;
        }
        if (argumento.length() > ConstantsCatalogos.TRES) {
            return ERR_LONGITUD;
        }
        if (buscarIdObligacion(argumento)<0) {
            return ERR_CATALOGO;
        }
        return EXITO;
    }

    private static int validaCVEREGIMEN(String argumento) {
        if (argumento.isEmpty()) {
            return ERR_VACIO;
        }
        if (!StringUtils.isNumeric(argumento)) {
            return ERR_NO_NUMERIC;
        }
        if (argumento.length() > ConstantsCatalogos.TRES) {
            return ERR_LONGITUD;
        }
        if (buscarIdRegimen(argumento)<0) {
            return ERR_CATALOGO;
        }
        return EXITO;
    }

    private static int validaCVEEJERCICIO(String argumento) {
        if (argumento.isEmpty()) {
            return ERR_VACIO;
        }
        if (!StringUtils.isNumeric(argumento)) {
            return ERR_NO_NUMERIC;
        }
        if (argumento.length() != ConstantsCatalogos.CUATRO) {
            return ERR_LONGITUD;
        }
        if (buscarIdEjercicioFiscal(argumento)<0) {
            return ERR_CATALOGO;
        }
        return EXITO;
    }

    private static int validaCVEPERIODO(String argumento) {
        if (argumento.isEmpty()) {
            return ERR_VACIO;
        }
        if (!StringUtils.isNumeric(argumento)) {
            return ERR_NO_NUMERIC;
        }
        if (argumento.length() > 2 ||argumento.length() < 1) {
            return ERR_LONGITUD;
        }
        if (buscarIdPeriodo(argumento)<0) {
            return ERR_CATALOGO;
        }
        return EXITO;
    }

    private static int validaCVEPERIODICIDAD(String argumento) {
        if (argumento.isEmpty()) {
            return ERR_VACIO;
        }
        if (!StringUtils.isAlpha(argumento)) {
            return ERR_NO_ALFA;
        }
        if (argumento.length() > 1) {
            return ERR_LONGITUD ;
        }
        if (buscarIdPeriodicidad(argumento)<0) {
            return ERR_CATALOGO;
        }
        return EXITO;
    }

    private static int validaICEPFECHAVENC(String argumento) {
        if (argumento.isEmpty()) {
            return ERR_VACIO;
        }
        try{
            if(argumento.length()!=ConstantsCatalogos.DIEZ){
                return ERR_LONGITUD;
            }
            SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy");
            format.setLenient(false);
            format.parse(argumento);
        }catch(Exception ex){
            return ERR_NO_CORRECT_FORMAT;
        }
        return EXITO;
    }
    
    private static int validaFundLegFECHAVENC(String argumento) {
        if (argumento.isEmpty()) {
            return ERR_VACIO;
        }
        try {
            if (argumento.length() != ConstantsCatalogos.DIEZ) {
                return ERR_LONGITUD;
            }
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            format.setLenient(false);
            format.parse(argumento);
        } catch (Exception ex) {
            return ERR_NO_CORRECT_FORMAT;
        }
        return EXITO;
    }
    
    private static int validaFundamentoLegal(String argumento) {
        if (argumento.isEmpty()) {
            return ERR_VACIO;
        }
        if (argumento.length() > ConstantsCatalogos.QUINIENTOS) {
            return ERR_LONGITUD;
        }
        return EXITO;
    }

    private ValidadorRegistro() {
    }


    //catalogo de obligacion####################################################
    public static List<CatalogoBase> getCatalogoObligacion() {
        return catalogoObligacion;
    }

    public static void setCatalogoObligacion(List<CatalogoBase> catalogoObligacion) {
        ValidadorRegistro.catalogoObligacion = catalogoObligacion;
    }

    private static int buscarIdObligacion(String argumento){
        return Collections.binarySearch(getCatalogoObligacion(), new CatalogoBase(Integer.parseInt(argumento)));
    }
    //catalogo de regimen#######################################################
    public static List<CatalogoBase> getCatalogoRegimen() {
        return catalogoRegimen;
    }

    public static void setCatalogoRegimen(List<CatalogoBase> catalogoRegimen) {
        ValidadorRegistro.catalogoRegimen = catalogoRegimen;
    }

    private static int buscarIdRegimen(String argumento){
        return Collections.binarySearch(getCatalogoRegimen(), new CatalogoBase(Integer.parseInt(argumento)));
    }
    //catalogo de ejercicio fiscal##############################################
    public static List<CatalogoBase> getCatalogoEjercicioFiscal() {
        return catalogoEjercicioFiscal;
    }

    public static void setCatalogoEjercicioFiscal(List<CatalogoBase> catalogoEjercicioFiscal) {
        ValidadorRegistro.catalogoEjercicioFiscal = catalogoEjercicioFiscal;
    }

    private static int buscarIdEjercicioFiscal(String argumento){
        return Collections.binarySearch(getCatalogoEjercicioFiscal(), new CatalogoBase(Integer.parseInt(argumento)));
    }
    //catalogo de periodo#######################################################
    public static List<CatalogoBase> getCatalogoPeriodo() {
        return catalogoPeriodo;
    }

    public static void setCatalogoPeriodo(List<CatalogoBase> catalogoPeriodo) {
        ValidadorRegistro.catalogoPeriodo = catalogoPeriodo;
    }

    private static int buscarIdPeriodo(String argumento){
        return Collections.binarySearch(getCatalogoPeriodo(), new CatalogoBase(Integer.parseInt(argumento)));
    }
    //catalogo de periodicidad##################################################
    public static List<CatalogoBase> getCatalogoPeriodicidad() {
        return catalogoPeriodicidad;
    }

    public static void setCatalogoPeriodicidad(List<CatalogoBase> catalogoPeriodicidad) {
        ValidadorRegistro.catalogoPeriodicidad = catalogoPeriodicidad;
    }

    private static int buscarIdPeriodicidad(String argumento){
        int estatus=-1;
        for(CatalogoBase cb: getCatalogoPeriodicidad()){
            if(cb.getIdString().equals(argumento)){
                estatus=1;
            }
        }
        return estatus;
    }
    //catalogo de PeriodoXPeriodicidad##########################################
    public static List<CatalogoBase> getCatalogoPeriodoXPeriodicidad() {
        return catalogoPeriodoXPeriodicidad;
    }

    public static void setCatalogoPeriodoXPeriodicidad(List<CatalogoBase> catalogoPeriodoXPeriodicidad) {
        ValidadorRegistro.catalogoPeriodoXPeriodicidad = catalogoPeriodoXPeriodicidad;
    }

    private static int buscarIdPeriodoXPeriodicidad(String pPeriodoXPeriodicidad){
        int estatus=-1;
        for(CatalogoBase cb: getCatalogoPeriodoXPeriodicidad()){
            if(cb.getIdString().equals(pPeriodoXPeriodicidad)){
                estatus=1;
                break;
            }
        }
        return estatus;
    }
    
    //catalogo de fundamentos legales existentes##########################################
    public static List<CatalogoBase> getCatalogoFundamLegalesExistentes() {
        return catalogoFundamLegalesExistentes;
    }

    public static void setCatalogoFundamLegalesExistentes(List<CatalogoBase> catalogoFundamLegalesExistentes) {
        ValidadorRegistro.catalogoFundamLegalesExistentes = catalogoFundamLegalesExistentes;
    }

    private static int buscarFundamLegalesExistentes(String pFundamentoLegal){
        int estatus=-1;
        for(CatalogoBase cb: getCatalogoFundamLegalesExistentes()){
            if(cb.getNombre().equals(pFundamentoLegal)){
                estatus=1;
                break;
            }
        }
        return estatus;
    }

}
