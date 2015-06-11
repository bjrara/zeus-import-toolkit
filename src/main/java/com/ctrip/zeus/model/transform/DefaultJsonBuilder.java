package com.ctrip.zeus.model.transform;

import com.ctrip.zeus.model.IEntity;
import com.ctrip.zeus.model.IVisitor;
import com.ctrip.zeus.model.entity.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.ctrip.zeus.model.Constants.*;

public class DefaultJsonBuilder implements IVisitor {

   private IVisitor m_visitor;

   private int m_level;

   private StringBuilder m_sb;

   private boolean m_compact;

   public DefaultJsonBuilder() {
      this(false);
   }

   public DefaultJsonBuilder(boolean compact) {
      this(compact, new StringBuilder(4096));
   }

   public DefaultJsonBuilder(boolean compact, StringBuilder sb) {
      m_compact = compact;
      m_sb = sb;
      m_visitor = this;
   }

   protected void arrayBegin(String name) {
      indent();
      m_sb.append('"').append(name).append(m_compact ? "\":[" : "\": [\r\n");
      m_level++;
   }

   protected void arrayEnd(String name) {
      m_level--;

      trimComma();
      indent();
      m_sb.append("],").append(m_compact ? "" : "\r\n");
   }

   protected void attributes(Map<String, String> dynamicAttributes, Object... nameValues) {
      int len = nameValues.length;

      for (int i = 0; i + 1 < len; i += 2) {
         Object attrName = nameValues[i];
         Object attrValue = nameValues[i + 1];

         if (attrValue != null) {
            if (attrValue instanceof List) {
               @SuppressWarnings("unchecked")
               List<Object> list = (List<Object>) attrValue;

               if (!list.isEmpty()) {
                  indent();
                  m_sb.append('"').append(attrName).append(m_compact ? "\":[" : "\": [");

                  for (Object item : list) {
                     m_sb.append(' ');
                     toString(m_sb, item);
                     m_sb.append(',');
                  }

                  m_sb.setLength(m_sb.length() - 1);
                  m_sb.append(m_compact ? "]," : " ],\r\n");
               }
            } else {
               if (m_compact) {
                  m_sb.append('"').append(attrName).append("\":");
                  toString(m_sb, attrValue);
                  m_sb.append(",");
               } else {
                  indent();
                  m_sb.append('"').append(attrName).append("\": ");
                  toString(m_sb, attrValue);
                  m_sb.append(",\r\n");
               }
            }
         }
      }

      if (dynamicAttributes != null) {
         for (Map.Entry<String, String> e : dynamicAttributes.entrySet()) {
            if (m_compact) {
               m_sb.append('"').append(e.getKey()).append("\":");
               toString(m_sb, e.getValue());
               m_sb.append(",");
            } else {
               indent();
               m_sb.append('"').append(e.getKey()).append("\": ");
               toString(m_sb, e.getValue());
               m_sb.append(",\r\n");
            }
         }
      }
   }

   public String build(IEntity<?> entity) {
      objectBegin(null);
      entity.accept(this);
      objectEnd(null);
      trimComma();

      return m_sb.toString();
   }

   public String buildArray(Collection<? extends IEntity<?>> entities) {
      m_sb.append('[');

      if (entities != null) {
         for (IEntity<?> entity : entities) {
            objectBegin(null);
            entity.accept(this);
            objectEnd(null);
         }

         trimComma();
      }

      m_sb.append(']');

      return m_sb.toString();
   }

   protected void indent() {
      if (!m_compact) {
         for (int i = m_level - 1; i >= 0; i--) {
            m_sb.append("   ");
         }
      }
   }

   protected void objectBegin(String name) {
      indent();

      if (name == null) {
         m_sb.append("{").append(m_compact ? "" : "\r\n");
      } else {
         m_sb.append('"').append(name).append(m_compact ? "\":{" : "\": {\r\n");
      }

      m_level++;
   }

   protected void objectEnd(String name) {
      m_level--;

      trimComma();
      indent();
      m_sb.append(m_compact ? "}," : "},\r\n");
   }

   protected void toString(StringBuilder sb, Object value) {
      if (value == null) {
         sb.append("null");
      } else if (value instanceof Boolean || value instanceof Number) {
         sb.append(value);
      } else {
         String val = value.toString();
         int len = val.length();

         sb.append('"');

         for (int i = 0; i < len; i++) {
            char ch = val.charAt(i);

            switch (ch) {
            case '\\':
            case '/':
            case '"':
               sb.append('\\').append(ch);
               break;
            case '\t':
               sb.append("\\t");
               break;
            case '\r':
               sb.append("\\r");
               break;
            case '\n':
               sb.append("\\n");
               break;
            default:
               sb.append(ch);
               break;
            }
         }

         sb.append('"');
      }
   }

   protected void trimComma() {
      int len = m_sb.length();

      if (m_compact) {
         if (len > 1 && m_sb.charAt(len - 1) == ',') {
            m_sb.replace(len - 1, len, "");
         }
      } else {
         if (len > 3 && m_sb.charAt(len - 3) == ',') {
            m_sb.replace(len - 3, len - 2, "");
         }
      }
   }

   @Override
   public void visitArchive(Archive archive) {
      attributes(null, ELEMENT_ID, archive.getId(), ELEMENT_CONTENT, archive.getContent(), ELEMENT_VERSION, archive.getVersion());
   }

   @Override
   public void visitConfGroupName(ConfGroupName confGroupName) {
      attributes(null, ATTR_GROUPNAME, confGroupName.getGroupname());
   }

   @Override
   public void visitConfReq(ConfReq confReq) {

      if (!confReq.getConfSlbNames().isEmpty()) {
         arrayBegin(ENTITY_CONF_SLB_NAMES);

         for (ConfSlbName confSlbName : confReq.getConfSlbNames()) {
            objectBegin(null);
            confSlbName.accept(m_visitor);
            objectEnd(null);
         }

         arrayEnd(ENTITY_CONF_SLB_NAMES);
      }

      if (!confReq.getConfGroupNames().isEmpty()) {
         arrayBegin(ENTITY_CONF_GROUP_NAMES);

         for (ConfGroupName confGroupName : confReq.getConfGroupNames()) {
            objectBegin(null);
            confGroupName.accept(m_visitor);
            objectEnd(null);
         }

         arrayEnd(ENTITY_CONF_GROUP_NAMES);
      }
   }

   @Override
   public void visitConfSlbName(ConfSlbName confSlbName) {
      attributes(null, ATTR_SLBNAME, confSlbName.getSlbname());
   }

   @Override
   public void visitDomain(Domain domain) {
      attributes(null, ATTR_NAME, domain.getName());
   }

   @Override
   public void visitDyUpstreamOpsData(DyUpstreamOpsData dyUpstreamOpsData) {
      attributes(null, ELEMENT_UPSTREAM_NAME, dyUpstreamOpsData.getUpstreamName(), ELEMENT_UPSTREAM_COMMANDS, dyUpstreamOpsData.getUpstreamCommands());
   }

   @Override
   public void visitGroup(Group group) {
      attributes(null, ATTR_ID, group.getId(), ATTR_NAME, group.getName(), ATTR_APP_ID, group.getAppId(), ATTR_VERSION, group.getVersion(), ATTR_SSL, group.getSsl());

      if (!group.getGroupSlbs().isEmpty()) {
         arrayBegin(ENTITY_GROUP_SLBS);

         for (GroupSlb groupSlb : group.getGroupSlbs()) {
            objectBegin(null);
            groupSlb.accept(m_visitor);
            objectEnd(null);
         }

         arrayEnd(ENTITY_GROUP_SLBS);
      }

      if (group.getHealthCheck() != null) {
         objectBegin(ENTITY_HEALTH_CHECK);
         group.getHealthCheck().accept(m_visitor);
         objectEnd(ENTITY_HEALTH_CHECK);
      }

      if (group.getLoadBalancingMethod() != null) {
         objectBegin(ENTITY_LOAD_BALANCING_METHOD);
         group.getLoadBalancingMethod().accept(m_visitor);
         objectEnd(ENTITY_LOAD_BALANCING_METHOD);
      }

      if (!group.getGroupServers().isEmpty()) {
         arrayBegin(ENTITY_GROUP_SERVERS);

         for (GroupServer groupServer : group.getGroupServers()) {
            objectBegin(null);
            groupServer.accept(m_visitor);
            objectEnd(null);
         }

         arrayEnd(ENTITY_GROUP_SERVERS);
      }
   }

   @Override
   public void visitGroupList(GroupList groupList) {
      attributes(null, ELEMENT_TOTAL, groupList.getTotal());

      if (!groupList.getGroups().isEmpty()) {
         arrayBegin(ENTITY_GROUPS);

         for (Group group : groupList.getGroups()) {
            objectBegin(null);
            group.accept(m_visitor);
            objectEnd(null);
         }

         arrayEnd(ENTITY_GROUPS);
      }
   }

   @Override
   public void visitGroupServer(GroupServer groupServer) {
      attributes(null, ATTR_PORT, groupServer.getPort(), ATTR_WEIGHT, groupServer.getWeight(), ATTR_MAX_FAILS, groupServer.getMaxFails(), ATTR_FAIL_TIMEOUT, groupServer.getFailTimeout(), ELEMENT_HOST_NAME, groupServer.getHostName(), ELEMENT_IP, groupServer.getIp());
   }

   @Override
   public void visitGroupServerStatus(GroupServerStatus groupServerStatus) {
      attributes(null, ELEMENT_IP, groupServerStatus.getIp(), ELEMENT_PORT, groupServerStatus.getPort(), ELEMENT_MEMBER, groupServerStatus.getMember(), ELEMENT_SERVER, groupServerStatus.getServer(), ELEMENT_UP, groupServerStatus.getUp());
   }

   @Override
   public void visitGroupSlb(GroupSlb groupSlb) {
      attributes(null, ATTR_PRIORITY, groupSlb.getPriority(), ELEMENT_GROUP_ID, groupSlb.getGroupId(), ELEMENT_SLB_ID, groupSlb.getSlbId(), ELEMENT_SLB_NAME, groupSlb.getSlbName(), ELEMENT_PATH, groupSlb.getPath(), ELEMENT_REWRITE, groupSlb.getRewrite());

      if (!groupSlb.getSlbVips().isEmpty()) {
         arrayBegin(ENTITY_SLB_VIPS);

         for (Vip vip : groupSlb.getSlbVips()) {
            objectBegin(null);
            vip.accept(m_visitor);
            objectEnd(null);
         }

         arrayEnd(ENTITY_SLB_VIPS);
      }

      if (groupSlb.getVirtualServer() != null) {
         objectBegin(ENTITY_VIRTUAL_SERVER);
         groupSlb.getVirtualServer().accept(m_visitor);
         objectEnd(ENTITY_VIRTUAL_SERVER);
      }
   }

   @Override
   public void visitGroupStatus(GroupStatus groupStatus) {
      attributes(null, ELEMENT_GROUP_NAME, groupStatus.getGroupName(), ELEMENT_SLB_NAME, groupStatus.getSlbName(), ELEMENT_GROUP_ID, groupStatus.getGroupId(), ELEMENT_SLB_ID, groupStatus.getSlbId());

      if (!groupStatus.getGroupServerStatuses().isEmpty()) {
         arrayBegin(ENTITY_GROUP_SERVER_STATUSES);

         for (GroupServerStatus groupServerStatus : groupStatus.getGroupServerStatuses()) {
            objectBegin(null);
            groupServerStatus.accept(m_visitor);
            objectEnd(null);
         }

         arrayEnd(ENTITY_GROUP_SERVER_STATUSES);
      }
   }

   @Override
   public void visitGroupStatusList(GroupStatusList groupStatusList) {
      attributes(null, ELEMENT_TOTAL, groupStatusList.getTotal());

      if (!groupStatusList.getGroupStatuses().isEmpty()) {
         arrayBegin(ENTITY_GROUP_STATUSES);

         for (GroupStatus groupStatus : groupStatusList.getGroupStatuses()) {
            objectBegin(null);
            groupStatus.accept(m_visitor);
            objectEnd(null);
         }

         arrayEnd(ENTITY_GROUP_STATUSES);
      }
   }

   @Override
   public void visitHealthCheck(HealthCheck healthCheck) {
      attributes(null, ATTR_INTERVALS, healthCheck.getIntervals(), ATTR_FAILS, healthCheck.getFails(), ATTR_PASSES, healthCheck.getPasses(), ELEMENT_URI, healthCheck.getUri());
   }

   @Override
   public void visitIpAddresses(IpAddresses ipAddresses) {
      attributes(null, ELEMENT_IP_ADDR, ipAddresses.getIpAddr());
   }

   @Override
   public void visitIpGroupname(IpGroupname ipGroupname) {
      attributes(null, ELEMENT_MEMBER_IP, ipGroupname.getMemberIp(), ELEMENT_MEMBER_GROUPNAME, ipGroupname.getMemberGroupname());
   }

   @Override
   public void visitLoadBalancingMethod(LoadBalancingMethod loadBalancingMethod) {
      attributes(null, ATTR_TYPE, loadBalancingMethod.getType(), ELEMENT_VALUE, loadBalancingMethod.getValue());
   }

   @Override
   public void visitMemberAction(MemberAction memberAction) {
      attributes(null, ELEMENT_GROUP_NAME, memberAction.getGroupName());

      if (!memberAction.getIps().isEmpty()) {
         arrayBegin(ELEMENT_IPS);

         for (String ip : memberAction.getIps()) {
            indent();
            m_sb.append('"').append(ip).append(m_compact ? "\"," : "\",\r\n");
         }

         arrayEnd(ELEMENT_IPS);
      }
   }

   @Override
   public void visitModel(Model model) {

      if (model.getSlb() != null) {
         objectBegin(ENTITY_SLB);
         model.getSlb().accept(m_visitor);
         objectEnd(ENTITY_SLB);
      }

      if (model.getSlbList() != null) {
         objectBegin(ENTITY_SLB_LIST);
         model.getSlbList().accept(m_visitor);
         objectEnd(ENTITY_SLB_LIST);
      }

      if (model.getVip() != null) {
         objectBegin(ENTITY_VIP);
         model.getVip().accept(m_visitor);
         objectEnd(ENTITY_VIP);
      }

      if (model.getGroup() != null) {
         objectBegin(ENTITY_GROUP);
         model.getGroup().accept(m_visitor);
         objectEnd(ENTITY_GROUP);
      }

      if (model.getArchive() != null) {
         objectBegin(ENTITY_ARCHIVE);
         model.getArchive().accept(m_visitor);
         objectEnd(ENTITY_ARCHIVE);
      }

      if (model.getGroupList() != null) {
         objectBegin(ENTITY_GROUP_LIST);
         model.getGroupList().accept(m_visitor);
         objectEnd(ENTITY_GROUP_LIST);
      }

      if (model.getMemberAction() != null) {
         objectBegin(ENTITY_MEMBER_ACTION);
         model.getMemberAction().accept(m_visitor);
         objectEnd(ENTITY_MEMBER_ACTION);
      }

      if (model.getServerAction() != null) {
         objectBegin(ENTITY_SERVER_ACTION);
         model.getServerAction().accept(m_visitor);
         objectEnd(ENTITY_SERVER_ACTION);
      }

      if (model.getGroupStatus() != null) {
         objectBegin(ENTITY_GROUP_STATUS);
         model.getGroupStatus().accept(m_visitor);
         objectEnd(ENTITY_GROUP_STATUS);
      }

      if (model.getGroupStatusList() != null) {
         objectBegin(ENTITY_GROUP_STATUS_LIST);
         model.getGroupStatusList().accept(m_visitor);
         objectEnd(ENTITY_GROUP_STATUS_LIST);
      }

      if (model.getServerStatus() != null) {
         objectBegin(ENTITY_SERVER_STATUS);
         model.getServerStatus().accept(m_visitor);
         objectEnd(ENTITY_SERVER_STATUS);
      }

      if (model.getConfReq() != null) {
         objectBegin(ENTITY_CONF_REQ);
         model.getConfReq().accept(m_visitor);
         objectEnd(ENTITY_CONF_REQ);
      }

      if (model.getOpServerStatusReq() != null) {
         objectBegin(ENTITY_OP_SERVER_STATUS_REQ);
         model.getOpServerStatusReq().accept(m_visitor);
         objectEnd(ENTITY_OP_SERVER_STATUS_REQ);
      }

      if (model.getOpMemberStatusReq() != null) {
         objectBegin(ENTITY_OP_MEMBER_STATUS_REQ);
         model.getOpMemberStatusReq().accept(m_visitor);
         objectEnd(ENTITY_OP_MEMBER_STATUS_REQ);
      }

      if (model.getNginxConfUpstreamData() != null) {
         objectBegin(ENTITY_NGINX_CONF_UPSTREAM_DATA);
         model.getNginxConfUpstreamData().accept(m_visitor);
         objectEnd(ENTITY_NGINX_CONF_UPSTREAM_DATA);
      }

      if (model.getNginxConfServerData() != null) {
         objectBegin(ENTITY_NGINX_CONF_SERVER_DATA);
         model.getNginxConfServerData().accept(m_visitor);
         objectEnd(ENTITY_NGINX_CONF_SERVER_DATA);
      }

      if (model.getDyUpstreamOpsData() != null) {
         objectBegin(ENTITY_DY_UPSTREAM_OPS_DATA);
         model.getDyUpstreamOpsData().accept(m_visitor);
         objectEnd(ENTITY_DY_UPSTREAM_OPS_DATA);
      }
   }

   @Override
   public void visitNginxConfServerData(NginxConfServerData nginxConfServerData) {
      attributes(null, ELEMENT_VS_ID, nginxConfServerData.getVsId(), ELEMENT_CONTENT, nginxConfServerData.getContent());
   }

   @Override
   public void visitNginxConfUpstreamData(NginxConfUpstreamData nginxConfUpstreamData) {
      attributes(null, ELEMENT_VS_ID, nginxConfUpstreamData.getVsId(), ELEMENT_CONTENT, nginxConfUpstreamData.getContent());
   }

   @Override
   public void visitOpMemberStatusReq(OpMemberStatusReq opMemberStatusReq) {
      attributes(null, ELEMENT_OPERATION, opMemberStatusReq.getOperation());

      if (!opMemberStatusReq.getIpGroupnames().isEmpty()) {
         arrayBegin(ENTITY_IP_GROUPNAMES);

         for (IpGroupname ipGroupname : opMemberStatusReq.getIpGroupnames()) {
            objectBegin(null);
            ipGroupname.accept(m_visitor);
            objectEnd(null);
         }

         arrayEnd(ENTITY_IP_GROUPNAMES);
      }
   }

   @Override
   public void visitOpServerStatusReq(OpServerStatusReq opServerStatusReq) {
      attributes(null, ELEMENT_OPERATION, opServerStatusReq.getOperation());

      if (!opServerStatusReq.getIpAddresseses().isEmpty()) {
         arrayBegin(ENTITY_IP_ADDRESSESES);

         for (IpAddresses ipAddresses : opServerStatusReq.getIpAddresseses()) {
            objectBegin(null);
            ipAddresses.accept(m_visitor);
            objectEnd(null);
         }

         arrayEnd(ENTITY_IP_ADDRESSESES);
      }
   }

   @Override
   public void visitServerAction(ServerAction serverAction) {
      attributes(null, ELEMENT_NAME, serverAction.getName());

      if (!serverAction.getIps().isEmpty()) {
         arrayBegin(ELEMENT_IPS);

         for (String ip : serverAction.getIps()) {
            indent();
            m_sb.append('"').append(ip).append(m_compact ? "\"," : "\",\r\n");
         }

         arrayEnd(ELEMENT_IPS);
      }
   }

   @Override
   public void visitServerStatus(ServerStatus serverStatus) {
      attributes(null, ELEMENT_IP, serverStatus.getIp(), ELEMENT_UP, serverStatus.getUp());

      if (!serverStatus.getGroupNames().isEmpty()) {
         arrayBegin(ELEMENT_GROUP_NAMES);

         for (String groupName : serverStatus.getGroupNames()) {
            indent();
            m_sb.append('"').append(groupName).append(m_compact ? "\"," : "\",\r\n");
         }

         arrayEnd(ELEMENT_GROUP_NAMES);
      }
   }

   @Override
   public void visitSlb(Slb slb) {
      attributes(null, ATTR_ID, slb.getId(), ATTR_NAME, slb.getName(), ATTR_VERSION, slb.getVersion(), ELEMENT_NGINX_BIN, slb.getNginxBin(), ELEMENT_NGINX_CONF, slb.getNginxConf(), ELEMENT_NGINX_WORKER_PROCESSES, slb.getNginxWorkerProcesses(), ELEMENT_STATUS, slb.getStatus());

      if (!slb.getVips().isEmpty()) {
         arrayBegin(ENTITY_VIPS);

         for (Vip vip : slb.getVips()) {
            objectBegin(null);
            vip.accept(m_visitor);
            objectEnd(null);
         }

         arrayEnd(ENTITY_VIPS);
      }

      if (!slb.getSlbServers().isEmpty()) {
         arrayBegin(ENTITY_SLB_SERVERS);

         for (SlbServer slbServer : slb.getSlbServers()) {
            objectBegin(null);
            slbServer.accept(m_visitor);
            objectEnd(null);
         }

         arrayEnd(ENTITY_SLB_SERVERS);
      }

      if (!slb.getVirtualServers().isEmpty()) {
         arrayBegin(ENTITY_VIRTUAL_SERVERS);

         for (VirtualServer virtualServer : slb.getVirtualServers()) {
            objectBegin(null);
            virtualServer.accept(m_visitor);
            objectEnd(null);
         }

         arrayEnd(ENTITY_VIRTUAL_SERVERS);
      }
   }

   @Override
   public void visitSlbList(SlbList slbList) {
      attributes(null, ELEMENT_TOTAL, slbList.getTotal());

      if (!slbList.getSlbs().isEmpty()) {
         arrayBegin(ENTITY_SLBS);

         for (Slb slb : slbList.getSlbs()) {
            objectBegin(null);
            slb.accept(m_visitor);
            objectEnd(null);
         }

         arrayEnd(ENTITY_SLBS);
      }
   }

   @Override
   public void visitSlbServer(SlbServer slbServer) {
      attributes(null, ATTR_SLB_ID, slbServer.getSlbId(), ATTR_IP, slbServer.getIp(), ATTR_HOST_NAME, slbServer.getHostName(), ATTR_ENABLE, slbServer.getEnable());
   }

   @Override
   public void visitVip(Vip vip) {
      attributes(null, ATTR_IP, vip.getIp());
   }

   @Override
   public void visitVirtualServer(VirtualServer virtualServer) {
      attributes(null, ATTR_NAME, virtualServer.getName(), ATTR_ID, virtualServer.getId(), ATTR_SSL, virtualServer.getSsl(), ELEMENT_PORT, virtualServer.getPort());

      if (!virtualServer.getDomains().isEmpty()) {
         arrayBegin(ENTITY_DOMAINS);

         for (Domain domain : virtualServer.getDomains()) {
            objectBegin(null);
            domain.accept(m_visitor);
            objectEnd(null);
         }

         arrayEnd(ENTITY_DOMAINS);
      }
   }
}
