package com.ctrip.zeus.model.entity;

import com.ctrip.zeus.model.BaseEntity;
import com.ctrip.zeus.model.IVisitor;

public class Archive extends BaseEntity<Archive> {
   private Long m_id;

   private String m_content;

   private Integer m_version;

   public Archive() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitArchive(this);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof Archive) {
         Archive _o = (Archive) obj;
         Long id = _o.getId();
         String content = _o.getContent();
         Integer version = _o.getVersion();
         boolean result = true;

         result &= (m_id == id || m_id != null && m_id.equals(id));
         result &= (m_content == content || m_content != null && m_content.equals(content));
         result &= (m_version == version || m_version != null && m_version.equals(version));

         return result;
      }

      return false;
   }

   public String getContent() {
      return m_content;
   }

   public Long getId() {
      return m_id;
   }

   public Integer getVersion() {
      return m_version;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_id == null ? 0 : m_id.hashCode());
      hash = hash * 31 + (m_content == null ? 0 : m_content.hashCode());
      hash = hash * 31 + (m_version == null ? 0 : m_version.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(Archive other) {
   }

   public Archive setContent(String content) {
      m_content = content;
      return this;
   }

   public Archive setId(Long id) {
      m_id = id;
      return this;
   }

   public Archive setVersion(Integer version) {
      m_version = version;
      return this;
   }

}
