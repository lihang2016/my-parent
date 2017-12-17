package com.my.biz.order.domain.entity;

import com.my.biz.common.entity.AggEntity;
import com.my.biz.order.domain.enums.OrderStatusEnum;
import com.my.biz.order.domain.repository.OrderDetailsRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/11/24 20:19
 */
@Entity
@Table(name = "or_order")
@Getter
@Setter
public class Order  extends AggEntity{

    //订单详情领域
    @Autowired
    @Transient
   transient private OrderDetailsRepository orderDetailsRepository;

    @Column(name="order_no",columnDefinition ="varchar(50) comment '订单号'" )
    private String orderNo;

    @Column(name="order_time",columnDefinition ="datetime comment '下单时间'" )
    private Date orderTime;

    @Column(name="order_shop",columnDefinition ="varchar(50) comment '下单店铺'" )
    private String orderShop;

    @Column(name="shop_address",columnDefinition ="varchar(50) comment '店铺地址'" )
    private String shopAddress;

    @Column(name="shop_phone",columnDefinition ="varchar(50) comment '店铺电话'" )
    private String shopPhone;

    @Column(name="order_status_enum",columnDefinition ="varchar(50) comment '订单状态'" )
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatusEnum;

    @Column(name="delivery_man",columnDefinition ="varchar(50) comment '送货人'" )
    private String deliveryMan;

    @Column(name="delivery_man_phone",columnDefinition ="varchar(11) comment '送货人电话'" )
    private String deliveryManPhone;

    @Transient
   transient private List<OrderDetails> detailsList;//订单详情

    public List<OrderDetails> getDetailsList(){
        if(this.id!=null && this.detailsList==null){
            detailsList=orderDetailsRepository.find("EQ_orderId",this.id);
        }
        return detailsList;
    }
}
