package com.back.dao;

import com.llxx.pojo.images;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface imagesMapper {
    //保存图片
    @Insert("insert into images(USER_ID, IMAGE_URL, CREATE_AT) value (#{userId},#{imageUrl},#{createAt})")
    void insert(images image);

    //通过Url查询imageId
    @Select("select image_url from images where image_url=#{imageUrl}")
    Integer selectByUrl(String imageUrl);
}
