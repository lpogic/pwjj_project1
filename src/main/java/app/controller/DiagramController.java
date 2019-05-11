package app.controller;

import app.core.pane.OpenController;
import app.core.shop.contract.Contract;
import app.core.shop.contract.stamp.Stamp;
import app.model.chords.TrendyChord;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.stage.StageStyle;

import java.util.Collections;
import java.util.List;

public class DiagramController extends OpenController {

    public static final Contract<Object> show = Contract.forObject(Stamp.SERVICE);
    public static final Contract<List<TrendyChord>> getTrends = Contract.forListOf(TrendyChord.class, Stamp.SERVICE);

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
        for (TrendyChord it : shop().deal(getTrends,Collections.emptyList())) {
            pie.getData().add(new PieChart.Data(it.getId(), it.getProbability()));
        }
    }
}
