package com.sso.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping(value = "/user")
@RestController
public class UserController {

    //    使用模拟数据代替业务层和数据库访问
    private static Map<Integer, String> USERS = new HashMap<>(16);

    static {
        for (int i = 1; i <= 5; i++) {
            USERS.put(i, "username" + i);
        }
    }

    /**
     * 获取所有用户信息
     *
     * @return USERS
     */
    @GetMapping
    public ResponseEntity<String> getUser() {
        try {
            if (USERS.isEmpty()) {
//            资源不存在，响应 404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
//                返回业务数据，响应 200
//                return ResponseEntity.status(HttpStatus.OK).body(USERS.toString());
                return ResponseEntity.ok(USERS.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        响应服务器内部错误，响应 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 根据 id 获取用户信息
     *
     * @param id
     * @return USER
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<String> getUser(@PathVariable("id") Integer id) {
        try {
            if (null == USERS.get(id)) {
//                资源不存在，响应 404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
//                返回业务数据，响应 200
//                return ResponseEntity.status(HttpStatus.OK).body(USERS.toString());
                return ResponseEntity.ok("select success, username = " + USERS.get(id) + " by id = " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        响应服务器内部错误，响应 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @PostMapping
    public ResponseEntity<String> addUser(Integer id, String username) {
        try {
            if (null != USERS.get(id)) {
//                用户主键 id 已存在，响应 409
                return ResponseEntity.status(HttpStatus.CONFLICT).body("id is conflict");
            }
            USERS.put(id, username);
//            添加用户成功，响应 201
            return ResponseEntity.status(HttpStatus.CREATED).body("insert user success");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        响应服务器内部错误，响应 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @PutMapping
    public ResponseEntity<String> editUser(Integer id, String username) {
        try {
            if (null == USERS.get(id)) {
//                资源不存在，响应 404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
                USERS.put(id, username);
//                没有要返回的数据，响应 204
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("update user success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        响应服务器内部错误，响应 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestParam(value = "id", defaultValue = "0") Integer id) {
        try {
            if (id == 0) {
//                传入参数有错，响应 400
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } else {
                USERS.remove(id);
//                没有要返回的数据，响应 204
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        响应服务器内部错误，响应 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

}
