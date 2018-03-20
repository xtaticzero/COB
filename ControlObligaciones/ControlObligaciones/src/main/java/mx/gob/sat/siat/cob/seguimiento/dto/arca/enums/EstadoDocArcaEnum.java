/*	****************************************************************
	* Nombre de archivo: EstadoDocArcaEnum.java
	* Autores: Emmanuel Estrada Gonzalez 		 	 		
	* Bitácora de modificaciones:
	* CR/Defecto Fecha Autor Descripción del cambio
	* ---------------------------------------------------------------------------
	* N/A 23/10/2013 <Nombre del desarrollador que está modificando el archivo>
	* ---------------------------------------------------------------------------
*/
package mx.gob.sat.siat.cob.seguimiento.dto.arca.enums;

public enum EstadoDocArcaEnum {
    //ESTADOS ARCA    
    
    
    ARCA_PENDIENTE(0),
    ARCA_LOCALIZADO(1),
    ARCA_NO_LOCALIZADO(2),
    ARCA_NO_TRABAJADO(3),
    ARCA_DISTINTA_CRH(4),
    ARCA_ROBO_O_EXTRAVIO(5),
    ARCA_HUELGA(6),
    ARCA_FUSION(7),
    ARCA_DISTINTA_ALR(8),
    ARCA_ENTREGADO(20),
    ARCA_DEV_NO_LOCALIZADO(30),
    ARCA_DEV_CAMBIO_DOM(31),
    ARCA_DEV_NO_EXISTE_CALLE(32),
    ARCA_DEV_NO_EXISTE_NUMERO(33),
    ARCA_DEV_DOM_INSUFICIENTE(34),
    ARCA_DEV_FALLECIO(35),
    ARCA_DEV_OTRO_DESTINATARIO(36),
    ARCA_DEV_NO_RECLAMADO(37),
    ARCA_DEV_NO_QUISO_RECIBIR(38),
    ARCA_DEV_SE_ENCUENTRA_EN(39),
    ARCA_DEV_NO_TRABAJADO(40),
    ARCA_DEV_OTRAS(41),
    ARCA_IMPRESO(100),
    ARCA_CARGADO_EN_HNVU(200),
    ARCA_CANCELADO_POR_SIAT(300);    
    
    private int idEdoDoc;

    private EstadoDocArcaEnum(int value) {
            this.idEdoDoc = value;
    }
    
    public int getIdEdoDoc(){
        return idEdoDoc;
    }
}
