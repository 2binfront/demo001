package com.example.demo001.controller;

import com.example.demo001.dto.UserDto;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class TestController {
    /**
     * 限流策略 ：1秒钟100个请求
     */

    private final RateLimiter limiter = RateLimiter.create(100);
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 查询用户列表，返回一个JSON数组
     *
     * @return*/

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsers(){
        boolean tryAcquire = limiter.tryAcquire(10, TimeUnit.MILLISECONDS);
        if (!tryAcquire) {
            log.warn("进入服务降级，时间{}", LocalDateTime.now().format(dtf));
            List s = Arrays.asList(new String("当前排队人数较多，请稍后再试！"));
            return s;
        }
        log.info("获取令牌成功，时间{}", LocalDateTime.now().format(dtf));
        List<UserDto> list = getData();
        return list;
    }

    /**
     * 查询用户信息，返回一个新建的JSON对象
     * */
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Object getUser(@PathVariable("id") Long id){
        boolean tryAcquire = limiter.tryAcquire(10, TimeUnit.MILLISECONDS);
        if (!tryAcquire) {
            log.warn("进入服务降级，时间{}", LocalDateTime.now().format(dtf));
            String s = "当前排队人数较多，请稍后再试！";
            return s;
        }
        log.info("获取令牌成功，时间{}", LocalDateTime.now().format(dtf));

        if(Objects.isNull(id)){
            return null;
        }
        List<UserDto> list= getData();
        UserDto userDto = getUserDto(id, list);
        return userDto;
    }

    /**
     * 新增用户
     * */
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public Object addUser(@RequestBody UserDto user){
        boolean tryAcquire = limiter.tryAcquire(10, TimeUnit.MILLISECONDS);
        if (!tryAcquire) {
            log.warn("进入服务降级，时间{}", LocalDateTime.now().format(dtf));
            String s = "当前排队人数较多，请稍后再试！";
            return s;
        }
        log.info("获取令牌成功，时间{}", LocalDateTime.now().format(dtf));

        List<UserDto> list= getData();
        list.add(user);//模拟向列表中增加数据
        return user;
    }


    /**
     * 删除用户
     * */
    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Object deleteUser(@PathVariable("id") Long id){
        boolean tryAcquire = limiter.tryAcquire(10, TimeUnit.MILLISECONDS);
        if (!tryAcquire) {
            log.warn("进入服务降级，时间{}", LocalDateTime.now().format(dtf));
            String s = "当前排队人数较多，请稍后再试！";
            return s;
        }
        log.info("获取令牌成功，时间{}", LocalDateTime.now().format(dtf));

        List<UserDto> list = getData();
        UserDto userDto = getUserDto(id, list);
        return  userDto;
    }

    /**
     * 模拟数据
     * */
    private List<UserDto> getData(){
        List<UserDto> list=new ArrayList<>();

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("admin");
        userDto.setPwd("admin");
        list.add(userDto);

        userDto = new UserDto();
        userDto.setId(2L);
        userDto.setName("Test1");
        userDto.setPwd("Test1");
        list.add(userDto);

        userDto = new UserDto();
        userDto.setId(3L);
        userDto.setName("Test2");
        userDto.setPwd("Test2");
        list.add(userDto);

        userDto = new UserDto();
        userDto.setId(4L);
        userDto.setName("Test3");
        userDto.setPwd("Test3");
        list.add(userDto);

        return  list;
    }

    /**
     *  模拟根据id查询列表中的数据
     * @param id
     * @param list
     * @return
     */
    private UserDto getUserDto( Long id, List<UserDto> list) {
        boolean tryAcquire = limiter.tryAcquire(10, TimeUnit.MILLISECONDS);
        if (!tryAcquire) {
            log.warn("进入服务降级，时间{}", LocalDateTime.now().format(dtf));
            UserDto s = new UserDto();
            s.setName("当前排队人数较多，请稍后再试！");
            return s;
        }
        log.info("获取令牌成功，时间{}", LocalDateTime.now().format(dtf));

        UserDto UserDto = null;
        for (UserDto user : list) {
            if (id.equals(user.getId())) {
                UserDto = user;
                break;
            }
        }
        return UserDto;
    }
}