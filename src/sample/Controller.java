package sample;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.Optional;

public class Controller {

    private boolean xTurn = true;
    private volatile boolean win = false;

    private boolean[] xOccupy = {false,false,false,false,false,false,false,false,false};
    private boolean[] circleOccupy = {false,false,false,false,false,false,false,false,false};

    @FXML
    private GridPane mainGridPane;

    @FXML
    private AnchorPane firstTile;

    @FXML
    private AnchorPane secondTile;

    @FXML
    private AnchorPane thirdTile;

    @FXML
    private AnchorPane fourthTile;

    @FXML
    private AnchorPane fifthTile;

    @FXML
    private AnchorPane sixthTile;

    @FXML
    private AnchorPane seventhTile;

    @FXML
    private AnchorPane eighthTile;

    @FXML
    private AnchorPane ninethTile;

    @FXML
    public void initialize()
    {
        startThread();
    }

    @FXML
    public void handleClick(MouseEvent event)
    {
        AnchorPane anchorPane = (AnchorPane) event.getSource();
        if(xTurn)
        {
            if(anchorPane.getChildren().isEmpty())
            {
                drawX((AnchorPane) event.getSource());
                occupyX(anchorPane.getId());
                xTurn = false;
            }
        }
        else
        {
            if(anchorPane.getChildren().isEmpty())
            {
                drawCircle((AnchorPane) event.getSource());
                occupyCircle(anchorPane.getId());
                xTurn = true;
            }
        }

        if(circleWin() || xWin())
        {
            winDialog();
        }
    }

    @FXML
    private void winDialog()
    {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Winner!");
        dialog.initOwner(mainGridPane.getScene().getWindow());
        dialog.setContentText("A winner was found! Do you want to start a new game?");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Optional<ButtonType> choice = dialog.showAndWait();

        if(choice.isPresent() && choice.get() == ButtonType.OK)
        {
            cleanAll();
            startThread();
        }
        else if(choice.isPresent() && choice.get() == ButtonType.CLOSE)
        {
            win = true;
            this.mainGridPane.getScene().getWindow().hide();
        }


    }

    private void drawCircle(AnchorPane anchorPane)
    {
        Circle circle = new Circle();
        circle.setCenterX(anchorPane.getHeight()/2);
        circle.setCenterY(anchorPane.getWidth()/2);
        circle.setRadius(anchorPane.getWidth()/2);
        circle.setFill(Color.RED);
        anchorPane.getChildren().add(circle);
    }

    private void drawX(AnchorPane anchorPane)
    {
        Line line = new Line(0,0,anchorPane.getWidth(),anchorPane.getHeight());

        Line line1 = new Line(0,anchorPane.getHeight(),anchorPane.getWidth(),0);

        anchorPane.getChildren().addAll(line,line1);
    }

    private boolean xWin()
    {
        if(xOccupy[0] && xOccupy[1] && xOccupy[2])
        {
            return true;
        }
        if(xOccupy[3] && xOccupy[4] && xOccupy[5])
        {
            return true;
        }
        if(xOccupy[6] && xOccupy[7] && xOccupy[8])
        {
            return true;
        }
        if(xOccupy[0] && xOccupy[3] && xOccupy[6])
        {
            return true;
        }
        if(xOccupy[1] && xOccupy[4] && xOccupy[7])
        {
            return true;
        }
        if(xOccupy[2] && xOccupy[5] && xOccupy[8])
        {
            return true;
        }
        if(xOccupy[0] && xOccupy[4] && xOccupy[8])
        {
            return true;
        }
        if(xOccupy[2] && xOccupy[4] && xOccupy[6])
        {
            return true;
        }

        return false;
    }

    private void startThread()
    {
        Thread thread = new Thread(()->{
            while(!win)
            {
                if(circleWin())
                {
                    win=true;
                }else if(xWin())
                {
                    win = true;
                }
            }
        });
        thread.start();

    }
    private boolean circleWin()
    {
        if(circleOccupy[0] && circleOccupy[1] && circleOccupy[2])
        {
            return true;
        }
        if(circleOccupy[3] && circleOccupy[4] && circleOccupy[5])
        {
            return true;
        }
        if(circleOccupy[6] && circleOccupy[7] && circleOccupy[8])
        {
            return true;
        }
        if(circleOccupy[0] && circleOccupy[3] && circleOccupy[6])
        {
            return true;
        }
        if(circleOccupy[1] && circleOccupy[4] && circleOccupy[7])
        {
            return true;
        }
        if(circleOccupy[2] && circleOccupy[5] && circleOccupy[8])
        {
            return true;
        }
        if(circleOccupy[0] && circleOccupy[4] && circleOccupy[8])
        {
            return true;
        }
        if(circleOccupy[2] && circleOccupy[4] && circleOccupy[6])
        {
            return true;
        }

        return false;
    }

    private void occupyX(String id)
    {
        switch(id)
        {
            case "firstTile":
                xOccupy[0] = true;
                break;
            case "secondTile":
                xOccupy[1] = true;
                break;
            case "thirdTile":
                xOccupy[2] = true;
                break;
            case "fourthTile":
                xOccupy[3] = true;
                break;
            case "fifthTile":
                xOccupy[4] = true;
                break;
            case "sixthTile":
                xOccupy[5] = true;
                break;
            case "seventhTile":
                xOccupy[6] = true;
                break;
            case "eighthTile":
                xOccupy[7] = true;
                break;
            case "ninethTile":
                xOccupy[8] = true;
                break;
        }
    }

    private void occupyCircle(String id)
    {
        switch(id)
        {
            case "firstTile":
                circleOccupy[0] = true;
                break;
            case "secondTile":
                circleOccupy[1] = true;
                break;
            case "thirdTile":
                circleOccupy[2] = true;
                break;
            case "fourthTile":
                circleOccupy[3] = true;
                break;
            case "fifthTile":
                circleOccupy[4] = true;
                break;
            case "sixthTile":
                circleOccupy[5] = true;
                break;
            case "seventhTile":
                circleOccupy[6] = true;
                break;
            case "eighthTile":
                circleOccupy[7] = true;
                break;
            case "ninethTile":
                circleOccupy[8] = true;
                break;
        }
    }

    private void cleanAll()
    {
        firstTile.getChildren().removeAll(firstTile.getChildren());
        secondTile.getChildren().removeAll(secondTile.getChildren());
        thirdTile.getChildren().removeAll(thirdTile.getChildren());
        fourthTile.getChildren().removeAll(fourthTile.getChildren());
        fifthTile.getChildren().removeAll(fifthTile.getChildren());
        sixthTile.getChildren().removeAll(sixthTile.getChildren());
        seventhTile.getChildren().removeAll(seventhTile.getChildren());
        eighthTile.getChildren().removeAll(eighthTile.getChildren());
        ninethTile.getChildren().removeAll(ninethTile.getChildren());

        for(int i = 0; i < xOccupy.length;i++)
        {
            xOccupy[i] = false;
            circleOccupy[i] = false;
        }

        win = false;
    }


}
