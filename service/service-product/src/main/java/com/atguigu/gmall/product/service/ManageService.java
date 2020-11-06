package com.atguigu.gmall.product.service;

import com.atguigu.gmall.model.product.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ManageService {
    //查询所有一级分类数据
    List<BaseCategory1> getCategory1();

    //根据一级分类id  查询二级分类数据
    List<BaseCategory2> getCategory2(Long category1Id);

    //根据二级分类id  查询三级分类数据
    List<BaseCategory3> getCategory3(Long category2Id);

    //获取平台属性数据集合
    List<BaseAttrInfo> getAttrIofoList(Long category1Id,
                                       Long category2Id,
                                       Long category3Id);
    //保存平台属性数据
    void saveAttrInfo(BaseAttrInfo baseAttrInfo);

    //根据属性ID查询属性数据
    List<BaseAttrValue> getAttrValueList(Long attrId);

    //根据平台属性Id 查询平台属性对象
    BaseAttrInfo getBaseAttrInfo(Long attrId);

    //分页查询商品表
    IPage<SpuInfo> getSpuInfoPage(Page<SpuInfo> pageParam, SpuInfo spuInfo);

    //查询所有的销售属性数据
    List<BaseSaleAttr> getBaseSaleAttrList();
    //保存商品数据
    void saveSpuInfo(SpuInfo spuInfo);
    //根据spuId 查询spuImageList
    List<SpuImage> getSpuImageList(Long spuId);


    List<SpuSaleAttr> getSpuSaleAttrList(Long spuId);
    //保存skuInfo
    void saveSkuInfo(SkuInfo skuInfo);

    //分页查询skuInfo列表
    IPage<SkuInfo> getPage(Page<SkuInfo> page);

    //商品上架
    void onSale(Long skuId);
    //商品下架
    void cancelSale(Long skuId);
}
