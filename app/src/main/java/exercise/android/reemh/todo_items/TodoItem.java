package exercise.android.reemh.todo_items;

import java.io.Serializable;
import java.util.Date;

public class TodoItem implements Serializable {
    private String description;
    private boolean status; // false = IN-PROGRESS, true = DONE
    private final Date creationTime;
    private Date modifiedDate;

    public TodoItem(String desc)
    {
        this.description = desc;
        status = false;
        creationTime = new Date();
        modifiedDate = creationTime;
    }
    public TodoItem(String desc, Boolean status, Date creationTime, Date modifiedDate)
    {
        this.description = desc;
        this.status = status;
        this.creationTime = creationTime;
        this.modifiedDate = modifiedDate;
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
    public void setModifiedDate() { this.modifiedDate = new Date(); }
    public Date getModifiedDate() { return this.modifiedDate; }
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
