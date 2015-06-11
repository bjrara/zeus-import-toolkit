package com.ctrip.zeus.model.entity;

import com.ctrip.zeus.model.BaseEntity;
import com.ctrip.zeus.model.IVisitor;

public class GroupServerStatus extends BaseEntity<GroupServerStatus> {
   private String m_ip;

   private Integer m_port;

   private Boolean m_member;

   private Boolean m_server;

   private Boolean m_up;

   public GroupServerStatus() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitGroupServerStatus(this);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof GroupServerStatus) {
         GroupServerStatus _o = (GroupServerStatus) obj;
         String ip = _o.getIp();
         Integer port = _o.getPort();
         Boolean member = _o.getMember();
         Boolean server = _o.getServer();
         Boolean up = _o.getUp();
         boolean result = true;

         result &= (m_ip == ip || m_ip != null && m_ip.equals(ip));
         result &= (m_port == port || m_port != null && m_port.equals(port));
         result &= (m_member == member || m_member != null && m_member.equals(member));
         result &= (m_server == server || m_server != null && m_server.equals(server));
         result &= (m_up == up || m_up != null && m_up.equals(up));

         return result;
      }

      return false;
   }

   public String getIp() {
      return m_ip;
   }

   public Boolean getMember() {
      return m_member;
   }

   public Integer getPort() {
      return m_port;
   }

   public Boolean getServer() {
      return m_server;
   }

   public Boolean getUp() {
      return m_up;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_ip == null ? 0 : m_ip.hashCode());
      hash = hash * 31 + (m_port == null ? 0 : m_port.hashCode());
      hash = hash * 31 + (m_member == null ? 0 : m_member.hashCode());
      hash = hash * 31 + (m_server == null ? 0 : m_server.hashCode());
      hash = hash * 31 + (m_up == null ? 0 : m_up.hashCode());

      return hash;
   }

   public boolean isMember() {
      return m_member != null && m_member.booleanValue();
   }

   public boolean isServer() {
      return m_server != null && m_server.booleanValue();
   }

   public boolean isUp() {
      return m_up != null && m_up.booleanValue();
   }

   @Override
   public void mergeAttributes(GroupServerStatus other) {
   }

   public GroupServerStatus setIp(String ip) {
      m_ip = ip;
      return this;
   }

   public GroupServerStatus setMember(Boolean member) {
      m_member = member;
      return this;
   }

   public GroupServerStatus setPort(Integer port) {
      m_port = port;
      return this;
   }

   public GroupServerStatus setServer(Boolean server) {
      m_server = server;
      return this;
   }

   public GroupServerStatus setUp(Boolean up) {
      m_up = up;
      return this;
   }

}
