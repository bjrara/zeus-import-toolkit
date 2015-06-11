package com.ctrip.zeus.model;

/**
 * Created by zhoumy on 2015/6/11.
 */
public class RewriteRule {
    private String ruleName;
    private String in;
    private String out;

    public RewriteRule setRuleName(String ruleName) {
        this.ruleName = ruleName;
        return this;
    }

    public RewriteRule setIn(String in) {
        this.in = in;
        return this;
    }

    public RewriteRule setOut(String out) {
        this.out = out;
        return this;
    }

    public String getRuleName() {
        return ruleName;
    }

    public String getIn() {
        return in;
    }

    public String getOut() {
        return out;
    }
}
