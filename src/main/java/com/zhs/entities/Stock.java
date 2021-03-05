package com.zhs.entities;

import java.util.List;

public class Stock {
    private String code;
    private String name;
    private List<MAs> mas;
    private List<Kdj> kdj;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Kdj> getKdj() {
        return kdj;
    }

    public void setKdj(List<Kdj> kdj) {
        this.kdj = kdj;
    }

    public List<MAs> getMas() {
        return mas;
    }

    public void setMas(List<MAs> mas) {
        this.mas = mas;
    }

    public Stock(String code, List<Kdj> kdj, List<MAs> masList) {
        this.code = code;
        this.kdj = kdj;
        this.mas = masList;
    }

    public Stock(String code){
        this.code = code;
    }

    public int getDayCount(){
        if (mas == null && kdj == null) return 0;
        if(mas.size() == kdj.size())
            return mas.size();
        else
            return Math.max(mas.size(), kdj.size());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        Stock stock = (Stock)obj;
        if(this == stock){
            return true;
        }else{
            return (this.code.equals(stock.code));
        }
    }

    @Override
    public int hashCode() {
        int hashno = 7;
        hashno = 13*hashno+(code == null?0:code.hashCode());
        return hashno;
    }
}
