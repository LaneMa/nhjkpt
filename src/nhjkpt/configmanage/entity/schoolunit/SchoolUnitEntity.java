package nhjkpt.configmanage.entity.schoolunit;

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
 * @Description: 学校单位用能统计计算表
 * @author qf
 * @date 2013-08-02 01:14:55
 * @version V1.0   
 *
 */
@Entity
@Table(name = "schoolunit", schema = "")
@SuppressWarnings("serial")
public class SchoolUnitEntity implements java.io.Serializable {
	/**主键id*/
	private java.lang.String id;
	/**单位编码*/
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  单位编码
	 */
	@Column(name ="UNITID",nullable=true,precision=10,scale=0)
	public java.lang.Integer getUnitid(){
		return this.unitid;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  单位编码
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
