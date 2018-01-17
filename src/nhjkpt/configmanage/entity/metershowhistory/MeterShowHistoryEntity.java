package nhjkpt.configmanage.entity.metershowhistory;

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
 * @Description: 瞬时基本信息
 * @author zhangdaihao
 * @date 2013-08-29 19:13:27
 * @version V1.0   
 *
 */
@Entity
@Table(name = "metershowhistory", schema = "")
@SuppressWarnings("serial")
public class MeterShowHistoryEntity implements java.io.Serializable {
	/**主键id*/
	private java.lang.String id;
	/**大楼id*/
	private java.lang.String buildingid;
	/**gatewayid*/
	private java.lang.String gatewayid;
	/**包类型*/
	private java.lang.String datatype;
	/**采集时间*/
	private java.util.Date senddate;
	/**sequence*/
	private java.lang.String sequence;
	/**电表*/
	private java.lang.String meterid;
	
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
	 *@return: java.lang.String  大楼id
	 */
	@Column(name ="BUILDINGID",nullable=true,length=32)
	public java.lang.String getBuildingid(){
		return this.buildingid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  大楼id
	 */
	public void setBuildingid(java.lang.String buildingid){
		this.buildingid = buildingid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  gatewayid
	 */
	@Column(name ="GATEWAYID",nullable=true,length=32)
	public java.lang.String getGatewayid(){
		return this.gatewayid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  gatewayid
	 */
	public void setGatewayid(java.lang.String gatewayid){
		this.gatewayid = gatewayid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  包类型
	 */
	@Column(name ="DATATYPE",nullable=true,length=32)
	public java.lang.String getDatatype(){
		return this.datatype;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  包类型
	 */
	public void setDatatype(java.lang.String datatype){
		this.datatype = datatype;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  采集时间
	 */
	@Column(name ="SENDDATE",nullable=true)
	public java.util.Date getSenddate(){
		return this.senddate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  采集时间
	 */
	public void setSenddate(java.util.Date senddate){
		this.senddate = senddate;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  sequence
	 */
	@Column(name ="SEQUENCE",nullable=true,length=50)
	public java.lang.String getSequence(){
		return this.sequence;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  sequence
	 */
	public void setSequence(java.lang.String sequence){
		this.sequence = sequence;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电表
	 */
	@Column(name ="METERID",nullable=true,length=32)
	public java.lang.String getMeterid(){
		return this.meterid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电表
	 */
	public void setMeterid(java.lang.String meterid){
		this.meterid = meterid;
	}
}
