package com.ctrip.zeus.model.entity;

import com.ctrip.zeus.model.BaseEntity;
import com.ctrip.zeus.model.IVisitor;

import java.util.ArrayList;
import java.util.List;

public class GroupSlb extends BaseEntity<GroupSlb> {
   private Integer m_priority;

   private Long m_groupId;

   private Long m_slbId;

   private String m_slbName;

   private String m_path;

   private String m_rewrite;

   private List<Vip> m_slbVips = new ArrayList<Vip>();

   private VirtualServer m_virtualServer;

   public GroupSlb() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitGroupSlb(this);
   }

   public GroupSlb addVip(Vip vip) {
      m_slbVips.add(vip);
      return this;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof GroupSlb) {
         GroupSlb _o = (GroupSlb) obj;
         Integer priority = _o.getPriority();
         Long groupId = _o.getGroupId();
         Long slbId = _o.getSlbId();
         String slbName = _o.getSlbName();
         String path = _o.getPath();
         String rewrite = _o.getRewrite();
         List<Vip> slbVips = _o.getSlbVips();
         VirtualServer virtualServer = _o.getVirtualServer();
         boolean result = true;

         result &= (m_priority == priority || m_priority != null && m_priority.equals(priority));
         result &= (m_groupId == groupId || m_groupId != null && m_groupId.equals(groupId));
         result &= (m_slbId == slbId || m_slbId != null && m_slbId.equals(slbId));
         result &= (m_slbName == slbName || m_slbName != null && m_slbName.equals(slbName));
         result &= (m_path == path || m_path != null && m_path.equals(path));
         result &= (m_rewrite == rewrite || m_rewrite != null && m_rewrite.equals(rewrite));
         result &= (m_slbVips == slbVips || m_slbVips != null && m_slbVips.equals(slbVips));
         result &= (m_virtualServer == virtualServer || m_virtualServer != null && m_virtualServer.equals(virtualServer));

         return result;
      }

      return false;
   }

   public Long getGroupId() {
      return m_groupId;
   }

   public String getPath() {
      return m_path;
   }

   public Integer getPriority() {
      return m_priority;
   }

   public String getRewrite() {
      return m_rewrite;
   }

   public Long getSlbId() {
      return m_slbId;
   }

   public String getSlbName() {
      return m_slbName;
   }

   public List<Vip> getSlbVips() {
      return m_slbVips;
   }

   public VirtualServer getVirtualServer() {
      return m_virtualServer;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_priority == null ? 0 : m_priority.hashCode());
      hash = hash * 31 + (m_groupId == null ? 0 : m_groupId.hashCode());
      hash = hash * 31 + (m_slbId == null ? 0 : m_slbId.hashCode());
      hash = hash * 31 + (m_slbName == null ? 0 : m_slbName.hashCode());
      hash = hash * 31 + (m_path == null ? 0 : m_path.hashCode());
      hash = hash * 31 + (m_rewrite == null ? 0 : m_rewrite.hashCode());
      hash = hash * 31 + (m_slbVips == null ? 0 : m_slbVips.hashCode());
      hash = hash * 31 + (m_virtualServer == null ? 0 : m_virtualServer.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(GroupSlb other) {
      if (other.getPriority() != null) {
         m_priority = other.getPriority();
      }
   }

   public GroupSlb setGroupId(Long groupId) {
      m_groupId = groupId;
      return this;
   }

   public GroupSlb setPath(String path) {
      m_path = path;
      return this;
   }

   public GroupSlb setPriority(Integer priority) {
      m_priority = priority;
      return this;
   }

   public GroupSlb setRewrite(String rewrite) {
      m_rewrite = rewrite;
      return this;
   }

   public GroupSlb setSlbId(Long slbId) {
      m_slbId = slbId;
      return this;
   }

   public GroupSlb setSlbName(String slbName) {
      m_slbName = slbName;
      return this;
   }

   public GroupSlb setVirtualServer(VirtualServer virtualServer) {
      m_virtualServer = virtualServer;
      return this;
   }

}
