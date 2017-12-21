/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication4;


import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.WindowEvent;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;
import org.opencv.core.Size;
/**
 *
 * @author Sashank
 */
public class FXMLDocumentController  {
    
     @FXML
    private Button start_btn;
      @FXML
    private Button rcd_btn;
    @FXML
    private ImageView currentFrame;

    private Pane rootElement;
    private Timer timer;
    private VideoCapture capture = new VideoCapture();
    int wid,hei;
    
    
    
    final JavaSoundRecorder recorder = new JavaSoundRecorder();
    Random rand1 = new Random();

int  n1 = rand1.nextInt(50) + 1;
 Size s = new Size(640,480);
VideoWriter op = new VideoWriter(n1+".avi",VideoWriter.fourcc('M', 'J', 'P', 'G'),30,s,true);

    @FXML
    protected void startCamera(ActionEvent event)
    {
        
// check: the main class is accessible?
            if (this.rootElement != null)
            {
                    // get the ImageView object for showing the video stream
                    final ImageView frameView = currentFrame;
                    // check if the capture stream is opened
                    if (!this.capture.isOpened())
                    {
                            // start the video capture
                            this.capture.open(0);
                            // grab a frame every 33 ms (30 frames/sec)
                            TimerTask frameGrabber = new TimerTask() {
                                    @Override
                                    public void run()
                                    {
                                            Image tmp = grabFrame();
                                            Platform.runLater(new Runnable() {
                                                    @Override
                                        public void run()
                                                    {
                                                            frameView.setImage(tmp);
                                        }
                                            });

                                    }
                            };
                            this.timer = new Timer();
                            //set the timer scheduling, this allow you to perform frameGrabber every 33ms;
                            this.timer.schedule(frameGrabber, 0, 33);
                            this.start_btn.setText("Stop Camera");
                            
                    }
                    else
                    {
                            this.start_btn.setText("Start Camera");
                            // stop the timer
                            if (this.timer != null)
                            {
                                    this.timer.cancel();
                                    this.timer = null;
                            }
                            // release the camera
                            this.capture.release();
                            // clear the image container
                           Image image =  frameView.getImage();
                           Random rand = new Random();

int  n = rand.nextInt(50) + 1;
                           File outputFile = new File(n+".png");
    BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
    try {
      ImageIO.write(bImage, "png", outputFile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
                            frameView.setImage(null);
                    }
            }
    }

    private Image grabFrame()
    {
            //init
            Image imageToShow = null;
            Mat frame = new Mat();
            // check if the capture is open
            if (this.capture.isOpened())
            {
                    try
                    {
                            // read the current frame
                            this.capture.read(frame);
                            
                            // if the frame is not empty, process it
                            if (!frame.empty())
                            {
                                   
                                // convert the image to gray scale
                                    //Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
                                    // convert the Mat object (OpenCV) to Image (JavaFX)
                                    imageToShow = mat2Image(frame);
                            }
                    }
                    catch (Exception e)
                    {
                            // log the error
                            System.err.println("ERROR: " + e.getMessage());
                    }
            }
            return imageToShow;
    }

    private Image mat2Image(Mat frame)
    {
            // create a temporary buffer
            MatOfByte buffer = new MatOfByte();
            // encode the frame in the buffer
            Imgcodecs.imencode(".png", frame, buffer);
            // build and return an Image created from the image encoded in the buffer
            return new Image(new ByteArrayInputStream(buffer.toArray()));
    }

    public void setRootElement(Pane root)
    {
            this.rootElement = root;
    }
    @FXML
    protected void recordVideo(ActionEvent event) 
    {
        // check: the main class is accessible?
         
        // start recording
        
              // start capturing
        Thread stopper = new Thread(new Runnable() {
            public void run() {
        recorder.start();}});
        

        if (this.rootElement != null)
            {
                    // get the ImageView object for showing the video stream
                    final ImageView frameView = currentFrame;
                    // check if the capture stream is opened
                    if (!this.capture.isOpened())
                    {
                        stopper.start();                            
// start the video capture
                            this.capture.open(0);
                            // grab a frame every 33 ms (30 frames/sec)
                            TimerTask frameGrabber = new TimerTask() {
                                    @Override
                                    public void run()
                                    {
                                            Image tmp = grabFramevid();
                                            Platform.runLater(new Runnable() {
                                                    @Override
                                        public void run()
                                                    {
                                                            frameView.setImage(tmp);
                                        }
                                            });

                                    }
                            };
                            this.timer = new Timer();
                            //set the timer scheduling, this allow you to perform frameGrabber every 33ms;
                            this.timer.schedule(frameGrabber, 0, 33);
                            this.start_btn.setText("Stop Camera");
                            
                    }
                    else
                    {
                            this.start_btn.setText("Start Camera");
                            // stop the timer
                            if (this.timer != null)
                            {
                                    this.timer.cancel();
                                    this.timer = null;
                            }
                            // release the camera
                            this.capture.release();
                            // clear the image container
                            recorder.finish();
                            op.release();
                           
                          String s1 = new String(n1+".avi");
                          Random rand2 = new Random();

int  n2 = rand2.nextInt(50) + 1;
String r = new String(n2+".avi");
                            WrapperExe w = new WrapperExe("sample.wav",s1,r);
                            w.doSomething();
                            File f = new File("sample.wav");
                            f.delete();
                            File f1 = new File(n1+".avi");
                            f1.delete();
                            frameView.setImage(null);
                    }
            }
    }
private Image grabFramevid()
    {
            //init
            Image imageToShow = null;
            Mat frame = new Mat();
            // check if the capture is open
            if (this.capture.isOpened())
            {
                    try
                    {
                            // read the current frame
                            this.capture.read(frame);
                            
                            // if the frame is not empty, process it
                            if (!frame.empty())
                            {
                                   
                                       op.write(frame);
// convert the image to gray scale
                                    //Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
                                    // convert the Mat object (OpenCV) to Image (JavaFX)
                                    imageToShow = mat2Image(frame);
                            }
                    }
                    catch (Exception e)
                    {
                            // log the error
                            System.err.println("ERROR: " + e.getMessage());
                    }
            }
            return imageToShow;
    }
    
   
}
