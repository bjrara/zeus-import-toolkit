package com.ctrip.zeus;

import com.ctrip.zeus.model.ModelFiller;
import com.ctrip.zeus.model.entity.GroupServer;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zhoumy on 2015/6/10.
 */
public class ServerReader {
    private final String serverFilename;
    private final String DELIMITERS = "\\s+";

    public ServerReader(String filename) {
        serverFilename = filename;
    }

    public List<GroupServer> read() throws IOException {
        String line;
        InputStream is = new FileInputStream(serverFilename);
        BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        List<String> args = new ArrayList<String>();
        while((line = br.readLine()) != null) {
            String[] s = line.split(DELIMITERS);
            for (String arg : s) {
                args.add(arg);
            }
        }
        List<GroupServer> groupServers = new ArrayList<GroupServer>();
        if (args.size() % 2 != 0)
            throw new IllegalStateException("Servers does not list by pair.");

        for (int i = 0; i < args.size(); i++) {
            GroupServer gs = new GroupServer().setHostName(args.get(i)).setIp(args.get(++i));
            ModelFiller.fillGroupServer(gs);
            groupServers.add(gs);
        }
        return groupServers;
    }
}