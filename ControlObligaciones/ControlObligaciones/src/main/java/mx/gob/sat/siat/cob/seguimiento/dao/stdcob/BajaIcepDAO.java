/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.cob.seguimiento.dao.stdcob;

import java.util.Set;

/**
 *
 * @author root
 */
public interface BajaIcepDAO {
    
    Integer borrarPorBoidEIcep(Set<String> boids,Set<Long> iceps);
    
}
