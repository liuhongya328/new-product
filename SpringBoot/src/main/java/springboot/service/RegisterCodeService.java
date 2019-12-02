package springboot.service;

import java.util.List;
import java.util.Map;

import springboot.model.RegisterCode;

/**
 * 
 * @author QiuSheng Lv
 *
 * @date 2019-6-19
 */
public interface RegisterCodeService {

	/**
	 * 根据注册码序列号查询注册码详细信息
	 Title: findRegisterInfo
	 *@author QiuSheng Lv
	 *@date 2019年7月16日
	 */
	public RegisterCode findRegisterInfo(String registerNumber);
	
	/**
	 * 更新注册码信息表
	 Title: updateByPrimaryKeySelective
	 *@author QiuSheng Lv
	 *@date 2019年7月16日
	 */
	int updateByPrimaryKeySelective(RegisterCode record);
	
	/**
	 * 新增注册码信息
	 Title: insert
	 *@author QiuSheng Lv
	 *@date 2019年8月20日
	 */
	int insert(RegisterCode record);
	
	/**
	   * 查询注册码信息列表
	 Title: findRegisterCode
	 *@author QiuSheng Lv
	 *@date 2019年8月16日
	 */
	public List<RegisterCode> findRegisterCode(Map<String, Object> map);
	
	/**
	 * 根据orgId查询注册码信息
	 Title: selectByOrgId
	 *@author QiuSheng Lv
	 *@date 2019年9月21日
	 */
	RegisterCode selectByOrgId(String orgId);
}
