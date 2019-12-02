package springboot.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.mapper.RegisterCodeMapper;
import springboot.model.RegisterCode;
import springboot.service.RegisterCodeService;

/**
 * 
 * @author QiuSheng Lv
 * 
 * @date 2019-6-19
 */
@Service
public class RegisterCodeServiceImpl implements RegisterCodeService {

	@Autowired
	private RegisterCodeMapper registerCodeMapper;

	public RegisterCode findRegisterInfo(String registerNumber) {
		// TODO Auto-generated method stub
		return registerCodeMapper.findRegisterInfo(registerNumber);
	}

	@Override
	public int updateByPrimaryKeySelective(RegisterCode record) {
		// TODO Auto-generated method stub
		return registerCodeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<RegisterCode> findRegisterCode(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return registerCodeMapper.findRegisterCode(map);
	}

	@Override
	public int insert(RegisterCode record) {
		// TODO Auto-generated method stub
		return registerCodeMapper.insert(record);
	}

	@Override
	public RegisterCode selectByOrgId(String orgId) {
		// TODO Auto-generated method stub
		return registerCodeMapper.selectByOrgId(orgId);
	}

}
