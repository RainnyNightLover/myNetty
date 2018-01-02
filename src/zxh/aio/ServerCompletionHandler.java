package zxh.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ServerCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Server> {

	@Override
	public void completed(AsynchronousSocketChannel asc, Server attachment) {
		//������һ���ͻ��˽����ʱ�� ֱ�ӵ���Server��accept��������������ִ����ȥ����֤����ͻ��˶���������
		attachment.assc.accept(attachment, this);
		read(asc);
	}
	
	private void read(final AsynchronousSocketChannel asc){
		//������������
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		asc.read(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {

			@Override
			public void completed(Integer resultSize, ByteBuffer attachment) {
				//���ж�ȡ֮��,���ñ�ʶλ
				attachment.flip();
				//��ö�ȡ���ֽ���
				System.out.println("Server -> " + "�յ��ͻ��˵����ݳ���Ϊ:" + resultSize);
				//��ȡ��ȡ������
				String resultData = new String(attachment.array()).trim();
				System.out.println("Server -> " + "�յ��ͻ��˵�������ϢΪ:" + resultData);
				String response = "��������Ӧ, �յ��˿ͻ��˷���������: " + resultData;
				write(asc, response);
				
			}

			@Override
			public void failed(Throwable e, ByteBuffer attachment) {
				e.printStackTrace();
			}
			
		});
	}
	
	private void write(final AsynchronousSocketChannel asc,String responseMsg){
		//1������д������
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		//2��д������
		byteBuffer.put(responseMsg.getBytes());
		//3����λ
		byteBuffer.flip();
		//4��д��ܵ�
		asc.write(byteBuffer);
		
	}

	@Override
	public void failed(Throwable e, Server server) {
		System.out.println("======failed=====");
		e.printStackTrace();
		
	}

}
