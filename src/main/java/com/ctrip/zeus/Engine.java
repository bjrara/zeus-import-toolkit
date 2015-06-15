package com.ctrip.zeus;

import com.ctrip.zeus.model.config.Rules;
import com.ctrip.zeus.model.entity.Group;
import com.ctrip.zeus.model.entity.GroupList;
import com.ctrip.zeus.model.entity.GroupServer;

import java.io.*;
import java.util.List;

/**
 * Created by zhoumy on 2015/6/10.
 */
public class Engine {
    // run with args: domain, config filename, server list filename
    public static void main(String[] args) {
        Long vsId = Long.parseLong(args[0]);
        String configFilename = args[1];
        String serverFilename = args[2];

        try {
            ServerReader serverReader = new ServerReader(serverFilename);
            List<GroupServer> servers = serverReader.read();
            ConfigReader configReader = new ConfigReader(configFilename);
            Rules rules = configReader.read();
            Transformer transformer = new Transformer();
            List<Group> groups = transformer.transform(rules.getRule(), vsId, servers);
            System.out.println("--------------- Export information ---------------");
            System.out.println("Group count: " + groups.size());
            System.out.println("Server count: " + servers.size());
            System.out.println("--------------------------------------------------");
            GroupList groupList = new GroupList();
            for (Group group : groups) {
                groupList.addGroup(group);
            }
            System.out.println("[INFO] Writing file to " + "result.json");
            writeToFile(groupList);
            System.out.println("[INFO] Writing batch to new-group-batch.sh");
            writeBatch(groups);
            System.out.println("[INFO] Writing rewrite rules to rewrite-rules.xls.");
            RewriteRulesWriter.writeToExcel(transformer.getRewriteRules());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeToFile(GroupList groupList) throws IOException {
        File file = new File("result.json");
        if (!file.exists()) {
            file.createNewFile();
        }
        Writer writer = new BufferedWriter(new FileWriter(file));
        writer.write(JsonWriter.write(groupList));
    }

    private static void writeBatch(List<Group> groupList) throws IOException {
        File file = new File("new-group-batch.sh");
        if (file.exists()) {
            file.createNewFile();
        }

        String command1 = "curl -H \"'Content-Type': 'application/json'\" --data ";
        String command2 = "http://10.8.95.31:8099/api/group/new";
        for (int i = 0; i < groupList.size(); i++) {
            Writer writer = new BufferedWriter(new FileWriter(file, true));
            writer.append(command1 + "\"" + JsonWriter.writeCompact(groupList.get(i)).replace("\"", "'") + "\" " + command2 + "\n");
            writer.flush();
        }
    }
}