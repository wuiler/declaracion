package com.decla.util;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import com.google.zxing.common.BitMatrix;

public final class ImageHelper {
	
	private static final int BLACK = 0xff000000;
	private static final int WHITE = 0xffffffff;
	
	private ImageHelper() {}

        public static BufferedImage toBufferedImage(BitMatrix matrix, int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int x=0; x<width; ++x) {
			for (int y=0; y<height; ++y) {
				image.setRGB(x, y, matrix.get(x,y) ? BLACK : WHITE);
			}
		}
		return image;
	}


	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();    
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int x=0; x<width; ++x) {
			for (int y=0; y<height; ++y) {
				image.setRGB(x, y, matrix.get(x,y) ? BLACK : WHITE);
			}
		}
		return image;
	}
	
	public static BufferedImage captureScreen() throws Exception {
	   Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	   Rectangle screenRectangle = new Rectangle(screenSize);
	   Robot robot = new Robot();
	   BufferedImage image = robot.createScreenCapture(screenRectangle);
	   return image;
	}
	
	public static BufferedImage cropImage(BufferedImage src, int x, int y, int w, int h)
	{
	    BufferedImage dest = new BufferedImage(w, h, src.getType());
	    Graphics g = dest.getGraphics();
	    g.drawImage(src, 0, 0, w, h, x, y, x+w, y+h, null);
	    g.dispose();
	    return dest;
	}
	
	public static BufferedImage resizeImage(BufferedImage src, int w, int h) {
		BufferedImage dest = new BufferedImage(w, h, src.getType());
		Graphics g = dest.getGraphics();
		g.drawImage(src, 0, 0, w, h, null);
		g.dispose();
		return dest;
	}
	
}
