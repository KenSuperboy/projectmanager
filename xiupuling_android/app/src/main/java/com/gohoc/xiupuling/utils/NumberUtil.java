package com.gohoc.xiupuling.utils;

import java.text.DecimalFormat;

/**
 * @version V1.0
 * @createAuthor （keke）
 * @createDate 2017/7/10 0010
 * @updateAuthor
 * @updateDate
 * @company 跨越速运
 * @description
 * @copyright copyright(c)2016 Shenzhen Kye Technology Co., Ltd. Inc. All rights reserved.
 */
public class NumberUtil {

    /**
     * 获取四舍五入的2位小数
     */
    public static String getDecimal(double num) {
        try {

            DecimalFormat df = new DecimalFormat("#0.00");
            return getDecimalFormat(df.format(num));
        } catch (Exception e) {
            return "0.00";
        }
    }

    public static String getDecimalFormat(String num) {
        try {
            DecimalFormat df = new DecimalFormat("#0.00");
            return df.format(Double.valueOf(num));
        } catch (Exception e) {
            return "0.00";
        }
    }
}
