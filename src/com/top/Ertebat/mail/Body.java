
package com.top.Ertebat.mail;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.top.Ertebat.mail.store.UnavailableStorageException;

public interface Body {
    public InputStream getInputStream() throws MessagingException;
    public void setEncoding(String encoding) throws UnavailableStorageException, MessagingException;
    public void writeTo(OutputStream out) throws IOException, MessagingException;
}
