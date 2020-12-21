package springboot.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import springboot.model.Product;
import springboot.service.ProductService;

/**
 * 
 * @author liuhongya328
 *
 * @date 2019-11-21
 */
@RestController
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	/**
	 * 根据产品编号查询产品信息
	 * @throws Exception 
	 */
	@RequestMapping("/findProductById")
	@ResponseBody
	public Map<String, Object> findProductById(String productId) throws Exception{
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Product product = productService.findProductById(productId);
		returnMap.put("returnValue",product);
		return returnMap;
	}
	
}
