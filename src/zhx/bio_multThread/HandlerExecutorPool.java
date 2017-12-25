package zhx.bio_multThread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
* @Description: �������̳߳�
 */
public class HandlerExecutorPool {
	private ExecutorService executor;
	
	public HandlerExecutorPool(int maxPoolSize,int queueSize){
		//�Զ����̳߳�
		this.executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), 
				maxPoolSize, 
				120L,
				TimeUnit.SECONDS, 
				new ArrayBlockingQueue<Runnable>(queueSize));
		
	}
	
	/**
	* ִ���̷߳���
	 */
	public void execute(Runnable runnable){
		this.executor.execute(runnable);
	}
	

}
