package com.ctrip.zeus.model.transform;

import com.ctrip.zeus.model.IEntity;
import com.ctrip.zeus.model.IVisitor;
import com.ctrip.zeus.model.entity.*;

import static com.ctrip.zeus.model.Constants.*;

public class DefaultXmlBuilder implements IVisitor {

   private IVisitor m_visitor = this;

   private int m_level;

   private StringBuilder m_sb;

   private boolean m_compact;

   public DefaultXmlBuilder() {
      this(false);
   }

   public DefaultXmlBuilder(boolean compact) {
      this(compact, new StringBuilder(4096));
   }

   public DefaultXmlBuilder(boolean compact, StringBuilder sb) {
      m_compact = compact;
      m_sb = sb;
      m_sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n");
   }

   public String buildXml(IEntity<?> entity) {
      entity.accept(m_visitor);
      return m_sb.toString();
   }

   protected void endTag(String name) {
      m_level--;

      indent();
      m_sb.append("</").append(name).append(">\r\n");
   }

   protected String escape(Object value) {
      return escape(value, false);
   }
   
   protected String escape(Object value, boolean text) {
      if (value == null) {
         return null;
      }

      String str = value.toString();
      int len = str.length();
      StringBuilder sb = new StringBuilder(len + 16);

      for (int i = 0; i < len; i++) {
         final char ch = str.charAt(i);

         switch (ch) {
         case '<':
            sb.append("&lt;");
            break;
         case '>':
            sb.append("&gt;");
            break;
         case '&':
            sb.append("&amp;");
            break;
         case '"':
            if (!text) {
               sb.append("&quot;");
               break;
            }
         default:
            sb.append(ch);
            break;
         }
      }

      return sb.toString();
   }
   
   protected void indent() {
      if (!m_compact) {
         for (int i = m_level - 1; i >= 0; i--) {
            m_sb.append("   ");
         }
      }
   }

   protected void startTag(String name) {
      startTag(name, false, null);
   }
   
   protected void startTag(String name, boolean closed, java.util.Map<String, String> dynamicAttributes, Object... nameValues) {
      startTag(name, null, closed, dynamicAttributes, nameValues);
   }

   protected void startTag(String name, java.util.Map<String, String> dynamicAttributes, Object... nameValues) {
      startTag(name, null, false, dynamicAttributes, nameValues);
   }

   protected void startTag(String name, Object text, boolean closed, java.util.Map<String, String> dynamicAttributes, Object... nameValues) {
      indent();

      m_sb.append('<').append(name);

      int len = nameValues.length;

      for (int i = 0; i + 1 < len; i += 2) {
         Object attrName = nameValues[i];
         Object attrValue = nameValues[i + 1];

         if (attrValue != null) {
            m_sb.append(' ').append(attrName).append("=\"").append(escape(attrValue)).append('"');
         }
      }

      if (dynamicAttributes != null) {
         for (java.util.Map.Entry<String, String> e : dynamicAttributes.entrySet()) {
            m_sb.append(' ').append(e.getKey()).append("=\"").append(escape(e.getValue())).append('"');
         }
      }

      if (text != null && closed) {
         m_sb.append('>');
         m_sb.append(escape(text, true));
         m_sb.append("</").append(name).append(">\r\n");
      } else {
         if (closed) {
            m_sb.append('/');
         } else {
            m_level++;
         }
   
         m_sb.append(">\r\n");
      }
   }

   protected void tagWithText(String name, String text, Object... nameValues) {
      if (text == null) {
         return;
      }
      
      indent();

      m_sb.append('<').append(name);

      int len = nameValues.length;

      for (int i = 0; i + 1 < len; i += 2) {
         Object attrName = nameValues[i];
         Object attrValue = nameValues[i + 1];

         if (attrValue != null) {
            m_sb.append(' ').append(attrName).append("=\"").append(escape(attrValue)).append('"');
         }
      }

      m_sb.append(">");
      m_sb.append(escape(text, true));
      m_sb.append("</").append(name).append(">\r\n");
   }

   protected void element(String name, String text, boolean escape) {
      if (text == null) {
         return;
      }
      
      indent();
      
      m_sb.append('<').append(name).append(">");
      
      if (escape) {
         m_sb.append(escape(text, true));
      } else {
         m_sb.append("<![CDATA[").append(text).append("]]>");
      }
      
      m_sb.append("</").append(name).append(">\r\n");
   }

   @Override
   public void visitArchive(Archive archive) {
      startTag(ENTITY_ARCHIVE, null);

      tagWithText(ELEMENT_ID, archive.getId() == null ? null : String.valueOf(archive.getId()));

      element(ELEMENT_CONTENT, archive.getContent(), true);

      tagWithText(ELEMENT_VERSION, archive.getVersion() == null ? null : String.valueOf(archive.getVersion()));

      endTag(ENTITY_ARCHIVE);
   }

   @Override
   public void visitConfGroupName(ConfGroupName confGroupName) {
      startTag(ENTITY_CONF_GROUP_NAME, true, null, ATTR_GROUPNAME, confGroupName.getGroupname());
   }

   @Override
   public void visitConfReq(ConfReq confReq) {
      startTag(ENTITY_CONF_REQ, null);

      if (!confReq.getConfSlbNames().isEmpty()) {
         for (ConfSlbName confSlbName : confReq.getConfSlbNames().toArray(new ConfSlbName[0])) {
            confSlbName.accept(m_visitor);
         }
      }

      if (!confReq.getConfGroupNames().isEmpty()) {
         for (ConfGroupName confGroupName : confReq.getConfGroupNames().toArray(new ConfGroupName[0])) {
            confGroupName.accept(m_visitor);
         }
      }

      endTag(ENTITY_CONF_REQ);
   }

   @Override
   public void visitConfSlbName(ConfSlbName confSlbName) {
      startTag(ENTITY_CONF_SLB_NAME, true, null, ATTR_SLBNAME, confSlbName.getSlbname());
   }

   @Override
   public void visitDomain(Domain domain) {
      startTag(ENTITY_DOMAIN, true, null, ATTR_NAME, domain.getName());
   }

   @Override
   public void visitDyUpstreamOpsData(DyUpstreamOpsData dyUpstreamOpsData) {
      startTag(ENTITY_DY_UPSTREAM_OPS_DATA, null);

      element(ELEMENT_UPSTREAM_NAME, dyUpstreamOpsData.getUpstreamName(), true);

      element(ELEMENT_UPSTREAM_COMMANDS, dyUpstreamOpsData.getUpstreamCommands(), true);

      endTag(ENTITY_DY_UPSTREAM_OPS_DATA);
   }

   @Override
   public void visitGroup(Group group) {
      startTag(ENTITY_GROUP, null, ATTR_ID, group.getId(), ATTR_NAME, group.getName(), ATTR_APP_ID, group.getAppId(), ATTR_VERSION, group.getVersion(), ATTR_SSL, group.getSsl());

      if (!group.getGroupSlbs().isEmpty()) {
         startTag(ENTITY_GROUP_SLBS);

         for (GroupSlb groupSlb : group.getGroupSlbs().toArray(new GroupSlb[0])) {
            groupSlb.accept(m_visitor);
         }

         endTag(ENTITY_GROUP_SLBS);
      }

      if (group.getHealthCheck() != null) {
         group.getHealthCheck().accept(m_visitor);
      }

      if (group.getLoadBalancingMethod() != null) {
         group.getLoadBalancingMethod().accept(m_visitor);
      }

      if (!group.getGroupServers().isEmpty()) {
         startTag(ENTITY_GROUP_SERVERS);

         for (GroupServer groupServer : group.getGroupServers().toArray(new GroupServer[0])) {
            groupServer.accept(m_visitor);
         }

         endTag(ENTITY_GROUP_SERVERS);
      }

      endTag(ENTITY_GROUP);
   }

   @Override
   public void visitGroupList(GroupList groupList) {
      startTag(ENTITY_GROUP_LIST, null);

      tagWithText(ELEMENT_TOTAL, groupList.getTotal() == null ? null : String.valueOf(groupList.getTotal()));

      if (!groupList.getGroups().isEmpty()) {
         startTag(ENTITY_GROUPS);

         for (Group group : groupList.getGroups().toArray(new Group[0])) {
            group.accept(m_visitor);
         }

         endTag(ENTITY_GROUPS);
      }

      endTag(ENTITY_GROUP_LIST);
   }

   @Override
   public void visitGroupServer(GroupServer groupServer) {
      startTag(ENTITY_GROUP_SERVER, null, ATTR_PORT, groupServer.getPort(), ATTR_WEIGHT, groupServer.getWeight(), ATTR_MAX_FAILS, groupServer.getMaxFails(), ATTR_FAIL_TIMEOUT, groupServer.getFailTimeout());

      element(ELEMENT_HOST_NAME, groupServer.getHostName(), true);

      element(ELEMENT_IP, groupServer.getIp(), true);

      endTag(ENTITY_GROUP_SERVER);
   }

   @Override
   public void visitGroupServerStatus(GroupServerStatus groupServerStatus) {
      startTag(ENTITY_GROUP_SERVER_STATUS, null);

      element(ELEMENT_IP, groupServerStatus.getIp(), true);

      tagWithText(ELEMENT_PORT, groupServerStatus.getPort() == null ? null : String.valueOf(groupServerStatus.getPort()));

      tagWithText(ELEMENT_MEMBER, groupServerStatus.getMember() == null ? null : String.valueOf(groupServerStatus.getMember()));

      tagWithText(ELEMENT_SERVER, groupServerStatus.getServer() == null ? null : String.valueOf(groupServerStatus.getServer()));

      tagWithText(ELEMENT_UP, groupServerStatus.getUp() == null ? null : String.valueOf(groupServerStatus.getUp()));

      endTag(ENTITY_GROUP_SERVER_STATUS);
   }

   @Override
   public void visitGroupSlb(GroupSlb groupSlb) {
      startTag(ENTITY_GROUP_SLB, null, ATTR_PRIORITY, groupSlb.getPriority());

      tagWithText(ELEMENT_GROUP_ID, groupSlb.getGroupId() == null ? null : String.valueOf(groupSlb.getGroupId()));

      tagWithText(ELEMENT_SLB_ID, groupSlb.getSlbId() == null ? null : String.valueOf(groupSlb.getSlbId()));

      element(ELEMENT_SLB_NAME, groupSlb.getSlbName(), true);

      element(ELEMENT_PATH, groupSlb.getPath(), true);

      element(ELEMENT_REWRITE, groupSlb.getRewrite(), true);

      if (!groupSlb.getSlbVips().isEmpty()) {
         startTag(ENTITY_SLB_VIPS);

         for (Vip vip : groupSlb.getSlbVips().toArray(new Vip[0])) {
            vip.accept(m_visitor);
         }

         endTag(ENTITY_SLB_VIPS);
      }

      if (groupSlb.getVirtualServer() != null) {
         groupSlb.getVirtualServer().accept(m_visitor);
      }

      endTag(ENTITY_GROUP_SLB);
   }

   @Override
   public void visitGroupStatus(GroupStatus groupStatus) {
      startTag(ENTITY_GROUP_STATUS, null);

      element(ELEMENT_GROUP_NAME, groupStatus.getGroupName(), true);

      element(ELEMENT_SLB_NAME, groupStatus.getSlbName(), true);

      tagWithText(ELEMENT_GROUP_ID, groupStatus.getGroupId() == null ? null : String.valueOf(groupStatus.getGroupId()));

      tagWithText(ELEMENT_SLB_ID, groupStatus.getSlbId() == null ? null : String.valueOf(groupStatus.getSlbId()));

      if (!groupStatus.getGroupServerStatuses().isEmpty()) {
         for (GroupServerStatus groupServerStatus : groupStatus.getGroupServerStatuses().toArray(new GroupServerStatus[0])) {
            groupServerStatus.accept(m_visitor);
         }
      }

      endTag(ENTITY_GROUP_STATUS);
   }

   @Override
   public void visitGroupStatusList(GroupStatusList groupStatusList) {
      startTag(ENTITY_GROUP_STATUS_LIST, null);

      tagWithText(ELEMENT_TOTAL, groupStatusList.getTotal() == null ? null : String.valueOf(groupStatusList.getTotal()));

      if (!groupStatusList.getGroupStatuses().isEmpty()) {
         startTag(ENTITY_GROUP_STATUSES);

         for (GroupStatus groupStatus : groupStatusList.getGroupStatuses().toArray(new GroupStatus[0])) {
            groupStatus.accept(m_visitor);
         }

         endTag(ENTITY_GROUP_STATUSES);
      }

      endTag(ENTITY_GROUP_STATUS_LIST);
   }

   @Override
   public void visitHealthCheck(HealthCheck healthCheck) {
      startTag(ENTITY_HEALTH_CHECK, null, ATTR_INTERVALS, healthCheck.getIntervals(), ATTR_FAILS, healthCheck.getFails(), ATTR_PASSES, healthCheck.getPasses());

      element(ELEMENT_URI, healthCheck.getUri(), true);

      endTag(ENTITY_HEALTH_CHECK);
   }

   @Override
   public void visitIpAddresses(IpAddresses ipAddresses) {
      startTag(ENTITY_IP_ADDRESSES, null);

      element(ELEMENT_IP_ADDR, ipAddresses.getIpAddr(), true);

      endTag(ENTITY_IP_ADDRESSES);
   }

   @Override
   public void visitIpGroupname(IpGroupname ipGroupname) {
      startTag(ENTITY_IP_GROUPNAME, null);

      element(ELEMENT_MEMBER_IP, ipGroupname.getMemberIp(), true);

      element(ELEMENT_MEMBER_GROUPNAME, ipGroupname.getMemberGroupname(), true);

      endTag(ENTITY_IP_GROUPNAME);
   }

   @Override
   public void visitLoadBalancingMethod(LoadBalancingMethod loadBalancingMethod) {
      startTag(ENTITY_LOAD_BALANCING_METHOD, null, ATTR_TYPE, loadBalancingMethod.getType());

      element(ELEMENT_VALUE, loadBalancingMethod.getValue(), true);

      endTag(ENTITY_LOAD_BALANCING_METHOD);
   }

   @Override
   public void visitMemberAction(MemberAction memberAction) {
      startTag(ENTITY_MEMBER_ACTION, null);

      element(ELEMENT_GROUP_NAME, memberAction.getGroupName(), true);

      if (!memberAction.getIps().isEmpty()) {
         for (String ip : memberAction.getIps().toArray(new String[0])) {
            tagWithText(ELEMENT_IP, ip);
         }
      }

      endTag(ENTITY_MEMBER_ACTION);
   }

   @Override
   public void visitModel(Model model) {
      startTag(ENTITY_MODEL, null);

      if (model.getSlb() != null) {
         model.getSlb().accept(m_visitor);
      }

      if (model.getSlbList() != null) {
         model.getSlbList().accept(m_visitor);
      }

      if (model.getVip() != null) {
         model.getVip().accept(m_visitor);
      }

      if (model.getGroup() != null) {
         model.getGroup().accept(m_visitor);
      }

      if (model.getArchive() != null) {
         model.getArchive().accept(m_visitor);
      }

      if (model.getGroupList() != null) {
         model.getGroupList().accept(m_visitor);
      }

      if (model.getMemberAction() != null) {
         model.getMemberAction().accept(m_visitor);
      }

      if (model.getServerAction() != null) {
         model.getServerAction().accept(m_visitor);
      }

      if (model.getGroupStatus() != null) {
         model.getGroupStatus().accept(m_visitor);
      }

      if (model.getGroupStatusList() != null) {
         model.getGroupStatusList().accept(m_visitor);
      }

      if (model.getServerStatus() != null) {
         model.getServerStatus().accept(m_visitor);
      }

      if (model.getConfReq() != null) {
         model.getConfReq().accept(m_visitor);
      }

      if (model.getOpServerStatusReq() != null) {
         model.getOpServerStatusReq().accept(m_visitor);
      }

      if (model.getOpMemberStatusReq() != null) {
         model.getOpMemberStatusReq().accept(m_visitor);
      }

      if (model.getNginxConfUpstreamData() != null) {
         model.getNginxConfUpstreamData().accept(m_visitor);
      }

      if (model.getNginxConfServerData() != null) {
         model.getNginxConfServerData().accept(m_visitor);
      }

      if (model.getDyUpstreamOpsData() != null) {
         model.getDyUpstreamOpsData().accept(m_visitor);
      }

      endTag(ENTITY_MODEL);
   }

   @Override
   public void visitNginxConfServerData(NginxConfServerData nginxConfServerData) {
      startTag(ENTITY_NGINX_CONF_SERVER_DATA, null);

      tagWithText(ELEMENT_VS_ID, nginxConfServerData.getVsId() == null ? null : String.valueOf(nginxConfServerData.getVsId()));

      element(ELEMENT_CONTENT, nginxConfServerData.getContent(), true);

      endTag(ENTITY_NGINX_CONF_SERVER_DATA);
   }

   @Override
   public void visitNginxConfUpstreamData(NginxConfUpstreamData nginxConfUpstreamData) {
      startTag(ENTITY_NGINX_CONF_UPSTREAM_DATA, null);

      tagWithText(ELEMENT_VS_ID, nginxConfUpstreamData.getVsId() == null ? null : String.valueOf(nginxConfUpstreamData.getVsId()));

      element(ELEMENT_CONTENT, nginxConfUpstreamData.getContent(), true);

      endTag(ENTITY_NGINX_CONF_UPSTREAM_DATA);
   }

   @Override
   public void visitOpMemberStatusReq(OpMemberStatusReq opMemberStatusReq) {
      startTag(ENTITY_OP_MEMBER_STATUS_REQ, null);

      element(ELEMENT_OPERATION, opMemberStatusReq.getOperation(), true);

      if (!opMemberStatusReq.getIpGroupnames().isEmpty()) {
         for (IpGroupname ipGroupname : opMemberStatusReq.getIpGroupnames().toArray(new IpGroupname[0])) {
            ipGroupname.accept(m_visitor);
         }
      }

      endTag(ENTITY_OP_MEMBER_STATUS_REQ);
   }

   @Override
   public void visitOpServerStatusReq(OpServerStatusReq opServerStatusReq) {
      startTag(ENTITY_OP_SERVER_STATUS_REQ, null);

      element(ELEMENT_OPERATION, opServerStatusReq.getOperation(), true);

      if (!opServerStatusReq.getIpAddresseses().isEmpty()) {
         for (IpAddresses ipAddresses : opServerStatusReq.getIpAddresseses().toArray(new IpAddresses[0])) {
            ipAddresses.accept(m_visitor);
         }
      }

      endTag(ENTITY_OP_SERVER_STATUS_REQ);
   }

   @Override
   public void visitServerAction(ServerAction serverAction) {
      startTag(ENTITY_SERVER_ACTION, null);

      element(ELEMENT_NAME, serverAction.getName(), true);

      if (!serverAction.getIps().isEmpty()) {
         for (String ip : serverAction.getIps().toArray(new String[0])) {
            tagWithText(ELEMENT_IP, ip);
         }
      }

      endTag(ENTITY_SERVER_ACTION);
   }

   @Override
   public void visitServerStatus(ServerStatus serverStatus) {
      startTag(ENTITY_SERVER_STATUS, null);

      element(ELEMENT_IP, serverStatus.getIp(), true);

      tagWithText(ELEMENT_UP, serverStatus.getUp() == null ? null : String.valueOf(serverStatus.getUp()));

      if (!serverStatus.getGroupNames().isEmpty()) {
         for (String groupName : serverStatus.getGroupNames().toArray(new String[0])) {
            tagWithText(ELEMENT_GROUP_NAME, groupName);
         }
      }

      endTag(ENTITY_SERVER_STATUS);
   }

   @Override
   public void visitSlb(Slb slb) {
      startTag(ENTITY_SLB, null, ATTR_ID, slb.getId(), ATTR_NAME, slb.getName(), ATTR_VERSION, slb.getVersion());

      element(ELEMENT_NGINX_BIN, slb.getNginxBin(), true);

      element(ELEMENT_NGINX_CONF, slb.getNginxConf(), true);

      tagWithText(ELEMENT_NGINX_WORKER_PROCESSES, slb.getNginxWorkerProcesses() == null ? null : String.valueOf(slb.getNginxWorkerProcesses()));

      element(ELEMENT_STATUS, slb.getStatus(), true);

      if (!slb.getVips().isEmpty()) {
         startTag(ENTITY_VIPS);

         for (Vip vip : slb.getVips().toArray(new Vip[0])) {
            vip.accept(m_visitor);
         }

         endTag(ENTITY_VIPS);
      }

      if (!slb.getSlbServers().isEmpty()) {
         startTag(ENTITY_SLB_SERVERS);

         for (SlbServer slbServer : slb.getSlbServers().toArray(new SlbServer[0])) {
            slbServer.accept(m_visitor);
         }

         endTag(ENTITY_SLB_SERVERS);
      }

      if (!slb.getVirtualServers().isEmpty()) {
         startTag(ENTITY_VIRTUAL_SERVERS);

         for (VirtualServer virtualServer : slb.getVirtualServers().toArray(new VirtualServer[0])) {
            virtualServer.accept(m_visitor);
         }

         endTag(ENTITY_VIRTUAL_SERVERS);
      }

      endTag(ENTITY_SLB);
   }

   @Override
   public void visitSlbList(SlbList slbList) {
      startTag(ENTITY_SLB_LIST, null);

      tagWithText(ELEMENT_TOTAL, slbList.getTotal() == null ? null : String.valueOf(slbList.getTotal()));

      if (!slbList.getSlbs().isEmpty()) {
         startTag(ENTITY_SLBS);

         for (Slb slb : slbList.getSlbs().toArray(new Slb[0])) {
            slb.accept(m_visitor);
         }

         endTag(ENTITY_SLBS);
      }

      endTag(ENTITY_SLB_LIST);
   }

   @Override
   public void visitSlbServer(SlbServer slbServer) {
      startTag(ENTITY_SLB_SERVER, true, null, ATTR_SLB_ID, slbServer.getSlbId(), ATTR_IP, slbServer.getIp(), ATTR_HOST_NAME, slbServer.getHostName(), ATTR_ENABLE, slbServer.getEnable());
   }

   @Override
   public void visitVip(Vip vip) {
      startTag(ENTITY_VIP, true, null, ATTR_IP, vip.getIp());
   }

   @Override
   public void visitVirtualServer(VirtualServer virtualServer) {
      startTag(ENTITY_VIRTUAL_SERVER, null, ATTR_NAME, virtualServer.getName(), ATTR_ID, virtualServer.getId(), ATTR_SSL, virtualServer.getSsl());

      element(ELEMENT_PORT, virtualServer.getPort(), true);

      if (!virtualServer.getDomains().isEmpty()) {
         startTag(ENTITY_DOMAINS);

         for (Domain domain : virtualServer.getDomains().toArray(new Domain[0])) {
            domain.accept(m_visitor);
         }

         endTag(ENTITY_DOMAINS);
      }

      endTag(ENTITY_VIRTUAL_SERVER);
   }
}
