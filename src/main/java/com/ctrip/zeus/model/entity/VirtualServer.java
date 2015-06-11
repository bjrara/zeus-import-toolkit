package com.ctrip.zeus.model.entity;

import com.ctrip.zeus.model.BaseEntity;
import com.ctrip.zeus.model.IVisitor;

import java.util.ArrayList;
import java.util.List;

public class VirtualServer extends BaseEntity<VirtualServer> {
   private String m_name;

   private Long m_id;

   private Boolean m_ssl;

   private String m_port;

   private List<Domain> m_domains = new ArrayList<Domain>();

   public VirtualServer() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitVirtualServer(this);
   }

   public VirtualServer addDomain(Domain domain) {
      m_domains.add(domain);
      return this;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof VirtualServer) {
         VirtualServer _o = (VirtualServer) obj;
         String name = _o.getName();
         Long id = _o.getId();
         Boolean ssl = _o.getSsl();
         String port = _o.getPort();
         List<Domain> domains = _o.getDomains();
         boolean result = true;

         result &= (m_name == name || m_name != null && m_name.equals(name));
         result &= (m_id == id || m_id != null && m_id.equals(id));
         result &= (m_ssl == ssl || m_ssl != null && m_ssl.equals(ssl));
         result &= (m_port == port || m_port != null && m_port.equals(port));
         result &= (m_domains == domains || m_domains != null && m_domains.equals(domains));

         return result;
      }

      return false;
   }

   public List<Domain> getDomains() {
      return m_domains;
   }

   public Long getId() {
      return m_id;
   }

   public String getName() {
      return m_name;
   }

   public String getPort() {
      return m_port;
   }

   public Boolean getSsl() {
      return m_ssl;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_name == null ? 0 : m_name.hashCode());
      hash = hash * 31 + (m_id == null ? 0 : m_id.hashCode());
      hash = hash * 31 + (m_ssl == null ? 0 : m_ssl.hashCode());
      hash = hash * 31 + (m_port == null ? 0 : m_port.hashCode());
      hash = hash * 31 + (m_domains == null ? 0 : m_domains.hashCode());

      return hash;
   }

   public boolean isSsl() {
      return m_ssl != null && m_ssl.booleanValue();
   }

   @Override
   public void mergeAttributes(VirtualServer other) {
      if (other.getName() != null) {
         m_name = other.getName();
      }

      if (other.getId() != null) {
         m_id = other.getId();
      }

      if (other.getSsl() != null) {
         m_ssl = other.getSsl();
      }
   }

   public VirtualServer setId(Long id) {
      m_id = id;
      return this;
   }

   public VirtualServer setName(String name) {
      m_name = name;
      return this;
   }

   public VirtualServer setPort(String port) {
      m_port = port;
      return this;
   }

   public VirtualServer setSsl(Boolean ssl) {
      m_ssl = ssl;
      return this;
   }

}
