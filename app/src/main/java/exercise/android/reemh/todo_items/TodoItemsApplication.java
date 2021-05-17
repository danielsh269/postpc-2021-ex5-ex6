package exercise.android.reemh.todo_items;

import android.app.Application;

public class TodoItemsApplication extends Application {

    private LocalDataBase dataBase;
    private static TodoItemsApplication instance = null;
    public LocalDataBase getDataBase()
    {
        return dataBase;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        dataBase = new LocalDataBase(this);
    }

    public static TodoItemsApplication getInstance()
    {
        return instance;
    }
}
