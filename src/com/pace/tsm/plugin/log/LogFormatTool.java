
package com.pace.tsm.plugin.log;

public class LogFormatTool {
    private static String SPACE = "   ";
    private static LogFormatTool instance;

    private LogFormatTool() {
    }

    public static LogFormatTool getInstance() {
        if (instance == null) {
            instance = new LogFormatTool();
        }
        return instance;
    }

    public String formatJson(String json) {
        StringBuffer result = new StringBuffer();
        int length = json.length();
        int number = 0;
        int i = 0;
        while (i < length) {
            char key = json.charAt(i);
            if (key == '[' || key == '{') {
                if (i - 1 > 0 && json.charAt(i - 1) == ':') {
                    result.append('\n');
                    result.append(indent(number));
                }
                result.append(key);
                result.append('\n');
                number++;
                result.append(indent(number));
            } else if (key == ']' || key == '}') {
                result.append('\n');
                number--;
                result.append(indent(number));
                result.append(key);
                if (i + 1 < length && json.charAt(i + 1) != ',') {
                    result.append('\n');
                }
            } else if (key == ',') {
                result.append(key);
                result.append('\n');
                result.append(indent(number));
            } else {
                result.append(key);
            }
            i++;
        }
        return result.toString();
    }

    private String indent(int number) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < number; i++) {
            result.append(SPACE);
        }
        return result.toString();
    }
}
