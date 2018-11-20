package com.example.willian.prandium5.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.willian.prandium5.DAO.ConfiguracaoFirebase;
import com.example.willian.prandium5.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VerMapaCalorActivity extends AppCompatActivity {

    private Button btnVoltar;
    private DatabaseReference reference;
    private String tipoUsuario;
    private String valorteste;

    private TextView txtBlocoVerde;
    private TextView txtBlocoAzul;
    private TextView txtBlocoAmarelo;
    private TextView txtBlocoLaranja;
    private TextView txtBlocoVermelho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_mapa_calor);

        reference = ConfiguracaoFirebase.getFirebase();

        btnVoltar = (Button) findViewById(R.id.btnVoltarMapaBloco);

        txtBlocoVerde = (TextView) findViewById(R.id.txtBlocoVerde);
        txtBlocoAzul = (TextView) findViewById(R.id.txtBlocoAzul);
        txtBlocoAmarelo = (TextView) findViewById(R.id.txtBlocoAmarelo);
        txtBlocoLaranja = (TextView) findViewById(R.id.txtBlocoLaranja);
        txtBlocoVermelho = (TextView) findViewById(R.id.txtBlocoVermelho);
        // vai ser " "+valorteste+" / 2";


        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                valorteste = dataSnapshot.getValue().toString();
                txtBlocoVerde.setText(valorteste);
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        FirebaseDatabase.getInstance().getReference().child("Bloco Verde").addValueEventListener(postListener);


        final String emailcheck = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child("usuarios").orderByChild("email").equalTo(emailcheck).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            tipoUsuario = postSnapshot.child("id_TIPO").getValue().toString();

                            if (tipoUsuario.equals("Lojista")) {
                                Intent intent = new Intent(VerMapaCalorActivity.this, MenuLojistaActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Intent intent = new Intent(VerMapaCalorActivity.this, MenuUsuarioActivity.class);
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
