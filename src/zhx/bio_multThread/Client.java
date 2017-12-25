package zhx.bio_multThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
*�ͻ���
 */
public class Client {
	final static String ADDRESS = "127.0.0.1";
	final static int PORT = 8765;
	
	public static void main(String[] args) throws Exception{
		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;
		for(int i=0;i<50;i++){//ģ�����ͻ���
			
			try{
					socket = new Socket(ADDRESS, PORT);//������������˵����ӣ����������û��������Connection refused�쳣
					//���Եõ�һ�����������ͻ��˵�Socket�����ϵ�getInputStream�����õ���������ʵ���Ǵӷ������˷��ص����ݡ�
					in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					//�õ�����һ����������ͻ��˵�Socket�����ϵ�getOutputStream�����õ����������ʵ���Ƿ��͸��������˵����ݡ����������ϵ�ʹ��
					out = new PrintWriter(socket.getOutputStream(), true);
					
					//��������˷�����Ϣ
					out.println("���ǿͻ�����Ϣ"+i);
					
					//��ȡ��������Ӧ��Ϣ
					String response = in.readLine();
					System.out.println("Client��ȡServer��Ӧ��Ϣ: " + response);
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(in != null){
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(out != null){
					try {
						out.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(socket != null){
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				socket = null;
			}
			
			Thread.sleep(1000);
		}
		

	}


}
