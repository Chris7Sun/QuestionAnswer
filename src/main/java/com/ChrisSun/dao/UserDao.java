package com.ChrisSun.dao;

import com.ChrisSun.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Created by Chris on 2016/7/24.
 */
@Mapper
@Component
public interface UserDao {
    String TABLE_NAME = "user";
    String INSERT_FIELDS = "name,password,salt,head_url";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;

    @Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,") Values(#{name},#{password},#{salt},#{headUrl})"})
    public void addUser(User user);
    @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME,"where id=#{id}"})
    public User getUserById(int id);
    @Update({"update ",TABLE_NAME," set password=#{password} where id=#{id}"})
    public void updatePassword(User user);

    public List<User> selectByUserIdAndOffset(@Param("id") int id,
                                              @Param("offset") int offset,
                                              @Param("limit") int limit);

    @Select({"select ",SELECT_FIELDS,"from ",TABLE_NAME,"where name=#{userName}"})
    public User getUserByName(String userName);
}
