package com.bobandata.iot.transport.util;

import io.netty.buffer.ByteBuf;

public class HexUtils {
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static char[] encodeHex(byte[] data) {
        return encodeHex(data, true);
    }

    public static char[] encodeHex(byte[] data, boolean toLowerCase) {
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    protected static char[] encodeHex(byte[] data, char[] toDigits) {
        int l = data.length;
        char[] out = new char[l << 1];

        int i = 0;
        for (int j = 0; i < l; i++) {
            out[(j++)] = toDigits[((0xF0 & data[i]) >>> 4)];
            out[(j++)] = toDigits[(0xF & data[i])];
        }
        return out;
    }

    public static String encodeHexStr(byte[] data) {
        return encodeHexStr(data, true);
    }

    public static String encodeHexStr(byte[] data, int length) {
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            bytes[(length - 1 - i)] = data[i];
        }
        return encodeHexStr(bytes, true);
    }

    public static String encodeHexStr(byte[] data, boolean toLowerCase) {
        return encodeHexStr(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    public static String encodeHexStrLog(byte[] data, boolean toLowerCase) {
        int length = 0;
        switch (data[0]) {
            case 16:
                length = 6;
                break;
            case 104:
                length = byteToInt(data[1]) + 6;
                break;
            default:
                return "";
        }

        return encodeHexStrLog(data, length, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    protected static String encodeHexStr(byte[] data, char[] toDigits) {
        return new String(encodeHex(data, toDigits));
    }

    protected static String encodeHexStrLog(byte[] data, int length, char[] toDigits) {
        char[] chars = encodeHex(data, toDigits);
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length * 2; i++) {
            stringBuffer.append(chars[i]);
            if ((i + 1) % 2 == 0) {
                stringBuffer.append(" ");
            }
        }
        return stringBuffer.toString();
    }

    public static byte[] decodeHex(char[] data) {
        int len = data.length;

        if ((len & 0x1) != 0) {
            throw new RuntimeException("Odd number of characters.");
        }

        byte[] out = new byte[len >> 1];

        int i = 0;
        for (int j = 0; j < len; i++) {
            int f = toDigit(data[j], j) << 4;
            j++;
            f |= toDigit(data[j], j);
            j++;
            out[i] = ((byte) (f & 0xFF));
        }

        return out;
    }

    protected static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
        }

        return digit;
    }

    public static byte xor(byte[] data) {
        byte temp = 0;
        for (int i = 0; i < data.length; i++) {
            temp = (byte) (temp ^ data[i]);
        }
        return temp;
    }

    public static byte cs(byte[] array) {
        int value = 0;
        for (int i = 0; i < array.length; i++) {
            value += array[i];
        }
        return (byte) (value % 256);
    }

    public static byte cs(ByteBuf buffer, int index) {
        int value = 0;
        byte[] array = buffer.array();
        for (int i = index; i < array.length; i++) {
            value += array[i];
        }
        return (byte) (value % 256);
    }

    public static byte countCs(ByteBuf buffer) {
        int value = 0;
        int len = buffer.writerIndex() - 4 - 2;
        byte[] arr = new byte[len];
        buffer.getBytes(4, arr);
        for (int i = 0; i < arr.length; i++) {
            value += arr[i];
        }
        return (byte) (value % 256);
    }

    public static byte intToByte(int x) {
        return (byte) x;
    }

    public static int byteToInt(byte b) {
        return b & 0xFF;
    }

    public static void main(String[] args) {
        byte b = 26;
        System.out.print(byteToInt(b));
    }
}