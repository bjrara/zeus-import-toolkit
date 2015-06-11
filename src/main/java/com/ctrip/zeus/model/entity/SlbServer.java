package com.ctrip.zeus.model.entity;

import com.ctrip.zeus.model.BaseEntity;
import com.ctrip.zeus.model.IVisitor;

public class SlbServer extends BaseEntity<SlbServer> {
   private Long m_slbId;

   private String m_ip;

   private String m_hostName;

   private Boolean m_enable;

   public SlbServer() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitSlbServer(this);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof SlbServer) {
         SlbServer _o = (SlbServer) obj;
         Long slbId = _o.getSlbId();
         String ip = _o.getIp();
         String hostName = _o.getHostName();
         Boolean enable = _o.getEnable();
         boolean result = true;

         result &= (m_slbId == slbId || m_slbId != null && m_slbId.equals(slbId));
         result &= (m_ip == ip || m_ip != null && m_ip.equals(ip));
         result &= (m_hostName == hostName || m_hostName != null && m_hostName.equals(hostName));
         result &= (m_enable == enable || m_enable != null && m_enable.equals(enable));

         return result;
      }

      return false;
   }

   public Boolean getEnable() {
      return m_enable;
   }

   public String getHostName() {
      return m_hostName;
   }

   public String getIp() {
      return m_ip;
   }

   public Long getSlbId() {
      return m_slbId;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_slbId == null ? 0 : m_slbId.hashCode());
      hash = hash * 31 + (m_ip == null ? 0 : m_ip.hashCode());
      hash = hash * 31 + (m_hostName == null ? 0 : m_hostName.hashCode());
      hash = hash * 31 + (m_enable == null ? 0 : m_enable.hashCode());

      return hash;
   }

   public boolean isEnable() {
      return m_enable != null && m_enable.booleanValue();
   }

   @Override
   public void mergeAttributes(SlbServer other) {
      if (other.getSlbId() != null) {
         m_slbId = other.getSlbId();
      }

      if (other.getIp() != null) {
         m_ip = other.getIp();
      }

      if (other.getHostName() != null) {
         m_hostName = other.getHostName();
      }

      if (other.getEnable() != null) {
         m_enable = other.getEnable();
      }
   }

   public SlbServer setEnable(Boolean enable) {
      m_enable = enable;
      return this;
   }

   public SlbServer setHostName(String hostName) {
      m_hostName = hostName;
      return this;
   }

   public SlbServer setIp(String ip) {
      m_ip = ip;
      return this;
   }

   public SlbServer setSlbId(Long slbId) {
      m_slbId = slbId;
      return this;
   }

}
