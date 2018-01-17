package nhjkpt.configmanage.entity.roomlight;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import nhjkpt.configmanage.entity.itemize.ItemizeEntity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;

/**   
 * @Title: Entity
 * @Description: 教室照明管理
 * @author qf
 * @date 2015-01-03 19:47:14
 * @version V1.0   
 *
 */
@Entity
@Table(name = "roomlight", schema = "")
@SuppressWarnings("serial")
public class RoomlightEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**buildingid*/
	private java.lang.Integer buildingid;
	/**buildingname*/
	private java.lang.String buildingname;
	/**layerid*/
	private java.lang.Integer layerid;
	/**roomid*/
	private java.lang.Integer roomid;
	/**controltype*/
	private java.lang.Integer controltype;
	/**controldata1*/
	private java.lang.Integer controldata1;
	/**controldata2*/
	private java.lang.Integer controldata2;
	/**lightdata*/
	private java.lang.Integer lightdata;
	/**mannumber*/
	private java.lang.Integer mannumber;
	/**lightnumber*/
	private java.lang.Integer lightnumber;
	/**lightstate*/
	private java.lang.Integer lightstate;
	/**mark1*/
	private java.lang.Integer mark1;
	/**mark2*/
	private java.lang.Integer mark2;
	
	private List<RoomlightEntity> roomlights = new ArrayList<RoomlightEntity>();  //下级节点
	
	private Integer level ;  //级别  0-表示教学楼   ，1-表示层，2-表示宿舍号
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=50)
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  buildingid
	 */
	@Column(name ="BUILDINGID",nullable=true,precision=10,scale=0)
	public java.lang.Integer getBuildingid(){
		return this.buildingid;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  buildingid
	 */
	public void setBuildingid(java.lang.Integer buildingid){
		this.buildingid = buildingid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  buildingname
	 */
	@Column(name ="BUILDINGNAME",nullable=true,length=50)
	public java.lang.String getBuildingname(){
		return this.buildingname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  buildingname
	 */
	public void setBuildingname(java.lang.String buildingname){
		this.buildingname = buildingname;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  layerid
	 */
	@Column(name ="LAYERID",nullable=true,precision=10,scale=0)
	public java.lang.Integer getLayerid(){
		return this.layerid;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  layerid
	 */
	public void setLayerid(java.lang.Integer layerid){
		this.layerid = layerid;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  roomid
	 */
	@Column(name ="ROOMID",nullable=true,precision=10,scale=0)
	public java.lang.Integer getRoomid(){
		return this.roomid;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  roomid
	 */
	public void setRoomid(java.lang.Integer roomid){
		this.roomid = roomid;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  controltype
	 */
	@Column(name ="CONTROLTYPE",nullable=true,precision=10,scale=0)
	public java.lang.Integer getControltype(){
		return this.controltype;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  controltype
	 */
	public void setControltype(java.lang.Integer controltype){
		this.controltype = controltype;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  controldata1
	 */
	@Column(name ="CONTROLDATA1",nullable=true,precision=10,scale=0)
	public java.lang.Integer getControldata1(){
		return this.controldata1;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  controldata1
	 */
	public void setControldata1(java.lang.Integer controldata1){
		this.controldata1 = controldata1;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  controldata2
	 */
	@Column(name ="CONTROLDATA2",nullable=true,precision=10,scale=0)
	public java.lang.Integer getControldata2(){
		return this.controldata2;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  controldata2
	 */
	public void setControldata2(java.lang.Integer controldata2){
		this.controldata2 = controldata2;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  lightdata
	 */
	@Column(name ="LIGHTDATA",nullable=true,precision=10,scale=0)
	public java.lang.Integer getLightdata(){
		return this.lightdata;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  lightdata
	 */
	public void setLightdata(java.lang.Integer lightdata){
		this.lightdata = lightdata;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  mannumber
	 */
	@Column(name ="MANNUMBER",nullable=true,precision=10,scale=0)
	public java.lang.Integer getMannumber(){
		return this.mannumber;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  mannumber
	 */
	public void setMannumber(java.lang.Integer mannumber){
		this.mannumber = mannumber;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  lightnumber
	 */
	@Column(name ="LIGHTNUMBER",nullable=true,precision=10,scale=0)
	public java.lang.Integer getLightnumber(){
		return this.lightnumber;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  lightnumber
	 */
	public void setLightnumber(java.lang.Integer lightnumber){
		this.lightnumber = lightnumber;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  lightstate
	 */
	@Column(name ="LIGHTSTATE",nullable=true,precision=10,scale=0)
	public java.lang.Integer getLightstate(){
		return this.lightstate;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  lightstate
	 */
	public void setLightstate(java.lang.Integer lightstate){
		this.lightstate = lightstate;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  mark1
	 */
	@Column(name ="MARK1",nullable=true,precision=10,scale=0)
	public java.lang.Integer getMark1(){
		return this.mark1;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  mark1
	 */
	public void setMark1(java.lang.Integer mark1){
		this.mark1 = mark1;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  mark2
	 */
	@Column(name ="MARK2",nullable=true,precision=10,scale=0)
	public java.lang.Integer getMark2(){
		return this.mark2;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  mark2
	 */
	public void setMark2(java.lang.Integer mark2){
		this.mark2 = mark2;
	}

	@Transient
	public List<RoomlightEntity> getRoomlights() {
		return roomlights;
	}

	public void setRoomlights(List<RoomlightEntity> roomlights) {
		this.roomlights = roomlights;
	}

	@Transient
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
}
