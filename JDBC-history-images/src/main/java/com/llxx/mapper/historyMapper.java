package com.llxx.mapper;

import com.llxx.pojo.history;
import com.llxx.pojo.historyRes;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface historyMapper {
    //存图片
    @Insert("insert into history (user_id, image_id, create_at) value (#{userId},#{imageId},#{createAt})")
    void insert(history hy);

    //获取全部历史记录,封装到historyRes
    @Select("select h.history_id,i.image_url,h.create_at from history h join images i on h.image_id=i.image_id where h.user_id=#{userId}")
    List<historyRes> listByUserId(Integer userId);

    //管理员获取所有历史记录
    @Select("select h.history_id,i.image_url,h.create_at from history h join images i on h.image_id=i.image_id")
    List<historyRes> list();

    //获取图像id
    @Select("select image_id from history where history_id=#{historyId} and user_id=#{userId}")
    Integer selectById(Integer historyId,Integer userId);

    //删除历史记录
    void delete(List<Integer>  historyIds);
}
