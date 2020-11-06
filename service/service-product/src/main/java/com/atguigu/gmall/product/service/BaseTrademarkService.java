package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.product.BaseTrademark;
import com.atguigu.gmall.model.product.SpuInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

@Service
public interface BaseTrademarkService extends IService<BaseTrademark> {
    //查询所有品牌  分页
    IPage<BaseTrademark> getPage(Page<BaseTrademark> page);

    //void saveBaseTrademark(BaseTrademark baseTrademark);


}
