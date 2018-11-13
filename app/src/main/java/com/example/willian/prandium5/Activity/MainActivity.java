package com.example.willian.prandium5.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.willian.prandium5.Classes.Usuario;
import com.example.willian.prandium5.DAO.ConfiguracaoFirebase;
import com.example.willian.prandium5.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private EditText edtEmailLogin;
    private EditText edtSenhaLogin;
    private TextView edtAbreCadastro;
    private Button btnLogin;
    private Usuario usuario;

    private DatabaseReference referenciaFirebase;
    private String tipoUsuario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmailLogin = (EditText) findViewById(R.id.edtEmail);
        edtSenhaLogin = (EditText) findViewById(R.id.edtSenha);
        btnLogin = (Button) findViewById(R.id.btnLogar);
        edtAbreCadastro = (TextView) findViewById(R.id.edtAbreCadastro);

        if(usuarioLogado()){

            String email = autenticacao.getCurrentUser().getEmail().toString();

            referenciaFirebase = FirebaseDatabase.getInstance().getReference();

            referenciaFirebase.child("usuario").orderByChild("email").equalTo(email.toString()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                        tipoUsuario = postSnapshot.child("ID_TIPO").getValue().toString();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            if(tipoUsuario.equals("Lojista")){
                Intent intentMinhaConta = new Intent(MainActivity.this,MenuLojistaActivity.class);
                abrirNovaActivity(intentMinhaConta);
            }else{
                Intent intentMinhaConta = new Intent(MainActivity.this,MenuUsuarioActivity.class);
                abrirNovaActivity(intentMinhaConta);
            }
        }else {
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!edtEmailLogin.getText().toString().equals("") && !edtSenhaLogin.getText().toString().equals("")) {
                        usuario = new Usuario();

                        usuario.setEmail(edtEmailLogin.getText().toString());
                        usuario.setSenha(edtSenhaLogin.getText().toString());

                        validarLogin();
                    } else {
                        Toast.makeText(MainActivity.this, "Preencha os campos de E-mail e Senha!", Toast.LENGTH_LONG).show();
                    }
                }
            });

            edtAbreCadastro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    abreCadastroUsuario();
                }
            });
        }
    }



    private void validarLogin(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail().toString(),usuario.getSenha().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    Toast.makeText(MainActivity.this,"Login Efetuado com sucesso!",Toast.LENGTH_LONG).show();

                    String email = autenticacao.getCurrentUser().getEmail().toString();

                    referenciaFirebase = FirebaseDatabase.getInstance().getReference();

                    referenciaFirebase.child("usuario").orderByChild("email").equalTo(email.toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                                tipoUsuario = postSnapshot.child("ID_TIPO").getValue().toString();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                    if(tipoUsuario.equals("Lojista")){
                        Intent intentMinhaConta = new Intent(MainActivity.this,MenuLojistaActivity.class);
                        abrirNovaActivity(intentMinhaConta);
                    }else{
                        Intent intentMinhaConta = new Intent(MainActivity.this,MenuUsuarioActivity.class);
                        abrirNovaActivity(intentMinhaConta);
                    }

                }else{
                    Toast.makeText(MainActivity.this,"Usuário ou Senha Inválidos! Tente Novamente!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void abreCadastroUsuario() {
        Intent intent = new Intent(MainActivity.this, CadastroUsuario.class);
        startActivity(intent);

    }

    public Boolean usuarioLogado(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if ( user != null){
            return true;
        }else{
            return false;
        }
    }

    public void abrirNovaActivity(Intent intent){
        startActivity(intent);
    }
}
