package tech.aaaaaa.mapper;

import org.apache.ibatis.annotations.Param;
import tech.aaaaaa.pojo.UserGroup;

public interface UserGroupMapper {
    //查询用户权限
    Integer selectUserLimits(@Param("ugid")Integer ugid);
    //查询用户所属用户组
    UserGroup selectUserGroup(@Param("ugid")Integer ugid);
}
