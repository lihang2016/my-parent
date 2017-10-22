
package com.my.biz.common.udc.domain.entity;

import com.my.biz.common.entity.AggEntity;
import com.my.biz.common.udc.domain.repository.UDCItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.List;

/**
 *
 */
@Entity
@Table(name = "pf_udc_type", indexes = {@Index(unique = true, name = "unique_code_idx",
        columnList = "code")})
public class UDCType extends AggEntity {
    @Autowired
    private transient UDCItemRepository udcItemRepository;

    @Column(name = "code", columnDefinition = "varchar(30) NOT NULL DEFAULT '' COMMENT '类型编码'")
    private String code;

    @Column(name = "name", columnDefinition = "varchar(30) NOT NULL COMMENT '类型名称'")
    private String name;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "category", columnDefinition = "tinyint(3) DEFAULT NULL COMMENT '枚举类别(非UDC)'")
    private Category category;

    private transient List<UDCItem> items;

    public UDCItem getItemByItemValue(Integer value) {
        Assert.notNull(value);
        for (UDCItem udcItem : this.getItems()) {
            if (udcItem.getValue().equals(value)) {
                return udcItem;
            }
        }
        throw new IllegalArgumentException("UDCItem value 不存在");
    }

    public UDCItem getItemByItemName(String itemName) {
        Assert.notNull(itemName);
        for (UDCItem udcItem : this.getItems()) {
            if (itemName.equals(udcItem.getName())) {
                return udcItem;
            }
        }
        throw new IllegalArgumentException("UDCItem name 不存在");
    }

    public UDCItem getItemByItemCode(String itemCode) {
        Assert.notNull(itemCode);
        for (UDCItem udcItem : this.getItems()) {
            if (itemCode.equals(udcItem.getCode())) {
                return udcItem;
            }
        }
        throw new IllegalArgumentException("UDCItem code 不存在");
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<UDCItem> getItems() {
        if (items == null) {
            items = udcItemRepository.findByTypeIdOrderByValue(this.getId());
        }
        return items;
    }

    public void setItems(List<UDCItem> items) {
        this.items = items;
    }

    public enum Category {
        SN("系统不可扩展"),
        SE("系统可扩展"),
        UD("用户定义");

        private String msg;

        Category(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
