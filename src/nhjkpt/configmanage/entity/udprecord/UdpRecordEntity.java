package nhjkpt.configmanage.entity.udprecord;

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
 * @Description: udp数据
 * @author qf
 * @date 2013-11-02 00:06:10
 * @version V1.0   
 *
 */
@Entity
@Table(name = "udprecord", schema = "")
@SuppressWarnings("serial")
public class UdpRecordEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**udprecord*/
	private java.lang.String udprecord;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
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
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  udprecord
	 */
	@Column(name ="UDPRECORD",nullable=true,length=3000)
	public java.lang.String getUdprecord(){
		return this.udprecord;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  udprecord
	 */
	public void setUdprecord(java.lang.String udprecord){
		this.udprecord = udprecord;
	}
}
