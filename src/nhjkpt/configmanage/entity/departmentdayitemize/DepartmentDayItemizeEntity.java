package nhjkpt.configmanage.entity.departmentdayitemize;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;

/**   
 * @Title: Entity
 * @Description: 系天分类用能
 * @author qf
 * @date 2014-11-19 00:01:36
 * @version V1.0   
 *
 */
@Entity
@Table(name = "departmentdayitemize", schema = "")
@SuppressWarnings("serial")
public class DepartmentDayItemizeEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**系*/
	private java.lang.String departmentid;
	/**分类*/
	private java.lang.String itemizeid;
	/**时间*/
	private java.util.Date receivetime;
	/**用电量*/
	private java.lang.Double data;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=true,length=32)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  系
	 */
	@Column(name ="DEPARTMENTID",nullable=true,length=32)
	public java.lang.String getDepartmentid(){
		return this.departmentid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  系
	 */
	public void setDepartmentid(java.lang.String departmentid){
		this.departmentid = departmentid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分类
	 */
	@Column(name ="ITEMIZEID",nullable=true,length=32)
	public java.lang.String getItemizeid(){
		return this.itemizeid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分类
	 */
	public void setItemizeid(java.lang.String itemizeid){
		this.itemizeid = itemizeid;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  时间
	 */
	@Column(name ="RECEIVETIME",nullable=true)
	public java.util.Date getReceivetime(){
		return this.receivetime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  时间
	 */
	public void setReceivetime(java.util.Date receivetime){
		this.receivetime = receivetime;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  用电量
	 */
	@Column(name ="DATA",nullable=true,precision=22)
	public java.lang.Double getData(){
		return this.data;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  用电量
	 */
	public void setData(java.lang.Double data){
		this.data = data;
	}
}
