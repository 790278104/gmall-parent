package com.atguigu.gmall.item.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface ItemService {
    //获取sku详情信息
    Map<String, Object> getBySkuId(Long skuId);
}
