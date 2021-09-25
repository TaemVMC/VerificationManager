package com.verifymycoin.VerificationManager.common.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IOUtil {

    /**
     * date format
     */
    public static String getDateFormat() {
        SimpleDateFormat formatter = new SimpleDateFormat("'VMC_'yy-MM-dd'T'HHmmss");
        Date nowdate = new Date();
        return formatter.format(nowdate);
    }

    /**
     * Dir Check
     */
    public static void createDir(String outputDir) {
        File folder = new File(outputDir);
        if(!folder.exists()) {
            folder.mkdir();
        }
    }

    /**
     * write file
     */
    public static void writeFile(InputStream is, FileOutputStream os) {
        try {
            final int BUFFER_SIZE = 4096;
            int bytesRead;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * I/O Close
     */
    public static void close(InputStream is, FileOutputStream os) {
        try {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
