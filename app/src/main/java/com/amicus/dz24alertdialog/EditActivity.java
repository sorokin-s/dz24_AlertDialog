package com.amicus.dz24alertdialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class EditActivity extends AppCompatActivity {
    Button btnAdd,btnBack;
    EditText editTextProduct, editTextQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
    btnAdd = findViewById(R.id.btnAdd);
    btnBack= findViewById(R.id.btnBack);
    editTextProduct =findViewById(R.id.editProduct);
    editTextQuantity = findViewById(R.id.editQuantity);

    btnAdd.setOnClickListener(b->{
        if(!editTextProduct.getText().toString().isEmpty()) {
            createDialog();
        }
        else{
            Toast.makeText(EditActivity.this,"Заполните поле продукт",Toast.LENGTH_LONG).show();
        }
    });

    btnBack.setOnClickListener(b->{finish();});
    }
    public void createDialog(){// диалог добавления элемента в список
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage("Добавить:\n"+
                editTextProduct.getText().toString());
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(EditActivity.this,MainActivity.class);
                intent.putExtra("product",editTextProduct.getText().toString());
                intent.putExtra("quantity",editTextQuantity.getText().toString());
                setResult(101,intent);
                finish();
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
}