package com.gh.ghdg.common.utils;

import com.gh.ghdg.common.utils.constant.Constants;
import lombok.Data;

@Data
public class Result {
    protected static final String SAVE_SUC = "保存成功";
    protected static final String DEL_SUC = "删除成功";
    protected static final String CREATE_SUC = "创建成功";
    protected static final String MOVE_SUC = "移动成功";

    protected boolean success;
    protected String message;
    protected Object data;
    protected int code = Constants.SUCCESS;//响应码,默认200

    public Result() {
    }

    public Result(boolean success, String message, Object data, int code) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.code = code;
    }
    public Result(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功
     * @param message
     * @return
     */
    public static Result suc(String message) {
        return suc(message, null);
    }

    /**
     * 成功
     * @param data
     * @return
     */
    public static Result suc(Object data) {
        return suc(null, data);
    }

    /**
     * 成功并返回数据
     * @param message
     * @param data
     * @return
     */
    public static Result suc(String message, Object data) {
        return new Result(true, message, data);
    }

    /**
     * 保存成功
     * @return
     */
    public static Result saveSuc() {
        return saveSuc(null);
    }

    /**
     * 保存成功并返回数据
     * @param data
     * @return
     */
    public static Result saveSuc(Object data) {
        return suc(SAVE_SUC, data);
    }

    /**
     * 创建成功
     * @return
     */
    public static Result createSuc() {
        return suc(CREATE_SUC);
    }

    /**
     * 删除成功
     * @return
     */
    public static Result delSuc() {
        return suc(DEL_SUC);
    }

    /**
     * 删除成功并返回数据
     * @param data
     * @return
     */
    public static Result delSuc(Object data) {
        return suc(DEL_SUC, data);
    }

    /**
     * 错误
     * @param message
     * @return
     */
    public static Result error(String message) {
        return new Result(false, message, null);
    }

    /**
     * 错误
     * @param message
     * @param data
     * @return
     */
    public static Result error(String message, Object data) {
        return new Result(false, message, data);
    }
    
    /**
     * 错误，带错误码
     * @param success
     * @param message
     * @param data
     * @param code
     * @return
     */
    public static Result error(boolean success, String message, Object data, int code){
        return new Result(success, message, data, code);
    }
    /**
     * 移动成功并返回数据
     * @param data
     * @return
     */
    public static Result moveSuc(Object data) {
        return suc(MOVE_SUC, data);
    }
}
