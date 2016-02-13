package com.alexmarken.navigator.my.university.util.Boxlists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alexmarken.navigator.my.university.R;

import java.util.ArrayList;


public class SearchAuditoryAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<SearchAuditoryListItemObject> objects;

    public SearchAuditoryAdapter(Context context, ArrayList<SearchAuditoryListItemObject> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;

        if (view == null)
            view = lInflater.inflate(R.layout.item_search_auditory, parent, false);

        TextView tvAuditoryItemNumber = (TextView) view.findViewById(R.id.tvAuditoryItemNumber);
        TextView tvAuditoryItemName = (TextView) view.findViewById(R.id.tvAuditoryItemName);
        TextView tvAuditoryItemType = (TextView) view.findViewById(R.id.tvAuditoryItemType);

        SearchAuditoryListItemObject item = getProduct(position);

        tvAuditoryItemNumber.setText(item.getCorp() + "-" + item.getAuditor());
        tvAuditoryItemName.setText(item.getName());
        tvAuditoryItemType.setText(item.getType());

        return view;
    }

    // товар по позиции
    SearchAuditoryListItemObject getProduct(int position) {
        return ((SearchAuditoryListItemObject) getItem(position));
    }

    // содержимое корзины
    ArrayList<SearchAuditoryListItemObject> getBox() {
        ArrayList<SearchAuditoryListItemObject> box = new ArrayList<SearchAuditoryListItemObject>();
        for (SearchAuditoryListItemObject p : objects) {
            box.add(p);
        }
        return box;
    }
}