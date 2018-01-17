package nhjkpt.configmanage.entity.pointdata;

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
 * @Description: 历史数据
 * @author qf
 * @date 2013-08-11 00:21:49
 * @version V1.0   
 *
 */
@Entity
@Table(name = "pointdata", schema = "")
@SuppressWarnings("serial")
public class PointdataEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**测量表具的唯一标识*/
	private java.lang.String meterid;
	/**功能id*/
	private java.lang.String funcid;
	/**接收数据时间*/
	private java.util.Date receivetime;
	/**表数*/
	private java.lang.Double data;
	public PointdataEntity(){
		super();
	}
	public PointdataEntity(Date receivetime,Double data){
		super();
		this.receivetime=receivetime;
		this.data=data;
	}
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
	 *@return: java.lang.String  测量表具的唯一标识
	 */
	@Column(name ="METERID",nullable=true,length=32)
	public java.lang.String getMeterid(){
		return this.meterid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  测量表具的唯一标识
	 */
	public void setMeterid(java.lang.String meterid){
		this.meterid = meterid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  功能id
	 */
	@Column(name ="FUNCID",nullable=true,length=32)
	public java.lang.String getFuncid(){
		return this.funcid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  功能id
	 */
	public void setFuncid(java.lang.String funcid){
		this.funcid = funcid;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  接收数据时间
	 */
	@Column(name ="RECEIVETIME",nullable=true)
	public java.util.Date getReceivetime(){
		return this.receivetime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  接收数据时间
	 */
	public void setReceivetime(java.util.Date receivetime){
		this.receivetime = receivetime;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  表数
	 */
	@Column(name ="DATA",nullable=true,precision=22)
	public java.lang.Double getData(){
		return this.data;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  表数
	 */
	public void setData(java.lang.Double data){
		this.data = data;
	}
}
