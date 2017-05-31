
package com.pace.tsm.plugin.utils;

import java.util.UUID;
import java.util.regex.Pattern;

public class ValueUtil {
    private static final double EPS = 1.0E-6d;
    private static final int GB = 1073741824;
    public static final int HistoryActLineNumber = 7;
    private static final int KB = 1024;
    public static final int LineNumber = 10;
    private static final int MG = 1048576;
    public static String TempSymbol = "°C";
    public static String YuanSymbol = "￥";

    public static boolean isEmpty(String value) {
        if (value == null || "".equals(value.trim())) {
            return true;
        }
        value = value.replaceAll(" ", "").trim();
        if (value == null || "".equals(value.trim())) {
            return true;
        }
        return false;
    }

    public static short parseShort(String shortString) {
        if (!(shortString == null || shortString.trim().equals(""))) {
            try {
                return Short.valueOf(shortString).shortValue();
            } catch (Exception e) {
            }
        }
        return (short) 0;
    }

    public static int parseInt(String intString) {
        if (!(intString == null || intString.trim().equals(""))) {
            try {
                return Integer.valueOf(intString).intValue();
            } catch (Exception e) {
            }
        }
        return 0;
    }

    public static long parseLong(String longString, int radix) {
        if (!isEmpty(longString)) {
            try {
                return Long.parseLong(longString, radix);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static String newUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static int containsNum(String content, String target) {
        if (content == null || "".equals(content) || target == null || "".equals(target)) {
            return 0;
        }
        return content.length() - content.replace(target, "").length();
    }

    public static double formula2(double a, double b, double c, double total) {
        c -= total;
        if (!doubleEqual(a, 0.0d)) {
            double delta = (b * b) - ((4.0d * a) * c);
            if (delta < 0.0d) {
                return 0.0d;
            }
            return (((-b) + Math.sqrt(delta)) / 2.0d) / a;
        } else if (doubleEqual(b, 0.0d)) {
            return 0.0d;
        } else {
            return (-c) / b;
        }
    }

    public static boolean doubleEqual(double a, double b) {
        return Math.abs(a - b) < EPS;
    }

    public static boolean validateChineseLength(String value, int length) {
        Pattern pattern = Pattern.compile("[一-龥]");
        int len = 0;
        for (int i = 0; i < value.length(); i++) {
            if (pattern.matcher(value.substring(i, i + 1)).matches()) {
                len += 2;
            } else {
                len++;
            }
        }
        if (len > length) {
            return true;
        }
        return false;
    }

    public static String replaceTemplate(String templateVal, String[] realVals, String[] temps) {
        int count1 = realVals.length;
        int count2 = temps.length;
        if (count1 != count2) {
            throw new IllegalArgumentException("输入的参数个数不一致!!");
        }
        for (int i = 0; i < count2; i++) {
            if (Pattern.compile(temps[i]).matcher(templateVal).find()) {
                templateVal = templateVal.replaceAll(temps[i], realVals[i]);
            }
        }
        return templateVal;
    }

    private static boolean match(String regex, String str) {
        return Pattern.compile(regex).matcher(str).matches();
    }

    public static boolean IsIntNumber(String str) {
        return match("^\\+?[1-9][0-9]*$", str);
    }

    public static boolean IsNumber(String str) {
        return match("^[0-9]*$", str);
    }

    public static boolean isMobile(String str) {
        return match("[0-9]{3}[0-9]{4}[0-9]{4}", str);
    }

    // public static int validateMobile(String mobile) {
    // if (isEmpty(mobile)) {
    // return ResultCode.BUSINESS_MOBILE_NULL;
    // }
    // if (isMobile(mobile)) {
    // return 0;
    // }
    // return ResultCode.BUSINESS_MOBILE_INVALID;
    // }

    public static String toHex(int data, int len, String defaultHold) {
        StringBuilder hex = new StringBuilder(Integer.toHexString(data));
        int size = len - hex.length();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                hex.insert(0, defaultHold);
            }
        }
        return hex.toString();
    }

    public static String toBin(int data, int len, String defaultHold) {
        StringBuilder bin = new StringBuilder(Integer.toBinaryString(data));
        int size = len - bin.length();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                bin.insert(0, defaultHold);
            }
        }
        return bin.toString();
    }

    public static String retainValidDecimal(String money, int decimalNum, String defaultValue) {
        try {
            if (!isEmpty(money) && IsNumber(money)) {
                defaultValue = String.format("%." + decimalNum + "f", new Object[] {
                        Float.valueOf(((float) Long.parseLong(money)) / 100.0f)
                });
            }
        } catch (Exception e) {
        }
        return defaultValue;
    }

    public static String formatSize(long size) {
        String display_size = "";
        if (size > 1073741824) {
            return String.format("%.2f Gb ", new Object[] {
                    Double.valueOf(((double) size) / 1.073741824E9d)
            });
        } else if (size < 1073741824 && size > 1048576) {
            return String.format("%.2f Mb ", new Object[] {
                    Double.valueOf(((double) size) / 1048576.0d)
            });
        } else if (size >= 1048576 || size <= 1024) {
            return String.format("%.2f bytes ", new Object[] {
                    Double.valueOf((double) size)
            });
        } else {
            return String.format("%.2f Kb ", new Object[] {
                    Double.valueOf(((double) size) / 1024.0d)
            });
        }
    }

    public static int parseInt(String intString, int radix) {
        if (!isEmpty(intString)) {
            try {
                return Integer.parseInt(intString, radix);
            } catch (Exception e) {
            }
        }
        return 0;
    }

    public static boolean isHexNumber(String str) {
        boolean flag = false;
        for (int i = 0; i < str.length(); i++) {
            char cc = str.charAt(i);
            if (cc == '0' || cc == '1' || cc == '2' || cc == '3' || cc == '4' || cc == '5'
                    || cc == '6' || cc == '7' || cc == '8' || cc == '9' || cc == 'A' || cc == 'B'
                    || cc == 'C' || cc == 'D' || cc == 'E' || cc == 'F' || cc == 'a' || cc == 'b'
                    || cc == 'c' || cc == 'c' || cc == 'd' || cc == 'e' || cc == 'f') {
                flag = true;
            }
        }
        return flag;
    }

    public static String toLV(String targetVal) {
        String result = "";
        if (isEmpty(targetVal)) {
            return result;
        }
        return new StringBuilder(String.valueOf(toHex(targetVal.length() / 2, 2, "0")))
                .append(targetVal).toString();
    }

    public static boolean checkBlanceIsPlus(String blance) {
        if (blance == null || blance.length() != 8) {
            // LogUtil.log("this balance is not normative");
            return false;
        }
        String a = blance.substring(0, 1);
        String[] b = new String[] {
                "8", "9", "a", "b", "c", "d", "e", "f", "A", "B", "C", "D", "E", "F"
        };
        for (Object equals : b) {
            if (a.equals(equals)) {
                return true;
            }
        }
        return false;
    }
}
