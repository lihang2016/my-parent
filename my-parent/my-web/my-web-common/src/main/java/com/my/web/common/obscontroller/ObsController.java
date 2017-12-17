package com.my.web.common.obscontroller;

import com.my.biz.common.interactive.ViewInfo;
import com.my.biz.obs.app.dto.ObsDto;
import com.my.biz.obs.app.dto.ObsViewDto;
import com.my.biz.obs.app.service.ObsAppService;
import com.my.common.utils.FilesUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

import static com.my.common.utils.FilesUtil.isPreviewFileType;


/**
 * @Author:lihang
 * @Description:文件公共controller
 * @Date Create in 15:51 2017/12/13
 */
@RestController
@RequestMapping("/obs")
public class ObsController {

    @Autowired
    private ObsAppService obsAppService;

    @PostMapping(value = "/upload.json")
    public ViewInfo upload(HttpServletRequest request) throws Exception {
        ViewInfo viewResult = new ViewInfo();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        if (file.isEmpty()) {
            viewResult.setMessage("上传失败：未选择文件");
            viewResult.setSuccess(false);
            return viewResult;
        }
        ObsDto obsDto=new ObsDto();
        obsDto.setFileName(file.getOriginalFilename());
        obsDto.setFileSize(file.getSize());
        obsDto.setInputStream(file.getInputStream());
        Long id = obsAppService.put(obsDto).getData();
        if(id!=null){
            ObsViewDto obsViewDto=new ObsViewDto();
            obsViewDto.setViewUrl("/obs/view.json?id=" + id);
            obsViewDto.setDownloadUrl("/obs/download.json?id=" + id);
            obsViewDto.setId(id);
            viewResult.setData(obsViewDto);
        }else{
            viewResult.setSuccess(false);
            viewResult.setMessage("上传文件异常");
        }
        return viewResult;
    }

    /**
     * 预览文件
     * @param id
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET, value = "/view.json")
    public void viewFile(Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ObsDto obsDto=obsAppService.get(id).getData();
        byte[] bytes = FileUtils.readFileToByteArray(new File(obsDto.getProviderId()));
        //文件可预览,直接预览,否则转到下载
        if (isPreviewFileType(obsDto.getFileName())) {
            IOUtils.write(bytes, response.getOutputStream());
        } else {
            FilesUtil.download(request, response, obsDto.getFileName(), bytes);
        }
    }

    /**
     * 下载文件
     */
    @RequestMapping(value = "/download.json")
    public void downloadFile(Long id, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ObsDto obsDto = obsAppService.get(id).getData();
        if (obsDto!=null&&obsDto.getProviderId()!=null) {
            FilesUtil.download(request,response,obsDto.getFileName(),FileUtils.readFileToByteArray(new File(obsDto.getProviderId())));
        }else{
            throw new RuntimeException("文件id:"+id+",下载文件失败");
        }
    }

}
