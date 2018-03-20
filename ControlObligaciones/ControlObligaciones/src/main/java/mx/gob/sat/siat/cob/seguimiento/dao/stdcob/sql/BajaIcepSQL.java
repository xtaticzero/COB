/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.cob.seguimiento.dao.stdcob.sql;

/**
 *
 * @author root
 */
public interface BajaIcepSQL {
    String DELETE_POR_BOIDS_ICEPS="delete from sgtt_bajaicep where boid not in BOIDS  and claveicep not in  ICEPS ";
    String DELETE_ALL="delete from sgtt_bajaicep ";
}
