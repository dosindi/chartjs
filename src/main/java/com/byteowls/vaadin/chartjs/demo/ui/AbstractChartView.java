package com.byteowls.vaadin.chartjs.demo.ui;

import com.vaadin.external.org.slf4j.Logger;
import com.vaadin.external.org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.annotation.PostConstruct;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

public abstract class AbstractChartView extends VerticalLayout implements ChartView {

    private static final long serialVersionUID = -1280497201872048972L;
    
    private Logger log = LoggerFactory.getLogger(AbstractChartView.class);

    @PostConstruct
    public void postConstruct() {
        setSizeFull();
        setMargin(true);
        Component layout = getChart();
        layout.setWidth(100, Unit.PERCENTAGE);
        addComponent(layout);
        setComponentAlignment(layout, Alignment.MIDDLE_CENTER);
    }

    @Override
    public String getSource() {
        // thanks to https://github.com/johndevs/dragdroplayouts/blob/master/demo/src/main/java/fi/jasoft/dragdroplayouts/demo/DemoView.java#L41
        String path = getClass().getCanonicalName().replaceAll("\\.", "/") + ".java";
        InputStream in = null;
        try {
            if ("dev".equals(System.getProperty("profile"))) {
                File f = new File(System.getProperty("user.dir") + "/src/main/java/"+path);
                in = new FileInputStream(f);
                log.info("dev:"+path);
            } else {
                File f = new File(System.getProperty("user.dir") + "/src/main/java/"+path);
                in = new FileInputStream(f);
//                in = getClass().getClassLoader().getResourceAsStream(path);
                log.info("class:"+path);
            }

//            log.info("path:"+path);
            
            if (in != null) {

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                    boolean inCodeBlock = false;

                    StringBuilder codelines = new StringBuilder();
                    String line = reader.readLine();
                    while (line != null) {
                        if (line.contains("public Component getChart() {")) {
                            inCodeBlock = true;
                        } else if (line.contains("return chart;")) {
                            inCodeBlock = false;
                        } else if (inCodeBlock) {
                            codelines.append(line);
                            codelines.append("\n");
                        }
                        line = reader.readLine();
                    }
                    return codelines.toString();
                } catch (Exception ignore) {}
            }
        } catch (Exception ignore) {

            log.info(ignore.toString());
            
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception ignored) { }
        }
        return null;
    }

    @Override
    public void enter(ViewChangeEvent event) {

    }

}
