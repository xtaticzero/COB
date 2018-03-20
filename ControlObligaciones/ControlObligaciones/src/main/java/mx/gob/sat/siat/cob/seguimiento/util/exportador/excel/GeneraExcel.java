package mx.gob.sat.siat.cob.seguimiento.util.exportador.excel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.List;

import mx.gob.sat.siat.cob.seguimiento.util.exportador.pdf.ModeloPDFExcel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public abstract class GeneraExcel {

    private ByteArrayOutputStream baos = null;
    private HSSFWorkbook wb = null;
    private HSSFSheet worksheet = null;
    private HSSFCellStyle cellStyle = null;


    /**
     *
     * @param modelo
     * @return
     */
    public ByteArrayOutputStream generarExcel(ModeloPDFExcel modelo) {
        try {
            inicilizar();
            crearTitulo(modelo.getTitulo());
            crearCabezera(modelo.getNombreColumnas());
            colocarContenido(modelo.getDatos());
            wb.write(baos);

        } catch (IOException ex) {

            ex.getMessage();
        }
        return baos;
    }

    /**
     *
     */
    public void inicilizar() {
        baos = new ByteArrayOutputStream();
        wb = new HSSFWorkbook();
        worksheet = wb.createSheet(" Worksheet");
        cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    }

    /**
     *
     * @param encabezados
     */
    public void crearCabezera(List<String> encabezados) {

        HSSFRow header = worksheet.createRow((short) 1);
        int c = 0;
        for (String str : encabezados) {
            HSSFCell cellC = header.createCell(c);
            cellC.setCellValue(str);
            cellC.setCellStyle(cellStyle);
            c++;
        }
    }

    /**
     *
     * @param titulo
     */
    public void crearTitulo(String titulo) {
        HSSFRow tittle = worksheet.createRow((short) 0);
        HSSFCell cellC = tittle.createCell(0);
        cellC.setCellValue(titulo);

    }

    /**
     *
     * @param datos
     */
    public abstract void colocarContenido(List<Object> datos);

    public void setBaos(ByteArrayOutputStream baos) {
        this.baos = baos;
    }

    public ByteArrayOutputStream getBaos() {
        return baos;
    }

    public void setWb(HSSFWorkbook wb) {
        this.wb = wb;
    }

    public HSSFWorkbook getWb() {
        return wb;
    }

    public void setWorksheet(HSSFSheet worksheet) {
        this.worksheet = worksheet;
    }

    public HSSFSheet getWorksheet() {
        return worksheet;
    }

    public void setCellStyle(HSSFCellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }

    public HSSFCellStyle getCellStyle() {
        return cellStyle;
    }
}
