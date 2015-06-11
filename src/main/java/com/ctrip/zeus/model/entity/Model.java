package com.ctrip.zeus.model.entity;

import com.ctrip.zeus.model.BaseEntity;
import com.ctrip.zeus.model.IVisitor;

public class Model extends BaseEntity<Model> {
   private Slb m_slb;

   private SlbList m_slbList;

   private Vip m_vip;

   private Group m_group;

   private Archive m_archive;

   private GroupList m_groupList;

   private MemberAction m_memberAction;

   private ServerAction m_serverAction;

   private GroupStatus m_groupStatus;

   private GroupStatusList m_groupStatusList;

   private ServerStatus m_serverStatus;

   private ConfReq m_confReq;

   private OpServerStatusReq m_opServerStatusReq;

   private OpMemberStatusReq m_opMemberStatusReq;

   private NginxConfUpstreamData m_nginxConfUpstreamData;

   private NginxConfServerData m_nginxConfServerData;

   private DyUpstreamOpsData m_dyUpstreamOpsData;

   public Model() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitModel(this);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof Model) {
         Model _o = (Model) obj;
         Slb slb = _o.getSlb();
         SlbList slbList = _o.getSlbList();
         Vip vip = _o.getVip();
         Group group = _o.getGroup();
         Archive archive = _o.getArchive();
         GroupList groupList = _o.getGroupList();
         MemberAction memberAction = _o.getMemberAction();
         ServerAction serverAction = _o.getServerAction();
         GroupStatus groupStatus = _o.getGroupStatus();
         GroupStatusList groupStatusList = _o.getGroupStatusList();
         ServerStatus serverStatus = _o.getServerStatus();
         ConfReq confReq = _o.getConfReq();
         OpServerStatusReq opServerStatusReq = _o.getOpServerStatusReq();
         OpMemberStatusReq opMemberStatusReq = _o.getOpMemberStatusReq();
         NginxConfUpstreamData nginxConfUpstreamData = _o.getNginxConfUpstreamData();
         NginxConfServerData nginxConfServerData = _o.getNginxConfServerData();
         DyUpstreamOpsData dyUpstreamOpsData = _o.getDyUpstreamOpsData();
         boolean result = true;

         result &= (m_slb == slb || m_slb != null && m_slb.equals(slb));
         result &= (m_slbList == slbList || m_slbList != null && m_slbList.equals(slbList));
         result &= (m_vip == vip || m_vip != null && m_vip.equals(vip));
         result &= (m_group == group || m_group != null && m_group.equals(group));
         result &= (m_archive == archive || m_archive != null && m_archive.equals(archive));
         result &= (m_groupList == groupList || m_groupList != null && m_groupList.equals(groupList));
         result &= (m_memberAction == memberAction || m_memberAction != null && m_memberAction.equals(memberAction));
         result &= (m_serverAction == serverAction || m_serverAction != null && m_serverAction.equals(serverAction));
         result &= (m_groupStatus == groupStatus || m_groupStatus != null && m_groupStatus.equals(groupStatus));
         result &= (m_groupStatusList == groupStatusList || m_groupStatusList != null && m_groupStatusList.equals(groupStatusList));
         result &= (m_serverStatus == serverStatus || m_serverStatus != null && m_serverStatus.equals(serverStatus));
         result &= (m_confReq == confReq || m_confReq != null && m_confReq.equals(confReq));
         result &= (m_opServerStatusReq == opServerStatusReq || m_opServerStatusReq != null && m_opServerStatusReq.equals(opServerStatusReq));
         result &= (m_opMemberStatusReq == opMemberStatusReq || m_opMemberStatusReq != null && m_opMemberStatusReq.equals(opMemberStatusReq));
         result &= (m_nginxConfUpstreamData == nginxConfUpstreamData || m_nginxConfUpstreamData != null && m_nginxConfUpstreamData.equals(nginxConfUpstreamData));
         result &= (m_nginxConfServerData == nginxConfServerData || m_nginxConfServerData != null && m_nginxConfServerData.equals(nginxConfServerData));
         result &= (m_dyUpstreamOpsData == dyUpstreamOpsData || m_dyUpstreamOpsData != null && m_dyUpstreamOpsData.equals(dyUpstreamOpsData));

         return result;
      }

      return false;
   }

   public Archive getArchive() {
      return m_archive;
   }

   public ConfReq getConfReq() {
      return m_confReq;
   }

   public DyUpstreamOpsData getDyUpstreamOpsData() {
      return m_dyUpstreamOpsData;
   }

   public Group getGroup() {
      return m_group;
   }

   public GroupList getGroupList() {
      return m_groupList;
   }

   public GroupStatus getGroupStatus() {
      return m_groupStatus;
   }

   public GroupStatusList getGroupStatusList() {
      return m_groupStatusList;
   }

   public MemberAction getMemberAction() {
      return m_memberAction;
   }

   public NginxConfServerData getNginxConfServerData() {
      return m_nginxConfServerData;
   }

   public NginxConfUpstreamData getNginxConfUpstreamData() {
      return m_nginxConfUpstreamData;
   }

   public OpMemberStatusReq getOpMemberStatusReq() {
      return m_opMemberStatusReq;
   }

   public OpServerStatusReq getOpServerStatusReq() {
      return m_opServerStatusReq;
   }

   public ServerAction getServerAction() {
      return m_serverAction;
   }

   public ServerStatus getServerStatus() {
      return m_serverStatus;
   }

   public Slb getSlb() {
      return m_slb;
   }

   public SlbList getSlbList() {
      return m_slbList;
   }

   public Vip getVip() {
      return m_vip;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_slb == null ? 0 : m_slb.hashCode());
      hash = hash * 31 + (m_slbList == null ? 0 : m_slbList.hashCode());
      hash = hash * 31 + (m_vip == null ? 0 : m_vip.hashCode());
      hash = hash * 31 + (m_group == null ? 0 : m_group.hashCode());
      hash = hash * 31 + (m_archive == null ? 0 : m_archive.hashCode());
      hash = hash * 31 + (m_groupList == null ? 0 : m_groupList.hashCode());
      hash = hash * 31 + (m_memberAction == null ? 0 : m_memberAction.hashCode());
      hash = hash * 31 + (m_serverAction == null ? 0 : m_serverAction.hashCode());
      hash = hash * 31 + (m_groupStatus == null ? 0 : m_groupStatus.hashCode());
      hash = hash * 31 + (m_groupStatusList == null ? 0 : m_groupStatusList.hashCode());
      hash = hash * 31 + (m_serverStatus == null ? 0 : m_serverStatus.hashCode());
      hash = hash * 31 + (m_confReq == null ? 0 : m_confReq.hashCode());
      hash = hash * 31 + (m_opServerStatusReq == null ? 0 : m_opServerStatusReq.hashCode());
      hash = hash * 31 + (m_opMemberStatusReq == null ? 0 : m_opMemberStatusReq.hashCode());
      hash = hash * 31 + (m_nginxConfUpstreamData == null ? 0 : m_nginxConfUpstreamData.hashCode());
      hash = hash * 31 + (m_nginxConfServerData == null ? 0 : m_nginxConfServerData.hashCode());
      hash = hash * 31 + (m_dyUpstreamOpsData == null ? 0 : m_dyUpstreamOpsData.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(Model other) {
   }

   public Model setArchive(Archive archive) {
      m_archive = archive;
      return this;
   }

   public Model setConfReq(ConfReq confReq) {
      m_confReq = confReq;
      return this;
   }

   public Model setDyUpstreamOpsData(DyUpstreamOpsData dyUpstreamOpsData) {
      m_dyUpstreamOpsData = dyUpstreamOpsData;
      return this;
   }

   public Model setGroup(Group group) {
      m_group = group;
      return this;
   }

   public Model setGroupList(GroupList groupList) {
      m_groupList = groupList;
      return this;
   }

   public Model setGroupStatus(GroupStatus groupStatus) {
      m_groupStatus = groupStatus;
      return this;
   }

   public Model setGroupStatusList(GroupStatusList groupStatusList) {
      m_groupStatusList = groupStatusList;
      return this;
   }

   public Model setMemberAction(MemberAction memberAction) {
      m_memberAction = memberAction;
      return this;
   }

   public Model setNginxConfServerData(NginxConfServerData nginxConfServerData) {
      m_nginxConfServerData = nginxConfServerData;
      return this;
   }

   public Model setNginxConfUpstreamData(NginxConfUpstreamData nginxConfUpstreamData) {
      m_nginxConfUpstreamData = nginxConfUpstreamData;
      return this;
   }

   public Model setOpMemberStatusReq(OpMemberStatusReq opMemberStatusReq) {
      m_opMemberStatusReq = opMemberStatusReq;
      return this;
   }

   public Model setOpServerStatusReq(OpServerStatusReq opServerStatusReq) {
      m_opServerStatusReq = opServerStatusReq;
      return this;
   }

   public Model setServerAction(ServerAction serverAction) {
      m_serverAction = serverAction;
      return this;
   }

   public Model setServerStatus(ServerStatus serverStatus) {
      m_serverStatus = serverStatus;
      return this;
   }

   public Model setSlb(Slb slb) {
      m_slb = slb;
      return this;
   }

   public Model setSlbList(SlbList slbList) {
      m_slbList = slbList;
      return this;
   }

   public Model setVip(Vip vip) {
      m_vip = vip;
      return this;
   }

}
