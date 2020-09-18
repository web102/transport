package com.bobandata.iot.transport.util;

import java.io.*;

/**
 * @Author: lizhipeng
 * @Description: 对象和字节数组之间相互转换
 * @Company: 上海博般数据技术有限公司
 * @Date: Created in 17:52 2018/11/19.
 */
public class ByteArrayUtils {

    public static<T> byte[] objectToBytes(T obj){
        byte[] bytes = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream sOut;
        try { sOut = new ObjectOutputStream(out);
            sOut.writeObject(obj);
            sOut.flush();
            bytes= out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public static<T> T bytesToObject(byte[] bytes) {
        T t = null;
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream sIn;
        try { sIn = new ObjectInputStream(in);
            t = (T)sIn.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

}
