package com.ctrip.zeus.model.transform;

import com.ctrip.zeus.model.entity.*;

public interface IMaker<T> {

   public Archive buildArchive(T node);

   public ConfGroupName buildConfGroupName(T node);

   public ConfReq buildConfReq(T node);

   public ConfSlbName buildConfSlbName(T node);

   public Domain buildDomain(T node);

   public DyUpstreamOpsData buildDyUpstreamOpsData(T node);

   public Group buildGroup(T node);

   public GroupList buildGroupList(T node);

   public String buildGroupName(T node);

   public GroupServer buildGroupServer(T node);

   public GroupServerStatus buildGroupServerStatus(T node);

   public GroupSlb buildGroupSlb(T node);

   public GroupStatus buildGroupStatus(T node);

   public GroupStatusList buildGroupStatusList(T node);

   public HealthCheck buildHealthCheck(T node);

   public String buildIp(T node);

   public IpAddresses buildIpAddresses(T node);

   public IpGroupname buildIpGroupname(T node);

   public LoadBalancingMethod buildLoadBalancingMethod(T node);

   public MemberAction buildMemberAction(T node);

   public Model buildModel(T node);

   public NginxConfServerData buildNginxConfServerData(T node);

   public NginxConfUpstreamData buildNginxConfUpstreamData(T node);

   public OpMemberStatusReq buildOpMemberStatusReq(T node);

   public OpServerStatusReq buildOpServerStatusReq(T node);

   public ServerAction buildServerAction(T node);

   public ServerStatus buildServerStatus(T node);

   public Slb buildSlb(T node);

   public SlbList buildSlbList(T node);

   public SlbServer buildSlbServer(T node);

   public Vip buildVip(T node);

   public VirtualServer buildVirtualServer(T node);
}
