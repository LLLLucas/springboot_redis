package com.lucas.springboot_redis.controller;

import com.lucas.springboot_redis.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RestController
public class StudentController {
    @Autowired
    private RedisTemplate redisTemplate;
    @PostMapping("/set")
    public void set(@RequestBody Student student){
        redisTemplate.opsForValue().set("student",student);
    }
    @GetMapping("/get/{key}")
    public Student get(@PathVariable("key") String key){
        return (Student) redisTemplate.opsForValue().get(key);
    }
    @DeleteMapping("/delete/{key}")
    public boolean delete(@PathVariable("key") String key){
        Boolean delete = redisTemplate.delete(key);
        System.out.println(delete);
        return redisTemplate.hasKey(key);
    }
    @GetMapping("/string")
    public String stringTest(){
        redisTemplate.opsForValue().set("str","helloWorld");
        String str = (String)redisTemplate.opsForValue().get("str");
        return str;
    }
    /*
    列表的存储List
     */
    @GetMapping("/list")
    public List<String> listTest(){
        ListOperations<String,String> listOperations = redisTemplate.opsForList();
        listOperations.leftPush("list","hello");
        listOperations.leftPush("list","hello");
        listOperations.leftPush("list","world");
        listOperations.leftPush("list","world");
        listOperations.leftPush("list","java");
        List<String> list = listOperations.range("list", 0, 4);
        return list;

    }
    /*
    Set集合的存储
     */
    @GetMapping("/set2")
    public Set<String> setTest(){
        SetOperations setOperations = redisTemplate.opsForSet();
        setOperations.add("set","hello");
        setOperations.add("set","hello");
        setOperations.add("set","world");
        setOperations.add("set","world");
        setOperations.add("set","java");
        Set set = setOperations.members("set");
        return set;
    }
    /*
    有序的sort
     */
    @GetMapping("/sortedSet")
    public Set<String> sortSetTest(){
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add("zset","hello",1);
        zSetOperations.add("zset","world",2);
        zSetOperations.add("zset","Java",3);
        Set zset = zSetOperations.range("zset", 0, 2);
        return zset;

    }
    @GetMapping("/hash")
    public String hash(){
        HashOperations<String,String,String> hashOperations = redisTemplate.opsForHash();
        hashOperations.put("hash1","hashKey1","hashValue1");
        hashOperations.put("hash2","hashKey2","hashValue2");
        hashOperations.put("hash3","hashKey3","hashValue3");
        String s = hashOperations.get("hash1", "hashKey1");
        return s;
    }



}
