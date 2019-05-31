package com.d2c.store.modules.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.d2c.store.modules.product.model.ProductDO;
import com.d2c.store.modules.product.query.ProductQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author BaiCai
 */
public interface ProductMapper extends BaseMapper<ProductDO> {

    int doDeductStock(@Param("id") Long id, @Param("quantity") Integer quantity);

    int doReturnStock(@Param("id") Long id, @Param("quantity") Integer quantity);

    List<ProductDO> findByQuery(@Param("p2pId") Long p2pId, @Param("query") ProductQuery query, @Param("offset") long offset, @Param("size") long size, @Param("sort") String sort);

    int countByQuery(@Param("p2pId") Long p2pId, @Param("query") ProductQuery query);

    int doUpdateSales(@Param("id") Long id, @Param("quantity") Integer quantity);

}
