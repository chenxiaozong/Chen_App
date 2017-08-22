package com.example.a12_materialdesign.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a12_materialdesign.Fruit;
import com.example.a12_materialdesign.FruitActivity;
import com.example.a12_materialdesign.R;

import java.util.List;

/**
 * Created by chen on 2017/8/22.
 *
 * Recycleview 的adapter
 */

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.FruitViewHolder> {


    private Context context;
    private List<Fruit> fruitList;

    public FruitAdapter(Context context, List<Fruit> fruitList) {
        this.context = context;
        this.fruitList = fruitList;
    }


    @Override
    public FruitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }

        // View view = View.inflate(context,R.layout.item_fruit_recyclerview,null);
        View view = LayoutInflater.from(context).inflate(R.layout.item_fruit_recyclerview, parent, false);

        final FruitViewHolder viewHolder = new FruitViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FruitViewHolder holder, final int position) {

        //显示item信息
        Fruit fruit = fruitList.get(position);

        //设置水果 name
        holder.textView.setText(fruit.getName());

        //设置imaeg --使用glide
        Glide.with(context).load(fruit.getImageId()).into(holder.imageView);



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = fruitList.get(position).getName();
                int imgId = fruitList.get(position).getImageId();

                Intent intent = new Intent(context, FruitActivity.class);
                intent.putExtra(FruitActivity.FRUIT_NAME,name);
                intent.putExtra(FruitActivity.FRUIT_IMAGE_ID,imgId);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return fruitList==null?0:fruitList.size();
    }

    static class FruitViewHolder extends RecyclerView.ViewHolder {

        CardView cardView ;
        ImageView imageView;
        TextView textView;

        public FruitViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView;
            imageView = cardView.findViewById(R.id.iv_cardview_fruit_img);
            textView = cardView.findViewById(R.id.tv_cardview_fruit_name);

        }
    }

}

