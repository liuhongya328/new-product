package springboot.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import springboot.model.Product;
@Mapper
public interface ProductMapper {
	Product selectByPrimaryKey(String productId);

	/**
	 * 查询所有产品信息 Title: findAllProduct
	 */
	List<Product> findAllProduct(List<String> productIds);
	
	
	/**
	 * Title: findProductById
	 */
	Product findProductById(Map<String,Object> map);
}