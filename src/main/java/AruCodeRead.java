// Author: Andy Huang
// E-Mail: ahuang2@hawaii.edu
// Last Revision: 9 - 25 - 26

import org.opencv.core.Mat;
import org.opencv.objdetect.ArucoDetector;
import org.opencv.objdetect.DetectorParameters;
import org.opencv.objdetect.Dictionary;
import org.opencv.objdetect.Objdetect;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;

import java.util.ArrayList;
import java.util.List;

public class AruCodeRead {
    //CONSTANTS
    private static final int[] VALID_IDS = {1, 3, 7};

    private static final Dictionary ARUCO_DICT = Objdetect.getPredefinedDictionary(0);
    private static final DetectorParameters DETECT_PARAMS = new DetectorParameters();

    // Index 0 = default video stream
    private static final VideoCapture VIDEO_CAPTURE = new VideoCapture(0);

    private final Mat image = new Mat();
    private final Mat ids = new Mat();
    private final List<Mat> corners = new ArrayList<>();

    private final ArucoDetector arucoDetectorInstance;
    private final VideoWriter videoWriterInstance;

    public AruCodeRead(){
        this.arucoDetectorInstance = new ArucoDetector(ARUCO_DICT, DETECT_PARAMS);
        this.videoWriterInstance = new VideoWriter();
    }

    public void aruRead(){
        boolean isActive = true;

        if (!VIDEO_CAPTURE.isOpened()){
            try{
                VIDEO_CAPTURE.open(0);
                System.out.println("Video stream successfuly opened.");
            }
            catch (Exception e){
                VIDEO_CAPTURE.release();
                System.out.println("Video closed due to an error." + e);
            }
            return;
        }

        while(isActive){
            VIDEO_CAPTURE.read(image);
            if (!image.empty()){
                arucoDetectorInstance.detectMarkers(image,corners,ids);
                arucoHelper(ids);
            }
            else{
                isActive = false;
            }
        }
    }

    public void arucoScan(){



    }

    public void arucoCloseVideo(){
        if (VIDEO_CAPTURE.isOpened()){
            VIDEO_CAPTURE.release();
            System.out.println("Video capture stream closed.");
        }
    }

    // interprets the ID's and assists in computing the motor commands that will be sent via ROS2 topic
    // This will be done using this helper method and the MotorControlLogic()

    // For case "1","2", and "3", this means that the aruco tag with ID "1" has been detected. When it detects this, it will
    // attempt to approach the tag. After a certain threshold, it will stop and reverse. This particular threshold
    // (or cutoff) has not been decided yet.

    private static void arucoHelper(Mat ids){
       final String MSG_SUCCESS = "Detected Aruco ID with code" ;

        if (ids.empty()){}
        var dumpID = ids.dump();

        switch (dumpID){
            case "[1]" ->
                System.out.println(MSG_SUCCESS + dumpID);
            case "[3]" ->
                System.out.println(MSG_SUCCESS + dumpID);
            case "[5]" -> System.out.println(MSG_SUCCESS + dumpID);
            default ->
                System.out.println("No valid ID's detected.");
        }

    }
}
