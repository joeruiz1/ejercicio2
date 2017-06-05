/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crunchify.jsp.servlet;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import edu.co.sergio.mundo.dao.DepartamentoDAO;

public class ChartServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        OutputStream outputStream = response.getOutputStream();
        JFreeChart chart = getChart();
        int width = 500;
        int height = 350;
        ChartUtilities.writeChartAsPNG(outputStream, chart, width, height);

    }

    public JFreeChart getChart() {
        DepartamentoDAO dep = new DepartamentoDAO();
        DefaultPieDataset dataset = new DefaultPieDataset();
        //Crear la capa de servicios que se enlace con el DAO

        for (int i = 0; i < dep.service().size(); i++) {

            int nom = dep.service().get(i).getTotal1();
            int num = dep.service().get(i).getTotal2();
            int nam = dep.service().get(i).getTotal3();

            dataset.setValue("milimetros", nom*100);
            dataset.setValue("temperatura", num*100);
            dataset.setValue("nivel de Carga", nam*100);
        }

//        dataset.setValue("Ford", 23.3);
//        dataset.setValue("Chevy", 32.4);
//        dataset.setValue("Yugo", 44.2);
        boolean legend = true;
        boolean tooltips = false;
        boolean urls = false;

        JFreeChart chart = ChartFactory.createPieChart("sensores", dataset, legend, tooltips, urls);

        chart.setBorderPaint(Color.GREEN);
        chart.setBorderStroke(new BasicStroke(5.0f));
        chart.setBorderVisible(true);

        return chart;
    }

}
