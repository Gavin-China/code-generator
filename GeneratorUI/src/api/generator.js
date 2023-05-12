import request from "@/utils/request";

// 查询生成表数据
export function listTable(query) {
  return request({
    url: "/generator/list",
    method: "get",
    params: query,
  });
}
// 查询db数据库列表
export function listDbTable(query) {
  return request({
    url: "/generator/db/list",
    method: "get",
    params: query,
  });
}

// 查询表详细信息
export function getGenTable(tableId) {
  return request({
    url: "/generator/" + tableId,
    method: "get",
  });
}

// 修改代码生成信息
export function updateGenTable(data) {
  return request({
    url: "/generator",
    method: "put",
    data: data,
  });
}

// 预览生成代码
export function previewTable(tableId) {
  return request({
    url: "/generator/preview/" + tableId,
    method: "get",
  });
}

// 删除表数据
export function delTable(tableId) {
  return request({
    url: "/generator/" + tableId,
    method: "delete",
  });
}

// 生成代码（自定义路径）
export function genCode(tableName) {
  return request({
    url: "/generator/genCode/" + tableName,
    method: "get",
  });
}

// 同步数据库
export function synchDb(tableName) {
  return request({
    url: "/generator/synchDb/" + tableName,
    method: "get",
  });
}

// 导入表
export function importTable(data) {
  return request({
    url: "/generator/importTable",
    method: "post",
    params: data,
  });
}
