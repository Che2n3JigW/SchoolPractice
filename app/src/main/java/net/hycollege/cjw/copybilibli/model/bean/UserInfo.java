package net.hycollege.cjw.copybilibli.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/12.
 */

public class UserInfo {

    private List<UserBean> user;

    public List<UserBean> getUser() {
        return user;
    }

    public void setUser(List<UserBean> user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * user_account : qqq
         * user_password : 123
         * user_nick : jChenys
         * user_head : user_head/ppp.jpg
         */

        private String user_account;
        private String user_password;
        private String user_nick;
        private String user_head;

        public String getUser_account() {
            return user_account;
        }

        public void setUser_account(String user_account) {
            this.user_account = user_account;
        }

        public String getUser_password() {
            return user_password;
        }

        public void setUser_password(String user_password) {
            this.user_password = user_password;
        }

        public String getUser_nick() {
            return user_nick;
        }

        public void setUser_nick(String user_nick) {
            this.user_nick = user_nick;
        }

        public String getUser_head() {
            return user_head;
        }

        public void setUser_head(String user_head) {
            this.user_head = user_head;
        }
    }
}
