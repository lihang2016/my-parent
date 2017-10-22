package com.my.web.front.type;

import com.my.biz.common.interactive.ViewInfo;
import com.my.biz.type.app.dto.AllTypeSearchDto;
import com.my.biz.type.app.service.AllTypeAppService;
import com.my.web.common.webcommon.requestmapping.FrontContrllor;
import com.my.web.front.model.ModelId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author lihang 【962309372@qq.com】
 * @Description
 * @Date 2017/7/31 21:31
 */
@RestController
public class AllTypeContrllor extends FrontContrllor {

    @Autowired
    private AllTypeAppService allTypeAppService;

    /**
     * 查询全部类型
     * @param allTypeSearchDto
     * @return
     */
    @RequestMapping("/allType/findByType.json")
    public ViewInfo findByType(AllTypeSearchDto allTypeSearchDto){
        return allTypeAppService.findAllType(allTypeSearchDto).convertTo();
    }

    /**
     * 根据大类型查找小类型
     * @param modelId
     * @return
     */
    @RequestMapping("/allType/findByTypeId.json")
    public ViewInfo findByTypeId(ModelId modelId){
        return allTypeAppService.findAllTypeById(modelId.getId()).convertTo();
    }
}
