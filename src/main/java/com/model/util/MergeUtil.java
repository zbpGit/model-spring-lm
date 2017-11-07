//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.model.util;

import com.jfinal.plugin.activerecord.Record;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;

public class MergeUtil {
    public MergeUtil() {
    }

    public static String resizeImage(String srcImgPath, String distImgPath, int width, int height) throws IOException {
        File srcFile = new File(srcImgPath);
        Image srcImg = ImageIO.read(srcFile);
        BufferedImage buffImg = null;
        buffImg = new BufferedImage(width, height, 1);
        buffImg.getGraphics().drawImage(srcImg.getScaledInstance(width, height, 4), 0, 0, (ImageObserver)null);
        ImageIO.write(buffImg, "JPEG", new File(distImgPath));
        return distImgPath;
    }

    public static final String overlapImage(String bigPath, String smallPath, String outFile, int x, int y) {
        try {
            BufferedImage big = ImageIO.read(new File(bigPath));
            BufferedImage small = ImageIO.read(new File(smallPath));
            Graphics2D g = big.createGraphics();
            g.drawImage(small, x, y, small.getWidth(), small.getHeight(), (ImageObserver)null);
            g.dispose();
            ImageIO.write(big, "jpg", new File(outFile));
            return outFile;
        } catch (Exception var8) {
            throw new RuntimeException(var8);
        }
    }

    public static int[] integers(Integer type, String i) {
        int[] j = new int[4];
        byte var4;
        if(type.intValue() == 5) {
            var4 = -1;
            switch(i.hashCode()) {
                case 48:
                    if(i.equals("0")) {
                        var4 = 0;
                    }
                    break;
                case 49:
                    if(i.equals("1")) {
                        var4 = 1;
                    }
                    break;
                case 50:
                    if(i.equals("2")) {
                        var4 = 3;
                    }
                    break;
                case 51:
                    if(i.equals("3")) {
                        var4 = 2;
                    }
                    break;
                case 52:
                    if(i.equals("4")) {
                        var4 = 4;
                    }
            }

            switch(var4) {
                case 0:
                    j[0] = 3;
                    j[1] = 3;
                    break;
                case 1:
                    j[0] = 3;
                    j[1] = 1254;
                    break;
                case 2:
                    j[0] = 3;
                    j[1] = 2151;
                    break;
                case 3:
                    j[0] = 630;
                    j[1] = 1254;
                    break;
                case 4:
                    j[0] = 630;
                    j[1] = 2151;
            }

            return j;
        } else if(type.intValue() == 7) {
            var4 = -1;
            switch(i.hashCode()) {
                case 48:
                    if(i.equals("0")) {
                        var4 = 0;
                    }
                    break;
                case 49:
                    if(i.equals("1")) {
                        var4 = 1;
                    }
                    break;
                case 50:
                    if(i.equals("2")) {
                        var4 = 2;
                    }
                    break;
                case 51:
                    if(i.equals("3")) {
                        var4 = 3;
                    }
                    break;
                case 52:
                    if(i.equals("4")) {
                        var4 = 4;
                    }
                    break;
                case 53:
                    if(i.equals("5")) {
                        var4 = 5;
                    }
                    break;
                case 54:
                    if(i.equals("6")) {
                        var4 = 6;
                    }
            }

            switch(var4) {
                case 0:
                    j[0] = 3;
                    j[1] = 3;
                    break;
                case 1:
                    j[0] = 3;
                    j[1] = 903;
                    break;
                case 2:
                    j[0] = 3;
                    j[1] = 1350;
                    break;
                case 3:
                    j[0] = 630;
                    j[1] = 903;
                    break;
                case 4:
                    j[0] = 630;
                    j[1] = 1350;
                    break;
                case 5:
                    j[0] = 3;
                    j[1] = 1800;
                    break;
                case 6:
                    j[0] = 630;
                    j[1] = 1800;
            }

            return j;
        } else if(type.intValue() == 9) {
            var4 = -1;
            switch(i.hashCode()) {
                case 48:
                    if(i.equals("0")) {
                        var4 = 0;
                    }
                    break;
                case 49:
                    if(i.equals("1")) {
                        var4 = 1;
                    }
                    break;
                case 50:
                    if(i.equals("2")) {
                        var4 = 2;
                    }
                    break;
                case 51:
                    if(i.equals("3")) {
                        var4 = 3;
                    }
                    break;
                case 52:
                    if(i.equals("4")) {
                        var4 = 4;
                    }
                    break;
                case 53:
                    if(i.equals("5")) {
                        var4 = 5;
                    }
                    break;
                case 54:
                    if(i.equals("6")) {
                        var4 = 6;
                    }
                    break;
                case 55:
                    if(i.equals("7")) {
                        var4 = 7;
                    }
                    break;
                case 56:
                    if(i.equals("8")) {
                        var4 = 8;
                    }
            }

            switch(var4) {
                case 0:
                    j[0] = 3;
                    j[1] = 354;
                    break;
                case 1:
                    j[0] = 3;
                    j[1] = 1254;
                    break;
                case 2:
                    j[1] = 1701;
                    j[0] = 3;
                    break;
                case 3:
                    j[1] = 1254;
                    j[0] = 630;
                    break;
                case 4:
                    j[1] = 1701;
                    j[0] = 630;
                    break;
                case 5:
                    j[1] = 2151;
                    j[0] = 3;
                    break;
                case 6:
                    j[1] = 2598;
                    j[0] = 3;
                    break;
                case 7:
                    j[1] = 2151;
                    j[0] = 630;
                    break;
                case 8:
                    j[1] = 2598;
                    j[0] = 630;
            }

            return j;
        } else {
            var4 = -1;
            switch(i.hashCode()) {
                case 48:
                    if(i.equals("0")) {
                        var4 = 0;
                    }
                    break;
                case 49:
                    if(i.equals("1")) {
                        var4 = 1;
                    }
                    break;
                case 50:
                    if(i.equals("2")) {
                        var4 = 2;
                    }
                    break;
                case 51:
                    if(i.equals("3")) {
                        var4 = 3;
                    }
                    break;
                case 52:
                    if(i.equals("4")) {
                        var4 = 4;
                    }
                    break;
                case 53:
                    if(i.equals("5")) {
                        var4 = 5;
                    }
                    break;
                case 54:
                    if(i.equals("6")) {
                        var4 = 6;
                    }
                    break;
                case 55:
                    if(i.equals("7")) {
                        var4 = 7;
                    }
                    break;
                case 56:
                    if(i.equals("8")) {
                        var4 = 8;
                    }
                    break;
                case 57:
                    if(i.equals("9")) {
                        var4 = 9;
                    }
                    break;
                case 1567:
                    if(i.equals("10")) {
                        var4 = 10;
                    }
            }

            switch(var4) {
                case 0:
                    j[1] = 3;
                    j[0] = 129;
                    break;
                case 1:
                    j[1] = 999;
                    j[0] = 3;
                    break;
                case 2:
                    j[1] = 1446;
                    j[0] = 3;
                    break;
                case 3:
                    j[1] = 999;
                    j[0] = 630;
                    break;
                case 4:
                    j[1] = 1446;
                    j[0] = 630;
                    break;
                case 5:
                    j[1] = 1890;
                    j[0] = 3;
                    break;
                case 6:
                    j[1] = 2343;
                    j[0] = 3;
                    break;
                case 7:
                    j[0] = 420;
                    j[1] = 1890;
                    break;
                case 8:
                    j[0] = 420;
                    j[1] = 2343;
                    break;
                case 9:
                    j[0] = 840;
                    j[1] = 1896;
                    break;
                case 10:
                    j[0] = 840;
                    j[1] = 2343;
            }

            return j;
        }
    }

    public static String url(Integer type) {
        String utl = null;
        if(type.intValue() == 5) {
            utl = "/home/java/apache-tomcat-8.0.43/webapps/model-spring-lm/Files/workMk/5.jpg";
        } else if(type.intValue() == 7) {
            utl = "/home/java/apache-tomcat-8.0.43/webapps/model-spring-lm/Files/workMk/7.jpg";
        } else if(type.intValue() == 9) {
            utl = "/home/java/apache-tomcat-8.0.43/webapps/model-spring-lm/Files/workMk/9.jpg";
        } else if(type.intValue() == 11) {
            utl = "/home/java/apache-tomcat-8.0.43/webapps/model-spring-lm/Files/workMk/11.jpg";
        }

        return utl;
    }

    public static String random(Integer j) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < j.intValue(); ++i) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }

        return sb.toString();
    }

    public static String[] montage(List<Record> mookes, Integer type, String path) {
        String outFile = null;
        String random = random(Integer.valueOf(10));
        Map<String, String> map = new TreeMap();
        Iterator it = mookes.iterator();

        while(it.hasNext()) {
            Record mooke = (Record)it.next();
            map.put(String.valueOf(mooke.get("subscript")), (String) mooke.get("path"));
        }

        it = map.entrySet().iterator();

        while(it.hasNext()) {
            Entry<String, String> entry = (Entry)it.next();
            String key = (String)entry.getKey();
            String value = (String)entry.getValue();
            int[] integers = integers(type, key);
            String url = url(type);
            if(outFile == null) {
                outFile = overlapImage(url, value, path + "/" + random + ".jpg", integers[1], integers[0]);
            } else {
                outFile = overlapImage(outFile, value, path + "/" + random + ".jpg", integers[1], integers[0]);
            }
        }

        String[] price = new String[]{outFile, "/model-spring-lm/Files/mooke/" + random + ".jpg"};
        System.out.println(price[1]);
        return price;
    }

    public static String ThreadMK(List<Record> mookes, Integer type, String path, String name, String height, String weight, String modelBust, String modelWaist, String modelHips, String shoes) {
        String[] price = null;
        FileRunnableUtil fileTest = new FileRunnableUtil(mookes, type, path);
        Thread thread = new Thread(fileTest);
        thread.start();

        while(thread.isAlive()) {
            ;
        }

        price = fileTest.getPrice();
        watermark(type, price[0], name, height, weight, modelBust, modelWaist, modelHips, shoes);
        return price[1];
    }

    public static String watermark(Integer type, String result, String name, String height, String weight, String Bust, String Waist, String Hips, String shoes) {
        CharacterUtil tt;
        BufferedImage d;
        if(type.intValue() == 5) {
            tt = new CharacterUtil();
            d = tt.loadImageLocal(result);
            System.out.println(name);
            tt.writeImageLocal(result, tt.modifyImage(d, name, 933, 201));
            tt.writeImageLocal(result, tt.modifyImage(d, height + "cm", 933, 378));
            tt.writeImageLocal(result, tt.modifyImage(d, weight + "kg", 933, 522));
            tt.writeImageLocal(result, tt.modifyImage(d, Bust, 933, 663));
            tt.writeImageLocal(result, tt.modifyImage(d, Waist, 933, 807));
            tt.writeImageLocal(result, tt.modifyImage(d, Hips, 933, 954));
            tt.writeImageLocal(result, tt.modifyImage(d, shoes, 933, 1104));
        } else if(type.intValue() == 7) {
            tt = new CharacterUtil();
            d = tt.loadImageLocal(result);
            tt.writeImageLocal(result, tt.modifyImage(d, name, 273, 1395));
            tt.writeImageLocal(result, tt.modifyImage(d, height + "cm", 537, 1395));
            tt.writeImageLocal(result, tt.modifyImage(d, weight + "kg", 888, 1395));
            tt.writeImageLocal(result, tt.modifyImage(d, Bust, 1239, 1395));
            tt.writeImageLocal(result, tt.modifyImage(d, Waist, 1539, 1395));
            tt.writeImageLocal(result, tt.modifyImage(d, Hips, 1881, 1395));
            tt.writeImageLocal(result, tt.modifyImage(d, shoes, 2196, 1395));
        } else if(type.intValue() == 9) {
            tt = new CharacterUtil();
            d = tt.loadImageLocal(result);
            tt.writeImageLocal(result, tt.modifyImage(d, name, 33, 213));
            tt.writeImageLocal(result, tt.modifyImage(d, height + "cm", 33, 372));
            tt.writeImageLocal(result, tt.modifyImage(d, weight + "kg", 33, 510));
            tt.writeImageLocal(result, tt.modifyImage(d, Bust, 33, 654));
            tt.writeImageLocal(result, tt.modifyImage(d, Waist, 33, 798));
            tt.writeImageLocal(result, tt.modifyImage(d, Hips, 33, 942));
            tt.writeImageLocal(result, tt.modifyImage(d, shoes, 33, 1086));
        } else if(type.intValue() == 11) {
            tt = new CharacterUtil();
            d = tt.loadImageLocal(result);
            tt.writeImageLocal(result, tt.modifyImage(d, name, 450, 81));
            tt.writeImageLocal(result, tt.modifyImage(d, height + "cm", 51, 1227));
            tt.writeImageLocal(result, tt.modifyImage(d, weight + "kg", 231, 1227));
            tt.writeImageLocal(result, tt.modifyImage(d, Bust, 411, 1227));
            tt.writeImageLocal(result, tt.modifyImage(d, Waist, 552, 1227));
            tt.writeImageLocal(result, tt.modifyImage(d, Hips, 720, 1227));
            tt.writeImageLocal(result, tt.modifyImage(d, shoes, 840, 1227));
        }

        return result;
    }

    public static String reduceImg(String imgsrc, String imgdist, Float rate) {
        try {
            File srcfile = new File(imgsrc);
            int[] wh = getImgWidth(srcfile);
            if(!srcfile.exists()) {
                return null;
            }

            if(rate != null && rate.floatValue() > 0.0F) {
                int[] results = getImgWidth(srcfile);
                if(results == null || results[0] == 0 || results[1] == 0) {
                    return null;
                }

                wh[0] = (int)((float)results[0] * rate.floatValue());
                wh[1] = (int)((float)results[1] * rate.floatValue());
            }

            Image src = ImageIO.read(srcfile);
            BufferedImage tag = new BufferedImage(wh[0], wh[1], 1);
            tag.getGraphics().drawImage(src.getScaledInstance(wh[0], wh[1], 4), 0, 0, (ImageObserver)null);
            FileOutputStream out = new FileOutputStream(imgdist);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(tag);
            out.close();
        } catch (IOException var9) {
            var9.printStackTrace();
        }

        return imgdist;
    }

    public static int[] getImgWidth(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int[] result = new int[]{0, 0};

        try {
            is = new FileInputStream(file);
            src = ImageIO.read(is);
            result[0] = src.getWidth((ImageObserver)null);
            result[1] = src.getHeight((ImageObserver)null);
            is.close();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return result;
    }

    @Test
    public void wh() {
        String url = "D:\\Files\\876837988442460384.png";
        System.out.println(reduceImg(url, url, (Float)null));
    }
}
