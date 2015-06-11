package com.ctrip.zeus.model.transform;

import com.ctrip.zeus.model.entity.*;
import org.xml.sax.Attributes;

import static com.ctrip.zeus.model.Constants.*;

public class DefaultSaxMaker implements IMaker<Attributes> {

   @Override
   public Archive buildArchive(Attributes attributes) {
      Archive archive = new Archive();

      return archive;
   }

   @Override
   public ConfGroupName buildConfGroupName(Attributes attributes) {
      String groupname = attributes.getValue(ATTR_GROUPNAME);
      ConfGroupName confGroupName = new ConfGroupName();

      if (groupname != null) {
         confGroupName.setGroupname(groupname);
      }

      return confGroupName;
   }

   @Override
   public ConfReq buildConfReq(Attributes attributes) {
      ConfReq confReq = new ConfReq();

      return confReq;
   }

   @Override
   public ConfSlbName buildConfSlbName(Attributes attributes) {
      String slbname = attributes.getValue(ATTR_SLBNAME);
      ConfSlbName confSlbName = new ConfSlbName();

      if (slbname != null) {
         confSlbName.setSlbname(slbname);
      }

      return confSlbName;
   }

   @Override
   public Domain buildDomain(Attributes attributes) {
      String name = attributes.getValue(ATTR_NAME);
      Domain domain = new Domain();

      if (name != null) {
         domain.setName(name);
      }

      return domain;
   }

   @Override
   public DyUpstreamOpsData buildDyUpstreamOpsData(Attributes attributes) {
      DyUpstreamOpsData dyUpstreamOpsData = new DyUpstreamOpsData();

      return dyUpstreamOpsData;
   }

   @Override
   public Group buildGroup(Attributes attributes) {
      String id = attributes.getValue(ATTR_ID);
      String name = attributes.getValue(ATTR_NAME);
      String appId = attributes.getValue(ATTR_APP_ID);
      String version = attributes.getValue(ATTR_VERSION);
      String ssl = attributes.getValue(ATTR_SSL);
      Group group = new Group();

      if (id != null) {
         group.setId(convert(Long.class, id, null));
      }

      if (name != null) {
         group.setName(name);
      }

      if (appId != null) {
         group.setAppId(appId);
      }

      if (version != null) {
         group.setVersion(convert(Integer.class, version, null));
      }

      if (ssl != null) {
         group.setSsl(convert(Boolean.class, ssl, null));
      }

      return group;
   }

   @Override
   public GroupList buildGroupList(Attributes attributes) {
      GroupList groupList = new GroupList();

      return groupList;
   }

   @Override
   public String buildGroupName(Attributes attributes) {
      throw new UnsupportedOperationException();
   }

   @Override
   public GroupServer buildGroupServer(Attributes attributes) {
      String port = attributes.getValue(ATTR_PORT);
      String weight = attributes.getValue(ATTR_WEIGHT);
      String maxFails = attributes.getValue(ATTR_MAX_FAILS);
      String failTimeout = attributes.getValue(ATTR_FAIL_TIMEOUT);
      GroupServer groupServer = new GroupServer();

      if (port != null) {
         groupServer.setPort(convert(Integer.class, port, null));
      }

      if (weight != null) {
         groupServer.setWeight(convert(Integer.class, weight, null));
      }

      if (maxFails != null) {
         groupServer.setMaxFails(convert(Integer.class, maxFails, null));
      }

      if (failTimeout != null) {
         groupServer.setFailTimeout(convert(Integer.class, failTimeout, null));
      }

      return groupServer;
   }

   @Override
   public GroupServerStatus buildGroupServerStatus(Attributes attributes) {
      GroupServerStatus groupServerStatus = new GroupServerStatus();

      return groupServerStatus;
   }

   @Override
   public GroupSlb buildGroupSlb(Attributes attributes) {
      String priority = attributes.getValue(ATTR_PRIORITY);
      GroupSlb groupSlb = new GroupSlb();

      if (priority != null) {
         groupSlb.setPriority(convert(Integer.class, priority, null));
      }

      return groupSlb;
   }

   @Override
   public GroupStatus buildGroupStatus(Attributes attributes) {
      GroupStatus groupStatus = new GroupStatus();

      return groupStatus;
   }

   @Override
   public GroupStatusList buildGroupStatusList(Attributes attributes) {
      GroupStatusList groupStatusList = new GroupStatusList();

      return groupStatusList;
   }

   @Override
   public HealthCheck buildHealthCheck(Attributes attributes) {
      String intervals = attributes.getValue(ATTR_INTERVALS);
      String fails = attributes.getValue(ATTR_FAILS);
      String passes = attributes.getValue(ATTR_PASSES);
      HealthCheck healthCheck = new HealthCheck();

      if (intervals != null) {
         healthCheck.setIntervals(convert(Integer.class, intervals, null));
      }

      if (fails != null) {
         healthCheck.setFails(convert(Integer.class, fails, null));
      }

      if (passes != null) {
         healthCheck.setPasses(convert(Integer.class, passes, null));
      }

      return healthCheck;
   }

   @Override
   public String buildIp(Attributes attributes) {
      throw new UnsupportedOperationException();
   }

   @Override
   public IpAddresses buildIpAddresses(Attributes attributes) {
      IpAddresses ipAddresses = new IpAddresses();

      return ipAddresses;
   }

   @Override
   public IpGroupname buildIpGroupname(Attributes attributes) {
      IpGroupname ipGroupname = new IpGroupname();

      return ipGroupname;
   }

   @Override
   public LoadBalancingMethod buildLoadBalancingMethod(Attributes attributes) {
      String type = attributes.getValue(ATTR_TYPE);
      LoadBalancingMethod loadBalancingMethod = new LoadBalancingMethod();

      if (type != null) {
         loadBalancingMethod.setType(type);
      }

      return loadBalancingMethod;
   }

   @Override
   public MemberAction buildMemberAction(Attributes attributes) {
      MemberAction memberAction = new MemberAction();

      return memberAction;
   }

   @Override
   public Model buildModel(Attributes attributes) {
      Model model = new Model();

      return model;
   }

   @Override
   public NginxConfServerData buildNginxConfServerData(Attributes attributes) {
      NginxConfServerData nginxConfServerData = new NginxConfServerData();

      return nginxConfServerData;
   }

   @Override
   public NginxConfUpstreamData buildNginxConfUpstreamData(Attributes attributes) {
      NginxConfUpstreamData nginxConfUpstreamData = new NginxConfUpstreamData();

      return nginxConfUpstreamData;
   }

   @Override
   public OpMemberStatusReq buildOpMemberStatusReq(Attributes attributes) {
      OpMemberStatusReq opMemberStatusReq = new OpMemberStatusReq();

      return opMemberStatusReq;
   }

   @Override
   public OpServerStatusReq buildOpServerStatusReq(Attributes attributes) {
      OpServerStatusReq opServerStatusReq = new OpServerStatusReq();

      return opServerStatusReq;
   }

   @Override
   public ServerAction buildServerAction(Attributes attributes) {
      ServerAction serverAction = new ServerAction();

      return serverAction;
   }

   @Override
   public ServerStatus buildServerStatus(Attributes attributes) {
      ServerStatus serverStatus = new ServerStatus();

      return serverStatus;
   }

   @Override
   public Slb buildSlb(Attributes attributes) {
      String id = attributes.getValue(ATTR_ID);
      String name = attributes.getValue(ATTR_NAME);
      String version = attributes.getValue(ATTR_VERSION);
      Slb slb = new Slb();

      if (id != null) {
         slb.setId(convert(Long.class, id, null));
      }

      if (name != null) {
         slb.setName(name);
      }

      if (version != null) {
         slb.setVersion(convert(Integer.class, version, null));
      }

      return slb;
   }

   @Override
   public SlbList buildSlbList(Attributes attributes) {
      SlbList slbList = new SlbList();

      return slbList;
   }

   @Override
   public SlbServer buildSlbServer(Attributes attributes) {
      String slbId = attributes.getValue(ATTR_SLB_ID);
      String ip = attributes.getValue(ATTR_IP);
      String hostName = attributes.getValue(ATTR_HOST_NAME);
      String enable = attributes.getValue(ATTR_ENABLE);
      SlbServer slbServer = new SlbServer();

      if (slbId != null) {
         slbServer.setSlbId(convert(Long.class, slbId, null));
      }

      if (ip != null) {
         slbServer.setIp(ip);
      }

      if (hostName != null) {
         slbServer.setHostName(hostName);
      }

      if (enable != null) {
         slbServer.setEnable(convert(Boolean.class, enable, null));
      }

      return slbServer;
   }

   @Override
   public Vip buildVip(Attributes attributes) {
      String ip = attributes.getValue(ATTR_IP);
      Vip vip = new Vip();

      if (ip != null) {
         vip.setIp(ip);
      }

      return vip;
   }

   @Override
   public VirtualServer buildVirtualServer(Attributes attributes) {
      String name = attributes.getValue(ATTR_NAME);
      String id = attributes.getValue(ATTR_ID);
      String ssl = attributes.getValue(ATTR_SSL);
      VirtualServer virtualServer = new VirtualServer();

      if (name != null) {
         virtualServer.setName(name);
      }

      if (id != null) {
         virtualServer.setId(convert(Long.class, id, null));
      }

      if (ssl != null) {
         virtualServer.setSsl(convert(Boolean.class, ssl, null));
      }

      return virtualServer;
   }

   @SuppressWarnings("unchecked")
   protected <T> T convert(Class<T> type, String value, T defaultValue) {
      if (value == null) {
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
}
