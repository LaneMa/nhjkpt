package nhjkpt.configmanage.entity.lighting;

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
 * @Description: 教室照明
 * @author qf
 * @date 2014-11-17 22:28:39
 * @version V1.0   
 *
 */
@Entity
@Table(name = "lighting", schema = "")
@SuppressWarnings("serial")
public class LightingEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**楼层id*/
	private java.lang.String floorid;
	/**楼id*/
	private java.lang.String buildingid;
	/**教室id*/
	private java.lang.String roomid;
	/**控制类型*/
	private java.lang.String controlType;
	/**控制数据*/
	private java.lang.String controlData;
	/**光照度*/
	private java.lang.String lightFalls;
	/**人数*/
	private java.lang.Integer userNum;
	/**灯数*/
	private java.lang.Integer lightNum;
	/**灯状态*/
	private java.lang.String lightType;
	/**保留1*/
	private java.lang.String retain1;
	/**保留2*/
	private java.lang.String retain2;
	
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
	 *@return: java.lang.String  楼层id
	 */
	@Column(name ="FLOORID",nullable=true,length=100)
	public java.lang.String getFloorid(){
		return this.floorid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  楼层id
	 */
	public void setFloorid(java.lang.String floorid){
		this.floorid = floorid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  楼id
	 */
	@Column(name ="BUILDINGID",nullable=true,length=100)
	public java.lang.String getBuildingid(){
		return this.buildingid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  楼id
	 */
	public void setBuildingid(java.lang.String buildingid){
		this.buildingid = buildingid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  教室id
	 */
	@Column(name ="ROOMID",nullable=true,length=100)
	public java.lang.String getRoomid(){
		return this.roomid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  教室id
	 */
	public void setRoomid(java.lang.String roomid){
		this.roomid = roomid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  控制类型
	 */
	@Column(name ="CONTROL_TYPE",nullable=true,length=10)
	public java.lang.String getControlType(){
		return this.controlType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  控制类型
	 */
	public void setControlType(java.lang.String controlType){
		this.controlType = controlType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  控制数据
	 */
	@Column(name ="CONTROL_DATA",nullable=true,length=255)
	public java.lang.String getControlData(){
		return this.controlData;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  控制数据
	 */
	public void setControlData(java.lang.String controlData){
		this.controlData = controlData;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  光照度
	 */
	@Column(name ="LIGHT_FALLS",nullable=true,length=255)
	public java.lang.String getLightFalls(){
		return this.lightFalls;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  光照度
	 */
	public void setLightFalls(java.lang.String lightFalls){
		this.lightFalls = lightFalls;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  人数
	 */
	@Column(name ="USER_NUM",nullable=true,precision=10,scale=0)
	public java.lang.Integer getUserNum(){
		return this.userNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  人数
	 */
	public void setUserNum(java.lang.Integer userNum){
		this.userNum = userNum;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  灯数
	 */
	@Column(name ="LIGHT_NUM",nullable=true,precision=10,scale=0)
	public java.lang.Integer getLightNum(){
		return this.lightNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  灯数
	 */
	public void setLightNum(java.lang.Integer lightNum){
		this.lightNum = lightNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  灯状态
	 */
	@Column(name ="LIGHT_TYPE",nullable=true,length=10)
	public java.lang.String getLightType(){
		return this.lightType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  灯状态
	 */
	public void setLightType(java.lang.String lightType){
		this.lightType = lightType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  保留1
	 */
	@Column(name ="RETAIN1",nullable=true,length=255)
	public java.lang.String getRetain1(){
		return this.retain1;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  保留1
	 */
	public void setRetain1(java.lang.String retain1){
		this.retain1 = retain1;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  保留2
	 */
	@Column(name ="RETAIN2",nullable=true,length=255)
	public java.lang.String getRetain2(){
		return this.retain2;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  保留2
	 */
	public void setRetain2(java.lang.String retain2){
		this.retain2 = retain2;
	}
}
