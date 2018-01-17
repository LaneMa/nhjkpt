package nhjkpt.configmanage.entity.departmentunit;

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
 * @Description: 分户单位用能
 * @author qf
 * @date 2014-11-19 22:06:40
 * @version V1.0   
 *
 */
@Entity
@Table(name = "departmentunit", schema = "")
@SuppressWarnings("serial")
public class DepartmentUnitEntity implements java.io.Serializable {
	/**主键id*/
	private java.lang.String id;
	/**系*/
	private java.lang.String departmentid;
	/**单位标识*/
	private java.lang.Integer unitid;
	/**单位名*/
	private java.lang.String unittext;
	/**单位总量*/
	private java.lang.Integer unitsum;
	
	/**水配额*/
	private java.lang.Double waterlimit;
	
	/**电配额*/
	private java.lang.Double electriclimit;
	
	/**气配额*/
	private java.lang.Double gaslimit;
	
	/**热配额*/
	private java.lang.Double heatlimit;
	
	
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  单位标识
	 */
	@Column(name ="UNITID",nullable=true,precision=10,scale=0)
	public java.lang.Integer getUnitid(){
		return this.unitid;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  单位标识
	 */
	public void setUnitid(java.lang.Integer unitid){
		this.unitid = unitid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位名
	 */
	@Column(name ="UNITTEXT",nullable=true,length=32)
	public java.lang.String getUnittext(){
		return this.unittext;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位名
	 */
	public void setUnittext(java.lang.String unittext){
		this.unittext = unittext;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  单位总量
	 */
	@Column(name ="UNITSUM",nullable=true,precision=10,scale=0)
	public java.lang.Integer getUnitsum(){
		return this.unitsum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  单位总量
	 */
	public void setUnitsum(java.lang.Integer unitsum){
		this.unitsum = unitsum;
	}
	
	
	@Column(name ="waterlimit",nullable=true,precision=22)
	public java.lang.Double getWaterlimit() {
		return waterlimit;
	}

	public void setWaterlimit(java.lang.Double waterlimit) {
		this.waterlimit = waterlimit;
	}

	@Column(name ="electriclimit",nullable=true,precision=22)
	public java.lang.Double getElectriclimit() {
		return electriclimit;
	}

	public void setElectriclimit(java.lang.Double electriclimit) {
		this.electriclimit = electriclimit;
	}

	@Column(name ="gaslimit",nullable=true,precision=22)
	public java.lang.Double getGaslimit() {
		return gaslimit;
	}

	public void setGaslimit(java.lang.Double gaslimit) {
		this.gaslimit = gaslimit;
	}

	@Column(name ="heatlimit",nullable=true,precision=22)
	public java.lang.Double getHeatlimit() {
		return heatlimit;
	}

	public void setHeatlimit(java.lang.Double heatlimit) {
		this.heatlimit = heatlimit;
	}
}
