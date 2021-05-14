import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    
    static HashMap<String, Object> hash;
    public static void main(String[] args) {
   
       
        try {
            //���� ��ȣ�� �ӽ÷� 3309
            ServerSocket server = new ServerSocket(1525);
            hash = new HashMap<String, Object>();
            //hash�� Ű���ҷ��ͼ� �� �渶�� ������� ���ϱ�
            
            while(true)
            {
                System.out.println("=======================");
                System.out.println("���� ������   "+hash.size()+"��...");
                //hash�� ���� ���ϱ�
                
                System.out.println("������ ��ٸ�����...");
                Socket sck = server.accept();
                //Ŭ���̾�Ʈ�� ���ε��ö����� chatThr������ �Ѱ� ���� ������ ���������� ����
                ChatThread chatThr = new ChatThread(sck, hash);
                chatThr.start();            
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
//
//
//
//
//
//
//
//
//
//
//
//

class ChatThread extends Thread{
    Socket sck;
    String [] split;
    String code;
    BufferedReader br;
    //���� Ŭ���̾�Ʈ�� Printwriter��ü�� code�� �����ϰ� �������ִ� �ؽ���
    HashMap<String, Object> hash;
    boolean initFlag = false;
    public ChatThread(Socket sck,HashMap<String, Object> hash) {
        
        this.sck = sck;
        this.hash = hash;
        try {
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(sck.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(sck.getInputStream()));
            //123a�� 123b���� ä���� ����
            pw.println("Please press your code number(123a, 123p, 456a, 456p)");
            pw.flush();
            System.out.println("�ڵ庸����");
            code = br.readLine();
            System.out.println("===="+code+"�԰� ���������� ����");
            //����ȭ �� �ؽ��ʿ� ����
            synchronized (hash) {
                hash.put(code, pw);
            }
            initFlag = true;
        } catch (IOException e) {
            e.printStackTrace();
        }            
    }
    public void run() {
    	
       String line = null;
         DBcon DBcon = new DBcon();
        try {
            while((line = br.readLine()) != null)
            {//Ŭ���̾�Ʈ�κ��� quit�� ������ ����
                if(line.split("/")[0].equals("quit"))
                {
                    System.out.println(code+"���� �ý����� �����մϴ�...");
                    break;
                    
                }else
                {//�ƴ� ��� ��� �о�� �����͸� Ŭ���̾�Ʈ�鿡�� ����
                    sendMsg(line);
                   
                   DBcon.user(code , line);

                }
            }
         
        } catch (IOException e) {
            System.out.println(code+"���� �ý����� ���������� �����մϴ�...");
        }finally
        {
            synchronized (hash) {
                hash.remove(code);
            }
            System.out.println("=======================");
            System.out.println("���� ������   "+hash.size()+"��...");
            System.out.println("=======================");
            
            try {
                sck.close();
            } catch (IOException e) {
                System.out.println("socket�� ���������� ������� �ʾҽ��ϴ�.");
            }
        }
    }
    
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //

    //1:1 ���� (�ڵ尡 123a�̸� 123p���Ը� 1:1�� �������ִ� ���)
    public void sendMsg(String msg)
    {
        synchronized (hash) 
        {
            PrintWriter pw = null;
          
             if(msg.split("/")[0].endsWith("a"))
            {//split�Լ��� replace�Լ��� �̿��� �����ϰ� ���Ź��� Ŭ���̾�Ʈ printwriter��ü�� ����
                pw = (PrintWriter) hash.get(msg.split("/")[0].replace("a", "p"));
                pw.println(msg.split("/")[1]);
                pw.flush();
            }else if(msg.split("/")[0].endsWith("p"))//�޽��� ���� ��ü�� pc�� ���
            {
                pw = (PrintWriter) hash.get(msg.split("/")[0].replace("p", "a"));
                pw.println(msg.split("/")[1]);
                pw.flush();
            }else//�߸��� �ڵ�
            {
                pw = (PrintWriter) hash.get(msg.split("/")[0]);
                pw.println("����� �ڵ尡 �߸��� �ڵ��Դϴ�.");
                pw.flush();
            }
        }
    }
}