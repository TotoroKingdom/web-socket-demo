package com.totoro.util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;

/**
 * @author:totoro
 * @createDate:2022/9/7
 * @description:
 */
public class ByteUtils {

    public static ByteBuffer getByteBuffer(String str){
        return ByteBuffer.wrap(str.getBytes(StandardCharsets.UTF_8));
    }

    public static String getString(ByteBuffer buffer){
        Charset charset = null;
        CharsetDecoder decoder = null;
        CharBuffer charBuffer = null;

        try {
            charset = Charset.forName("UTF-8");
            decoder = charset.newDecoder();
            charBuffer = decoder.decode(buffer.asReadOnlyBuffer());
            return charBuffer.toString();
        } catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
}
