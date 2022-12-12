package tech.aaaaaa.mapper;

import org.apache.ibatis.annotations.Param;
import tech.aaaaaa.pojo.Collect;

import java.util.List;

public interface CollectMapper {
    //添加收藏
    Integer insertcollect(@Param("uid")Integer uid,@Param("pid")Integer pid);
    //获得该用户的所有收藏
    List<Collect> selectrusercollect(@Param("uid")Integer uid,@Param("start")Integer start);
    //查询是否被添加过
    Collect selectifcollect(@Param("uid")Integer uid,@Param("pid")Integer pid);
    //取消收藏
    void deletecollect(@Param("uid")Integer uid,@Param("pid")Integer pid);
    //查看收藏帖子的总数量
    Integer selectcollectcount(@Param("uid")Integer uid);
}
