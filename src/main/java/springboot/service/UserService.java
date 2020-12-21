package springboot.service;

import java.util.List;
import java.util.Map;

import springboot.model.User;

public interface UserService {

	/**
	 * 查询所有用户信息 Title: findAllUser
	 * 
	 * @author QiuSheng Lv
	 * @date 2019-6-21
	 */
	public List<User> findAllUser(Map<String,Object> map);

	/**
	 * 根据机构和微信号查询用户 Title: fingUserByOrg
	 * 
	 * @author QiuSheng Lv
	 * @date 2019年6月26日
	 */
	List<User> findUserByOrg(Map<String,Object> map);

	public User findUserIsExist(Map<String,Object> map);

	/**
	 * 插入用户信息 Title: insert
	 * 
	 * @author QiuSheng Lv
	 * @date 2019-6-21
	 */
	public int insert(User user);

	public User checkPassword(User user);

	/**
	 * 根据unionid查询用户信息 Title: findUserInfo
	 * 
	 * @author QiuSheng Lv
	 * @date 2019-6-21
	 */
	public User findUserInfo(String unionid);

	/**
	 * 根据code查询微信用户信息 Title: loginByWeixin
	 * 
	 * @author QiuSheng Lv
	 * @date 2019-6-21
	 */
	public String loginByWeixin(String code);

	/**
	 * 删除用户(仅管理员) Title: deleteByPrimaryKey
	 * 
	 * @author QiuSheng Lv
	 * @date 2019-6-21
	 */
	int deleteByPrimaryKey(String userId);
	
	/**
	 * 修改用户信息
	 Title: updateByPrimaryKeySelective
	 *@author QiuSheng Lv
	 *@date 2019年7月11日
	 */
	int updateByPrimaryKeySelective(User user);
	
	/**
	 * 修改密码
	 Title: updatePwd
	 *@author QiuSheng Lv
	 *@date 2019年9月20日
	 */
	int updatePwd(String userId,String newPwd);
}
