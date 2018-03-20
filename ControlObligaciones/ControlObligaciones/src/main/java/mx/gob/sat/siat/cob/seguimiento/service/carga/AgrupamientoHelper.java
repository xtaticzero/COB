package mx.gob.sat.siat.cob.seguimiento.service.carga;

import java.util.StringTokenizer;

public final class AgrupamientoHelper {
    
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

   private AgrupamientoHelper(){
        super();
        
    }
}