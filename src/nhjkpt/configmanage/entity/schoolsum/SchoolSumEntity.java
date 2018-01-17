package nhjkpt.configmanage.entity.schoolsum;

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
 * @Description: 学校水电气总量计算表
 * @author qf
 * @date 2013-07-31 19:22:49
 * @version V1.0   
 *
 */
@Entity
@Table(name = "schoolsum", schema = "")
@SuppressWarnings("serial")
public class SchoolSumEntity implements java.io.Serializable {
	/**主键id*/
	private java.lang.String id;
	/**功能号*/
	private java.lang.String funcid;
	/**测量表具唯一标识*/
	private java.lang.String meterid;
	/**拆分系数*/
	private java.lang.Integer factor;
	
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
	 *@return: java.lang.String  功能号
	 */
	@Column(name ="FUNCID",nullable=true,length=32)
	public java.lang.String getFuncid(){
		return this.funcid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  功能号
	 */
	public void setFuncid(java.lang.String funcid){
		this.funcid = funcid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  测量表具唯一标识
	 */
	@Column(name ="METERID",nullable=true,length=32)
	public java.lang.String getMeterid(){
		return this.meterid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  测量表具唯一标识
	 */
	public void setMeterid(java.lang.String meterid){
		this.meterid = meterid;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  拆分系数
	 */
	@Column(name ="FACTOR",nullable=true,precision=10,scale=0)
	public java.lang.Integer getFactor(){
		return this.factor;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  拆分系数
	 */
	public void setFactor(java.lang.Integer factor){
		this.factor = factor;
	}
}
