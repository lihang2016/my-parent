package com.my.common.utils;


import java.util.Collection;
import java.util.Map;

/**
 * Created by 96230 on 2017/6/10.
 */
public class StringUtil {

    /**
     * 使用java正则表达式去掉多余的.与0
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s){
        if (s==null || "".equals(s)) return "0";
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    public static boolean isBlank(String str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 检查字符串是否为<code>null</code>或空字符串<code>""</code>。
     * <p/>
     * <p>
     * <pre>
     * ExStringUtils.isEmpty(null)      = true
     * ExStringUtils.isEmpty("")        = true
     * ExStringUtils.isEmpty(" ")       = false
     * ExStringUtils.isEmpty("bob")     = false
     * ExStringUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     * @param str 要检查的字符串
     * @return 如果为空, 则返回<code>true</code>
     */
    public static boolean isEmpty(String str) {
        return ((str == null) || (str.length() == 0));
    }

    /**
     * 判断对象是否为空
     *
     * @param obj
     *            -参数对象
     * @return boolean -true:表示对象为空;false:表示对象为非空 集合： Collection.isEmpty()
     *         数组：判断数组每个元素，所有元素都为空，即判定数组为空
     *         字符串：判断字符串等于"null"，或去除两端""字窜后返回String.isEmpty()的结果 其它类型返回 false
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null)
            return true;

        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).entrySet().isEmpty();
        }

        if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        }

        if (obj instanceof String) {
            return ((String) obj).equalsIgnoreCase("null") |((String) obj).trim().isEmpty();
            // 为了解决列表查询输入null不能查询所有
            //return ((String) obj).trim().isEmpty();
        }

        if (obj instanceof StringBuffer) {
            return ((StringBuffer) obj).length() == 0;
        }

        if (obj.getClass().isArray()) {
            try {
                Object[] a = (Object[]) obj;

                boolean b = true;
                for (Object o : a) {
                    b = b & isEmpty(o);

                    if (!b)
                        break;
                }

                return b;
            } catch (ClassCastException e) {
            }
        }

        return false;
    }

}