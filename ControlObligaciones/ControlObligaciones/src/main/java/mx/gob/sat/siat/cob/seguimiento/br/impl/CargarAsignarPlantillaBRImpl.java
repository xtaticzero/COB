package mx.gob.sat.siat.cob.seguimiento.br.impl;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mx.gob.sat.siat.cob.seguimiento.br.CargarAsignarPlantillaBR;
import mx.gob.sat.siat.cob.seguimiento.dto.stdcob.PlantillaDocumento;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service
public class CargarAsignarPlantillaBRImpl implements CargarAsignarPlantillaBR{
    /**
     *
     */
    public CargarAsignarPlantillaBRImpl() {
        super();
    }
    
    private static final String EXTENSION_ARCHIVO_WORD="([^\\s]+(\\.(?i)(doc|docx))$)";
    private Pattern pattern;
    private Matcher matcher;
    
    /**
     *
     * @param plantilla
     * @param listaPlantillas
     * @return
     */
    @Override
    public int existePlantillaRepetida(PlantillaDocumento plantilla, List<PlantillaDocumento> listaPlantillas) {
        int idPlantillaRepetida=0;
        
        for(PlantillaDocumento p:listaPlantillas){
            if(plantilla.getIdTipoDocumento().toString().equals(p.getIdTipoDocumento().toString())
               && plantilla.getNivelEmision().toString().equals(p.getNivelEmision().toString())
               && plantilla.getIdCargoAdministrativo().toString().equals(p.getIdCargoAdministrativo().toString())
                && plantilla.getIdEtapaVigilancia().toString().equals(p.getIdEtapaVigilancia().toString())
                    && plantilla.getIdTipoFirma().toString().equals(p.getIdTipoFirma().toString())
                      && plantilla.getIdMedioEnvio().toString().equals(p.getIdMedioEnvio().toString())){
                
                idPlantillaRepetida=p.getIdPlantilla();
                break;
            }
            
        }
        
        return idPlantillaRepetida;
    }
    
    /**
     *
     * @param plantillaDocumento
     * @return
     */
    
    
    /**
     *
     * @param fileName
     * @return
     */
    @Override
    public boolean validarArchivo(final String fileName){
        pattern=Pattern.compile(EXTENSION_ARCHIVO_WORD);
        matcher = pattern.matcher(fileName);
        return matcher.matches();
    }
    
    /**
     *
     * @param pattern
     */
    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    /**
     *
     * @return
     */
    public Pattern getPattern() {
        return pattern;
    }

    /**
     *
     * @param matcher
     */
    public void setMatcher(Matcher matcher) {
        this.matcher = matcher;
    }

    /**
     *
     * @return
     */
    public Matcher getMatcher() {
        return matcher;
    }
}
