package nhjkpt.configmanage.entity.alarminfo;

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
 * @Description: 报警信息列表
 * @author zhangdaihao
 * @date 2013-07-21 00:20:34
 * @version V1.0   
 *
 */
@Entity
@Table(name = "alarminfo", schema = "")
@SuppressWarnings("serial")
public class AlarminfoEntity implements java.io.Serializable {
	/**主键id*/
	private java.lang.String id;
	/**表具id*/
	private java.lang.String meterid;
	/**报警时间*/
	private java.util.Date alarmtime;
	/**报警信息*/
	private java.lang.String info;
	//0为报警1为断线
	private java.lang.String type;
	
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  报警时间
	 */
	@Column(name ="ALARMTIME",nullable=false)
	public java.util.Date getAlarmtime(){
		return this.alarmtime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  报警时间
	 */
	public void setAlarmtime(java.util.Date alarmtime){
		this.alarmtime = alarmtime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  报警信息
	 */
	@Column(name ="INFO",nullable=true,length=300)
	public java.lang.String getInfo(){
		return this.info;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  报警信息
	 */
	public void setInfo(java.lang.String info){
		this.info = info;
	}
	@Column(name ="TYPE",nullable=true,length=1)
	public java.lang.String getType() {
		return type;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}
	
}
