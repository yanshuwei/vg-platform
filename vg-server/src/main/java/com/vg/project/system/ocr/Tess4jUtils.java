package com.vg.project.system.ocr;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.ImageHelper;
import net.sourceforge.tess4j.util.LoadLibs;
import org.springframework.util.ClassUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: curtain
 * @create: 2021-06-10 14:52
 **/
public class Tess4jUtils {
	public static void main(String[] args) throws IOException {
		String ch = "c:\\users\\12083\\desktop\\d.jpg";
		// 创建实例
		ITesseract instance = new Tesseract();
		instance.setDatapath("E:\\Tesseract-OCR\\tessdata");
		// 设置识别语言
		instance.setLanguage("chi_sim");
		// 设置识别引擎
		instance.setOcrEngineMode(0);
		// 读取文件
		BufferedImage image = ImageIO.read(new File(ch));
		try {
			// 识别 普通调用
			String result = instance.doOCR(image);
			System.out.println(result);
		} catch (TesseractException e) {
			System.err.println(e.getMessage());
		}
		//图片经过黑白、锐化、放大等处理后识别
		String result = ocr("c:\\users\\12083\\desktop\\d.jpg");
		System.out.println("识别结果：" + filter(result));
	}

	private static String ocr(String filePath) {
		String result = null;
		try {
			double start = System.currentTimeMillis();
			BufferedImage textImage = ImageIO.read(new File(filePath));
			// 这里对图片黑白处理,增强识别率.这里先通过截图,截取图片中需要识别的部分
			textImage = ImageHelper.convertImageToGrayscale(textImage);
			// 图片锐化
			textImage = ImageHelper.convertImageToBinary(textImage);
			// 图片放大倍数,增强识别率(很多图片本身无法识别,放大5倍时就可以轻易识,但是考滤到客户电脑配置低,针式打印机打印不连贯的问题,这里就放大5倍)
			textImage = ImageHelper.getScaledInstance(textImage, textImage.getWidth() * 1, textImage.getHeight() * 1);

			textImage = ImageHelper.convertImageToBinary(textImage);
			ImageIO.write(textImage, "png", new File("d:\\temp.jpg"));

			Tesseract instance = new Tesseract();
			String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
			instance.setDatapath(path.substring(1) + "static/tessdata");//设置训练库的位置
//            instance.setLanguage("chi_simmm+eng");//中文识别
//            instance.setLanguage("chi_simm");//中文识别
			instance.setLanguage("chi_sim");//中文识别
//            instance.setOcrEngineMode(0);
			result = instance.doOCR(textImage);
			double end = System.currentTimeMillis();
			System.out.println("耗时" + (end - start) / 1000 + " s");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private static String filter(String str) {
		String regEx = "[`~!@#$%^&*()+=|{}':;,\"\\[\\].<>/?￥……&*（）__′_—+|{}【】'；：”“’ ]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
}
