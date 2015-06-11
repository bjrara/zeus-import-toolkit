package com.ctrip.zeus.model.entity;

import com.ctrip.zeus.model.BaseEntity;
import com.ctrip.zeus.model.IVisitor;

import java.util.ArrayList;
import java.util.List;

public class GroupStatusList extends BaseEntity<GroupStatusList> {
   private Integer m_total;

   private List<GroupStatus> m_groupStatuses = new ArrayList<GroupStatus>();

   public GroupStatusList() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitGroupStatusList(this);
   }

   public GroupStatusList addGroupStatus(GroupStatus groupStatus) {
      m_groupStatuses.add(groupStatus);
      return this;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof GroupStatusList) {
         GroupStatusList _o = (GroupStatusList) obj;
         Integer total = _o.getTotal();
         List<GroupStatus> groupStatuses = _o.getGroupStatuses();
         boolean result = true;

         result &= (m_total == total || m_total != null && m_total.equals(total));
         result &= (m_groupStatuses == groupStatuses || m_groupStatuses != null && m_groupStatuses.equals(groupStatuses));

         return result;
      }

      return false;
   }

   public List<GroupStatus> getGroupStatuses() {
      return m_groupStatuses;
   }

   public Integer getTotal() {
      return m_total;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_total == null ? 0 : m_total.hashCode());
      hash = hash * 31 + (m_groupStatuses == null ? 0 : m_groupStatuses.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(GroupStatusList other) {
   }

   public GroupStatusList setTotal(Integer total) {
      m_total = total;
      return this;
   }

}
