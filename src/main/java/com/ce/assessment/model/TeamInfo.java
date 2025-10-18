package com.ce.assessment.model;

public class TeamInfo {
    private String name;
    private int numLicensedUsers;
    private int numUsedLicenses;

    public TeamInfo() { }

    public TeamInfo(String name, int numLicensedUsers, int numUsedLicenses) {
        this.name = name;
        this.numLicensedUsers = numLicensedUsers;
        this.numUsedLicenses = numUsedLicenses;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getNumLicensedUsers() {
        return numLicensedUsers;
    }
    public void setNumLicensedUsers(int numLicensedUsers) {
        this.numLicensedUsers = numLicensedUsers;
    }
    public int getNumUsedLicenses() {
        return numUsedLicenses;
    }
    public void setNumUsedLicenses(int numUsedLicenses) {
        this.numUsedLicenses = numUsedLicenses;
    }

    @Override
    public String toString() {
        return "TeamInfo{" +
                "name='" + name + '\'' +
                ", numLicensedUsers=" + numLicensedUsers +
                ", numUsedLicenses=" + numUsedLicenses +
                '}';
    }
}