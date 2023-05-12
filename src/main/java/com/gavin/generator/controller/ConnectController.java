package com.gavin.generator.controller;

import com.gavin.generator.domin.AjaxResult;
import com.gavin.generator.domin.DataSourceInfo;
import com.gavin.generator.service.IDataSourceService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Gavin Lin
 */
@RestController
@RequestMapping("/datasource")
public class ConnectController {

    @Resource
    private IDataSourceService dataSourceService;

    @PostMapping("/connect")
    public AjaxResult connect(@Validated @RequestBody DataSourceInfo dataSourceInfo) {
        dataSourceInfo.setPoolName("GeneratorDataSource");
        dataSourceInfo.setUrl("jdbc:mysql://" + dataSourceInfo.getHostname() + ":" + dataSourceInfo.getPort() + "/" + dataSourceInfo.getSchema() + "?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8");
        dataSourceService.addDataSource(dataSourceInfo);
        return AjaxResult.success();
    }
}
