package com.ctrip.zeus;

import com.ctrip.zeus.model.config.Rules;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

/**
 * Created by zhoumy on 2015/6/9.
 */
public class ConfigReader {
    private final String filename;
    private Unmarshaller unmarshaller;

    public ConfigReader(String filename) {
        this.filename = filename;
        try {
            unmarshaller = createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public Rules read() throws JAXBException, IOException {
        if (unmarshaller == null) {
            unmarshaller = createUnmarshaller();
        }
        if (unmarshaller == null)
            throw new IllegalStateException("Fail to create unmarshaller.");
        FileInputStream is = null;
        try {
            is = new FileInputStream(filename);
            Reader reader = new InputStreamReader(is, "UTF-8");
            JAXBElement<Rules> element = unmarshaller.unmarshal(new StreamSource(reader), Rules.class);
            return element.getValue();
        } finally {
            if (is != null)
                is.close();
        }
    }

    private static Unmarshaller createUnmarshaller() throws JAXBException {
        JAXBContext jxtxt = JAXBContext.newInstance(Rules.class);
        return jxtxt.createUnmarshaller();
    }
}