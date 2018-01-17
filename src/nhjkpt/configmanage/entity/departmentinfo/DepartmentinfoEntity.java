package nhjkpt.configmanage.entity.departmentinfo;

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
 * @Description: 系基本信息
 * @author qf
 * @date 2014-11-18 23:36:23
 * @version V1.0   
 *
 */
@Entity
@Table(name = "departmentinfo", schema = "")
@SuppressWarnings("serial")
public class DepartmentinfoEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**系名*/
	private java.lang.String departmentname;
	/**系编号*/
	private java.lang.String departmentid;
	/**系描述*/
	private java.lang.String departmenttext;
	
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
	 *@return: java.lang.String  系名
	 */
	@Column(name ="DEPARTMENTNAME",nullable=true,length=100)
	public java.lang.String getDepartmentname(){
		return this.departmentname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  系名
	 */
	public void setDepartmentname(java.lang.String departmentname){
		this.departmentname = departmentname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  系编号
	 */
	@Column(name ="DEPARTMENTID",nullable=true,length=100)
	public java.lang.String getDepartmentid(){
		return this.departmentid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  系编号
	 */
	public void setDepartmentid(java.lang.String departmentid){
		this.departmentid = departmentid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  系描述
	 */
	@Column(name ="DEPARTMENTTEXT",nullable=true,length=1000)
	public java.lang.String getDepartmenttext(){
		return this.departmenttext;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  系描述
	 */
	public void setDepartmenttext(java.lang.String departmenttext){
		this.departmenttext = departmenttext;
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
