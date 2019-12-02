package springboot.service;

import java.util.List;
import java.util.Map;

import springboot.model.Product;
import springboot.model.RegisterCode;
import springboot.model.Supplier;

public interface SupplierService {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table supplier
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table supplier
     *
     * @mbg.generated
     */
    int insert(Supplier record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table supplier
     *
     * @mbg.generated
     */
    int insertSelective(Supplier record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table supplier
     *
     * @mbg.generated
     */
    Supplier selectByPrimaryKey(String id);

    /**
              * 编辑厂商信息
     Title: updateByPrimaryKeySelective
     *@author QiuSheng Lv
     *@date 2019年8月17日
     */
    int updateByPrimaryKeySelective(Supplier record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table supplier
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Supplier record);
    
    /**
             * 查询厂商列表
     Title: findSuppliers
     *@author QiuSheng Lv
     *@date 2019年8月16日
     */
    public List<Supplier> findSuppliers(Map<String, Object> map);
    
    /**
             * 根据厂商名查询厂商信息
     Title: selectByName
     *@author QiuSheng Lv
     *@date 2019年9月21日
     */
    Supplier selectByName(String supplierName);
}