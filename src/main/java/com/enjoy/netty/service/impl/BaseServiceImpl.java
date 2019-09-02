package com.enjoy.netty.service.impl;

import com.enjoy.netty.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class BaseServiceImpl implements BaseService {
    @Override
    public void test() {
        System.out.println("aaa");
    }
}
