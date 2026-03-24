package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class NavigationElementUnion implements Comparable<NavigationElementUnion> {

    protected String uuid;

    protected String id;

    protected String groupID;

    protected String link;

    protected String label;

    protected int index;

    protected String elementTitle;

    protected String iconClass;

    protected boolean hasNext;

    protected boolean activeFlag = false;

    protected List<NavigationElementUnion> secondNavigationList = new ArrayList<NavigationElementUnion>();

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getId() {
        return id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getElementTitle() {
        return elementTitle;
    }

    public void setElementTitle(String elementTitle) {
        this.elementTitle = elementTitle;
    }

    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

    public List<NavigationElementUnion> getSecondNavigationList() {
        return secondNavigationList;
    }

    public void setSecondNavigationList(
            List<NavigationElementUnion> secondNavigationList) {
        this.secondNavigationList = secondNavigationList;
    }

    public boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public void addUpdateSecondNavigation(NavigationElementUnion secondElementUnion) {
        if (!this.secondNavigationList.contains(secondElementUnion)) {
            this.secondNavigationList.add(secondElementUnion);
        }
    }

    @Override
    public int compareTo(NavigationElementUnion other) {
        Integer thisIndex = Integer.valueOf(this.getIndex());
        Integer otherIndex = Integer.valueOf(other.getIndex());
        return thisIndex.compareTo(otherIndex);
    }

}
