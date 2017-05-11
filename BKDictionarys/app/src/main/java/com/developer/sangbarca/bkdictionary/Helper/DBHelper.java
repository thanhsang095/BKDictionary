package com.developer.sangbarca.bkdictionary.Helper;

/**
 * Created by nhat on 07/05/2017.
 */

public class DBHelper {

    public static final int DB_VERSION  = 1;
    public static final String DB_NAME     = "bk_dict.db";
    public static final String COMMA_SPACE     = ", ";
    public static final String CREATE_TABLE    = "CREATE TABLE ";
    public static final String PRIMARY_KEY     = "PRIMARY KEY ";
    public static final String UNIQUE          = "UNIQUE ";
    public static final String TYPE_TEXT       = " TEXT ";
    public static final String TYPE_DATE       = " DATETIME ";
    public static final String TYPE_INT        = " INTEGER ";
    public static final String DEFAULT         = "DEFAULT ";
    public static final String AUTOINCREMENT   = "AUTOINCREMENT ";
    public static final String NOT_NULL        = "NOT NULL ";
    public static final String DROP_TABLE      = "DROP TABLE IF EXISTS ";
    public static final String TABLE_DICTS  = "dicts";
    public static final String TABLE_EXAMPLES  = "examples";
    public static final String TABLE_OPTIONALS = "optionals";
    public static final String TABLE_CATEGORIES  = "categories";
    public static final String TABLE_DICTS_CATEGORIES = "dicts_categories";
    public static final String TABLE_MEANDICT = "mean_dict";
    public static final String OPEN_TAG = "(";
    public static final String CLOSE_TAG = ")";
    public static final String WHERE_CONDITION = " = ? ";
    public static final String LIKE_CONDITION = " LIKE ?";
    public static final String PROJECTION_ALL  = " * ";
    public static final String SORT_DESC = " DESC ";
    public static final String SELECT = " SELECT ";
    public static final String FROM = " FROM ";


    public static final class TB_DICTS {
        public static final String PRIMARY_KEY = "_id";
        public static final String ID = "_key";
        public static final String NAME = "name";
        public static final String PRONOUNCE = "pronounce";

    }

    public static final class TB_DICTS_CATEGORIES {
        public static final String PRIMARY_KEY = "_id";
        public static final String DICT_ID = "dict_id";
        public static final String CATEGORIES_ID = "category_id";
    }

    public static final class TB_EXAMPLES {
        public static final String PRIMARY_KEY = "_id";
        public static final String CONTENT = "content";
        public static final String DICTS_ID = "dict_id";
    }

    public static final class TB_OPTIONALS{
        public static final String PRIMARY_KEY = "_id";
        public static final String ID = "_key";
        public static final String NAME = "name";
        public static final String NAME_VI = "nameVi";
    }

    public static final class TB_MEANDICT{
        public static final String PRIMARY_KEY = "_id";
        public static final String DICT_ID = "dict_id";
        public static final String OPTIONAL_ID = "optional_id";
        public static final String CONTENT = "content";
    }

    public static final class TB_CATEGORIES {
        public static final String PRIMARY_KEY = "_id";
        public static final String ID = "_key";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
    }

    public static final String CREATE_TABLE_DICTS = CREATE_TABLE + TABLE_DICTS + OPEN_TAG
            + TB_DICTS.PRIMARY_KEY + TYPE_INT + NOT_NULL + PRIMARY_KEY + COMMA_SPACE
            + TB_DICTS.NAME + TYPE_TEXT + NOT_NULL + COMMA_SPACE
            + TB_DICTS.ID + TYPE_TEXT + NOT_NULL + COMMA_SPACE
            + TB_DICTS.PRONOUNCE + TYPE_TEXT + NOT_NULL
            +  CLOSE_TAG;

    public static final String CREATE_TABLE_DICTS_CATEGORIES = CREATE_TABLE + TABLE_DICTS_CATEGORIES + OPEN_TAG
            + TB_DICTS_CATEGORIES.PRIMARY_KEY + TYPE_INT + NOT_NULL + PRIMARY_KEY + COMMA_SPACE
            + TB_DICTS_CATEGORIES.DICT_ID + TYPE_INT + COMMA_SPACE
            + TB_DICTS_CATEGORIES.CATEGORIES_ID + TYPE_INT + CLOSE_TAG;

    public static final String CREATE_TABLE_MEANDICT = CREATE_TABLE + TABLE_MEANDICT + OPEN_TAG
            + TB_MEANDICT.PRIMARY_KEY + TYPE_INT + NOT_NULL + PRIMARY_KEY + COMMA_SPACE
            + TB_MEANDICT.DICT_ID + TYPE_INT + COMMA_SPACE
            + TB_MEANDICT.OPTIONAL_ID  + TYPE_INT + COMMA_SPACE
            + TB_MEANDICT.CONTENT + TYPE_TEXT + CLOSE_TAG;


    public static final String CREATE_TABLE_EXAMPLES = CREATE_TABLE + TABLE_EXAMPLES + OPEN_TAG
            + TB_EXAMPLES.PRIMARY_KEY + TYPE_INT + NOT_NULL + PRIMARY_KEY + COMMA_SPACE
            + TB_EXAMPLES.DICTS_ID + TYPE_INT + COMMA_SPACE
            + TB_EXAMPLES.CONTENT + TYPE_TEXT + CLOSE_TAG;

    public static final String CREATE_TABLE_OPTIONALS = CREATE_TABLE + TABLE_OPTIONALS + OPEN_TAG
            + TB_OPTIONALS.PRIMARY_KEY + TYPE_INT + NOT_NULL + PRIMARY_KEY + COMMA_SPACE
            + TB_OPTIONALS.ID + TYPE_TEXT + NOT_NULL+ COMMA_SPACE
            + TB_OPTIONALS.NAME + TYPE_TEXT + COMMA_SPACE
            + TB_OPTIONALS.NAME_VI + TYPE_TEXT
            + CLOSE_TAG;
    public static final String CREATE_TABLE_CATEGORIES = CREATE_TABLE + TABLE_CATEGORIES + OPEN_TAG
            + TB_CATEGORIES.PRIMARY_KEY + TYPE_INT + NOT_NULL + PRIMARY_KEY + COMMA_SPACE
            + TB_OPTIONALS.ID + TYPE_TEXT + NOT_NULL+ COMMA_SPACE
            + TB_CATEGORIES.NAME + TYPE_TEXT + COMMA_SPACE
            + TB_CATEGORIES.DESCRIPTION + TYPE_TEXT + CLOSE_TAG;



}
