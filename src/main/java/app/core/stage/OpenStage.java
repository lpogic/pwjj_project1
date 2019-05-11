package app.core.stage;

import app.core.OpenRoot;
import app.core.scene.OpenScene;
import app.core.stage.OpenRootScion;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.*;

public class OpenStage extends OpenRootScion {

    private Stage stage;
    private OpenScene primaryScene;
    private Map<Object, OpenScene> loadedScenes;
    private Stack<OpenScene> pastScenes;
    private Deque<Object> hideTickets;

    public OpenStage(Object id, OpenRoot openRoot, Stage stage) {
        super(id, openRoot);
        this.stage = stage;
        loadedScenes = new HashMap<>();
        pastScenes = new Stack<>();
        hideTickets = new ArrayDeque<>();
        stage.setOnHiding((we)->{
            while (hideTickets.size() > 0){
                Platform.exitNestedEventLoop(hideTickets.remove(),null);
            }
        });
    }

    public Stage getStage() {
        return stage;
    }

    public void show(){
        if(primaryScene == null)openScene(getId(),true);
        primaryScene.dress();
        stage.setScene(primaryScene.getScene());

        if (stage.isShowing())
            stage.toFront();
        else stage.show();
    }

    public void showAndWait(){
        show();
        Object o = new Object();
        hideTickets.addFirst(o);
        Platform.enterNestedEventLoop(o);
    }

    public void close(){
        openRoot().collectStage(getId());
    }

    public OpenScene openScene(){return openScene(getId(),false);}

    public OpenScene openScene(Object openSceneId){
        return openScene(openSceneId,false);
    }

    public OpenScene openScene(Object openSceneId, boolean setAsPrimary){
        OpenScene openScene = loadedScenes.get(openSceneId);
        if(openScene == null){
            openScene = new OpenScene(openSceneId,this);
            loadedScenes.put(openSceneId,openScene);
        }
        if(setAsPrimary){
            if(primaryScene != null && openScene != primaryScene)
                pastScenes.push(primaryScene);
            primaryScene = openScene;
        }
        return openScene;
    }

    public void popOpenScene(){
        if(pastScenes.isEmpty()){
            openRoot().collectStage(getId());
        } else {
            primaryScene = pastScenes.pop();
            openScene(primaryScene.getId(),true);
        }
    }
}
