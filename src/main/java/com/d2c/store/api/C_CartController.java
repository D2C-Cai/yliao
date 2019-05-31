package com.d2c.store.api;

import com.baomidou.mybatisplus.extension.api.R;
import com.d2c.store.api.base.BaseController;
import com.d2c.store.common.api.Asserts;
import com.d2c.store.common.api.Response;
import com.d2c.store.common.api.ResultCode;
import com.d2c.store.common.utils.QueryUtil;
import com.d2c.store.modules.member.model.MemberDO;
import com.d2c.store.modules.order.model.CartItemDO;
import com.d2c.store.modules.order.query.CartItemQuery;
import com.d2c.store.modules.order.service.CartItemService;
import com.d2c.store.modules.product.model.ProductDO;
import com.d2c.store.modules.product.model.ProductSkuDO;
import com.d2c.store.modules.product.service.ProductService;
import com.d2c.store.modules.product.service.ProductSkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Cai
 */
@Api(description = "购物车业务")
@RestController
@RequestMapping("/api/cart")
public class C_CartController extends BaseController {

    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSkuService productSkuService;

    @ApiOperation(value = "列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public R<List<CartItemDO>> list() {
        MemberDO member = loginMemberHolder.getLoginMember();
        CartItemQuery query = new CartItemQuery();
        query.setMemberId(member.getId());
        query.setP2pId(member.getAccountInfo().getP2pId());
        List<CartItemDO> list = (List<CartItemDO>) cartItemService.list(QueryUtil.buildWrapper(query));
        List<Long> skuIds = new ArrayList<>();
        Map<Long, CartItemDO> map = new ConcurrentHashMap<>();
        for (CartItemDO cartItem : list) {
            skuIds.add(cartItem.getProductSkuId());
            map.put(cartItem.getProductSkuId(), cartItem);
        }
        if (skuIds.size() == 0) return Response.restResult(list, ResultCode.SUCCESS);
        List<ProductSkuDO> skuList = (List<ProductSkuDO>) productSkuService.listByIds(skuIds);
        for (ProductSkuDO productSku : skuList) {
            if (map.get(productSku.getId()) != null) {
                map.get(productSku.getId()).setRealPrice(productSku.getSellPrice());
                map.get(productSku.getId()).setStock(productSku.getStock());
                map.get(productSku.getId()).setFreight(productSku.getFreight());
                map.get(productSku.getId()).setStatus(productSku.getStatus());
            }
        }
        return Response.restResult(list, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "新增")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public R insert(Long skuId, Integer quantity) {
        MemberDO member = loginMemberHolder.getLoginMember();
        Asserts.gt(quantity, 0, "数量必须大于0");
        ProductSkuDO sku = productSkuService.getById(skuId);
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, sku);
        CartItemQuery query = new CartItemQuery();
        query.setMemberId(member.getId());
        query.setP2pId(member.getAccountInfo().getP2pId());
        query.setProductSkuId(skuId);
        CartItemDO cartItem = cartItemService.getOne(QueryUtil.buildWrapper(query));
        if (cartItem == null) {
            Asserts.ge(sku.getStock(), quantity, sku.getId() + "的SKU库存不足");
            ProductDO product = productService.getById(sku.getProductId());
            CartItemDO entity = new CartItemDO();
            entity.setP2pId(member.getAccountInfo().getP2pId());
            entity.setMemberId(member.getId());
            entity.setMemberAccount(member.getAccount());
            entity.setProductId(sku.getProductId());
            entity.setProductSkuId(sku.getId());
            entity.setQuantity(quantity);
            entity.setStandard(sku.getStandard());
            entity.setProductName(product.getName());
            entity.setProductPic(product.getFirstPic());
            entity.setProductPrice(product.getPrice());
            cartItemService.save(entity);
        } else {
            Asserts.ge(sku.getStock(), cartItem.getQuantity() + quantity, sku.getId() + "的SKU库存不足");
            CartItemDO entity = new CartItemDO();
            entity.setQuantity(cartItem.getQuantity() + quantity);
            entity.setId(cartItem.getId());
            cartItemService.updateById(entity);
        }
        return Response.restResult(null, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R update(Long id, Integer quantity) {
        Asserts.gt(quantity, 0, "数量必须大于0");
        CartItemDO cartItem = cartItemService.getById(id);
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, cartItem);
        Asserts.eq(cartItem.getMemberId(), loginMemberHolder.getLoginId(), "您不是本人");
        ProductSkuDO sku = productSkuService.getById(cartItem.getProductSkuId());
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, sku);
        Asserts.ge(sku.getStock(), quantity, sku.getId() + "的SKU库存不足");
        CartItemDO entity = new CartItemDO();
        entity.setId(cartItem.getId());
        entity.setQuantity(quantity);
        cartItemService.updateById(entity);
        return Response.restResult(null, ResultCode.SUCCESS);
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public R delete(Long[] ids) {
        CartItemDO cartItem = cartItemService.getById(ids[0]);
        Asserts.notNull(ResultCode.RESPONSE_DATA_NULL, cartItem);
        Asserts.eq(cartItem.getMemberId(), loginMemberHolder.getLoginId(), "您不是本人");
        cartItemService.removeByIds(Arrays.asList(ids));
        return Response.restResult(null, ResultCode.SUCCESS);
    }

}

