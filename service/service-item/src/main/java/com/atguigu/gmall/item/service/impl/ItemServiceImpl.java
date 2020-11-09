package com.atguigu.gmall.item.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.item.service.ItemService;
import com.atguigu.gmall.model.product.BaseCategoryView;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.product.client.ProductFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public Map<String, Object> getBySkuId(Long skuId) {
        Map<String, Object> result = new HashMap<>();

        // 通过skuId 查询skuInfo
        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
        // 销售属性-销售属性值回显并锁定
        List<SpuSaleAttr> spuSaleAttrList =  productFeignClient.getSpuSaleAttrListCheckBySku(skuInfo.getId(), skuInfo.getSpuId());

        //根据spuId 查询map 集合属性
        Map skuValueIdsMap =  productFeignClient.getSkuValueIdsMap(skuInfo.getSpuId());

        //获取商品最新价格
        BigDecimal skuPrice = productFeignClient.getSkuPrice(skuId);
        //获取商品分类
        BaseCategoryView categoryView = productFeignClient.getCategoryView(skuInfo.getCategory3Id());
//保存商品分类数据
        result.put("categoryView",categoryView);

        // 保存 json字符串
        String valuesSkuJson = JSON.toJSONString(skuValueIdsMap);
        // 获取价格
        result.put("price",skuPrice);
        // 保存valuesSkuJson
        result.put("valuesSkuJson",valuesSkuJson);
        // 保存数据
        result.put("spuSaleAttrList",spuSaleAttrList);
        // 保存skuInfo
        result.put("skuInfo",skuInfo);
        return result;
    }
}
