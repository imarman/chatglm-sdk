package com.arman.glm.sdk.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色
 *
 * @author Arman
 */
@Getter
@AllArgsConstructor
public enum Role {

    /**
     * user 用户输入的内容，role位user
     */
    USER("user"),

    /**
     * 模型生成的内容，role位assistant
     */
    ASSISTANT("assistant"),

    ;

    private final String code;

}
