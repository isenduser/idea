package com.cssl.service;


import com.cssl.pojo.Users;

import java.util.List;

public interface UsersService {
    public List<Users> query();

    public List<Users> queryAll();

    public Users queryById(Integer id);

    public int update(Users users);

    public int del(Integer id);

    public List<Users> find();
}
