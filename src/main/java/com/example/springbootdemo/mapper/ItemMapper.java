package com.example.springbootdemo.mapper;

import com.example.springbootdemo.entity.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ItemMapper {
    //@Select("select i.id , i.item_name from item i where i.id  = #{id}")
    Item findById(int id);
}
