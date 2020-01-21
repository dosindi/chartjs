package com.byteowls.vaadin.chartjs.demo.ui.charts;

import java.util.ArrayList;
import java.util.List;

import com.byteowls.vaadin.chartjs.ChartJs;
import com.byteowls.vaadin.chartjs.config.PieChartConfig;
import com.byteowls.vaadin.chartjs.data.Dataset;
import com.byteowls.vaadin.chartjs.data.PieDataset;
import com.byteowls.vaadin.chartjs.demo.ui.AbstractChartView;
import com.byteowls.vaadin.chartjs.demo.ui.DemoUtils;
import com.byteowls.vaadin.chartjs.utils.ColorUtils;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;

@UIScope
@SpringView
public class AngledPieChartView extends AbstractChartView {

    private static final long serialVersionUID = 4894923343920891837L;

    @Override
    public Component getChart() {
        PieChartConfig config = new PieChartConfig();
        config
            .data()
                .labels("Red", "Green", "Yellow", "Grey", "Dark Grey")
                .addDataset(new PieDataset().label("Dataset 1")
                    .borderWidth(3)
                    .borderColor("#000")
                    .randomBackgroundColors(true))
                .and();

        config.
            options()
                .responsive(true)
                .rotation(30)
                .circumference(Math.PI / 3)
                .title()
                    .display(true)
                    .text("Chart.js Angled pie chart")
                    .and()
                .animation()
                    //.animateScale(true)
                    .animateRotate(true)
                    .and()
               .done();

        List<String> labels = config.data().getLabels();
        for (Dataset<?, ?> ds : config.data().getDatasets()) {
            PieDataset lds = (PieDataset) ds;
            List<Double> data = new ArrayList<>();
            for (int i = 0; i < labels.size(); i++) {
                data.add((double) (Math.round(Math.random() * 100)));
            }
            lds.dataAsList(data);
        }

        ChartJs chart = new ChartJs(config);
        chart.setJsLoggingEnabled(true);
        chart.addClickListener((a,b) -> DemoUtils.notification(a, b, config.data().getDatasets().get(a)));
        return chart;
    }

}
