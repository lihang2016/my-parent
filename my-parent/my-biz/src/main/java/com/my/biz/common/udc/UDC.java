
package com.my.biz.common.udc;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.base.Strings;
import com.my.biz.common.udc.biz.UDCService;
import com.my.biz.common.udc.domain.entity.UDCItem;
import com.my.biz.common.udc.domain.entity.UDCType;
import com.my.biz.common.util.SpringContextHolder;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.lang.annotation.*;
import java.util.List;

/**
 * 用户定义分类
 * <p>
 * UDC转换说明：
 * <p>1. UDC 转换为json：{"itemCode":"no_invoice","itemId":38,"itemName":"不开发票","itemValue":1,"typeCode":"invoice_type"}
 * <p>2. json转换为UDC: json值类型为字符串，格式为typeCode,itemValue
 * <p>3. 请求参数转换为UDC: 格式为typeCode,itemValue
 * <p>
 * 参考用例:
 * <ul>
 * <li>com.zds.scf.biz.web.common.controller.DemoController#testUDC(com.zds.scf.biz.common.udc.UDC)</li>
 * <li>com.zds.scf.biz.web.common.controller.DemoController#testUDC1(com.zds.scf.biz.common.udc.UDC)</li>
 * <li>com.zds.scf.biz.web.common.controller.DemoController#testUDC2(com.zds.scf.biz.common.udc.UDC)</li>
 * <li>com.zds.scf.biz.web.common.controller.DemoController#testUDC3(com.zds.scf.biz.common.udc.UDC)</li>
 * </ul>
 *
 *
 */
public class UDC implements Serializable,Cloneable {

    public static final int NULL_ITEM_VALUE = 0;

    private static UDCService udcService;
    /**
     * UDC 类型编码
     */
    private String typeCode;
    /**
     * UDC 类型项值
     */
    private Integer itemValue;

    private String itemName;

    private String itemCode;

    private transient UDCType udcType;

    private boolean init = true;

    protected UDC(@Nonnull String typeCode, @Nonnull int itemValue) {
        this.typeCode = typeCode;
        this.itemValue = itemValue;
    }

    private UDC(String typeCode, @Nullable String itemName, @Nullable String itemCode) {
        this.typeCode = typeCode;
        this.itemName = itemName;
        this.itemCode = itemCode;
        this.init = false;
    }
    public static void initUdcService(){
       if (udcService == null) {
            udcService = SpringContextHolder.getApplicationContext().getBean(UDCService.class);
        }
    }

    public static UDC newUDCWithItemName(String typeCode, @Nullable String itemName) {
        return newUDC(typeCode, itemName, null);
    }

    public static UDC newUDCWithItemCode(String typeCode, @Nullable String itemCode) {
        return newUDC(typeCode, null, itemCode);
    }

    public static UDC newUDCWithItemValue(String typeCode, int itemValue) {
        return new UDC(typeCode, itemValue);
    }

    public static UDC newUDC(String typeCode, @Nullable String itemName, @Nullable String itemCode) {
        //spring 还没有初始化完
        if (udcService == null) {
            UDC udc = new UDC(typeCode, itemName, itemCode);
            return udc;
        } else {
            UDCType udcType = udcService.findByCode(typeCode);
            Assert.notNull(udcType, "udcTypeCode不存在");
            Integer udcItemValue;
            if (!Strings.isNullOrEmpty(itemName)) {
                udcItemValue = udcType.getItemByItemName(itemName).getValue();
            } else {
                udcItemValue = udcType.getItemByItemCode(itemCode).getValue();
            }
            UDC udc = new UDC(typeCode, udcItemValue);
            udc.udcType = udcType;
            return udc;
        }
    }

    public UDC newUDCWithItemName(@Nonnull String itmeName) {
        Assert.notNull(itmeName);
        return newUDC(itmeName, null);
    }

    public UDC newUDCWithItemCode(@Nonnull String itemCode) {
        Assert.notNull(itemCode);
        return newUDC(null, itemCode);
    }

    public UDC newUDC(@Nullable String itmeName, @Nullable String itemCode) {
        Assert.isTrue(!(itmeName == null && itemCode == null), "两个参数不能都为null");
        Integer udcItemValue;
        if (!Strings.isNullOrEmpty(itmeName)) {
            udcItemValue = getType().getItemByItemName(itmeName).getValue();
        } else {
            udcItemValue = getType().getItemByItemCode(itemCode).getValue();
        }
        UDC udc = new UDC(this.typeCode, udcItemValue);
        udc.udcType = this.udcType;
        return udc;
    }

    public String getTypeCode() {
        return typeCode;
    }


    public Integer getItemValue() {
        return itemValue;
    }

    public String getItemName() {
        return getItem().getName();
    }

    public String getItemCode(){
        return getItem().getCode();
    }

    @JSONField(serialize = false)
    public UDCItem getItem() {
        UDCType udcType = getType();
        return udcType.getItemByItemValue(this.itemValue);
    }

    @JSONField(serialize = false)
    public List<UDCItem> getItemsByTypeCode() {
        return getType().getItems();
    }

    @JSONField(serialize = false)
    public UDCType getType() {
        if (udcType == null) {
            udcType = udcService.findByCode(typeCode);
        }
        if (!this.init) {
            if (!Strings.isNullOrEmpty(this.itemName)) {
                this.itemValue = udcType.getItemByItemName(this.itemName).getValue();
            } else {
                this.itemValue = udcType.getItemByItemCode(this.itemCode).getValue();
            }
            this.init = true;
        }
        return udcType;
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        UDC udc = new UDC(this.typeCode, this.itemValue);
        udc.udcType = this.udcType;
        return udc;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UDC{");
        sb.append("typeCode=").append(typeCode);
        sb.append(", itemValue=").append(itemValue);
        sb.append('}');
        return sb.toString();
    }

    @Documented
    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    public @interface EnumTypeCode {
        String value();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UDC)) return false;

        UDC udc = (UDC) o;
        //初始化
        this.getType();
        if (typeCode != null ? !typeCode.equals(udc.typeCode) : udc.typeCode != null) return false;
        return itemValue != null ? itemValue.equals(udc.itemValue) : udc.itemValue == null;

    }

    @Override
    public int hashCode() {
        //初始化
        this.getType();
        int result = typeCode != null ? typeCode.hashCode() : 0;
        result = 31 * result + (itemValue != null ? itemValue.hashCode() : 0);
        return result;
    }
}
