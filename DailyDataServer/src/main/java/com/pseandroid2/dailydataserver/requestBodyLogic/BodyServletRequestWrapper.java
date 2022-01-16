package com.pseandroid2.dailydataserver.requestBodyLogic;

import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * #TODO official Test
 */
public class BodyServletRequestWrapper extends HttpServletRequestWrapper {

    private byte[] body;

    public BodyServletRequestWrapper(HttpServletRequest request) throws IOException{
        super(request);

        InputStream requestInputStream = request.getInputStream();
        this.body = StreamUtils.copyToByteArray(requestInputStream);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException{
        return new CachedBodyServletInputStream(this.body);
    }

    @Override
    public BufferedReader getReader() throws IOException{
        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(this.body);

        return new BufferedReader(new InputStreamReader(byteInputStream));
    }
}
