package com.biaoke.bklive.user.eventbus;

/**
 * Created by hasee on 2017/3/22.
 */

public class Event_nickname {
    private String nickname;

    public Event_nickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
