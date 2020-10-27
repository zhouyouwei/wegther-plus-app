package com.codetreatise.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.alibaba.fastjson.JSONArray;
import com.codetreatise.bean.UserLoginInfo;
import com.codetreatise.bean.UserTask;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import com.codetreatise.bean.User;
import com.codetreatise.config.StageManager;
import com.codetreatise.service.BusinessService;
import com.codetreatise.view.FxmlView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

@Controller
public class UserTaskController implements Initializable {

    @FXML
    private TableView<UserTask> userTaskTableView;

    @FXML
    private TableColumn<UserTask, Integer> colId;

    @FXML
    private TableColumn<UserTask, String> colTradeNo;

    @FXML
    private TableColumn<UserTask, Integer> colItemCount;

    @FXML
    private TableColumn<UserTask, String> colCollectDate;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    private DatePicker txtCalendar;

    @Autowired
    private BusinessService businessService;

    private ObservableList<UserTask> userTaskList = FXCollections.observableArrayList();


    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.LOGIN);
    }

    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }


    private void clearFields() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("UserLoginInfo:" + UserLoginInfo.accessToken);

        txtCalendar.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                int year = newValue.getYear();
                int month = newValue.getMonthValue();
                int day = newValue.getDayOfMonth();
                String argDate = String.format("%d-%d-%d", year, month, day);

                Map respones = businessService.getTaskList(UserLoginInfo.accessToken, argDate);

                String inArg = "year = " + year + ", month = " + month + ", day = " + day;
                System.out.println("request:");
                System.out.println(inArg);
                System.out.println("response:");
                System.out.println(respones);

                JSONArray resultJSONArray =  (JSONArray)respones.get("data");

                List<UserTask> resultList = new ArrayList(resultJSONArray.size());

                for(int i=0;i<resultJSONArray.size();i++) {

                    UserTask userTask = new UserTask();

                    userTask.setColId((String)resultJSONArray.getJSONObject(i).get("id"));

                    userTask.setColTradeNo((String)resultJSONArray.getJSONObject(i).get("trade_no"));

                    resultList.add(userTask);
                }

                userTaskList.clear();
                userTaskList.addAll(resultList);
            }
        });

        setColumnProperties();

        loadUserDetails();
    }

    private void setColumnProperties() {

    }


    Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>> cellFactory =
            new Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>>() {
                @Override
                public TableCell<User, Boolean> call(final TableColumn<User, Boolean> param) {
                    final TableCell<User, Boolean> cell = new TableCell<User, Boolean>() {
                        Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
                        final Button btnEdit = new Button();

                        @Override
                        public void updateItem(Boolean check, boolean empty) {

                            super.updateItem(check, empty);

                            if (empty) {

                                setGraphic(null);
                                setText(null);

                            } else {

                                btnEdit.setOnAction(e -> {
                                    User user = getTableView().getItems().get(getIndex());
                                    updateUser(user);
                                });

                                btnEdit.setStyle("-fx-background-color: transparent;");
                                ImageView iv = new ImageView();
                                iv.setImage(imgEdit);
                                iv.setPreserveRatio(true);
                                iv.setSmooth(true);
                                iv.setCache(true);
                                btnEdit.setGraphic(iv);

                                setGraphic(btnEdit);
                                setAlignment(Pos.CENTER);
                                setText(null);
                            }

                        }

                        private void updateUser(User user) {

                        }
                    };
                    return cell;
                }
            };

    private void loadUserDetails() {


    }
}
