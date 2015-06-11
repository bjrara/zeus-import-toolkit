package com.ctrip.zeus.model.entity;

import com.ctrip.zeus.model.BaseEntity;
import com.ctrip.zeus.model.IVisitor;

public class LoadBalancingMethod extends BaseEntity<LoadBalancingMethod> {
   private String m_type;

   private String m_value;

   public LoadBalancingMethod() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitLoadBalancingMethod(this);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof LoadBalancingMethod) {
         LoadBalancingMethod _o = (LoadBalancingMethod) obj;
         String type = _o.getType();
         String value = _o.getValue();
         boolean result = true;

         result &= (m_type == type || m_type != null && m_type.equals(type));
         result &= (m_value == value || m_value != null && m_value.equals(value));

         return result;
      }

      return false;
   }

   public String getType() {
      return m_type;
   }

   public String getValue() {
      return m_value;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_type == null ? 0 : m_type.hashCode());
      hash = hash * 31 + (m_value == null ? 0 : m_value.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(LoadBalancingMethod other) {
      if (other.getType() != null) {
         m_type = other.getType();
      }
   }

   public LoadBalancingMethod setType(String type) {
      m_type = type;
      return this;
   }

   public LoadBalancingMethod setValue(String value) {
      m_value = value;
      return this;
   }

}
