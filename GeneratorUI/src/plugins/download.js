import axios from "axios";
import { ElNotification, ElMessage, ElLoading } from "element-plus";
import { saveAs } from "file-saver";
import errorCode from "@/utils/errorCode";
import { blobValidate } from "@/utils/common";

const baseURL = import.meta.env.VITE_APP_BASE_API;

export default {
  zip(url, name) {
    var url = baseURL + url;
    axios({
      method: "get",
      url: url,
      responseType: "blob",
    }).then(async (res) => {
      const isLogin = await blobValidate(res.data);
      if (isLogin) {
        const blob = new Blob([res.data], { type: "application/zip" });
        this.saveAs(blob, name);
      } else {
        this.printErrMsg(res.data);
      }
    });
  },
  saveAs(text, name, opts) {
    saveAs(text, name, opts);
  },
  async printErrMsg(data) {
    const resText = await data.text();
    const rspObj = JSON.parse(resText);
    const errMsg = errorCode[rspObj.code] || rspObj.msg || errorCode["default"];
    ElMessage.error(errMsg);
  },
};
