package nhjkpt.configmanage.entity.recorddata;

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
 * @Description: 瞬时数据
 * @author qf
 * @date 2014-12-05 22:58:58
 * @version V1.0   
 *
 */
@Entity
@Table(name = "recorddata", schema = "")
@SuppressWarnings("serial")
public class RecordDataEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**meterid*/
	private java.lang.String meterid;
	/**funcid*/
	private java.lang.Integer funcid;
	/**receivetime*/
	private java.util.Date receivetime;
	/**data*/
	private java.lang.Double data;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
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
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  meterid
	 */
	@Column(name ="METERID",nullable=false,length=16)
	public java.lang.String getMeterid(){
		return this.meterid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  meterid
	 */
	public void setMeterid(java.lang.String meterid){
		this.meterid = meterid;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  funcid
	 */
	@Column(name ="FUNCID",nullable=false,precision=10,scale=0)
	public java.lang.Integer getFuncid(){
		return this.funcid;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  funcid
	 */
	public void setFuncid(java.lang.Integer funcid){
		this.funcid = funcid;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  receivetime
	 */
	@Column(name ="RECEIVETIME",nullable=false)
	public java.util.Date getReceivetime(){
		return this.receivetime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  receivetime
	 */
	public void setReceivetime(java.util.Date receivetime){
		this.receivetime = receivetime;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  data
	 */
	@Column(name ="DATA",nullable=false,precision=22)
	public java.lang.Double getData(){
		return this.data;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  data
	 */
	public void setData(java.lang.Double data){
		this.data = data;
	}
}
