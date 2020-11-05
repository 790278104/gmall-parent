package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseTrademark;
import com.atguigu.gmall.product.service.BaseTrademarkService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/product/baseTrademark")
public class BaseTrademarkController {
    @Autowired
    private BaseTrademarkService baseTrademarkService;

    @GetMapping("{page}/{limit}")
    public Result getbaseTrademarkList(@PathVariable Long page,
                                       @PathVariable Long limit){
        Page<BaseTrademark> baseTrademarkPage = new Page<>(page,limit);
        IPage<BaseTrademark> pageList = baseTrademarkService.getPage(baseTrademarkPage);
        return Result.ok(pageList);
    }


    @PostMapping("save")
    public Result saveBaseTrademark(@RequestBody BaseTrademark baseTrademark){
        baseTrademarkService.save(baseTrademark);
        return Result.ok();
    }

    @PutMapping("update")
    public Result updateBaseTrademark(@RequestBody BaseTrademark baseTrademark){
        baseTrademarkService.updateById(baseTrademark);
        return Result.ok();
    }

    @DeleteMapping("remove/{id}")
    public Result removeBaseTrademark(@PathVariable Long id){
        baseTrademarkService.removeById(id);
        return Result.ok();
    }

    @GetMapping("get/{id}")
    public Result getBaseTrademark(@PathVariable Long id){
        return Result.ok(baseTrademarkService.getById(id));
    }
}
