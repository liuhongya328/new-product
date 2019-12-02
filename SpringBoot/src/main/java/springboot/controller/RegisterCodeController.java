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
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import springboot.model.RegisterCode;
import springboot.model.User;
import springboot.service.RegisterCodeService;
import springboot.util.StringUtil;

/**
 * 
 * @author QiuSheng Lv
 *
 * @date 2019年8月16日
 */
@RestController
public class RegisterCodeController {
	@Autowired
	private RegisterCodeService registerCodeService;

	/**
	  * 查询注册码信息Title: findRegisterCode
	 * 
	 * @author QiuSheng Lv
	 * @date 2019年8月16日
	 */
	@RequestMapping("/findRegisterCode")
	@ResponseBody
	public Map<String, Object> findRegisterCode(int page, int rows, String search, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<RegisterCode> RegisterCodeList = new ArrayList<RegisterCode>();
		try {
			User loginUser = (User) request.getSession().getAttribute("user");
			if (!StringUtil.isBlank(loginUser)) {
				if (loginUser.getOrg().equals("管理员")&&loginUser.getLastOrg().equals("管理员")) {//最高权限管理员
				// 管理员
				} else if(!loginUser.getOrg().equals("管理员")&&loginUser.getLastOrg().equals("管理员")){//厂家管理员
					map.put("orgId", loginUser.getOrg());
				}
			}
			PageHelper.startPage(page, rows);
			map.put("search", search);
			RegisterCodeList = registerCodeService.findRegisterCode(map);
			PageInfo<RegisterCode> pageInfo = new PageInfo<RegisterCode>(RegisterCodeList);
			returnMap.put("pageInfo", pageInfo);
			returnMap.put("total", pageInfo.getTotal());
			returnMap.put("rows", pageInfo.getList());
		} catch (Exception e) {
			returnMap.put("returnCode", "fail");
		}
		return returnMap;
	}
	
	/**
	   * 生成注册码
	 Title: insertRegisterCode
	 *@author QiuSheng Lv
	 *@date 2019年8月20日
	 */
	@RequestMapping("/insertRegisterCode")
	@ResponseBody
	public Map<String, Object> insertRegisterCode(String supplierId, String orgId) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		RegisterCode registerCode = new RegisterCode();
		try {
			RegisterCode registerCodeById = registerCodeService.selectByOrgId(supplierId);
			if(StringUtil.isEmpty(registerCodeById)) {//查询该厂商下是否有管理员
				String verifyCode = StringUtil.VerifyCode();
				if(orgId.equals("管理员")) {//管理员
					registerCode.setIsmanager("1");
					registerCode.setOrgId(supplierId);
					registerCode.setLastOrg(orgId);
				}else if(orgId.equals("用户")) {//用户
					registerCode.setIsmanager("0");
					registerCode.setOrgId(orgId);
					registerCode.setLastOrg(supplierId);
				}
				registerCode.setId(StringUtil.UID());
				registerCode.setIsuse("0");//0.未使用  1.已使用
				registerCode.setCreateTime(nowDate);
				registerCode.setUpdateTime(nowDate);
				registerCode.setRegisterNumber(verifyCode);
				registerCodeService.insert(registerCode);
			returnMap.put("returnCode", "success");
			returnMap.put("verifyCode", verifyCode);
			}else {
				returnMap.put("returnCode", "exists");
			}
				
		} catch (Exception e) {
			returnMap.put("returnCode", "fail");
		}
		return returnMap;
	}
	
}
