package com.atguigu.gmall.product.service;

import org.springframework.stereotype.Service;

@Service
public interface TestServcie {
    public void testLock();

    String readLock();

    String writeLock();
}
