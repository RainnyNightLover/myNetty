package zhx.bio_multThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	final static int PORT = 8765;
	
	public static void main(String[] args){
		ServerSocket serverSocker = null;
		
		try{
			serverSocker = new ServerSocket(PORT);//�����˿�
			System.out.println("Server ����");
			
			Socket socket = null;
			HandlerExecutorPool executorPool = new HandlerExecutorPool(50,1000);//�Զ����̳߳أ�
			while(true){//�����һֱ����
				socket = serverSocker.accept();//��������(��ȡ�ͻ���socket)
				executorPool.execute(new ServerHandler(socket));//���߳��д���ͻ��ˣ�һ���̴߳���һ���ͻ��ˣ�
			}
			
		}catch(Exception e){
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
