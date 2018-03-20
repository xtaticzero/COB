package mx.gob.sat.siat.cob.seguimiento.web.util;

import java.util.StringTokenizer;

public final class CadenaDecomposer {
    private static final String PATRON_DESCOMPOSICION = "|";
    
    
    public static String[] descomponer(String argumento) {
        StringTokenizer descomponedor = new StringTokenizer(argumento,PATRON_DESCOMPOSICION);
        String[] resultado = new String[descomponedor.countTokens()];
        int contador = 0;
        while(descomponedor.hasMoreTokens()){            
            resultado[contador] = descomponedor.nextToken();
            contador++;
        }
        return resultado;
    }

    private CadenaDecomposer() {
    }

}
