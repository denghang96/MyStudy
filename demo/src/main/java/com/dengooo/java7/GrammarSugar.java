package com.dengooo.java7;

import java.io.*;

/**
 * try with resource
 */
public class GrammarSugar {
    /**
     * 传统方法拷贝文件
     */
    public void traditionalMethod() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream("C:\\Users\\denghang\\Desktop\\a.txt");
            outputStream = new FileOutputStream("C:\\Users\\denghang\\Desktop\\b.txt");
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
//            int i = 1/0;  //try语句块与finally同时出现异常时，只有finally的异常会被抛出
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭输入输出流
            if (outputStream != null) {
                try {
//                    outputStream = null;
                    outputStream.close();
                } catch (IOException ioE) {
                    //关闭资源时发生异常
                    ioE.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ioE) {
                    //关闭资源时发生异常
                    ioE.printStackTrace();
                }
            }
        }
    }

    /**
     * java7语法糖 try-with-resource
     * 该语法糖适用于实现了java.lang.AutoCloseable 或者 java.io.Closeable的资源类
     */
    public void tryWithResource() {
        try(OutputStream outputStream = new FileOutputStream("C:\\Users\\denghang\\Desktop\\b.txt");
            InputStream inputStream = new FileInputStream("C:\\Users\\denghang\\Desktop\\a.txt")) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试自定义resource类
     */
    public void testMyResource() {
        try (MyResource myResouce = new MyResource("资源1");) { //FILO
            myResouce.openResouse();
            System.out.println("=======处理了一大堆事情=======");
            Thread.sleep(2000);
            System.out.println("=======事情处理完了=======");

            throw new Exception("业务处理异常了");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GrammarSugar grammarSugar = new GrammarSugar();
//        grammarSugar.traditionalMethod();
//        grammarSugar.tryWithResource();
        grammarSugar.testMyResource();
    }
}
