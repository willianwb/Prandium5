package com.example.willian.prandium5.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.willian.prandium5.Classes.Usuario;
import com.example.willian.prandium5.DAO.ConfiguracaoFirebase;
import com.example.willian.prandium5.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MeuPerfilActivity extends AppCompatActivity {

    private TextView txtNome;
    private TextView txtCPF;
    private TextView txtEMAIL;
    private TextView txtTIPO;
    private TextView txtSALDO;

    private String tipoUsuario;

    private Button btnEditar;
    private Button btnExlcuirConta;
    private Button Voltar;

    private FirebaseAuth autenticacao;
    private DatabaseReference reference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meu_perfil);

        final String emailcheck = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        reference = ConfiguracaoFirebase.getFirebase();

        txtNome = (TextView) findViewById(R.id.txtNomeUsuario);
        txtCPF = (TextView) findViewById(R.id.txtCPFUsuario);
        txtEMAIL = (TextView) findViewById(R.id.txtEMAILUsuario);
        txtTIPO = (TextView) findViewById(R.id.txtTIPOUsuario);
        txtSALDO = (TextView) findViewById(R.id.txtSALDOUsuario);


        btnEditar = (Button) findViewById(R.id.btnEditar);
        btnExlcuirConta = (Button) findViewById(R.id.btnExcluirConta);
        Voltar = (Button) findViewById(R.id.btnVoltar);


        reference.child("usuarios").orderByChild("email").equalTo(emailcheck).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Usuario usuario = postSnapshot.getValue(Usuario.class);

                    txtNome.setText(usuario.getNome());
                    txtCPF.setText(usuario.getCPF());
                    txtEMAIL.setText(usuario.getEmail());
                    txtTIPO.setText(usuario.getID_TIPO());
                    txtSALDO.setText(usuario.getSaldo().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child("usuarios").orderByChild("email").equalTo(emailcheck).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                            tipoUsuario = postSnapshot.child("id_TIPO").getValue().toString();

                            if(tipoUsuario=="Lojista"){
                                Intent intent = new Intent(MeuPerfilActivity.this, MenuLojistaActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Intent intent = new Intent(MeuPerfilActivity.this, MenuUsuarioActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
