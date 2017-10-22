package com.my.biz.common.udc.app.dto;


import com.my.biz.common.interactive.PageDto;

import javax.validation.constraints.NotNull;

/**
 *
 */
public class UDCItemDto extends PageDto {

    @NotNull(groups = Update.class)
    private Long id;

    @NotNull
    private String code;

    @NotNull
    private String name;

    @NotNull
    private Integer value;

    @NotNull
    private boolean reserved;

    @NotNull
    private Long typeId;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }



    public interface Update {

    }
}
