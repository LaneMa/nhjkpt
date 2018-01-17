package nhjkpt.configmanage.entity.schoolmonthsum;

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
 * @Description: 学校用电总量分月统计表
 * @author qf
 * @date 2013-08-02 00:49:01
 * @version V1.0   
 *
 */
@Entity
@Table(name = "schoolmonthsum", schema = "")
@SuppressWarnings("serial")
public class SchoolMonthSumEntity implements java.io.Serializable {
	/**主键id*/
	private java.lang.String id;
	/**功能*/
	private java.lang.String funcid;
	/**收到数据时间*/
	private java.util.Date receivetime;
	/**电量*/
	private java.lang.Double data;
	public SchoolMonthSumEntity(){
		super();
	}
	public SchoolMonthSumEntity(Date receivetime,Double data){
		super();
		this.receivetime=receivetime;
		this.data=data;
	}
	public SchoolMonthSumEntity(String funcid,Date receivetime,Double data){
		super();
		this.funcid=funcid;
		this.receivetime=receivetime;
		this.data=data;
	}
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
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  收到数据时间
	 */
	@Column(name ="RECEIVETIME",nullable=true)
	public java.util.Date getReceivetime(){
		return this.receivetime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  收到数据时间
	 */
	public void setReceivetime(java.util.Date receivetime){
		this.receivetime = receivetime;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  电量
	 */
	@Column(name ="DATA",nullable=true,precision=22)
	public java.lang.Double getData(){
		return this.data;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  电量
	 */
	public void setData(java.lang.Double data){
		this.data = data;
	}
}
