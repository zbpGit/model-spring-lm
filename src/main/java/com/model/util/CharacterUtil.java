//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.model.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class CharacterUtil {
    private Font font = new Font("simhei", 1, 50);
    private Graphics2D g = null;
    private int fontsize = 0;
    private int x = 0;
    private int y = 0;

    public CharacterUtil() {
    }

    public BufferedImage loadImageLocal(String imgName) {
        try {
            return ImageIO.read(new File(imgName));
        } catch (IOException var3) {
            System.out.println(var3.getMessage());
            return null;
        }
    }

    public BufferedImage loadImageUrl(String imgName) {
        try {
            URL url = new URL(imgName);
            return ImageIO.read(url);
        } catch (IOException var3) {
            System.out.println(var3.getMessage());
            return null;
        }
    }

    public void writeImageLocal(String newImage, BufferedImage img) {
        if(newImage != null && img != null) {
            try {
                File outputfile = new File(newImage);
                ImageIO.write(img, "jpg", outputfile);
            } catch (IOException var4) {
                System.out.println(var4.getMessage());
            }
        }

    }

    public void setFont(String fontStyle, int fontSize) {
        this.fontsize = fontSize;
        this.font = new Font(fontStyle, 0, fontSize);
    }

    public BufferedImage modifyImage(BufferedImage img, String content, int x, int y) {
        try {
            int w = img.getWidth();
            int h = img.getHeight();
            this.g = img.createGraphics();
            this.g.setBackground(Color.WHITE);
            this.g.setColor(Color.WHITE);
            if(this.font != null) {
                this.g.setFont(this.font);
            }

            if(x < w && y < h) {
                this.x = x;
                this.y = y;
            } else {
                this.x = h - this.fontsize + 2;
                this.y = w;
            }

            if(content != null) {
                this.g.drawString(content, this.x, this.y);
            }

            this.g.dispose();
        } catch (Exception var7) {
            System.out.println(var7.getMessage());
        }

        return img;
    }

    public BufferedImage modifyImage(BufferedImage img, Object[] contentArr, int x, int y, boolean xory) {
        try {
            int w = img.getWidth();
            int h = img.getHeight();
            this.g = img.createGraphics();
            this.g.setBackground(Color.WHITE);
            this.g.setColor(Color.RED);
            if(this.font != null) {
                this.g.setFont(this.font);
            }

            if(x < h && y < w) {
                this.x = x;
                this.y = y;
            } else {
                this.x = h - this.fontsize + 2;
                this.y = w;
            }

            if(contentArr != null) {
                int arrlen = contentArr.length;
                int i;
                if(xory) {
                    for(i = 0; i < arrlen; ++i) {
                        this.g.drawString(contentArr[i].toString(), this.x, this.y);
                        this.x += contentArr[i].toString().length() * this.fontsize / 2 + 5;
                    }
                } else {
                    for(i = 0; i < arrlen; ++i) {
                        this.g.drawString(contentArr[i].toString(), this.x, this.y);
                        this.y += this.fontsize + 2;
                    }
                }
            }

            this.g.dispose();
        } catch (Exception var10) {
            System.out.println(var10.getMessage());
        }

        return img;
    }

    public BufferedImage modifyImageYe(BufferedImage img) {
        try {
            int w = img.getWidth();
            int h = img.getHeight();
            this.g = img.createGraphics();
            this.g.setBackground(Color.WHITE);
            this.g.setColor(Color.blue);
            if(this.font != null) {
                this.g.setFont(this.font);
            }

            this.g.drawString("www.hi.baidu.com?xia_mingjian", w - 85, h - 5);
            this.g.dispose();
        } catch (Exception var4) {
            System.out.println(var4.getMessage());
        }

        return img;
    }

    public BufferedImage modifyImagetogeter(BufferedImage b, BufferedImage d) {
        try {
            int w = b.getWidth();
            int h = b.getHeight();
            this.g = d.createGraphics();
            this.g.drawImage(b, 100, 10, w, h, (ImageObserver)null);
            this.g.dispose();
        } catch (Exception var5) {
            System.out.println(var5.getMessage());
        }

        return d;
    }
}
