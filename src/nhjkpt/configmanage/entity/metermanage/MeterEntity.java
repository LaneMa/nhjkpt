package nhjkpt.configmanage.entity.metermanage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.framework.core.common.entity.IdEntity;

import javax.persistence.SequenceGenerator;

import nhjkpt.system.pojo.base.TSDemo;

/**   
 * @Title: Entity
 * @Description: 表具管理
 * @author zhangdaihao
 * @date 2013-07-10 23:11:30
 * @version V1.0   
 *
 */
@Entity
@Table(name = "meter")
public class MeterEntity extends IdEntity implements java.io.Serializable {
	
	private MeterEntity meter; //上级表具
	/**表具标识*/
	private java.lang.String meterid;
	

	/**表具描述*/
	private java.lang.String metertext;
	/**配电房id*/
	private java.lang.String roomid;
	/**级别*/
	private java.lang.Integer level;
//	/**父id*/
//	private java.lang.String fatherid;
	private java.lang.String alarm;
	
	private java.lang.Double lat;
	private java.lang.Double lng;
	private java.lang.String joinmeterid;
	
	
	private java.lang.Double joinlat;
	private java.lang.Double joinlng;
	
	private List<MeterEntity> meters = new ArrayList<MeterEntity>();  //下级表具
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fatherid")
	public MeterEntity getMeter() {
		return this.meter;
	}
	
	public void setMeter(MeterEntity meter) {
		this.meter = meter;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  表具标识
	 */
	@Column(name ="METERID",nullable=false,length=30)
	public java.lang.String getMeterid() {
		return meterid;
	}

	public void setMeterid(java.lang.String meterid) {
		this.meterid = meterid;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  表具描述
	 */
	@Column(name ="METERTEXT",nullable=false,length=40)
	public java.lang.String getMetertext(){
		return this.metertext;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  表具描述
	 */
	public void setMetertext(java.lang.String metertext){
		this.metertext = metertext;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  配电房id
	 */
	@Column(name ="ROOMID",nullable=false,length=40)
	public java.lang.String getRoomid(){
		return this.roomid;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  配电房id
	 */
	public void setRoomid(java.lang.String roomid){
		this.roomid = roomid;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  级别
	 */
	@Column(name ="LEVEL",nullable=false,precision=10,scale=0)
	public java.lang.Integer getLevel(){
		return this.level;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  级别
	 */
	public void setLevel(java.lang.Integer level){
		this.level = level;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  父id
	 */
	
	
	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "meter")
	public List<MeterEntity> getMeters() {
		return meters;
	}

	public void setMeters(List<MeterEntity> meters) {
		this.meters = meters;
	}
	@Column(name ="alarm",nullable=false,precision=1,scale=0)
	public java.lang.String getAlarm() {
		return alarm;
	}

	public void setAlarm(java.lang.String alarm) {
		this.alarm = alarm;
	}

	@Column(name ="lng")
	public java.lang.Double getLng() {
		return lng;
	}

	public void setLng(java.lang.Double lng) {
		this.lng = lng;
	}

	@Column(name ="lat")
	public java.lang.Double getLat() {
		return lat;
	}

	public void setLat(java.lang.Double lat) {
		this.lat = lat;
	}

	@Column(name ="joinmeterid")
	public java.lang.String getJoinmeterid() {
		return joinmeterid;
	}

	public void setJoinmeterid(java.lang.String joinmeterid) {
		this.joinmeterid = joinmeterid;
	}

	@Transient
	public java.lang.Double getJoinlat() {
		return joinlat;
	}

	public void setJoinlat(java.lang.Double joinlat) {
		this.joinlat = joinlat;
	}

	@Transient
	public java.lang.Double getJoinlng() {
		return joinlng;
	}

	public void setJoinlng(java.lang.Double joinlng) {
		this.joinlng = joinlng;
	}
	
//	@Column(name ="FATHERID",nullable=true,length=32)
//	public java.lang.String getFatherid(){
//		return this.fatherid;
//	}

//	/**
//	 *方法: 设置java.lang.String
//	 *@param: java.lang.String  父id
//	 */
//	public void setFatherid(java.lang.String fatherid){
//		this.fatherid = fatherid;
//	}
}
