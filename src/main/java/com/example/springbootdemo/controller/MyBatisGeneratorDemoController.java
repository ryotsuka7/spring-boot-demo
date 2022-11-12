package com.example.springbootdemo.controller;

import com.example.springbootdemo.mapper.ItemMapper;
import example.mapper.DemoMapper;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class MyBatisGeneratorDemoController {

    @Autowired
    DemoMapper demoMapper;

    @Autowired
    ItemMapper itemMapper;

    @GetMapping
    public void doGet() {

        if (true) {
            return;
        }

        // select by pk
        example.model.Item item = demoMapper.selectByPrimaryKey(1);
        printBean(item);
        // insert
        item = new example.model.Item();
        item.setItemName("自動生成Mapper");
        int ret = demoMapper.insert(item);
        printBean(item);
        ret = demoMapper.insert(item);
        printBean(item);

        // count
        example.model.ItemExample itemExample = new example.model.ItemExample();
        long longRet = demoMapper.countByExample(null);
        System.out.println("count:" + longRet);
    }

    private void printBean(Object obj) {
        System.out.println(ToStringBuilder.reflectionToString(obj));
    }
}
