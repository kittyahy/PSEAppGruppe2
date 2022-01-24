/*

    DailyData is an android app to easily create diagrams from data one has collected
    Copyright (C) 2022  Antonia Heiming, Anton Kadelbach, Arne Kuchenbecker, Merlin Opp, Robin Amman

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

*/
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

/**
 * Wrapper for the RequestBody to read it multible times.
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
