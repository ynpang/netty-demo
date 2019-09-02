package com.enjoy.rpc.provider;

import com.enjoy.rpc.api.IRpcHello;

public class RpcHello implements IRpcHello {
    @Override
    public String hello(String name) {
        return "hello," + name;
    }
}
