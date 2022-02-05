package com.itmo.server;

import com.itmo.client.User;
import com.itmo.collection.dragon.classes.Dragon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor @Setter @Getter
public class Response implements Serializable {
    private Set<Dragon> dragons;
    private String answer;
    private User user;
    public Response(String answer) {
        this.answer = answer;
    }
}