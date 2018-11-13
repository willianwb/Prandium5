package com.example.willian.prandium5.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.willian.prandium5.R;
import com.google.firebase.auth.FirebaseAuth;

public class MenuUsuarioActivity extends AppCompatActivity {

    private Button btnsair;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_usuario);

        btnsair = (Button) findViewById(R.id.btnSair);
        autenticacao = FirebaseAuth.getInstance();

        btnsair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deslogarUsuario();
            }
        });

    }

    private void abrirTelaInicial(){
        Intent intent = new Intent(MenuUsuarioActivity.this,MainActivity.class);
        startActivity(intent);
    }

    private void deslogarUsuario(){
        autenticacao.signOut();
        abrirTelaInicial();
        finish();
    }
}
