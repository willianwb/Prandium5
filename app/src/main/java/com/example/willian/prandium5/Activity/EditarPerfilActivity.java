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

import org.w3c.dom.Text;

public class EditarPerfilActivity extends AppCompatActivity {

    private EditText edtEditNome;
    private EditText edtEditCpf;
    private EditText edtEditSenha;
    private EditText edtEditConfirmarSenha;

    private TextView textEmail;

    private Button btnCancelar;
    private Button btnGravar;

    private String txtorigem = "";
    private String txtnome= "";
    private String txtCPF= "";
    private String txtkeyUsuario= "";
    private String id_tipo= "";
    private Float saldo= (float)0.0;
    private String email= "";

    private DatabaseReference reference;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);


        edtEditNome = (EditText) findViewById(R.id.edtEditNome);
        edtEditCpf = (EditText) findViewById(R.id.edtEditCPF);
        edtEditSenha = (EditText) findViewById(R.id.edtEditSenha);
        edtEditConfirmarSenha = (EditText) findViewById(R.id.edtEditConfirmarSenha);

        textEmail = (TextView) findViewById(R.id.textEMAIL);

        btnGravar = (Button) findViewById(R.id.btnGRAVAR);
        btnCancelar = (Button) findViewById(R.id.btnCANCELAR);


        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();

        txtorigem = bundle.getString("origem");
        if(txtorigem.equals("editarUsuario")){
            txtnome = bundle.getString("nome");
            txtCPF = bundle.getString("cpf");
            txtkeyUsuario = bundle.getString("keyUsuario");
            id_tipo = bundle.getString("id_tipo");
            saldo = bundle.getFloat("saldo");
            email = bundle.getString("email");


            edtEditNome.setText(txtnome.toString());
            edtEditCpf.setText(txtCPF.toString());
            textEmail.setText(email);
        }


        btnGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtEditSenha.getText().toString().equals(edtEditConfirmarSenha.getText().toString())) {
                    Usuario usuario = new Usuario();
                    usuario.setNome(edtEditNome.getText().toString());
                    usuario.setCPF(edtEditCpf.getText().toString());
                    usuario.setKeyUsuario(txtkeyUsuario);
                    usuario.setSenha(edtEditSenha.getText().toString());
                    usuario.setSaldo(saldo);
                    usuario.setEmail(email);
                    usuario.setID_TIPO(id_tipo);

                    atualizaDados(usuario);
                }else{
                    Toast.makeText(EditarPerfilActivity.this,"As senhas não conferem!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTelaPrincipal();
            }
        });

    }

    private void abrirTelaPrincipal(){
        Intent intent = new Intent(EditarPerfilActivity.this,MeuPerfilActivity.class);
        startActivity(intent);
        finish();
    }

    private void abrirMainActivity(){
        Intent intent = new Intent(EditarPerfilActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean atualizaDados(final Usuario usuario){
        btnGravar.setEnabled(false);
        try{
            reference = ConfiguracaoFirebase.getFirebase().child("usuarios");
            atualizarSenha(usuario.getSenha());

            reference.child(txtkeyUsuario).setValue(usuario);
            Toast.makeText(EditarPerfilActivity.this,"Dados alterados com sucesso!",Toast.LENGTH_LONG).show();
            FirebaseAuth.getInstance().signOut();
            abrirMainActivity();

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
}
