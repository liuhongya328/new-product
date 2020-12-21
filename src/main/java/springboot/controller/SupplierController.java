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
import springboot.model.Supplier;
import springboot.model.User;
import springboot.service.SupplierService;
import springboot.util.StringUtil;

/**
 * 
 * @author QiuSheng Lv
 *
 * @date 2019年8月16日
 */
@RestController
public class SupplierController {
	@Autowired
	private SupplierService supplierService;
	
	/**
	 * 查询厂商列表
	 Title: findSupplier
	 *@author QiuSheng Lv
	 *@date 2019年8月16日
	 */
	@RequestMapping("/findSuppliers")
	@ResponseBody
	public Map<String, Object> findSuppliers(int page,int rows,String search,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<Supplier> suppliers = new ArrayList<Supplier>();
		try {
			PageHelper.startPage(page, rows);
			map.put("search", search);
			suppliers = supplierService.findSuppliers(map);
			PageInfo<Supplier> pageInfo = new PageInfo<Supplier>(suppliers);
			returnMap.put("pageInfo", pageInfo);
			returnMap.put("total", pageInfo.getTotal());
			returnMap.put("rows", pageInfo.getList());
		} catch (Exception e) {
			returnMap.put("returnCode", "fail");
		}
		return returnMap;
	}
	
	/**
	 * 新增厂商信息
	 Title: insertSupplier
	 *@author QiuSheng Lv
	 *@date 2019年7月11日
	 */
	@RequestMapping("/insertSupplier")
	@ResponseBody
	public Map<String, Object> insertSupplier(Supplier supplier){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		try {
			Supplier supplierByName = supplierService.selectByName(supplier.getSuppliername());
			if(StringUtil.isEmpty(supplierByName)) {//判断厂商是否已存在
				supplier.setId(StringUtil.UID());
				supplier.setCreatetime(nowDate);
				supplier.setUpdatetime(nowDate);
				int i = supplierService.insert(supplier);
				if(i==1) {
					returnMap.put("msg", "success");
				}else {
					returnMap.put("msg", "fail");
				}
			}else {
				returnMap.put("msg", "exists");
			}
			
		} catch (Exception e) {
			returnMap.put("msg", "fail");
		}
		return returnMap;
	}
	
	/**
	 * 删除用户（仅管理员） Title: delete
	 * 
	 * @author QiuSheng Lv
	 * @date 2019-6-22
	 */
	@RequestMapping("/deleteSupplier")
	@ResponseBody
	public int deleteSupplier(String id) {
		return supplierService.deleteByPrimaryKey(id);
	}
	
	/**
	   * 编辑厂商信息
	 * 
	 * @author QiuSheng Lv
	 * @date 2019-6-22
	 */
	@RequestMapping("/updateSupplier")
	@ResponseBody
	public Map<String, Object> updateSupplier(Supplier supplier) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		try {
			supplier.setUpdatetime(nowDate);
			int i = supplierService.updateByPrimaryKeySelective(supplier);
			if(i==1) {
				returnMap.put("msg", "success");
			}else {
				returnMap.put("msg", "fail");
			}
		} catch (Exception e) {
			returnMap.put("msg", "fail");
		}
		return returnMap;
		
	}
	
	/**
	 * 查询厂商信息（不分页） Title: findAllSupplier
	 * 
	 * @author QiuSheng Lv
	 * @date 2019年7月11日
	 */

	@RequestMapping("/findAllSupplier")
	@ResponseBody
	public List<Supplier> findAllSupplier(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
			List<Supplier> supplierList = new ArrayList<Supplier>();
			User loginUser = (User) request.getSession().getAttribute("user");
			if (!StringUtil.isBlank(loginUser)) {
				if (loginUser.getOrg().equals("管理员")&&loginUser.getLastOrg().equals("管理员")) {//最高权限管理员
				// 管理员
				} else if(!loginUser.getOrg().equals("管理员")&&loginUser.getLastOrg().equals("管理员")){//厂家管理员
					map.put("orgId", loginUser.getOrg());
				}
			}
			supplierList = supplierService.findSuppliers(map);
		return supplierList;
	}
}
