package com.ctrip.zeus.model.entity;

import com.ctrip.zeus.model.BaseEntity;
import com.ctrip.zeus.model.IVisitor;

public class NginxConfServerData extends BaseEntity<NginxConfServerData> {
   private Long m_vsId;

   private String m_content;

   public NginxConfServerData() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitNginxConfServerData(this);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof NginxConfServerData) {
         NginxConfServerData _o = (NginxConfServerData) obj;
         Long vsId = _o.getVsId();
         String content = _o.getContent();
         boolean result = true;

         result &= (m_vsId == vsId || m_vsId != null && m_vsId.equals(vsId));
         result &= (m_content == content || m_content != null && m_content.equals(content));

         return result;
      }

      return false;
   }

   public String getContent() {
      return m_content;
   }

   public Long getVsId() {
      return m_vsId;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_vsId == null ? 0 : m_vsId.hashCode());
      hash = hash * 31 + (m_content == null ? 0 : m_content.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(NginxConfServerData other) {
   }

   public NginxConfServerData setContent(String content) {
      m_content = content;
      return this;
   }

   public NginxConfServerData setVsId(Long vsId) {
      m_vsId = vsId;
      return this;
   }

}
