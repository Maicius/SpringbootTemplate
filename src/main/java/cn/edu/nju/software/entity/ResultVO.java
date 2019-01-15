package cn.edu.nju.software.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * author: maicius
 * date: 2018/12/12
 * description:
 */
@ApiModel(description = "通用请求响应结果类")
@Data
public class ResultVO<T> {
    @ApiModelProperty(value = "请求结果")
    private boolean succeed;
    @ApiModelProperty(value = "附加信息")
    private String message;
    @ApiModelProperty(value = "请求的目标对象")
    private T object;

    @Override
    public String toString() {
        return "ResultVO{" +
                "succeed=" + succeed +
                ", message='" + message + '\'' +
                ", object=" + object.toString() +
                '}';
    }
}
