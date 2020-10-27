package com.codetreatise.view;

import java.util.ResourceBundle;

public enum FxmlView {
    USER {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("userTask.title");
        }
        @Override
        public String getFxmlFile() {
            return "/fxml/User.fxml";
        }
    },
    LOGIN {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("login.title");
        }
        @Override
        public String getFxmlFile() {
            return "/fxml/Login.fxml";
        }
    },
    USERTASK {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("userTask.title");
        }
        @Override
        public String getFxmlFile() {
            return "/fxml/UserTask.fxml";
        }
    },
    USEROPERAT {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("userOperate.title");
        }
        @Override
        public String getFxmlFile() {
            return "/fxml/UserOperate.fxml";
        }
    };

    public abstract String getTitle();

    public abstract String getFxmlFile();

    String getStringFromResourceBundle(String key) {

        try {
            return new String(ResourceBundle.getBundle("Bundle").getString(key).getBytes("ISO-8859-1"), "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }
}
