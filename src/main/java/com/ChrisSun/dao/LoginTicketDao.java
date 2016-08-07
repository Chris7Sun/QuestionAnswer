package com.ChrisSun.dao;

import com.ChrisSun.model.LoginTicket;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * Created by Chris on 2016/7/30.
 */
@Mapper
@Component
public interface LoginTicketDao {
    String TABLE_NAME = "login_ticket";
    String INSERT_FIELDS = "user_id,ticket,expired,status";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;

    @Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,") Values(#{userId},#{ticket},#{expired},#{status})"})
    public void addTicket(LoginTicket ticket);

    @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where ticket=#{ticket}"})
    public LoginTicket selectByTicket(String ticket);

    @Update({"update ",TABLE_NAME," set status=#{status} where ticket=#{ticket}"})
    public void updateStatus(@Param("ticket") String ticket, @Param("status") int status);

    @Select({"select ticket from ",TABLE_NAME," where user_id=#{userId} and status=#{status}"})
    public String selectTicketByUseridAndStatus(@Param("userId") int userId, @Param("status") int status);

}
