package com.fpt.router.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.fpt.router.R;
import com.fpt.router.library.model.bus.BusImage;
import com.fpt.router.library.model.common.AutocompleteObject;

import java.util.List;

/**
 * Created by Nguyen Trung Nam on 10/3/2015.
 */
public class AutocompleteAdapter extends ArrayAdapter implements Filterable {
    public static enum  AutoCompleteType{
        HISTORY_TYPE,
        SEARCH_TYPE
    }

    public static AutoCompleteType autoCompleteType;
    private List<AutocompleteObject> resultList;

    public AutocompleteAdapter(Context context, List<AutocompleteObject> arrayList) {
        super(context, R.layout.list_item_autocomplete_search);
        this.resultList = arrayList;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_item_autocomplete_search, parent, false);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imgIcon);
        TextView textView = (TextView) rowView.findViewById(R.id.text1);
        if(autoCompleteType == AutoCompleteType.HISTORY_TYPE){
            imageView.setImageResource(R.drawable.ic_history_black_24dp);
        }else{
            imageView.setImageResource(R.drawable.ic_place_black_24dp);
        }
        textView.setText(resultList.get(position).getName());
        return rowView;
    }

    /*@Override
    public String getItem(int index) {
        return resultList.get(index).getName();
    }
*/
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
