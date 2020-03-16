package com.cssl.dao;

import com.cssl.pojo.Users;

import java.util.List;

public interface UsersDao {
    public List<Users> select();
    public Users queryById(Integer id);
    public int update (Users user);
    public int del(Integer id);
}
