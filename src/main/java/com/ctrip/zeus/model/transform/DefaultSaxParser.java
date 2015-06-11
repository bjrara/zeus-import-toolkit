package com.ctrip.zeus.model.transform;

import com.ctrip.zeus.model.IEntity;
import com.ctrip.zeus.model.entity.*;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Stack;

import static com.ctrip.zeus.model.Constants.*;

public class DefaultSaxParser extends DefaultHandler {

   private DefaultLinker m_linker = new DefaultLinker(true);

   private DefaultSaxMaker m_maker = new DefaultSaxMaker();

   private Stack<String> m_tags = new Stack<String>();

   private Stack<Object> m_objs = new Stack<Object>();

   private IEntity<?> m_entity;

   private StringBuilder m_text = new StringBuilder();

   public static Model parse(InputSource is) throws SAXException, IOException {
      return parseEntity(Model.class, is);
   }

   public static Model parse(InputStream in) throws SAXException, IOException {
      return parse(new InputSource(in));
   }

   public static Model parse(Reader reader) throws SAXException, IOException {
      return parse(new InputSource(reader));
   }

   public static Model parse(String xml) throws SAXException, IOException {
      return parse(new InputSource(new StringReader(xml)));
   }

   public static <T extends IEntity<?>> T parseEntity(Class<T> type, String xml) throws SAXException, IOException {
      return parseEntity(type, new InputSource(new StringReader(xml)));
   }

   @SuppressWarnings("unchecked")
   public static <T extends IEntity<?>> T parseEntity(Class<T> type, InputSource is) throws SAXException, IOException {
      try {
         DefaultSaxParser handler = new DefaultSaxParser();
         SAXParserFactory factory = SAXParserFactory.newInstance();

         factory.setValidating(false);
         factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
         factory.setFeature("http://xml.org/sax/features/validation", false);

         factory.newSAXParser().parse(is, handler);
         return (T) handler.getEntity();
      } catch (ParserConfigurationException e) {
         throw new IllegalStateException("Unable to get SAX parser instance!", e);
      }
   }


   @SuppressWarnings("unchecked")
   protected <T> T convert(Class<T> type, String value, T defaultValue) {
      if (value == null || value.length() == 0) {
         return defaultValue;
      }

      if (type == Boolean.class) {
         return (T) Boolean.valueOf(value);
      } else if (type == Integer.class) {
         return (T) Integer.valueOf(value);
      } else if (type == Long.class) {
         return (T) Long.valueOf(value);
      } else if (type == Short.class) {
         return (T) Short.valueOf(value);
      } else if (type == Float.class) {
         return (T) Float.valueOf(value);
      } else if (type == Double.class) {
         return (T) Double.valueOf(value);
      } else if (type == Byte.class) {
         return (T) Byte.valueOf(value);
      } else if (type == Character.class) {
         return (T) (Character) value.charAt(0);
      } else {
         return (T) value;
      }
   }

   @Override
   public void characters(char[] ch, int start, int length) throws SAXException {
      m_text.append(ch, start, length);
   }

   @Override
   public void endDocument() throws SAXException {
      m_linker.finish();
   }

   @Override
   public void endElement(String uri, String localName, String qName) throws SAXException {
      if (uri == null || uri.length() == 0) {
         Object currentObj = m_objs.pop();
         String currentTag = m_tags.pop();

         if (currentObj instanceof Slb) {
            Slb slb = (Slb) currentObj;

            if (ELEMENT_NGINX_BIN.equals(currentTag)) {
               slb.setNginxBin(getText());
            } else if (ELEMENT_NGINX_CONF.equals(currentTag)) {
               slb.setNginxConf(getText());
            } else if (ELEMENT_NGINX_WORKER_PROCESSES.equals(currentTag)) {
               slb.setNginxWorkerProcesses(convert(Integer.class, getText(), null));
            } else if (ELEMENT_STATUS.equals(currentTag)) {
               slb.setStatus(getText());
            }
         } else if (currentObj instanceof VirtualServer) {
            VirtualServer virtualServer = (VirtualServer) currentObj;

            if (ELEMENT_PORT.equals(currentTag)) {
               virtualServer.setPort(getText());
            }
         } else if (currentObj instanceof SlbList) {
            SlbList slbList = (SlbList) currentObj;

            if (ELEMENT_TOTAL.equals(currentTag)) {
               slbList.setTotal(convert(Integer.class, getText(), null));
            }
         } else if (currentObj instanceof GroupSlb) {
            GroupSlb groupSlb = (GroupSlb) currentObj;

            if (ELEMENT_GROUP_ID.equals(currentTag)) {
               groupSlb.setGroupId(convert(Long.class, getText(), null));
            } else if (ELEMENT_SLB_ID.equals(currentTag)) {
               groupSlb.setSlbId(convert(Long.class, getText(), null));
            } else if (ELEMENT_SLB_NAME.equals(currentTag)) {
               groupSlb.setSlbName(getText());
            } else if (ELEMENT_PATH.equals(currentTag)) {
               groupSlb.setPath(getText());
            } else if (ELEMENT_REWRITE.equals(currentTag)) {
               groupSlb.setRewrite(getText());
            }
         } else if (currentObj instanceof HealthCheck) {
            HealthCheck healthCheck = (HealthCheck) currentObj;

            if (ELEMENT_URI.equals(currentTag)) {
               healthCheck.setUri(getText());
            }
         } else if (currentObj instanceof LoadBalancingMethod) {
            LoadBalancingMethod loadBalancingMethod = (LoadBalancingMethod) currentObj;

            if (ELEMENT_VALUE.equals(currentTag)) {
               loadBalancingMethod.setValue(getText());
            }
         } else if (currentObj instanceof GroupServer) {
            GroupServer groupServer = (GroupServer) currentObj;

            if (ELEMENT_HOST_NAME.equals(currentTag)) {
               groupServer.setHostName(getText());
            } else if (ELEMENT_IP.equals(currentTag)) {
               groupServer.setIp(getText());
            }
         } else if (currentObj instanceof Archive) {
            Archive archive = (Archive) currentObj;

            if (ELEMENT_ID.equals(currentTag)) {
               archive.setId(convert(Long.class, getText(), null));
            } else if (ELEMENT_CONTENT.equals(currentTag)) {
               archive.setContent(getText());
            } else if (ELEMENT_VERSION.equals(currentTag)) {
               archive.setVersion(convert(Integer.class, getText(), null));
            }
         } else if (currentObj instanceof GroupList) {
            GroupList groupList = (GroupList) currentObj;

            if (ELEMENT_TOTAL.equals(currentTag)) {
               groupList.setTotal(convert(Integer.class, getText(), null));
            }
         } else if (currentObj instanceof MemberAction) {
            MemberAction memberAction = (MemberAction) currentObj;

            if (ELEMENT_GROUP_NAME.equals(currentTag)) {
               memberAction.setGroupName(getText());
            } else if (ELEMENT_IP.equals(currentTag)) {
               memberAction.addIp(getText());
            }
         } else if (currentObj instanceof ServerAction) {
            ServerAction serverAction = (ServerAction) currentObj;

            if (ELEMENT_NAME.equals(currentTag)) {
               serverAction.setName(getText());
            } else if (ELEMENT_IP.equals(currentTag)) {
               serverAction.addIp(getText());
            }
         } else if (currentObj instanceof GroupStatus) {
            GroupStatus groupStatus = (GroupStatus) currentObj;

            if (ELEMENT_GROUP_NAME.equals(currentTag)) {
               groupStatus.setGroupName(getText());
            } else if (ELEMENT_SLB_NAME.equals(currentTag)) {
               groupStatus.setSlbName(getText());
            } else if (ELEMENT_GROUP_ID.equals(currentTag)) {
               groupStatus.setGroupId(convert(Long.class, getText(), null));
            } else if (ELEMENT_SLB_ID.equals(currentTag)) {
               groupStatus.setSlbId(convert(Long.class, getText(), null));
            }
         } else if (currentObj instanceof GroupServerStatus) {
            GroupServerStatus groupServerStatus = (GroupServerStatus) currentObj;

            if (ELEMENT_IP.equals(currentTag)) {
               groupServerStatus.setIp(getText());
            } else if (ELEMENT_PORT.equals(currentTag)) {
               groupServerStatus.setPort(convert(Integer.class, getText(), null));
            } else if (ELEMENT_MEMBER.equals(currentTag)) {
               groupServerStatus.setMember(convert(Boolean.class, getText(), null));
            } else if (ELEMENT_SERVER.equals(currentTag)) {
               groupServerStatus.setServer(convert(Boolean.class, getText(), null));
            } else if (ELEMENT_UP.equals(currentTag)) {
               groupServerStatus.setUp(convert(Boolean.class, getText(), null));
            }
         } else if (currentObj instanceof GroupStatusList) {
            GroupStatusList groupStatusList = (GroupStatusList) currentObj;

            if (ELEMENT_TOTAL.equals(currentTag)) {
               groupStatusList.setTotal(convert(Integer.class, getText(), null));
            }
         } else if (currentObj instanceof ServerStatus) {
            ServerStatus serverStatus = (ServerStatus) currentObj;

            if (ELEMENT_IP.equals(currentTag)) {
               serverStatus.setIp(getText());
            } else if (ELEMENT_UP.equals(currentTag)) {
               serverStatus.setUp(convert(Boolean.class, getText(), null));
            } else if (ELEMENT_GROUP_NAME.equals(currentTag)) {
               serverStatus.addGroupName(getText());
            }
         } else if (currentObj instanceof OpServerStatusReq) {
            OpServerStatusReq opServerStatusReq = (OpServerStatusReq) currentObj;

            if (ELEMENT_OPERATION.equals(currentTag)) {
               opServerStatusReq.setOperation(getText());
            }
         } else if (currentObj instanceof IpAddresses) {
            IpAddresses ipAddresses = (IpAddresses) currentObj;

            if (ELEMENT_IP_ADDR.equals(currentTag)) {
               ipAddresses.setIpAddr(getText());
            }
         } else if (currentObj instanceof OpMemberStatusReq) {
            OpMemberStatusReq opMemberStatusReq = (OpMemberStatusReq) currentObj;

            if (ELEMENT_OPERATION.equals(currentTag)) {
               opMemberStatusReq.setOperation(getText());
            }
         } else if (currentObj instanceof IpGroupname) {
            IpGroupname ipGroupname = (IpGroupname) currentObj;

            if (ELEMENT_MEMBER_IP.equals(currentTag)) {
               ipGroupname.setMemberIp(getText());
            } else if (ELEMENT_MEMBER_GROUPNAME.equals(currentTag)) {
               ipGroupname.setMemberGroupname(getText());
            }
         } else if (currentObj instanceof NginxConfUpstreamData) {
            NginxConfUpstreamData nginxConfUpstreamData = (NginxConfUpstreamData) currentObj;

            if (ELEMENT_VS_ID.equals(currentTag)) {
               nginxConfUpstreamData.setVsId(convert(Long.class, getText(), null));
            } else if (ELEMENT_CONTENT.equals(currentTag)) {
               nginxConfUpstreamData.setContent(getText());
            }
         } else if (currentObj instanceof NginxConfServerData) {
            NginxConfServerData nginxConfServerData = (NginxConfServerData) currentObj;

            if (ELEMENT_VS_ID.equals(currentTag)) {
               nginxConfServerData.setVsId(convert(Long.class, getText(), null));
            } else if (ELEMENT_CONTENT.equals(currentTag)) {
               nginxConfServerData.setContent(getText());
            }
         } else if (currentObj instanceof DyUpstreamOpsData) {
            DyUpstreamOpsData dyUpstreamOpsData = (DyUpstreamOpsData) currentObj;

            if (ELEMENT_UPSTREAM_NAME.equals(currentTag)) {
               dyUpstreamOpsData.setUpstreamName(getText());
            } else if (ELEMENT_UPSTREAM_COMMANDS.equals(currentTag)) {
               dyUpstreamOpsData.setUpstreamCommands(getText());
            }
         }
      }

      m_text.setLength(0);
   }

   private IEntity<?> getEntity() {
      return m_entity;
   }

   protected String getText() {
      return m_text.toString();
   }

   private void parseForArchive(Archive parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ELEMENT_ID.equals(qName) || ELEMENT_CONTENT.equals(qName) || ELEMENT_VERSION.equals(qName)) {
         m_objs.push(parentObj);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under archive!", qName));
      }

      m_tags.push(qName);
   }

   private void parseForConfGroupName(ConfGroupName parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      m_objs.push(parentObj);
      m_tags.push(qName);
   }

   private void parseForConfReq(ConfReq parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ENTITY_CONF_SLB_NAME.equals(qName)) {
         ConfSlbName confSlbName = m_maker.buildConfSlbName(attributes);

         m_linker.onConfSlbName(parentObj, confSlbName);
         m_objs.push(confSlbName);
      } else if (ENTITY_CONF_GROUP_NAME.equals(qName)) {
         ConfGroupName confGroupName = m_maker.buildConfGroupName(attributes);

         m_linker.onConfGroupName(parentObj, confGroupName);
         m_objs.push(confGroupName);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under conf-req!", qName));
      }

      m_tags.push(qName);
   }

   private void parseForConfSlbName(ConfSlbName parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      m_objs.push(parentObj);
      m_tags.push(qName);
   }

   private void parseForDomain(Domain parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      m_objs.push(parentObj);
      m_tags.push(qName);
   }

   private void parseForDyUpstreamOpsData(DyUpstreamOpsData parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ELEMENT_UPSTREAM_NAME.equals(qName) || ELEMENT_UPSTREAM_COMMANDS.equals(qName)) {
         m_objs.push(parentObj);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under dy-upstream-ops-data!", qName));
      }

      m_tags.push(qName);
   }

   private void parseForGroup(Group parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ENTITY_GROUP_SLBS.equals(qName) || ENTITY_GROUP_SERVERS.equals(qName)) {
         m_objs.push(parentObj);
      } else if (ENTITY_GROUP_SLB.equals(qName)) {
         GroupSlb groupSlb = m_maker.buildGroupSlb(attributes);

         m_linker.onGroupSlb(parentObj, groupSlb);
         m_objs.push(groupSlb);
      } else if (ENTITY_HEALTH_CHECK.equals(qName)) {
         HealthCheck healthCheck = m_maker.buildHealthCheck(attributes);

         m_linker.onHealthCheck(parentObj, healthCheck);
         m_objs.push(healthCheck);
      } else if (ENTITY_LOAD_BALANCING_METHOD.equals(qName)) {
         LoadBalancingMethod loadBalancingMethod = m_maker.buildLoadBalancingMethod(attributes);

         m_linker.onLoadBalancingMethod(parentObj, loadBalancingMethod);
         m_objs.push(loadBalancingMethod);
      } else if (ENTITY_GROUP_SERVER.equals(qName)) {
         GroupServer groupServer = m_maker.buildGroupServer(attributes);

         m_linker.onGroupServer(parentObj, groupServer);
         m_objs.push(groupServer);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under group!", qName));
      }

      m_tags.push(qName);
   }

   private void parseForGroupList(GroupList parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ELEMENT_TOTAL.equals(qName) || ENTITY_GROUPS.equals(qName)) {
         m_objs.push(parentObj);
      } else if (ENTITY_GROUP.equals(qName)) {
         Group group = m_maker.buildGroup(attributes);

         m_linker.onGroup(parentObj, group);
         m_objs.push(group);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under group-list!", qName));
      }

      m_tags.push(qName);
   }

   private void parseForGroupServer(GroupServer parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ELEMENT_HOST_NAME.equals(qName) || ELEMENT_IP.equals(qName)) {
         m_objs.push(parentObj);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under group-server!", qName));
      }

      m_tags.push(qName);
   }

   private void parseForGroupServerStatus(GroupServerStatus parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ELEMENT_IP.equals(qName) || ELEMENT_PORT.equals(qName) || ELEMENT_MEMBER.equals(qName) || ELEMENT_SERVER.equals(qName) || ELEMENT_UP.equals(qName)) {
         m_objs.push(parentObj);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under group-server-status!", qName));
      }

      m_tags.push(qName);
   }

   private void parseForGroupSlb(GroupSlb parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ELEMENT_GROUP_ID.equals(qName) || ELEMENT_SLB_ID.equals(qName) || ELEMENT_SLB_NAME.equals(qName) || ELEMENT_PATH.equals(qName) || ELEMENT_REWRITE.equals(qName) || ENTITY_SLB_VIPS.equals(qName)) {
         m_objs.push(parentObj);
      } else if (ENTITY_VIP.equals(qName)) {
         Vip vip = m_maker.buildVip(attributes);

         m_linker.onVip(parentObj, vip);
         m_objs.push(vip);
      } else if (ENTITY_VIRTUAL_SERVER.equals(qName)) {
         VirtualServer virtualServer = m_maker.buildVirtualServer(attributes);

         m_linker.onVirtualServer(parentObj, virtualServer);
         m_objs.push(virtualServer);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under group-slb!", qName));
      }

      m_tags.push(qName);
   }

   private void parseForGroupStatus(GroupStatus parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ELEMENT_GROUP_NAME.equals(qName) || ELEMENT_SLB_NAME.equals(qName) || ELEMENT_GROUP_ID.equals(qName) || ELEMENT_SLB_ID.equals(qName)) {
         m_objs.push(parentObj);
      } else if (ENTITY_GROUP_SERVER_STATUS.equals(qName)) {
         GroupServerStatus groupServerStatus = m_maker.buildGroupServerStatus(attributes);

         m_linker.onGroupServerStatus(parentObj, groupServerStatus);
         m_objs.push(groupServerStatus);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under group-status!", qName));
      }

      m_tags.push(qName);
   }

   private void parseForGroupStatusList(GroupStatusList parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ELEMENT_TOTAL.equals(qName) || ENTITY_GROUP_STATUSES.equals(qName)) {
         m_objs.push(parentObj);
      } else if (ENTITY_GROUP_STATUS.equals(qName)) {
         GroupStatus groupStatus = m_maker.buildGroupStatus(attributes);

         m_linker.onGroupStatus(parentObj, groupStatus);
         m_objs.push(groupStatus);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under group-status-list!", qName));
      }

      m_tags.push(qName);
   }

   private void parseForHealthCheck(HealthCheck parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ELEMENT_URI.equals(qName)) {
         m_objs.push(parentObj);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under health-check!", qName));
      }

      m_tags.push(qName);
   }

   private void parseForIpAddresses(IpAddresses parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ELEMENT_IP_ADDR.equals(qName)) {
         m_objs.push(parentObj);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under ip-addresses!", qName));
      }

      m_tags.push(qName);
   }

   private void parseForIpGroupname(IpGroupname parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ELEMENT_MEMBER_IP.equals(qName) || ELEMENT_MEMBER_GROUPNAME.equals(qName)) {
         m_objs.push(parentObj);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under ip-groupname!", qName));
      }

      m_tags.push(qName);
   }

   private void parseForLoadBalancingMethod(LoadBalancingMethod parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ELEMENT_VALUE.equals(qName)) {
         m_objs.push(parentObj);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under load-balancing-method!", qName));
      }

      m_tags.push(qName);
   }

   private void parseForMemberAction(MemberAction parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ELEMENT_GROUP_NAME.equals(qName) || ELEMENT_IPS.equals(qName) || ELEMENT_IP.equals(qName)) {
         m_objs.push(parentObj);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under member-action!", qName));
      }

      m_tags.push(qName);
   }

   private void parseForModel(Model parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ENTITY_SLB.equals(qName)) {
         Slb slb = m_maker.buildSlb(attributes);

         m_linker.onSlb(parentObj, slb);
         m_objs.push(slb);
      } else if (ENTITY_SLB_LIST.equals(qName)) {
         SlbList slbList = m_maker.buildSlbList(attributes);

         m_linker.onSlbList(parentObj, slbList);
         m_objs.push(slbList);
      } else if (ENTITY_VIP.equals(qName)) {
         Vip vip = m_maker.buildVip(attributes);

         m_linker.onVip(parentObj, vip);
         m_objs.push(vip);
      } else if (ENTITY_GROUP.equals(qName)) {
         Group group = m_maker.buildGroup(attributes);

         m_linker.onGroup(parentObj, group);
         m_objs.push(group);
      } else if (ENTITY_ARCHIVE.equals(qName)) {
         Archive archive = m_maker.buildArchive(attributes);

         m_linker.onArchive(parentObj, archive);
         m_objs.push(archive);
      } else if (ENTITY_GROUP_LIST.equals(qName)) {
         GroupList groupList = m_maker.buildGroupList(attributes);

         m_linker.onGroupList(parentObj, groupList);
         m_objs.push(groupList);
      } else if (ENTITY_MEMBER_ACTION.equals(qName)) {
         MemberAction memberAction = m_maker.buildMemberAction(attributes);

         m_linker.onMemberAction(parentObj, memberAction);
         m_objs.push(memberAction);
      } else if (ENTITY_SERVER_ACTION.equals(qName)) {
         ServerAction serverAction = m_maker.buildServerAction(attributes);

         m_linker.onServerAction(parentObj, serverAction);
         m_objs.push(serverAction);
      } else if (ENTITY_GROUP_STATUS.equals(qName)) {
         GroupStatus groupStatus = m_maker.buildGroupStatus(attributes);

         m_linker.onGroupStatus(parentObj, groupStatus);
         m_objs.push(groupStatus);
      } else if (ENTITY_GROUP_STATUS_LIST.equals(qName)) {
         GroupStatusList groupStatusList = m_maker.buildGroupStatusList(attributes);

         m_linker.onGroupStatusList(parentObj, groupStatusList);
         m_objs.push(groupStatusList);
      } else if (ENTITY_SERVER_STATUS.equals(qName)) {
         ServerStatus serverStatus = m_maker.buildServerStatus(attributes);

         m_linker.onServerStatus(parentObj, serverStatus);
         m_objs.push(serverStatus);
      } else if (ENTITY_CONF_REQ.equals(qName)) {
         ConfReq confReq = m_maker.buildConfReq(attributes);

         m_linker.onConfReq(parentObj, confReq);
         m_objs.push(confReq);
      } else if (ENTITY_OP_SERVER_STATUS_REQ.equals(qName)) {
         OpServerStatusReq opServerStatusReq = m_maker.buildOpServerStatusReq(attributes);

         m_linker.onOpServerStatusReq(parentObj, opServerStatusReq);
         m_objs.push(opServerStatusReq);
      } else if (ENTITY_OP_MEMBER_STATUS_REQ.equals(qName)) {
         OpMemberStatusReq opMemberStatusReq = m_maker.buildOpMemberStatusReq(attributes);

         m_linker.onOpMemberStatusReq(parentObj, opMemberStatusReq);
         m_objs.push(opMemberStatusReq);
      } else if (ENTITY_NGINX_CONF_UPSTREAM_DATA.equals(qName)) {
         NginxConfUpstreamData nginxConfUpstreamData = m_maker.buildNginxConfUpstreamData(attributes);

         m_linker.onNginxConfUpstreamData(parentObj, nginxConfUpstreamData);
         m_objs.push(nginxConfUpstreamData);
      } else if (ENTITY_NGINX_CONF_SERVER_DATA.equals(qName)) {
         NginxConfServerData nginxConfServerData = m_maker.buildNginxConfServerData(attributes);

         m_linker.onNginxConfServerData(parentObj, nginxConfServerData);
         m_objs.push(nginxConfServerData);
      } else if (ENTITY_DY_UPSTREAM_OPS_DATA.equals(qName)) {
         DyUpstreamOpsData dyUpstreamOpsData = m_maker.buildDyUpstreamOpsData(attributes);

         m_linker.onDyUpstreamOpsData(parentObj, dyUpstreamOpsData);
         m_objs.push(dyUpstreamOpsData);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under model!", qName));
      }

      m_tags.push(qName);
   }

   private void parseForNginxConfServerData(NginxConfServerData parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ELEMENT_VS_ID.equals(qName) || ELEMENT_CONTENT.equals(qName)) {
         m_objs.push(parentObj);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under nginx-conf-server-data!", qName));
      }

      m_tags.push(qName);
   }

   private void parseForNginxConfUpstreamData(NginxConfUpstreamData parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ELEMENT_VS_ID.equals(qName) || ELEMENT_CONTENT.equals(qName)) {
         m_objs.push(parentObj);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under nginx-conf-upstream-data!", qName));
      }

      m_tags.push(qName);
   }

   private void parseForOpMemberStatusReq(OpMemberStatusReq parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ELEMENT_OPERATION.equals(qName)) {
         m_objs.push(parentObj);
      } else if (ENTITY_IP_GROUPNAME.equals(qName)) {
         IpGroupname ipGroupname = m_maker.buildIpGroupname(attributes);

         m_linker.onIpGroupname(parentObj, ipGroupname);
         m_objs.push(ipGroupname);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under op-member-status-req!", qName));
      }

      m_tags.push(qName);
   }

   private void parseForOpServerStatusReq(OpServerStatusReq parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ELEMENT_OPERATION.equals(qName)) {
         m_objs.push(parentObj);
      } else if (ENTITY_IP_ADDRESSES.equals(qName)) {
         IpAddresses ipAddresses = m_maker.buildIpAddresses(attributes);

         m_linker.onIpAddresses(parentObj, ipAddresses);
         m_objs.push(ipAddresses);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under op-server-status-req!", qName));
      }

      m_tags.push(qName);
   }

   private void parseForServerAction(ServerAction parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ELEMENT_NAME.equals(qName) || ELEMENT_IPS.equals(qName) || ELEMENT_IP.equals(qName)) {
         m_objs.push(parentObj);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under server-action!", qName));
      }

      m_tags.push(qName);
   }

   private void parseForServerStatus(ServerStatus parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ELEMENT_IP.equals(qName) || ELEMENT_UP.equals(qName) || ELEMENT_GROUP_NAMES.equals(qName) || ELEMENT_GROUP_NAME.equals(qName)) {
         m_objs.push(parentObj);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under server-status!", qName));
      }

      m_tags.push(qName);
   }

   private void parseForSlb(Slb parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ELEMENT_NGINX_BIN.equals(qName) || ELEMENT_NGINX_CONF.equals(qName) || ELEMENT_NGINX_WORKER_PROCESSES.equals(qName) || ELEMENT_STATUS.equals(qName) || ENTITY_VIPS.equals(qName) || ENTITY_SLB_SERVERS.equals(qName) || ENTITY_VIRTUAL_SERVERS.equals(qName)) {
         m_objs.push(parentObj);
      } else if (ENTITY_VIP.equals(qName)) {
         Vip vip = m_maker.buildVip(attributes);

         m_linker.onVip(parentObj, vip);
         m_objs.push(vip);
      } else if (ENTITY_SLB_SERVER.equals(qName)) {
         SlbServer slbServer = m_maker.buildSlbServer(attributes);

         m_linker.onSlbServer(parentObj, slbServer);
         m_objs.push(slbServer);
      } else if (ENTITY_VIRTUAL_SERVER.equals(qName)) {
         VirtualServer virtualServer = m_maker.buildVirtualServer(attributes);

         m_linker.onVirtualServer(parentObj, virtualServer);
         m_objs.push(virtualServer);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under slb!", qName));
      }

      m_tags.push(qName);
   }

   private void parseForSlbList(SlbList parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ELEMENT_TOTAL.equals(qName) || ENTITY_SLBS.equals(qName)) {
         m_objs.push(parentObj);
      } else if (ENTITY_SLB.equals(qName)) {
         Slb slb = m_maker.buildSlb(attributes);

         m_linker.onSlb(parentObj, slb);
         m_objs.push(slb);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under slb-list!", qName));
      }

      m_tags.push(qName);
   }

   private void parseForSlbServer(SlbServer parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      m_objs.push(parentObj);
      m_tags.push(qName);
   }

   private void parseForVip(Vip parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      m_objs.push(parentObj);
      m_tags.push(qName);
   }

   private void parseForVirtualServer(VirtualServer parentObj, String parentTag, String qName, Attributes attributes) throws SAXException {
      if (ELEMENT_PORT.equals(qName) || ENTITY_DOMAINS.equals(qName)) {
         m_objs.push(parentObj);
      } else if (ENTITY_DOMAIN.equals(qName)) {
         Domain domain = m_maker.buildDomain(attributes);

         m_linker.onDomain(parentObj, domain);
         m_objs.push(domain);
      } else {
         throw new SAXException(String.format("Element(%s) is not expected under virtual-server!", qName));
      }

      m_tags.push(qName);
   }

   private void parseRoot(String qName, Attributes attributes) throws SAXException {
      if (ENTITY_MODEL.equals(qName)) {
         Model model = m_maker.buildModel(attributes);

         m_entity = model;
         m_objs.push(model);
         m_tags.push(qName);
      } else if (ENTITY_SLB.equals(qName)) {
         Slb slb = m_maker.buildSlb(attributes);

         m_entity = slb;
         m_objs.push(slb);
         m_tags.push(qName);
      } else if (ENTITY_VIP.equals(qName)) {
         Vip vip = m_maker.buildVip(attributes);

         m_entity = vip;
         m_objs.push(vip);
         m_tags.push(qName);
      } else if (ENTITY_SLB_SERVER.equals(qName)) {
         SlbServer slbServer = m_maker.buildSlbServer(attributes);

         m_entity = slbServer;
         m_objs.push(slbServer);
         m_tags.push(qName);
      } else if (ENTITY_VIRTUAL_SERVER.equals(qName)) {
         VirtualServer virtualServer = m_maker.buildVirtualServer(attributes);

         m_entity = virtualServer;
         m_objs.push(virtualServer);
         m_tags.push(qName);
      } else if (ENTITY_DOMAIN.equals(qName)) {
         Domain domain = m_maker.buildDomain(attributes);

         m_entity = domain;
         m_objs.push(domain);
         m_tags.push(qName);
      } else if (ENTITY_SLB_LIST.equals(qName)) {
         SlbList slbList = m_maker.buildSlbList(attributes);

         m_entity = slbList;
         m_objs.push(slbList);
         m_tags.push(qName);
      } else if (ENTITY_GROUP.equals(qName)) {
         Group group = m_maker.buildGroup(attributes);

         m_entity = group;
         m_objs.push(group);
         m_tags.push(qName);
      } else if (ENTITY_GROUP_SLB.equals(qName)) {
         GroupSlb groupSlb = m_maker.buildGroupSlb(attributes);

         m_entity = groupSlb;
         m_objs.push(groupSlb);
         m_tags.push(qName);
      } else if (ENTITY_HEALTH_CHECK.equals(qName)) {
         HealthCheck healthCheck = m_maker.buildHealthCheck(attributes);

         m_entity = healthCheck;
         m_objs.push(healthCheck);
         m_tags.push(qName);
      } else if (ENTITY_LOAD_BALANCING_METHOD.equals(qName)) {
         LoadBalancingMethod loadBalancingMethod = m_maker.buildLoadBalancingMethod(attributes);

         m_entity = loadBalancingMethod;
         m_objs.push(loadBalancingMethod);
         m_tags.push(qName);
      } else if (ENTITY_GROUP_SERVER.equals(qName)) {
         GroupServer groupServer = m_maker.buildGroupServer(attributes);

         m_entity = groupServer;
         m_objs.push(groupServer);
         m_tags.push(qName);
      } else if (ENTITY_ARCHIVE.equals(qName)) {
         Archive archive = m_maker.buildArchive(attributes);

         m_entity = archive;
         m_objs.push(archive);
         m_tags.push(qName);
      } else if (ENTITY_GROUP_LIST.equals(qName)) {
         GroupList groupList = m_maker.buildGroupList(attributes);

         m_entity = groupList;
         m_objs.push(groupList);
         m_tags.push(qName);
      } else if (ENTITY_MEMBER_ACTION.equals(qName)) {
         MemberAction memberAction = m_maker.buildMemberAction(attributes);

         m_entity = memberAction;
         m_objs.push(memberAction);
         m_tags.push(qName);
      } else if (ENTITY_SERVER_ACTION.equals(qName)) {
         ServerAction serverAction = m_maker.buildServerAction(attributes);

         m_entity = serverAction;
         m_objs.push(serverAction);
         m_tags.push(qName);
      } else if (ENTITY_GROUP_STATUS.equals(qName)) {
         GroupStatus groupStatus = m_maker.buildGroupStatus(attributes);

         m_entity = groupStatus;
         m_objs.push(groupStatus);
         m_tags.push(qName);
      } else if (ENTITY_GROUP_SERVER_STATUS.equals(qName)) {
         GroupServerStatus groupServerStatus = m_maker.buildGroupServerStatus(attributes);

         m_entity = groupServerStatus;
         m_objs.push(groupServerStatus);
         m_tags.push(qName);
      } else if (ENTITY_GROUP_STATUS_LIST.equals(qName)) {
         GroupStatusList groupStatusList = m_maker.buildGroupStatusList(attributes);

         m_entity = groupStatusList;
         m_objs.push(groupStatusList);
         m_tags.push(qName);
      } else if (ENTITY_SERVER_STATUS.equals(qName)) {
         ServerStatus serverStatus = m_maker.buildServerStatus(attributes);

         m_entity = serverStatus;
         m_objs.push(serverStatus);
         m_tags.push(qName);
      } else if (ENTITY_CONF_REQ.equals(qName)) {
         ConfReq confReq = m_maker.buildConfReq(attributes);

         m_entity = confReq;
         m_objs.push(confReq);
         m_tags.push(qName);
      } else if (ENTITY_CONF_SLB_NAME.equals(qName)) {
         ConfSlbName confSlbName = m_maker.buildConfSlbName(attributes);

         m_entity = confSlbName;
         m_objs.push(confSlbName);
         m_tags.push(qName);
      } else if (ENTITY_CONF_GROUP_NAME.equals(qName)) {
         ConfGroupName confGroupName = m_maker.buildConfGroupName(attributes);

         m_entity = confGroupName;
         m_objs.push(confGroupName);
         m_tags.push(qName);
      } else if (ENTITY_OP_SERVER_STATUS_REQ.equals(qName)) {
         OpServerStatusReq opServerStatusReq = m_maker.buildOpServerStatusReq(attributes);

         m_entity = opServerStatusReq;
         m_objs.push(opServerStatusReq);
         m_tags.push(qName);
      } else if (ENTITY_IP_ADDRESSES.equals(qName)) {
         IpAddresses ipAddresses = m_maker.buildIpAddresses(attributes);

         m_entity = ipAddresses;
         m_objs.push(ipAddresses);
         m_tags.push(qName);
      } else if (ENTITY_OP_MEMBER_STATUS_REQ.equals(qName)) {
         OpMemberStatusReq opMemberStatusReq = m_maker.buildOpMemberStatusReq(attributes);

         m_entity = opMemberStatusReq;
         m_objs.push(opMemberStatusReq);
         m_tags.push(qName);
      } else if (ENTITY_IP_GROUPNAME.equals(qName)) {
         IpGroupname ipGroupname = m_maker.buildIpGroupname(attributes);

         m_entity = ipGroupname;
         m_objs.push(ipGroupname);
         m_tags.push(qName);
      } else if (ENTITY_NGINX_CONF_UPSTREAM_DATA.equals(qName)) {
         NginxConfUpstreamData nginxConfUpstreamData = m_maker.buildNginxConfUpstreamData(attributes);

         m_entity = nginxConfUpstreamData;
         m_objs.push(nginxConfUpstreamData);
         m_tags.push(qName);
      } else if (ENTITY_NGINX_CONF_SERVER_DATA.equals(qName)) {
         NginxConfServerData nginxConfServerData = m_maker.buildNginxConfServerData(attributes);

         m_entity = nginxConfServerData;
         m_objs.push(nginxConfServerData);
         m_tags.push(qName);
      } else if (ENTITY_DY_UPSTREAM_OPS_DATA.equals(qName)) {
         DyUpstreamOpsData dyUpstreamOpsData = m_maker.buildDyUpstreamOpsData(attributes);

         m_entity = dyUpstreamOpsData;
         m_objs.push(dyUpstreamOpsData);
         m_tags.push(qName);
      } else {
         throw new SAXException("Unknown root element(" + qName + ") found!");
      }
   }

   @Override
   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      if (uri == null || uri.length() == 0) {
         if (m_objs.isEmpty()) { // root
            parseRoot(qName, attributes);
         } else {
            Object parent = m_objs.peek();
            String tag = m_tags.peek();

            if (parent instanceof Model) {
               parseForModel((Model) parent, tag, qName, attributes);
            } else if (parent instanceof Slb) {
               parseForSlb((Slb) parent, tag, qName, attributes);
            } else if (parent instanceof Vip) {
               parseForVip((Vip) parent, tag, qName, attributes);
            } else if (parent instanceof SlbServer) {
               parseForSlbServer((SlbServer) parent, tag, qName, attributes);
            } else if (parent instanceof VirtualServer) {
               parseForVirtualServer((VirtualServer) parent, tag, qName, attributes);
            } else if (parent instanceof Domain) {
               parseForDomain((Domain) parent, tag, qName, attributes);
            } else if (parent instanceof SlbList) {
               parseForSlbList((SlbList) parent, tag, qName, attributes);
            } else if (parent instanceof Group) {
               parseForGroup((Group) parent, tag, qName, attributes);
            } else if (parent instanceof GroupSlb) {
               parseForGroupSlb((GroupSlb) parent, tag, qName, attributes);
            } else if (parent instanceof HealthCheck) {
               parseForHealthCheck((HealthCheck) parent, tag, qName, attributes);
            } else if (parent instanceof LoadBalancingMethod) {
               parseForLoadBalancingMethod((LoadBalancingMethod) parent, tag, qName, attributes);
            } else if (parent instanceof GroupServer) {
               parseForGroupServer((GroupServer) parent, tag, qName, attributes);
            } else if (parent instanceof Archive) {
               parseForArchive((Archive) parent, tag, qName, attributes);
            } else if (parent instanceof GroupList) {
               parseForGroupList((GroupList) parent, tag, qName, attributes);
            } else if (parent instanceof MemberAction) {
               parseForMemberAction((MemberAction) parent, tag, qName, attributes);
            } else if (parent instanceof ServerAction) {
               parseForServerAction((ServerAction) parent, tag, qName, attributes);
            } else if (parent instanceof GroupStatus) {
               parseForGroupStatus((GroupStatus) parent, tag, qName, attributes);
            } else if (parent instanceof GroupServerStatus) {
               parseForGroupServerStatus((GroupServerStatus) parent, tag, qName, attributes);
            } else if (parent instanceof GroupStatusList) {
               parseForGroupStatusList((GroupStatusList) parent, tag, qName, attributes);
            } else if (parent instanceof ServerStatus) {
               parseForServerStatus((ServerStatus) parent, tag, qName, attributes);
            } else if (parent instanceof ConfReq) {
               parseForConfReq((ConfReq) parent, tag, qName, attributes);
            } else if (parent instanceof ConfSlbName) {
               parseForConfSlbName((ConfSlbName) parent, tag, qName, attributes);
            } else if (parent instanceof ConfGroupName) {
               parseForConfGroupName((ConfGroupName) parent, tag, qName, attributes);
            } else if (parent instanceof OpServerStatusReq) {
               parseForOpServerStatusReq((OpServerStatusReq) parent, tag, qName, attributes);
            } else if (parent instanceof IpAddresses) {
               parseForIpAddresses((IpAddresses) parent, tag, qName, attributes);
            } else if (parent instanceof OpMemberStatusReq) {
               parseForOpMemberStatusReq((OpMemberStatusReq) parent, tag, qName, attributes);
            } else if (parent instanceof IpGroupname) {
               parseForIpGroupname((IpGroupname) parent, tag, qName, attributes);
            } else if (parent instanceof NginxConfUpstreamData) {
               parseForNginxConfUpstreamData((NginxConfUpstreamData) parent, tag, qName, attributes);
            } else if (parent instanceof NginxConfServerData) {
               parseForNginxConfServerData((NginxConfServerData) parent, tag, qName, attributes);
            } else if (parent instanceof DyUpstreamOpsData) {
               parseForDyUpstreamOpsData((DyUpstreamOpsData) parent, tag, qName, attributes);
            } else {
               throw new RuntimeException(String.format("Unknown entity(%s) under %s!", qName, parent.getClass().getName()));
            }
         }

         m_text.setLength(0);
        } else {
         throw new SAXException(String.format("Namespace(%s) is not supported by %s.", uri, this.getClass().getName()));
      }
   }
}
