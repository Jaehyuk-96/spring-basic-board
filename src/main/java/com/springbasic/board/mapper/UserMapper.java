package com.springbasic.board.mapper;

import com.springbasic.board.domain.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserDTO selectUser(String id) throws Exception;
    int deleteUser(String id) throws Exception;
    int insertUser(UserDTO userDTO) throws Exception;
    int updateUser(UserDTO userDTO) throws Exception;
    int count() throws Exception;
    void deleteAll() throws Exception;
}