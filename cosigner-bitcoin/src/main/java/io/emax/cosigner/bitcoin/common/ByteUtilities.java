package io.emax.cosigner.bitcoin.common;

import java.util.Arrays;

public class ByteUtilities {

  public static byte[] stripLeadingNullBytes(byte[] input) {
    byte[] result = Arrays.copyOf(input, input.length);
    while (result.length > 0 && result[0] == 0x00) {
      result = Arrays.copyOfRange(result, 1, result.length);
    }
    return result;
  }

  static final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();

  public static String toHexString(byte[] data) {
    char[] chars = new char[data.length * 2];
    for (int i = 0; i < data.length; i++) {
      chars[i * 2] = HEX_DIGITS[(data[i] >> 4) & 0xf];
      chars[i * 2 + 1] = HEX_DIGITS[data[i] & 0xf];
    }
    return new String(chars).toLowerCase();
  }

  public static byte[] toByteArray(String data) {
    if (data.length() == 0) {
      return new byte[] {};
    }
    while (data.length() < 2) {
      data = "0" + data;
    }
    if (data.substring(0, 2).toLowerCase().equals("0x")) {
      data = data.substring(2);
    }
    if (data.length() % 2 == 1) {
      data = "0" + data;
    }

    data = data.toUpperCase();

    byte[] bytes = new byte[data.length() / 2];
    String hexString = new String(HEX_DIGITS);
    for (int i = 0; i < bytes.length; i++) {
      int byteConv = (hexString.indexOf(data.charAt(i * 2)) * 0x10);
      byteConv += (hexString.indexOf(data.charAt((i * 2) + 1)));
      bytes[i] = (byte) (byteConv & 0xFF);
    }

    return bytes;
  }

  public static byte[] flipEndian(byte[] data) {
    if (data == null) {
      return null;
    }
    byte[] newData = new byte[data.length];
    for (int i = 0; i < data.length; i++) {
      newData[data.length - i - 1] = data[i];
    }

    return newData;
  }

  public static byte[] leftPad(byte[] data, int size, byte pad) {
    if (size <= data.length) {
      return data;
    }

    byte[] newData = new byte[size];
    for (int i = 0; i < size; i++) {
      newData[i] = pad;
    }
    for (int i = 0; i < data.length; i++) {
      newData[size - i - 1] = data[data.length - i - 1];
    }

    return newData;
  }

  public static byte[] readBytes(byte[] data, int start, int size) {
    if (data.length < start + size) {
      return null;
    }

    byte[] newData = new byte[size];
    newData = Arrays.copyOfRange(data, start, start + size);

    return newData;
  }
}
