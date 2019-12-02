package springboot.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.mapper.SupplierMapper;
import springboot.model.Supplier;
import springboot.service.SupplierService;

/**
 * 
 * @author QiuSheng Lv
 *
 * @date 2019年8月16日
 */
@Service
public class SupplierServiceImpl implements SupplierService {
	@Autowired
	private SupplierMapper supplierMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return supplierMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Supplier record) {
		// TODO Auto-generated method stub
		return supplierMapper.insert(record);
	}

	@Override
	public int insertSelective(Supplier record) {
		// TODO Auto-generated method stub
		return supplierMapper.insertSelective(record);
	}

	@Override
	public Supplier selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return supplierMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Supplier record) {
		// TODO Auto-generated method stub
		return supplierMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Supplier record) {
		// TODO Auto-generated method stub
		return supplierMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Supplier> findSuppliers(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return supplierMapper.findSuppliers(map);
	}

	@Override
	public Supplier selectByName(String supplierName) {
		// TODO Auto-generated method stub
		return supplierMapper.selectByName(supplierName);
	}

}
