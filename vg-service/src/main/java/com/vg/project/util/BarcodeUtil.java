package com.vg.project.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 条形码工具类
 */
public class BarcodeUtil {
    /**
     * 生成条形码
     *
     * @param barCode 生成条形码的编号
     * @throws Exception
     */
    public static byte[] genBarCodePng(String barCode) throws Exception {
        Code128Bean bean = new Code128Bean();
        final int dpi = 1024;
        // 样式
        bean.setModuleWidth(0.25);
        bean.setBarHeight(10);
        bean.doQuietZone(true);
        bean.setQuietZone(2);
        bean.setVerticalQuietZone(-0.35);

        bean.setFontName("Helvetica");
        bean.setFontSize(2);
        bean.setMsgPosition(HumanReadablePlacement.HRP_BOTTOM);

        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(out, "image/png", dpi,
                    BufferedImage.TYPE_BYTE_BINARY, true, 0);

            bean.generateBarcode(canvas, barCode);
            canvas.finish();
            return out.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 生成二维码
     *
     * @param content
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static byte[] gen2DBarCodePng(String content, int width, int height) throws Exception {
        Hashtable<EncodeHintType, Comparable> hints = new Hashtable<EncodeHintType, Comparable>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 0);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);

            // 清除生成二维码周边的白边
            int[] rec = bitMatrix.getEnclosingRectangle();
            int resWidth = rec[2] + 1;
            int resHeight = rec[3] + 1;
            BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
            resMatrix.clear();
            for (int i = 0; i < resWidth; i++) {
                for (int j = 0; j < resHeight; j++) {
                    if (bitMatrix.get(i + rec[0], j + rec[1]))
                        resMatrix.set(i, j);
                }
            }

            BufferedImage image = MatrixToImageWriter.toBufferedImage(resMatrix);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(image, "png", out);

            return out.toByteArray();
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将条形码写入PDF文件
     *
     * @param barcode 生成条形码的code
     * @return
     * @throws Exception
     */
    public static File pdfAddBarCode(String barCode) throws Exception {
        // 创建临时文件
        File file = File.createTempFile(barCode, ".pdf");
        // 创建文件
        Document document = new Document();
        document.setPageSize(new Rectangle(288, 144));
        // 建立一个书写器
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        // 打开文件
        document.open();

        Image img = Image.getInstance(genBarCodePng(barCode));
        // 居中显示
        img.setAlignment(1);

        // 设置图片位置的x轴和y周
        // img.setAbsolutePosition(0f, 0f);
        // 设置图片的宽度和高度
        // img.scaleAbsolute(245, 85);
        img.scalePercent(16.5f);
        // 将图片添加到pdf文件中
        document.add(img);

        // 关闭文档
        document.close();
        // 关闭书写器
        writer.close();
        return file;
    }

    /**
     * 将文件转换成Base64输出
     *
     * @param in
     * @return
     */
    public static String getBase64FromInputStream(InputStream in) {
        // 将文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取字节数组
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int rc = 0;
            while ((rc = in.read(buff, 0, 10)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            data = swapStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new String(Base64.getEncoder().encodeToString(data));
    }

    /**
     * 图片放大缩小
     */
    public static BufferedImage zoomInImage(BufferedImage originalImage, int width, int height) {
        BufferedImage newImage = new BufferedImage(width, height, originalImage.getType());
        Graphics g = newImage.getGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return newImage;
    }
}