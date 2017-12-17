package com.my.web.common.webcommon.webconverter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.my.common.exception.AppException;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class EnumJsonDeserializer extends StdDeserializer<Enum> {
    private static final String ENUM_VALUE_KEY = "name";

    protected EnumJsonDeserializer() {
        super((Class<?>) null);
    }

    protected EnumJsonDeserializer(Class<?> vc) {
        super(vc);
    }

    protected EnumJsonDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected EnumJsonDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    @Override
    public Enum deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String fieldName = p.getParsingContext().getCurrentName();
        Object currentValue = p.getParsingContext().getCurrentValue();
        Class<? extends Enum> type = getEnumClass(fieldName, currentValue);
        //字符串转枚举,比如前端post传参:type=LOCAL转成LOCAL(枚举实例)
        if ((node.asText() != null) && (node.asText().length() > 0)) {
            return Enum.valueOf(type, node.asText());
        }
        //json对象转枚举,比如:"type":{"name":"LOCAL"}转成LOCAL(枚举实例)
        if (node.get(ENUM_VALUE_KEY) != null) {
            return Enum.valueOf(type, node.get(ENUM_VALUE_KEY).asText());
        }
        throw new AppException("反序列化枚举出错!");
    }

    private Class<? extends Enum> getEnumClass(String fieldName, Object currentValue) {
        Class<? extends Enum> type;
        try {
            type = (Class<? extends Enum>) currentValue.getClass().getDeclaredField(fieldName).getType();
        } catch (NoSuchFieldException e) {
            throw new AppException(e);
        }
        return type;
    }

    @Override
    public Class<Enum> handledType() {
        return Enum.class;
    }
}
