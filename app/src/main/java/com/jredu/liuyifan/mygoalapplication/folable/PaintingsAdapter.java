package com.jredu.liuyifan.mygoalapplication.folable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexvasilkov.android.commons.adapters.ItemsAdapter;
import com.alexvasilkov.android.commons.utils.Views;
import com.jredu.liuyifan.mygoalapplication.R;

import java.util.ArrayList;

/**
 * Created by DELL on 2016/10/19.
 */
public class PaintingsAdapter extends ItemsAdapter<Painting> implements View.OnClickListener {

    public PaintingsAdapter(Context context ,ArrayList<Painting> mList) {
        super(context);
        setItemsList(mList);
    }

    @Override
    protected View createView(Painting item, int pos, ViewGroup parent, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        ViewHolder vh = new ViewHolder();
        vh.image = Views.find(view, R.id.list_item_image);
        vh.image.setOnClickListener(this);
        vh.title = Views.find(view, R.id.list_item_title);
        view.setTag(vh);

        return view;
    }

    @Override
    protected void bindView(Painting item, int pos, View convertView) {
        ViewHolder vh = (ViewHolder) convertView.getTag();
        vh.image.setTag(R.id.list_item_image, item);
        GlideHelper.loadPaintingImage(vh.image, item);
        vh.title.setText(item.getTitle());
    }

    @Override
    public void onClick(View view) {
        Painting item = (Painting) view.getTag(R.id.list_item_image);
        if (view.getContext() instanceof UnfoldableDetailsActivity) {
            ((UnfoldableDetailsActivity) view.getContext()).openDetails(view, item);
        }
    }

    private static class ViewHolder {
        ImageView image;
        TextView title;
    }
}
