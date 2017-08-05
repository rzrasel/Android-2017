package com.rz.gridviewdynamicadapter;

/**
 * Created by Rz Rasel on 2017-08-02.
 */

public class ModelInit {
    private String title;
    private String description;

    public ModelInit() {
        //
    }

    public ModelInit(String argTitle, String argDescription) {
        this.title = argTitle;
        this.description = argDescription;
    }

    public void setTitle(String argTitle) {
        this.title = argTitle;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String argDescription) {
        this.description = argDescription;
    }
}