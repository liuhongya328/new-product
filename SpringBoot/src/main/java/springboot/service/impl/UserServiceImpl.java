package springboot.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import springboot.common.Httprequests;
import springboot.mapper.UserMapper;
import springboot.model.User;
import springboot.service.UserService;
/**
 * 
 * @author QiuSheng Lv
 *
 * @date 2019-6-21
 */
@Service
public class UserServiceImpl implements UserService {

	final String APPID = "wx1ca811d21faa09f5";
	final String SECRET = "e6935007dba5cae452b1ba4c425b0e0e";
	@Autowired
	private UserMapper userMapper;

	/**
	 * 查询所有用户信息
	 */
	@Override
	public List<User> findAllUser(Map<String, Object> map) {
		
		return userMapper.findAllUser(map);
	}

	/**
	 * 根据传入的用户的用户名在数据库中进行查找 如果该用户存在就将该用户返回，如果不存在则返回的就为null
	 * 
	 * @param users
	 *            用户的pojo类
	 * @return
	 */
	@Override
	public User findUserIsExist(Map<String,Object> map) {
		return userMapper.findUserIsExist(map);
	}

	/**
	 * 将用户信息存入数据库
	 * 
	 * @param users
	 * @return
	 */
	@Override
	public int insert(User user) {
		// TODO Auto-generated method stub
		return userMapper.insert(user);
	}

	/**
	 * 通过用户名校验用户密码是否正确，如正确则将查询到的对象返回 若果错误则返回null
	 * 
	 * @param users
	 * @return
	 */
	@Override
	public User checkPassword(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findUserInfo(String unionid) {

		return userMapper.findUserInfo(unionid);
	}

	@Override
	public String loginByWeixin(String code) {
		// 发送
		// https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
		// 获取用户的openid和session_key
		// 注意这个是 WeChatTool.wxspAppid 是微信小程序的appid 从微信小程序后台获取
		// WeChatTool.wxspSecret 这个也一样，我这里是用了常量来进行保存方便多次使用
		String params = "appid=wx1ca811d21faa09f5&secret=e6935007dba5cae452b1ba4c425b0e0e"+"&js_code=" + code
				+ "&grant_type=authorization_code";
		String url = "https://api.weixin.qq.com/sns/jscode2session";
		String sendGet = Httprequests.sendGet(url, params); // 发起请求拿到key和openid
		return sendGet;
	}

	/**
	 * 根据userId删除用户信息
	 */
	@Override
	public int deleteByPrimaryKey(String userId) {
		// TODO Auto-generated method stub
		return userMapper.deleteByPrimaryKey(userId);
	}

	/**
	 * 根据厂家和微信号查询用户信息
	 */
	@Override
	public List<User> findUserByOrg(Map<String, Object> map) {
		return userMapper.findUserByOrg(map);
	}

	@Override
	public int updateByPrimaryKeySelective(User user) {
		return userMapper.updateByPrimaryKeySelective(user);
	}

	/**
	 * 根据userId修改用户密码
	 */
	@Override
	public int updatePwd(String userId,String userPaw) {
		return userMapper.updatePwd(userId,userPaw);
	}

}
