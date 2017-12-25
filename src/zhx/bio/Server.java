package zhx.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
* �ͻ���
 */
public class Server {

	final static int PROT = 8765;//�˿�
	
	public static void main(String[] args) {
		ServerSocket serverSocker = null;
		try {
			serverSocker = new ServerSocket(PROT);//�����˿�
			System.out.println("Server ����.......");
			//��������(��ȡ�ͻ���socket)
			Socket socket = serverSocker.accept();
			
			//�½�һ���߳�ִ�пͻ��˵�����ÿһ���ͻ��ˣ�����˶����½�һ���̴߳���
			new Thread(new ServerHandler(socket)).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(serverSocker!=null){
				try {
					serverSocker.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}

	}

}
