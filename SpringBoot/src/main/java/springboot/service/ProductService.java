package springboot.service;

import springboot.model.Product;

public interface ProductService {

	/**
	 * 根据产品编号查询产品
	 * @throws Exception 
	 */
	public Product findProductById(String productId) throws Exception;
	
}
