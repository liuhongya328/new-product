package springboot.util;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@Component
public class ChartGraphics{
	BufferedImage image;

	@Autowired
	private CacheService cacheService;
	
	void createImage(String fileLocation) {
		try {
			FileOutputStream fos = new FileOutputStream(fileLocation);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
			encoder.encode(image);
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void graphicsGeneration(String changjia, String productCode, String classname, String imgurl) throws IOException, WriterException {
        int width = 200; // 二维码图片宽度
        int height = 200; // 二维码图片高度
        String format = "jpg";// 二维码的图片格式

        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); // 内容所使用字符集编码

        BitMatrix bitMatrix = new MultiFormatWriter().encode(productCode,
                BarcodeFormat.QR_CODE, width, height, hints);
        // 生成二维码
        File outputFile = new File(imgurl);
        MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
        
        
        
		int imageWidth = 350;// 图片的宽度
		int imageHeight = 250;// 图片的高度
		image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, imageWidth, imageHeight);
		graphics.setColor(Color.BLACK);
		Font font1=new Font("宋体",Font.PLAIN,20);
		graphics.setFont(font1);
		graphics.drawString(changjia, 200, 100);
		graphics.drawString(productCode.split("=")[1], 200, 150);
		//ImageIcon imageIcon = new ImageIcon(imgurl);
		//graphics.drawImage(imageIcon.getImage(), 230, 0, null);
		//改成这样:
		BufferedImage bimg = null;
		try {
			bimg = javax.imageio.ImageIO.read(new java.io.File(imgurl));
		} catch (Exception e) {
		}
		if (bimg != null)
			graphics.drawImage(bimg, 0, 25, null);
		graphics.dispose();
		createImage(imgurl);
	}

	public static void main(String[] args) {
	
	}
}