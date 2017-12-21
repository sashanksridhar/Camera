/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication4;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sashank
 */
public class WrapperExe {
String au,vi,op;
 WrapperExe(String au,String vi,String op)
 {
     this.au= au;
     this.vi = vi;
     this.op = op;
 }
    public boolean doSomething() {

 String[] exeCmd = new String[]{"G:\\ffmpeg-20171016-7480f23-win64-static\\bin\\ffmpeg","-itsoffset" ,"00:00:01.0","-i", "C:\\Users\\Sashank\\Documents\\NetBeansProjects\\JavaFXApplication4\\"+au, "-i", "C:\\Users\\Sashank\\Documents\\NetBeansProjects\\JavaFXApplication4\\"+vi ,"-acodec", "copy", "-vcodec", "copy", "C:\\Users\\Sashank\\Documents\\NetBeansProjects\\JavaFXApplication4\\"+op};
     
         //Runtime.getRuntime().exec(exeCmd);
         ProcessBuilder pb = new ProcessBuilder(exeCmd);
         boolean exeCmdStatus = executeCMD(pb);
         
         return exeCmdStatus;
         } //End doSomething Function
         
         private boolean executeCMD(ProcessBuilder pb)
         {
         pb.redirectErrorStream(true);
         Process p = null;
         
         try {
         p = pb.start();
         
         } catch (Exception ex) {
         ex.printStackTrace();
         System.out.println("oops");
         p.destroy();
         return false;
         }
         // wait until the process is done
         try {
         p.waitFor();
         } catch (InterruptedException e) {
         e.printStackTrace();
         System.out.println("woopsy");
         p.destroy();
         return false;
         }
         return true;
         }// End function executeCMD
     
 
/*public static void main(String args[])
{
    WrapperExe w = new WrapperExe();
    w.doSomething();
}*/
} // End class WrapperExe
