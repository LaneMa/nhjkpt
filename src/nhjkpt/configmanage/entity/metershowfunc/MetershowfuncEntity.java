package nhjkpt.configmanage.entity.metershowfunc;

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
 * @Description: 电表需要展示的功能
 * @author zhangdaihao
 * @date 2013-07-17 23:09:43
 * @version V1.0   
 *
 */
@Entity
@Table(name = "metershowfunc", schema = "")
@SuppressWarnings("serial")
public class MetershowfuncEntity implements java.io.Serializable {
	/**主键id*/
	private java.lang.String id;
	/**表具id*/
	private java.lang.String meterid;
	/**功能号id*/
	private java.lang.String funcid;
	/**上限*/
	private java.lang.Double top;
	/**下限*/
	private java.lang.Double floor;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键id
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=32)
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
	 *@return: java.lang.String  表具id
	 */
	@Column(name ="METERID",nullable=false,length=32)
	public java.lang.String getMeterid(){
		return this.meterid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  表具id
	 */
	public void setMeterid(java.lang.String meterid){
		this.meterid = meterid;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  功能号id
	 */
	@Column(name ="FUNCID",nullable=false,length=32)
	public java.lang.String getFuncid(){
		return this.funcid;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  功能号id
	 */
	public void setFuncid(java.lang.String funcid){
		this.funcid = funcid;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  上限
	 */
	@Column(name ="TOP",nullable=true,precision=22)
	public java.lang.Double getTop(){
		return this.top;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  上限
	 */
	public void setTop(java.lang.Double top){
		this.top = top;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  下限
	 */
	@Column(name ="FLOOR",nullable=true,precision=22)
	public java.lang.Double getFloor(){
		return this.floor;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  下限
	 */
	public void setFloor(java.lang.Double floor){
		this.floor = floor;
	}
}
