//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.model.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

public class DaseUtil {
    public DaseUtil() {
    }

    public static String generateImage(String imgStr, String path) {
        BASE64Decoder decoder = new BASE64Decoder();

        try {
            byte[] b = decoder.decodeBuffer(imgStr);

            for(int i = 0; i < b.length; ++i) {
                if(b[i] < 0) {
                    b[i] = (byte)(b[i] + 256);
                }
            }

            OutputStream stream = new FileOutputStream(path);
            stream.write(b);
            stream.flush();
            stream.close();
            return MergeUtil.reduceImg(path, path, (Float)null);
        } catch (Exception var5) {
            var5.printStackTrace();
            return "1";
        }
    }

    public static String getImageStr(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;

        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }
}
