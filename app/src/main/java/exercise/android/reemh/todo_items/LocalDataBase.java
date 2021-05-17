package exercise.android.reemh.todo_items;


import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class LocalDataBase {

    private final TodoItemsHolderImpl itemsHolder = new TodoItemsHolderImpl();
    private final Context context;
    private final SharedPreferences sp;

    private final MutableLiveData<TodoItemsHolderImpl> itemsLiveDataMutable = new MutableLiveData<>();

    public final LiveData<TodoItemsHolderImpl> itemsLiveDataPublic = itemsLiveDataMutable;

    public LocalDataBase(Context context)
    {
        this.context = context;
        this.sp = context.getSharedPreferences("local_db_items", Context.MODE_PRIVATE);
        initializeFromSp();

    }

    private void initializeFromSp()
    {
        Set<String> keys = sp.getAll().keySet();
        for (String key: keys)
        {
            String itemSavedAsString = sp.getString(key, null);
            TodoItem item = stringToItem(itemSavedAsString);
            if (item != null)
            {
                this.itemsHolder.setItem(item);
            }
        }

        this.itemsLiveDataMutable.setValue(new TodoItemsHolderImpl(this.itemsHolder.getCurrentItems()));
    }

    public TodoItemsHolderImpl getCopies()
    {
        return new TodoItemsHolderImpl(this.itemsHolder.getCurrentItems());
    }

    public void markItemDone(TodoItem item)
    {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(itemToString(item));
        editor.apply();
        this.itemsHolder.markItemDone(item);
        editor.putString(itemToString(item), itemToString(item));
        editor.apply();

        this.itemsLiveDataMutable.setValue(new TodoItemsHolderImpl(this.itemsHolder.getCurrentItems()));
    }

    public void markItemInProgress(TodoItem item)
    {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(itemToString(item));
        editor.apply();
        this.itemsHolder.markItemInProgress(item);
        editor.putString(itemToString(item), itemToString(item));
        editor.apply();

        this.itemsLiveDataMutable.setValue(new TodoItemsHolderImpl(this.itemsHolder.getCurrentItems()));
    }

    public void addNewInProgressItem(String description)
    {
        this.itemsHolder.addNewInProgressItem(description);
        TodoItem item = new TodoItem(description);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(itemToString(item), itemToString(item));
        editor.apply();

        this.itemsLiveDataMutable.setValue(new TodoItemsHolderImpl(this.itemsHolder.getCurrentItems()));
    }

    public void deleteItem(TodoItem item)
    {
        this.itemsHolder.deleteItem(item);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(itemToString(item));
        editor.apply();

        this.itemsLiveDataMutable.setValue(new TodoItemsHolderImpl(this.itemsHolder.getCurrentItems()));
    }

    public String itemToString(TodoItem item)
    {
        String res = item.getDescription() + "#";
        res += Boolean.toString(item.isDone()) + "#";
        res += item.getCreationTime().toString() + "#";
        res += item.getModifiedDate().toString();
        return res;
    }

    public TodoItem stringToItem(String string)
    {
        try
        {
            String[] split = string.split("#");
            String desc = split[0];
            Boolean status = Boolean.parseBoolean(split[1]);
            Date creationTime = new SimpleDateFormat("dd/MM/yyyy").parse(split[2]);
            Date modifiedTime = new SimpleDateFormat("dd/MM/yyyy").parse(split[3]);
            return new TodoItem(desc, status, creationTime, modifiedTime);
        }
        catch (Exception e)
        {
            System.out.println("Exception in stringToItem");
            return null;
        }
    }
}
