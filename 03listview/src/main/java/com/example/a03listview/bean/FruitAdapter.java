package com.example.a03listview.bean;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a03listview.R;
import com.example.a03listview.adapter.Fruit;

import java.util.List;

/**
 * Created by chen on 2017/7/4.
 */

public class FruitAdapter extends ArrayAdapter<Fruit> {


    private int resourceid;

    public FruitAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Fruit> objects) {
        super(context, resource, objects);
        resourceid = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        /*
        Fruit fruit = getItem(position);


        View view = LayoutInflater.from(getContext()).inflate(resourceid, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.iv_fruit_item);
        TextView textView = (TextView) view.findViewById(R.id.tv_fruit_item);

        imageView.setImageResource(fruit.getImageid());
        textView.setText(fruit.getName());


        return view;
*/

        /**
         *优化view 二
         Fruit fruit = getItem(position);

         View view = null;

         if (convertView == null) {
         view = LayoutInflater.from(getContext()).inflate(resourceid, null);
         } else {
         view = convertView;
         }

         ImageView imageView = (ImageView) view.findViewById(R.id.iv_fruit_item);
         TextView textView = (TextView) view.findViewById(R.id.tv_fruit_item);

         imageView.setImageResource(fruit.getImageid());
         textView.setText(fruit.getName());
         return view;
         */

        /**
         *优化 三使用handler
         *虽然现在已经不会再重复去加载布局，
         但是每次在 getView()方法中还是会调用 View的 findViewById()方法来获取一次控件的实例。
         我们可以借助一个 ViewHolder 来对这部分性能进行优化，修改 FruitAdapter 中的代码，如下
         所示：
         */
        Fruit fruit = getItem(position);

        View view = null;
        ViewHolder viewholder;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceid, null);


            viewholder = new ViewHolder();
            viewholder.fruitImage = view.findViewById(R.id.iv_fruit_item);
            viewholder.fruitName = view.findViewById(R.id.tv_fruit_item);

            view.setTag(viewholder);//将viewholder 存储哦在view 中

        } else {
            view = convertView;
            viewholder = (ViewHolder) view.getTag();//重新获取viewholder


        }


        viewholder.fruitImage.setImageResource(fruit.getImageid());
        viewholder.fruitName.setText(fruit.getName());

        return view;


    }

    class ViewHolder {
        ImageView fruitImage;
        TextView fruitName;


    }
}
