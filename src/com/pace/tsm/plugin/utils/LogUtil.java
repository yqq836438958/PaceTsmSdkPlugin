
package com.pace.tsm.plugin.utils;

import android.text.TextUtils;
import android.util.Log;

import com.pace.tsm.plugin.log.LogFilter;
import com.pace.tsm.plugin.log.LogFormatTool;

import java.util.ArrayList;
import java.util.List;

public class LogUtil {
    public static final String ACCESS_SERVER = " access_server ";
    public static final String ALA = " INTERFACE_";
    public static final String CARD_INFO = " CardInfo_";
    public static final String CHANNEL = " Channel_";
    public static final String CONTENTENDTAG = ") ";
    public static final String CONTENTSTARTTAG = " (";
    public static final String DEFAULT_PROJECT = " WalletService";
    private static final int ERROR = 1;
    private static final int INFO = 0;
    public static final String MUPERSO = " perso_";
    public static final String REQUEST_PARAM = "_call_api_request_param=";
    public static final String RESPONSE_RESULT = "_call_api_response=";
    public static final String ROOTNAME = "Pacewear_Plugin_tos_v4.0.07_";
    public static final String SUMSUNG = " SUMSUNG_";
    private static List<LogFilter> mappingTags = new ArrayList();

    private static void logContent(String tag, String msg, int level) {
        if (!(ValueUtil.isEmpty(msg) || mappingTags == null || mappingTags.size() <= 0)) {
            for (LogFilter item : mappingTags) {
                if (msg.indexOf(item.getLogFilterTag()) != -1 && item.getStatus() == 2) {
                    return;
                }
            }
        }
        if (!TextUtils.isEmpty(tag)) {
            msg = new StringBuilder(String.valueOf(tag)).append("_").append(msg).toString();
        }
        tag = " Snowballtech_Plugin_tos_v4.0.07_ WalletService";
        if (msg.contains("{")) {
            msg = LogFormatTool.getInstance().formatJson(msg);
        }
        switch (level) {
            case 0:
                Log.i(tag, msg);
                return;
            case 1:
                Log.e(tag, msg);
                return;
            default:
                return;
        }
    }

    public static void log(String msg) {
        logContent("", msg, 0);
    }

    public static void log(String tag, String msg) {
        logContent(tag, msg, 0);
    }

    public static void loge(String tag, String msg) {
        logContent(tag, msg, 1);
    }
}
