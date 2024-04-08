package com.springbasic.board.mapper;

import com.springbasic.board.domain.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegisterMapper {
    void add(UserDTO userDTO);
}
