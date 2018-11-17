package com.example.willian.prandium5.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.willian.prandium5.Adapter.CardapioAdapter;
import com.example.willian.prandium5.Classes.Cardapio;
import com.example.willian.prandium5.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CardapioLojistaActivity extends AppCompatActivity {

    private RecyclerView mRecyclerViewCardapio;

    private CardapioAdapter adapter;
    private List<Cardapio> cardapios;
    private DatabaseReference referenciaFireBase;
    private Cardapio todosCardapios;
    private LinearLayoutManager mLayoutManagerTodosProdutos;
    private Button Voltar;

    private Button AdicionarProduto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio_lojista);

        mRecyclerViewCardapio = (RecyclerView) findViewById(R.id.recycleViewTodosProdutos);

        carregarTodosProdutos();

        AdicionarProduto = (Button) findViewById(R.id.btnAdicionarProdutos);

        Voltar = (Button) findViewById(R.id.btnVoltarMenuLojista);

        Voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirMenuLojista();
            }
        });

        AdicionarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CardapioLojistaActivity.this,AdicionarProdutoActivity.class);
                startActivity(intent);
            }
        });



    }

    private void carregarTodosProdutos(){
        mRecyclerViewCardapio.setHasFixedSize(true);

        mLayoutManagerTodosProdutos = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        mRecyclerViewCardapio.setLayoutManager(mLayoutManagerTodosProdutos);

        cardapios = new ArrayList<>();

        referenciaFireBase = FirebaseDatabase.getInstance().getReference();


        referenciaFireBase.child("cardapio").orderByChild("nomePrato").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    todosCardapios = postSnapshot.getValue(Cardapio.class);

                    cardapios.add(todosCardapios);



                }

                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter = new CardapioAdapter(cardapios,this);

        mRecyclerViewCardapio.setAdapter(adapter);
    }

    private void abrirMenuLojista(){
        Intent intent = new Intent(CardapioLojistaActivity.this,MenuLojistaActivity.class);
        startActivity(intent);
        finish();
    }
}
