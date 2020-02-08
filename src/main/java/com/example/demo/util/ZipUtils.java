package com.example.demo.util;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * ZIP文件压缩
 */
public final class ZipUtils {
    /**
     * 默认编码
     */
    public static final String DEFAULT_ENCODING = "utf-8";

    // LOG
    private static final Logger LOG = LoggerFactory.getLogger(ZipUtils.class);

    private static final int DEFAULT_BYTE_LENGTH = ConstantUtils.getConstantValue(4);

    /**
     * 压缩格式的文件头标识
     */
    private static final byte[] MAGIC_HEADER = new byte[]{(byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE};

    /**
     * constructor
     */
    private ZipUtils() {

    }

    /**
     * 把指定的源文件压缩到一个文件
     *
     * @param srcFiles 源文件,可指定多个, key是文件名
     * @param destFile 目标文件
     * @param encoding 文件名编码
     * @throws IOException Exception
     */
    public static void compressFilesToFile(Map<String, File> srcFiles, File destFile, String encoding) throws IOException {
        Map<String, InputStream> srcStreams = convertFilesToInputStreams(srcFiles);
        compressStreamsToFile(srcStreams, destFile, encoding);
    }

    private static Map<String, InputStream> convertFilesToInputStreams(Map<String, File> srcFiles) throws FileNotFoundException {
        Map<String, InputStream> srcStreams = new HashMap<>();
        for (Map.Entry<String, File> entry : srcFiles.entrySet()) {
            if (entry.getValue().exists()) {
                srcStreams.put(entry.getKey(), new FileInputStream(entry.getValue()));
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("can not read from file : {}({})", entry.getKey(), entry.getValue());
                }
            }
        }
        return srcStreams;
    }

    /**
     * 把指定的源文件压缩到一个文件,文件名编码:UTF-8
     *
     * @param srcFiles 源文件,可指定多个, key是文件名
     * @param destFile 目标文件
     * @throws IOException Exception
     */
    public static void compressFilesToFile(Map<String, File> srcFiles, File destFile) throws IOException {
        compressFilesToFile(srcFiles, destFile, DEFAULT_ENCODING);
    }

    /**
     * 把指定的源文件压缩到一个输出流
     *
     * @param srcFiles   源文件,可指定多个, key是文件名
     * @param destStream 目标输出流
     * @param encoding   文件名编码
     * @throws IOException Exception
     */
    public static void compressFilesToStream(Map<String, File> srcFiles, OutputStream destStream, String encoding) throws IOException {
        Map<String, InputStream> srcStreams = convertFilesToInputStreams(srcFiles);
        compressStreamsToStream(srcStreams, destStream, encoding);
    }

    /**
     * 把指定的源文件压缩到一个输出流, 文件名编码:UTF-8
     *
     * @param srcFiles   源文件,可指定多个, key是文件名
     * @param destStream 目标输出流
     * @throws IOException Exception
     */
    public static void compressFilesToStream(Map<String, File> srcFiles, OutputStream destStream) throws IOException {
        compressFilesToStream(srcFiles, destStream, DEFAULT_ENCODING);
    }

    /**
     * 把指定的源输入流压缩到一个输出流
     *
     * @param srcStreams 源输入流,可指定多个, key是文件名
     * @param destFile   目标文件
     * @param encoding   文件名编码
     * @throws IOException Exception
     */
    public static void compressStreamsToFile(Map<String, InputStream> srcStreams, File destFile, String encoding) throws IOException {
        boolean notExist = destFile.createNewFile();
        if (!notExist && LOG.isDebugEnabled()) {
            LOG.debug("file {} already exists, overwrite!", destFile);
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(destFile);
            compressStreamsToStream(srcStreams, fos, encoding);
        }
        finally {
            if (fos != null) {
                try {
                    fos.close();
                }
                catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 把指定的源输入流压缩到一个输出流, 文件名编码:UTF-8
     *
     * @param srcStreams 源输入流,可指定多个, key是文件名
     * @param destFile   目标文件
     * @throws IOException Exception
     */
    public static void compressStreamsToFile(Map<String, InputStream> srcStreams, File destFile) throws IOException {
        compressStreamsToFile(srcStreams, destFile, DEFAULT_ENCODING);
    }

    /**
     * 把指定的源输入流压缩到一个输出流
     *
     * @param srcStreams 源输入流,可指定多个, key是文件名
     * @param destStream 目标输出流
     * @param encoding   文件名编码
     * @throws IOException Exception
     */
    public static void compressStreamsToStream(Map<String, InputStream> srcStreams, OutputStream destStream, String encoding) throws IOException {
        ZipArchiveOutputStream zaos = null;
        try {
            zaos = new ZipArchiveOutputStream(destStream);
            zaos.setEncoding(encoding);

            for (Map.Entry<String, InputStream> entry : srcStreams.entrySet()) {
                InputStream is = entry.getValue();
                String filename = entry.getKey();
                ZipArchiveEntry zae = new ZipArchiveEntry(filename);
                zaos.putArchiveEntry(zae);
                IOUtils.copy(is, zaos);
                is.close();
                zaos.closeArchiveEntry();
            }
        }
        finally {
            if (zaos != null) {
                try {
                    zaos.close();
                }
                catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }

    }
    /**
     * 把指定的源输入流压缩到一个输出流
     *
     * @param srcStreams 源输入流,可指定多个, key是文件名
     * @param destStream 目标输出流
     * @param encoding   文件名编码
     * @param fileSizeMap   文件长度Map
     * @throws IOException Exception
     */
    public static void compressStreamsToStream(Map<String, InputStream> srcStreams, OutputStream destStream, String encoding,Map<String, Long> fileSizeMap) throws IOException {
        ZipArchiveOutputStream zaos = null;
        try {
            zaos = new ZipArchiveOutputStream(destStream);
            zaos.setEncoding(encoding);
            for (Map.Entry<String, InputStream> entry : srcStreams.entrySet()) {
                InputStream is = entry.getValue();
                String filename = entry.getKey();
                ZipArchiveEntry zae = new ZipArchiveEntry(filename);
                zaos.putArchiveEntry(zae);
                if(fileSizeMap.get(filename)!=null) {
                    parserInputStream(is, zaos, fileSizeMap.get(filename));
                }else{
                    IOUtils.copy(is, zaos);
                }
                is.close();
                zaos.closeArchiveEntry();
            }
        } finally {
            if (zaos != null) {
                try {
                    zaos.close();
                } catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }

    }
    /**
     * 文件上传
     *
     * @param fileData
     *            文件数据
     * @param bos
     *            输出的流
     * @param fileSize
     *            文件长度
     * @throws IOException
     */
    public static void parserInputStream(InputStream fileData, ZipArchiveOutputStream bos,Long fileSize) throws IOException {
        try {
            if (fileSize > DEFAULT_BYTE_LENGTH) {
                byte[] head = new byte[DEFAULT_BYTE_LENGTH];
                // 如果是压缩格式
                if (fileData.read(head) == DEFAULT_BYTE_LENGTH && Arrays.equals(head, MAGIC_HEADER)) {
                    // 复制原始图片
                    if (fileData.read(head) == DEFAULT_BYTE_LENGTH) {
                        int length = ByteBuffer.wrap(head).getInt();
                        IOUtils.copy(fileData, bos, length);
                    }
                } else {
                    // 如果不是压缩格式,直接复制
                    IOUtils.copy(head, bos);
                    IOUtils.copy(fileData, bos);
                }
            } else {
                // 小于4个字节, 直接复制
                IOUtils.copy(fileData, bos);
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
    /**
     * 把指定的源输入流压缩到一个输出流, 文件名编码:UTF-8
     *
     * @param srcStreams 源输入流,可指定多个, key是文件名
     * @param destStream 目标输出流
     * @throws IOException Exception
     */
    public static void compressStreamsToStream(Map<String, InputStream> srcStreams, OutputStream destStream) throws IOException {
        compressStreamsToStream(srcStreams, destStream, DEFAULT_ENCODING);
    }
}
