package com.ctrip.zeus.model.entity;

import com.ctrip.zeus.model.BaseEntity;
import com.ctrip.zeus.model.IVisitor;

public class DyUpstreamOpsData extends BaseEntity<DyUpstreamOpsData> {
   private String m_upstreamName;

   private String m_upstreamCommands;

   public DyUpstreamOpsData() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitDyUpstreamOpsData(this);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof DyUpstreamOpsData) {
         DyUpstreamOpsData _o = (DyUpstreamOpsData) obj;
         String upstreamName = _o.getUpstreamName();
         String upstreamCommands = _o.getUpstreamCommands();
         boolean result = true;

         result &= (m_upstreamName == upstreamName || m_upstreamName != null && m_upstreamName.equals(upstreamName));
         result &= (m_upstreamCommands == upstreamCommands || m_upstreamCommands != null && m_upstreamCommands.equals(upstreamCommands));

         return result;
      }

      return false;
   }

   public String getUpstreamCommands() {
      return m_upstreamCommands;
   }

   public String getUpstreamName() {
      return m_upstreamName;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_upstreamName == null ? 0 : m_upstreamName.hashCode());
      hash = hash * 31 + (m_upstreamCommands == null ? 0 : m_upstreamCommands.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(DyUpstreamOpsData other) {
   }

   public DyUpstreamOpsData setUpstreamCommands(String upstreamCommands) {
      m_upstreamCommands = upstreamCommands;
      return this;
   }

   public DyUpstreamOpsData setUpstreamName(String upstreamName) {
      m_upstreamName = upstreamName;
      return this;
   }

}
