package com.kiss.util;

import com.kiss.common.ReturnObject;

/**
 * @author 11723
 */
public final class ReturnUtil {
    private final static int SUCCESS_CODE = 1;
    private final static String SUCCESS_MSG = "SUCCESS";
    private final static int FAIL_CODE = -1;
    private final static String FAIL_MSG = "FAIL";
    /**
     * 接口调用成功，不需要
     * @param o 返回的内容
     * */
    public static ReturnObject success(Object o) {
        return success(null,o);
    }
    /**
     * 接口调用成功，有提示消息
     * @param msg 提示消息
     * @param o 返回内容
     * */
    public static ReturnObject success(String msg,Object o) {
        ReturnObject object = new ReturnObject();
        object.setStatus(SUCCESS_CODE);
        object.setMsg(msg == null ? SUCCESS_MSG : msg);
        object.setData(o);
        return object;
    }
    /**
     * 接口调用失败
     * @param o 失败时返回的内容
     * */
    public static ReturnObject fail(Object o) {
        return fail(null,null,o);
    }

    /**
     * 接口调用失败，有提示消息
     * @param msg 提示消息
     * @param o 失败返回内容
     * */
    public static ReturnObject fail(Integer code,String msg,Object o) {
        ReturnObject object = new ReturnObject();
        object.setStatus(code == null ? FAIL_CODE : code);
        object.setMsg(msg == null ? FAIL_MSG : msg);
        object.setData(o);
        return object;
    }
}
