package com.example.tracker;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
    //Create variable
    private String databaseName ="Tracker";
    private int databaseVersion = 49;

    //Create Database variable
    private Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }


    //Creates Table
    private class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, databaseName, null, databaseVersion);
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try
            {
                db.execSQL("CREATE TABLE IF NOT EXISTS users (" +
                        " user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " user_email VARCHAR," +
                        " user_password VARCHAR, " +
                        " user_salt VARCHAR, " +
                        " user_alias VARCHAR," +
                        " user_dob DATE, " +
                        " user_gender INT, " +
                        " user_location VARHCAR, " +
                        " user_height INT, " +
                        " user_activity_level INT, " +
                        " user_weight INT, " +
                        " user_target_weight INT, " +
                        " user_target_weight_level INT," +
                        " user_measurement VARHCAR, " +
                        " user_last_seen TIME," +
                        " user_note VARCHAR);");

                db.execSQL("CREATE TABLE IF NOT EXISTS food_diary_cal_eaten (" +
                        " cal_eaten_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " cal_eaten_date DATE, " +
                        " cal_eaten_meal_no INT, " +
                        " cal_eaten_energy INT, " +
                        " cal_eaten_proteins INT, " +
                        " cal_eaten_carbs INT, " +
                        " cal_eaten_fat INT);");

                db.execSQL("CREATE TABLE IF NOT EXISTS food_diary (" +
                        " fd_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " fd_date DATE," +
                        " fd_meal_number INT," +
                        " fd_food_id INT," +
                        " fd_serving_size DOUBLE," +
                        " fd_serving_mesurment VARCHAR," +
                        " fd_energy_calculated DOUBLE," +
                        " fd_protein_calculated DOUBLE," +
                        " fd_carbohydrates_calculated DOUBLE," +
                        " fd_fat_calculated DOUBLE," +
                        " fd_fat_meal_id INT);");

                db.execSQL("CREATE TABLE IF NOT EXISTS categories (" +
                        " category_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " category_name VARCHAR," +
                        " category_parent_id INT," +
                        " category_icon VARCHAR," +
                        " category_note VARCHAR);");

                //Double Check to make sure spelling is correct on each parameter when creating SQL items
                db.execSQL("CREATE TABLE IF NOT EXISTS food (" +
                        " food_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " food_name VARCHAR," +
                        " food_manufactor_name VARCHAR," +
                        " food_serving_size DOUBLE," +
                        " food_serving_measurement VARCHAR," +
                        " food_serving_name_number DOUBLE," +
                        " food_serving_name_word VARCHAR," +
                        " food_energy_calories DOUBLE," +
                        " food_proteins DOUBLE," +
                        " food_carbohydrates DOUBLE," +
                        " food_fat DOUBLE," +
                        " food_energy_calculated_calories DOUBLE," +
                        " food_proteins_calculated DOUBLE," +
                        " food_carbohydrates_calculated DOUBLE," +
                        " food_fat_calculated DOUBLE," +
                        " food_user_id INT," +
                        " food_barcode VARCHAR," +
                        " food_category_id INT," +
                       // " food_thumb VARCHAR," +
                        //" food_image_a VARCHAR," +
                        //" food_image_b VARCHAR," +
                        //" food_image_c VARCHAR," +
                        " food_notes VARCHAR);");
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS users");
            db.execSQL("DROP TABLE IF EXISTS food_diary_cal_eaten");
            db.execSQL("DROP TABLE IF EXISTS food_diary");
            db.execSQL("DROP TABLE IF EXISTS categories");
            db.execSQL("DROP TABLE IF EXISTS food");
            onCreate(db);

            String TAG = "Tag";
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");

        } // end public void onUpgrade

    }

    //Opens Database
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //Close Database
    public void close()
    {
        DBHelper.close();
    }

    //Insert food into data
    public void insert(String table,String fields,String values)
    {
        db.execSQL("INSERT INTO " + table + "("+ fields +") VALUES ("+values+")");
    }

    //Counter
    public int count(String table)
    {
        Cursor mCount = db.rawQuery("SELECT COUNT(*) FROM " + table + "", null);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();
        return count;
    }


    // To insert to category table
    public void setupInsertToCategories(String values)
    {
        DBAdapter db = new DBAdapter(context);
        db.open();
        db.insert("categories",
                "category_id, category_name, category_parent_id, category_icon, category_note", values);
        db.close();
    }
    public void insertAllCategories()
    {
        setupInsertToCategories("NULL, 'Bread', '0', '', NULL");
        setupInsertToCategories("NULL, 'Bread', '1', '', NULL");
        setupInsertToCategories("NULL, 'Cereals', '1', '', NULL");
        setupInsertToCategories("NULL, 'Frozen bread and rolls', '1', '', NULL");
        setupInsertToCategories("NULL, 'Crispbread', '1', '', NULL");


        setupInsertToCategories("NULL, 'Dessert and baking', '0', '', NULL");
        setupInsertToCategories("NULL, 'Baking', '2', '', NULL");
        setupInsertToCategories("NULL, 'Biscuit', '2', '', NULL");


        setupInsertToCategories("NULL, 'Drinks', '0', '', NULL");
        setupInsertToCategories("NULL, 'Soda', '3', '', NULL");


        setupInsertToCategories("NULL, 'Fruit and vegetables', '0', '', NULL");
        setupInsertToCategories("NULL, 'Frozen fruits and vegetables', '4', '', NULL");
        setupInsertToCategories("NULL, 'Fruit', '4', '', NULL");
        setupInsertToCategories("NULL, 'Vegetables', '4', '', NULL");
        setupInsertToCategories("NULL, 'Canned fruits and vegetables', '4', '', NULL");


        setupInsertToCategories("NULL, 'Health', '0', '', NULL");
        setupInsertToCategories("NULL, 'Meal substitutes', '5', '', NULL");
        setupInsertToCategories("NULL, 'Protein bars', '5', '', NULL");
        setupInsertToCategories("NULL, 'Protein powder', '5', '', NULL");


        setupInsertToCategories("NULL, 'Meat, chicken and fish', '0', '', NULL");
        setupInsertToCategories("NULL, 'Meat', '6', '', NULL");
        setupInsertToCategories("NULL, 'Chicken', '6', '', NULL");
        setupInsertToCategories("NULL, 'Seafood', '6', '', NULL");


        setupInsertToCategories("NULL, 'Dairy and eggs', '0', '', NULL");
        setupInsertToCategories("NULL, 'Eggs', '7', '', NULL");
        setupInsertToCategories("NULL, 'Cream and sour cream', '7', '', NULL");
        setupInsertToCategories("NULL, 'Yogurt', '7', '', NULL");


        setupInsertToCategories("NULL, 'Dinner', '0', '', NULL");
        setupInsertToCategories("NULL, 'Ready dinner dishes', '8', '', NULL");
        setupInsertToCategories("NULL, 'Pizza', '8', '', NULL");
        setupInsertToCategories("NULL, 'Noodle', '8', '', NULL");
        setupInsertToCategories("NULL, 'Pasta', '8', '', NULL");
        setupInsertToCategories("NULL, 'Rice', '8', '', NULL");
        setupInsertToCategories("NULL, 'Taco', '8', '', NULL");


        setupInsertToCategories("NULL, 'Cheese', '0', '', NULL");
        setupInsertToCategories("NULL, 'Cream cheese', '9', '', NULL");


        setupInsertToCategories("NULL, 'On bread', '0', '', NULL");
        setupInsertToCategories("NULL, 'Cold meats', '10', '', NULL");
        setupInsertToCategories("NULL, 'Sweet spreads', '10', '', NULL");
        setupInsertToCategories("NULL, 'Jam', '10', '', NULL");


        setupInsertToCategories("NULL, 'Snacks', '0', '', NULL");
        setupInsertToCategories("NULL, 'Nuts', '11', '', NULL");
        setupInsertToCategories("NULL, 'Potato chips', '11', '', NULL");
    }


    public void setupInsertToFood(String values)
    {
        DBAdapter db = new DBAdapter(context);
        db.open();
        db.insert("food",
                "food_id, food_name, food_manufactor_name, food_serving_size, food_serving_measurement, food_serving_name_number, food_serving_name_word, food_energy_calories, food_proteins, food_carbohydrates, food_fat, food_energy_calculated_calories, food_proteins_calculated, food_carbohydrates_calculated, food_fat_calculated, food_user_id, food_barcode, food_category_id, food_notes",
                values);
        db.close();

    }

    public void insertAllFood()
    {
        setupInsertToFood("NULL, 'Egg', 'First Price', '15', 'gram', '1', 'stk', '308', '15.8', '0.2', '27.1', '46', '2', '0', '4', NULL, NULL, '25', NULL");
    }


    public String quoteSmart(String value) {
        // Is numeric?
        boolean isNumeric = false;
        try {
            double myDouble = Double.parseDouble(value);
            isNumeric = true;
        } catch (NumberFormatException e) {
            System.out.println("Could not parse " + e);
        }
        if (isNumeric == false) {
            // Escapes special characters in a string for use in an SQL statement
            if (value != null && value.length() > 0) {
                value = value.replace("\\", "\\\\");
                value = value.replace("'", "\\'");
                value = value.replace("\0", "\\0");
                value = value.replace("\n", "\\n");
                value = value.replace("\r", "\\r");
                value = value.replace("\"", "\\\"");
                value = value.replace("\\x1a", "\\Z");
            }
        }

        value = "'" + value + "'";

        return value;
    }

    public double quoteSmart(double value) {
        return value;
    }

    public int quoteSmart(int value) {
        return value;
    }

}


