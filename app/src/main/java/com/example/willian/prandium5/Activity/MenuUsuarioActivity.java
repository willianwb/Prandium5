package com.example.willian.prandium5.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.willian.prandium5.R;
import com.google.firebase.auth.FirebaseAuth;

public class MenuUsuarioActivity extends AppCompatActivity {

    private Button btnsair;
    private Button VerPromocao;
    private Button Pedido;
    private Button MapadeCalor;
    private Button addCred;
    private Button Historico;
    private Button VerPerfil;
    private Button FazerPedido;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_usuario);

        FazerPedido = (Button) findViewById(R.id.btnFazerPedido);

        VerPerfil = (Button) findViewById(R.id.btnVerPerfil) ;

        btnsair = (Button) findViewById(R.id.btnSair);
        autenticacao = FirebaseAuth.getInstance();

        btnsair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deslogarUsuario();
            }
        });

        VerPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MeuPerfil();
            }
        });

        FazerPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTelaCardapioUsuario();
            }
        });



    }

    private void abrirTelaInicial(){
        Intent intent = new Intent(MenuUsuarioActivity.this,MainActivity.class);
        startActivity(intent);
    }
    private void MeuPerfil(){
        Intent intent = new Intent(MenuUsuarioActivity.this,MeuPerfilActivity.class);
        startActivity(intent);
    }

    private void deslogarUsuario(){
        Toast.makeText(MenuUsuarioActivity.this,"Usuário deslogado com sucesso!", Toast.LENGTH_LONG).show();
        autenticacao.signOut();
        abrirTelaInicial();
        finish();
    }

    private void abrirTelaCardapioUsuario(){
        Intent intent = new Intent(MenuUsuarioActivity.this,CardapioActivity.class);
        startActivity(intent);
    }
    private void abrirTelaAddCreditos(){
        Intent intent = new Intent(MenuUsuarioActivity.this,AddCreditosActivity.class);
        startActivity(intent);
    }
}
