package ru.minejs.rest.api;

import java.util.Arrays;

public class CodeGen {
    public final static char PATTERN_PLACEHOLDER = '#';
    private final int length;
    private final String charset;
    private final String prefix;
    private final String postfix;
    private final String pattern;

    public CodeGen(Integer length, String charset, String prefix, String postfix, String pattern) {
        if (length == null) {
            length = 8;
        }

        if (charset == null) {
            charset = Charset.ALPHANUMERIC;
        }

        if (pattern == null) {
            char[] chars = new char[length];
            Arrays.fill(chars, PATTERN_PLACEHOLDER);
            pattern = new String(chars);
        }

        this.length = length;
        this.charset = charset;
        this.prefix = prefix;
        this.postfix = postfix;
        this.pattern = pattern;
    }

    public static CodeGen length(int length) {
        return new CodeGen(length, null, null, null, null);
    }

    public static CodeGen pattern(String pattern) {
        return new CodeGen(null, null, null, null, pattern);
    }

    public int getLength() {
        return length;
    }

    public String getCharset() {
        return charset;
    }

    public CodeGen withCharset(String charset) {
        return new CodeGen(length, charset, prefix, postfix, pattern);
    }

    public String getPrefix() {
        return prefix;
    }

    public CodeGen withPrefix(String prefix) {
        return new CodeGen(length, charset, prefix, postfix, pattern);
    }

    public String getPostfix() {
        return postfix;
    }

    public CodeGen withPostfix(String postfix) {
        return new CodeGen(length, charset, prefix, postfix, pattern);
    }

    public String getPattern() {
        return pattern;
    }

    @Override
    public String toString() {
        return "CodeConfig ["
                + "length=" + length + ", "
                + "charset=" + charset + ", "
                + "prefix=" + prefix + ", "
                + "postfix=" + postfix + ", "
                + "pattern=" + pattern + "]";
    }

    public static class Charset {
        public static final String ALPHABETIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        public static final String ALPHANUMERIC = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        public static final String NUMBERS = "0123456789";
    }
}
