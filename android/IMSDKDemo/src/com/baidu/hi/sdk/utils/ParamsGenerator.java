package com.baidu.hi.sdk.utils;

import java.util.HashMap;
import java.util.Map;

import com.baidu.hi.sdk.Constant;

public class ParamsGenerator {

    /**
     * Create a new group by the owner
     */
    public static Map<String, String> getCreateGroupParameters(String ownerId, String groupName, String groupDesc) {
        Map<String, String> paraMap = new HashMap<String, String>();
        paraMap.put(Constant.ACTION, Constant.CREATE);
        paraMap.put(Constant.HOSTID, ownerId);
        paraMap.put(Constant.NAME, groupName);
        paraMap.put(Constant.DESC, groupDesc);
        return paraMap;
    }

    /**
     * Dismiss a already existed group by its owner
     */
    public static Map<String, String> getDismissGroupParameters(String ownerId, String gid) {
        Map<String, String> paraMap = new HashMap<String, String>();
        paraMap.put(Constant.ACTION, Constant.DISMISS);
        paraMap.put(Constant.HOSTID, ownerId);
        paraMap.put(Constant.GID, gid);
        return paraMap;
    }

    /**
     * Add a new member into a already existed group by its owner
     */
    public static Map<String, String> getAddGroupMemberParameters(String ownerId, String targetUserId, String gid) {
        Map<String, String> paraMap = new HashMap<String, String>();
        paraMap.put(Constant.ACTION, Constant.ADDMEMBEBER);
        paraMap.put(Constant.HOSTID, ownerId);
        paraMap.put(Constant.UID, targetUserId);
        paraMap.put(Constant.GID, gid);
        return paraMap;
    }

    /**
     * Delete a already existed group member from its group by its owner
     */
    public static Map<String, String> getDeleteGroupMemberParameters(String ownerId, String targetUserId, String gid) {
        Map<String, String> paraMap = new HashMap<String, String>();
        paraMap.put(Constant.ACTION, Constant.DELMEMBEBER);
        paraMap.put(Constant.HOSTID, ownerId);
        paraMap.put(Constant.UID, targetUserId);
        paraMap.put(Constant.GID, gid);
        return paraMap;
    }

    /**
     * Query group member list from its group by one of those group members
     */
    public static Map<String, String> getGroupMembersParameters(String groupMemberId, String gid) {
        Map<String, String> paraMap = new HashMap<String, String>();
        paraMap.put(Constant.ACTION, Constant.QUERYMEMBER);
        paraMap.put(Constant.UID, groupMemberId);
        paraMap.put(Constant.GID, gid);
        return paraMap;
    }

    /**
     * Query all groups of a user
     */
    public static Map<String, String> getGroupsParameters(String groupMemberId) {
        Map<String, String> paraMap = new HashMap<String, String>();
        paraMap.put(Constant.ACTION, Constant.QUERYGROUP);
        paraMap.put(Constant.UID, groupMemberId);
        return paraMap;
    }

    /**
     * Add a new member into a already existed group by its owner
     */
    public static Map<String, String> getAddGroupParameters(String targetUserId, String gid) {
        Map<String, String> paraMap = new HashMap<String, String>();
        paraMap.put(Constant.ACTION, Constant.ADDMEMBEBER);
        paraMap.put(Constant.UID, targetUserId);
        paraMap.put(Constant.GID, gid);
        return paraMap;
    }
}
