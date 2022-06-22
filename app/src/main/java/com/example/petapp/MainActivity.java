package com.example.petapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.petapp.DAO.ImagemDAO;
import com.example.petapp.activities.FormularioActivity;
import com.example.petapp.Model.Imagem;
import com.example.petapp.adapters.ListaImagensAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listaImagems;


    private Button postar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postar = (Button) findViewById(R.id.postar);
        listaImagems = (ListView) findViewById(R.id.lista_imagens);
//        registerForContextMenu(postar);
        postar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(MainActivity.this, FormularioActivity.class);
                startActivity(intent);
            }

        });

    }

    protected void onResume() {
        this.carregaLista();
        super.onResume();
    }

    private void carregaLista() {

        List<Imagem> imagens;
        ImagemDAO dao = new ImagemDAO(this);
        imagens = dao.getLista();
        dao.close();
        Log.d("tamanho imagem", ""+imagens.size());
        ListaImagensAdapter adapter = new ListaImagensAdapter(this, imagens);


        listaImagems.setAdapter(adapter);


    }
}