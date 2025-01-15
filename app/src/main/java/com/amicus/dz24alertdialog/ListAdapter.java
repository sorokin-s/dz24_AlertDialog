package com.amicus.dz24alertdialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends  RecyclerView.Adapter<ListAdapter.ViewHolder> {
    List<Item> items;
    List<Item> _items;
    View view;

    public interface OnItemClickListener{void onItemClick(Item item,int position,View itemView);}  // слушатель
    private final OnItemClickListener onItemClickListener;
    public ListAdapter(List<Item> items, OnItemClickListener listener) { // конструктор
        this.items = items;
        this.onItemClickListener = listener;
    }
    ViewGroup viewGroup;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        viewGroup=parent;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item currentItem = items.get(position);
        holder.textView1.setText(currentItem.getText1());
        holder.textView2.setText(currentItem.getText2());
        holder.imageView.setImageResource(currentItem.getImageResId());
        holder.itemView.setOnClickListener(i->{   // обработка клика на элементе
            onItemClickListener.onItemClick(currentItem,position,holder.itemView); // передаём данные в метод слушателя
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void deleteItemByPosition(int position){ // удаляем по позиции
        items.remove(position);
        notifyDataSetChanged();
    }
    public void deleteItemsByItem(ArrayList<Item> itemsForDel){  // удаляем по элементу
        items.removeAll(itemsForDel);
        notifyDataSetChanged();
    }
    public  class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView1,textView2;
        ImageView imageView;
        CheckBox checkBox;
        public ViewHolder(@NotNull View itemView){ // конструктор
            super(itemView);
            textView1=itemView.findViewById(R.id.itemTextView);
            textView2= itemView.findViewById(R.id.itemTextView2);
            imageView = itemView.findViewById(R.id.image);
          //  itemView.setOnClickListener(this);

        }


    }

}
