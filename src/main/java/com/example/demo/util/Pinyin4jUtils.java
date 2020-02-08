package com.example.demo.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 中文转拼音的工具类
 */
public class Pinyin4jUtils {

    private static final Logger LOG = LoggerFactory.getLogger(Pinyin4jUtils.class.getName());
    /**
     * 获取第一个汉字的汉语拼音首字母，英文字母不变，特殊字符丢失
     * 多音字用英文逗号分隔
     *
     * @param chines 汉字
     * @return 拼音
     */
    public static String getSpellIndex(String chines) {
        String firstWord = null;
        String trimChines = StringUtils.trim(chines);
        if (trimChines != null && trimChines.length() > 0) {
            firstWord = trimChines.substring(0, 1);
        }
        if (firstWord == null) {
            return null;
        }
        StringBuilder pinyinName = new StringBuilder();
        char[] nameChar = firstWord.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char curChar : nameChar) {
            StringBuilder curSpell = new StringBuilder();
            if (curChar >= 0x4e00 && curChar <= 0x9fbb) {
                try {
                    // 取得当前汉字的所有全拼
                    String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(curChar, defaultFormat);
                    if (pinyins != null) {
                        for (String pinyin : pinyins) {
                            // 取首字母
                            if (curSpell.length() > 0) {
                                curSpell.append(",");
                            }
                            curSpell.append(pinyin.charAt(0));
                        }
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    LOG.error("bad pinyin format", e);
                }
            } else if ((curChar >= 65 && curChar <= 90) || (curChar >= 97 && curChar <= 122)) {
                curSpell.append(String.valueOf(curChar).toUpperCase());
            }

            if (curSpell.length() > 0) {
                if (pinyinName.length() > 0) {
                    pinyinName.append(" ");
                }
                pinyinName.append(curSpell);
            }
        }
        return parseTheChineseByObject(discountTheChinese(pinyinName.toString()));
    }

    /**
     * 汉字转换汉语全拼，英文字母不变，特殊字符丢失
     * 支持多音字，生成方式如（重当参:ZHONGDANGCEN,ZHONGDANGCAN,CHONGDANGCEN,CHONGDANGSHEN,ZHONGDANGSHEN,CHONGDANGCAN）
     *
     * @param chines 汉字
     * @return 拼音
     */
    public static String converterToSpell(String chines) {
        if (chines == null) {
            return null;
        }
        StringBuilder pinyinName = new StringBuilder();
        char[] nameChar = StringUtils.trim(chines).toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char curChar : nameChar) {
            StringBuilder curSpell = new StringBuilder();
            if (curChar >= 0x4e00 && curChar <= 0x9fbb) {
                try {
                    // 取得当前汉字的所有全拼
                    String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(curChar, defaultFormat);
                    if (pinyins != null) {
                        for (String pinyin : pinyins) {
                            if (curSpell.length() > 0) {
                                curSpell.append(",");
                            }
                            curSpell.append(pinyin);
                        }
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    LOG.error(e.getMessage(), e);
                }
            } else if ((curChar >= 65 && curChar <= 90) || (curChar >= 97 && curChar <= 122)) {
                curSpell.append(String.valueOf(curChar).toUpperCase());
            }

            if (curSpell.length() > 0) {
                if (pinyinName.length() > 0) {
                    pinyinName.append(" ");
                }
                pinyinName.append(curSpell);
            }
        }
        return parseTheChineseByObject(discountTheChinese(pinyinName.toString()));
    }

    /**
     * 去除多音字重复数据
     *
     * @param theStr
     * @return
     */
    private static List<Set<String>> discountTheChinese(String theStr) {
        // 去除重复拼音后的拼音列表
        List<Set<String>> mapList = new ArrayList<>();
        // 读出每个汉字的拼音
        for (String str : theStr.split(" ")) {
            // 用于处理每个字的多音字，去掉重复
            Set<String> discount = new HashSet<>(Arrays.asList(str.split(",")));
            mapList.add(discount);
        }
        return mapList;
    }

    /**
     * 组合拼音
     *
     * @return
     */
    private static String parseTheChineseByObject(List<Set<String>> list) {
        Set<String> first = null; // 用于统计每一次,集合组合数据
        // 遍历每一组集合
        for (int i = 0; i < list.size(); i++) {
            // 每一组集合与上一次组合的Map
            Set<String> temp = null;
            // 第一次循环，first为空
            if (first != null) {
                // 取出上次组合与此次集合的字符，并保存
                temp = new HashSet<String>();
                for (String s : first) {
                    for (String s1 : list.get(i)) {
                        String str = s + s1;
                        temp.add(str);
                    }
                }
            } else {
                temp = list.get(i);
            }
            // 保存组合数据以便下次循环使用
            if (temp != null && temp.size() > 0) {
                first = temp;
            }
        }

        String result = "";
        if (first != null && first.size() > 0) {
            result = StringUtils.join(first, ",");
        }
        return result;
    }

}