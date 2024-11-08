package com.llxx.mapper;

import com.llxx.pojo.images;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface imagesMapper {
    //保存图片
    @Insert("insert into images(USER_ID, IMAGE_URL, CREATE_AT) value (#{userId},#{imageUrl},#{createAt})")
    void insert(images image);

    //通过userId查询imageId
    @Select("select image_id from images where user_id=#{userId}")
    Integer selectByUserId(Integer userId);

    //通过Url查询imageId
    @Select("select image_id from images where image_url=#{imageUrl}")
    Integer selectByUrl(String imageUrl);

    //通过ids删除
    void delete(List<Integer> imageIds);
}
