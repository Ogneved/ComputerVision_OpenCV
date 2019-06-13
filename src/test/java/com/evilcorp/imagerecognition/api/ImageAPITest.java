package com.evilcorp.imagerecognition.api;

import static com.evilcorp.imagerecognition.Constants.*;
import static com.evilcorp.imagerecognition.api.FilterType.*;
import static com.evilcorp.imagerecognition.api.MorphologyType.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;



/**
 *
 * @author Ogneved
 */
public class ImageAPITest {
    
    private static Logger log = Logger.getLogger(ImageAPITest.class);
    private ImageAPI api;
    String imgPath = PATH_TO_ORIGIN_IMAGE;
    String imgName = "Atomic.png";
    Mat imageSrc;
    
    public ImageAPITest() {
    }
    
    @Before
    public void setUp() throws Exception{
    api = new ImageAPI();
    imageSrc = Imgcodecs.imread(imgPath+imgName, Imgcodecs.IMREAD_COLOR);
    
    
    }
    
    @Test
    public void testGetMat() throws IOException {
        log.info("Trying to load picture...");
        for (int i = 0; i < 4; i++){
            Mat imageSrc = this.imageSrc.clone();
            api.changeChan(imageSrc, i);
            Mat dst = imageSrc.clone();
            Imgcodecs.imwrite(PATH_TO_RESULT_IMAGE + "Change_chanell_" + i + ".jpg", dst);
            api.showImage(imageSrc, "Test");
            }    
        System.out.println("Stop");
    }    
    
    @Test
    public void testMorphology() throws IOException{
        log.info("Trying to load picture...");
        Mat imageSrc = this.imageSrc.clone();
        List<MorphologyType> morphType = new ArrayList<>(Arrays.asList(
        GRADIENT_erode, GRADIENT_dilate, BLACKHAT_erode, BLACKHAT_dilate, 
                RECT_erode, RECT_dilate, ELLIPSE_erode, ELLIPSE_dilate));
        for (int i = 0; i < 8; i++){
        for (int coreSize = 3; coreSize <= 15; coreSize=coreSize+2){  
            if (coreSize != 11){
                api.morphology(imageSrc, coreSize, morphType.get(i)); 
            }
        }
        }
        System.out.println("Stop");
    }
    
    @Test
    public void testFilters() throws IOException{
        log.info("Trying to load picture...");
        Mat imageSrc = this.imageSrc.clone();
        List<FilterType> filtType = new ArrayList<>(Arrays.asList(
            BLUR_Filter, GAUSSIAN_Filter, MEDIAN_BLUR_Filter, BILATERAL_Filter));
        for (int i = 0; i < 4; i++){
        for (int coreSize = 3; coreSize <= 15; coreSize=coreSize+2){  
            if (coreSize != 11){
                api.filters(imageSrc, coreSize, filtType.get(i)); 
            }
        }
        }
        System.out.println("Stop");
    }
    @Test
    public void testFillFlood() throws IOException{
        log.info("Trying to load picture...");
        Mat imageSrc = this.imageSrc.clone();
        api.showImage(imageSrc, "Origin_image");
        api.fillFlood(imageSrc, 200, 100, 50, new Scalar(0, 255, 0));
        api.fillFlood(imageSrc, 550, 500, 50, new Scalar(255, 0, 0));
        api.fillFlood(imageSrc, 650, 650, 50, new Scalar(0, 0, 255));
        
        System.out.println("Stop");
    }
    @Test
    public void testPyramyds() throws IOException{
        log.info("Trying to load picture...");
        Mat imageSrc = this.imageSrc.clone();
        api.showImage(imageSrc, "Origin_image");
        api.pyramids(imageSrc);
    
        System.out.println("Stop");
    }
    @Test
    public void testIdentifyingRectangles() throws IOException{
        log.info("Trying to load picture...");
        Mat imageSrc = this.imageSrc.clone();
        api.identifyingRectangles(imageSrc);
        
        System.out.println("Stop");
    }
}

