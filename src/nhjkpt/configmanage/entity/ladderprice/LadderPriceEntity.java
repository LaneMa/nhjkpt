package nhjkpt.configmanage.entity.ladderprice;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ladderprice", schema = "")
@SuppressWarnings("serial")
public class LadderPriceEntity implements java.io.Serializable {
	/**主键id*/
	private Long id;
	/**名称*/
	private String name;
	/**阶梯价格类型 1水 2电*/
	private Integer priceType;
	/**价格（单价）*/
	private BigDecimal price;
	
	@Id
	@Column(name ="id",nullable=false,length=20)
	public Long getId(){
		return this.id;
	}

	public void setId(Long id){
		this.id = id;
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
