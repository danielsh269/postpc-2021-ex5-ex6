package exercise.android.reemh.todo_items;

import java.io.Serializable;
import java.util.Date;

public class TodoItem implements Serializable {
    private String description;
    private boolean status; // false = IN-PROGRESS, true = DONE
    private final Date creationTime;

    public TodoItem(String desc)
    {
        this.description = desc;
        status = false;
        creationTime = new Date();
    }

    public String getDescription()
    {
        return this.description;
    }
    public boolean isDone()
    {
        return this.status;
    }
    public Date getCreationTime()
    {
        return this.creationTime;
    }
    public void setDone()
    {
        this.status = true;
    }
    public void setInProgress()
    {
        this.status = false;
    }
    public void setDescription(String desc)
    {
        this.description = desc;
    }




}
