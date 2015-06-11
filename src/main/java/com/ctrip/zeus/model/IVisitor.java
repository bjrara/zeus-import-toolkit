package com.ctrip.zeus.model;

import com.ctrip.zeus.model.entity.*;

public interface IVisitor {

   public void visitArchive(Archive archive);

   public void visitConfGroupName(ConfGroupName confGroupName);

   public void visitConfReq(ConfReq confReq);

   public void visitConfSlbName(ConfSlbName confSlbName);

   public void visitDomain(Domain domain);

   public void visitDyUpstreamOpsData(DyUpstreamOpsData dyUpstreamOpsData);

   public void visitGroup(Group group);

   public void visitGroupList(GroupList groupList);

   public void visitGroupServer(GroupServer groupServer);

   public void visitGroupServerStatus(GroupServerStatus groupServerStatus);

   public void visitGroupSlb(GroupSlb groupSlb);

   public void visitGroupStatus(GroupStatus groupStatus);

   public void visitGroupStatusList(GroupStatusList groupStatusList);

   public void visitHealthCheck(HealthCheck healthCheck);

   public void visitIpAddresses(IpAddresses ipAddresses);

   public void visitIpGroupname(IpGroupname ipGroupname);

   public void visitLoadBalancingMethod(LoadBalancingMethod loadBalancingMethod);

   public void visitMemberAction(MemberAction memberAction);

   public void visitModel(Model model);

   public void visitNginxConfServerData(NginxConfServerData nginxConfServerData);

   public void visitNginxConfUpstreamData(NginxConfUpstreamData nginxConfUpstreamData);

   public void visitOpMemberStatusReq(OpMemberStatusReq opMemberStatusReq);

   public void visitOpServerStatusReq(OpServerStatusReq opServerStatusReq);

   public void visitServerAction(ServerAction serverAction);

   public void visitServerStatus(ServerStatus serverStatus);

   public void visitSlb(Slb slb);

   public void visitSlbList(SlbList slbList);

   public void visitSlbServer(SlbServer slbServer);

   public void visitVip(Vip vip);

   public void visitVirtualServer(VirtualServer virtualServer);
}
