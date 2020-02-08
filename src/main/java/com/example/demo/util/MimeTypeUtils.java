package com.example.demo.util;

import java.util.HashMap;
import java.util.Map;

/**
 * MimeType
 */
public final class MimeTypeUtils {
    private static final Map<String, String> MIME_TYPE_MAP = new HashMap<String, String>();

    // 扩展名 -> MIMETYPE的映射
    static {
        MIME_TYPE_MAP.put("js", "text/javascript");
        MIME_TYPE_MAP.put("png", "image/png");
        MIME_TYPE_MAP.put("jpeg", "image/jpeg");
        MIME_TYPE_MAP.put("jpg", "image/jpeg");
        MIME_TYPE_MAP.put("css", "text/css");
        MIME_TYPE_MAP.put("evy", "application/envoy");
        MIME_TYPE_MAP.put("fif", "application/fractals");
        MIME_TYPE_MAP.put("spl", "application/futuresplash");
        MIME_TYPE_MAP.put("hta", "application/hta");
        MIME_TYPE_MAP.put("acx", "application/internet-property-stream");
        MIME_TYPE_MAP.put("hqx", "application/mac-binhex40");
        MIME_TYPE_MAP.put("doc", "application/msword");
        MIME_TYPE_MAP.put("dot", "application/msword");
        MIME_TYPE_MAP.put("bin", "application/octet-stream");
        MIME_TYPE_MAP.put("class", "application/octet-stream");
        MIME_TYPE_MAP.put("dms", "application/octet-stream");
        MIME_TYPE_MAP.put("exe", "application/octet-stream");
        MIME_TYPE_MAP.put("lha", "application/octet-stream");
        MIME_TYPE_MAP.put("lzh", "application/octet-stream");
        MIME_TYPE_MAP.put("oda", "application/oda");
        MIME_TYPE_MAP.put("axs", "application/olescript");
        MIME_TYPE_MAP.put("pdf", "application/pdf");
        MIME_TYPE_MAP.put("prf", "application/pics-rules");
        MIME_TYPE_MAP.put("p10", "application/pkcs10");
        MIME_TYPE_MAP.put("crl", "application/pkix-crl");
        MIME_TYPE_MAP.put("ai", "application/postscript");
        MIME_TYPE_MAP.put("eps", "application/postscript");
        MIME_TYPE_MAP.put("ps", "application/postscript");
        MIME_TYPE_MAP.put("rtf", "application/rtf");
        MIME_TYPE_MAP.put("setpay", "application/set-payment-initiation");
        MIME_TYPE_MAP.put("setreg", "application/set-registration-initiation");
        MIME_TYPE_MAP.put("xla", "application/vnd.ms-excel");
        MIME_TYPE_MAP.put("xlc", "application/vnd.ms-excel");
        MIME_TYPE_MAP.put("xlm", "application/vnd.ms-excel");
        MIME_TYPE_MAP.put("xls", "application/vnd.ms-excel");
        MIME_TYPE_MAP.put("xlt", "application/vnd.ms-excel");
        MIME_TYPE_MAP.put("xlw", "application/vnd.ms-excel");
        MIME_TYPE_MAP.put("msg", "application/vnd.ms-outlook");
        MIME_TYPE_MAP.put("sst", "application/vnd.ms-pkicertstore");
        MIME_TYPE_MAP.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        MIME_TYPE_MAP.put("cat", "application/vnd.ms-pkiseccat");
        MIME_TYPE_MAP.put("stl", "application/vnd.ms-pkistl");
        MIME_TYPE_MAP.put("pot", "application/vnd.ms-powerpoint");
        MIME_TYPE_MAP.put("pps", "application/vnd.ms-powerpoint");
        MIME_TYPE_MAP.put("ppt", "application/vnd.ms-powerpoint");
        MIME_TYPE_MAP.put("mpp", "application/vnd.ms-project");
        MIME_TYPE_MAP.put("wcm", "application/vnd.ms-works");
        MIME_TYPE_MAP.put("wdb", "application/vnd.ms-works");
        MIME_TYPE_MAP.put("wks", "application/vnd.ms-works");
        MIME_TYPE_MAP.put("wps", "application/vnd.ms-works");
        MIME_TYPE_MAP.put("hlp", "application/winhlp");
        MIME_TYPE_MAP.put("bcpio", "application/x-bcpio");
        MIME_TYPE_MAP.put("cdf", "application/x-cdf");
        MIME_TYPE_MAP.put("z", "application/x-compress");
        MIME_TYPE_MAP.put("tgz", "application/x-compressed");
        MIME_TYPE_MAP.put("cpio", "application/x-cpio");
        MIME_TYPE_MAP.put("csh", "application/x-csh");
        MIME_TYPE_MAP.put("dcr", "application/x-director");
        MIME_TYPE_MAP.put("dir", "application/x-director");
        MIME_TYPE_MAP.put("dxr", "application/x-director");
        MIME_TYPE_MAP.put("dvi", "application/x-dvi");
        MIME_TYPE_MAP.put("gtar", "application/x-gtar");
        MIME_TYPE_MAP.put("gz", "application/x-gzip");
        MIME_TYPE_MAP.put("hdf", "application/x-hdf");
        MIME_TYPE_MAP.put("ins", "application/x-internet-signup");
        MIME_TYPE_MAP.put("isp", "application/x-internet-signup");
        MIME_TYPE_MAP.put("iii", "application/x-iphone");
        MIME_TYPE_MAP.put("latex", "application/x-latex");
        MIME_TYPE_MAP.put("mdb", "application/x-msaccess");
        MIME_TYPE_MAP.put("crd", "application/x-mscardfile");
        MIME_TYPE_MAP.put("clp", "application/x-msclip");
        MIME_TYPE_MAP.put("dll", "application/x-msdownload");
        MIME_TYPE_MAP.put("m13", "application/x-msmediaview");
        MIME_TYPE_MAP.put("m14", "application/x-msmediaview");
        MIME_TYPE_MAP.put("mvb", "application/x-msmediaview");
        MIME_TYPE_MAP.put("wmf", "application/x-msmetafile");
        MIME_TYPE_MAP.put("mny", "application/x-msmoney");
        MIME_TYPE_MAP.put("pub", "application/x-mspublisher");
        MIME_TYPE_MAP.put("scd", "application/x-msschedule");
        MIME_TYPE_MAP.put("trm", "application/x-msterminal");
        MIME_TYPE_MAP.put("wri", "application/x-mswrite");
        MIME_TYPE_MAP.put("cdf", "application/x-netcdf");
        MIME_TYPE_MAP.put("nc", "application/x-netcdf");
        MIME_TYPE_MAP.put("pma", "application/x-perfmon");
        MIME_TYPE_MAP.put("pmc", "application/x-perfmon");
        MIME_TYPE_MAP.put("pml", "application/x-perfmon");
        MIME_TYPE_MAP.put("pmr", "application/x-perfmon");
        MIME_TYPE_MAP.put("pmw", "application/x-perfmon");
        MIME_TYPE_MAP.put("p12", "application/x-pkcs12");
        MIME_TYPE_MAP.put("pfx", "application/x-pkcs12");
        MIME_TYPE_MAP.put("p7b", "application/x-pkcs7-certificates");
        MIME_TYPE_MAP.put("spc", "application/x-pkcs7-certificates");
        MIME_TYPE_MAP.put("p7r", "application/x-pkcs7-certreqresp");
        MIME_TYPE_MAP.put("p7c", "application/x-pkcs7-mime");
        MIME_TYPE_MAP.put("p7m", "application/x-pkcs7-mime");
        MIME_TYPE_MAP.put("p7s", "application/x-pkcs7-signature");
        MIME_TYPE_MAP.put("sh", "application/x-sh");
        MIME_TYPE_MAP.put("shar", "application/x-shar");
        MIME_TYPE_MAP.put("swf", "application/x-shockwave-flash");
        MIME_TYPE_MAP.put("sit", "application/x-stuffit");
        MIME_TYPE_MAP.put("sv4cpio", "application/x-sv4cpio");
        MIME_TYPE_MAP.put("sv4crc", "application/x-sv4crc");
        MIME_TYPE_MAP.put("tar", "application/x-tar");
        MIME_TYPE_MAP.put("tcl", "application/x-tcl");
        MIME_TYPE_MAP.put("tex", "application/x-tex");
        MIME_TYPE_MAP.put("texi", "application/x-texinfo");
        MIME_TYPE_MAP.put("texinfo", "application/x-texinfo");
        MIME_TYPE_MAP.put("roff", "application/x-troff");
        MIME_TYPE_MAP.put("t", "application/x-troff");
        MIME_TYPE_MAP.put("tr", "application/x-troff");
        MIME_TYPE_MAP.put("man", "application/x-troff-man");
        MIME_TYPE_MAP.put("me", "application/x-troff-me");
        MIME_TYPE_MAP.put("ms", "application/x-troff-ms");
        MIME_TYPE_MAP.put("ustar", "application/x-ustar");
        MIME_TYPE_MAP.put("src", "application/x-wais-source");
        MIME_TYPE_MAP.put("cer", "application/x-x509-ca-cert");
        MIME_TYPE_MAP.put("crt", "application/x-x509-ca-cert");
        MIME_TYPE_MAP.put("der", "application/x-x509-ca-cert");
        MIME_TYPE_MAP.put("pko", "application/ynd.ms-pkipko");
        MIME_TYPE_MAP.put("zip", "application/zip");
        MIME_TYPE_MAP.put("au", "audio/basic");
        MIME_TYPE_MAP.put("snd", "audio/basic");
        MIME_TYPE_MAP.put("mid", "audio/mid");
        MIME_TYPE_MAP.put("rmi", "audio/mid");
        MIME_TYPE_MAP.put("mp3", "audio/mpeg");
        MIME_TYPE_MAP.put("aif", "audio/x-aiff");
        MIME_TYPE_MAP.put("aifc", "audio/x-aiff");
        MIME_TYPE_MAP.put("aiff", "audio/x-aiff");
        MIME_TYPE_MAP.put("m3u", "audio/x-mpegurl");
        MIME_TYPE_MAP.put("ra", "audio/x-pn-realaudio");
        MIME_TYPE_MAP.put("ram", "audio/x-pn-realaudio");
        MIME_TYPE_MAP.put("wav", "audio/x-wav");
        MIME_TYPE_MAP.put("bmp", "image/bmp");
        MIME_TYPE_MAP.put("cod", "image/cis-cod");
        MIME_TYPE_MAP.put("gif", "image/gif");
        MIME_TYPE_MAP.put("ief", "image/ief");
        MIME_TYPE_MAP.put("jpe", "image/jpeg");
        MIME_TYPE_MAP.put("jfif", "image/pipeg");
        MIME_TYPE_MAP.put("svg", "image/svg+xml");
        MIME_TYPE_MAP.put("tif", "image/tiff");
        MIME_TYPE_MAP.put("tiff", "image/tiff");
        MIME_TYPE_MAP.put("ras", "image/x-cmu-raster");
        MIME_TYPE_MAP.put("cmx", "image/x-cmx");
        MIME_TYPE_MAP.put("ico", "image/x-icon");
        MIME_TYPE_MAP.put("pnm", "image/x-portable-anymap");
        MIME_TYPE_MAP.put("pbm", "image/x-portable-bitmap");
        MIME_TYPE_MAP.put("pgm", "image/x-portable-graymap");
        MIME_TYPE_MAP.put("ppm", "image/x-portable-pixmap");
        MIME_TYPE_MAP.put("rgb", "image/x-rgb");
        MIME_TYPE_MAP.put("xbm", "image/x-xbitmap");
        MIME_TYPE_MAP.put("xpm", "image/x-xpixmap");
        MIME_TYPE_MAP.put("xwd", "image/x-xwindowdump");
        MIME_TYPE_MAP.put("mht", "message/rfc822");
        MIME_TYPE_MAP.put("mhtml", "message/rfc822");
        MIME_TYPE_MAP.put("nws", "message/rfc822");
        MIME_TYPE_MAP.put("323", "text/h323");
        MIME_TYPE_MAP.put("htm", "text/html");
        MIME_TYPE_MAP.put("html", "text/html");
        MIME_TYPE_MAP.put("stm", "text/html");
        MIME_TYPE_MAP.put("uls", "text/iuls");
        MIME_TYPE_MAP.put("bas", "text/plain");
        MIME_TYPE_MAP.put("c", "text/plain");
        MIME_TYPE_MAP.put("h", "text/plain");
        MIME_TYPE_MAP.put("txt", "text/plain");
        MIME_TYPE_MAP.put("rtx", "text/richtext");
        MIME_TYPE_MAP.put("sct", "text/scriptlet");
        MIME_TYPE_MAP.put("tsv", "text/tab-separated-values");
        MIME_TYPE_MAP.put("htt", "text/webviewhtml");
        MIME_TYPE_MAP.put("htc", "text/x-component");
        MIME_TYPE_MAP.put("etx", "text/x-setext");
        MIME_TYPE_MAP.put("vcf", "text/x-vcard");
        MIME_TYPE_MAP.put("mp2", "video/mpeg");
        MIME_TYPE_MAP.put("mpa", "video/mpeg");
        MIME_TYPE_MAP.put("mpe", "video/mpeg");
        MIME_TYPE_MAP.put("mpeg", "video/mpeg");
        MIME_TYPE_MAP.put("mpg", "video/mpeg");
        MIME_TYPE_MAP.put("mpv2", "video/mpeg");
        MIME_TYPE_MAP.put("mov", "video/quicktime");
        MIME_TYPE_MAP.put("qt", "video/quicktime");
        MIME_TYPE_MAP.put("lsf", "video/x-la-asf");
        MIME_TYPE_MAP.put("lsx", "video/x-la-asf");
        MIME_TYPE_MAP.put("asf", "video/x-ms-asf");
        MIME_TYPE_MAP.put("asr", "video/x-ms-asf");
        MIME_TYPE_MAP.put("asx", "video/x-ms-asf");
        MIME_TYPE_MAP.put("avi", "video/x-msvideo");
        MIME_TYPE_MAP.put("movie", "video/x-sgi-movie");
        MIME_TYPE_MAP.put("flr", "x-world/x-vrml");
        MIME_TYPE_MAP.put("vrml", "x-world/x-vrml");
        MIME_TYPE_MAP.put("wrl", "x-world/x-vrml");
        MIME_TYPE_MAP.put("wrz", "x-world/x-vrml");
        MIME_TYPE_MAP.put("xaf", "x-world/x-vrml");
        MIME_TYPE_MAP.put("xof", "x-world/x-vrml");
    }

    private MimeTypeUtils() {

    }

    /**
     * 根据扩展名来取得MIMETYPE
     *
     * @param extension 扩展名
     * @return MIMETYPE
     */
    public static String getMimeType(String extension) {
        String mimeType = MIME_TYPE_MAP.get(extension);
        if (StringUtils.isBlank(mimeType)) {
            return "application/octet-stream";
        } else {
            return mimeType;
        }
    }
}
