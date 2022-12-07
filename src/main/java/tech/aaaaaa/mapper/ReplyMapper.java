package tech.aaaaaa.mapper;

import org.apache.ibatis.annotations.Param;
import tech.aaaaaa.pojo.Reply;

import java.util.List;

public interface ReplyMapper {
    List<Reply> replyList(@Param("start")Integer start,@Param("pid")Integer pid);
}
