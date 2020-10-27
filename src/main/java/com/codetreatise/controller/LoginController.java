package com.codetreatise.controller;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import com.codetreatise.bean.UserLoginInfo;
import com.codetreatise.config.CommonConfig;
import com.codetreatise.service.BusinessService;
import com.codetreatise.util.CheckToolsUtils;
import com.codetreatise.util.CommonAlert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import com.codetreatise.config.StageManager;
import com.codetreatise.view.FxmlView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@Controller
public class LoginController implements Initializable {

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnExit;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    private ImageView imageViewlogin;

    @FXML
    private ImageView imageViewUserName;

    @FXML
    private ImageView imageViewPassword;

    @FXML
    private Pane paneUsername;

    @FXML
    private Pane panePassword;

    @Autowired
    private BusinessService businessService;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    private void login(ActionEvent event) {

        if (!CheckToolsUtils.isPhone(getUsername())) {
            CommonAlert.alert("登录信息", "登录失败", "登录账号有误");
            return;
        }

        if (!CheckToolsUtils.isPassWord(getPassword())) {
            CommonAlert.alert("登录信息", "登录失败", "密码有误");
            return;
        }

        Map resultMap = businessService.authenticate(getUsername(), getPassword());

        if (CommonConfig.CODE_SUCCESSS.equals(resultMap.get("code"))) {
            UserLoginInfo.accessToken = (String) ((Map) resultMap.get("data")).get("access_token");
            stageManager.switchScene(FxmlView.USERTASK);

        } else {
            CommonAlert.alert("登录信息", "登录失败", resultMap.get("msg").toString());
        }

        System.out.println("username:" + getUsername());
        System.out.println("password:" + getPassword());
    }

    @FXML
    private void exit(ActionEvent event) { }

    public String getPassword() {
        return txtPassword.getText();
    }

    public String getUsername() {
        return txtUsername.getText();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //todo 交付时删除部分
        txtUsername.setText("18805320011");
        txtPassword.setText("123456");
    }

}
