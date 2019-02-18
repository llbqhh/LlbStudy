package org.llbqhh.test.io;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class BzWriteTest {
    public static void main(String[] args) throws Exception {
        compress("C:/Users/Administrator/Desktop/PCRHomeLog20160106.log", false);
    }

    public static final int BUFFER = 1024;
    public static final CharSequence EXT = ".bz2";

    /**
     * ����ѹ��
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] compress(byte[] data) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        compress(bais, baos);

        byte[] output = baos.toByteArray();

        baos.flush();
        baos.close();

        bais.close();

        return output;
    }

    /**
     * �ļ�ѹ��
     *
     * @param file
     * @throws Exception
     */
    public static void compress(File file) throws Exception {
        compress(file, true);
    }

    /**
     * �ļ�ѹ��
     *
     * @param file
     * @param delete �Ƿ�ɾ��ԭʼ�ļ�
     * @throws Exception
     */
    public static void compress(File file, boolean delete) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        FileOutputStream fos = new FileOutputStream(file.getPath() + EXT);

        compress(fis, fos);

        fis.close();
        fos.flush();
        fos.close();

        if (delete) {
            file.delete();
        }
    }

    /**
     * ����ѹ��
     *
     * @param is
     * @param os
     * @throws Exception
     */
    public static void compress(InputStream is, OutputStream os)
            throws Exception {
        BZip2CompressorOutputStream gos = new BZip2CompressorOutputStream(os);

        int count;
        byte[] data = new byte[BUFFER];
        while ((count = is.read(data, 0, BUFFER)) != -1) {
            gos.write(data, 0, count);
        }

        gos.finish();

        gos.flush();
        gos.close();
    }

    /**
     * �ļ�ѹ��
     *
     * @param path
     * @throws Exception
     */
    public static void compress(String path) throws Exception {
        compress(path, true);
    }

    /**
     * �ļ�ѹ��
     *
     * @param path
     * @param delete �Ƿ�ɾ��ԭʼ�ļ�
     * @throws Exception
     */
    public static void compress(String path, boolean delete) throws Exception {
        File file = new File(path);
        compress(file, delete);
    }

    /**
     * ���ݽ�ѹ��
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] decompress(byte[] data) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        decompress(bais, baos);

        data = baos.toByteArray();

        baos.flush();
        baos.close();

        bais.close();

        return data;
    }

    /**
     * �ļ���ѹ��
     *
     * @param file
     * @throws Exception
     */
    public static void decompress(File file) throws Exception {
        decompress(file, true);
    }

    /**
     * �ļ���ѹ��
     *
     * @param file
     * @param delete �Ƿ�ɾ��ԭʼ�ļ�
     * @throws Exception
     */
    public static void decompress(File file, boolean delete) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        FileOutputStream fos = new FileOutputStream(file.getPath().replace(EXT,
                ""));
        decompress(fis, fos);
        fis.close();
        fos.flush();
        fos.close();

        if (delete) {
            file.delete();
        }
    }

    /**
     * ���ݽ�ѹ��
     *
     * @param is
     * @param os
     * @throws Exception
     */
    public static void decompress(InputStream is, OutputStream os)
            throws Exception {
        BZip2CompressorInputStream gis = new BZip2CompressorInputStream(is);

        int count;
        byte[] data = new byte[BUFFER];
        while ((count = gis.read(data, 0, BUFFER)) != -1) {
            os.write(data, 0, count);
        }
        gis.close();
    }

    /**
     * �ļ���ѹ��
     *
     * @param path
     * @throws Exception
     */
    public static void decompress(String path) throws Exception {
        decompress(path, true);
    }

    /**
     * �ļ���ѹ��
     *
     * @param path
     * @param delete �Ƿ�ɾ��ԭʼ�ļ�
     * @throws Exception
     */
    public static void decompress(String path, boolean delete) throws Exception {
        File file = new File(path);
        decompress(file, delete);
    }
}
