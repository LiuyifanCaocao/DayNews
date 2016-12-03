package com.jredu.liuyifan.mygoalapplication.util;

/**
 * Created by DELL on 2016/9/19.
 */
public class Constant {
    public static class NewTable{
        public static final String TBL_NAME = "NEWS";
        public static final String TBL_COL_TITLE = "NEWS_TITLE";
        public static final String TBL_COL_SRC = "NEWS_SRC";
        public static final String TBL_COL_COMM = "NEWS_COMM";
        public static final String TBL_COL_DATE = "NEWS_DATE";
        public static String getCrateTableSQL(){
            String sq2 = "create table if not exists "
                    + TBL_NAME
                    + " ( "
                    + " _id integer primary key autoincrement , "
                    + TBL_COL_TITLE + " text , "
                    + TBL_COL_SRC + " text , "
                    + TBL_COL_COMM + " text , "
                    + TBL_COL_DATE + " varchar(50) "
                    + " ) ";
            String sq3 = "create table if not exists "
                    + TBL_NAME
                    + " ( "
                    + " _id integer primary key autoincrement , "
                    + TBL_COL_TITLE + " text , "
                    + TBL_COL_SRC + " text , "
                    + TBL_COL_COMM + " text , "
                    + TBL_COL_DATE + " text "
                    + " ) ";
            return sq2;
        }

        public static final String SET_NAME = "setting";
        public static final String SET_COL_THE_LIST_SHOW = "set_the_list_show";
        public static final String SET_COL_FONT_SIZE = "set_font_size";
        public static final String SET_COL_NETWORK_FLOW = "set_network_flow";
        public static final String SET_COL_PUSH_NOTIFICATION = "set_push_notification";
        public static final String SET_COL_COLLECTION = "set_collection";
        public static final String SET_COL_CHOOSE = "set_choose";
        public static final String SET_COL_CHOOSE_FLOW = "set_choose_flow";

        public static String getSettingSQL(){
            String sq1 = "create table if not exists "
                    + SET_NAME
                    + " ( "
                    + " _id integer primary key autoincrement , "
                    + SET_COL_THE_LIST_SHOW + " text , "
                    + SET_COL_FONT_SIZE + " text , "
                    + SET_COL_NETWORK_FLOW + " text , "
                    + SET_COL_PUSH_NOTIFICATION + " text , "
                    + SET_COL_COLLECTION + " text , "
                    + SET_COL_CHOOSE + " text , "
                    + SET_COL_CHOOSE_FLOW + " text "
                    + " ) ";
            return sq1;
        }

    }
}
