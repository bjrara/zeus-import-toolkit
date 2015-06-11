package com.ctrip.zeus.model.entity;

import com.ctrip.zeus.model.BaseEntity;
import com.ctrip.zeus.model.IVisitor;

import java.util.ArrayList;
import java.util.List;

public class OpServerStatusReq extends BaseEntity<OpServerStatusReq> {
   private String m_operation;

   private List<IpAddresses> m_ipAddresseses = new ArrayList<IpAddresses>();

   public OpServerStatusReq() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitOpServerStatusReq(this);
   }

   public OpServerStatusReq addIpAddresses(IpAddresses ipAddresses) {
      m_ipAddresseses.add(ipAddresses);
      return this;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof OpServerStatusReq) {
         OpServerStatusReq _o = (OpServerStatusReq) obj;
         String operation = _o.getOperation();
         List<IpAddresses> ipAddresseses = _o.getIpAddresseses();
         boolean result = true;

         result &= (m_operation == operation || m_operation != null && m_operation.equals(operation));
         result &= (m_ipAddresseses == ipAddresseses || m_ipAddresseses != null && m_ipAddresseses.equals(ipAddresseses));

         return result;
      }

      return false;
   }

   public List<IpAddresses> getIpAddresseses() {
      return m_ipAddresseses;
   }

   public String getOperation() {
      return m_operation;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_operation == null ? 0 : m_operation.hashCode());
      hash = hash * 31 + (m_ipAddresseses == null ? 0 : m_ipAddresseses.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(OpServerStatusReq other) {
   }

   public OpServerStatusReq setOperation(String operation) {
      m_operation = operation;
      return this;
   }

}
