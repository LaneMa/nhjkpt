package nhjkpt.configmanage.entity.schoolshowfunc;

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
 * @Description: 学校需要展示的瞬时功能
 * @author qf
 * @date 2013-07-29 22:43:46
 * @version V1.0   
 *
 */
@Entity
@Table(name = "schoolshowfunc", schema = "")
@SuppressWarnings("serial")
public class SchoolShowFuncEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**showtext*/
	private java.lang.String showtext;
	/**meterid*/
	private java.lang.String meterid;
	/**funcid*/
	private java.lang.String funcid;
	
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
	 *@return: java.lang.String  showtext
	 */
	@Column(name ="SHOWTEXT",nullable=true,length=40)
	public java.lang.String getShowtext(){
		return this.showtext;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  showtext
	 */
	public void setShowtext(java.lang.String showtext){
		this.showtext = showtext;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  meterid
	 */
	@Column(name ="METERID",nullable=true,length=32)
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
	@Column(name ="FUNCID",nullable=true,length=32)
	public java.lang.String getFuncid(){
		return this.funcid;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  funcid
	 */
	public void setFuncid(java.lang.String funcid){
		this.funcid = funcid;
	}
}
