package exercise.android.reemh.todo_items;

import android.content.Context;
import android.content.Intent;
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
    Context context;

    public ItemAdapter(Context context, LocalDataBase db)
    {
        this.context = context;
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
//        holder.dateView.setText(item.getCreationTime().toString());
        holder.dateView.setText("");

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

        holder.editButton.setOnClickListener(view->{
            Intent editIntent = new Intent(this.context, EditItemActivity.class);
            editIntent.putExtra("item_to_edit", item);
            db.deleteItem(item);
            this.context.startActivity(editIntent);
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
    ImageButton deleteButton, editButton;
    CheckBox checkBox;

    public ItemHolder(@NonNull View itemView) {
        super(itemView);
        this.desc = itemView.findViewById(R.id.description);
        this.deleteButton = itemView.findViewById(R.id.deleteButton);
        this.checkBox = itemView.findViewById(R.id.doneCheckBox);
        this.dateView = itemView.findViewById(R.id.dateView);
        this.editButton = itemView.findViewById(R.id.editButton);
    }
}