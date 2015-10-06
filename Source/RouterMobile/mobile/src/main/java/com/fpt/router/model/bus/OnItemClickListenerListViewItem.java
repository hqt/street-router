package com.fpt.router.model.bus;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.fpt.router.R;

/**
 * Created by asus on 10/5/2015.
 */
public class OnItemClickListenerListViewItem implements AdapterView.OnItemClickListener {

    TextView mTitle,mSubTitle;
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Context context = view.getContext();

        String title, subTitle;
        String name_1, country_1, birthday_1;
        mTitle = (TextView) view.findViewById(R.id.mTitle);
        title = mTitle.getText().toString();
        mSubTitle = (TextView) view.findViewById(R.id.mSubTitle);
        subTitle = mSubTitle.getText().toString();

        Toast.makeText(context, "Person Detail :" + title + "," + subTitle, Toast.LENGTH_LONG).show();
    }
}
