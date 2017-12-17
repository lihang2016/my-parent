package com.my.biz.obs.domain.service;

import com.my.biz.obs.app.dto.ObsDto;
import com.my.biz.obs.domain.entity.Obs;
import com.my.biz.obs.domain.repository.ObsRepository;
import com.my.common.annotation.DomainService;
import com.my.common.exception.CPBusinessException;
import com.my.common.utils.Dates;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.Date;
import java.util.UUID;

/**
 * @Author:lihang
 * @Description:文件服务
 * @Date Create in 17:16 2017/12/13
 */
@DomainService
public class ObsDomainService {

    @Autowired
    private ObsRepository obsRepository;

    /**
     * 创建
     *
     * @param obsDto
     * @return
     */
    private Long create(ObsDto obsDto) {
        try {
            Obs obs = new Obs();
            obs.from(obsDto);
            obsRepository.create(obs);
            return obs.getId();
        } catch (Exception e) {
            throw e;
        }
    }


    public Obs findById(Long id) {
        if (id == null) {
            CPBusinessException.throwIt("id不能为空");
        }
        Obs obs = obsRepository.get(id);
        if (obs == null)
            CPBusinessException.throwIt("id记录为空");
        return obs;
    }

    public Long doPut(ObsDto obsDto) {
        String filePath = getPath(obsDto.getFileName());
        File file = new File(filePath);
        file.setWritable(true, false);
        Long id = null;
        try {
            FileUtils.copyInputStreamToFile(obsDto.getInputStream(), file);
            obsDto.setProviderId(filePath);
            if (file.exists()) {
                id = create(obsDto);
            }
        } catch (Exception e) {
            file.delete();
            CPBusinessException.throwIt("上传文件错误");
        }
        return id;
    }


    private String getPath(String name) {
        String path = System.getProperty("user.dir");
        String newPath=path.substring(0, path.indexOf("\\"))+"\\Data\\img";
        String newFileName =UUID.randomUUID() + name.substring(name.indexOf("."), name.length());
        Date date = new Date();
        Integer year= Dates.getYear(date);
        Integer month=Dates.getMonth(date);
        Integer day=Dates.getDay(date);
        newPath=newPath+"\\"+year+"\\"+month+"\\"+day+"\\"+newFileName;
        return newPath;
    }

    public static void main(String[] args) {
        String name="aaa.jpg";
        String path = System.getProperty("user.dir");
        String newPath=path.substring(0, path.indexOf("\\"));
        String newFileName =UUID.randomUUID() + name.substring(name.indexOf("."), name.length());
        Date date = new Date();
        Integer year= Dates.getYear(date);
        Integer month=Dates.getMonth(date);
        Integer day=Dates.getDay(date);
        newPath=newPath+"\\"+year+"\\"+month+"\\"+day+"\\"+newFileName;
        System.out.println(newPath);
    }
}
