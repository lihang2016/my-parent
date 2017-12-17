package com.my.web.common.webcommon.webconverter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.my.common.utils.Dates;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.Date;

/**
 * @Author:lihang
 * @Description:格式化时间
 * @Date Create in 16:00 2017/12/11
 */
@JsonComponent
public class DateJsnSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeString(Dates.format(date,Dates.CHINESE_DATETIME_FORMAT_LINE));
    }
}
