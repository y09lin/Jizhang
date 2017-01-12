package com.huim.money.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.UUID;

/**
 * 购物清单
 */
@Table(name = "shop")
public class Shopping implements Serializable{
    @Column(name = "id",isId = true)
    private String id;

    @Column(name = "gname")
    private String gName;

    @Column(name = "price")
    private float price;

    @Column(name = "market")
    private String market;

    @Column(name = "time")
    private String time;

    @Column(name = "pay")
    private String pay;

    @Column(name = "off")
    private String off;

    public Shopping(){}
    public Shopping(String n,float p,String m,String t, String b){
        new Shopping(n,p,m,t,b,false);
    }
    public Shopping(String n,float p,String m,String t, String b,boolean o){
        id= UUID.randomUUID()+"";
        gName=n;
        price=p;
        market=m;
        time=t;
        pay=b;
        off=Boolean.toString(o);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getOff() {
        return off;
    }

    public void setOff(String off) {
        this.off = off;
    }
}
