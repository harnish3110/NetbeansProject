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
public class ServerInd extends Thread{
    int curr;
    Socket[] c;
    ServerInd()
    {
        try
        {
            ServerSocket sc=new ServerSocket(5252);
            c=new Socket[100];
            ServerInd_Thr sin[]=new ServerInd_Thr[100];
            ServerInd s[]=new ServerInd[100];
            for(int i=0;i<100;i++)
            {
                curr=i;
                c[i]=sc.accept();
                sin[i]=new ServerInd_Thr(c,i);
                sin[i].start();
                s[i].start();
                
            }
        }
        catch(Exception e)
        {System.out.println(e);}
    }
    public static void main(String[] args) {
        // TODO code application logic here
        ServerInd s=new ServerInd();
        
    }
    public void run()
    {
        int count=ServerInd_Thr.count;
        int temp=-1;
        while(true)
        {
            if(temp!=count)
            {
                try
                {
                    
                    PrintWriter pr=new PrintWriter(c[curr].getOutputStream());
                    pr.println("No. of present Client are: "+count);
                    temp=count;
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
            }
        }
    }
    
}

class ServerInd_Thr extends Thread
{
    Socket cx[];
    int sen;
    static int count=-1;
    ServerInd_Thr(Socket[] f,int h)
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