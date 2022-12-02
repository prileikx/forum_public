package tech.aaaaaa.mapper;

import org.apache.ibatis.annotations.Param;

public interface UserGroupMapper {
    //查询用户权限
    Integer selectUserLimirs(@Param("ugid")Integer ugid);
}
