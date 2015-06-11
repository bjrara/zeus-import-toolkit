package com.ctrip.zeus.model.entity;

import com.ctrip.zeus.model.BaseEntity;
import com.ctrip.zeus.model.IVisitor;

public class IpGroupname extends BaseEntity<IpGroupname> {
   private String m_memberIp;

   private String m_memberGroupname;

   public IpGroupname() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitIpGroupname(this);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof IpGroupname) {
         IpGroupname _o = (IpGroupname) obj;
         String memberIp = _o.getMemberIp();
         String memberGroupname = _o.getMemberGroupname();
         boolean result = true;

         result &= (m_memberIp == memberIp || m_memberIp != null && m_memberIp.equals(memberIp));
         result &= (m_memberGroupname == memberGroupname || m_memberGroupname != null && m_memberGroupname.equals(memberGroupname));

         return result;
      }

      return false;
   }

   public String getMemberGroupname() {
      return m_memberGroupname;
   }

   public String getMemberIp() {
      return m_memberIp;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_memberIp == null ? 0 : m_memberIp.hashCode());
      hash = hash * 31 + (m_memberGroupname == null ? 0 : m_memberGroupname.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(IpGroupname other) {
   }

   public IpGroupname setMemberGroupname(String memberGroupname) {
      m_memberGroupname = memberGroupname;
      return this;
   }

   public IpGroupname setMemberIp(String memberIp) {
      m_memberIp = memberIp;
      return this;
   }

}
