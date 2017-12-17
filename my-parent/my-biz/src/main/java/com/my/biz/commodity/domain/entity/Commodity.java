package com.my.biz.commodity.domain.entity;

import com.my.biz.commodity.domain.enums.CompanyEnum;
import com.my.biz.commodity.domain.repository.CommodityTypeRepository;
import com.my.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/11/26 14:23
 */
@Getter
@Setter
@Entity
@Table(name = "cd_commodity")
public class Commodity extends AggEntity {

    @Autowired
    @Transient
    transient private CommodityTypeRepository commodityTypeRepository;

    @Column(name = "code", columnDefinition = "varchar(500) comment '商品编码'")
    private Long code;

    @Column(name = "name", columnDefinition = "varchar(500) comment '商品名称'")
    private String name;

    @Column(name = "price", columnDefinition = "varchar(500) comment '商品价格'")
    private BigDecimal price;

    @Column(name = "company_enum", columnDefinition = "varchar(500) comment '商品单位'")
    private CompanyEnum companyEnum;

    @Column(name = "one_type", columnDefinition = "int(11) comment '1类型'")
    private Long oneType;

    @Column(name = "two_type", columnDefinition = "int(11) comment '2类型'")
    private Long twoType;

    @Column(name = "three_type", columnDefinition = "int(11) comment '3类型'")
    private Long threeType;

    @Column(name = "supplier_one_level", columnDefinition = "decimal(10,2) comment '供应商等级1'")
    private BigDecimal supplierOneLevel;

    @Column(name = "supplier_two_level", columnDefinition = "decimal(10,2) comment '供应商等级2'")
    private BigDecimal supplierTwoLevel;

    @Column(name = "supplier_three_level", columnDefinition = "decimal(10,2) comment '供应商等级3'")
    private BigDecimal supplierThreeLevel;

    @Column(name = "supplier_four_level", columnDefinition = "decimal(10,2) comment '供应商等级4'")
    private BigDecimal supplierFourLevel;


    @Transient
   transient private CommodityType oneCommodityType;
    @Transient
    transient private CommodityType twoCommodityType;
    @Transient
    transient private CommodityType threeCommodityType;


    @Transient
   transient private String oneTypeName;
    @Transient
    transient private String twoTypeName;
    @Transient
    transient private String threeTypeName;


    public String getOneTypeName(){
        if(getOneCommodityType()!=null){
            oneTypeName=oneCommodityType.getName();
        }
        return oneTypeName;
    }

    public String getTwoTypeName(){
        if(getTwoCommodityType()!=null){
            twoTypeName=twoCommodityType.getName();
        }
        return twoTypeName;
    }

    public String getThreeTypeName(){
        if(getThreeCommodityType()!=null){
            threeTypeName=threeCommodityType.getName();
        }
        return threeTypeName;
    }


   public CommodityType getOneCommodityType(){
       if(this.oneType!=null && oneCommodityType==null){
           oneCommodityType=commodityTypeRepository.get(this.oneType);
       }
       return oneCommodityType;
   }

   public CommodityType getTwoCommodityType(){
       if(this.twoType!=null && twoCommodityType==null){
           twoCommodityType=commodityTypeRepository.get(this.twoType);
       }
       return twoCommodityType;
   }

    public CommodityType getThreeCommodityType(){
        if(this.threeType!=null && threeCommodityType==null){
            threeCommodityType=commodityTypeRepository.get(this.threeType);
        }
        return threeCommodityType;
    }
}
