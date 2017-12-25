import java.nio.IntBuffer;



public class TestBuffer {

	public static void main(String[] args) {
		System.out.println("*******************1 ����������*************************************" );
		// 1 ��������
		IntBuffer buf = IntBuffer.allocate(10);
		buf.put(13);// positionλ�ã�0 - > 1
		buf.put(21);// positionλ�ã�1 - > 2
		buf.put(35);// positionλ�ã�2 - > 3
		//��λ�ø�λΪ0��Ҳ����positionλ�ã�3 - > 0
		buf.flip();
		System.out.println("ʹ��flip��λ��" + buf);
		System.out.println("����Ϊ: " + buf.capacity());	//����һ����ʼ��������ı䣨warp��������������⣩
		System.out.println("����Ϊ: " + buf.limit());		//����ֻװ��������Ԫ��,���Կɶ�ȡ���߲�����Ԫ��Ϊ3 ��limit=3
		
		System.out.println("��ȡ�±�Ϊ1��Ԫ�أ�" + buf.get(1));
		System.out.println("get(index)������positionλ�ò��ı䣺" + buf);
		buf.put(1, 4);
		System.out.println("put(index, change)������positionλ�ò��䣺" + buf);;
		
		for (int i = 0; i < buf.limit(); i++) {
			//����get������ʹ�仺����λ�ã�position��������һλ
			System.out.print(buf.get() + "\t");
		}
		System.out.println("buf�������֮��Ϊ: " + buf);
		
		System.out.println("*******************2��wrap����ʹ��*************************************" );
		// 2 wrap����ʹ��
		//  wrap���������һ������: һ�������÷������ȳ�ʼ���������ĳ��ȣ���Ϊû�����壬��󻹻ᱻwrap�����������鸲�ǵ��� 
		//  ����wrap�����޸Ļ����������ʱ�����鱾��Ҳ����ŷ����仯��                     
		int[] arr = new int[]{1,2,5};
		IntBuffer buf1 = IntBuffer.wrap(arr);
		System.out.println(buf1);
		
		IntBuffer buf2 = IntBuffer.wrap(arr, 0 , 2);
		//����ʹ�ñ�ʾ����Ϊ����arr�ĳ��ȣ����ǿɲ�����Ԫ��ֻ��ʵ�ʽ��뻺������Ԫ�س���
		System.out.println(buf2);
		
		System.out.println("*******************2����������*************************************" );
		// 3 ��������
		IntBuffer buf4 = IntBuffer.allocate(10);
		int[] arr4 = new int[]{1,2,5};
		buf4.put(arr4);
		System.out.println(buf4);
		//һ�ָ��Ʒ���
		IntBuffer buf5 = buf1.duplicate();
		System.out.println(buf5);
		
		//����buf1��λ������
		//buf1.position(0);
		buf1.flip();
		System.out.println(buf1);
		
		System.out.println("�ɶ�����Ϊ��" + buf1.remaining());
		
		int[] arr2 = new int[buf1.remaining()];
		//�����������ݷ���arr2������ȥ
		buf1.get(arr2);
		for(int i : arr2){
			System.out.print(Integer.toString(i) + ",");
		}
		
		
	}

}
