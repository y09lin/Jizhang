package com.huim.money.adapter;

import android.content.Context;

import com.huim.money.R;
import com.huim.money.bean.Shopping;
import com.huim.money.superadapter.SuperAdapter;
import com.huim.money.superadapter.internal.SuperViewHolder;

import java.util.List;

public class ShoppingAdapter extends SuperAdapter<Shopping>{

    public ShoppingAdapter(Context context, List<Shopping> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Shopping item) {
        holder.setText(R.id.text_name,item.getgName());
        holder.setText(R.id.text_wp,item.getMarket()+ " " +item.getPrice()+"￥");
        holder.setText(R.id.text_bt,item.getPay()+ " " +item.getTime());
        holder.setChecked(R.id.check_pay,Boolean.getBoolean(item.getOff()));
    }
}
