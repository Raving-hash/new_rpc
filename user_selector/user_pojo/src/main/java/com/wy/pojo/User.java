package com.wy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 伊莫
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private String code;
    private String name;
    private String sex;
    private String phone;
    private String qq;
}
