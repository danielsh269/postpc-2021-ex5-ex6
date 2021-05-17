package exercise.android.reemh.todo_items;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemAdapter extends RecyclerView.Adapter<ItemHolder>
{
    LocalDataBase db;


    public ItemAdapter(LocalDataBase db){
        this.db = db;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_todo_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {

        TodoItem item = this.db.getCopies().getCurrentItems().get(position);
        holder.desc.setText(item.getDescription());
        holder.checkBox.setChecked(item.isDone());
        holder.dateView.setText(item.getCreationTime().toString());

        if (item.isDone())
            holder.desc.setPaintFlags(holder.desc.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        else
            holder.desc.setPaintFlags(0);

        holder.deleteButton.setOnClickListener(view->{
            this.db.deleteItem(item);
            notifyDataSetChanged();
        });

        holder.checkBox.setOnClickListener(view->{
            if (!item.isDone())
                this.db.markItemDone(item);
            else
                this.db.markItemInProgress(item);

            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return this.db.getCopies().getCurrentItems().size();
    }
}

class ItemHolder extends RecyclerView.ViewHolder
{

    TextView desc, dateView;
    ImageButton deleteButton;
    CheckBox checkBox;

    public ItemHolder(@NonNull View itemView) {
        super(itemView);
        this.desc = itemView.findViewById(R.id.description);
        this.deleteButton = itemView.findViewById(R.id.deleteButton);
        this.checkBox = itemView.findViewById(R.id.doneCheckBox);
        this.dateView = itemView.findViewById(R.id.dateView);
    }
}