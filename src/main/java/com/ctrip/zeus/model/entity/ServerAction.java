package com.ctrip.zeus.model.entity;

import com.ctrip.zeus.model.BaseEntity;
import com.ctrip.zeus.model.IVisitor;

import java.util.ArrayList;
import java.util.List;

public class ServerAction extends BaseEntity<ServerAction> {
   private String m_name;

   private List<String> m_ips = new ArrayList<String>();

   public ServerAction() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitServerAction(this);
   }

   public ServerAction addIp(String ip) {
      m_ips.add(ip);
      return this;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof ServerAction) {
         ServerAction _o = (ServerAction) obj;
         String name = _o.getName();
         List<String> ips = _o.getIps();
         boolean result = true;

         result &= (m_name == name || m_name != null && m_name.equals(name));
         result &= (m_ips == ips || m_ips != null && m_ips.equals(ips));

         return result;
      }

      return false;
   }

   public List<String> getIps() {
      return m_ips;
   }

   public String getName() {
      return m_name;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_name == null ? 0 : m_name.hashCode());
      hash = hash * 31 + (m_ips == null ? 0 : m_ips.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(ServerAction other) {
   }

   public ServerAction setName(String name) {
      m_name = name;
      return this;
   }

}
