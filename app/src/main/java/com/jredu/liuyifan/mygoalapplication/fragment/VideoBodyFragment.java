package com.jredu.liuyifan.mygoalapplication.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.jredu.liuyifan.mygoalapplication.R;
import com.jredu.liuyifan.mygoalapplication.adapter.NewsAdapter;
import com.jredu.liuyifan.mygoalapplication.entity.News;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoBodyFragment extends Fragment {
    ListView listView;
    List<News> list;
    News news;
    NewsAdapter newsAdapter;
    public interface dismiss{
        void setpopup(PopupWindow window);
    }


    public VideoBodyFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View v = inflater.inflate(R.layout.fragment_video_body, container, false);
        listView = (ListView) v.findViewById(R.id.list_item);
        list = new ArrayList<News>();
        news = new News("克强指出，中加务实合作基础良好，空间广阔，潜力巨大。","新浪网 23赞 2712评论 4小时前",R.drawable.icon,0);
        list.add(news);
        news = new News("李克强指出，中加务实合作基础良好，空间广阔，潜力巨大。","新浪网 23赞 2712评论 4小时前",R.drawable.icon,0);
        list.add(news);
        news = new News(R.drawable.dog,"李克强指出，中加务实合作基础良好，空间广阔，潜力巨大。","新浪网 23赞 2712评论 4小时前",R.drawable.icon,1);
        list.add(news);
        news = new News(R.drawable.dog,"李克强指出，中加务实合作基础良好，空间广阔，潜力巨大。","新浪网 23赞 2712评论 4小时前",R.drawable.icon,1);
        list.add(news);
        news = new News(R.drawable.dog,R.drawable.dog,R.drawable.dog,"明天下雨","新浪网 23赞 2712评论 4小时前",R.drawable.icon,3);
        list.add(news);
        news = new News("李克强指出，中加务实合作基础良好，空间广阔，潜力巨大。","新浪网 23赞 2712评论 4小时前",R.drawable.icon,0);
        list.add(news);
        news = new News("李克强指出，中加务实合作基础良好，空间广阔，潜力巨大。","新浪网 23赞 2712评论 4小时前",R.drawable.icon,0);
        list.add(news);
        news = new News(R.drawable.dog,"李克强指出，中加务实合作基础良好，空间广阔，潜力巨大。","新浪网 23赞 2712评论 4小时前",R.drawable.icon,1);
        list.add(news);
        news = new News(R.drawable.dog,"李克强指出，中加务实合作基础良好，空间广阔，潜力巨大。","新浪网 23赞 2712评论 4小时前",R.drawable.icon,1);
        list.add(news);
        news = new News(R.drawable.dog,R.drawable.dog,R.drawable.dog,"李克强指出，中加务实合作基础良好，空间广阔，潜力巨大。","新浪网 23赞 2712评论 4小时前",R.drawable.icon,3);
        list.add(news);
        news = new News("李克强指出，中加务实合作基础良好，空间广阔，潜力巨大。","新浪网 23赞 2712评论 4小时前",R.drawable.icon,0);
        list.add(news);
        news = new News("李克强指出，中加务实合作基础良好，空间广阔，潜力巨大。","新浪网 23赞 2712评论 4小时前",R.drawable.icon,0);
        list.add(news);
        news = new News(R.drawable.dog,"李克强指出，中加务实合作基础良好，空间广阔，潜力巨大。","新浪网 23赞 2712评论 4小时前",R.drawable.icon,1);
        list.add(news);
        news = new News(R.drawable.dog,"明天下雨","新浪网 23赞 2712评论 4小时前",R.drawable.icon,1);
        list.add(news);
        news = new News(R.drawable.dog,R.drawable.dog,R.drawable.dog,"李克强指出，中加务实合作基础良好，空间广阔，潜力巨大。","新浪网 23赞 2712评论 4小时前",R.drawable.icon,3);
        list.add(news);
        newsAdapter = new NewsAdapter(getActivity(),list);
        listView.setAdapter(newsAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),"hah",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                Toast.makeText(getActivity(),list.get(position).toString(),Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(getActivity())
                        .setTitle("删除")
                        .setMessage("确认删除")
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.remove(position);
                                newsAdapter.notifyDataSetChanged();
                            }
                        })
                        .show();
                return false;
            }
        });
        return v;

    }

}
