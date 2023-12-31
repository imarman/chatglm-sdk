package com.arman.glm.sdk.model.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author Arman
 * @date 2023/10/18
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class GlmResponse<T> {

    private T data;

    private String msg;

    private Integer code;

    private Boolean success;

}
