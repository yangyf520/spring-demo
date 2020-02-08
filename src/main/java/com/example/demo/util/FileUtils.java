package com.example.demo.util;

//import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件相关工具类
 */
public final class FileUtils {
    private static final Logger LOG = LoggerFactory.getLogger(FileUtils.class);

    private FileUtils() {
    }

    /**
     * 获取指定文件的扩展名
     *
     * @param fileName 文件全名
     * @return 文件的扩展名
     */
    public static String getExtName(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return null;
        }

        int pos = fileName.lastIndexOf('.');
        if (pos > 0 && pos != fileName.length() - 1) {
            return fileName.substring(pos + 1).toLowerCase();
        }
        return null;
    }

    /**
     * 获取指定文件的名称,不包含扩展名
     *
     * @param fileName 文件全名
     * @return 不包含扩展名的文件名
     */
    public static String getFileNameExceptExt(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return "";
        }
        int pos = fileName.lastIndexOf('.');
        if (pos >= 0) {
            return fileName.substring(0, pos);
        } else {
            return fileName;
        }
    }

    /**
     * 删除文件
     *
     * @param atachmentUrl 文件的路径
     * @return 是否删除成功
     */
    public static boolean deleteFolder(String atachmentUrl) {
        boolean flag = false;
        File file = new File(atachmentUrl);
        // 判断目录或文件是否存在
        if (!file.exists()) {
            // 不存在返回false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) {
                // 为文件时调用删除文件方法
                return deleteFile(atachmentUrl);
            } else {
                // 为目录时调用删除目录方法
                return deleteDirectory(atachmentUrl);
            }
        }
    }

    private static boolean deleteDirectory(String sPath) {
        // 如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        // 删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        if (files != null) {
            for (File file : files) {
                // 删除子文件
                if (file.isFile()) {
                    flag = deleteFile(file.getAbsolutePath());
                } else {
                    // 删除子目录
                    flag = deleteDirectory(file.getAbsolutePath());
                }
                if (!flag) {
                    return false;
                }
            }
        }
        // 删除当前目录
        return dirFile.delete();
    }

    private static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            flag = file.delete();
        }
        return flag;
    }

    /**
     * 复制文件
     *
     * @param srcFile  源文件路径
     * @param destFile 目标文件路径
     * @throws IOException 复制文件时可能抛出IO异常
     */
    public static void copy(String srcFile, String destFile) throws IOException {
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);
            IOUtils.copy(fis, fos);
        }
        finally {
            if (fis != null) {
                try {
                    fis.close();
                }
                catch (IOException e) {
                    LOG.debug(e.getMessage(), e);
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                }
                catch (IOException e) {
                    LOG.debug(e.getMessage(), e);
                }
            }

        }

    }
    /**
     * 给定json数据（用list包装）、文件名、文件路径，生成json压缩文件
     *
     * @param data     json数据
     * @param filePath 文件路径
     * @param fileName 文件名
     */
    public static void createJSONFile(List data, String filePath, String fileName) {
        InputStream in = null;
        OutputStream os = null;
        if (filePath.contains("/null")) {
            LOG.error(String.format("生成JSON压缩文件失败:文件路径-%s，文件名称-%s!", filePath, fileName));
        }
        try {
            File fileDir = new File(filePath);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            os = new FileOutputStream(String.format("%s%s%s.json", filePath, File.separator, fileName));
            //TODO in = new ByteArrayInputStream(FastJsonUtils.convertObject2JSONString(data).getBytes("UTF-8"));
            IOUtils.copy(in, os);
            LOG.info(String.format("生成JSON压缩文件成功:文件路径-%s，文件名称-%s!", filePath, fileName));
        }
        catch (Exception e) {
            LOG.error(String.format("生成JSON压缩文件失败:文件路径-%s，文件名称-%s!", filePath, fileName), e);
        }
        finally {
            try {
                in.close();
                os.close();
            } catch (IOException e) {
                LOG.error(String.format("生成JSON压缩文件失败:文件路径-%s，文件名称-%s!", filePath, fileName), e);
            }
        }
    }

    /**
     * 根据文件内容生成文件
     *
     * @param content  文件内容
     * @param filePath 文件路径
     * @param fileName 文件名
     * @param suffix   文件后缀名
     */
    public static void createFile(String content, String filePath, String fileName, String suffix) {
        InputStream in = null;
        OutputStream os = null;
        try {
            File fileDir = new File(filePath);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            os = new FileOutputStream(String.format("%s%s%s." + suffix, filePath, File.separator, fileName));
            in = new ByteArrayInputStream(content.getBytes("UTF-8"));
            IOUtils.copy(in, os);
        }
        catch (Exception e) {
            LOG.error(String.format("生成" + suffix + "文件失败:文件路径-%s，文件名称-%s!", filePath, fileName), e);
        }
        finally {
            try {
                in.close();
                os.close();
            } catch (IOException e) {
                LOG.error(String.format("生成JSON压缩文件失败:文件路径-%s，文件名称-%s!", filePath, fileName), e);
            }
        }
    }

    /**
     * 给定文件名、文件路径，生成zip压缩文件
     *
     * @param filePath       文件路径
     * @param fileName       文件名
     * @param targetFilePath 目标路径
     */
    public static void createZipFileByPath(String filePath, String fileName, String targetFilePath) {
        OutputStream os = null;
        ZipOutputStream out = null;
        try {
            File resourceFile = new File(filePath);
            File targetFile = new File(targetFilePath);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            os = new FileOutputStream(String.format("%s%s%s.zip", targetFilePath, File.separator, fileName));
            out = new ZipOutputStream(os);
            createZipFile(out, resourceFile, "");
        }
        catch (Exception e) {
            LOG.error(String.format("生成zip压缩文件失败:文件路径-%s，文件名称-%s!", filePath, fileName), e);
        }
        finally {
            try {
                os.close();
                out.close();
            } catch (IOException e) {
                LOG.error(String.format("生成JSON压缩文件失败:文件路径-%s，文件名称-%s!", filePath, fileName), e);
            }
        }
    }

    /**
     * 根据文件生成压缩文件
     *
     * @param out  文件内容
     * @param file 文件路径
     * @param path 文件名
     */
    private static void createZipFile(ZipOutputStream out, File file, String path) {
        FileInputStream fis = null;
        try {
            // 文件输入流
            fis = new FileInputStream(file);
            // 如果当前的是文件夹，则进行进一步处理
            if (file.isDirectory()) {
                // 得到文件列表信息
                File[] files = file.listFiles();
                // 将文件夹添加到下一级打包目录
                out.putNextEntry(new ZipEntry(path + "/"));
                path = path.length() == 0 ? "" : path + "/";
                // 循环将文件夹中的文件打包ß
                if (files != null) {
                    for (File value : files) {
                        createZipFile(out, value, path + value.getName()); // 递归处理
                    }
                }
            } else {
                out.putNextEntry(new ZipEntry(path));
                // 进行写操作
                int j = 0;
                byte[] buffer = new byte[Constant.BYTE_1KB];
                while ((j = fis.read(buffer)) > 0) {
                    out.write(buffer, 0, j);
                }
                // 关闭输入流
                fis.close();
            }
        }
        catch (Exception e) {
            LOG.error(String.format("生成zip压缩文件失败:文件路径-%s，文件名称-%s!", path, file.getName()), e);
        }
        finally {
            try {
                fis.close();
            } catch (IOException e) {
                LOG.error(String.format("生成JSON压缩文件失败:文件路径-%s，文件名称-%s!", path, file.getName()), e);
            }
        }
    }

    /**
     * 给定json数据（用list包装）、文件名、文件路径，生成json压缩文件
     * @param data json数据
     * @param filePath 文件路径
     * @param fileName 文件名
     */
    public static void createGZipFile(List data, String filePath, String fileName) {
        InputStream in = null;
        OutputStream os = null;
        GZIPOutputStream gout = null;
        try {
            File fileDir = new File(filePath);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            os = new FileOutputStream(String.format("%s%s%s.json", filePath, File.separator, fileName));
            gout = new GZIPOutputStream(os);
            //TODO in = new ByteArrayInputStream(FastJsonUtils.convertObject2JSONString(data).getBytes("UTF-8"));
//            in = new ByteArrayInputStream(FastJsonUtils.convert2FormatJSONString(data,
//                    SerializerFeature.WriteMapNullValue,
//                    SerializerFeature.WriteDateUseDateFormat,
//                    SerializerFeature.WriteEnumUsingToString,
//                    SerializerFeature.SkipTransientField
//            ).getBytes("UTF-8"));
            IOUtils.copy(in, gout);
        } catch (Exception e) {
            LOG.error(String.format("生成JSON压缩文件失败:文件路径-%s，文件名称-%s!", filePath, fileName), e);
        } finally {
            try {
                in.close();
                gout.close();
                os.close();
            } catch (IOException e) {
                LOG.error(String.format("生成JSON压缩文件失败:文件路径-%s，文件名称-%s!", filePath, fileName), e);
            }
        }
    }
}
