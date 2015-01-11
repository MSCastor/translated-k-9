package com.fsck.ertebat.controller;

import com.fsck.ertebat.mail.Message;

public interface MessageRemovalListener {
    public void messageRemoved(Message message);
}
