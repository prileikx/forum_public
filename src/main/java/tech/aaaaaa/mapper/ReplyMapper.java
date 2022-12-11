package tech.aaaaaa.mapper;

import org.apache.ibatis.annotations.Param;
import tech.aaaaaa.pojo.Reply;

import java.util.List;

public interface ReplyMapper {
    //查询该帖子内的所有回复
    List<Reply> replyList(@Param("start")Integer start,@Param("pid")Integer pid);
    //查询该帖子内一共有多少条回复
    Integer selectreplycount(@Param("pid")Integer pid);
    //发送回复
    void insertReply(@Param("pid")Integer pid,@Param("uid")Integer uid,@Param("content")String content);
}
