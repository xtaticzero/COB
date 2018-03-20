package mx.gob.sat.siat.cob.seguimiento.service.arca.ftp;

import java.io.IOException;

import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;

/**
 *
 * @author root
 */
public interface FtpConnectionService {

    /**
     * @throws IOException
     * @throws SGTServiceException
     */
    void connect() throws IOException, SGTServiceException;

    /**
     *
     * @param host
     * @param user
     * @param password
     * @param sDir
     * @throws IOException
     * @throws SGTServiceException
     */
    void connect(String host, String user, String password, String sDir) throws IOException, SGTServiceException;

    /**
     *
     * @return @throws SGTServiceException
     */
    byte[] dowloadBytesFile() throws SGTServiceException;

    /**
     *
     * @param nameFile
     * @return
     * @throws IOException
     * @throws SGTServiceException
     */
    byte[] dowloadFile(String nameFile) throws IOException, SGTServiceException;

    /**
     *
     * @return @throws IOException
     */
    String[] listFiles() throws IOException;

    /**
     *
     * @throws IOException
     */
    void disconnect() throws IOException;
}
