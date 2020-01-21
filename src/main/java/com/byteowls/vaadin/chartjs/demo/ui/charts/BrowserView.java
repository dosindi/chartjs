/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.byteowls.vaadin.chartjs.demo.ui.charts;

import com.vaadin.navigator.View;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.VerticalLayout;
import javax.annotation.PostConstruct;

/**
 *
 * @author nyakundid
 */
@UIScope
@SpringView
public class BrowserView extends VerticalLayout implements View  {
    
    @PostConstruct
    public void postConstruct(){
    
        setSizeFull();
        ThemeResource tr = new ThemeResource("../demo/index.html");
        BrowserFrame bf = new BrowserFrame("html",tr);
        bf.setSizeFull();
        addComponent(bf);
        
    }
    
}
