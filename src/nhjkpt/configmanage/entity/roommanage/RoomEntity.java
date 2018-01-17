package nhjkpt.configmanage.entity.roommanage;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;

/**   
 * @Title: Entity
 * @Description: 配电房管理
 * @author zhangdaihao
 * @date 2013-07-10 21:56:41
 * @version V1.0   
 *
 */
@Entity
@Table(name = "room", schema = "")
@SuppressWarnings("serial")
public class RoomEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**配电房标识*/
	private java.lang.Integer roomid;
	/**配电房描述*/
	private java.lang.String roomtext;
	
	
	private java.lang.Double lat;
	private java.lang.Double lng;
	private java.lang.Integer joinroomid;
	
	private java.lang.Double joinlat;
	private java.lang.Double joinlng;
	
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
	 *@return: java.lang.Integer  配电房标识
	 */
	@Column(name ="ROOMID",nullable=false,precision=10,scale=0)
	public java.lang.Integer getRoomid(){
		return this.roomid;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  配电房标识
	 */
	public void setRoomid(java.lang.Integer roomid){
		this.roomid = roomid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  配电房描述
	 */
	@Column(name ="ROOMTEXT",nullable=false,length=40)
	public java.lang.String getRoomtext(){
		return this.roomtext;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  配电房描述
	 */
	public void setRoomtext(java.lang.String roomtext){
		this.roomtext = roomtext;
	}
	
	

	@Column(name ="lat")
	public java.lang.Double getLat() {
		return lat;
	}

	public void setLat(java.lang.Double lat) {
		this.lat = lat;
	}

	@Column(name ="lng")
	public java.lang.Double getLng() {
		return lng;
	}

	public void setLng(java.lang.Double lng) {
		this.lng = lng;
	}

	@Column(name ="joinroomid")
	public java.lang.Integer getJoinroomid() {
		return joinroomid;
	}

	public void setJoinroomid(java.lang.Integer joinroomid) {
		this.joinroomid = joinroomid;
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

}
