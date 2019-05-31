package com.d2c.store.common.api;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BaiCai
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PageModel<T> extends Page<T> {

    // 最大页长
    public static final long MAX_SIZE = 500L;
    //
    @ApiModelProperty(value = "页码")
    private long p;
    @ApiModelProperty(value = "页长")
    private long ps;

    public PageModel() {
        super();
        this.setDesc("create_date");
    }

    public void setP(long p) {
        this.p = p;
        this.setCurrent(p);
    }

    public void setPs(long ps) {
        this.ps = ps;
        this.setSize(ps);
    }

    public String orderDesc(String prefix) {
        if (this.descs() == null || this.descs().length == 0) return null;
        StringBuilder builder = new StringBuilder();
        for (String field : this.descs()) {
            builder.append(" " + prefix + "." + field + " DESC,");
        }
        String orderStr = builder.toString();
        return orderStr.substring(0, orderStr.length() - 1);
    }

    public String orderAsc(String prefix) {
        if (this.ascs() == null || this.ascs().length == 0) return null;
        StringBuilder builder = new StringBuilder();
        for (String field : this.ascs()) {
            builder.append(" " + prefix + "." + field + " ASC,");
        }
        String orderStr = builder.toString();
        return orderStr.substring(0, orderStr.length() - 1);
    }

    public String orderByStr(String prefix) {
        String ascStr = this.orderAsc(prefix);
        String descStr = this.orderDesc(prefix);
        if (StrUtil.isNotBlank(ascStr)) return ascStr;
        if (StrUtil.isNotBlank(descStr)) return descStr;
        return null;
    }

}
