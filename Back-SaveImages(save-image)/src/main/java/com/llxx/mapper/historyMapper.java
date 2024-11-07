package com.llxx.mapper;

import com.llxx.pojo.history;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface historyMapper {
    //存图片
    @Insert("insert into history (user_id, image_id, create_at) value (#{userId},#{imageId},#{createAt})")
    void insert(history hy);
}
