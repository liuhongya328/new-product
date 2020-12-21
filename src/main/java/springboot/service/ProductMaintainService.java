package springboot.service;

import springboot.model.ProductMaintain;

public interface ProductMaintainService {

	/**
	 * 插入激活信息
	 Title: insertSelective
	 *@author QiuSheng Lv
	 *@date 2019年7月10日
	 */
	public int insertSelective(ProductMaintain productMaintain);
	
	/**
	 * 根据产品编号查询激活信息
	 Title: findProductMaintainById
	 *@author QiuSheng Lv
	 *@date 2019年7月10日
	 */
	public ProductMaintain findProductMaintainById(String productId);
}
