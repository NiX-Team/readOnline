package com.kiss.cache.util;

import java.io.*;

/**
 * @author 11723
 */
public final class ObjectUtil {
    /**
     * 将object转为byte数组
     * */
    public static byte[] ObjectToByte(Object obj) throws Exception {
        if (!(obj instanceof Serializable)) throw new Exception("转换对象没有实现Serializable接口");
        byte[] bytes = null;
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);
            bytes = bo.toByteArray();
            oo.close();
            bo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }
    /**
     * 将byte数组转为object
     * */
    public static Object ByteToObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
            ObjectInputStream oi = new ObjectInputStream(bi);
            obj = oi.readObject();
            bi.close();
            oi.close();
        } catch (Exception e) {
            System.out.println("translation" + e.getMessage());
            e.printStackTrace();
        }
        return obj;
    }
}
