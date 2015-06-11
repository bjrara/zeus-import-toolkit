package com.ctrip.zeus.model.entity;

import com.ctrip.zeus.model.BaseEntity;
import com.ctrip.zeus.model.IVisitor;

import java.util.ArrayList;
import java.util.List;

public class Group extends BaseEntity<Group> {
   private Long m_id;

   private String m_name;

   private String m_appId;

   private Integer m_version;

   private Boolean m_ssl;

   private List<GroupSlb> m_groupSlbs = new ArrayList<GroupSlb>();

   private HealthCheck m_healthCheck;

   private LoadBalancingMethod m_loadBalancingMethod;

   private List<GroupServer> m_groupServers = new ArrayList<GroupServer>();

   public Group() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitGroup(this);
   }

   public Group addGroupServer(GroupServer groupServer) {
      m_groupServers.add(groupServer);
      return this;
   }

   public Group addGroupSlb(GroupSlb groupSlb) {
      m_groupSlbs.add(groupSlb);
      return this;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof Group) {
         Group _o = (Group) obj;
         Long id = _o.getId();
         String name = _o.getName();
         String appId = _o.getAppId();
         Integer version = _o.getVersion();
         Boolean ssl = _o.getSsl();
         List<GroupSlb> groupSlbs = _o.getGroupSlbs();
         HealthCheck healthCheck = _o.getHealthCheck();
         LoadBalancingMethod loadBalancingMethod = _o.getLoadBalancingMethod();
         List<GroupServer> groupServers = _o.getGroupServers();
         boolean result = true;

         result &= (m_id == id || m_id != null && m_id.equals(id));
         result &= (m_name == name || m_name != null && m_name.equals(name));
         result &= (m_appId == appId || m_appId != null && m_appId.equals(appId));
         result &= (m_version == version || m_version != null && m_version.equals(version));
         result &= (m_ssl == ssl || m_ssl != null && m_ssl.equals(ssl));
         result &= (m_groupSlbs == groupSlbs || m_groupSlbs != null && m_groupSlbs.equals(groupSlbs));
         result &= (m_healthCheck == healthCheck || m_healthCheck != null && m_healthCheck.equals(healthCheck));
         result &= (m_loadBalancingMethod == loadBalancingMethod || m_loadBalancingMethod != null && m_loadBalancingMethod.equals(loadBalancingMethod));
         result &= (m_groupServers == groupServers || m_groupServers != null && m_groupServers.equals(groupServers));

         return result;
      }

      return false;
   }

   public String getAppId() {
      return m_appId;
   }

   public List<GroupServer> getGroupServers() {
      return m_groupServers;
   }

   public List<GroupSlb> getGroupSlbs() {
      return m_groupSlbs;
   }

   public HealthCheck getHealthCheck() {
      return m_healthCheck;
   }

   public Long getId() {
      return m_id;
   }

   public LoadBalancingMethod getLoadBalancingMethod() {
      return m_loadBalancingMethod;
   }

   public String getName() {
      return m_name;
   }

   public Boolean getSsl() {
      return m_ssl;
   }

   public Integer getVersion() {
      return m_version;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_id == null ? 0 : m_id.hashCode());
      hash = hash * 31 + (m_name == null ? 0 : m_name.hashCode());
      hash = hash * 31 + (m_appId == null ? 0 : m_appId.hashCode());
      hash = hash * 31 + (m_version == null ? 0 : m_version.hashCode());
      hash = hash * 31 + (m_ssl == null ? 0 : m_ssl.hashCode());
      hash = hash * 31 + (m_groupSlbs == null ? 0 : m_groupSlbs.hashCode());
      hash = hash * 31 + (m_healthCheck == null ? 0 : m_healthCheck.hashCode());
      hash = hash * 31 + (m_loadBalancingMethod == null ? 0 : m_loadBalancingMethod.hashCode());
      hash = hash * 31 + (m_groupServers == null ? 0 : m_groupServers.hashCode());

      return hash;
   }

   public boolean isSsl() {
      return m_ssl != null && m_ssl.booleanValue();
   }

   @Override
   public void mergeAttributes(Group other) {
      if (other.getId() != null) {
         m_id = other.getId();
      }

      if (other.getName() != null) {
         m_name = other.getName();
      }

      if (other.getAppId() != null) {
         m_appId = other.getAppId();
      }

      if (other.getVersion() != null) {
         m_version = other.getVersion();
      }

      if (other.getSsl() != null) {
         m_ssl = other.getSsl();
      }
   }

   public Group setAppId(String appId) {
      m_appId = appId;
      return this;
   }

   public Group setHealthCheck(HealthCheck healthCheck) {
      m_healthCheck = healthCheck;
      return this;
   }

   public Group setId(Long id) {
      m_id = id;
      return this;
   }

   public Group setLoadBalancingMethod(LoadBalancingMethod loadBalancingMethod) {
      m_loadBalancingMethod = loadBalancingMethod;
      return this;
   }

   public Group setName(String name) {
      m_name = name;
      return this;
   }

   public Group setSsl(Boolean ssl) {
      m_ssl = ssl;
      return this;
   }

   public Group setVersion(Integer version) {
      m_version = version;
      return this;
   }

}
