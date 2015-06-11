package com.ctrip.zeus.model.entity;

import com.ctrip.zeus.model.BaseEntity;
import com.ctrip.zeus.model.IVisitor;

import java.util.ArrayList;
import java.util.List;

public class GroupList extends BaseEntity<GroupList> {
   private Integer m_total;

   private List<Group> m_groups = new ArrayList<Group>();

   public GroupList() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitGroupList(this);
   }

   public GroupList addGroup(Group group) {
      m_groups.add(group);
      return this;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof GroupList) {
         GroupList _o = (GroupList) obj;
         Integer total = _o.getTotal();
         List<Group> groups = _o.getGroups();
         boolean result = true;

         result &= (m_total == total || m_total != null && m_total.equals(total));
         result &= (m_groups == groups || m_groups != null && m_groups.equals(groups));

         return result;
      }

      return false;
   }

   public List<Group> getGroups() {
      return m_groups;
   }

   public Integer getTotal() {
      return m_total;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_total == null ? 0 : m_total.hashCode());
      hash = hash * 31 + (m_groups == null ? 0 : m_groups.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(GroupList other) {
   }

   public GroupList setTotal(Integer total) {
      m_total = total;
      return this;
   }

}
