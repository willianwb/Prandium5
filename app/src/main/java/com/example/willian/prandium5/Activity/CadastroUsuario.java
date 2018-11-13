package com.example.willian.prandium5.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.willian.prandium5.Classes.Usuario;
import com.example.willian.prandium5.DAO.ConfiguracaoFirebase;
import com.example.willian.prandium5.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroUsuario extends AppCompatActivity {

    private EditText cademail;
    private EditText cadsenha;
    private EditText cadnome;
    private EditText cadcpf;
    private EditText csenha;
    private RadioButton rbUsuario;
    private RadioButton rbLojista;
    private Button btnCadastrar;
    private Button btnSair;
    private FirebaseAuth autenticacao;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);


        cademail = (EditText) findViewById(R.id.edtCadEmail);
        cadsenha = (EditText) findViewById(R.id.edtCadSenha);
        cadnome = (EditText) findViewById(R.id.edtCadNome);
        cadcpf = (EditText) findViewById(R.id.edtCadCPF);
        csenha = (EditText) findViewById(R.id.edtCadConfirmarSenha);
        rbLojista = (RadioButton) findViewById(R.id.radioLOJISTA);
        rbUsuario = (RadioButton) findViewById(R.id.radioUSUARIO);
        btnCadastrar = (Button) findViewById(R.id.btnGravar);
        btnSair = (Button) findViewById(R.id.btnCancelarCad);


        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cadsenha.getText().toString().equals(csenha.getText().toString())){
                    usuario = new Usuario();

                    usuario.setEmail(cademail.getText().toString());
                    usuario.setSenha(cadsenha.getText().toString());
                    usuario.setNome(cadnome.getText().toString());
                    usuario.setCPF(cadcpf.getText().toString());
                    usuario.setSaldo((float)0.0);

                    if(rbLojista.isChecked()){
                        usuario.setID_TIPO("Lojista");
                    }else{
                        usuario.setID_TIPO("Usuario");
                    }

                    cadastrarUsuario();
                    //Intent intent = new Intent(CadastroUsuario.this,MainActivity.class);
                    //startActivity(intent);
                }else{
                    Toast.makeText(CadastroUsuario.this,"As senhas não correspondem!",Toast.LENGTH_LONG).show();
                }

            }
        });

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroUsuario.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

    private void cadastrarUsuario(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(CadastroUsuario.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    insereUsuario(usuario);
                }else{

                    String erroExcecao="";

                    try{
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        erroExcecao = "Digite uma senha mais forte, contendo no mínimo 8 caracteres e que contenha letras e numeros!";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erroExcecao = "O e-mail digitado é inválido,Digite um novo e-mail!";
                    }catch (FirebaseAuthUserCollisionException e){
                        erroExcecao = "Esse e-mail já está cadastrado!";
                    }catch (Exception e){
                        erroExcecao = "Erro ao efetuar o cadastro!";
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroUsuario.this,"Erro: " + erroExcecao, Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private boolean insereUsuario(Usuario usuario){
        try{
            reference = ConfiguracaoFirebase.getFirebase().child("usuarios");
            reference.push().setValue(usuario);
            Toast.makeText(CadastroUsuario.this,"Usuário cadastrado com Sucesso!",Toast.LENGTH_LONG).show();
            return true;
        }catch (Exception e){
            Toast.makeText(CadastroUsuario.this,"Erro ao gravar usuário!",Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false;
        }
    }
}
