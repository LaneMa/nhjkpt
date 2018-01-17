package nhjkpt.configmanage.entity.originadata;

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
 * @Description: 数据库连接
 * @author qf
 * @date 2013-08-11 01:51:29
 * @version V1.0   
 *
 */
@Entity
@Table(name = "originadata", schema = "")
@SuppressWarnings("serial")
public class OriginadataEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**数据库*/
	private java.lang.String dbtype;
	/**数据库ip*/
	private java.lang.String dbip;
	/**数据库名称*/
	private java.lang.String dbname;
	/**用户名*/
	private java.lang.String dbuser;
	/**密码*/
	private java.lang.String dbpwd;
	/**表名*/
	private java.lang.String tbname;
	
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  数据库
	 */
	@Column(name ="DBTYPE",nullable=true,length=32)
	public java.lang.String getDbtype(){
		return this.dbtype;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  数据库
	 */
	public void setDbtype(java.lang.String dbtype){
		this.dbtype = dbtype;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  数据库ip
	 */
	@Column(name ="DBIP",nullable=false,length=20)
	public java.lang.String getDbip(){
		return this.dbip;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  数据库ip
	 */
	public void setDbip(java.lang.String dbip){
		this.dbip = dbip;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  数据库名称
	 */
	@Column(name ="DBNAME",nullable=false,length=20)
	public java.lang.String getDbname(){
		return this.dbname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  数据库名称
	 */
	public void setDbname(java.lang.String dbname){
		this.dbname = dbname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户名
	 */
	@Column(name ="DBUSER",nullable=true,length=20)
	public java.lang.String getDbuser(){
		return this.dbuser;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户名
	 */
	public void setDbuser(java.lang.String dbuser){
		this.dbuser = dbuser;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  密码
	 */
	@Column(name ="DBPWD",nullable=true,length=20)
	public java.lang.String getDbpwd(){
		return this.dbpwd;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  密码
	 */
	public void setDbpwd(java.lang.String dbpwd){
		this.dbpwd = dbpwd;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  表名
	 */
	@Column(name ="TBNAME",nullable=true,length=100)
	public java.lang.String getTbname(){
		return this.tbname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  表名
	 */
	public void setTbname(java.lang.String tbname){
		this.tbname = tbname;
	}
}
