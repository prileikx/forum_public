package tech.aaaaaa.mapper;

import org.apache.ibatis.annotations.Param;
import tech.aaaaaa.pojo.PostClass;

import java.util.List;

public interface PostClassMapper {
    //查询帖子分类(版区)权限
    Integer selectPostClassLimits(@Param("pcid")Integer pcid);
    //查询所有版区
    List<PostClass> selectPostClasspcname();
    Integer selectpcid(@Param("postclassenglishname")String postclassenglishname);
}
