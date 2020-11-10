package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.product.service.TestServcie;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "测试接口")
@RestController
@RequestMapping("admin/product/test")
public class TestController {

    @Autowired
    private TestServcie testServcie;
    @GetMapping("testLock")
    public Result testLock(){
        testServcie.testLock();
        return Result.ok();
    }

    @GetMapping("read")
    public Result<String> read(){
        String msg = testServcie.readLock();
        return Result.ok(msg);
    }

    @GetMapping("write")
    public Result<String> write(){
        String msg = testServcie.writeLock();
        return Result.ok(msg);
    }

}
