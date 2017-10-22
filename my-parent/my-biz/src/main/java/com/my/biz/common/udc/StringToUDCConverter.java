
package com.my.biz.common.udc;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import org.springframework.core.convert.converter.Converter;


/**
 *
 */
public class StringToUDCConverter implements Converter<String, UDC> {

    @Override
    public UDC convert(String source) {
        if (Strings.isNullOrEmpty(source)) {
            return null;
        } else {
            JSONObject jsStr = JSONObject.parseObject(source); //将字符串{“id”：1}
            return UDC.newUDCWithItemValue(jsStr.getString("typeCode"), jsStr.getIntValue("itemValue"));
//            List<String> list = Splitter.on(SplitConstants.SEPARATOR_CHAR_COMMA).trimResults().splitToList(source);
//            if (list.size() != 2) {
//                throw Exceptions.newRuntimeException("UDC类型必须用逗号隔开");
//            }
//            return UDC.newUDCWithItemValue(list.get(0), Integer.parseInt(list.get(1)));
        }
    }
}
