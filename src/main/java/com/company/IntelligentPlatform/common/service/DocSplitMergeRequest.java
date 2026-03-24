package com.company.IntelligentPlatform.common.service;

import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

@Service
public class DocSplitMergeRequest<T extends ServiceEntityNode, Item extends ServiceEntityNode> {

    public interface IFilterDocToMerge<T extends ServiceEntityNode> {

        T filterData(T targetDoc, T docToMerge);

    }

    public interface ISetTargetDocItem<T extends ServiceEntityNode, Item extends ServiceEntityNode>{

         Item execute(Item docItemToMerge, T docToMerge);
    }

    private IFilterDocToMerge<T> filterDocToMerge;

    private ISetTargetDocItem<T, Item> setTargetDocItem;

    public DocSplitMergeRequest() {
        this.setDefFilterDocToMerge();
        this.setDefTargetDocItem();
    }

    public void setDefFilterDocToMerge(){
        this.filterDocToMerge = null;
    }

    public void setDefTargetDocItem(){
        this.setTargetDocItem = null;
    }

    public IFilterDocToMerge<T> getFilterDocToMerge() {
        return filterDocToMerge;
    }

    public void setFilterDocToMerge(IFilterDocToMerge<T> filterDocToMerge) {
        this.filterDocToMerge = filterDocToMerge;
    }

    public ISetTargetDocItem<T, Item> getSetTargetDocItem() {
        return setTargetDocItem;
    }

    public void setSetTargetDocItem(ISetTargetDocItem<T, Item> setTargetDocItem) {
        this.setTargetDocItem = setTargetDocItem;
    }
}
