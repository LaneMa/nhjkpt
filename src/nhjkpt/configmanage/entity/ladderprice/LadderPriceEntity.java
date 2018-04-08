package nhjkpt.configmanage.entity.ladderprice;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ladderprice", schema = "")
@SuppressWarnings("serial")
public class LadderPriceEntity implements java.io.Serializable {
	/**主键id*/
	private String id;
	/**名称*/
	private String name;
	/**阶梯价格类型 1水 2电*/
	private Integer priceType;
	
	private String priceTypeName;
	/**价格（单价）*/
	private BigDecimal price;
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="id",nullable=false,length=32)
	public String getId(){
		return this.id;
	}

	public void setId(String id){
		this.id = id;
	}
	
	@Column(name ="price_type_name",nullable=true,length=100)
	public String getPriceTypeName() {
		return priceTypeName;
	}

	public void setPriceTypeName(String priceTypeName) {
		this.priceTypeName = priceTypeName;
	}

	@Column(name ="name",nullable=true,length=100)
	public java.lang.String getName(){
		return this.name;
	}

	public void setName(String name){
		this.name = name;
	}

	@Column(name ="price_type",nullable=true,length=4)
	public Integer getPriceType(){
		return this.priceType;
	}

	public void setPriceType(Integer priceType){
		this.priceType = priceType;
	}

	@Column(name ="price",nullable=true)
	public BigDecimal getPrice(){
		return this.price;
	}

	public void setPrice(BigDecimal price){
		this.price = price;
	}
	
}
