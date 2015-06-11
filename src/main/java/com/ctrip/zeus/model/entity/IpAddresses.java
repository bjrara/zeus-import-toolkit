package com.ctrip.zeus.model.entity;

import com.ctrip.zeus.model.BaseEntity;
import com.ctrip.zeus.model.IVisitor;

public class IpAddresses extends BaseEntity<IpAddresses> {
   private String m_ipAddr;

   public IpAddresses() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitIpAddresses(this);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof IpAddresses) {
         IpAddresses _o = (IpAddresses) obj;
         String ipAddr = _o.getIpAddr();
         boolean result = true;

         result &= (m_ipAddr == ipAddr || m_ipAddr != null && m_ipAddr.equals(ipAddr));

         return result;
      }

      return false;
   }

   public String getIpAddr() {
      return m_ipAddr;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_ipAddr == null ? 0 : m_ipAddr.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(IpAddresses other) {
   }

   public IpAddresses setIpAddr(String ipAddr) {
      m_ipAddr = ipAddr;
      return this;
   }

}
