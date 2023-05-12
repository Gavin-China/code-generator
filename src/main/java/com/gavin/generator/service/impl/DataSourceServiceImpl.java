package com.gavin.generator.service.impl;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DruidDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.gavin.generator.domin.DataSourceInfo;
import com.gavin.generator.exception.ServiceException;
import com.gavin.generator.mapper.SysTableMapper;
import com.gavin.generator.service.IDataSourceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 数据源 业务层处理
 *
 * @author Gavin Lin
 */
@Service
public class DataSourceServiceImpl implements IDataSourceService {

    @Resource
    private DataSource dataSource;

    @Resource
    private DruidDataSourceCreator druidDataSourceCreator;

    @Resource
    private SysTableMapper sysTableMapper;

    /**
     * 获取数据源数量
     */
    @Override
    public Integer getDataSourceSize() {
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        return ds.getDataSources().size();
    }

    /**
     * 动态添加数据源
     */
    @Override
    public void addDataSource(DataSourceInfo dataSourceInfo) {
        DataSourceProperty dataSourceProperty = new DataSourceProperty();
        dataSourceProperty.setPoolName(dataSourceInfo.getPoolName());
        dataSourceProperty.setUrl(dataSourceInfo.getUrl());
        dataSourceProperty.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceProperty.setUsername(dataSourceInfo.getUsername());
        dataSourceProperty.setPassword(dataSourceInfo.getPassword());
        dataSourceProperty.setLazy(true);
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        DataSource newDataSource = druidDataSourceCreator.createDataSource(dataSourceProperty);
        ds.addDataSource(dataSourceInfo.getPoolName(), newDataSource);
        ds.setPrimary(dataSourceInfo.getPoolName());

        if (sysTableMapper.findTable(dataSourceInfo.getSchema(), "sys_dict_type") == 0){
            sysTableMapper.createDictType();
        }

        if (sysTableMapper.findTable(dataSourceInfo.getSchema(), "sys_dict_data") == 0){
            sysTableMapper.createDictData();
        }

        if (sysTableMapper.findTable(dataSourceInfo.getSchema(), "gen_table") == 0){
            sysTableMapper.createGenTable();
        }

        if (sysTableMapper.findTable(dataSourceInfo.getSchema(), "gen_table_column") == 0){
            sysTableMapper.createGenTableColumn();
        }
    }

    /**
     * 动态删除数据源
     * */
    @Override
    public void removeDataSource(String poolName) {
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        ds.removeDataSource(poolName);
    }
}
