package nhjkpt.configmanage.entity.buildingmonthitemize;

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
 * @Description: 大楼用电分类分月统计表
 * @author qf
 * @date 2013-08-02 01:11:37
 * @version V1.0   
 *
 */
@Entity
@Table(name = "buildingmonthitemize", schema = "")
@SuppressWarnings("serial")
public class BuildingMonthItemizeEntity implements java.io.Serializable {
	/**主键id*/
	private java.lang.String id;
	/**大楼*/
	private java.lang.String buildingid;
	/**分类*/
	private java.lang.String itemizeid;
	/**收到数据的时间*/
	private java.util.Date receivetime;
	/**用电量*/
	private java.lang.Double data;
	public BuildingMonthItemizeEntity(){
		super();
	}
	public BuildingMonthItemizeEntity(Date receivetime,Double data){
		super();
		this.receivetime=receivetime;
		this.data=data;
	}
	public BuildingMonthItemizeEntity(String itemizeid,Date receivetime,Double data){
		super();
		this.itemizeid=itemizeid;
		this.receivetime=receivetime;
		this.data=data;
	}
	
	public BuildingMonthItemizeEntity(String itemizeid, Double data){
		super();
		this.itemizeid=itemizeid;
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
	 *@return: java.lang.String  大楼
	 */
	@Column(name ="BUILDINGID",nullable=true,length=32)
	public java.lang.String getBuildingid(){
		return this.buildingid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  大楼
	 */
	public void setBuildingid(java.lang.String buildingid){
		this.buildingid = buildingid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分类
	 */
	@Column(name ="ITEMIZEID",nullable=true,length=32)
	public java.lang.String getItemizeid(){
		return this.itemizeid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分类
	 */
	public void setItemizeid(java.lang.String itemizeid){
		this.itemizeid = itemizeid;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  收到数据的时间
	 */
	@Column(name ="RECEIVETIME",nullable=true)
	public java.util.Date getReceivetime(){
		return this.receivetime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  收到数据的时间
	 */
	public void setReceivetime(java.util.Date receivetime){
		this.receivetime = receivetime;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  用电量
	 */
	@Column(name ="DATA",nullable=true,precision=22)
	public java.lang.Double getData(){
		return this.data;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  用电量
	 */
	public void setData(java.lang.Double data){
		this.data = data;
	}
}
