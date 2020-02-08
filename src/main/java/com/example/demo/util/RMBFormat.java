package com.example.demo.util;

import java.text.FieldPosition;
import java.text.ParsePosition;

/**
 * RMB格式化辅助类
 */
public class RMBFormat extends java.text.NumberFormat {

    private static final long serialVersionUID = 1L;

    private static final char[] FRACTION = { '角', '分' };

    private static final char[] DIGIT = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' };

    private static final String[][] UNIT = { { "元", "万", "亿" }, { "", "拾", "佰", "仟" } };

    /**
     * 判断一个double类型对象是否小于0;
     *
     * @param value 数值
     * @return 负 or ""
     */
    protected static String head(double value) {
        return value < 0 ? "负" : "";
    }

    @Override
    public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {

        double num = Math.abs(number);
        final int times = 10;

        StringBuilder resultBuilder = new StringBuilder();
        for (int i = 0; i < FRACTION.length; i++) {
            resultBuilder.append(String.valueOf(new char[]{DIGIT[(int) (Math.floor(num * times * Math.pow(times, i)) % times)], FRACTION[i]})
                    .replaceAll("(零.)+", ""));
        }
        String result = resultBuilder.toString();
        if (result.length() < 1) {
            result = "整";
        }

        int integerPart = (int) Math.floor(num);
        for (int i = 0; i < UNIT[0].length && integerPart > 0; i++) {
            StringBuilder part = new StringBuilder();
            for (int j = 0; j < UNIT[1].length && num > 0; j++) {
                part.insert(0, DIGIT[integerPart % times] + UNIT[1][j]);
                integerPart = integerPart / times;
            }
            result = part.toString().replaceAll("(零.)*零$", "")
                    .replaceAll("^$", "零") + UNIT[0][i] + result;
        }

        return toAppendTo.append(head(number)).append(
                result.replaceAll("(零.)*零元", "元")
                        .replaceFirst("(零.)+", "").replaceAll("(零.)+", "零")
                        .replaceAll("^整$", "零元整"));
    }

    @Override
    public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
        return format((double) number, toAppendTo, pos);

    }

    @Override
    public Number parse(String source, ParsePosition parsePosition) {
        throw new UnsupportedOperationException();
    }
}
