package com.progetto.animeuniverse.ui.main;

import com.progetto.animeuniverse.model.Anime;

import java.util.List;

public class ParentItem {
    private String ParentItemTitle;
    private List<Anime> ChildItemList;

    // Constructor of the class
    // to initialize the variables
    public ParentItem(
            String ParentItemTitle,
            List<Anime> ChildItemList)
    {

        this.ParentItemTitle = ParentItemTitle;
        this.ChildItemList = ChildItemList;
    }

    public String getParentItemTitle()
    {
        return ParentItemTitle;
    }

    public void setParentItemTitle(
            String parentItemTitle)
    {
        ParentItemTitle = parentItemTitle;
    }

    public List<Anime> getChildItemList()
    {
        return ChildItemList;
    }

    public void setChildItemList(
            List<Anime> childItemList)
    {
        ChildItemList = childItemList;
    }
}
