package com.dengooo.java7;

import java.io.IOException;
/**
 * 自定义实现一个可以使用try-with-resource语法的类
 */
public class MyResource implements AutoCloseable{

    private String name;

    public  MyResource(String name){
        this.name=name;
    }

    @Override
    public void close() throws Exception {
        System.out.println(name+"资源被关闭了");
        throw  new IOException(name+"资源在关闭时发生异常");
    }

    public void openResouse(){
        System.out.println(name+"资源被打开了");
    }
}
