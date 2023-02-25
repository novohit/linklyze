package com.wyu.common.util;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.wyu.common.enums.BizCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author novo
 * @date 2023-02-20 23:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL) //如果json的data为null 不返回给前端
public class Resp {

    /**
     * 状态码 0 表示成功
     */

    private Integer code;
    /**
     * 数据
     */
    private Object data;
    /**
     * 描述
     */
    private String msg;


    /**
     *  获取远程调用数据
     *  注意事项：
     *      支持多单词下划线专驼峰（序列化和反序列化）
     *
     * @param typeReference
     * @param <T>
     * @return
     */
    public <T> T getData(TypeReference<T> typeReference){
        return JSON.parseObject(JSON.toJSONString(data),typeReference);
    }

    /**
     * 成功，不传入数据
     * @return
     */
    public static Resp success() {
        return new Resp(0, null, null);
    }

    /**
     *  成功，传入数据
     * @param data
     * @return
     */
    public static Resp success(Object data) {
        return new Resp(0, data, null);
    }

    /**
     * 失败，传入描述信息
     * @param msg
     * @return
     */
    public static Resp error(String msg) {
        return new Resp(-1, null, msg);
    }



    /**
     * 自定义状态码和错误信息
     * @param code
     * @param msg
     * @return
     */
    public static Resp buildCodeAndMsg(int code, String msg) {
        return new Resp(code, null, msg);
    }

    /**
     * 传入枚举，返回信息
     * @param codeEnum
     * @return
     */
    public static Resp buildResult(BizCodeEnum codeEnum){
        return Resp.buildCodeAndMsg(codeEnum.getCode(),codeEnum.getMessage());
    }
}
