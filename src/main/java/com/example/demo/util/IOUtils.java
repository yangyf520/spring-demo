package com.example.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 输入输出相关的工具方法类
 */
public final class IOUtils {

    /**
     * 默认的缓冲字节数
     */
    public static final int DEFAULT_BUF_SIZE = 1024;

    /**
     * LOG
     */
    private static final Logger LOG = LoggerFactory.getLogger(IOUtils.class);

    /**
     * constructor
     */
    private IOUtils() {

    }

    /**
     * 把InputStream复制到指定文件
     *
     * @param input  输入InputStream
     * @param output 输出文件
     * @return 传输量
     * @throws IOException copy时可能抛出IO异常
     */
    public static long copy(InputStream input, File output) throws IOException {
        return copy(input, output, Long.MAX_VALUE);
    }

    /**
     * 复制输入流到文件, 只复制指定长度
     *
     * @param input  输入流
     * @param output 文件
     * @param length 复制长度
     * @return 实际复制的长度
     * @throws IOException 可能抛出IO异常
     */
    public static long copy(InputStream input, File output, long length) throws IOException {
        FileOutputStream fos = null;
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("coping file  to {}", output.getPath());
            }

            // 输出文件不存在,先创建
            if (!output.exists()) {
                boolean created = output.createNewFile();
                if (!created && LOG.isDebugEnabled()) {
                    LOG.debug("file already exists : {}", output.getPath());
                }
            }

            // 把数据从InputStream读出来复制到输出
            fos = new FileOutputStream(output);
            return copy(input, fos, length);
        }
        finally {
            if (fos != null) {
                try {
                    fos.close();
                }
                catch (IOException e) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug(e.getMessage(), e);
                    }
                }
            }
        }
    }

    /**
     * 复制输入流到输出流, 只复制指定长度
     *
     * @param input  输入流
     * @param output 输出流
     * @param length 复制长度
     * @return 实际复制的长度
     * @throws IOException 可能抛出IO异常
     */
    public static long copy(InputStream input, OutputStream output, long length) throws IOException {
        return copy(input, output, new byte[DEFAULT_BUF_SIZE], length);
    }

    /**
     * 把InputStream复制到指定文件
     *
     * @param input  输入InputStream
     * @param output 输出文件
     * @param buf    Buffer
     * @return 传输量
     * @throws IOException 可能抛出IO异常
     */
    public static long copy(InputStream input, File output, byte[] buf) throws IOException {
        FileOutputStream fos = null;
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("coping file  to {}", output.getPath());
            }

            // 输出文件不存在,先创建
            if (!output.exists()) {
                boolean created = output.createNewFile();
                if (!created && LOG.isDebugEnabled()) {
                    LOG.debug("file already exists : {}", output.getPath());
                }
            }

            // 把数据从InputStream读出来复制到输出
            fos = new FileOutputStream(output);
            return copy(input, fos, buf);
        }
        finally {
            if (fos != null) {
                try {
                    fos.close();
                }
                catch (IOException e) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug(e.getMessage(), e);
                    }
                }
            }
        }
    }

    /**
     * 把InputStream复制到指定OutpuStream
     *
     * @param input  输入InputStream
     * @param output 输出OutpuStream
     * @param buf    Buffer
     * @return 传输量
     * @throws IOException 可能抛出IO异常
     */
    public static long copy(InputStream input, OutputStream output, byte[] buf) throws IOException {
        return copy(input, output, buf, Long.MAX_VALUE);
    }

    /**
     * 把InputStream复制到指定OutpuStream
     *
     * @param input     输入InputStream
     * @param output    输出OutpuStream
     * @param buf       Buffer
     * @param maxLength 最大长度
     * @return 传输量
     * @throws IOException Exception 可能抛出IO异常
     */
    public static long copy(InputStream input, OutputStream output, byte[] buf, long maxLength) throws IOException {
        buf = (buf == null ? new byte[DEFAULT_BUF_SIZE] : buf);
        long totalTransferred = 0;
        int len;
        int bufLen = buf.length;
        while ((len = input.read(buf, 0, bufLen)) != -1) {
            // 如果要复制的总长度大于了总长度上限, 则复制满足总长度的部分
            if (totalTransferred + len > maxLength) {
                len = (int) (maxLength - totalTransferred);
            }
            output.write(buf, 0, len);
            output.flush();
            totalTransferred += len;
            if (maxLength - totalTransferred < bufLen) {
                bufLen = (int) (maxLength - totalTransferred);
            }
            if (totalTransferred >= maxLength) {
                break;
            }
        }
        output.flush();
        return totalTransferred;
    }

    /**
     * 把InputStream复制到指定OutpuStream
     *
     * @param input  输入InputStream
     * @param output 输出OutpuStream
     * @return 传输量
     * @throws IOException 可能抛出IO异常
     */
    public static long copy(InputStream input, OutputStream output) throws IOException {
        return copy(input, output, new byte[DEFAULT_BUF_SIZE], Long.MAX_VALUE);
    }

    /**
     * 把文件复制到输出流
     *
     * @param file         文件
     * @param outputStream 输出流
     * @return 传输量
     * @throws IOException 可能抛出IO异常
     */
    public static long copy(File file, OutputStream outputStream) throws IOException {
        return copy(file, outputStream, new byte[DEFAULT_BUF_SIZE]);
    }

    /**
     * 文件复制到输出流
     *
     * @param file         文件
     * @param outputStream 输出流
     * @param buf          buffer
     * @return long
     * @throws IOException Exception
     */
    public static long copy(File file, OutputStream outputStream, byte[] buf) throws IOException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            return copy(fis, outputStream, buf);
        }
        finally {
            if (fis != null) {
                try {
                    fis.close();
                }
                catch (IOException e) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug(e.getMessage(), e);
                    }
                }
            }
        }
    }

    /**
     * 把字节数组的数据写到一个文件
     *
     * @param data 数据
     * @param file 文件
     * @return long
     * @throws IOException Exception
     */
    public static long copy(byte[] data, File file) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        return copy(bais, file);
    }

    /**
     * 把字节数组的数据写到一个输出流
     *
     * @param data 数据
     * @param os   输出流
     * @return long
     * @throws IOException Exception
     */
    public static long copy(byte[] data, OutputStream os) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        return copy(bais, os);
    }

    /**
     * 把字符输入流复制到字符输出流
     *
     * @param r         字符输入流
     * @param w         字符输出流
     * @param buf       缓冲区
     * @param maxLength 最大复制多少字符
     * @return long
     * @throws IOException Exception
     */
    public static long copy(Reader r, Writer w, char[] buf, long maxLength) throws IOException {
        buf = (buf == null ? new char[DEFAULT_BUF_SIZE] : buf);
        long totalTransferred = 0;
        int len;
        int bufLen = buf.length;
        while ((len = r.read(buf, 0, bufLen)) != -1) {
            // 如果要复制的总长度大于了总长度上限, 则复制满足总长度的部分
            if (totalTransferred + len > maxLength) {
                len = (int) (maxLength - totalTransferred);
            }
            w.write(buf, 0, len);
            w.flush();
            totalTransferred += len;
            if (maxLength - totalTransferred < bufLen) {
                bufLen = (int) (maxLength - totalTransferred);
            }
            if (totalTransferred >= maxLength) {
                break;
            }
        }
        w.flush();
        return totalTransferred;
    }

    /**
     * 把字符输入流复制到字符输出流
     *
     * @param in       输入流
     * @param encoding 输入流的字符集
     * @param w        字符输出流
     * @return long
     * @throws IOException Exception
     */
    public static long copy(InputStream in, Writer w, String encoding) throws IOException {
        return copy(new InputStreamReader(in, encoding), w, new char[DEFAULT_BUF_SIZE], Long.MAX_VALUE);
    }

    /**
     * 把字符输入流复制到字符输出流
     *
     * @param r 字符输入流
     * @param w 字符输出流
     * @return long
     * @throws IOException Exception
     */
    public static long copy(Reader r, Writer w) throws IOException {
        return copy(r, w, new char[DEFAULT_BUF_SIZE], Long.MAX_VALUE);
    }

    /**
     * 读取一个文件到字符串
     *
     * @param stream   输入流
     * @param encoding 字符集
     * @return 字符(文件的内容)
     * @throws IOException 可能抛出IO异常
     */
    public static String readToString(InputStream stream, String encoding) throws IOException {
        StringWriter sw = new StringWriter();
        copy(stream, sw, encoding);
        return sw.toString();
    }
}
