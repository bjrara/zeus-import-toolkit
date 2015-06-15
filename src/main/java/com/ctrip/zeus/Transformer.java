package com.ctrip.zeus;

import com.ctrip.zeus.model.ModelFiller;
import com.ctrip.zeus.model.RewriteRule;
import com.ctrip.zeus.model.config.Rule;
import com.ctrip.zeus.model.config.Rules;
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
        int i = 0;
        for (Rule rule : rules) {
            Group g = transform("Gonglue" + i, rule, virtualServerId, groupServers);
            if (g == null)
                continue;
            i++;
            ModelFiller.fillGroup(g);
            groups.add(g);
        }
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

    private Group transform(String groupName, Rule rule, Long virtualServerId, List<GroupServer> groupServers) {
        Group group = new Group().setName(groupName).setAppId("000000");
        if (rule.getAction().getType().equals("Redirect")) {
            reportError(rule, REDIRECT);
            return null;
        }
        if (visitedGroup.contains(rule.getName())) {
            reportError(rule, REWRITE);
            return null;
        }
        group.addGroupSlb(transformRule(virtualServerId, rule));
        for (GroupServer groupServer : groupServers) {
            group.addGroupServer(groupServer);
        }
        return group;
    }

    private GroupSlb transformRule(Long virtualServerId, Rule rule) {
        String path = "";
        if (rule.getMatch().getUrl().startsWith("^")) {
            path += "^/" + rule.getMatch().getUrl().substring(1).replaceAll("\\/", "/");
            path = path.replaceAll(BACK_SLASH + BACK_SLASH, BACK_SLASH + BACK_SLASH + BACK_SLASH + BACK_SLASH);
        }
        GroupSlb groupSlb = new GroupSlb().setPath("~* \"" + path + "\"")
                .setSlbId(1L)
                .setVirtualServer(new VirtualServer().setId(virtualServerId));

        String in = "\"(?i)" + path + "\"";
        String out = rule.getAction().getUrl().replaceAll("\\{R:([0-9]*)\\}", "\\$$1");
        String appendQueryString;
        if ((appendQueryString = rule.getAction().getAppendQueryString()) != null
                && appendQueryString.equals("false")) {
            out = out + "?";
        }

        groupSlb.setRewrite(in + " " + out);
        visitedGroup.add(rule.getName());
        rewriteRules.add(new RewriteRule().setIn(in).setOut(out).setRuleName(rule.getName()));
        return groupSlb;
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
            rules.getRule().add(invalidRule);
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
