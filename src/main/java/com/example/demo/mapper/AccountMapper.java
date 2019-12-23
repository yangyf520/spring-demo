package com.example.demo.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AccountMapper {

    int update(@Param("money") double money, @Param("id") int  id);

    @Select("select 2")
    int sqlConnect();
}
