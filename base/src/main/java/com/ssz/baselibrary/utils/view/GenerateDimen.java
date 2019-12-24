package com.ssz.baselibrary.utils.view;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * @author : zsp
 * time : 2019 07 2019/7/2 17:08
 * 生成不同分辨率下的demen文件
 */
public class GenerateDimen {

    public static void genDimen() {
        StringBuilder swdef = new StringBuilder();
        PrintWriter out = null;
        try {
            swdef.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<resources>");
            double value;
            for (int i = 0; i < 700; i++) {
                //这里控制对应转换的值，如果是标准尺寸就一对一转换
                value = (i + 1) * 1024/800;
                value = Math.round(value * 100) / 100;
                swdef.append("<dimen name=\"dp" + (i + 1) + "\">" + value + "dp</dimen>\r\n");
            }
            swdef.append("</resources>");
            //这里是文件名，1 注意修改 sw 后面的值，和转换值一一对应  2 文件夹和文件要先创建好否则要代码创建
            String filedef = "./app/src/main/res/values-w1024dp/dimen.xml";
            out = new PrintWriter(new BufferedWriter(new FileWriter(filedef)));
            out.println(swdef.toString());
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != out)out.close();
        }
    }

    public static void main(String[] args) {
        genDimen();
    }
}
