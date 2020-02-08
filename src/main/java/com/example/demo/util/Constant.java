package com.example.demo.util;

import static com.example.demo.util.ConstantUtils.getConstantValue;

/**
 * 通用常量定义
 * @author Derrick
 */
public final class Constant {
    /**
     * 中文逗号.
     */
    public static final String COMMA_ZH_CN = getConstantValue("，");

    /**
     * 英文逗号.
     */
    public static final String COMMA_EN_US = getConstantValue(",");

    /**
     * 中文冒号.
     */
    public static final String COLON_ZH_CN = getConstantValue("：");

    /**
     * 英文冒号
     */
    public static final String COLON_EN_US = getConstantValue(":");

    /**
     * 中文分号
     */
    public static final String SEMICOLON_ZH_CN = getConstantValue("；");

    /**
     * 英文分号
     */
    public static final String SEMICOLON_EN_US = getConstantValue(";");
    /**
     * 中划线
     */
    public static final String UNDERLINE = getConstantValue("-");

    /**
     * 是常量
     */
    public static final String CONSTANT_FLAG_YES = getConstantValue("1");

    /**
     * 不是常量
     */
    public static final String CONSTANT_FLAG_NO = getConstantValue("0");

    /**
     * 日期格式 yyyy-MM-dd HH:mm:ss
     */
    public static final String YYYY_MM_DD_24HH_MM_SS = getConstantValue("yyyy-MM-dd HH:mm:ss");

    /**
     * 日期格式 yyyy-MM-dd
     */
    public static final String YYYY_MM_DD = getConstantValue("yyyy-MM-dd");

    /**
     * 日期格式 yyyy-MM-dd HH:mm
     */
    public static final String DATE_HOUR_FORMAT = getConstantValue("yyyy-MM-dd HH:mm");

    /**
     * 日期格式 yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_TIME_FORMAT = getConstantValue("yyyy-MM-dd HH:mm:ss");

    /**
     * 日期格式HH:mm
     */
    public static final String HOUR_TIME_FORMAT = getConstantValue("HH:mm");

    /**
     * Bigdecimal小数精度
     */
    public static final int BIGDECIMAL_DIGITAL_6 = getConstantValue(6);

    /**
     * Bigdecimal小数精度
     */
    public static final int BIGDECIMAL_DIGITAL_4 = getConstantValue(4);

    /**
     * Bigdecimal小数精度
     */
    public static final int BIGDECIMAL_DIGITAL_2 = getConstantValue(2);

    /**
     * pdf字体路径
     */
    public static final String PDF_FONT_PATH = getConstantValue("pdfFontPath");

    /**
     * ROOT_COMPANY_ID 顶级机构id
     */
    public static final Long ROOT_COMPANY_ID = 0L;

    /**
     * ccc company id
     */
    public static final Long CCC_COMPANY_ID = 1L;

    /**
     * 一级公司
     */
    public static final Integer COMPANY_LEVEL_ONE = 1;

    /**
     * 有效
     */
    public static final String VALID_FLAG_YES = getConstantValue("1");

    /**
     * 无效
     */
    public static final String VALID_FLAG_NO = getConstantValue("0");

    /**
     * excel 后缀xlsx
     */
    public static final String EXCEL_SUFFIX_XLSX = getConstantValue(".xlsx");

    /**
     * 提示语分隔符“、”
     */
    public static final String CAESURA_SIGN = getConstantValue("、");

    /**
     * YES
     */
    public static final String YES = getConstantValue("1");

    /**
     * NO
     */
    public static final String NO = getConstantValue("0");

    /**
     * 强制的
     */
    public static final String COMPULSIVE = getConstantValue("1");

    /**
     * 友好的
     */
    public static final String FRIENDLY = getConstantValue("2");

    /**
     * 100的整数常量
     */
    public static final int ONE_HUNDRED = getConstantValue(100);

    /**
     * 默认的表格一页显示数
     */
    public static final int DEFAULT_PAGE_SIZE = getConstantValue(25);

    /**
     * 分页第一页
     */
    public static final int DEFAULT_PAGE_NUMBER = getConstantValue(0);

    /**
     * 1KB的字节长度
     */
    public static final int BYTE_1KB = getConstantValue(1024);

    /**
     * URL编码UTF-8.
     */
    public static final String URL_ENCODER_UTF8 = getConstantValue("UTF-8");

    /**
     * URL编码ISO8859-1.
     */
    public static final String URL_ENCODER_ISO88591 = getConstantValue("ISO8859-1");

    /**
     * 浏览器-IE.
     */
    public static final String BROWSER_IE = getConstantValue("MSIE");

    private Constant() {
    }
}
