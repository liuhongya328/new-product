package springboot.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import springboot.model.User;
import springboot.service.UserService;
import springboot.util.StringUtil;
import com.alibaba.fastjson.JSON;


/**
 * sss
 * @author QiuSheng Lv
 * 
 * @date 2019-6-21
 */
@Controller
public class UserController {

	@Autowired
	private UserService userService;

	String nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	
	/**
	 * 查询所有用户信息 Title: findAllUser
	 * 
	 * @author QiuSheng Lv
	 * @date 2019-6-22
	 */
	@RequestMapping("/findAllUser")
	@ResponseBody
	public Map<String, Object> findAllUser(int page,int rows,String search,HttpServletRequest request) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<User> user = new ArrayList<User>();
		try {
			User loginUser = (User) request.getSession().getAttribute("user");
			if (!StringUtil.isBlank(loginUser)) {
				if (loginUser.getOrg().equals("管理员")&&loginUser.getLastOrg().equals("管理员")) {//最高权限管理员
				// 管理员
				} else if(!loginUser.getOrg().equals("管理员")&&loginUser.getLastOrg().equals("管理员")){//厂家管理员
					map.put("orgId", loginUser.getOrg());
				}
			}
			map.put("search", search);
			PageHelper.startPage(page, rows);
			user = userService.findAllUser(map);
			PageInfo<User>  pageUser = new PageInfo<User>(user);
			returnMap.put("pageInfo", pageUser);
			returnMap.put("total", pageUser.getTotal());
			returnMap.put("rows", pageUser.getList());
		} catch (Exception e) {
			returnMap.put("returnCode", "fail");
		}
		return returnMap;
	}

	/**
	 * 删除用户（仅管理员） Title: delete
	 * 
	 * @author QiuSheng Lv
	 * @date 2019-6-22
	 */
	@RequestMapping("/deleteUser")
	@ResponseBody
	public int delete(String userId) {
		return userService.deleteByPrimaryKey(userId);
	}
	
	/**
	 * 根据厂家和微信号查询用户信息
	 Title: fingUserByOrg
	 *@author QiuSheng Lv
	 *@date 2019年6月26日
	 */
	@RequestMapping("/findUserByOrg")
	@ResponseBody
	public Map<String, Object> findUserByOrg(int page,int rows,String unionid,String org){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<User> userList = new ArrayList<User>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("unionid", unionid);
		map.put("org", org);
		PageHelper.startPage(page, rows);
		userList = userService.findUserByOrg(map);
		PageInfo<User> pageInfo = new PageInfo<User>(userList);
		returnMap.put("pageInfo",pageInfo);
		returnMap.put("total", pageInfo.getTotal());
		returnMap.put("rows", pageInfo.getList());
		return returnMap;
	}
	
	/**
	 * 新增用户信息
	 Title: insertUser
	 *@author QiuSheng Lv
	 *@date 2019年7月11日
	 */
	@RequestMapping("/insertUser")
	@ResponseBody
	public Map<String, Object> insertUser(User user){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			user.setUserPaw(StringUtil.md5(user.getUserPaw()));
			user.setIsregister("0");
			user.setUserId(StringUtil.UID());
			user.setCreateTime(nowDate);
			user.setUpdateTime(nowDate);
			int i = userService.insert(user);
			if(i==1) {
				returnMap.put("msg", "success");
			}else {
				returnMap.put("msg", "fail");
			}
		} catch (Exception e) {
			// TODO: handle exception
			returnMap.put("msg", "fail");
		}
		
		return returnMap;
	}
	
	/**
	 * 跳转修改用户信息页面
	 Title: updateUserPage
	 *@author QiuSheng Lv
	 *@date 2019年7月14日
	 */
	@RequestMapping("/updateUserPage")
	public String updateUserPage(Model model,User user) {
		User  userInfo = new User();
		userInfo =	userService.findUserInfo(user.getUserId());
		model.addAttribute("user", userInfo);
		return "updateUser";
	}
	/**
	 * 修改用户信息
	 Title: updateUser
	 *@author QiuSheng Lv
	 *@date 2019年7月11日
	 */
	@RequestMapping("/updateUser")
	@ResponseBody
	public Map<String,Object> updateUser(User user){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			user.setUpdateTime(nowDate);
			int i = userService.updateByPrimaryKeySelective(user);
			if(i==1) {
				returnMap.put("msg", "success");
			}else {
				returnMap.put("msg", "fail");
			}
		} catch (Exception e) {
			// TODO: handle exception
			returnMap.put("msg", "fail");
		}
		return returnMap;
	}
}
