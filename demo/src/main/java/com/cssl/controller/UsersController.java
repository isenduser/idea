package com.cssl.controller;

import com.cssl.pojo.Users;
import com.cssl.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UsersController {
    @Autowired
    private UsersService service;
    @Autowired
    private MongoTemplate mongoTemplate;
    /**
     * mongodb
     * @return
     */
    @GetMapping("find")
    @ResponseBody
    public List<Users> find(){
        return mongoTemplate.findAll(Users.class);
        //return service.find();
    }
    @GetMapping("save")
    @ResponseBody
    public Users save(Users users){
        return mongoTemplate.save(users);
    }
    @GetMapping("upd")
    @ResponseBody
    public Users update2(Users users){
        Query query =new Query(Criteria.where("id").is(users.getId()));
        Update update=new Update().set("username",users.getUsername()).set("password",users.getPassword());
        mongoTemplate.updateFirst(query,update,Users.class);
        return users;
    }
    @GetMapping("del")
    public void del(Integer id){
        Query query=new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query,Users.class);
    }
    @GetMapping("queryPage")
    @ResponseBody
    public List<Users> queryPage(Integer pageIndex,Integer pageSize){
            Query query=new Query();
            query.with(Sort.by(Sort.Direction.DESC,"id"));
            query.skip((pageIndex-1)*pageSize);
            query.limit(pageSize);
            return  mongoTemplate.find(query,Users.class);
    }

    /**
     * redis
     * @return
     */
    @RequestMapping("query")
    @ResponseBody
    public List<Users> query(){
        return service.query();
    }
    @RequestMapping("queryAll")
    @ResponseBody
    public List<Users> queryAll(){
        return service.queryAll();
    }

    @RequestMapping("queryById")
    @ResponseBody
    public Users queryById(Integer id){
        return service.queryById(id);
    }

    @ResponseBody
    @RequestMapping("update")
    public int update(int id,String username,String password){
        Users users=new Users();
        users.setId(id);
        users.setUsername(username);
        users.setPassword(password);
        return service.update(users);
    }
    @RequestMapping("delete")
    @ResponseBody
    public int delete(Integer id){
        return service.del(id);
    }
}
