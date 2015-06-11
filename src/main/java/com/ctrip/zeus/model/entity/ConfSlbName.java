package com.ctrip.zeus.model.entity;

import com.ctrip.zeus.model.BaseEntity;
import com.ctrip.zeus.model.IVisitor;

public class ConfSlbName extends BaseEntity<ConfSlbName> {
   private String m_slbname;

   public ConfSlbName() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitConfSlbName(this);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof ConfSlbName) {
         ConfSlbName _o = (ConfSlbName) obj;
         String slbname = _o.getSlbname();
         boolean result = true;

         result &= (m_slbname == slbname || m_slbname != null && m_slbname.equals(slbname));

         return result;
      }

      return false;
   }

   public String getSlbname() {
      return m_slbname;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_slbname == null ? 0 : m_slbname.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(ConfSlbName other) {
      if (other.getSlbname() != null) {
         m_slbname = other.getSlbname();
      }
   }

   public ConfSlbName setSlbname(String slbname) {
      m_slbname = slbname;
      return this;
   }

}
