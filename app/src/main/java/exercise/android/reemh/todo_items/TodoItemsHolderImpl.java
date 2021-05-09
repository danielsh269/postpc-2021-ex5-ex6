package exercise.android.reemh.todo_items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class TodoItemsHolderImpl implements TodoItemsHolder {
  List<TodoItem> items;

  public TodoItemsHolderImpl()
  {
    this.items = new ArrayList<>();
  }


  @Override
  public List<TodoItem> getCurrentItems()
  {
    return this.items;
  }

  @Override
  public void addNewInProgressItem(String description)
  {
    this.items.add(new TodoItem(description));
    Collections.sort(this.items, new itemComparator());
  }

  @Override
  public void markItemDone(TodoItem item)
  {
    for (TodoItem i : this.items)
    {
      if (i.equals(item))
      {
        i.setDone();
      }
    }
    Collections.sort(this.items, new itemComparator());
  }

  @Override
  public void markItemInProgress(TodoItem item)
  {
    for (TodoItem i : this.items)
    {
      if (i.equals(item))
      {
        i.setInProgress();
      }
    }
    Collections.sort(this.items, new itemComparator());
  }

  @Override
  public void deleteItem(TodoItem item)
  {
    this.items.remove(item);
  }

  private static class itemComparator implements Comparator<TodoItem>
  {
    @Override
    public int compare(TodoItem o1, TodoItem o2) {

      if (o1.isDone() == o2.isDone())
        return o1.getCreationTime().compareTo(o2.getCreationTime());

      if (o1.isDone())
        return 1;

      return -1;
    }
  }
}
