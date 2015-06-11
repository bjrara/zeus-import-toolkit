package com.ctrip.zeus.model.entity;

import com.ctrip.zeus.model.BaseEntity;
import com.ctrip.zeus.model.IVisitor;

public class GroupServer extends BaseEntity<GroupServer> {
   private Integer m_port;

   private Integer m_weight;

   private Integer m_maxFails;

   private Integer m_failTimeout;

   private String m_hostName;

   private String m_ip;

   public GroupServer() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitGroupServer(this);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof GroupServer) {
         GroupServer _o = (GroupServer) obj;
         Integer port = _o.getPort();
         Integer weight = _o.getWeight();
         Integer maxFails = _o.getMaxFails();
         Integer failTimeout = _o.getFailTimeout();
         String hostName = _o.getHostName();
         String ip = _o.getIp();
         boolean result = true;

         result &= (m_port == port || m_port != null && m_port.equals(port));
         result &= (m_weight == weight || m_weight != null && m_weight.equals(weight));
         result &= (m_maxFails == maxFails || m_maxFails != null && m_maxFails.equals(maxFails));
         result &= (m_failTimeout == failTimeout || m_failTimeout != null && m_failTimeout.equals(failTimeout));
         result &= (m_hostName == hostName || m_hostName != null && m_hostName.equals(hostName));
         result &= (m_ip == ip || m_ip != null && m_ip.equals(ip));

         return result;
      }

      return false;
   }

   public Integer getFailTimeout() {
      return m_failTimeout;
   }

   public String getHostName() {
      return m_hostName;
   }

   public String getIp() {
      return m_ip;
   }

   public Integer getMaxFails() {
      return m_maxFails;
   }

   public Integer getPort() {
      return m_port;
   }

   public Integer getWeight() {
      return m_weight;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_port == null ? 0 : m_port.hashCode());
      hash = hash * 31 + (m_weight == null ? 0 : m_weight.hashCode());
      hash = hash * 31 + (m_maxFails == null ? 0 : m_maxFails.hashCode());
      hash = hash * 31 + (m_failTimeout == null ? 0 : m_failTimeout.hashCode());
      hash = hash * 31 + (m_hostName == null ? 0 : m_hostName.hashCode());
      hash = hash * 31 + (m_ip == null ? 0 : m_ip.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(GroupServer other) {
      if (other.getPort() != null) {
         m_port = other.getPort();
      }

      if (other.getWeight() != null) {
         m_weight = other.getWeight();
      }

      if (other.getMaxFails() != null) {
         m_maxFails = other.getMaxFails();
      }

      if (other.getFailTimeout() != null) {
         m_failTimeout = other.getFailTimeout();
      }
   }

   public GroupServer setFailTimeout(Integer failTimeout) {
      m_failTimeout = failTimeout;
      return this;
   }

   public GroupServer setHostName(String hostName) {
      m_hostName = hostName;
      return this;
   }

   public GroupServer setIp(String ip) {
      m_ip = ip;
      return this;
   }

   public GroupServer setMaxFails(Integer maxFails) {
      m_maxFails = maxFails;
      return this;
   }

   public GroupServer setPort(Integer port) {
      m_port = port;
      return this;
   }

   public GroupServer setWeight(Integer weight) {
      m_weight = weight;
      return this;
   }

}
