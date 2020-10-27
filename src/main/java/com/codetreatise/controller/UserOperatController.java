package com.codetreatise.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.codetreatise.bean.UserLoginInfo;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import com.codetreatise.bean.User;
import com.codetreatise.config.StageManager;
import com.codetreatise.service.BusinessService;
import com.codetreatise.view.FxmlView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

@Controller
public class UserOperatController implements Initializable {

    @FXML
    private Button btnLogout;

    @FXML
    private Label userId;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private DatePicker dob;

    @FXML
    private RadioButton rbMale;

    @FXML
    private ToggleGroup gender;

    @FXML
    private RadioButton rbFemale;

    @FXML
    private ComboBox<String> cbRole;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Button reset;

    @FXML
    private Button saveUser;

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, Long> colUserId;

    @FXML
    private TableColumn<User, String> colFirstName;

    @FXML
    private TableColumn<User, String> colLastName;

    @FXML
    private TableColumn<User, LocalDate> colDOB;

    @FXML
    private TableColumn<User, String> colGender;

    @FXML
    private TableColumn<User, String> colRole;

    @FXML
    private TableColumn<User, String> colEmail;

    @FXML
    private TableColumn<User, Boolean> colEdit;

    @FXML
    private MenuItem deleteUsers;

    @FXML
    private Pane paneTop;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private BusinessService businessService;

    private ObservableList<User> userList = FXCollections.observableArrayList();
    private ObservableList<String> roles = FXCollections.observableArrayList("Admin", "User");

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

    /**
     * Logout and go to the login page
     */
    @FXML
    private void logout(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.LOGIN);
    }

    @FXML
    void reset(ActionEvent event) {
        clearFields();
    }

    @FXML
    private void saveUser(ActionEvent event) {

        if (validate("First Name", getFirstName(), "[a-zA-Z]+") &&
                validate("Last Name", getLastName(), "[a-zA-Z]+") &&
                emptyValidation("DOB", dob.getEditor().getText().isEmpty()) &&
                emptyValidation("Role", getRole() == null)) {

            if (userId.getText() == null || userId.getText() == "") {
                if (validate("Email", getEmail(), "[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+") &&
                        emptyValidation("Password", getPassword().isEmpty())) {

                    User user = new User();
                    user.setFirstName(getFirstName());
                    user.setLastName(getLastName());
                    user.setDob(getDob());
                    user.setGender(getGender());
                    user.setRole(getRole());
                    user.setEmail(getEmail());
                    user.setPassword(getPassword());


                }

            } else {

            }

            clearFields();
            loadUserDetails();
        }


    }

    @FXML
    private void deleteUsers(ActionEvent event) {
        List<User> users = userTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected?");
        Optional<ButtonType> action = alert.showAndWait();


        loadUserDetails();
    }

    private void clearFields() {
        userId.setText(null);
        firstName.clear();
        lastName.clear();
        dob.getEditor().clear();
        rbMale.setSelected(true);
        rbFemale.setSelected(false);
        cbRole.getSelectionModel().clearSelection();
        email.clear();
        password.clear();
    }

    private void saveAlert(User user) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("User saved successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The user " + user.getFirstName() + " " + user.getLastName() + " has been created and \n" + getGenderTitle(user.getGender()) + " id is " + user.getId() + ".");
        alert.showAndWait();
    }

    private void updateAlert(User user) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("User updated successfully.");
        alert.setHeaderText(null);
        alert.setContentText("The user " + user.getFirstName() + " " + user.getLastName() + " has been updated.");
        alert.showAndWait();
    }

    private String getGenderTitle(String gender) {
        return (gender.equals("Male")) ? "his" : "her";
    }

    public String getFirstName() {
        return firstName.getText();
    }

    public String getLastName() {
        return lastName.getText();
    }

    public LocalDate getDob() {
        return dob.getValue();
    }

    public String getGender() {
        return rbMale.isSelected() ? "Male" : "Female";
    }

    public String getRole() {
        return cbRole.getSelectionModel().getSelectedItem();
    }

    public String getEmail() {
        return email.getText();
    }

    public String getPassword() {
        return password.getText();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("UserLoginInfo:" + UserLoginInfo.accessToken);



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
                            userId.setText(Long.toString(user.getId()));
                            firstName.setText(user.getFirstName());
                            lastName.setText(user.getLastName());
                            dob.setValue(user.getDob());
                            if (user.getGender().equals("Male")) {
                                rbMale.setSelected(true);
                            } else {
                                rbFemale.setSelected(true);
                            }
                            cbRole.getSelectionModel().select(user.getRole());
                        }
                    };
                    return cell;
                }
            };


    /*
     *  Add All users to observable list and update table
     */
    private void loadUserDetails() {
        userList.clear();
        //userList.addAll(businessService.findAll());


    }

    /*
     * Validations
     */
    private boolean validate(String field, String value, String pattern) {
        if (!value.isEmpty()) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(value);
            if (m.find() && m.group().equals(value)) {
                return true;
            } else {
                validationAlert(field, false);
                return false;
            }
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    private boolean emptyValidation(String field, boolean empty) {
        if (!empty) {
            return true;
        } else {
            validationAlert(field, true);
            return false;
        }
    }

    private void validationAlert(String field, boolean empty) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        if (field.equals("Role")) {
            alert.setContentText("Please Select " + field);
        } else {
            if (empty) {
                alert.setContentText("Please Enter " + field);
            } else {
                alert.setContentText("Please Enter Valid " + field);
            }
        }
        alert.showAndWait();
    }
}
