package tech.aaaaaa.mapper;

import org.apache.ibatis.annotations.Param;
import tech.aaaaaa.pojo.Posts;

import java.util.List;

public interface PostMapper {
    //发送帖子
     Integer insertpost(@Param("uid")Integer uid,@Param("title")String title,@Param("pcid")Integer pcid,@Param("content")String content);
     //根据版区获取帖子列表
     List<Posts> selectpostList(@Param("pcid")Integer pcid);
     //查询帖子内容
     Posts selectPostContent(@Param("pid")Integer pid);
}
