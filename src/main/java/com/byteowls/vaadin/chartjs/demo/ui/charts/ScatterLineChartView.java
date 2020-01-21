package com.byteowls.vaadin.chartjs.demo.ui.charts;

import com.byteowls.vaadin.chartjs.ChartJs;
import com.byteowls.vaadin.chartjs.config.ScatterChartConfig;
import com.byteowls.vaadin.chartjs.data.Dataset;
import com.byteowls.vaadin.chartjs.data.ScatterDataset;
import com.byteowls.vaadin.chartjs.demo.ui.AbstractChartView;
import com.byteowls.vaadin.chartjs.demo.ui.DemoUtils;
import com.byteowls.vaadin.chartjs.options.InteractionMode;
import com.byteowls.vaadin.chartjs.options.Position;
import com.byteowls.vaadin.chartjs.options.scale.Axis;
import com.byteowls.vaadin.chartjs.options.scale.LinearScale;
import com.byteowls.vaadin.chartjs.utils.ColorUtils;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@UIScope
@SpringView
public class ScatterLineChartView extends AbstractChartView {

    private static final long serialVersionUID = -4668420742225695694L;

    @Override
    public Component getChart() {
        ScatterChartConfig config = new ScatterChartConfig();
        config
                .data()
                .addDataset(new ScatterDataset().label("My First dataset").xAxisID("x-axis-1").yAxisID("y-axis-1"))
                .addDataset(new ScatterDataset().label("My Second  dataset").xAxisID("x-axis-1").yAxisID("y-axis-2"))
                .and();
        config.
                options()
                .responsive(true)
                .hover()
                .mode(InteractionMode.NEAREST)
                .intersect(false)
                .and()
                .title()
                .display(true)
                .text("Chart.js Scatter Chart - Multi Axis")
                .and()
                .scales()
                .add(Axis.X, new LinearScale().position(Position.BOTTOM).gridLines().zeroLineColor("rgba(0,0,0,1)").and())
                .add(Axis.Y, new LinearScale().display(true).position(Position.LEFT).id("y-axis-1"))
                .add(Axis.Y, new LinearScale().display(true).position(Position.RIGHT).id("y-axis-2").ticks().reverse(true).and().gridLines().drawOnChartArea(false).and())
                .and()
                .done();

        for (Dataset<?, ?> ds : config.data().getDatasets()) {
            ScatterDataset lds = (ScatterDataset) ds;
            lds.borderColor(ColorUtils.randomColor(.4));
            lds.backgroundColor(ColorUtils.randomColor(.1));
            lds.pointBorderColor(ColorUtils.randomColor(.7));
            lds.pointBackgroundColor(ColorUtils.randomColor(.5));
            lds.pointBorderWidth(1);
            for (int i = 0; i < 7; i++) {
                lds.addData(DemoUtils.randomScalingFactor(), DemoUtils.randomScalingFactor());
            }
        }

        ChartJs chart = new ChartJs(config);
        chart.setJsLoggingEnabled(true);
        chart.addClickListener((a, b)
                -> DemoUtils.notification(a, b, config.data().getDatasets().get(a)));

        Button button = new Button("Generate Pdf");
        button.addClickListener(e -> {
            try {
                generatePDFFromHTML("C:\\PORTER\\test.html");
            } catch (DocumentException ex) {
                Logger.getLogger(ScatterLineChartView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ScatterLineChartView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return new VerticalLayout(chart, button);
    }

    private static void generatePDFFromHTML(String filename) throws FileNotFoundException, DocumentException, IOException {

        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:\\PORTER\\html.pdf"));
        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(filename));
        document.close();
        Notification.show("Finished...",Notification.Type.TRAY_NOTIFICATION);
    }

}
