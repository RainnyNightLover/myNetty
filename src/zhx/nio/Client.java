package zhx.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {

	public static void main(String[] args) {
		//�������ӵĵ�ַ
		InetSocketAddress  address = new InetSocketAddress(9876);
		//��������ͨ��
		SocketChannel sc = null;
		//����������
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		try{
			//��ͨ��
			sc = SocketChannel.open();
			//��������
			sc.connect(address);
			
			for(;;){
				//����һ���ֽ����飬Ȼ��ʹ��ϵͳ¼�빦�ܣ�
				byte[] bytes = new byte[1024];
				System.in.read(bytes);
				
				//�����ݷŵ���������
				byteBuffer.put(bytes);
				//�Ի��������и�λ
				byteBuffer.flip();
				//ͨ��д������
				sc.write(byteBuffer);
				//��ջ���������
				byteBuffer.clear();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(sc!=null){
				try {
					sc.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		

	}

}
