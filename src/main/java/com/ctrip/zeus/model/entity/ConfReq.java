package com.ctrip.zeus.model.entity;

import com.ctrip.zeus.model.BaseEntity;
import com.ctrip.zeus.model.IVisitor;

import java.util.ArrayList;
import java.util.List;

public class ConfReq extends BaseEntity<ConfReq> {
   private List<ConfSlbName> m_confSlbNames = new ArrayList<ConfSlbName>();

   private List<ConfGroupName> m_confGroupNames = new ArrayList<ConfGroupName>();

   public ConfReq() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitConfReq(this);
   }

   public ConfReq addConfGroupName(ConfGroupName confGroupName) {
      m_confGroupNames.add(confGroupName);
      return this;
   }

   public ConfReq addConfSlbName(ConfSlbName confSlbName) {
      m_confSlbNames.add(confSlbName);
      return this;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof ConfReq) {
         ConfReq _o = (ConfReq) obj;
         List<ConfSlbName> confSlbNames = _o.getConfSlbNames();
         List<ConfGroupName> confGroupNames = _o.getConfGroupNames();
         boolean result = true;

         result &= (m_confSlbNames == confSlbNames || m_confSlbNames != null && m_confSlbNames.equals(confSlbNames));
         result &= (m_confGroupNames == confGroupNames || m_confGroupNames != null && m_confGroupNames.equals(confGroupNames));

         return result;
      }

      return false;
   }

   public List<ConfGroupName> getConfGroupNames() {
      return m_confGroupNames;
   }

   public List<ConfSlbName> getConfSlbNames() {
      return m_confSlbNames;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_confSlbNames == null ? 0 : m_confSlbNames.hashCode());
      hash = hash * 31 + (m_confGroupNames == null ? 0 : m_confGroupNames.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(ConfReq other) {
   }

}
