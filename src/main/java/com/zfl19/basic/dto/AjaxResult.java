package com.zfl19.basic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 19zfl
 * @description: 将所有返回给前端的数据封装成统一的格式
 * @date 2023-12-02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AjaxResult {

    /**
     * code：状态码，便于开发者定位问题
     */
    private String code;

    /**
     * success：请求状态
     */
    private Boolean success;

    /**
     * msg：返回的信息
     */
    private String msg;

    /**
     * data：返回给前端的数据
     */
    private Object data;

    /**
     * 没有数据传送的请求成功
     * @return
     */
    public static AjaxResult getSuccess() {
        return new AjaxResult("200", true, "请求成功！", null);
    }

    /**
     * 有数据需要返回给前端的请求成功
     * @param obj
     * @return
     */
    public static AjaxResult getSuccess(Object obj) {
        return new AjaxResult("210", true, "请求成功！", obj);
    }

    /**
     * 请求失败
     * @param msg
     * @return
     */
    public static AjaxResult getError(String msg) {
        return new AjaxResult("202", false, msg, null);
    }

    /**
     * 请求失败
     * @param code  自定义code
     * @param msg   自定义msg
     * @return
     */
    public static AjaxResult getError(String code, String msg) {
        return new AjaxResult(code, false, msg, null);
    }

}
