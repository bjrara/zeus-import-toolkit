package com.ctrip.zeus.model.entity;

import com.ctrip.zeus.model.BaseEntity;
import com.ctrip.zeus.model.IVisitor;

import java.util.ArrayList;
import java.util.List;

public class MemberAction extends BaseEntity<MemberAction> {
   private String m_groupName;

   private List<String> m_ips = new ArrayList<String>();

   public MemberAction() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitMemberAction(this);
   }

   public MemberAction addIp(String ip) {
      m_ips.add(ip);
      return this;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof MemberAction) {
         MemberAction _o = (MemberAction) obj;
         String groupName = _o.getGroupName();
         List<String> ips = _o.getIps();
         boolean result = true;

         result &= (m_groupName == groupName || m_groupName != null && m_groupName.equals(groupName));
         result &= (m_ips == ips || m_ips != null && m_ips.equals(ips));

         return result;
      }

      return false;
   }

   public String getGroupName() {
      return m_groupName;
   }

   public List<String> getIps() {
      return m_ips;
   }

   @Override
   public int hashCode() {
      int hash = 0;

      hash = hash * 31 + (m_groupName == null ? 0 : m_groupName.hashCode());
      hash = hash * 31 + (m_ips == null ? 0 : m_ips.hashCode());

      return hash;
   }

   @Override
   public void mergeAttributes(MemberAction other) {
   }

   public MemberAction setGroupName(String groupName) {
      m_groupName = groupName;
      return this;
   }

}
