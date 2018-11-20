package com.example.willian.prandium5.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class AddCreditosActivity extends AppCompatActivity {

    private EditText edtCreditos;
    private EditText edtAddSenha;
    private EditText edtAddConfirmarSenha;


    private String txtorigem = "";
    private String txtnome= "";
    private String txtCPF= "";
    private String txtkeyUsuario= "";
    private String id_tipo= "";
    private Float saldo= (float)0.0;
    private Float saldo2= (float)0.0;
    private String email= "";
    private String saldostring;

    private DatabaseReference reference;
    private FirebaseAuth autenticacao;

    private Button VoltarMenuPrincipal;
    private Button AddCreditos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_creditos);

        AddCreditos = (Button) findViewById(R.id.btnConfirmarAdd);


        VoltarMenuPrincipal = (Button) findViewById(R.id.btnAnteriorMenuAdd);

        edtAddSenha = (EditText) findViewById(R.id.edtAddSenha);
        edtAddConfirmarSenha = (EditText) findViewById(R.id.edtAddConfirmarSenha);
        edtCreditos = (EditText) findViewById(R.id.edtCreditos);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();


        txtorigem = bundle.getString("origem");
        if(txtorigem.equals("adicionarCreditos")){
            txtnome = bundle.getString("nome");
            txtCPF = bundle.getString("cpf");
            txtkeyUsuario = bundle.getString("keyUsuario");
            id_tipo = bundle.getString("id_tipo");
            email = bundle.getString("email");
            saldo2 = bundle.getFloat("saldo");
        }

        VoltarMenuPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VoltarTelaVerPerfil();


            }
        });

        AddCreditos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtCreditos.getText().toString().equals("")){
                    saldostring = edtCreditos.getText().toString();
                    saldo = Float.parseFloat(saldostring);
                    if(saldo>=10){
                        if (edtAddSenha.getText().toString().equals(edtAddConfirmarSenha.getText().toString())) {
                            Usuario usuario = new Usuario();
                            usuario.setNome(txtnome.toString());
                            usuario.setCPF(txtCPF.toString());
                            usuario.setKeyUsuario(txtkeyUsuario);
                            usuario.setSenha(edtAddSenha.getText().toString());
                            usuario.setSaldo(saldo+saldo2);
                            usuario.setEmail(email);
                            usuario.setID_TIPO(id_tipo);

                            atualizaDados(usuario);
                        }else{
                            Toast.makeText(AddCreditosActivity.this,"As senhas não conferem!", Toast.LENGTH_LONG).show();
                        }

                    }else{
                        Toast.makeText(AddCreditosActivity.this,"Valor muito baixo, Adicione mais de 10 R$!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }



    private boolean atualizaDados(final Usuario usuario){
        AddCreditos.setEnabled(false);
        try{
            reference = ConfiguracaoFirebase.getFirebase().child("usuarios");

            reference.child(txtkeyUsuario).setValue(usuario);
            Toast.makeText(AddCreditosActivity.this,"VALOR ADICIONADO COM SUCESSO!",Toast.LENGTH_LONG).show();

        }catch (Exception e){
            e.printStackTrace();
        }



        return true;
    }

    private void atualizarSenha(String senhaNova){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updatePassword(senhaNova)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d("Nova_senha_atualizada","Senha atualizadas com sucesso!");

                        }
                    }
                });
    }

    private void VoltarTelaVerPerfil(){
        Intent intent = new Intent(AddCreditosActivity.this, MeuPerfilActivity.class);
        startActivity(intent);
        finish();
    }

}
