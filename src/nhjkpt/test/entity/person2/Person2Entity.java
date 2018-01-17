package nhjkpt.test.entity.person2;

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
 * @Description: 人员管理
 * @author zhangdaihao
 * @date 2013-07-17 17:02:56
 * @version V1.0   
 *
 */
@Entity
@Table(name = "person2", schema = "")
@SuppressWarnings("serial")
public class Person2Entity implements java.io.Serializable {
	/**personid*/
	private java.lang.Integer personid;
	/**name*/
	private java.lang.String name;
	/**remark*/
	private java.lang.String remark;
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  personid
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
	@Column(name ="PERSONID",nullable=false,precision=10,scale=0)
	public java.lang.Integer getPersonid(){
		return this.personid;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  personid
	 */
	public void setPersonid(java.lang.Integer personid){
		this.personid = personid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  name
	 */
	@Column(name ="NAME",nullable=false,length=20)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  name
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  remark
	 */
	@Column(name ="REMARK",nullable=true,length=50)
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  remark
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
	}
}
