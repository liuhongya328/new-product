package springboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.mapper.ProductMaintainMapper;
import springboot.model.ProductMaintain;
import springboot.service.ProductMaintainService;
/**
 * 
 * @author QiuSheng Lv
 *
 * @date 2019年7月10日
 */
@Service
public class ProductMaintainServiceImpl implements ProductMaintainService {
	@Autowired
	private ProductMaintainMapper productMaintainMapper;
	
	/**
	 * 插入激活信息
	 */
	@Override
	public int insertSelective(ProductMaintain productMaintain) {
		// TODO Auto-generated method stub
		return productMaintainMapper.insertSelective(productMaintain);
	}

	@Override
	public ProductMaintain findProductMaintainById(String productId) {
		// TODO Auto-generated method stub
		return productMaintainMapper.selectByPrimaryKey(productId);
	}

}
