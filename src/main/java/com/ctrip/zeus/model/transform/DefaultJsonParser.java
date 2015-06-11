package com.ctrip.zeus.model.transform;

import com.ctrip.zeus.model.IEntity;
import com.ctrip.zeus.model.entity.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static com.ctrip.zeus.model.Constants.*;

public class DefaultJsonParser {

   private DefaultLinker m_linker = new DefaultLinker(true);

   private Stack<String> m_tags = new Stack<String>();

   private Stack<Object> m_objs = new Stack<Object>();

   private List<Object> m_entities = new ArrayList<Object>();

   private Class<?> m_entityClass;

   private DefaultJsonParser(Class<?> entityClass) {
      m_entityClass = entityClass;
   }

   public static <T extends IEntity<T>> T parse(Class<T> entityClass, InputStream in) throws IOException {
      return parse(entityClass, new InputStreamReader(in, "utf-8"));
   }

   @SuppressWarnings("unchecked")
      public static <T extends IEntity<T>> T parse(Class<T> entityClass, Reader reader) throws IOException {
      DefaultJsonParser parser = new DefaultJsonParser(entityClass);

      parser.onArrayBegin();
      parser.parse(new JsonReader(reader));
      parser.onArrayEnd();

      if (parser.m_entities.isEmpty()) {
         return null;
      } else {
         return (T) parser.m_entities.get(0);
      }
   }

   public static <T extends IEntity<T>> T parse(Class<T> entityClass, String json) throws IOException {
      return parse(entityClass, new StringReader(json));
   }

   public static <T extends IEntity<T>> List<T> parseArray(Class<T> entityClass, InputStream in) throws Exception {
      return parseArray(entityClass, new InputStreamReader(in, "utf-8"));
   }

   @SuppressWarnings("unchecked")
   public static <T extends IEntity<T>> List<T> parseArray(Class<T> entityClass, Reader reader) throws Exception {
      DefaultJsonParser parser = new DefaultJsonParser(entityClass);

      parser.parse(new JsonReader(reader));
      return (List<T>) (List<?>) parser.m_entities;
   }

   public static <T extends IEntity<T>> List<T> parseArray(Class<T> entityClass, String json) throws Exception {
      return parseArray(entityClass, new StringReader(json));
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

   private Object createRootEntity() {
      try {
         Object entity = m_entityClass.newInstance();

         return entity;
      } catch (Exception e) {
         throw new RuntimeException(String.format("Unable to create entity(%s) instance!", m_entityClass.getName()), e);
      }
   }

   private boolean isTopLevel() {
      return m_objs.size() == 1;
   }

   protected void onArrayBegin() {
      if (m_objs.isEmpty()) {
         m_objs.push(m_entities);
         m_tags.push("");
      } else {
         Object parent = m_objs.peek();
         String tag = m_tags.peek();

         if (parent instanceof Slb) {
            if (ENTITY_VIPS.equals(tag)) {
               m_objs.push(parent);
            } else if (ENTITY_SLB_SERVERS.equals(tag)) {
               m_objs.push(parent);
            } else if (ENTITY_VIRTUAL_SERVERS.equals(tag)) {
               m_objs.push(parent);
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof VirtualServer) {
            if (ENTITY_DOMAINS.equals(tag)) {
               m_objs.push(parent);
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof SlbList) {
            if (ENTITY_SLBS.equals(tag)) {
               m_objs.push(parent);
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof Group) {
            if (ENTITY_GROUP_SLBS.equals(tag)) {
               m_objs.push(parent);
            } else if (ENTITY_GROUP_SERVERS.equals(tag)) {
               m_objs.push(parent);
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof GroupSlb) {
            if (ENTITY_SLB_VIPS.equals(tag)) {
               m_objs.push(parent);
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof GroupList) {
            if (ENTITY_GROUPS.equals(tag)) {
               m_objs.push(parent);
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof GroupStatus) {
            if (ENTITY_GROUP_SERVER_STATUSES.equals(tag)) {
               m_objs.push(parent);
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof GroupStatusList) {
            if (ENTITY_GROUP_STATUSES.equals(tag)) {
               m_objs.push(parent);
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof ConfReq) {
            if (ENTITY_CONF_SLB_NAMES.equals(tag)) {
               m_objs.push(parent);
            } else if (ENTITY_CONF_GROUP_NAMES.equals(tag)) {
               m_objs.push(parent);
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof OpServerStatusReq) {
            if (ENTITY_IP_ADDRESSESES.equals(tag)) {
               m_objs.push(parent);
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof OpMemberStatusReq) {
            if (ENTITY_IP_GROUPNAMES.equals(tag)) {
               m_objs.push(parent);
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else {
            throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
         }
      }   }

   protected void onArrayEnd() {
      m_objs.pop();
      m_tags.pop();

   }
   protected void onName(String name) {
      m_tags.push(name);
   }

   protected void onObjectBegin() {
      if (isTopLevel()) {
         m_objs.push(createRootEntity());
         m_tags.push("");
      } else {
         Object parent = m_objs.peek();
         String tag = m_tags.peek();

         if (parent instanceof Model) {
            if (ENTITY_SLB.equals(tag)) {
               Slb slb = new Slb();

               m_linker.onSlb((Model) parent, slb);
               m_objs.push(slb);
            } else if (ENTITY_SLB_LIST.equals(tag)) {
               SlbList slbList = new SlbList();

               m_linker.onSlbList((Model) parent, slbList);
               m_objs.push(slbList);
            } else if (ENTITY_VIP.equals(tag)) {
               Vip vip = new Vip();

               m_linker.onVip((Model) parent, vip);
               m_objs.push(vip);
            } else if (ENTITY_GROUP.equals(tag)) {
               Group group = new Group();

               m_linker.onGroup((Model) parent, group);
               m_objs.push(group);
            } else if (ENTITY_ARCHIVE.equals(tag)) {
               Archive archive = new Archive();

               m_linker.onArchive((Model) parent, archive);
               m_objs.push(archive);
            } else if (ENTITY_GROUP_LIST.equals(tag)) {
               GroupList groupList = new GroupList();

               m_linker.onGroupList((Model) parent, groupList);
               m_objs.push(groupList);
            } else if (ENTITY_MEMBER_ACTION.equals(tag)) {
               MemberAction memberAction = new MemberAction();

               m_linker.onMemberAction((Model) parent, memberAction);
               m_objs.push(memberAction);
            } else if (ENTITY_SERVER_ACTION.equals(tag)) {
               ServerAction serverAction = new ServerAction();

               m_linker.onServerAction((Model) parent, serverAction);
               m_objs.push(serverAction);
            } else if (ENTITY_GROUP_STATUS.equals(tag)) {
               GroupStatus groupStatus = new GroupStatus();

               m_linker.onGroupStatus((Model) parent, groupStatus);
               m_objs.push(groupStatus);
            } else if (ENTITY_GROUP_STATUS_LIST.equals(tag)) {
               GroupStatusList groupStatusList = new GroupStatusList();

               m_linker.onGroupStatusList((Model) parent, groupStatusList);
               m_objs.push(groupStatusList);
            } else if (ENTITY_SERVER_STATUS.equals(tag)) {
               ServerStatus serverStatus = new ServerStatus();

               m_linker.onServerStatus((Model) parent, serverStatus);
               m_objs.push(serverStatus);
            } else if (ENTITY_CONF_REQ.equals(tag)) {
               ConfReq confReq = new ConfReq();

               m_linker.onConfReq((Model) parent, confReq);
               m_objs.push(confReq);
            } else if (ENTITY_OP_SERVER_STATUS_REQ.equals(tag)) {
               OpServerStatusReq opServerStatusReq = new OpServerStatusReq();

               m_linker.onOpServerStatusReq((Model) parent, opServerStatusReq);
               m_objs.push(opServerStatusReq);
            } else if (ENTITY_OP_MEMBER_STATUS_REQ.equals(tag)) {
               OpMemberStatusReq opMemberStatusReq = new OpMemberStatusReq();

               m_linker.onOpMemberStatusReq((Model) parent, opMemberStatusReq);
               m_objs.push(opMemberStatusReq);
            } else if (ENTITY_NGINX_CONF_UPSTREAM_DATA.equals(tag)) {
               NginxConfUpstreamData nginxConfUpstreamData = new NginxConfUpstreamData();

               m_linker.onNginxConfUpstreamData((Model) parent, nginxConfUpstreamData);
               m_objs.push(nginxConfUpstreamData);
            } else if (ENTITY_NGINX_CONF_SERVER_DATA.equals(tag)) {
               NginxConfServerData nginxConfServerData = new NginxConfServerData();

               m_linker.onNginxConfServerData((Model) parent, nginxConfServerData);
               m_objs.push(nginxConfServerData);
            } else if (ENTITY_DY_UPSTREAM_OPS_DATA.equals(tag)) {
               DyUpstreamOpsData dyUpstreamOpsData = new DyUpstreamOpsData();

               m_linker.onDyUpstreamOpsData((Model) parent, dyUpstreamOpsData);
               m_objs.push(dyUpstreamOpsData);
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof Slb) {
            if (ENTITY_VIPS.equals(tag)) {
               Vip vips = new Vip();

               m_linker.onVip((Slb) parent, vips);
               m_objs.push(vips);
               m_tags.push("");
            } else if (ENTITY_SLB_SERVERS.equals(tag)) {
               SlbServer slbServers = new SlbServer();

               m_linker.onSlbServer((Slb) parent, slbServers);
               m_objs.push(slbServers);
               m_tags.push("");
            } else if (ENTITY_VIRTUAL_SERVERS.equals(tag)) {
               VirtualServer virtualServers = new VirtualServer();

               m_linker.onVirtualServer((Slb) parent, virtualServers);
               m_objs.push(virtualServers);
               m_tags.push("");
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof VirtualServer) {
            if (ENTITY_DOMAINS.equals(tag)) {
               Domain domains = new Domain();

               m_linker.onDomain((VirtualServer) parent, domains);
               m_objs.push(domains);
               m_tags.push("");
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof SlbList) {
            if (ENTITY_SLBS.equals(tag)) {
               Slb slbs = new Slb();

               m_linker.onSlb((SlbList) parent, slbs);
               m_objs.push(slbs);
               m_tags.push("");
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof Group) {
            if (ENTITY_GROUP_SLBS.equals(tag)) {
               GroupSlb groupSlbs = new GroupSlb();

               m_linker.onGroupSlb((Group) parent, groupSlbs);
               m_objs.push(groupSlbs);
               m_tags.push("");
            } else if (ENTITY_GROUP_SERVERS.equals(tag)) {
               GroupServer groupServers = new GroupServer();

               m_linker.onGroupServer((Group) parent, groupServers);
               m_objs.push(groupServers);
               m_tags.push("");
            } else if (ENTITY_HEALTH_CHECK.equals(tag)) {
               HealthCheck healthCheck = new HealthCheck();

               m_linker.onHealthCheck((Group) parent, healthCheck);
               m_objs.push(healthCheck);
            } else if (ENTITY_LOAD_BALANCING_METHOD.equals(tag)) {
               LoadBalancingMethod loadBalancingMethod = new LoadBalancingMethod();

               m_linker.onLoadBalancingMethod((Group) parent, loadBalancingMethod);
               m_objs.push(loadBalancingMethod);
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof GroupSlb) {
            if (ENTITY_SLB_VIPS.equals(tag)) {
               Vip slbVips = new Vip();

               m_linker.onVip((GroupSlb) parent, slbVips);
               m_objs.push(slbVips);
               m_tags.push("");
            } else if (ENTITY_VIRTUAL_SERVER.equals(tag)) {
               VirtualServer virtualServer = new VirtualServer();

               m_linker.onVirtualServer((GroupSlb) parent, virtualServer);
               m_objs.push(virtualServer);
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof GroupList) {
            if (ENTITY_GROUPS.equals(tag)) {
               Group groups = new Group();

               m_linker.onGroup((GroupList) parent, groups);
               m_objs.push(groups);
               m_tags.push("");
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof GroupStatus) {
            if (ENTITY_GROUP_SERVER_STATUSES.equals(tag)) {
               GroupServerStatus groupServerStatuses = new GroupServerStatus();

               m_linker.onGroupServerStatus((GroupStatus) parent, groupServerStatuses);
               m_objs.push(groupServerStatuses);
               m_tags.push("");
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags)); ///
            }
         } else if (parent instanceof GroupStatusList) {
            if (ENTITY_GROUP_STATUSES.equals(tag)) {
               GroupStatus groupStatuses = new GroupStatus();

               m_linker.onGroupStatus((GroupStatusList) parent, groupStatuses);
               m_objs.push(groupStatuses);
               m_tags.push("");
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
            }
         } else if (parent instanceof ConfReq) {
            if (ENTITY_CONF_SLB_NAMES.equals(tag)) {
               ConfSlbName confSlbNames = new ConfSlbName();

               m_linker.onConfSlbName((ConfReq) parent, confSlbNames);
               m_objs.push(confSlbNames);
               m_tags.push("");
            } else if (ENTITY_CONF_GROUP_NAMES.equals(tag)) {
               ConfGroupName confGroupNames = new ConfGroupName();

               m_linker.onConfGroupName((ConfReq) parent, confGroupNames);
               m_objs.push(confGroupNames);
               m_tags.push("");
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags)); ///
            }
         } else if (parent instanceof OpServerStatusReq) {
            if (ENTITY_IP_ADDRESSESES.equals(tag)) {
               IpAddresses ipAddresseses = new IpAddresses();

               m_linker.onIpAddresses((OpServerStatusReq) parent, ipAddresseses);
               m_objs.push(ipAddresseses);
               m_tags.push("");
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags)); ///
            }
         } else if (parent instanceof OpMemberStatusReq) {
            if (ENTITY_IP_GROUPNAMES.equals(tag)) {
               IpGroupname ipGroupnames = new IpGroupname();

               m_linker.onIpGroupname((OpMemberStatusReq) parent, ipGroupnames);
               m_objs.push(ipGroupnames);
               m_tags.push("");
            } else {
               throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags)); ///
            }
         } else {
            throw new RuntimeException(String.format("Unknown tag(%s) found at %s!", tag, m_tags));
         }
      }
   }

   protected void onObjectEnd() {
      m_tags.pop();

      Object entity = m_objs.pop();

      if (isTopLevel()) {
         m_entities.add(entity);
      }
   }

   protected void onValue(String value) {
      Object parent = m_objs.peek();
      String tag = m_tags.pop();

      if (parent instanceof Model) {
         parseForModel((Model) parent, tag, value);
      } else if (parent instanceof Slb) {
         parseForSlb((Slb) parent, tag, value);
      } else if (parent instanceof Vip) {
         parseForVip((Vip) parent, tag, value);
      } else if (parent instanceof SlbServer) {
         parseForSlbServer((SlbServer) parent, tag, value);
      } else if (parent instanceof VirtualServer) {
         parseForVirtualServer((VirtualServer) parent, tag, value);
      } else if (parent instanceof Domain) {
         parseForDomain((Domain) parent, tag, value);
      } else if (parent instanceof SlbList) {
         parseForSlbList((SlbList) parent, tag, value);
      } else if (parent instanceof Group) {
         parseForGroup((Group) parent, tag, value);
      } else if (parent instanceof GroupSlb) {
         parseForGroupSlb((GroupSlb) parent, tag, value);
      } else if (parent instanceof HealthCheck) {
         parseForHealthCheck((HealthCheck) parent, tag, value);
      } else if (parent instanceof LoadBalancingMethod) {
         parseForLoadBalancingMethod((LoadBalancingMethod) parent, tag, value);
      } else if (parent instanceof GroupServer) {
         parseForGroupServer((GroupServer) parent, tag, value);
      } else if (parent instanceof Archive) {
         parseForArchive((Archive) parent, tag, value);
      } else if (parent instanceof GroupList) {
         parseForGroupList((GroupList) parent, tag, value);
      } else if (parent instanceof MemberAction) {
         parseForMemberAction((MemberAction) parent, tag, value);
      } else if (parent instanceof ServerAction) {
         parseForServerAction((ServerAction) parent, tag, value);
      } else if (parent instanceof GroupStatus) {
         parseForGroupStatus((GroupStatus) parent, tag, value);
      } else if (parent instanceof GroupServerStatus) {
         parseForGroupServerStatus((GroupServerStatus) parent, tag, value);
      } else if (parent instanceof GroupStatusList) {
         parseForGroupStatusList((GroupStatusList) parent, tag, value);
      } else if (parent instanceof ServerStatus) {
         parseForServerStatus((ServerStatus) parent, tag, value);
      } else if (parent instanceof ConfReq) {
         parseForConfReq((ConfReq) parent, tag, value);
      } else if (parent instanceof ConfSlbName) {
         parseForConfSlbName((ConfSlbName) parent, tag, value);
      } else if (parent instanceof ConfGroupName) {
         parseForConfGroupName((ConfGroupName) parent, tag, value);
      } else if (parent instanceof OpServerStatusReq) {
         parseForOpServerStatusReq((OpServerStatusReq) parent, tag, value);
      } else if (parent instanceof IpAddresses) {
         parseForIpAddresses((IpAddresses) parent, tag, value);
      } else if (parent instanceof OpMemberStatusReq) {
         parseForOpMemberStatusReq((OpMemberStatusReq) parent, tag, value);
      } else if (parent instanceof IpGroupname) {
         parseForIpGroupname((IpGroupname) parent, tag, value);
      } else if (parent instanceof NginxConfUpstreamData) {
         parseForNginxConfUpstreamData((NginxConfUpstreamData) parent, tag, value);
      } else if (parent instanceof NginxConfServerData) {
         parseForNginxConfServerData((NginxConfServerData) parent, tag, value);
      } else if (parent instanceof DyUpstreamOpsData) {
         parseForDyUpstreamOpsData((DyUpstreamOpsData) parent, tag, value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) under %s!", tag, parent));
      }
   }

   private void parse(JsonReader reader) throws IOException {
      try {
         reader.parse(this);
      } catch (EOFException e) {
         if (m_objs.size() > 1) {
            throw new EOFException(String.format("Unexpected end while parsing json! tags: %s.", m_tags));
         }
      }

      m_linker.finish();
   }

   public void parseForArchive(Archive archive, String tag, String value) {
      if (ELEMENT_ID.equals(tag)) {
         archive.setId(convert(Long.class, value, null));
      } else if (ELEMENT_CONTENT.equals(tag)) {
         archive.setContent(value);
      } else if (ELEMENT_VERSION.equals(tag)) {
         archive.setVersion(convert(Integer.class, value, null));
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, archive, m_tags));
      }
   }

   public void parseForConfGroupName(ConfGroupName confGroupName, String tag, String value) {
      if (ATTR_GROUPNAME.equals(tag)) {
         confGroupName.setGroupname(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, confGroupName, m_tags));
      }
   }

   public void parseForConfReq(ConfReq confReq, String tag, String value) {
      if (ENTITY_CONF_SLB_NAMES.equals(tag) || ENTITY_CONF_GROUP_NAMES.equals(tag)) {
         // do nothing here
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, confReq, m_tags));
      }
   }

   public void parseForConfSlbName(ConfSlbName confSlbName, String tag, String value) {
      if (ATTR_SLBNAME.equals(tag)) {
         confSlbName.setSlbname(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, confSlbName, m_tags));
      }
   }

   public void parseForDomain(Domain domain, String tag, String value) {
      if (ATTR_NAME.equals(tag)) {
         domain.setName(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, domain, m_tags));
      }
   }

   public void parseForDyUpstreamOpsData(DyUpstreamOpsData dyUpstreamOpsData, String tag, String value) {
      if (ELEMENT_UPSTREAM_NAME.equals(tag)) {
         dyUpstreamOpsData.setUpstreamName(value);
      } else if (ELEMENT_UPSTREAM_COMMANDS.equals(tag)) {
         dyUpstreamOpsData.setUpstreamCommands(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, dyUpstreamOpsData, m_tags));
      }
   }

   public void parseForGroup(Group group, String tag, String value) {
      if (ENTITY_GROUP_SLBS.equals(tag) || ENTITY_HEALTH_CHECK.equals(tag) || ENTITY_LOAD_BALANCING_METHOD.equals(tag) || ENTITY_GROUP_SERVERS.equals(tag)) {
         // do nothing here
      } else if (ATTR_ID.equals(tag)) {
         group.setId(convert(Long.class, value, null));
      } else if (ATTR_NAME.equals(tag)) {
         group.setName(value);
      } else if (ATTR_APP_ID.equals(tag)) {
         group.setAppId(value);
      } else if (ATTR_VERSION.equals(tag)) {
         group.setVersion(convert(Integer.class, value, null));
      } else if (ATTR_SSL.equals(tag)) {
         group.setSsl(convert(Boolean.class, value, null));
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, group, m_tags));
      }
   }

   public void parseForGroupList(GroupList groupList, String tag, String value) {
      if (ENTITY_GROUPS.equals(tag)) {
         // do nothing here
      } else if (ELEMENT_TOTAL.equals(tag)) {
         groupList.setTotal(convert(Integer.class, value, null));
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, groupList, m_tags));
      }
   }

   public void parseForGroupServer(GroupServer groupServer, String tag, String value) {
      if (ATTR_PORT.equals(tag)) {
         groupServer.setPort(convert(Integer.class, value, null));
      } else if (ATTR_WEIGHT.equals(tag)) {
         groupServer.setWeight(convert(Integer.class, value, null));
      } else if (ATTR_MAX_FAILS.equals(tag)) {
         groupServer.setMaxFails(convert(Integer.class, value, null));
      } else if (ATTR_FAIL_TIMEOUT.equals(tag)) {
         groupServer.setFailTimeout(convert(Integer.class, value, null));
      } else if (ELEMENT_HOST_NAME.equals(tag)) {
         groupServer.setHostName(value);
      } else if (ELEMENT_IP.equals(tag)) {
         groupServer.setIp(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, groupServer, m_tags));
      }
   }

   public void parseForGroupServerStatus(GroupServerStatus groupServerStatus, String tag, String value) {
      if (ELEMENT_IP.equals(tag)) {
         groupServerStatus.setIp(value);
      } else if (ELEMENT_PORT.equals(tag)) {
         groupServerStatus.setPort(convert(Integer.class, value, null));
      } else if (ELEMENT_MEMBER.equals(tag)) {
         groupServerStatus.setMember(convert(Boolean.class, value, null));
      } else if (ELEMENT_SERVER.equals(tag)) {
         groupServerStatus.setServer(convert(Boolean.class, value, null));
      } else if (ELEMENT_UP.equals(tag)) {
         groupServerStatus.setUp(convert(Boolean.class, value, null));
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, groupServerStatus, m_tags));
      }
   }

   public void parseForGroupSlb(GroupSlb groupSlb, String tag, String value) {
      if (ENTITY_SLB_VIPS.equals(tag) || ENTITY_VIRTUAL_SERVER.equals(tag)) {
         // do nothing here
      } else if (ATTR_PRIORITY.equals(tag)) {
         groupSlb.setPriority(convert(Integer.class, value, null));
      } else if (ELEMENT_GROUP_ID.equals(tag)) {
         groupSlb.setGroupId(convert(Long.class, value, null));
      } else if (ELEMENT_SLB_ID.equals(tag)) {
         groupSlb.setSlbId(convert(Long.class, value, null));
      } else if (ELEMENT_SLB_NAME.equals(tag)) {
         groupSlb.setSlbName(value);
      } else if (ELEMENT_PATH.equals(tag)) {
         groupSlb.setPath(value);
      } else if (ELEMENT_REWRITE.equals(tag)) {
         groupSlb.setRewrite(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, groupSlb, m_tags));
      }
   }

   public void parseForGroupStatus(GroupStatus groupStatus, String tag, String value) {
      if (ENTITY_GROUP_SERVER_STATUSES.equals(tag)) {
         // do nothing here
      } else if (ELEMENT_GROUP_NAME.equals(tag)) {
         groupStatus.setGroupName(value);
      } else if (ELEMENT_SLB_NAME.equals(tag)) {
         groupStatus.setSlbName(value);
      } else if (ELEMENT_GROUP_ID.equals(tag)) {
         groupStatus.setGroupId(convert(Long.class, value, null));
      } else if (ELEMENT_SLB_ID.equals(tag)) {
         groupStatus.setSlbId(convert(Long.class, value, null));
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, groupStatus, m_tags));
      }
   }

   public void parseForGroupStatusList(GroupStatusList groupStatusList, String tag, String value) {
      if (ENTITY_GROUP_STATUSES.equals(tag)) {
         // do nothing here
      } else if (ELEMENT_TOTAL.equals(tag)) {
         groupStatusList.setTotal(convert(Integer.class, value, null));
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, groupStatusList, m_tags));
      }
   }

   public void parseForHealthCheck(HealthCheck healthCheck, String tag, String value) {
      if (ATTR_INTERVALS.equals(tag)) {
         healthCheck.setIntervals(convert(Integer.class, value, null));
      } else if (ATTR_FAILS.equals(tag)) {
         healthCheck.setFails(convert(Integer.class, value, null));
      } else if (ATTR_PASSES.equals(tag)) {
         healthCheck.setPasses(convert(Integer.class, value, null));
      } else if (ELEMENT_URI.equals(tag)) {
         healthCheck.setUri(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, healthCheck, m_tags));
      }
   }

   public void parseForIpAddresses(IpAddresses ipAddresses, String tag, String value) {
      if (ELEMENT_IP_ADDR.equals(tag)) {
         ipAddresses.setIpAddr(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, ipAddresses, m_tags));
      }
   }

   public void parseForIpGroupname(IpGroupname ipGroupname, String tag, String value) {
      if (ELEMENT_MEMBER_IP.equals(tag)) {
         ipGroupname.setMemberIp(value);
      } else if (ELEMENT_MEMBER_GROUPNAME.equals(tag)) {
         ipGroupname.setMemberGroupname(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, ipGroupname, m_tags));
      }
   }

   public void parseForLoadBalancingMethod(LoadBalancingMethod loadBalancingMethod, String tag, String value) {
      if (ATTR_TYPE.equals(tag)) {
         loadBalancingMethod.setType(value);
      } else if (ELEMENT_VALUE.equals(tag)) {
         loadBalancingMethod.setValue(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, loadBalancingMethod, m_tags));
      }
   }

   public void parseForMemberAction(MemberAction memberAction, String tag, String value) {
      if (ELEMENT_GROUP_NAME.equals(tag)) {
         memberAction.setGroupName(value);
      } else if (ELEMENT_IPS.equals(tag)) {
         memberAction.addIp(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, memberAction, m_tags));
      }
   }

   public void parseForModel(Model model, String tag, String value) {
      if (ENTITY_SLB.equals(tag) || ENTITY_SLB_LIST.equals(tag) || ENTITY_VIP.equals(tag) || ENTITY_GROUP.equals(tag) || ENTITY_ARCHIVE.equals(tag) || ENTITY_GROUP_LIST.equals(tag) || ENTITY_MEMBER_ACTION.equals(tag) || ENTITY_SERVER_ACTION.equals(tag) || ENTITY_GROUP_STATUS.equals(tag) || ENTITY_GROUP_STATUS_LIST.equals(tag) || ENTITY_SERVER_STATUS.equals(tag) || ENTITY_CONF_REQ.equals(tag) || ENTITY_OP_SERVER_STATUS_REQ.equals(tag) || ENTITY_OP_MEMBER_STATUS_REQ.equals(tag) || ENTITY_NGINX_CONF_UPSTREAM_DATA.equals(tag) || ENTITY_NGINX_CONF_SERVER_DATA.equals(tag) || ENTITY_DY_UPSTREAM_OPS_DATA.equals(tag)) {
         // do nothing here
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, model, m_tags));
      }
   }

   public void parseForNginxConfServerData(NginxConfServerData nginxConfServerData, String tag, String value) {
      if (ELEMENT_VS_ID.equals(tag)) {
         nginxConfServerData.setVsId(convert(Long.class, value, null));
      } else if (ELEMENT_CONTENT.equals(tag)) {
         nginxConfServerData.setContent(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, nginxConfServerData, m_tags));
      }
   }

   public void parseForNginxConfUpstreamData(NginxConfUpstreamData nginxConfUpstreamData, String tag, String value) {
      if (ELEMENT_VS_ID.equals(tag)) {
         nginxConfUpstreamData.setVsId(convert(Long.class, value, null));
      } else if (ELEMENT_CONTENT.equals(tag)) {
         nginxConfUpstreamData.setContent(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, nginxConfUpstreamData, m_tags));
      }
   }

   public void parseForOpMemberStatusReq(OpMemberStatusReq opMemberStatusReq, String tag, String value) {
      if (ENTITY_IP_GROUPNAMES.equals(tag)) {
         // do nothing here
      } else if (ELEMENT_OPERATION.equals(tag)) {
         opMemberStatusReq.setOperation(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, opMemberStatusReq, m_tags));
      }
   }

   public void parseForOpServerStatusReq(OpServerStatusReq opServerStatusReq, String tag, String value) {
      if (ENTITY_IP_ADDRESSESES.equals(tag)) {
         // do nothing here
      } else if (ELEMENT_OPERATION.equals(tag)) {
         opServerStatusReq.setOperation(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, opServerStatusReq, m_tags));
      }
   }

   public void parseForServerAction(ServerAction serverAction, String tag, String value) {
      if (ELEMENT_NAME.equals(tag)) {
         serverAction.setName(value);
      } else if (ELEMENT_IPS.equals(tag)) {
         serverAction.addIp(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, serverAction, m_tags));
      }
   }

   public void parseForServerStatus(ServerStatus serverStatus, String tag, String value) {
      if (ELEMENT_IP.equals(tag)) {
         serverStatus.setIp(value);
      } else if (ELEMENT_UP.equals(tag)) {
         serverStatus.setUp(convert(Boolean.class, value, null));
      } else if (ELEMENT_GROUP_NAMES.equals(tag)) {
         serverStatus.addGroupName(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, serverStatus, m_tags));
      }
   }

   public void parseForSlb(Slb slb, String tag, String value) {
      if (ENTITY_VIPS.equals(tag) || ENTITY_SLB_SERVERS.equals(tag) || ENTITY_VIRTUAL_SERVERS.equals(tag)) {
         // do nothing here
      } else if (ATTR_ID.equals(tag)) {
         slb.setId(convert(Long.class, value, null));
      } else if (ATTR_NAME.equals(tag)) {
         slb.setName(value);
      } else if (ATTR_VERSION.equals(tag)) {
         slb.setVersion(convert(Integer.class, value, null));
      } else if (ELEMENT_NGINX_BIN.equals(tag)) {
         slb.setNginxBin(value);
      } else if (ELEMENT_NGINX_CONF.equals(tag)) {
         slb.setNginxConf(value);
      } else if (ELEMENT_NGINX_WORKER_PROCESSES.equals(tag)) {
         slb.setNginxWorkerProcesses(convert(Integer.class, value, null));
      } else if (ELEMENT_STATUS.equals(tag)) {
         slb.setStatus(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, slb, m_tags));
      }
   }

   public void parseForSlbList(SlbList slbList, String tag, String value) {
      if (ENTITY_SLBS.equals(tag)) {
         // do nothing here
      } else if (ELEMENT_TOTAL.equals(tag)) {
         slbList.setTotal(convert(Integer.class, value, null));
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, slbList, m_tags));
      }
   }

   public void parseForSlbServer(SlbServer slbServer, String tag, String value) {
      if (ATTR_SLB_ID.equals(tag)) {
         slbServer.setSlbId(convert(Long.class, value, null));
      } else if (ATTR_IP.equals(tag)) {
         slbServer.setIp(value);
      } else if (ATTR_HOST_NAME.equals(tag)) {
         slbServer.setHostName(value);
      } else if (ATTR_ENABLE.equals(tag)) {
         slbServer.setEnable(convert(Boolean.class, value, null));
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, slbServer, m_tags));
      }
   }

   public void parseForVip(Vip vip, String tag, String value) {
      if (ATTR_IP.equals(tag)) {
         vip.setIp(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, vip, m_tags));
      }
   }

   public void parseForVirtualServer(VirtualServer virtualServer, String tag, String value) {
      if (ENTITY_DOMAINS.equals(tag)) {
         // do nothing here
      } else if (ATTR_NAME.equals(tag)) {
         virtualServer.setName(value);
      } else if (ATTR_ID.equals(tag)) {
         virtualServer.setId(convert(Long.class, value, null));
      } else if (ATTR_SSL.equals(tag)) {
         virtualServer.setSsl(convert(Boolean.class, value, null));
      } else if (ELEMENT_PORT.equals(tag)) {
         virtualServer.setPort(value);
      } else {
         throw new RuntimeException(String.format("Unknown tag(%s) of %s under %s!", tag, virtualServer, m_tags));
      }
   }


   static class JsonReader {
      private Reader m_reader;

      private char[] m_buffer = new char[2048];

      private int m_size;

      private int m_index;

      public JsonReader(Reader reader) {
         m_reader = reader;
      }

      private char next() throws IOException {
         if (m_index >= m_size) {
            m_size = m_reader.read(m_buffer);
            m_index = 0;

            if (m_size == -1) {
               throw new EOFException();
            }
         }

         return m_buffer[m_index++];
      }

      public void parse(DefaultJsonParser parser) throws IOException {
         StringBuilder sb = new StringBuilder();
         boolean flag = false;

         while (true) {
            char ch = next();

            switch (ch) {
            case ' ':
            case '\t':
            case '\r':
            case '\n':
               break;
            case '{':
               parser.onObjectBegin();
               flag = false;
               break;
            case '}':
               if (flag) { // have value
                  parser.onValue(sb.toString());
                  sb.setLength(0);
               }

               parser.onObjectEnd();
               flag = false;
               break;
            case '\'':
            case '"':
               while (true) {
                  char ch2 = next();

                  if (ch2 != ch) {
                     if (ch2 == '\\') {
                        char ch3 = next();

                        switch (ch3) {
                        case 't':
                        	sb.append('\t');
                        	break;
                        case 'r':
                        	sb.append('\r');
                        	break;
                        case 'n':
                        	sb.append('\n');
                        	break;
                        default:
                        	sb.append(ch3);
                        	break;
                        }
                     } else {
                        sb.append(ch2);
                     }
                  } else {
                     if (!flag) {
                        parser.onName(sb.toString());
                     } else {
                        parser.onValue(sb.toString());
                        flag = false;
                     }

                     sb.setLength(0);
                     break;
                  }
               }

               break;
            case ':':
               if (sb.length() != 0) {
                  parser.onName(sb.toString());
                  sb.setLength(0);
               }

               flag = true;
               break;
            case ',':
               if (sb.length() != 0) {
                  if (!flag) {
                     parser.onName(sb.toString());
                  } else {
                     parser.onValue(sb.toString());
                  }

                  sb.setLength(0);
               }

               flag = false;
               break;
            case '[':
               parser.onArrayBegin();
               flag = false;
               break;
            case ']':
               parser.onArrayEnd();
               flag = false;
               break;
            default:
               sb.append(ch);
               break;
            }
         }
      }
   }
}
