package com.company.IntelligentPlatform.common.service;

public class NavigationGroupUnion implements Comparable<NavigationGroupUnion> {

    protected String id;

    protected String defaultElementID;

    protected String label;

    protected int displayIndex;

    protected String link;

    protected String groupTitle;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDefaultElementID() {
        return defaultElementID;
    }

    public void setDefaultElementID(String defaultElementID) {
        this.defaultElementID = defaultElementID;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getDisplayIndex() {
        return displayIndex;
    }

    public void setDisplayIndex(int displayIndex) {
        this.displayIndex = displayIndex;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    @Override
    public int compareTo(NavigationGroupUnion other) {
        Integer thisIndex = Integer.valueOf(this.getDisplayIndex());
        Integer otherIndex = Integer.valueOf(other.getDisplayIndex());
        return thisIndex.compareTo(otherIndex);
    }

}
