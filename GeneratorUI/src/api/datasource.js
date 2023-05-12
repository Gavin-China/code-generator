import request from "@/utils/request";

// 查询参数列表
export function connect(data) {
  return request({
    url: "/datasource/connect",
    method: "post",
    data: data,
  });
}
