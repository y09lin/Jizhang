package com.huim.money.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.huim.money.R;
import com.huim.money.adapter.ShoppingAdapter;
import com.huim.money.bean.Shopping;
import com.huim.money.utils.DbShopping;
import com.huim.money.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class ShoppingList extends AppCompatActivity {

    private MaterialDialog dialog;
    private EditText edit_name,edit_price;
    private CheckBox check_pay;
    private String strMarket,strBank;

    private ListView list_shopping;
    private ShoppingAdapter adapter;
    private List<Shopping> beanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_shopping_list);
        list_shopping=(ListView)findViewById(R.id.list_shopping);
        beanList=new ArrayList<>();
        beanList.addAll(DbShopping.getShoppings(this));
        adapter=new ShoppingAdapter(this,beanList,R.layout.item_shopping);
        list_shopping.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shopping, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_shopping:
                addShopping();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView(View view){
        edit_name=(EditText)view.findViewById(R.id.edit_name);
        edit_price=(EditText)view.findViewById(R.id.edit_price);
        Spinner sp_market = (Spinner) view.findViewById(R.id.sp_market);
        Spinner sp_bank = (Spinner) view.findViewById(R.id.sp_bank);
        check_pay=(CheckBox)view.findViewById(R.id.check_pay);

        Resources res=getResources();
        final String markets[]=res.getStringArray(R.array.ShoppingMarkets);
        final String banks[]=res.getStringArray(R.array.Banks);

        sp_market.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,markets));
        sp_market.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strMarket=markets[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                strMarket=markets[0];
            }
        });

        sp_bank.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,banks));
        sp_bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strBank=banks[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                strBank=banks[0];
            }
        });
    }

    private void addShopping(){
        //
        if (dialog==null){
            View view= LayoutInflater.from(this).inflate(R.layout.view_add_shopping,null);
            initView(view);
            dialog=new MaterialDialog.Builder(this)
                    .autoDismiss(true).title(R.string.add_shopping_title)
                    .customView(view,false)
                    .negativeText(R.string.cancel).negativeColorRes(R.color.green)
                    .positiveText(R.string.sure).positiveColorRes(R.color.green)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                             save();
                        }
                    }).show();
        }else{
            edit_name.setText("");
            edit_price.setText("");
            dialog.show();
        }
    }

    private void save(){
        String name=edit_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)){
            ToastUtil.getIns().showToast(getString(R.string.err_name_empty));
            return;
        }
        if (TextUtils.isEmpty(strMarket)){
            ToastUtil.getIns().showToast(getString(R.string.err_market_empty));
            return;
        }
        if (TextUtils.isEmpty(strBank)){
            ToastUtil.getIns().showToast(getString(R.string.err_bank_empty));
            return;
        }
        String price=edit_price.getText().toString().trim();
        if (TextUtils.isEmpty(price)){
            ToastUtil.getIns().showToast(getString(R.string.err_price_empty));
            return;
        }

        try {
            float p=Float.parseFloat(price);
            String date= DateUtils.formatDateTime(this,System.currentTimeMillis(),DateUtils.FORMAT_SHOW_DATE);
            Log.d("Test",p +"  "+date);
            Shopping shopping=new Shopping(name,p,strMarket,date,strBank,check_pay.isChecked());
            DbShopping.saveItem(this,shopping);
            beanList.add(shopping);
            adapter.add(shopping);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }
}
