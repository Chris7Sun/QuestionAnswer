package com.ChrisSun.service;

import com.ChrisSun.dao.LoginTicketDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Chris on 2016/7/30.
 */
@Service
public class LoginTicketService {
    @Autowired
    private LoginTicketDao loginTicketDao;

}
