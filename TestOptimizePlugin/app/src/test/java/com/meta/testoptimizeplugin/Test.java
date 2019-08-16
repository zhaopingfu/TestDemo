package com.meta.testoptimizeplugin;

import com.tinify.Source;
import com.tinify.Tinify;

import java.io.File;
import java.io.IOException;

/**
 * @author zhaopf
 * @date 2019-08-16
 */
public class Test {

    @org.junit.Test
    public void test2() {
        long tempLength = 29_104;
        long length = 78_301;
        long cha = length - tempLength;
        String format = String.format("%.2f%%", (float) cha / length * 100F);
        System.out.println(format);
    }

    @org.junit.Test
    public void test() {
        //  File file = new File("C:/Users/metaapp/Desktop/a/k.png");
        File file = new File("C:/Users/metaapp/Desktop/a/f.jpeg");
        File tempFile = new File(getTempFileName(file));

        System.out.println(file.getAbsolutePath());
        System.out.println(tempFile.getAbsolutePath());

        try {
            Tinify.setKey("47tGYp7yc759xPLJtByP4DdJY4f98vqM");
            Source source = Tinify.fromFile(file.getAbsolutePath());
            source.toFile(tempFile.getAbsolutePath());
            if (tempFile.length() < file.length()) {
                long cha = file.length() - tempFile.length();
                double percent = (double) cha / file.length() * 100F;
                String format = String.format("%.2f%%", percent);
                System.out.println("压缩成功, 压了 " + format);
            } else {
                System.out.println("压缩之后大小: " + tempFile.length() + "  压缩之前大小: " + file.length());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getTempFileName(File file) {
        String str = file.getAbsolutePath();
        // 根据最后一个 / 分成两段
        int lastIndexOf = str.lastIndexOf('\\');
        String dirName = str.substring(0, lastIndexOf);
        String fileName = str.substring(lastIndexOf + 1);

        // 把后半段根据 . 分成两段
        int pointIndex = fileName.lastIndexOf(".");
        String fileNameBefore = fileName.substring(0, pointIndex);
        String fileNameAfter = fileName.substring(pointIndex + 1);

        // 拼接新的文件名
        return dirName + "/temp_" + fileNameBefore + "." + fileNameAfter;
    }
}