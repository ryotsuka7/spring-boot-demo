package com.example.springbootdemo.mapper;

import com.example.springbootdemo.entity.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ItemMapper {
    Item findById(int id);
}
