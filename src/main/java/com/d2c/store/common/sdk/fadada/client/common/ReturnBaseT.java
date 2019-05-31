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
public class ReturnBaseT<T> extends ReturnBase {

    private T data;

    public ReturnBaseT(int code, String msg, T data) {
        super(code, msg);
        this.data = data;
    }

    public ReturnBaseT(ReturnBase base, T data) {
        super(base.getCode(), base.getMsg());
        this.data = data;
    }

    public ReturnBaseT() {
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
