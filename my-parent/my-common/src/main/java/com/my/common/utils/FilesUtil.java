package com.my.common.utils;

import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * @Author:lihang
 * @Description:文件处理工具
 * @Date Create in 16:02 2017/12/13
 */
public class FilesUtil {

    private static final String[] IEBrowserSignals = {"MSIE", "Trident", "Edge"};
    /**
     * 图片和pdf才能预览
     *
     * @param fileName
     * @return 是否能预览
     */
    public static boolean isPreviewFileType(String fileName) {
        return fileName.endsWith(".jpg") || fileName.endsWith(".JPG")
                || fileName.endsWith(".png") || fileName.endsWith(".PNG")
                || fileName.endsWith(".pdf") || fileName.endsWith(".PDF");
    }

    /**
     * @return 是否微软系列浏览器
     */
    public static boolean isMSBrowser(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        for (String signal : IEBrowserSignals) {
            if (userAgent.contains(signal)) return true;
        }
        return false;
    }

    /**
     * 中文文件名处理
     *
     * @param request  请求
     * @param fileName 名称
     * @return 处理编码过后的文件名
     */
    public static String ZHCNFileName(HttpServletRequest request, String fileName) throws Exception {
        boolean isMSIE = isMSBrowser(request);
        if (isMSIE) {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } else {
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        }
        return fileName;
    }

    /**
     * 下载
     * @param request 请求request对象
     * @param response 请求response对象
     * @param fileName 文件名
     * @param content  流
     * @throws Exception
     */
    public  static void  download(HttpServletRequest request, HttpServletResponse response, String fileName, byte []content) throws Exception {
        response.addHeader(
                "Content-Disposition",
                "attachment;filename=" + FilesUtil.ZHCNFileName(request, fileName));
        response.addHeader("Content-Length", "" + content.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(content, response.getOutputStream());
        IOUtils.closeQuietly();
    }
}
