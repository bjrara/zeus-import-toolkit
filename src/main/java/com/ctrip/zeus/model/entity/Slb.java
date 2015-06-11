package com.ctrip.zeus.model.entity;

import com.ctrip.zeus.model.BaseEntity;
import com.ctrip.zeus.model.IVisitor;

import java.util.ArrayList;
import java.util.List;

public class Slb extends BaseEntity<Slb> {
   private Long m_id;

   private String m_name;

   private Integer m_version;

   private String m_nginxBin;

   private String m_nginxConf;

   private Integer m_nginxWorkerProcesses;

   private String m_status;

   private List<Vip> m_vips = new ArrayList<Vip>();

   private List<SlbServer> m_slbServers = new ArrayList<SlbServer>();

   private List<VirtualServer> m_virtualServers = new ArrayList<VirtualServer>();

   public Slb() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitSlb(this);
   }

   public Slb addSlbServer(SlbServer slbServer) {
      m_slbServers.add(slbServer);
      return this;
   }

   public Slb addVip(Vip vip) {
      m_vips.add(vip);
      return this;
   }

   public Slb addVirtualServer(VirtualServer virtualServer) {
      m_virtualServers.add(virtualServer);
      return this;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof Slb) {
         Slb _o = (Slb) obj;
         Long id = _o.getId();
         String name = _o.getName();
         Integer version = _o.getVersion();
         String nginxBin = _o.getNginxBin();
         String nginxConf = _o.getNginxConf();
         Integer nginxWorkerProcesses = _o.getNginxWorkerProcesses();
         String status = _o.getStatus();
         List<Vip> vips = _o.getVips();
         List<SlbServer> slbServers = _o.getSlbServers();
         List<VirtualServer> virtualServers = _o.getVirtualServers();
         boolean result = true;

         result &= (m_id == id || m_id != null && m_id.equals(id));
         result &= (m_name == name || m_name != null && m_name.equals(name));
         result &= (m_version == version || m_version != null && m_version.equals(version));
         result &= (m_nginxBin == nginxBin || m_nginxBin != null && m_nginxBin.equals(nginxBin));
         result &= (m_nginxConf == nginxConf || m_nginxConf != null && m_nginxConf.equals(nginxConf));
         result &= (m_nginxWorkerProcesses == nginxWorkerProcesses || m_nginxWorkerProcesses != null && m_nginxWorkerProcesses.equals(nginxWorkerProcesses));
         result &= (m_status == status || m_status != null && m_status.equals(status));
         result &= (m_vips == vips || m_vips != null && m_vips.equals(vips));
         result &= (m_slbServers == slbServers || m_slbServers != null && m_slbServers.equals(slbServers));
         result &= (m_virtualServers == virtualServers || m_virtualServers != null && m_virtualServers.equals(virtualServers));

         return result;
      }

      return false;
   }

   public Long getId() {
      return m_id;
   }

   public String getName() {
      return m_name;
   }

   public String getNginxBin() {
      return m_nginxBin;
   }

   public String getNginxConf() {
      return m_nginxConf;
   }

   public Integer getNginxWorkerProcesses() {
      return m_nginxWorkerProcesses;
   }

   public List<SlbServer> getSlbServers() {
      return m_slbServers;
   }

   public String getStatus() {
      return m_status;
   }

   public Integer getVersion() {
      return m_version;
   }

   public List<Vip> getVips() {
      return m_vips;
   }

   public List<VirtualServer> getVirtualServers() {
      return m_virtualServers;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_id == null ? 0 : m_id.hashCode());
      hash = hash * 31 + (m_name == null ? 0 : m_name.hashCode());
      hash = hash * 31 + (m_version == null ? 0 : m_version.hashCode());
      hash = hash * 31 + (m_nginxBin == null ? 0 : m_nginxBin.hashCode());
      hash = hash * 31 + (m_nginxConf == null ? 0 : m_nginxConf.hashCode());
      hash = hash * 31 + (m_nginxWorkerProcesses == null ? 0 : m_nginxWorkerProcesses.hashCode());
      hash = hash * 31 + (m_status == null ? 0 : m_status.hashCode());
      hash = hash * 31 + (m_vips == null ? 0 : m_vips.hashCode());
      hash = hash * 31 + (m_slbServers == null ? 0 : m_slbServers.hashCode());
      hash = hash * 31 + (m_virtualServers == null ? 0 : m_virtualServers.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(Slb other) {
      if (other.getId() != null) {
         m_id = other.getId();
      }

      if (other.getName() != null) {
         m_name = other.getName();
      }

      if (other.getVersion() != null) {
         m_version = other.getVersion();
      }
   }

   public Slb setId(Long id) {
      m_id = id;
      return this;
   }

   public Slb setName(String name) {
      m_name = name;
      return this;
   }

   public Slb setNginxBin(String nginxBin) {
      m_nginxBin = nginxBin;
      return this;
   }

   public Slb setNginxConf(String nginxConf) {
      m_nginxConf = nginxConf;
      return this;
   }

   public Slb setNginxWorkerProcesses(Integer nginxWorkerProcesses) {
      m_nginxWorkerProcesses = nginxWorkerProcesses;
      return this;
   }

   public Slb setStatus(String status) {
      m_status = status;
      return this;
   }

   public Slb setVersion(Integer version) {
      m_version = version;
      return this;
   }

}
