package com.wy.user.mapper;

import com.wy.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select code, name, sex, phone, qq from user where name like concat('%',#{name},'%') group by code")
    List<User> selectByName( String name);
}
