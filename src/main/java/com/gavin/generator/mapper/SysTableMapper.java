package com.gavin.generator.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Gavin Lin
 */
@Mapper
public interface SysTableMapper {

    /**
     * 根据字典类型查询信息
     *
     * @param schemaName schema名
     * @param tableName table名
     * @return 表数量
     */
    public Integer findTable(@Param("schemaName") String schemaName,@Param("tableName") String tableName);

    /**
     * 创建字典类型表
     */
    public void createDictType();

    /**
     * 创建字典数据表
     */
    public void createDictData();

    /**
     * 创建代码生成业务表
     */
    public void createGenTable();

    /**
     * 创建代码生成业务表字段
     */
    public void createGenTableColumn();
}
