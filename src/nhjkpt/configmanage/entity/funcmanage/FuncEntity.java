package nhjkpt.configmanage.entity.funcmanage;

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
 * @Description: 表具配置
 * @author zhangdaihao
 * @date 2013-07-10 20:48:00
 * @version V1.0   
 *
 */
@Entity
@Table(name = "func", schema = "")
@SuppressWarnings("serial")
public class FuncEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**表具标号*/
	private java.lang.Integer funcid;
	/**表具名称*/
	private java.lang.String funcname;
	/**
	 * 是否显示在查询条件中
	 */
	private java.lang.String ischeck;
	
	private java.lang.String showtext;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
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
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  表具标号
	 */
	@Column(name ="FUNCID",nullable=false,precision=10,scale=0)
	public java.lang.Integer getFuncid(){
		return this.funcid;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  表具标号
	 */
	public void setFuncid(java.lang.Integer funcid){
		this.funcid = funcid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  表具名称
	 */
	@Column(name ="FUNCNAME",nullable=false,length=20)
	public java.lang.String getFuncname(){
		return this.funcname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  表具名称
	 */
	public void setFuncname(java.lang.String funcname){
		this.funcname = funcname;
	}
	@Column(name ="ISCHECK",nullable=false,length=1)
	public java.lang.String getIscheck() {
		return ischeck;
	}

	public void setIscheck(java.lang.String ischeck) {
		this.ischeck = ischeck;
	}

	@Column(name ="SHOWTEXT",nullable=false,length=20)
	public java.lang.String getShowtext() {
		return showtext;
	}

	public void setShowtext(java.lang.String showtext) {
		this.showtext = showtext;
	}
	
}
