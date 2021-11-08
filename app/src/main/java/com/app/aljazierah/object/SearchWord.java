package com.app.aljazierah.object;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class SearchWord {
    private String word_search,user_id;
    public SearchWord(String word_search,String user_id) {
        this.word_search = word_search;
        this.user_id = user_id;
    }
}
