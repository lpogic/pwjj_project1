package app.controller;

import app.core.pane.OpenController;
import app.dealer.HooktheoryDealer;
import app.model.chords.TrendyChord;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.stage.StageStyle;

import java.util.Collections;

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
        pie.getData().clear();
        for (TrendyChord it : shop().deal(HooktheoryDealer.getTrends,Collections.emptyList())) {
            pie.getData().add(new PieChart.Data(it.getId(), it.getProbability()));
        }
    }
}
