package com.tnd.myapplication;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.gsls.gt.GT;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@GT.Annotations.GT_AnnotationActivity(R.layout.activity_main)
public class MainActivity extends GT.GT_Activity.AnnotationActivity {

    @GT.Annotations.GT_View(R.id.lineChart)
    private LineChart lineChart;
    private List<List<Entry>> lists = new ArrayList<>();

    @Override
    protected void initView(Bundle savedInstanceState) {
        build(this);
        for (int i = 0; i < 3; i++) {
            lists.add(new ArrayList<>());
        }

        initData(9999999);

    }


    private void initData(int max) {
        //循环添加设置x轴和y轴的点

        GT.Thread.runJava(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < max; i++) {

                    LineData lineData = new LineData();// //线的总管理

                    for (int a = 0; a < lists.size(); a++) {
                        lists.get(a).add(new Entry(i, new Random().nextInt(1600) + 800 + a));
                        LineDataSet one = new LineDataSet(lists.get(a), "One");//将数据赋值到你的线条上
                        one.setColor(Color.parseColor("#67BCFF"));//设置线的颜色
                        one.setDrawCircles(false);//设置是否绘制点，默认是true
                        one.setMode(LineDataSet.Mode.LINEAR);//设置Mode为直线，这也是默认的Mode
                        lineData.addDataSet(one);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lineChart.setData(lineData);//把线条设置给你的lineChart上
                            lineChart.notifyDataSetChanged();
                            lineChart.invalidate();

                        }
                    });

                    for (int a = 0; a < lists.size(); a++) {
                        if (lists.get(a).size() > 100) {
                            lists.get(a).remove(0);
                        }
                    }

                    //延时时间
                    GT.Thread.sleep(100);

                }
            }
        });


    }

}