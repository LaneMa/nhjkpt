package nhjkpt.configmanage.entity.departmentmonthitemize;

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
 * @Description: 系月分类用能
 * @author qf
 * @date 2014-11-19 00:04:53
 * @version V1.0   
 *
 */
@Entity
@Table(name = "departmentmonthitemize", schema = "")
@SuppressWarnings("serial")
public class DepartmentMonthItemizeEntity implements java.io.Serializable {
	/**主键id*/
	private java.lang.String id;
	/**系*/
	private java.lang.String departmentid;
	/**分类*/
	private java.lang.String itemizeid;
	/**收到数据的时间*/
	private java.util.Date receivetime;
	/**用电量*/
	private java.lang.Double data;
	
	public DepartmentMonthItemizeEntity(){
		super();
	}
	public DepartmentMonthItemizeEntity(Date receivetime,Double data){
		super();
		this.receivetime=receivetime;
		this.data=data;
	}
	public DepartmentMonthItemizeEntity(String itemizeid,Date receivetime,Double data){
		super();
		this.itemizeid=itemizeid;
		this.receivetime=receivetime;
		this.data=data;
	}
	
	public DepartmentMonthItemizeEntity(String itemizeid, Double data){
		super();
		this.itemizeid=itemizeid;
		this.data=data;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键id
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
	 *@param: java.lang.String  主键id
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
	 *@return: java.util.Date  收到数据的时间
	 */
	@Column(name ="RECEIVETIME",nullable=true)
	public java.util.Date getReceivetime(){
		return this.receivetime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  收到数据的时间
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
