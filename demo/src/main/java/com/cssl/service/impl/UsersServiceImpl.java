package com.cssl.service.impl;

import com.cssl.dao.UsersDao;
import com.cssl.dao.UsersRepository;
import com.cssl.pojo.Users;
import com.cssl.service.UsersService;
import com.cssl.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersDao udao;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UsersRepository ur;

    public List<Users> find(){
        return ur.findAll();
    }



    /**
     * redis
     * @return
     */
    @Override
    public List<Users> query() {
        return udao.select();
    }

    @Override
    public List<Users> queryAll() {
        List<Users> list=null;
        if(redisUtil.exists("list")){
            System.out.println("redis");
            list=(List<Users>) redisUtil.get("list");
        }else{
            System.out.println("mysql");
            list=udao.select();
            redisUtil.set("list",list);
        }
        return list;
    }

    @Override
    @Cacheable(value = "user", key = "#id")
    public Users queryById(Integer id) {
        System.out.println("service...queryByid");
        return udao.queryById(id);
    }
    @Override
    @CachePut(value="user",key="#id")
    public int update(Users users){
        return udao.update(users);
    }
    @Override
    @CacheEvict(value="user",key="#id")
    public int del(Integer id){
        return udao.del(id);
    }


}
