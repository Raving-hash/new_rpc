package com.wy.httpClient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
     private String name;
     private String password;

    @Override
    public String toString() {
        return "{\"name\":\""+name+"\",\"password\":\""+password+"\"}";
    }
}
