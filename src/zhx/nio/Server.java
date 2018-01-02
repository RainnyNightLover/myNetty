package zhx.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Server implements Runnable{
	//��·������������ͨ����
	private Selector selector;
	//������������
	private ByteBuffer readBuffer = ByteBuffer.allocate(1024);
	//����д������
	private ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
	
	public Server(int port){
		try {
			//1���򿪶�·������
			this.selector = Selector.open();
			//2 �򿪷�����ͨ��
			ServerSocketChannel ssc = ServerSocketChannel.open();
			//3 ���÷�����ͨ��Ϊ������ģʽ
			ssc.configureBlocking(false);
			//4 �󶨵�ַ
			ssc.bind(new InetSocketAddress(port));
			//5 �ѷ�����ͨ��ע�ᵽ��·�������ϣ����Ҽ��������¼�
			ssc.register(this.selector, SelectionKey.OP_ACCEPT);
			
			System.out.println("Server start, port :" + port);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		for(;;){
			try {
				//1���öิ������ʼ����
				this.selector.select();
				//2 ���ض�·�������Ѿ�ѡ��Ľ����
				Iterator<SelectionKey> keys = this.selector.selectedKeys().iterator();
				//3������
				while(keys.hasNext()){
					//4 ��ȡһ��ѡ���Ԫ��
					SelectionKey key = keys.next();
					//5 ֱ�Ӵ��������Ƴ��Ϳ�����
					keys.remove();
					//6 �������Ч��
					if(key.isValid()){
						//���״̬Ϊ����
						if(key.isAcceptable()){
							accept(key);
						}
						
						//���״̬Ϊ�ɶ�
						if(key.isReadable()){
							read(key);
						}
						
						//���״̬Ϊ��д
						if(key.isWritable()){
							
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	private void read(SelectionKey key){
		try {
			//1�����������������
			this.readBuffer.clear();
			//2 ��ȡ֮ǰע���socketͨ������(�ͻ���ͨ��)
			SocketChannel sc = (SocketChannel)key.channel();
			//3 ��ȡ����
			int count = sc.read(this.readBuffer);
			//4 ���û������
			if(count==-1){
				key.channel().close();
				key.cancel();
				return ;
			}
			//5 ����������ж�ȡ ��ȡ֮ǰ��Ҫ���и�λ����(��position ��limit���и�λ)
			this.readBuffer.flip();
			//6 ���ݻ����������ݳ��ȴ�����Ӧ��С��byte���飬���ջ�����������
			byte[] bytes = new byte[this.readBuffer.remaining()];
			//7 ���ջ���������
			this.readBuffer.get(bytes);
			//8 ��ӡ���
			String body = new String(bytes).trim();
			System.out.println("Server : " + body);
			
			// 9..����д�ظ��ͻ������� 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void accept(SelectionKey key){
		try {
			//1 ��ȡ����ͨ��
			ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
			//2 ִ����������
			SocketChannel sc = ssc.accept();
			//3 ��������ģʽ
			sc.configureBlocking(false);
			//4 ע�ᵽ��·�������ϣ������ö�ȡ��ʶ
			sc.register(this.selector, SelectionKey.OP_READ);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Thread(new Server(9876)).start();
	}

}
