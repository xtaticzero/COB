/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.cob.seguimiento.web.controller.bean.backing;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import mx.gob.sat.siat.cob.seguimiento.utils.AbstractBaseMB;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author juan
 */
@Controller("creaPlantillaMB")
@Scope("view")
public class CreaPlantillaManagedBean extends AbstractBaseMB {

    private String textoPlantilla;
    private List<String> atributos;
    private String atributoSelect;
    private final String SALTO = "</p>";
    private String html;
    private Integer fila;
    private Integer columna;

    public CreaPlantillaManagedBean() {
    }

    @PostConstruct
    public void init() {
        textoPlantilla = "";
        atributos = new ArrayList<String>();

        atributos.add("Nombre");
        atributos.add("Domicilio");
        atributos.add("Ejercicio");
        atributos.add("Fecha");
    }

    public void concatenaTxt() {
        if (textoPlantilla.contains(SALTO)) {
            int ultimoEspacio = textoPlantilla.lastIndexOf(SALTO);
            if (SALTO.equals(textoPlantilla.substring(ultimoEspacio).trim())) {
                textoPlantilla = textoPlantilla.replace(SALTO, " " + SALTO).trim();
                if (ultimoEspacio != -1) {
                    textoPlantilla = textoPlantilla.substring(0, ultimoEspacio);
                }
            }
        }
        /**
         * System.out.println("Select -> " + atributoSelect);
         * System.out.println("txtPlantilla -> " + getTextoPlantilla());
         */
        setTextoPlantilla(getTextoPlantilla() + " " + atributoSelect);
        /**
         * System.out.println("txtPlantilla FIN -> " + getTextoPlantilla());
         */
    }

    public void crearTabla() {
        StringBuilder tabla = new StringBuilder("<table border='1'>");

        for (int i = 0; i < fila; i++) {
            tabla.append("<tr>");
            for (int j = 0; j < columna; j++) {
                tabla.append("<td>").append(i + "," + j).append("</td>");
            }
            tabla.append("</tr>");
        }
        tabla.append("</table>");
        System.out.println(tabla.toString());

        html = tabla.toString();
    }

    public void limpiar() {
        setFila(null);
        setColumna(null);
    }

    /**
     * Getters and Setters.
     */
    /**
     * @return
     */
    public String getTextoPlantilla() {
        return textoPlantilla;
    }

    /**
     *
     * @param textoPlantilla
     */
    public void setTextoPlantilla(String textoPlantilla) {
        this.textoPlantilla = textoPlantilla;
    }

    /**
     *
     * @return
     */
    public List<String> getAtributos() {
        return atributos;
    }

    /**
     *
     * @return
     */
    public String getAtributoSelect() {
        return atributoSelect;
    }

    /**
     *
     * @param atributoSelect
     */
    public void setAtributoSelect(String atributoSelect) {
        this.atributoSelect = atributoSelect;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Integer getFila() {
        return fila;
    }

    public void setFila(Integer fila) {
        this.fila = fila;
    }

    public Integer getColumna() {
        return columna;
    }

    public void setColumna(Integer columna) {
        this.columna = columna;
    }

}
