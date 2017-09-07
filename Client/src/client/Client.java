/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;
import java.net.*;
import java.io.*;
import java.awt.*;
import javax.imageio.*;
import java.awt.image.*;
import javax.swing.JOptionPane;


/**
 *
 * @author Harnish
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    BufferedReader read=null;
    Runtime r1=null;
    Client()
  {
      
    try
    {
        Socket c=new Socket("127.0.0.1",6565);
	BufferedReader br=new BufferedReader(new InputStreamReader(c.getInputStream()));
	String h=br.readLine();
	int m=Integer.parseInt(h);
        read=new BufferedReader(new InputStreamReader(c.getInputStream()));
        r1=Runtime.getRuntime();
	Client_Img_Thr thr = new Client_Img_Thr(m);
	thr.start();
        thread();

    }
    catch(Exception e)
    {        System.out.println(e);    }
  }
    
    public void thread()
    {
        try{
            
        while(true)
        {
            String msg=read.readLine();
            System.out.println("Hiiii");
            if(msg.equalsIgnoreCase("shutdown"))
            {
                r1.exec("shutdown -s");
            }
            JOptionPane.showMessageDialog(null, msg);
        }
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        // TODO code application logic here
        Client ct=new Client();
    }
}

class Client_Img_Thr extends Thread
{
   int port;
   Client_Img_Thr(int tempport)
    {
	port=tempport;	
    }

   public void run()
   {
     try
     {
	int i=0;
	while(true)
	{
	   Socket t=new Socket("127.0.0.1",port);
	   Robot robot =new Robot();
	   Thread.sleep(2000);
	   i++;
	   System.out.println(i);
	   BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
	   ImageIO.write(screenShot, "JPG",t.getOutputStream());
	}
   }
   catch(Exception e)
   {
       System.out.println(e);
   }
}
}