package fr.univtln.dosso_boudfor.mini_projet_d35;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import fr.univtln.dosso_boudfor.mini_projet_d35.entities.Project;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.ChartSeries;

@Named
@SessionScoped
public class ChartView implements Serializable {

    private BarChartModel barModel;
    private HorizontalBarChartModel horizontalBarModel;

    @Inject
    private UserController userController;

    @PostConstruct
    public void init() {
        createBarModels();
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public HorizontalBarChartModel getHorizontalBarModel() {
        return horizontalBarModel;
    }

    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();

        ChartSeries taskDone = new ChartSeries();
        taskDone.setLabel("Taches términées");

        ChartSeries taskNotDone = new ChartSeries();
        taskNotDone.setLabel("Toutes les Taches");

        for(Project project: userController.getUtilisateur().getMesProjets()){
            taskDone.set(project.getTitre(), userController.getTaskDone(project.getId()));
            taskNotDone.set(project.getTitre(), project.getTaches().size());

        }

        model.addSeries(taskDone);
        model.addSeries(taskNotDone);

        return model;
    }

    private void createBarModels() {
        createBarModel();
        createHorizontalBarModel();
    }

    private void createBarModel() {
        barModel = initBarModel();

        barModel.setTitle("Progression par projet");
        barModel.setLegendPosition("ne");

        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Projets");

        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Nombres de taches");
        yAxis.setMin(0);
        yAxis.setMax(200);
    }

    private void createHorizontalBarModel() {
        horizontalBarModel = new HorizontalBarChartModel();

        ChartSeries taskDone = new ChartSeries();
        taskDone.setLabel("Taches términées");

        ChartSeries taskNotDone = new ChartSeries();
        taskNotDone.setLabel("Toutes les Taches");

        for(Project project: userController.getUtilisateur().getMesProjets()){
            taskDone.set(project.getTitre(), userController.getTaskDone(project.getId()));
            taskNotDone.set(project.getTitre(), project.getTaches().size());

        }

        horizontalBarModel.addSeries(taskDone);
        horizontalBarModel.addSeries(taskNotDone);

        horizontalBarModel.setTitle("Progression par Projets");
        horizontalBarModel.setLegendPosition("e");
        horizontalBarModel.setStacked(true);

        Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
        xAxis.setLabel("Nombres de Taches");
        xAxis.setMin(0);
        xAxis.setMax(200);

        Axis yAxis = horizontalBarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Projets");
    }

}