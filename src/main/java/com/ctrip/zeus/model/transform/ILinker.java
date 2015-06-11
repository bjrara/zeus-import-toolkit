package com.ctrip.zeus.model.transform;

import com.ctrip.zeus.model.entity.*;

public interface ILinker {

   public boolean onArchive(Model parent, Archive archive);

   public boolean onConfGroupName(ConfReq parent, ConfGroupName confGroupName);

   public boolean onConfReq(Model parent, ConfReq confReq);

   public boolean onConfSlbName(ConfReq parent, ConfSlbName confSlbName);

   public boolean onDomain(VirtualServer parent, Domain domain);

   public boolean onDyUpstreamOpsData(Model parent, DyUpstreamOpsData dyUpstreamOpsData);

   public boolean onGroup(Model parent, Group group);

   public boolean onGroup(GroupList parent, Group group);

   public boolean onGroupList(Model parent, GroupList groupList);

   public boolean onGroupServer(Group parent, GroupServer groupServer);

   public boolean onGroupServerStatus(GroupStatus parent, GroupServerStatus groupServerStatus);

   public boolean onGroupSlb(Group parent, GroupSlb groupSlb);

   public boolean onGroupStatus(Model parent, GroupStatus groupStatus);

   public boolean onGroupStatus(GroupStatusList parent, GroupStatus groupStatus);

   public boolean onGroupStatusList(Model parent, GroupStatusList groupStatusList);

   public boolean onHealthCheck(Group parent, HealthCheck healthCheck);

   public boolean onIpAddresses(OpServerStatusReq parent, IpAddresses ipAddresses);

   public boolean onIpGroupname(OpMemberStatusReq parent, IpGroupname ipGroupname);

   public boolean onLoadBalancingMethod(Group parent, LoadBalancingMethod loadBalancingMethod);

   public boolean onMemberAction(Model parent, MemberAction memberAction);

   public boolean onNginxConfServerData(Model parent, NginxConfServerData nginxConfServerData);

   public boolean onNginxConfUpstreamData(Model parent, NginxConfUpstreamData nginxConfUpstreamData);

   public boolean onOpMemberStatusReq(Model parent, OpMemberStatusReq opMemberStatusReq);

   public boolean onOpServerStatusReq(Model parent, OpServerStatusReq opServerStatusReq);

   public boolean onServerAction(Model parent, ServerAction serverAction);

   public boolean onServerStatus(Model parent, ServerStatus serverStatus);

   public boolean onSlb(Model parent, Slb slb);

   public boolean onSlb(SlbList parent, Slb slb);

   public boolean onSlbList(Model parent, SlbList slbList);

   public boolean onSlbServer(Slb parent, SlbServer slbServer);

   public boolean onVip(Model parent, Vip vip);

   public boolean onVip(Slb parent, Vip vip);

   public boolean onVip(GroupSlb parent, Vip vip);

   public boolean onVirtualServer(Slb parent, VirtualServer virtualServer);

   public boolean onVirtualServer(GroupSlb parent, VirtualServer virtualServer);
}
