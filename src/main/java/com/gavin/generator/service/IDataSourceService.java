package com.gavin.generator.service;

import com.gavin.generator.domin.DataSourceInfo;
import com.gavin.generator.exception.ServiceException;

import java.sql.SQLException;

/**
 * @author Gavin Lin
 */
public interface IDataSourceService {

    /**
     * 获取数据源数量
     *
     * @return 数据源数量
     */
    public Integer getDataSourceSize();

    /**
     * 动态添加数据源
     *
     * @param dataSourceInfo 数据源实体信息
     */
    public void addDataSource(DataSourceInfo dataSourceInfo);

    /**
     * 动态删除数据源
     *
     * @param poolName 数据源名称
     * */
    public void removeDataSource(String poolName);
}
