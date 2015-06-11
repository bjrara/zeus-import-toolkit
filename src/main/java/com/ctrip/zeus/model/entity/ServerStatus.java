package com.ctrip.zeus.model.entity;

import com.ctrip.zeus.model.BaseEntity;
import com.ctrip.zeus.model.IVisitor;

import java.util.ArrayList;
import java.util.List;

public class ServerStatus extends BaseEntity<ServerStatus> {
   private String m_ip;

   private Boolean m_up;

   private List<String> m_groupNames = new ArrayList<String>();

   public ServerStatus() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitServerStatus(this);
   }

   public ServerStatus addGroupName(String groupName) {
      m_groupNames.add(groupName);
      return this;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof ServerStatus) {
         ServerStatus _o = (ServerStatus) obj;
         String ip = _o.getIp();
         Boolean up = _o.getUp();
         List<String> groupNames = _o.getGroupNames();
         boolean result = true;

         result &= (m_ip == ip || m_ip != null && m_ip.equals(ip));
         result &= (m_up == up || m_up != null && m_up.equals(up));
         result &= (m_groupNames == groupNames || m_groupNames != null && m_groupNames.equals(groupNames));

         return result;
      }

      return false;
   }

   public List<String> getGroupNames() {
      return m_groupNames;
   }

   public String getIp() {
      return m_ip;
   }

   public Boolean getUp() {
      return m_up;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_ip == null ? 0 : m_ip.hashCode());
      hash = hash * 31 + (m_up == null ? 0 : m_up.hashCode());
      hash = hash * 31 + (m_groupNames == null ? 0 : m_groupNames.hashCode());

      return hash;
   }

   public boolean isUp() {
      return m_up != null && m_up.booleanValue();
   }

   @Override
   public void mergeAttributes(ServerStatus other) {
   }

   public ServerStatus setIp(String ip) {
      m_ip = ip;
      return this;
   }

   public ServerStatus setUp(Boolean up) {
      m_up = up;
      return this;
   }

}
