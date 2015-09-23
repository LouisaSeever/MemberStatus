package com.louisaseever.memberstatus;


import java.util.ArrayList;

public abstract class MemberDataSource {
    abstract public void addMember(Member newMember) throws Exception;
    abstract public void removeMember(String name) throws Exception;
    abstract public void updateMember(String name, Member updatedMember) throws Exception;
    abstract public ArrayList<Member> getMembers();
    abstract public Member getMember(String name);
    abstract public void rebuild() throws Exception;
    abstract public void clear() throws Exception;
    abstract public void load() throws Exception;
    abstract public void save() throws Exception;

    abstract public void addCompletedAssignmentToMember(int memberId, int assignmentId, int points) throws Exception;
    abstract public ArrayList<CompletedAssignment> getCompletedAssignments(int memberId) throws Exception;

//    abstract public String toString();
    abstract public void fromString(String  input)throws Exception ;
}
