package com.ctrip.zeus;

import com.ctrip.zeus.config.entity.Rule;
import com.ctrip.zeus.config.entity.Rules;
import com.ctrip.zeus.model.ModelFiller;
import com.ctrip.zeus.model.RewriteRule;

import com.ctrip.zeus.model.entity.Group;
import com.ctrip.zeus.model.entity.GroupServer;
import com.ctrip.zeus.model.entity.GroupSlb;
import com.ctrip.zeus.model.entity.VirtualServer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zhoumy on 2015/6/10.
 */
public class Transformer {
    private static final String REDIRECT = "Redirect rule is not supported.";
    private static final String REWRITE = "More than one rewrite rule is found.";
    private static final String BACK_SLASH = "\\\\";

    private final Set<String> visitedGroup = new HashSet<String>();
    private final List<Rule> invalidRules = new ArrayList<Rule>();
    private final List<RewriteRule> rewriteRules = new ArrayList<RewriteRule>();

    public List<Group> transform(List<Rule> rules, Long virtualServerId, List<GroupServer> groupServers) {
        List<Group> groups = new ArrayList<Group>();
        Group g = transform("Gonglue", rules, virtualServerId, groupServers);
        ModelFiller.fillGroup(g);
        groups.add(g);
        try {
            System.out.println("[INFO] Write invalid rules to invalid-rules.xml.");
            exportInvalidRules();
        } catch (Exception e) {
            System.out.println("[ERROR] Fail to write invalid rule metadata.");
        }
        return groups;
    }

    public List<RewriteRule> getRewriteRules() {
        return rewriteRules;
    }

    private Group transform(String groupName, List<Rule> rule, Long virtualServerId, List<GroupServer> groupServers) {
        Group group = new Group().setName(groupName).setAppId("000000");
        group.addGroupSlb(transformRule(virtualServerId, rule));
        for (GroupServer groupServer : groupServers) {
            group.addGroupServer(groupServer);
        }
        return group;
    }

    private GroupSlb transformRule(Long virtualServerId, List<Rule> rules) {
        StringBuilder sb = new StringBuilder();
        for (Rule rule: rules) {
            String rewrite = generateRewrite(rule);
            if (rewrite == null)
                continue;
            sb.append(rewrite + ";");
        }
        String rewrite = sb.toString();
        GroupSlb groupSlb = new GroupSlb().setPath("~* /")
                .setSlbId(1L)
                .setRewrite(rewrite.substring(0, rewrite.length() - 2))
                .setVirtualServer(new VirtualServer().setId(virtualServerId));
        return groupSlb;
    }

    public String generateRewrite(Rule rule) {
        if (rule.getAction().getType().equals("Redirect")) {
            reportError(rule, REDIRECT);
            return null;
        }
        if (visitedGroup.contains(rule.getName())) {
            reportError(rule, REWRITE);
            return null;
        }
        String path = "";
        if (rule.getMatch().getUrl().startsWith("^")) {
            path += "^/" + rule.getMatch().getUrl().substring(1).replaceAll("\\/", "/");
            path = path.replaceAll(BACK_SLASH + BACK_SLASH, BACK_SLASH + BACK_SLASH + BACK_SLASH + BACK_SLASH);
        }

        String in = "\"(?i)" + path + "\"";
        String out = rule.getAction().getUrl().replaceAll("\\{R:([0-9]*)\\}", "\\$$1");
        Boolean appendQueryString;
        if ((appendQueryString = rule.getAction().getAppendQueryString()) != null
                && (!appendQueryString.booleanValue())) {
            out = out + "?";
        }
        visitedGroup.add(rule.getName());
        rewriteRules.add(new RewriteRule().setIn(in).setOut(out).setRuleName(rule.getName()));
        return in + " " + out;
    }

    private void exportInvalidRules() throws IOException, JAXBException {
        if (invalidRules.size() == 0)
            return;
        JAXBContext jctxt = JAXBContext.newInstance(Rules.class);
        Marshaller marshaller = jctxt.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);

        Rules rules = new Rules();
        for (Rule invalidRule : invalidRules) {
            rules.addRule(invalidRule);
        }

        File file = new File("invalid-rules.xml");
        if (!file.exists()) {
            file.createNewFile();
        }
        marshaller.marshal(rules, file);
    }

    private void reportError(Rule rule, String info) {
        System.out.println(rule.getName() + ": " + info);
        invalidRules.add(rule);
    }
}
