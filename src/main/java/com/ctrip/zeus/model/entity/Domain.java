package com.ctrip.zeus.model.entity;

import com.ctrip.zeus.model.BaseEntity;
import com.ctrip.zeus.model.IVisitor;

public class Domain extends BaseEntity<Domain> {
   private String m_name;

   public Domain() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitDomain(this);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof Domain) {
         Domain _o = (Domain) obj;
         String name = _o.getName();
         boolean result = true;

         result &= (m_name == name || m_name != null && m_name.equals(name));

         return result;
      }

      return false;
   }

   public String getName() {
      return m_name;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_name == null ? 0 : m_name.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(Domain other) {
      if (other.getName() != null) {
         m_name = other.getName();
      }
   }

   public Domain setName(String name) {
      m_name = name;
      return this;
   }

}
