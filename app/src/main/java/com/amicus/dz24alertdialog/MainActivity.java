package com.amicus.dz24alertdialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private static final String PREFS_NAME="AppLaunch"; //
    private static final String KEY_FIRST_LAUNCH="isFirstLaunch";
    List<Item> itemsProducts;
    Toolbar toolbar;
    RecyclerView recyclerView;
    ListAdapter listAdapter;
    ListAdapter.OnItemClickListener itemClickListener;
    // ArrayAdapter<>
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        fillItemsProducts();
        recyclerView= findViewById(R.id.shopping_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemClickListener = new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Item item, int position,View itemView) {
                itemSelected(item, position,itemView);
            }
        };
        listAdapter = new ListAdapter(itemsProducts,itemClickListener);
        recyclerView.setAdapter(listAdapter);
        toolbar.setTitle("Записей: "+itemsProducts.size());
        if(isFirstLaunch()){
            createDialog();
        }

        setSupportActionBar(toolbar); // активируем toolbar
    }
    List<View>listView = new ArrayList<>();  // вспомогательный список
    void itemSelected(Item item, int position,View itemView){  // устанавливаем флаг выбора в элементе и меняем цвет фона
        item.setChoice(!item.isChoice());                      // наверно нужно переделать по правильному по позже
        if(item.isChoice()){
            itemView.setBackgroundColor(R.drawable.ic_launcher_background);
            listView.add(itemView);
        }
        else {
            itemView.setBackgroundColor(500003);
            listView.remove(itemView);
        }
    }

    void fillItemsProducts(){          // заполняем список покупок
        itemsProducts = new ArrayList<>();
        itemsProducts.add(new Item(R.drawable.produkty,"Хлеб","1шт"));
        itemsProducts.add(new Item(R.drawable.produkty,"Кефир","1шт"));
        itemsProducts.add(new Item(R.drawable.produkty,"Картофель","3кг"));
        itemsProducts.add(new Item(R.drawable.produkty,"Яблоки","2кг"));
        itemsProducts.add(new Item(R.drawable.produkty,"Сок","1шт"));
        itemsProducts.add(new Item(R.drawable.produkty,"Сыр","1шт"));
        itemsProducts.add(new Item(R.drawable.produkty,"Йогурт","4шт"));
        itemsProducts.add(new Item(R.drawable.produkty,"Мясо","1,5кг"));
        itemsProducts.add(new Item(R.drawable.produkty,"Бекон","1шт"));
        itemsProducts.add(new Item(R.drawable.produkty,"Творог","2шт"));
        itemsProducts.add(new Item(R.drawable.produkty,"Пиво","6шт"));
        itemsProducts.add(new Item(R.drawable.produkty,"Вода","2шт"));}
    ////////////toolBar//////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu,menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        getMenuInflater().inflate(R.menu.delete_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Toast.makeText(MainActivity.this,"Settings choice",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_exit:
                finish();
                return true;
            case R.id.action_delete:   // - удаление элементов
                if(itemsProducts.stream().filter(Item::isChoice).findAny().isEmpty()){
                    Toast.makeText(MainActivity.this,"" +
                            "Выберите элемент для удаления из списка",Toast.LENGTH_LONG).show();
                }else{
                    createDialog(); // показываем диалог подтверждения удаления выбранных элементов
                }
                return true;
            case R.id.action_add:  //  + добавление элементов
                Intent intent = new Intent(MainActivity.this,EditActivity.class);
                startActivityForResult(intent,100);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //////////////////////////////////////////////////////
    private boolean isFirstLaunch(){
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
      return   preferences.getBoolean(KEY_FIRST_LAUNCH,true);

    }
    private void setFirstLaunchFalse(){
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_FIRST_LAUNCH,false);
        editor.apply(); ////
    }

    public void createDialog(){              // диалог удаления элементов списка
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage("Удалить выбранные элементы");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ArrayList<Item> collect = itemsProducts.parallelStream() //получаем коллекцию выбранных елементов для удаления
                        .filter(Item::isChoice)
                        .collect(Collectors
                                .toCollection(ArrayList::new));
                // listAdapter.deleteItemByModel(collect);
                itemsProducts.removeAll(collect);
                listAdapter.notifyDataSetChanged();
                toolbar.setTitle(itemsProducts.size()+" Записей");
                listView.forEach(v->{v.setBackgroundColor(500003);}); //крайне корявый способ т.к. не нашёл как получить по другому доступ к вьюжкам:(
            }
        });
        builder.setCancelable(false);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode==101 &&data!=null){
            var product = data.getStringExtra("product");
          var quantity =data.getStringExtra("quantity");
             itemsProducts.add(new Item(R.drawable.produkty,product,quantity));
             listAdapter.notifyDataSetChanged();
//           listAdapter.addItem(new Item(R.drawable.produkty,product,quantity));
            toolbar.setTitle("Записей: "+itemsProducts.size());

        }
    }


}