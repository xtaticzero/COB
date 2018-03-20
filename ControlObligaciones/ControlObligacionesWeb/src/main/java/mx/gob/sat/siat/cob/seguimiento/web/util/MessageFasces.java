/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.cob.seguimiento.web.util;

import java.io.Serializable;

/**
 *
 * @author emmanuel
 */
public class MessageFasces implements Serializable{
        
    private String message;
    private TypeMessage typeMessage;
    private static final long serialVersionUID = -4236835L;
    
    public static enum TypeMessage{
        INFO(0),WARN(1),ERROR(2),FATAL(3);
        
        private final int type;
        
        private TypeMessage(Integer type){
            this.type=type;        
        }
        
        public int getType(){
            return this.type;
        }
    }
    
    public MessageFasces(TypeMessage type,String message){
        this.message = message;
        this.typeMessage = type;        
    } 

    public String getMessage() {
        return message;
    }

    public TypeMessage getTypeMessage() {
        return typeMessage;
    }
    
}
