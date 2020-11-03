package com.codetreatise.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Map;
import java.util.ResourceBundle;

import com.alibaba.fastjson.JSONArray;
import com.codetreatise.bean.UserLoginInfo;
import com.codetreatise.bean.UserTask;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import com.codetreatise.config.StageManager;
import com.codetreatise.service.BusinessService;
import com.codetreatise.view.FxmlView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

@Controller
public class UserTaskController implements Initializable {

    @FXML
    private TableView<UserTask> userTaskSortedTableView;

    @FXML
    private TableColumn<UserTask, String> colId;

    @FXML
    private TableColumn<UserTask, String> colTradeNo;

    @FXML
    private TableColumn<UserTask, String> colItemCount;

    @FXML
    private TableColumn<UserTask, String> colCollectDate;

    private ObservableList<UserTask> userTaskTableViewData = FXCollections.observableArrayList();

    @FXML
    private Tab tab1;

    @FXML
    private Tab tab2;


    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    private DatePicker txtCalendar;

    @Autowired
    private BusinessService businessService;

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

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colId.setCellValueFactory(cellData -> cellData.getValue().getColId());

        colTradeNo.setCellValueFactory(cellData -> cellData.getValue().getColTradeNo());

        colItemCount.setCellValueFactory(cellData -> cellData.getValue().getColItemCount());

        colCollectDate.setCellValueFactory(cellData -> cellData.getValue().getColCollectDate());

        userTaskSortedTableView.setItems(userTaskTableViewData);

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

                JSONArray resultJSONArray = (JSONArray) respones.get("data");
                userTaskTableViewData.clear();

                for (int i = 0; i < resultJSONArray.size(); i++) {

                    UserTask userTask = new UserTask((String) resultJSONArray.getJSONObject(i).get("id"),
                            (String) resultJSONArray.getJSONObject(i).get("trade_no"),
                            (String) resultJSONArray.getJSONObject(i).get("item_count"),
                            (String) resultJSONArray.getJSONObject(i).get("collect_date"));

                    userTaskTableViewData.add(userTask);

                    userTaskSortedTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if (event.getButton().equals(MouseButton.PRIMARY)
                                    && event.getClickCount() == 1
                            ) {

                                System.out.println("userTaskSortedTableView.event:" + event);

                            }
                        }
                    });

                }
            }
        });
    }
}
