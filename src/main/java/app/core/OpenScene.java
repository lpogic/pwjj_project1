package app.core;

import javafx.scene.Scene;

import java.util.*;

public class OpenScene extends OpenStageScion {

    private Scene scene;
    private Map<Object, OpenPane> loadedPanes;
    private Set<String> styles;
    private OpenPane primaryPane;

    public OpenScene(Object id, OpenStage openStage) {
        super(id, openStage);
        loadedPanes = new HashMap<>();
        styles = new HashSet<>();
    }

    public void show(){
        openStage().openScene(getId(),true);
        openStage().show();
    }

    public void dress(){
        if(primaryPane == null)openPane(getId(),true);
        int i = 0;
        while (i != loadedPanes.size()) {
            i = loadedPanes.size();
            for (OpenPane openPane : loadedPanes.values()) {
                openPane.dress();
            }
        }
        if(scene == null){
            scene = new Scene(primaryPane.getPane());
            styles.forEach(s -> scene.getStylesheets().add(s));
        }
    }

    public Scene getScene(){
        return scene;
    }

    public OpenPane openPane(){
        return openPane(getId(),false);
    }

    public OpenPane openPane(Object openPaneId){
        return openPane(openPaneId,false);
    }

    public OpenPane openPane(Object openPaneId, boolean setAsPrimary){
        OpenPane openPane = loadedPanes.get(openPaneId);
        if(openPane == null){
            openPane = new OpenPane(openPaneId,this);
            loadedPanes.put(openPaneId,openPane);
        }
        if(setAsPrimary){
            primaryPane = openPane;
            scene = null;
        }
        return openPane;
    }

    public void openStyle(String stylePath){
        if(scene != null){
            scene.getStylesheets().add(stylePath);
        }
        styles.add(stylePath);
    }

    public OpenScene openStyleAnd(String stylePath){
        openStyle(stylePath);
        return this;
    }
}
