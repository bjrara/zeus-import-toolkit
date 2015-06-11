package com.ctrip.zeus.model.entity;

import com.ctrip.zeus.model.BaseEntity;
import com.ctrip.zeus.model.IVisitor;

import java.util.ArrayList;
import java.util.List;

public class OpMemberStatusReq extends BaseEntity<OpMemberStatusReq> {
   private String m_operation;

   private List<IpGroupname> m_ipGroupnames = new ArrayList<IpGroupname>();

   public OpMemberStatusReq() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitOpMemberStatusReq(this);
   }

   public OpMemberStatusReq addIpGroupname(IpGroupname ipGroupname) {
      m_ipGroupnames.add(ipGroupname);
      return this;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof OpMemberStatusReq) {
         OpMemberStatusReq _o = (OpMemberStatusReq) obj;
         String operation = _o.getOperation();
         List<IpGroupname> ipGroupnames = _o.getIpGroupnames();
         boolean result = true;

         result &= (m_operation == operation || m_operation != null && m_operation.equals(operation));
         result &= (m_ipGroupnames == ipGroupnames || m_ipGroupnames != null && m_ipGroupnames.equals(ipGroupnames));

         return result;
      }

      return false;
   }

   public List<IpGroupname> getIpGroupnames() {
      return m_ipGroupnames;
   }

   public String getOperation() {
      return m_operation;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_operation == null ? 0 : m_operation.hashCode());
      hash = hash * 31 + (m_ipGroupnames == null ? 0 : m_ipGroupnames.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(OpMemberStatusReq other) {
   }

   public OpMemberStatusReq setOperation(String operation) {
      m_operation = operation;
      return this;
   }

}
