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

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * #TODO official Test
 */

/**
 * Used to read the body input stream, which comes from the Client.
 * Saves the inputStream to reuse it.
 */
public class CachedBodyServletInputStream extends ServletInputStream {
    private InputStream bodyInputStream;

    /**
     * Constructor
     *
     * @param body the bytearray which is going to be an InputStream
     */
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
