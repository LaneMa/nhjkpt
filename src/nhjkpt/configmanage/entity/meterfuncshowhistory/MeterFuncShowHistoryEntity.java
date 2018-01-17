package nhjkpt.configmanage.entity.meterfuncshowhistory;

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
 * @author qf
 * @date 2013-08-29 19:13:25
 * @version V1.0   
 *
 */
@Entity
@Table(name = "meterfuncshowhistory", schema = "")
@SuppressWarnings("serial")
public class MeterFuncShowHistoryEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**meterhistoryid*/
	private java.lang.String meterhistoryid;
	/**funcid*/
	private java.lang.String funcid;
	/**showdata*/
	private java.lang.Double showdata;
	/**采集时间*/
	private java.util.Date senddate;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
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
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  meterhistoryid
	 */
	@Column(name ="METERHISTORYID",nullable=false,length=32)
	public java.lang.String getMeterhistoryid(){
		return this.meterhistoryid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  meterhistoryid
	 */
	public void setMeterhistoryid(java.lang.String meterhistoryid){
		this.meterhistoryid = meterhistoryid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  funcid
	 */
	@Column(name ="FUNCID",nullable=false,length=32)
	public java.lang.String getFuncid(){
		return this.funcid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  funcid
	 */
	public void setFuncid(java.lang.String funcid){
		this.funcid = funcid;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  showdata
	 */
	@Column(name ="SHOWDATA",nullable=false,precision=22)
	public java.lang.Double getShowdata(){
		return this.showdata;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  showdata
	 */
	public void setShowdata(java.lang.Double showdata){
		this.showdata = showdata;
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
}
