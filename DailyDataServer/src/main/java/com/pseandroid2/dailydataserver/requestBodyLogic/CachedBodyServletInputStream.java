package com.pseandroid2.dailydataserver.requestBodyLogic;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * #TODO official Test, JavaDoc
 */
public class CachedBodyServletInputStream extends ServletInputStream {
    private InputStream bodyInputStream;

    public CachedBodyServletInputStream(byte[] body) {
        this.bodyInputStream = new ByteArrayInputStream(body);
    }


    @Override
    public boolean isFinished() {
        try {
            return (bodyInputStream.available() == 0);
        } catch (IOException e) {
            e.printStackTrace();
            //#TODO
        }
        return false;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener listener) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int read() throws IOException {
        return bodyInputStream.read();
    }
}
