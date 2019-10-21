package ru.techpark.homework1;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

class DataSource {
    private final List<MyData> mData;

    private static DataSource sInstance;

    private static final int defaultSize = 100;

    private DataSource() {
        mData = new ArrayList<>();
        setSize(defaultSize);
    }

    List<MyData> getData() {
        return mData;
    }

    synchronized static DataSource getInstance() {
        if (sInstance == null) {
            sInstance = new DataSource();
        }
        return sInstance;
    }

    void addElem() {
        int i = mData.size();
        int color = i % 2 == 0 ? Color.RED : Color.BLUE;
        mData.add(new MyData(i + 1, color));
    }

    void setSize(int size) {
        for (int i = mData.size(); i < size; i++)
            addElem();
    }

    static class MyData {
        int mNumber;
        int mColor;

        MyData(int number, int color) {
            mNumber = number;
            mColor = color;
        }
    }
}

