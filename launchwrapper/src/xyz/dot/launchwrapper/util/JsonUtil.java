package xyz.dot.launchwrapper.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class JsonUtil {
    private int pos;
    private final String jsonStr;

    public JsonUtil(String jsonStr) {
        this.jsonStr = jsonStr;
        this.pos = 0;
    }
    public Object parse() {
        skipWhiteSpace();

        char ch = jsonStr.charAt(pos);
        if (ch == '{') {
            return parseObject();
        } else if (ch == '[') {
            return parseArray();
        } else if (ch == '\"') {
            return parseString();
        } else if (ch == 't') {
            return parseTrue();
        } else if (ch == 'f') {
            return parseFalse();
        } else if (ch == 'n') {
            return parseNull();
        } else {
            return parseNumber();
        }
    }

    private Object parseObject() {
        Map<String, Object> map = new HashMap<>();
        pos++;
        while (true) {
            skipWhiteSpace();
            char ch = jsonStr.charAt(pos);
            if (ch == '}') {
                pos++;
                break;
            } else if (ch == '\"') {
                String key = parseString();
                skipWhiteSpace();
                pos++;
                Object value = parse();
                map.put(key, value);
                skipWhiteSpace();
                ch = jsonStr.charAt(pos);
                if (ch == ',') {
                    pos++;
                } else if (ch == '}') {
                    pos++;
                    break;
                } else {
                    throw new RuntimeException("Invalid JSON string: " + jsonStr);
                }
            } else {
                throw new RuntimeException("Invalid JSON string: " + jsonStr);
            }
        }
        return map;
    }

    private Object parseArray() {
        List<Object> list = new ArrayList<>();
        pos++;
        while (true) {
            skipWhiteSpace();
            char ch = jsonStr.charAt(pos);
            if (ch == ']') {
                pos++;
                break;
            } else {
                Object value = parse();
                list.add(value);
                skipWhiteSpace();
                ch = jsonStr.charAt(pos);
                if (ch == ',') {
                    pos++;
                } else if (ch == ']') {
                    pos++;
                    break;
                } else {
                    throw new RuntimeException("Invalid JSON string: " + jsonStr);
                }
            }
        }

        return list;
    }

    private String parseString() {
        StringBuilder sb = new StringBuilder();
        pos++;
        while (true) {
            char ch = jsonStr.charAt(pos);
            if (ch == '\"') {
                pos++;
                break;
            } else if (ch == '\\') {
                pos++;
                ch = jsonStr.charAt(pos);
                if (ch == '\"' || ch == '\\' || ch == '/') {
                    sb.append(ch);
                } else if (ch == 'b') {
                    sb.append('\b');
                } else if (ch == 'f') {
                    sb.append('\f');
                } else if (ch == 'n') {
                    sb.append('\n');
                } else if (ch == 'r') {
                    sb.append('\r');
                } else if (ch == 't') {
                    sb.append('\t');
                } else if (ch == 'u') {
                    pos++;
                    int codePoint = Integer.parseInt(jsonStr.substring(pos, pos + 4), 16);
                    sb.append(Character.toChars(codePoint));
                    pos += 3;
                } else {
                    throw new RuntimeException("Invalid JSON string: " + jsonStr);
                }
            } else {
                sb.append(ch);
            }
            pos++;
        }
        return sb.toString();
    }

    private Object parseTrue() {
        if (jsonStr.startsWith("true", pos)) {
            pos += 4;
            return true;
        } else {
            throw new RuntimeException("Invalid JSON string: " + jsonStr);
        }
    }

    private Object parseFalse() {
        if (jsonStr.startsWith("false", pos)) {
            pos += 5;
            return false;
        } else {
            throw new RuntimeException("Invalid JSON string: " + jsonStr);
        }
    }

    private Object parseNull() {
        if (jsonStr.startsWith("null", pos)) {
            pos += 4;
            return null;
        } else {
            throw new RuntimeException("Invalid JSON string: " + jsonStr);
        }
    }

    private Number parseNumber() {
        int start = pos;
        boolean isFloat = false;

        while (pos < jsonStr.length()) {
            char ch = jsonStr.charAt(pos);
            if (ch == '.' || ch == 'e' || ch == 'E') {
                isFloat = true;
            } else if (ch < '0' || ch > '9') {
                break;
            }
            pos++;
        }

        String numberStr = jsonStr.substring(start, pos);
        if (isFloat) {
            return Double.parseDouble(numberStr);
        } else {
            return Long.parseLong(numberStr);
        }
    }

    private void skipWhiteSpace() {
        while (pos < jsonStr.length()) {
            char ch = jsonStr.charAt(pos);
            if (ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r') {
                pos++;
            } else {
                break;
            }
        }
    }
}