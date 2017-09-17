package Compression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by damir on 17/09/17. Taken from code geeks.
 */
public class ZipIt {


        public int getValueOf(String file1, String file2) {

            int ret = 0;

            String[] srcFiles = { file1, file2};

            try {

                // create byte buffer
                byte[] buffer = new byte[1024];


                ZipOutputStream zos = new ZipOutputStream(new FileOutputStream("./tmp"));

                for (int i=0; i < srcFiles.length; i++) {

                    File srcFile = new File(srcFiles[i]);

                    FileInputStream fis = new FileInputStream(srcFile);

                    // begin writing a new ZIP entry, positions the stream to the start of the entry data
                    zos.putNextEntry(new ZipEntry(srcFile.getName()));

                    int length;

                    while ((length = fis.read(buffer)) > 0) {
                        ret = ret+length;
                    }

                    zos.closeEntry();

                    // close the InputStream
                    fis.close();

                }

                // close the ZipOutputStream
                zos.close();

            }
            catch (Exception ioe) {
                System.out.println("Error creating zip file: " + ioe);
            }
            return ret;

        }



}
