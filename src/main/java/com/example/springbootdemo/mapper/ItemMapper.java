package com.example.springbootdemo.mapper;

import com.example.springbootdemo.entity.Item;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ItemMapper {

    Item findById(int id);

    List<Item> findAll();

    int insert(@Param("item") Item item);

    int update(@Param("item") Item item);

    boolean delete(int id);
}
