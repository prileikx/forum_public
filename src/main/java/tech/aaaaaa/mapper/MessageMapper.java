package tech.aaaaaa.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import tech.aaaaaa.pojo.Message;

import java.util.List;

public interface MessageMapper {
    //添加消息
    void addreplyMessage(@Param("uid")Integer uid,@Param("whoreplyuid")Integer whoreplyuid,@Param("msg")String msg,@Param("pid")Integer pid);
    //获取消息总数量
    Integer getMessageCount(@Param("uid")Integer uid);
    //获取消息列表(包含公告)
    List<Message> getMessageList(@Param("uid")Integer uid,@Param("start")Integer start);
    //发送公告
    Integer insertpublicmsg(@Param("msg")String msg);
}
