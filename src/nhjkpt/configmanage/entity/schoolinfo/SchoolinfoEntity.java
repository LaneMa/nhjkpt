package nhjkpt.configmanage.entity.schoolinfo;

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
 * @Description: 学校基本信息配置
 * @author zhangdaihao
 * @date 2013-07-20 17:52:22
 * @version V1.0   
 *
 */
@Entity
@Table(name = "schoolinfo", schema = "")
@SuppressWarnings("serial")
public class SchoolinfoEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	private java.lang.String schoolid;
	/**schoolname*/
	private java.lang.String schoolname;
	/**schooltext*/
	private java.lang.String schooltext;
	
	private java.lang.Integer alarmTimeType;
	
	private java.lang.Integer alarmTime;
	
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
	 *@return: java.lang.String  id
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
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  schoolname
	 */
	@Column(name ="SCHOOLNAME",nullable=false,length=40)
	public java.lang.String getSchoolname(){
		return this.schoolname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  schoolname
	 */
	public void setSchoolname(java.lang.String schoolname){
		this.schoolname = schoolname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  schooltext
	 */
	@Column(name ="SCHOOLTEXT",nullable=true,length=1300)
	public java.lang.String getSchooltext(){
		return this.schooltext;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  schooltext
	 */
	public void setSchooltext(java.lang.String schooltext){
		this.schooltext = schooltext;
	}
	@Column(name ="SCHOOLID",nullable=true,length=1300)
	public java.lang.String getSchoolid() {
		return schoolid;
	}

	public void setSchoolid(java.lang.String schoolid) {
		this.schoolid = schoolid;
	}

	@Column(name ="alarm_time_type",nullable=false,precision=10,scale=0)
	public java.lang.Integer getAlarmTimeType() {
		return alarmTimeType;
	}

	public void setAlarmTimeType(java.lang.Integer alarmTimeType) {
		this.alarmTimeType = alarmTimeType;
	}

	@Column(name ="alarm_time",nullable=false,precision=10,scale=0)
	public java.lang.Integer getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(java.lang.Integer alarmTime) {
		this.alarmTime = alarmTime;
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
