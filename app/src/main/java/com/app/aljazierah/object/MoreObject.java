package com.app.aljazierah.object;

import java.util.ArrayList;

public class MoreObject {


    private String section_name;

    private ArrayList<MoreObjectData> moreObjectDataArrayList;


    public String getSection_name() {
        return section_name;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }

    public ArrayList<MoreObjectData> getMoreObjectDataArrayList() {
        return moreObjectDataArrayList;
    }

    public void setMoreObjectDataArrayList(ArrayList<MoreObjectData> moreObjectDataArrayList) {
        this.moreObjectDataArrayList = moreObjectDataArrayList;
    }

    public static  class MoreObjectData{

        private String section_child;


        public String getSection_child() {
            return section_child;
        }

        public void setSection_child(String section_child) {
            this.section_child = section_child;
        }
    }
}
