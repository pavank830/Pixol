
package pixol;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import static java.lang.Thread.MIN_PRIORITY;
import static java.lang.Thread.sleep;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import static pixol.Pixol.stage;
import javax.swing.SwingUtilities;
interface MouseMonitorListener {

        public void mousePositionChanged(Point p);
    }

public class FXMLDocumentController implements Initializable,MouseMonitorListener {

    @FXML
    private  AnchorPane pane;
   
    static Robot robot;
    @FXML
    private Text text;
    @FXML
    private Text text1;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
          PointerInfo pi = MouseInfo.getPointerInfo();//PointerInfo provides information returns the 
                //information about the location of the pointer and the graphics device.
            updateColor(pi.getLocation());
            MouseMonitor monitor = new MouseMonitor();
           monitor.setMouseMonitorListener(this);
            monitor.start();
    }    
     protected void updateColor(Point p) {

            java.awt.Color pixelColor = robot.getPixelColor(p.x, p.y);//The Robot class in the Java AWT package is used to generate native system input events for 
            //the purposes of test automation, self-running demos, and other applications 
            //where control of the mouse and keyboard is needed
            int r=pixelColor.getRed();
        int g=pixelColor.getGreen();
        int b=pixelColor.getBlue();
         String col=Integer.toHexString(r)+Integer.toHexString(g)+Integer.toHexString(b);
         try{
           pane.setBackground(new Background(new BackgroundFill(Color.web("#"+col), CornerRadii.EMPTY, Insets.EMPTY)));
         }
         catch(Exception e){
             
         }
         text.setText("#"+col);
           text1.setText("[r:"+r+",g:"+g+",b:"+b+"]");
        }

        
    @Override
        public void mousePositionChanged(final Point p) {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    updateColor(p);
                }

            });
        }

    @FXML
    private void close(ActionEvent event) {
        
        stage.close();
        System.exit(0);
    }

    @FXML
    private void minimize(ActionEvent event) {
        stage.setIconified(true);
    }
 
}
 
class MouseMonitor extends Thread {

        private Point lastPoint;
        private MouseMonitorListener listener;

       public MouseMonitor() {
            setDaemon(true);//Daemon thread in Java. Daemon thread is a low priority 
            //thread that runs in background to perform tasks such as garbage collection.
            //Daemon thread in java can be useful to run some tasks in background
            //Daemon thread is to provide services to user thread for background supporting task.
      //if deamon thread not used then an illegal state exception arrives i.e Java 
//application is not in an appropriate state for the requested operation so that requested service can 
//be provided by deamon at that stage
            setPriority(MIN_PRIORITY);
        }

        public void setMouseMonitorListener(MouseMonitorListener listener) {
            this.listener = listener;
        }

        public MouseMonitorListener getMouseMonitorListener() {
            return listener;
        }

        protected Point getMouseCursorPoint() {
            PointerInfo pi = MouseInfo.getPointerInfo();
            return pi.getLocation();
        }

        @Override
        public void run() {
            lastPoint = getMouseCursorPoint();
            while (true) {
                try {
                    sleep(250);
                } catch (InterruptedException ex) {
                }

                Point currentPoint = getMouseCursorPoint();
                if (!currentPoint.equals(lastPoint)) {
                    lastPoint = currentPoint;
                    MouseMonitorListener listener = getMouseMonitorListener();
                    if (listener != null) {
                        listener.mousePositionChanged((Point) lastPoint.clone());
                //clone()-creates a new object of the same class and with the same contents as this object.
               //he clone() method saves the extra processing task for creating the exact copy of an object.
               //If we perform it by using the new keyword, it will take a lot of processing to be performed
               //that is why we use object cloning.     
                    }
                }

            }
        }
    }


      
 /*  public  void  test(){
         try{
            robot = new Robot();//The Robot class in the Java AWT package is used to generate native system input events for 
            //the purposes of test automation, self-running demos, and other applications 
            //where control of the mouse and keyboard is needed

            while(true){
                
                pointer = MouseInfo.getPointerInfo();//PointerInfo provides information returns the 
                //information about the location of the pointer and the graphics device.
                point = pointer.getLocation();
                if(point.getX() == 0 && point.getY() == 0){
                   break; // stop the program when you go to (0,0)
                }else{
                    color = robot.getPixelColor((int)point.getX(),(int)point.getY());
                    System.out.println("Color at: " + point.getX() + "," + point.getY() + " is: " + color);
        System.out.print("pavan");
        Integer r=color.getRed();
        Integer g=color.getGreen();
        Integer b=color.getBlue();
         
       String col=Integer.toHexString(r)+Integer.toHexString(g)+Integer.toHexString(b);
       //int p=Integer.parseInt(col);
        System.out.print("pavan56");
    
      //  System.out.println(p+"\n"+col+"\n");
         System.out.println("Color at: " + point.getX() + "," + point.getY() + " is: " + color);
        
         pane.setStyle("-fx-background-color:#col");
     // pane.setBackground(new Background(new BackgroundFill(Color.web("#"+col), CornerRadii.EMPTY, Insets.EMPTY)));
              
                }
            }
        }catch(Exception e){

        }
          
    }*/