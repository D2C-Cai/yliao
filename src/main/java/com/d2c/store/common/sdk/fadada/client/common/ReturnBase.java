/**
 * package：com.fadada.sample.client.common
 * filename：com.fadada.sample.client.common
 *
 * @auth：PF0RACMN
 * @date：2017年12月5日 CopyRight 2017 ShenZhen Fabigbig Technology Co.Ltd All Rights Reserved
 */
package com.d2c.store.common.sdk.fadada.client.common;

/**
 * <h3>概要:</h3>
 * TODO(请在此处填写概要)
 * <br>
 * <h3>功能:</h3>
 * <ol>
 * <li>TODO(这里用一句话描述功能点)</li>
 * </ol>
 * <h3>履历:</h3>
 * <ol>
 * <li>2017年12月5日[PF0RACMN] 新建</li>
 * </ol>
 */
public class ReturnBase {

    public static final ReturnBase SUCCESS = new ReturnBase(1, "success");
    private int code;
    private String msg;

    public ReturnBase(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ReturnBase() {
        super();
    }

    /**
     * TODO 判断结果是否成功
     *
     * @return
     * @author: PF0RACMN
     * @date: 2017年12月5日
     */
    public boolean checkSuccess() {
        return this.code == SUCCESS.getCode();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
