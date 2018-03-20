/*	****************************************************************
 * Nombre de archivo: FtpConnectionServiceImpl.java
 * Autores: Emmanuel Estrada Gonzalez 		 	 		
 * Bitácora de modificaciones:
 * CR/Defecto Fecha Autor Descripción del cambio
 * ---------------------------------------------------------------------------
 * N/A 25/11/2013 <Nombre del desarrollador que está modificando el archivo>
 * ---------------------------------------------------------------------------
 */
package mx.gob.sat.siat.cob.seguimiento.service.arca.ftp.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import mx.gob.sat.siat.cob.seguimiento.exception.SGTServiceException;
import mx.gob.sat.siat.cob.seguimiento.service.arca.ftp.FtpConnectionService;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service
public class FtpConnectionServiceImpl implements FtpConnectionService {

    /**
     *
     */
    private Logger log = Logger.getLogger(FtpConnectionServiceImpl.class);

    private FTPClient ftpClient;
    private String host = "99.91.8.74";
    private String user = "paso";
    private String password = "siatftp13";
    private String sDir = "/Paso/COB/db/";
    private String remoteFile = "conexionDB.txt";

    /**
     *
     */
    public FtpConnectionServiceImpl() {
        super();
    }

    @Override
    public void connect(String host, String user, String password, String sDir)
            throws IOException, SGTServiceException {

        ftpClient = new FTPClient();

        int reply;
        ftpClient.connect(host);
        reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftpClient.disconnect();
            throw new SGTServiceException("Exception in connecting to FTP Server");
        }
        ftpClient.login(user, password);

        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.changeWorkingDirectory(sDir);
        ftpClient.enterLocalPassiveMode();

    }

    @Override
    public void connect() throws IOException, SGTServiceException {

        ftpClient = new FTPClient();

        int reply;
        ftpClient.connect(host);
        reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftpClient.disconnect();
            throw new SGTServiceException("Exception in connecting to FTP Server");
        }
        ftpClient.login(user, password);

        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.changeWorkingDirectory(sDir);
        ftpClient.enterLocalPassiveMode();

    }

    /**
     * do whatever you want with these files, display them, etc. expensive
     * FTPFile objects not created until needed. "page size" you want
     *
     * @return
     * @throws IOException
     */
    @Override
    public String[] listFiles() throws IOException {
        FTPFile[] ftpFiles;
        ftpFiles = ftpClient.listFiles();
        String[] namesFiles;

        if (ftpFiles != null) {
            namesFiles = new String[ftpFiles.length];
            for (int i = 0; i < ftpFiles.length; i++) {
                log.info("FTPFile: " + ftpFiles[i].getName());
                namesFiles[i] = ftpFiles[i].getName();
            }
            return namesFiles;
        }
        namesFiles = null;
        return namesFiles;
    }

    /**
     * path to remote resource local memory bytes output stream try to read data
     * from remote server
     *
     * @return
     */
    @Override
    public byte[] dowloadBytesFile() throws SGTServiceException {

        String remoteFilePath;
        remoteFilePath = sDir + "/" + remoteFile;
        ByteArrayOutputStream baos;
        baos = new ByteArrayOutputStream();
        try {
            if (ftpClient.retrieveFile(remoteFilePath, baos)) {
                log.info("FileOut: ");
                return baos.toByteArray();
            } else {
                throw new SGTServiceException("Failed to download file completely");
            }
        } catch (IOException e) {
            throw new SGTServiceException("Failed to download file IOException", e);
        }
    }

    /**
     * path to remote resource local memory bytes output stream try to read data
     * from remote server
     *
     * @param nameFile
     * @return
     * @throws IOException
     */
    @Override
    public byte[] dowloadFile(String nameFile) throws IOException, SGTServiceException {

        String remoteFilePath;
        remoteFilePath = sDir + nameFile;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        if (ftpClient.retrieveFile(remoteFilePath, baos)) {
            log.info("FileOut: ");
            return baos.toByteArray();
        } else {
            throw new SGTServiceException("Failed to download file completely");
        }

    }

    @Override
    public void disconnect() throws IOException {
        if (this.ftpClient.isConnected()) {
            this.ftpClient.logout();
            this.ftpClient.disconnect();
        }
    }

}
