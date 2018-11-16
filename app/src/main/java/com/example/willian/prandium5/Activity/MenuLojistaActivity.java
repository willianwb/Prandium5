package com.example.willian.prandium5.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.willian.prandium5.R;
import com.google.firebase.auth.FirebaseAuth;

public class MenuLojistaActivity extends AppCompatActivity {

    private Button btnsair;
    private Button VerPromocao;
    private Button VerCardapio;
    private Button MapadeCalor;
    private Button VerPerfil;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_lojista);

        btnsair = (Button) findViewById(R.id.btnSairLojista);

        VerCardapio = (Button) findViewById(R.id.btnVerCardapioLojista);

        VerCardapio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTelaCardapioLojista();
            }
        });

        autenticacao = FirebaseAuth.getInstance();

        VerPerfil = (Button) findViewById(R.id.btnVerPerfil) ;

        VerPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MeuPerfil();
            }
        });


        btnsair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deslogarUsuario();
            }
        });
    }

    private void abrirTelaInicial(){
        Intent intent = new Intent(MenuLojistaActivity.this,MainActivity.class);
        startActivity(intent);
    }
    private void abrirTelaCardapioLojista(){
        Intent intent = new Intent(MenuLojistaActivity.this,CardapioActivity.class);
        startActivity(intent);
    }

    private void deslogarUsuario(){
        Toast.makeText(MenuLojistaActivity.this,"Usuário deslogado com sucesso!", Toast.LENGTH_LONG).show();
        autenticacao.signOut();
        abrirTelaInicial();
        finish();
    }

    private void MeuPerfil(){
        Intent intent = new Intent(MenuLojistaActivity.this,MeuPerfilActivity.class);
        startActivity(intent);
    }

}