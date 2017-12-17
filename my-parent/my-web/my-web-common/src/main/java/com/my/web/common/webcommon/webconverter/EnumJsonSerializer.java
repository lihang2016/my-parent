/*
 * www.zdsoft.cn Inc.
 * Copyright (c) 2005-2017 All Rights Reserved.
 */
/*
 * 修订记录：
 * Administrator 2017/6/16 16:23 创建
 */
/*
 * @author Administrator
 */
package com.my.web.common.webcommon.webconverter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.my.common.enumer.Messageable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.boot.jackson.JsonObjectSerializer;

import java.io.IOException;

@JsonComponent
@Slf4j
public class EnumJsonSerializer extends JsonObjectSerializer<Enum> {

    @Override
    protected void serializeObject(Enum value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {
        jgen.writeStringField("name", value.name());
        if (value instanceof Messageable) {
            jgen.writeStringField("code", ((Messageable) value).code());
            jgen.writeStringField("message", ((Messageable) value).message());
        }
    }

    @Override
    public Class<Enum> handledType() {
        return Enum.class;
    }
}
