/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grpchat;
import java.net.*;
import java.io.*;


/**
 *
 * @author Harnish
 */
public class GrpChat {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        // TODO code application logic here
        try
        {
            ServerSocket sc=new ServerSocket(5252);
            Socket c[]=new Socket[100];
            GrpChat_Thr gt[]=new GrpChat_Thr[100];
            for(int i=0;i<100;i++)
            {
                c[i]=sc.accept();
                gt[i]=new GrpChat_Thr(c,i);
                gt[i].start();
            }
        }
        catch(Exception e)
        {System.out.println(e);}
    }
}
class GrpChat_Thr extends Thread
{
    Socket cx[];
    int sen;
    static int count=-1;
    GrpChat_Thr(Socket[] f,int h)
    {
        count++;
        cx=f;
        sen=h;
    }
    public void run()
    {
        try
        {
            BufferedReader br=new BufferedReader(new InputStreamReader(cx[sen].getInputStream()));
            while(true)
            {
                String s=br.readLine();
                for(int i=0;i<count;i++)
                {
                    PrintWriter pw=new PrintWriter(cx[i].getOutputStream(),true);
                    pw.println("Message from "+i+": "+s);
                }
                
            }
            
        }
        catch(Exception e)
        {System.out.println(e);}
    }
}