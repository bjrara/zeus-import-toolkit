package com.ctrip.zeus.model.entity;

import com.ctrip.zeus.model.BaseEntity;
import com.ctrip.zeus.model.IVisitor;

import java.util.ArrayList;
import java.util.List;

public class SlbList extends BaseEntity<SlbList> {
   private Integer m_total;

   private List<Slb> m_slbs = new ArrayList<Slb>();

   public SlbList() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitSlbList(this);
   }

   public SlbList addSlb(Slb slb) {
      m_slbs.add(slb);
      return this;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof SlbList) {
         SlbList _o = (SlbList) obj;
         Integer total = _o.getTotal();
         List<Slb> slbs = _o.getSlbs();
         boolean result = true;

         result &= (m_total == total || m_total != null && m_total.equals(total));
         result &= (m_slbs == slbs || m_slbs != null && m_slbs.equals(slbs));

         return result;
      }

      return false;
   }

   public List<Slb> getSlbs() {
      return m_slbs;
   }

   public Integer getTotal() {
      return m_total;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_total == null ? 0 : m_total.hashCode());
      hash = hash * 31 + (m_slbs == null ? 0 : m_slbs.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(SlbList other) {
   }

   public SlbList setTotal(Integer total) {
      m_total = total;
      return this;
   }

}
