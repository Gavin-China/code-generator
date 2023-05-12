package com.gavin.generator.domin;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Gavin Lin
 */
@Data
public class DataSourceInfo {

    /** 数据源名称 */
    private String poolName;

    /** 数据源地址 */
    private String url;

    /** 数据源用户名 */
    @NotBlank(message = "数据源用户名不能为空！")
    private String username;

    /** 数据源密码 */
    @NotBlank(message = "数据源密码不能为空！")
    private String password;

    /** 数据源域名 */
    @NotBlank(message = "数据源Host不能为空！")
    private String hostname;

    /** 数据源端口 */
    @NotBlank(message = "数据源Port不能为空！")
    private String port;

    /** 数据源schema */
    @NotBlank(message = "数据源schema不能为空！")
    private String schema;
}
