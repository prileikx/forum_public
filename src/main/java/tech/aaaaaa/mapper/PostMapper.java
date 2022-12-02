package tech.aaaaaa.mapper;

import org.apache.ibatis.annotations.Param;

public interface PostMapper {
    //发送帖子
     Integer insertpost(@Param("uid")Integer uid,@Param("title")String title,@Param("pcid")Integer pcid,@Param("content")String content);
}
