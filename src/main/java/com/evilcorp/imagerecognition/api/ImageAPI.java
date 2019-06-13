package com.evilcorp.imagerecognition.api;

import com.evilcorp.imagerecognition.Constants;
import static com.evilcorp.imagerecognition.Constants.*;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.apache.log4j.Logger;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.photo.Photo;


/**
 *
 * @author BaceK
 */
public class ImageAPI {
    private static Logger log = Logger.getLogger(ImageAPI.class);
    
    public ImageAPI() throws Exception{
        log.info("Checking OS...");
        log.debug(Constants.getOperatingSystemType());
        switch (Constants.getOperatingSystemType()) {
        case LINUX:
            throw new Exception("Linux does not support!!!");
        case WINDOWS:
            System.load(PATH_TO_NATIVE_LIB_WIN);
        break;
        case MACOSX:
            throw new Exception("Mac OS does not support!!!");
        case UNKNOWN:
            throw new Exception("Current OS does not support!!!");
        default:
            throw new Exception("Your OS does not support!!!");
        }
    }
    
    
    public Mat changeChan(Mat srcImage, int numberOfChan){
        int totalBytes = (int) (srcImage.total() * srcImage.elemSize());
        byte buffer[] = new byte[totalBytes];
        srcImage.get(0, 0, buffer);
            for (int i = 0; i < totalBytes; i++) {
                if (i % srcImage.channels() == numberOfChan) {
                buffer[i] = 0;} } 
            srcImage.put(0, 0, buffer);
        return srcImage;
    }
    

    public void showImage(Mat m, String nameWindow){
        int type = BufferedImage.TYPE_BYTE_GRAY;
            if ( m.channels() > 1 ) {
                type = BufferedImage.TYPE_3BYTE_BGR;}
        int bufferSize = m.channels()*m.cols()*m.rows();
        byte [] b = new byte[bufferSize];
        m.get(0,0,b);

        BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        ImageIcon icon=new ImageIcon(image);
        JFrame frame=new JFrame(nameWindow);
        frame.setLayout(new FlowLayout());
        frame.setSize(image.getWidth(null)+50, image.getHeight(null)+50);
        JLabel lbl=new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}
    
    public void morphology(Mat imageSrc, int coreSize, MorphologyType morphType) {
    try {
        Mat dst = imageSrc.clone();
        switch(morphType){
            case GRADIENT_erode:
        Mat element_size = Imgproc.getStructuringElement(Imgproc.MORPH_GRADIENT, new Size(coreSize, coreSize));
        Imgproc.erode(imageSrc, dst, element_size);
        Imgcodecs.imwrite(PATH_TO_RESULT_IMAGE + "GRADIENT_erode_" + coreSize + ".jpg", dst);
        showImage(dst,"GRADIENT_erode_" + coreSize);
            break;
            case GRADIENT_dilate:
        element_size = Imgproc.getStructuringElement(Imgproc.MORPH_GRADIENT, new Size(coreSize, coreSize));
        Imgproc.dilate(imageSrc, dst, element_size);
        Imgcodecs.imwrite(PATH_TO_RESULT_IMAGE + "GRADIENT_dilate_" + coreSize + ".jpg", dst);
        showImage(dst,"GRADIENT_dilate_" + coreSize);
            break;
            case BLACKHAT_erode:
        element_size = Imgproc.getStructuringElement(Imgproc.MORPH_BLACKHAT, new Size(coreSize, coreSize));
        Imgproc.erode(imageSrc, dst, element_size);
        Imgcodecs.imwrite(PATH_TO_RESULT_IMAGE + "BLACKHAT_erode_" + coreSize + ".jpg", dst);
        showImage(dst,"BLACKHAT_erode_" + coreSize);
            break;
            case BLACKHAT_dilate:
        element_size = Imgproc.getStructuringElement(Imgproc.MORPH_BLACKHAT, new Size(coreSize, coreSize));
        Imgproc.dilate(imageSrc, dst, element_size);
        Imgcodecs.imwrite(PATH_TO_RESULT_IMAGE + "BLACKHAT_dilate_" + coreSize + ".jpg", dst);
        showImage(dst,"BLACKHAT_dilate_" + coreSize);
            break;
            case RECT_erode:
        element_size = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(coreSize, coreSize));
        Imgproc.erode(imageSrc, dst, element_size);
        Imgcodecs.imwrite(PATH_TO_RESULT_IMAGE + "RECT_erode_" + coreSize + ".jpg", dst);
        showImage(dst,"RECT_erode_" + coreSize);
            break;
            case RECT_dilate:
        element_size = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(coreSize, coreSize));
        Imgproc.dilate(imageSrc, dst, element_size);
        Imgcodecs.imwrite(PATH_TO_RESULT_IMAGE + "RECT_dilate_" + coreSize + ".jpg", dst);
        showImage(dst,"RECT_dilate_" + coreSize);
            break;
            case ELLIPSE_erode:
        element_size = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(coreSize, coreSize));
        Imgproc.erode(imageSrc, dst, element_size);
        Imgcodecs.imwrite(PATH_TO_RESULT_IMAGE + "ELLIPSE_erode_" + coreSize + ".jpg", dst);
        showImage(dst,"ELLIPSE_erode_" + coreSize);
            break;
            case ELLIPSE_dilate:
        element_size = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(coreSize, coreSize));
        Imgproc.dilate(imageSrc, dst, element_size);
        Imgcodecs.imwrite(PATH_TO_RESULT_IMAGE + "ELLIPSE_dilate_" + coreSize + ".jpg", dst);
        showImage(dst,"ELLIPSE_dilate_" + coreSize);
            break;
        }
    } 
    catch (Exception ex) {
        System.err.println(ex.getMessage());}
}
    public void filters(Mat imageSrc, int coreSize, FilterType filtType){
    try {
        Mat dst = imageSrc.clone(); 
        switch(filtType){
                case BLUR_Filter:  
        Imgproc.blur(imageSrc, dst, new Size(coreSize,coreSize));
        Imgcodecs.imwrite(PATH_TO_RESULT_IMAGE + "BLUR_Filter_" + coreSize + ".jpg", dst);
        showImage(dst, "BLUR_Filter_"+ coreSize);
        break;
                case GAUSSIAN_Filter:
        Imgproc.GaussianBlur(imageSrc, dst, new Size(coreSize,coreSize), 0);
        Imgcodecs.imwrite(PATH_TO_RESULT_IMAGE + "GAUSSIAN_Filter_" + coreSize + ".jpg", dst);
        showImage(dst, "GAUSSIAN_Filter_"+ coreSize);
        break;
                case MEDIAN_BLUR_Filter:
        Imgproc.medianBlur(imageSrc, dst, coreSize);
        Imgcodecs.imwrite(PATH_TO_RESULT_IMAGE + "MEDIAN_BLUR_Filter_" + coreSize + ".jpg", dst);
        showImage(dst, "MEDIAN_BLUR_Filter_"+ coreSize);
        break;
                case BILATERAL_Filter:
        Imgproc.bilateralFilter(imageSrc, dst, coreSize, 200, coreSize);
        Imgcodecs.imwrite(PATH_TO_RESULT_IMAGE + "BILATERAL_Filter_" + coreSize + ".jpg", dst);
        showImage(dst, "BILATERAL_Filter_"+ coreSize);
        break;
        }  
    }
    
    catch (Exception ex) {
        System.err.println(ex.getMessage());}
    }
    
    public void fillFlood(Mat imageSrc, int seedPointX, int seedPointY, int intensVal, Scalar RGB) {
        Point seedPoint = new Point(seedPointX, seedPointY);
        Scalar newVal = RGB;
        Scalar loDiff = new Scalar(intensVal, intensVal, intensVal);
        Scalar upDiff = new Scalar(intensVal, intensVal, intensVal);

        Rect rect = new Rect();
        Mat mask = new Mat();

        Imgproc.floodFill(imageSrc, mask, seedPoint, newVal, rect, loDiff, upDiff,Imgproc.FLOODFILL_FIXED_RANGE);
        Mat dst = imageSrc.clone();
        Imgcodecs.imwrite(PATH_TO_RESULT_IMAGE + "FILL_from_(" + seedPointX + "," + seedPointY + ")" + "_RGB_" + RGB + ".jpg", dst);
        showImage(imageSrc, "FILL_from_(" + seedPointX + "," + seedPointY + ")" + "_RGB_" + RGB);
    }
    
    public void pyramids(Mat imageSrc) {
        Mat mask = new Mat();
        int downSizeVal = 2;
        int upSizeVal = 2;
        Mat dst = imageSrc.clone();
        Imgproc.pyrDown(imageSrc, mask, new Size(imageSrc.cols() / downSizeVal,  imageSrc.rows() / downSizeVal));
        Imgcodecs.imwrite(PATH_TO_RESULT_IMAGE + "pyrDown native" + ".jpg", dst);
        showImage(mask, "pyrDown native");

        Imgproc.pyrUp(imageSrc, mask, new Size(imageSrc.cols() * upSizeVal,  imageSrc.rows() * upSizeVal));
        dst = imageSrc.clone();
        Imgcodecs.imwrite(PATH_TO_RESULT_IMAGE + "pyrUp native" + ".jpg", dst);
        showImage(mask, "pyrUp native");

        Core.subtract(imageSrc, imageSrc, mask);
        Imgcodecs.imwrite(PATH_TO_RESULT_IMAGE + "Core" + ".jpg", dst);
        dst = imageSrc.clone();
        showImage(mask, "Core");
}
    public void identifyingRectangles(Mat imageSrc){
        Mat grayImage = new Mat(); 
        Imgproc.cvtColor(imageSrc, grayImage, Imgproc.COLOR_BGR2GRAY);
        showImage(grayImage, "identifyingRectangles_1");

        Mat denoisingImage = new Mat();
        Photo.fastNlMeansDenoising(grayImage, denoisingImage);
        showImage(denoisingImage, "identifyingRectangles_2");

        Mat histogramEqualizationImage = new Mat();
        Imgproc.equalizeHist(grayImage, histogramEqualizationImage);
        showImage(histogramEqualizationImage, "identifyingRectangles_3");

        Mat morphologicalOpeningImage = new Mat();
        int morth = Imgproc.MORPH_RECT;
        Mat kernel = Imgproc.getStructuringElement(morth, new Size(5,5));
        Imgproc.morphologyEx(histogramEqualizationImage, morphologicalOpeningImage,morth, kernel);
        showImage(morphologicalOpeningImage,"identifyingRectangles_4");

        Mat subtractImage = new Mat();
        Core.subtract(denoisingImage, histogramEqualizationImage, subtractImage);
        showImage(subtractImage, "identifyingRectangles_5");

        Mat thresholdImage = new Mat();
        int type = Imgproc.THRESH_OTSU;
        double threshold = Imgproc.threshold(subtractImage, thresholdImage, 50, 255, type );
        showImage(thresholdImage, "identifyingRectangles_6");
        thresholdImage.convertTo(thresholdImage, CvType.CV_16SC1);

        Mat edgeImage = new Mat();
        thresholdImage.convertTo(thresholdImage, CvType.CV_8U);
        Imgproc.Canny(imageSrc, edgeImage, threshold, threshold * 3,3, true);
        showImage(edgeImage, "identifyingRectangles_7");

        Mat dilatedImage = new Mat();
        morth = Imgproc.MORPH_RECT;
        kernel = Imgproc.getStructuringElement(morth, new Size(3,3));
        Imgproc.dilate(thresholdImage, dilatedImage, kernel, new Point(-1,-1), 1);
        showImage(dilatedImage, "identifyingRectangles_8");  

        ArrayList<MatOfPoint> contours = new ArrayList<>();
        Mat qwerty = new Mat();
        Imgproc.findContours(dilatedImage, contours, qwerty, Imgproc.RETR_TREE,Imgproc.CHAIN_APPROX_SIMPLE);
        contours.sort(Collections.reverseOrder(Comparator.comparing(Imgproc::contourArea)));

        for (MatOfPoint contour : contours.subList(0, 10)) {
            System.out.println(Imgproc.contourArea(contour));
            MatOfPoint2f point2f = new MatOfPoint2f();
            MatOfPoint2f approxContour2f = new MatOfPoint2f();
            MatOfPoint approxContour = new MatOfPoint();
            contour.convertTo(point2f, CvType.CV_32FC2);
            double arcLength = Imgproc.arcLength(point2f, true);
            Imgproc.approxPolyDP(point2f, approxContour2f, 0.03 * arcLength, true);
            approxContour2f.convertTo(approxContour, CvType.CV_32S);
            Rect rect = Imgproc.boundingRect(approxContour);
            double ratio = (double) rect.height / rect.width;
                if (Math.abs(0.3 - ratio) > 0.15) {
                    continue;
                     }
            Mat submat = imageSrc.submat(rect);
            Imgproc.resize(submat, submat, new Size(400, 400 * ratio));
            showImage(submat, "identifyingRectangles_9");
 }
    
    
}
}
