package com.cssl.dao;

import com.cssl.pojo.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepository extends MongoRepository<Users,Integer> {
    public Users findByUsernameAndPassword(String username,String password);
}
