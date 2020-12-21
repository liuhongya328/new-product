package springboot.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.mapper.ProductMapper;
import springboot.model.Product;
import springboot.service.ProductService;

/**
 * 
 * @author liuhongya328
 *
 * @date 2019-11-21
 */
@Service
public class ProductServiceImpl implements ProductService {

	class Request{
		String productId;
		
		CompletableFuture<Product> future;
	}
	
	@Autowired
	private ProductMapper productMapper;

	//队列用来存储请求
	BlockingQueue<Request> queue = new LinkedBlockingQueue<Request>();
	
	@PostConstruct
	public void init() {
		//创建定时任务线程池,每隔50ms就执行一次,读取队列中的请求，放入到统一list中
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		scheduledExecutorService.scheduleAtFixedRate(() -> {
			int size = queue.size();
			if(size == 0 )
				return;
			ArrayList<Request> requests = new ArrayList<Request>();
			for(int i = 0; i < size; i++) {
				Request request = queue.poll();
				requests.add(request);
			}
			System.out.println("现在批量请求了:"+size+"条请求");
			List<String> productIds = new ArrayList<String>();
			for(Request request : requests) {
				productIds.add(request.productId);
			}
			
			List<Product> responses = productMapper.findAllProduct(productIds);
			//请求合并完成,开始将结果分发返回给每一个具体的request
			Map<String,Product> returnMap = new HashMap<String,Product>();
			for(Product product : responses) {
				returnMap.put(product.getProductId(), product);
			}
			
			for(Request request : requests) {
				Product product = returnMap.get(request.productId);
				request.future.complete(product);
			}
			
		}, 0, 50, TimeUnit.MILLISECONDS);
	}
	
	//正常的业务逻辑,每次请求从controller传递来调用一次service的方法，去数据库或者调用远程接口获取一次数据。
//	@Override
//	public Product findProductById(String productId) {
//		
//		System.out.println(System.currentTimeMillis()+":"+Thread.currentThread().getName()+"--进行请求！");
//		return productMapper.selectByPrimaryKey(productId);
//	}

	//请求合并-->2000次请求同时执行，前提需要有批量查询的方法或者接口
	@Override
	public Product findProductById(String productId) throws Exception {
		Request request = new Request();
		request.productId = productId;
		CompletableFuture<Product> future = new CompletableFuture<>();
		request.future = future;
		//拦截请求,将请求放入容器
		queue.add(request);
		
		//放弃单一调用
//		return productMapper.selectByPrimaryKey(productId);
		
		//用future是因为需要从另外个线程中取数据,线程中的数据交互可以用future
		return future.get();
	}
}
