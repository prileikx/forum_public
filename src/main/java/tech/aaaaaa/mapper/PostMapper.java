package tech.aaaaaa.mapper;

import org.apache.ibatis.annotations.Param;
import tech.aaaaaa.pojo.Posts;

import java.util.List;

public interface PostMapper {
    //发送帖子
     Integer insertpost(@Param("uid")Integer uid,@Param("title")String title,@Param("pcid")Integer pcid,@Param("content")String content);
     //根据版区获取帖子列表
     List<Posts> selectpostList(@Param("pcid")Integer pcid,@Param("start")Integer start);
     //查询帖子内容
     Posts selectPostContent(@Param("pid")Integer pid);
     //查询帖子数量
     Integer selectcountPost(@Param("pcid")Integer pcid);
     Integer selectpcid(@Param("pid")Integer pid);
     //根据pid删除帖子
     Integer deletebypid(@Param("pid")Integer pid);
     List<Posts> searchpostbycontent(@Param("searchcontent")String searchcontent,@Param("start")Integer start);
     //计数
     Integer searchpostcountbycontent(@Param("searchcontent")String searchcontent);
     //增加查看数
     Integer updateviewcount(@Param("pid")Integer pid);
     //增加回复数
     Integer updatereplycount(@Param("pid")Integer pid);
}
