/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
* parcial o total.
*/
package mx.gob.sat.siat.cob.background.service.utils;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code39Writer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import javax.imageio.ImageIO;
import org.apache.log4j.Logger;

/**
 * Clase Encoding39
 * 
 * Clase que define la generacion de codigo de barras.
 * 
 * @author Agustin Romero Mata - Softtek 
 */
public final class Encoding39 {
    private static int size;
    private static final Logger LOG=Logger.getLogger(Encoding39.class);

    private Encoding39() {
    }

    public static ByteArrayInputStream getBufferImagen(String cadena,
                                              int ancho,
                                              int alto,
                                              String formato) throws IOException {
        
        Map<EncodeHintType, ErrorCorrectionLevel> hintMap =
            new EnumMap<EncodeHintType, ErrorCorrectionLevel>(EncodeHintType.class);
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        Code39Writer codeWriter = new Code39Writer();

        BitMatrix byteMatrix;
        try {
            byteMatrix = codeWriter.encode(cadena, BarcodeFormat.CODE_39, ancho, alto, hintMap);
            BufferedImage bi = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
            Graphics2D ig2 = bi.createGraphics();
            ig2.setColor(Color.WHITE);
            ig2.fillRect(0, 0, ancho, alto);
            ig2.setColor(Color.BLACK);
            int d = ancho / ancho;
            int bitWidth = byteMatrix.getWidth();
            int bitHeight = byteMatrix.getHeight();            
            for (int i = 0; i < bitHeight; i++) {
                for (int j = 0; j < bitWidth; j++) {
                    boolean isSet = byteMatrix.get(j, i);
                    if (isSet) {
                        ig2.fillRect(d * j, d * i, d, d);
                    }
                }
            }            
            ByteArrayOutputStream out = new ByteArrayOutputStream();                                            
            ImageIO.write(bi, formato, out);
            size = out.size();
            out.close();
            return new ByteArrayInputStream(out.toByteArray());

        } catch (WriterException e) {
            Encoding39.LOG.error(e);
        } catch (IOException e) {
            Encoding39.LOG.error(e);
        }
        return null;
    }
        
    public static int getSize() {
        return size;
    }
}
