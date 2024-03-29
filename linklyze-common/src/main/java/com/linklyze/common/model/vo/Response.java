package com.linklyze.common.model.vo;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.linklyze.common.enums.BizCodeEnum;
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
public class Response<T> {

    /**
     * 状态码 0 表示成功
     */

    private Integer code;
    /**
     * 数据
     */
    private T data;
    /**
     * 描述
     */
    private String msg;


    /**
     * 获取远程调用数据
     * 注意事项：
     * 支持多单词下划线专驼峰（序列化和反序列化）
     *
     * @param typeReference
     * @param <K>
     * @return
     */
    public <K> K getData(TypeReference<K> typeReference) {
        return JSON.parseObject(JSON.toJSONString(data), typeReference);
    }

    /**
     * 成功，不传入数据
     *
     * @return
     */
    public static <T> Response<T> success() {
        return new Response<>(BizCodeEnum.SUCCESS.getCode(), null, BizCodeEnum.SUCCESS.getMessage());
    }

    /**
     * 成功，传入数据
     *
     * @param data
     * @return
     */
    public static <T> Response<T> success(T data) {
        return new Response<>(BizCodeEnum.SUCCESS.getCode(), data, BizCodeEnum.SUCCESS.getMessage());
    }

    /**
     * 失败，传入描述信息
     *
     * @param msg
     * @return
     */
    public static <T> Response<T> error(String msg) {
        return new Response<>(BizCodeEnum.SERVER_ERROR.getCode(), null, msg);
    }


    /**
     * 自定义状态码和错误信息
     *
     * @param code
     * @param msg
     * @return
     */
    public static <T> Response<T> buildCodeAndMsg(int code, String msg) {
        return new Response<>(code, null, msg);
    }

    /**
     * 传入枚举，返回信息
     *
     * @param codeEnum
     * @return
     */
    public static <T> Response<T> buildResult(BizCodeEnum codeEnum) {
        return Response.buildCodeAndMsg(codeEnum.getCode(), codeEnum.getMessage());
    }
}
