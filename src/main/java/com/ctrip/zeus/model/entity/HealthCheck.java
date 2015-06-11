package com.ctrip.zeus.model.entity;

import com.ctrip.zeus.model.BaseEntity;
import com.ctrip.zeus.model.IVisitor;

public class HealthCheck extends BaseEntity<HealthCheck> {
   private Integer m_intervals;

   private Integer m_fails;

   private Integer m_passes;

   private String m_uri;

   public HealthCheck() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitHealthCheck(this);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof HealthCheck) {
         HealthCheck _o = (HealthCheck) obj;
         Integer intervals = _o.getIntervals();
         Integer fails = _o.getFails();
         Integer passes = _o.getPasses();
         String uri = _o.getUri();
         boolean result = true;

         result &= (m_intervals == intervals || m_intervals != null && m_intervals.equals(intervals));
         result &= (m_fails == fails || m_fails != null && m_fails.equals(fails));
         result &= (m_passes == passes || m_passes != null && m_passes.equals(passes));
         result &= (m_uri == uri || m_uri != null && m_uri.equals(uri));

         return result;
      }

      return false;
   }

   public Integer getFails() {
      return m_fails;
   }

   public Integer getIntervals() {
      return m_intervals;
   }

   public Integer getPasses() {
      return m_passes;
   }

   public String getUri() {
      return m_uri;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_intervals == null ? 0 : m_intervals.hashCode());
      hash = hash * 31 + (m_fails == null ? 0 : m_fails.hashCode());
      hash = hash * 31 + (m_passes == null ? 0 : m_passes.hashCode());
      hash = hash * 31 + (m_uri == null ? 0 : m_uri.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(HealthCheck other) {
      if (other.getIntervals() != null) {
         m_intervals = other.getIntervals();
      }

      if (other.getFails() != null) {
         m_fails = other.getFails();
      }

      if (other.getPasses() != null) {
         m_passes = other.getPasses();
      }
   }

   public HealthCheck setFails(Integer fails) {
      m_fails = fails;
      return this;
   }

   public HealthCheck setIntervals(Integer intervals) {
      m_intervals = intervals;
      return this;
   }

   public HealthCheck setPasses(Integer passes) {
      m_passes = passes;
      return this;
   }

   public HealthCheck setUri(String uri) {
      m_uri = uri;
      return this;
   }

}
