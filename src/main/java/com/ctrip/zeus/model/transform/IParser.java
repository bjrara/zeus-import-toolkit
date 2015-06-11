package com.ctrip.zeus.model.transform;

import com.ctrip.zeus.model.entity.*;

public interface IParser<T> {
   public Model parse(IMaker<T> maker, ILinker linker, T node);

   public void parseForArchive(IMaker<T> maker, ILinker linker, Archive parent, T node);

   public void parseForConfGroupName(IMaker<T> maker, ILinker linker, ConfGroupName parent, T node);

   public void parseForConfReq(IMaker<T> maker, ILinker linker, ConfReq parent, T node);

   public void parseForConfSlbName(IMaker<T> maker, ILinker linker, ConfSlbName parent, T node);

   public void parseForDomain(IMaker<T> maker, ILinker linker, Domain parent, T node);

   public void parseForDyUpstreamOpsData(IMaker<T> maker, ILinker linker, DyUpstreamOpsData parent, T node);

   public void parseForGroup(IMaker<T> maker, ILinker linker, Group parent, T node);

   public void parseForGroupList(IMaker<T> maker, ILinker linker, GroupList parent, T node);

   public void parseForGroupServer(IMaker<T> maker, ILinker linker, GroupServer parent, T node);

   public void parseForGroupServerStatus(IMaker<T> maker, ILinker linker, GroupServerStatus parent, T node);

   public void parseForGroupSlb(IMaker<T> maker, ILinker linker, GroupSlb parent, T node);

   public void parseForGroupStatus(IMaker<T> maker, ILinker linker, GroupStatus parent, T node);

   public void parseForGroupStatusList(IMaker<T> maker, ILinker linker, GroupStatusList parent, T node);

   public void parseForHealthCheck(IMaker<T> maker, ILinker linker, HealthCheck parent, T node);

   public void parseForIpAddresses(IMaker<T> maker, ILinker linker, IpAddresses parent, T node);

   public void parseForIpGroupname(IMaker<T> maker, ILinker linker, IpGroupname parent, T node);

   public void parseForLoadBalancingMethod(IMaker<T> maker, ILinker linker, LoadBalancingMethod parent, T node);

   public void parseForMemberAction(IMaker<T> maker, ILinker linker, MemberAction parent, T node);

   public void parseForNginxConfServerData(IMaker<T> maker, ILinker linker, NginxConfServerData parent, T node);

   public void parseForNginxConfUpstreamData(IMaker<T> maker, ILinker linker, NginxConfUpstreamData parent, T node);

   public void parseForOpMemberStatusReq(IMaker<T> maker, ILinker linker, OpMemberStatusReq parent, T node);

   public void parseForOpServerStatusReq(IMaker<T> maker, ILinker linker, OpServerStatusReq parent, T node);

   public void parseForServerAction(IMaker<T> maker, ILinker linker, ServerAction parent, T node);

   public void parseForServerStatus(IMaker<T> maker, ILinker linker, ServerStatus parent, T node);

   public void parseForSlb(IMaker<T> maker, ILinker linker, Slb parent, T node);

   public void parseForSlbList(IMaker<T> maker, ILinker linker, SlbList parent, T node);

   public void parseForSlbServer(IMaker<T> maker, ILinker linker, SlbServer parent, T node);

   public void parseForVip(IMaker<T> maker, ILinker linker, Vip parent, T node);

   public void parseForVirtualServer(IMaker<T> maker, ILinker linker, VirtualServer parent, T node);
}
