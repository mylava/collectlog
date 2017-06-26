package com.ruwe.collectlog.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lipengfei on 2017/5/31.
 */
@Deprecated
public class Version {
    private List<Integer> list;

    private Version() {
    }

    private Version(List<Integer> list) {
        this.list = list;
    }

    public static Version build() {
        List<Integer> list = new ArrayList();
        list.add(1);
        return new Version(list);
    }

    public Version increase() {
        list.set(list.size()-1,(Integer)list.get(list.size()-1)+1);
        return this;
    }

    public Version child() {
        list.add(1);
        return this;
    }

    public static void main(String[] args) {
        Version version= Version.build().increase();
        System.out.println(version.list);
    }

}
