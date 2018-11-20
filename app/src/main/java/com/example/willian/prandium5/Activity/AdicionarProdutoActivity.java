package com.example.willian.prandium5.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.willian.prandium5.R;

public class AdicionarProdutoActivity extends AppCompatActivity {

    private Button voltarAnterior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_produto);

        voltarAnterior = (Button) findViewById(R.id.btnAnteriorCardapio);

        voltarAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTelaCardapio();
            }
        });




    }

    private void abrirTelaCardapio(){
        Intent intent = new Intent(AdicionarProdutoActivity.this,CardapioLojistaActivity.class);
        startActivity(intent);
        finish();
    }

}
