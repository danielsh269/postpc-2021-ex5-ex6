package exercise.android.reemh.todo_items;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class EditItemActivity extends AppCompatActivity
{

    TodoItemsApplication todoapp = TodoItemsApplication.getInstance();
    LocalDataBase db = todoapp.getDataBase();
    private TodoItem item;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        EditText description = findViewById(R.id.description);
        TextView creationDate = findViewById(R.id.creationDateView);
        CheckBox isDone = findViewById(R.id.doneCheckBox);
        TextView modifiedDate = findViewById(R.id.modifiedDateView);
        if (savedInstanceState == null)
        {
            Intent intentCreatedMe = getIntent();
            this.item = (TodoItem)intentCreatedMe.getSerializableExtra("item_to_edit");
            db.setItem(item); // i added the item because i deleted the item before i started the activity (memory problems)
        }
        else
        {
            this.item = (TodoItem)savedInstanceState.getSerializable("saved_item");
        }

        updateModifiedDate(this.item);
        description.setText(item.getDescription());
        creationDate.setText(item.getCreationTime().toString());
        isDone.setChecked(item.isDone());

        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                db.deleteItem(item);
                item.setDescription(s.toString());
                item.setModifiedDate();
                updateModifiedDate(item);
                db.setItem(item);
            }
        });

        description.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus)
            {
                InputMethodManager imm = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        isDone.setOnClickListener(view-> {
            if (!item.isDone())
                db.markItemDone(item);
            else
                db.markItemInProgress(item);

            item.setModifiedDate();
            updateModifiedDate(item);

        });

    }

    private void updateModifiedDate(TodoItem item)
    {

        Date nowDate = new Date();
        long diff = nowDate.getTime() - item.getModifiedDate().getTime();
        long minutes = (long)(diff/ (60000));
        long hours = (long)(minutes / 60);

        String modDate = "";

        if (minutes < 60)
            modDate = minutes + " minutes ago";
        else if (hours < 24)
            modDate = "Today at" + item.getModifiedDate().getHours() + ":" + item.getModifiedDate().getMinutes();
        else
            modDate = item.getModifiedDate().getDate() + " at " + item.getModifiedDate().getHours() + ":" + item.getModifiedDate().getMinutes();

        TextView modifiedDate = findViewById(R.id.modifiedDateView);
        modifiedDate.setText(modDate);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("saved_item", this.item);
    }
}
