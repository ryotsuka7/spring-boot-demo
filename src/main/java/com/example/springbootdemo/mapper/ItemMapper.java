package com.example.springbootdemo.mapper;

import com.example.springbootdemo.entity.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ItemMapper {
    Item findById(int id);
    List<Item> findAll();
    int insert(@Param("item") Item item);
    int update(@Param("item") Item item);
    boolean delete(int id);
}
