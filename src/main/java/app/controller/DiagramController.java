package app.controller;

import app.core.OpenController;
import app.model.chords.TrendyChord;
import app.shipper.HooktheoryShipper;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.stage.StageStyle;

import java.util.Collection;

public class DiagramController extends OpenController {

    @FXML
    private PieChart pie;

    @Override
    protected void employ() {

        openPane().getPane().setOnKeyPressed((ke)->openStage().close());
        openStage().getStage().initStyle(StageStyle.UTILITY);
    }

    @Override
    protected void dress() {
        if(openRoot().getShop().order(HooktheoryShipper.trends,Collection.class)){
            Object trends = openRoot().getShop().purchase(HooktheoryShipper.trends);
            if(trends instanceof Collection) {
                pie.getData().clear();
                for (TrendyChord it : (Collection<? extends TrendyChord>)trends) {
                    pie.getData().add(new PieChart.Data(it.getId(), it.getProbability()));
                }
            }
        }
    }
}
