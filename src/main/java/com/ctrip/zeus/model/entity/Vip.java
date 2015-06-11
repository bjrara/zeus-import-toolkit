package com.ctrip.zeus.model.entity;

import com.ctrip.zeus.model.BaseEntity;
import com.ctrip.zeus.model.IVisitor;

public class Vip extends BaseEntity<Vip> {
   private String m_ip;

   public Vip() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitVip(this);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof Vip) {
         Vip _o = (Vip) obj;
         String ip = _o.getIp();
         boolean result = true;

         result &= (m_ip == ip || m_ip != null && m_ip.equals(ip));

         return result;
      }

      return false;
   }

   public String getIp() {
      return m_ip;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_ip == null ? 0 : m_ip.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(Vip other) {
      if (other.getIp() != null) {
         m_ip = other.getIp();
      }
   }

   public Vip setIp(String ip) {
      m_ip = ip;
      return this;
   }

}
