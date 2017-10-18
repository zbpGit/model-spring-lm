package com.model.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;


public class DloadImgUtil {
    /**
     * 根据内容类型判断文件扩展名
     *
     * @param contentType 内容类型
     * @return
     */
    public static String getFileexpandedName(String contentType) {
        String fileEndWitsh = ".jpg";
       /* if ("image/jpeg".equals(contentType))
            fileEndWitsh = ".jpg";
        else if ("audio/mpeg".equals(contentType))
            fileEndWitsh = ".mp3";
        else if ("audio/amr".equals(contentType))
            fileEndWitsh = ".amr";
        else if ("video/mp4".equals(contentType))
            fileEndWitsh = ".mp4";
        else if ("video/mpeg4".equals(contentType))
            fileEndWitsh = ".mp4";*/
        return fileEndWitsh;
    }

    /**
     * 获取媒体文件
     * @param
     * @param mediaId 媒体文件id
     * @param savePath 文件在本地服务器上的存储路径
     * */
    public static String downloadMedia(String jsapi_ticket,String mediaId, String savePath,String sava) {
        String filePath = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=" +jsapi_ticket+
                "&media_id="+mediaId;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            if (!savePath.endsWith("/")) {
                savePath += "/";
            }
            // 根据内容类型获取扩展名
            String fileExt = DloadImgUtil.getFileexpandedName(conn.getHeaderField("Content-Type"));
            //随机生成图片id
            String file =getRandomStringByLength();
            /*filePath = savePath + file + fileExt;*/
            filePath = savePath + file+fileExt;
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            FileOutputStream fos = new FileOutputStream(new File(filePath));
            byte[] buf = new byte[8096];
            int size = 0;
            while ((size = bis.read(buf)) != -1)
                fos.write(buf, 0, size);
            fos.close();
            bis.close();

            conn.disconnect();
            /**
             * 访问路径
             */
            filePath = sava+file+fileExt;
        } catch (Exception e) {
            filePath = null;
        }
        return filePath;
    }


    public static String getRandomStringByLength() {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 15; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

}