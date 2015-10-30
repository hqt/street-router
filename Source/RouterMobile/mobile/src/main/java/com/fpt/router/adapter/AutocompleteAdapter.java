package com.fpt.router.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.fpt.router.library.model.common.AutocompleteObject;

import java.util.List;

/**
 * Created by Nguyen Trung Nam on 10/3/2015.
 */
public class AutocompleteAdapter extends ArrayAdapter implements Filterable {
    private List<AutocompleteObject> resultList;


    public AutocompleteAdapter(Context context, int textViewResourceId, List<AutocompleteObject> arrayList) {
        super(context, textViewResourceId);
        this.resultList = arrayList;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public String getItem(int index) {
        return resultList.get(index).getName();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    // Retrieve the autocomplete results.

                    // Assign the data to the FilterResults
                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }
}
