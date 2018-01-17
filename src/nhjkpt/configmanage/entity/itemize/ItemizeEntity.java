package nhjkpt.configmanage.entity.itemize;

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
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;

import nhjkpt.configmanage.entity.metermanage.MeterEntity;

/**   
 * @Title: Entity
 * @Description: 分类配置
 * @author zhangdaihao
 * @date 2013-07-21 00:27:06
 * @version V1.0   
 *
 */
@Entity
@Table(name = "itemize", schema = "")
@SuppressWarnings("serial")
public class ItemizeEntity implements java.io.Serializable {
	/**主键id*/
	private java.lang.String id;
	/**分类名称*/
	private java.lang.String itemizetext;
	/**分类级别*/
	private java.lang.Integer level;
	
	/**分类id*/
	private java.lang.Integer itemizeid;
	
	private ItemizeEntity itemize; //上级节点
	
	private List<ItemizeEntity> itemizes = new ArrayList<ItemizeEntity>();  //下级节点
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "itemize")
	public List<ItemizeEntity> getItemizes() {
		return itemizes;
	}

	public void setItemizes(List<ItemizeEntity> itemizes) {
		this.itemizes = itemizes;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fatherid")
	public ItemizeEntity getItemize() {
		return this.itemize;
	}
	
	public void setItemize(ItemizeEntity itemize) {
		this.itemize = itemize;
	}
	
	
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
	 *@return: java.lang.String  分类名称
	 */
	@Column(name ="ITEMIZETEXT",nullable=false,length=40)
	public java.lang.String getItemizetext(){
		return this.itemizetext;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分类名称
	 */
	public void setItemizetext(java.lang.String itemizetext){
		this.itemizetext = itemizetext;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  分类级别
	 */
	@Column(name ="LEVEL",nullable=false,precision=10,scale=0)
	public java.lang.Integer getLevel(){
		return this.level;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  分类级别
	 */
	public void setLevel(java.lang.Integer level){
		this.level = level;
	}
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  分类id
	 */
	@Column(name ="ITEMIZEID",nullable=false,precision=10,scale=0)
	public java.lang.Integer getItemizeid(){
		return this.itemizeid;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  分类id
	 */
	public void setItemizeid(java.lang.Integer itemizeid){
		this.itemizeid = itemizeid;
	}
}
