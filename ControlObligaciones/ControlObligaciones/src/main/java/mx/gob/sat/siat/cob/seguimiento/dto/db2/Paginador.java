package mx.gob.sat.siat.cob.seguimiento.dto.db2;

import java.io.Serializable;

public class Paginador implements Serializable {

    private static final int MINIMO_PAGINAS = 1;
    private static final int TAMANIO_NO_VALIDO = 0;

    private long rowInicial;
    private long rowFinal;
    private long numeroPaginas;
    private long numeroPagina;
    private final long tamanioTotal;
    private final int tamanioPaginas;

    public Paginador(long tamanioTotal, int tamanioPaginas) {
        this.tamanioTotal = tamanioTotal;
        if (tamanioPaginas > TAMANIO_NO_VALIDO) {
            this.tamanioPaginas = tamanioPaginas;
        } else {
            this.tamanioPaginas = MINIMO_PAGINAS;
        }
        if (tamanioPaginas > tamanioTotal) {
            numeroPaginas = MINIMO_PAGINAS;
        }
        numeroPaginas = tamanioTotal / tamanioPaginas;
        if (tamanioTotal % tamanioPaginas > 0) {
            numeroPaginas++;
        }
        rowFinal = tamanioPaginas+MINIMO_PAGINAS;
        rowInicial = MINIMO_PAGINAS;
        numeroPagina = MINIMO_PAGINAS;
    }

    public boolean next() {
        if (rowFinal <= tamanioTotal) {
            rowInicial += tamanioPaginas;
            rowFinal += tamanioPaginas;
            numeroPagina++;
            return true;
        } else {
            return false;
        }
    }

    public boolean back() {
        if (numeroPagina > MINIMO_PAGINAS) {
            rowInicial -= tamanioPaginas;
            rowFinal -= tamanioPaginas;
            numeroPagina--;
            return true;
        } else {
            return false;
        }
    }

    public boolean goTo(long i) {
        if (i > 0 && i <= numeroPaginas) {
            rowInicial=((tamanioPaginas)*i-tamanioPaginas)+1;
            rowFinal=(tamanioPaginas*i)+1;
            numeroPagina=i;
            return true;
        }
        return false;
    }

    public long getTamanioTotal() {
        return tamanioTotal;
    }

    public int getTamanioPaginas() {
        return tamanioPaginas;
    }

    /**
     * @return the numeroPaginas
     */
    public long getNumeroPaginas() {
        return numeroPaginas;
    }

    /**
     * @return the rowInicial
     */
    public long getRowInicial() {
        return rowInicial;
    }

    /**
     * @return the rowFinal
     */
    public long getRowFinal() {
        return rowFinal;
    }

    public long getNumeroPagina() {
        return numeroPagina;
    }

    @Override
    public String toString() {
        return "Paginador{" + "rowInicial=" + rowInicial + ", rowFinal=" + rowFinal + ", numeroPaginas=" + numeroPaginas + ", numeroPagina=" + numeroPagina + ", tamanioTotal=" + tamanioTotal + ", tamanioPaginas=" + tamanioPaginas + '}';
    }
}
