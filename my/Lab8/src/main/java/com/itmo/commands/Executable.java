package com.itmo.commands;

import com.itmo.server.Application;
import com.itmo.client.User;

/**
 * шаблон команда
 */
public interface Executable {
    String execute(Application application, User user);
}
