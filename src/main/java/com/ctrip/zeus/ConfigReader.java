package com.ctrip.zeus;

import com.ctrip.zeus.config.entity.Rules;
import com.ctrip.zeus.config.transform.DefaultSaxParser;
import org.xml.sax.SAXException;

import java.io.*;

/**
 * Created by zhoumy on 2015/6/9.
 */
public class ConfigReader {
    private final String filename;

    public ConfigReader(String filename) {
        this.filename = filename;
    }

    public Rules read() throws IOException, SAXException {
        FileInputStream is = null;
        try {
            is = new FileInputStream(filename);
            return DefaultSaxParser.parse(is);
        } finally {
            if (is != null)
                is.close();
        }
    }
}