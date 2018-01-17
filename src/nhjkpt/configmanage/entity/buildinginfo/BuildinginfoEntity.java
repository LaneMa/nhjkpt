package nhjkpt.configmanage.entity.buildinginfo;

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
 * @Description: 建筑物配置
 * @author zhangdaihao
 * @date 2013-07-21 00:18:04
 * @version V1.0   
 *
 */
@Entity
@Table(name = "buildinginfo", schema = "")
@SuppressWarnings("serial")
public class BuildinginfoEntity implements java.io.Serializable {
	/**主键id*/
	private java.lang.String id;
	/**建筑物名称*/
	private java.lang.String buildingname;
	/**建筑物描述*/
	private java.lang.String buildingtext;
	/**建筑物标识号*/
	private java.lang.String buildingid;
	private java.lang.Integer x;
	private java.lang.Integer y;
	
	/**水配额*/
	private java.lang.Double waterlimit;
	
	/**电配额*/
	private java.lang.Double electriclimit;
	
	/**气配额*/
	private java.lang.Double gaslimit;
	
	/**热配额*/
	private java.lang.Double heatlimit;
	
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键id
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
	 *@param: java.lang.String  主键id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  建筑物名称
	 */
	@Column(name ="BUILDINGNAME",nullable=false,length=40)
	public java.lang.String getBuildingname(){
		return this.buildingname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  建筑物名称
	 */
	public void setBuildingname(java.lang.String buildingname){
		this.buildingname = buildingname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  建筑物描述
	 */
	@Column(name ="BUILDINGTEXT",nullable=true,length=1300)
	public java.lang.String getBuildingtext(){
		return this.buildingtext;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  建筑物描述
	 */
	public void setBuildingtext(java.lang.String buildingtext){
		this.buildingtext = buildingtext;
	}
	
	
	@Column(name ="BUILDINGID",nullable=false,length=32)
	public java.lang.String getBuildingid() {
		return buildingid;
	}

	public void setBuildingid(java.lang.String buildingid) {
		this.buildingid = buildingid;
	}
	@Column(name ="X",nullable=true,precision=10,scale=0)
	public java.lang.Integer getX() {
		return x;
	}

	public void setX(java.lang.Integer x) {
		this.x = x;
	}
	@Column(name ="Y",nullable=true,precision=10,scale=0)
	public java.lang.Integer getY() {
		return y;
	}

	public void setY(java.lang.Integer y) {
		this.y = y;
	}

	@Column(name ="waterlimit",nullable=true,precision=22)
	public java.lang.Double getWaterlimit() {
		return waterlimit;
	}

	public void setWaterlimit(java.lang.Double waterlimit) {
		this.waterlimit = waterlimit;
	}

	@Column(name ="electriclimit",nullable=true,precision=22)
	public java.lang.Double getElectriclimit() {
		return electriclimit;
	}

	public void setElectriclimit(java.lang.Double electriclimit) {
		this.electriclimit = electriclimit;
	}

	@Column(name ="gaslimit",nullable=true,precision=22)
	public java.lang.Double getGaslimit() {
		return gaslimit;
	}

	public void setGaslimit(java.lang.Double gaslimit) {
		this.gaslimit = gaslimit;
	}

	@Column(name ="heatlimit",nullable=true,precision=22)
	public java.lang.Double getHeatlimit() {
		return heatlimit;
	}

	public void setHeatlimit(java.lang.Double heatlimit) {
		this.heatlimit = heatlimit;
	}

}
