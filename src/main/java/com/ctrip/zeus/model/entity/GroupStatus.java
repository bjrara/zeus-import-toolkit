package com.ctrip.zeus.model.entity;

import com.ctrip.zeus.model.BaseEntity;
import com.ctrip.zeus.model.IVisitor;

import java.util.ArrayList;
import java.util.List;

public class GroupStatus extends BaseEntity<GroupStatus> {
   private String m_groupName;

   private String m_slbName;

   private Long m_groupId;

   private Long m_slbId;

   private List<GroupServerStatus> m_groupServerStatuses = new ArrayList<GroupServerStatus>();

   public GroupStatus() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitGroupStatus(this);
   }

   public GroupStatus addGroupServerStatus(GroupServerStatus groupServerStatus) {
      m_groupServerStatuses.add(groupServerStatus);
      return this;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof GroupStatus) {
         GroupStatus _o = (GroupStatus) obj;
         String groupName = _o.getGroupName();
         String slbName = _o.getSlbName();
         Long groupId = _o.getGroupId();
         Long slbId = _o.getSlbId();
         List<GroupServerStatus> groupServerStatuses = _o.getGroupServerStatuses();
         boolean result = true;

         result &= (m_groupName == groupName || m_groupName != null && m_groupName.equals(groupName));
         result &= (m_slbName == slbName || m_slbName != null && m_slbName.equals(slbName));
         result &= (m_groupId == groupId || m_groupId != null && m_groupId.equals(groupId));
         result &= (m_slbId == slbId || m_slbId != null && m_slbId.equals(slbId));
         result &= (m_groupServerStatuses == groupServerStatuses || m_groupServerStatuses != null && m_groupServerStatuses.equals(groupServerStatuses));

         return result;
      }

      return false;
   }

   public Long getGroupId() {
      return m_groupId;
   }

   public String getGroupName() {
      return m_groupName;
   }

   public List<GroupServerStatus> getGroupServerStatuses() {
      return m_groupServerStatuses;
   }

   public Long getSlbId() {
      return m_slbId;
   }

   public String getSlbName() {
      return m_slbName;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_groupName == null ? 0 : m_groupName.hashCode());
      hash = hash * 31 + (m_slbName == null ? 0 : m_slbName.hashCode());
      hash = hash * 31 + (m_groupId == null ? 0 : m_groupId.hashCode());
      hash = hash * 31 + (m_slbId == null ? 0 : m_slbId.hashCode());
      hash = hash * 31 + (m_groupServerStatuses == null ? 0 : m_groupServerStatuses.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(GroupStatus other) {
   }

   public GroupStatus setGroupId(Long groupId) {
      m_groupId = groupId;
      return this;
   }

   public GroupStatus setGroupName(String groupName) {
      m_groupName = groupName;
      return this;
   }

   public GroupStatus setSlbId(Long slbId) {
      m_slbId = slbId;
      return this;
   }

   public GroupStatus setSlbName(String slbName) {
      m_slbName = slbName;
      return this;
   }

}
