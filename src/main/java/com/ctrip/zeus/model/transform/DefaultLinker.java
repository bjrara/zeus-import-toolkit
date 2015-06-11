package com.ctrip.zeus.model.transform;

import com.ctrip.zeus.model.entity.*;

import java.util.ArrayList;
import java.util.List;

public class DefaultLinker implements ILinker {
   @SuppressWarnings("unused")
   private boolean m_deferrable;

   private List<Runnable> m_deferedJobs = new ArrayList<Runnable>();

   public DefaultLinker(boolean deferrable) {
      m_deferrable = deferrable;
   }

   public void finish() {
      for (Runnable job : m_deferedJobs) {
         job.run();
      }
   }

   @Override
   public boolean onArchive(final Model parent, final Archive archive) {
      parent.setArchive(archive);
      return true;
   }

   @Override
   public boolean onConfGroupName(final ConfReq parent, final ConfGroupName confGroupName) {
      parent.addConfGroupName(confGroupName);
      return true;
   }

   @Override
   public boolean onConfReq(final Model parent, final ConfReq confReq) {
      parent.setConfReq(confReq);
      return true;
   }

   @Override
   public boolean onConfSlbName(final ConfReq parent, final ConfSlbName confSlbName) {
      parent.addConfSlbName(confSlbName);
      return true;
   }

   @Override
   public boolean onDomain(final VirtualServer parent, final Domain domain) {
      parent.addDomain(domain);
      return true;
   }

   @Override
   public boolean onDyUpstreamOpsData(final Model parent, final DyUpstreamOpsData dyUpstreamOpsData) {
      parent.setDyUpstreamOpsData(dyUpstreamOpsData);
      return true;
   }

   @Override
   public boolean onGroup(final Model parent, final Group group) {
      parent.setGroup(group);
      return true;
   }

   @Override
   public boolean onGroup(final GroupList parent, final Group group) {
      parent.addGroup(group);
      return true;
   }

   @Override
   public boolean onGroupList(final Model parent, final GroupList groupList) {
      parent.setGroupList(groupList);
      return true;
   }

   @Override
   public boolean onGroupServer(final Group parent, final GroupServer groupServer) {
      parent.addGroupServer(groupServer);
      return true;
   }

   @Override
   public boolean onGroupServerStatus(final GroupStatus parent, final GroupServerStatus groupServerStatus) {
      parent.addGroupServerStatus(groupServerStatus);
      return true;
   }

   @Override
   public boolean onGroupSlb(final Group parent, final GroupSlb groupSlb) {
      parent.addGroupSlb(groupSlb);
      return true;
   }

   @Override
   public boolean onGroupStatus(final Model parent, final GroupStatus groupStatus) {
      parent.setGroupStatus(groupStatus);
      return true;
   }

   @Override
   public boolean onGroupStatus(final GroupStatusList parent, final GroupStatus groupStatus) {
      parent.addGroupStatus(groupStatus);
      return true;
   }

   @Override
   public boolean onGroupStatusList(final Model parent, final GroupStatusList groupStatusList) {
      parent.setGroupStatusList(groupStatusList);
      return true;
   }

   @Override
   public boolean onHealthCheck(final Group parent, final HealthCheck healthCheck) {
      parent.setHealthCheck(healthCheck);
      return true;
   }

   @Override
   public boolean onIpAddresses(final OpServerStatusReq parent, final IpAddresses ipAddresses) {
      parent.addIpAddresses(ipAddresses);
      return true;
   }

   @Override
   public boolean onIpGroupname(final OpMemberStatusReq parent, final IpGroupname ipGroupname) {
      parent.addIpGroupname(ipGroupname);
      return true;
   }

   @Override
   public boolean onLoadBalancingMethod(final Group parent, final LoadBalancingMethod loadBalancingMethod) {
      parent.setLoadBalancingMethod(loadBalancingMethod);
      return true;
   }

   @Override
   public boolean onMemberAction(final Model parent, final MemberAction memberAction) {
      parent.setMemberAction(memberAction);
      return true;
   }

   @Override
   public boolean onNginxConfServerData(final Model parent, final NginxConfServerData nginxConfServerData) {
      parent.setNginxConfServerData(nginxConfServerData);
      return true;
   }

   @Override
   public boolean onNginxConfUpstreamData(final Model parent, final NginxConfUpstreamData nginxConfUpstreamData) {
      parent.setNginxConfUpstreamData(nginxConfUpstreamData);
      return true;
   }

   @Override
   public boolean onOpMemberStatusReq(final Model parent, final OpMemberStatusReq opMemberStatusReq) {
      parent.setOpMemberStatusReq(opMemberStatusReq);
      return true;
   }

   @Override
   public boolean onOpServerStatusReq(final Model parent, final OpServerStatusReq opServerStatusReq) {
      parent.setOpServerStatusReq(opServerStatusReq);
      return true;
   }

   @Override
   public boolean onServerAction(final Model parent, final ServerAction serverAction) {
      parent.setServerAction(serverAction);
      return true;
   }

   @Override
   public boolean onServerStatus(final Model parent, final ServerStatus serverStatus) {
      parent.setServerStatus(serverStatus);
      return true;
   }

   @Override
   public boolean onSlb(final Model parent, final Slb slb) {
      parent.setSlb(slb);
      return true;
   }

   @Override
   public boolean onSlb(final SlbList parent, final Slb slb) {
      parent.addSlb(slb);
      return true;
   }

   @Override
   public boolean onSlbList(final Model parent, final SlbList slbList) {
      parent.setSlbList(slbList);
      return true;
   }

   @Override
   public boolean onSlbServer(final Slb parent, final SlbServer slbServer) {
      parent.addSlbServer(slbServer);
      return true;
   }

   @Override
   public boolean onVip(final Model parent, final Vip vip) {
      parent.setVip(vip);
      return true;
   }

   @Override
   public boolean onVip(final Slb parent, final Vip vip) {
      parent.addVip(vip);
      return true;
   }

   @Override
   public boolean onVip(final GroupSlb parent, final Vip vip) {
      parent.addVip(vip);
      return true;
   }

   @Override
   public boolean onVirtualServer(final Slb parent, final VirtualServer virtualServer) {
      parent.addVirtualServer(virtualServer);
      return true;
   }

   @Override
   public boolean onVirtualServer(final GroupSlb parent, final VirtualServer virtualServer) {
      parent.setVirtualServer(virtualServer);
      return true;
   }
}
