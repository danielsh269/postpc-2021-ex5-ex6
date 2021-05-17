package exercise.android.reemh.todo_items;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  public TodoItemsApplication todoApp = TodoItemsApplication.getInstance();
  public LocalDataBase dataBase = todoApp.getDataBase();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    EditText inputText = findViewById(R.id.editTextInsertTask);
    inputText.setText("");
    FloatingActionButton addTaskButton = findViewById(R.id.buttonCreateTodoItem);

    RecyclerView recyclerView = findViewById(R.id.recyclerTodoItemsList);
    ItemAdapter adapter = new ItemAdapter(this, this.dataBase);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));


    addTaskButton.setOnClickListener(view -> {

      String description = inputText.getText().toString();

      if (!description.equals("")) {
        this.dataBase.addNewInProgressItem(description);
        inputText.setText("");
        adapter.notifyDataSetChanged();
      }
    });

    dataBase.itemsLiveDataPublic.observe(this, new Observer<TodoItemsHolderImpl>() {
      @Override
      public void onChanged(TodoItemsHolderImpl todoItemsHolder) {
        adapter.notifyDataSetChanged();
      }
    });
  }
}

