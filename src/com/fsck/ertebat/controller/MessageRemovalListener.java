package com.fsck.Ertebat.controller;

import com.fsck.Ertebat.mail.Message;

public interface MessageRemovalListener {
    public void messageRemoved(Message message);
}
