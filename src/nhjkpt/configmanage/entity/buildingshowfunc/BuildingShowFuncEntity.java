package nhjkpt.configmanage.entity.buildingshowfunc;

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
 * @Description: 大楼需要展示的瞬时功能
 * @author qf
 * @date 2013-08-02 00:44:43
 * @version V1.0   
 *
 */
@Entity
@Table(name = "buildingshowfunc", schema = "")
@SuppressWarnings("serial")
public class BuildingShowFuncEntity implements java.io.Serializable {
	/**主键id*/
	private java.lang.String id;
	/**大楼*/
	private java.lang.String buildingid;
	/**唯一标识*/
	private java.lang.Integer showid;
	/**瞬时描述*/
	private java.lang.String showtext;
	/**表具*/
	private java.lang.String meterid;
	/**功能*/
	private java.lang.String funcid;
	
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
	 *@return: java.lang.String  大楼
	 */
	@Column(name ="BUILDINGID",nullable=true,length=16)
	public java.lang.String getBuildingid(){
		return this.buildingid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  大楼
	 */
	public void setBuildingid(java.lang.String buildingid){
		this.buildingid = buildingid;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  唯一标识
	 */
	@Column(name ="SHOWID",nullable=true,precision=10,scale=0)
	public java.lang.Integer getShowid(){
		return this.showid;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  唯一标识
	 */
	public void setShowid(java.lang.Integer showid){
		this.showid = showid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  瞬时描述
	 */
	@Column(name ="SHOWTEXT",nullable=true,length=32)
	public java.lang.String getShowtext(){
		return this.showtext;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  瞬时描述
	 */
	public void setShowtext(java.lang.String showtext){
		this.showtext = showtext;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  表具
	 */
	@Column(name ="METERID",nullable=true,length=32)
	public java.lang.String getMeterid(){
		return this.meterid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  表具
	 */
	public void setMeterid(java.lang.String meterid){
		this.meterid = meterid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  功能
	 */
	@Column(name ="FUNCID",nullable=true,length=32)
	public java.lang.String getFuncid(){
		return this.funcid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  功能
	 */
	public void setFuncid(java.lang.String funcid){
		this.funcid = funcid;
	}
}
