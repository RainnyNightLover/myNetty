package zhx.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
* ����˴�����
 */
public class ServerHandler implements Runnable{
	private Socket socket;
	
	public ServerHandler(Socket socket){
		this.socket = socket;
	}

	@Override
	public void run() {
		BufferedReader in = null;
		PrintWriter out = null;
		try{
			//�õ�����һ��������������˵�Socket�����ϵ�getInputStream�����õ�����������ʵ���Ǵӿͻ��˷��͸��������˵���������
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			//�õ�����һ�������������˵�Socket�����ϵ�getOutputStream�����õ����������ʵ���Ƿ��͸��ͻ��˵����ݡ�
			out = new PrintWriter(this.socket.getOutputStream(), true);
			
			String msg = "";
			while(true){
				msg = in.readLine();
				if(msg==null) break;
				System.out.println("Server��ȡ Client��Ϣ:" + msg); //����ͻ��˷�����Ϣ
				out.println("���Ƿ���˵�����.");//����˷�����Ϣ���ͻ���
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(out!=null){
				try {
					out.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(socket !=null){
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

	
}
