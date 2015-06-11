package com.ctrip.zeus.model.entity;

import com.ctrip.zeus.model.BaseEntity;
import com.ctrip.zeus.model.IVisitor;

public class ConfGroupName extends BaseEntity<ConfGroupName> {
   private String m_groupname;

   public ConfGroupName() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitConfGroupName(this);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof ConfGroupName) {
         ConfGroupName _o = (ConfGroupName) obj;
         String groupname = _o.getGroupname();
         boolean result = true;

         result &= (m_groupname == groupname || m_groupname != null && m_groupname.equals(groupname));

         return result;
      }

      return false;
   }

   public String getGroupname() {
      return m_groupname;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_groupname == null ? 0 : m_groupname.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(ConfGroupName other) {
      if (other.getGroupname() != null) {
         m_groupname = other.getGroupname();
      }
   }

   public ConfGroupName setGroupname(String groupname) {
      m_groupname = groupname;
      return this;
   }

}
