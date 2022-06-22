package com.example.petapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.petapp.R;
import com.example.petapp.DAO.ImagemDAO;
import com.example.petapp.helpers.FormularioHelper;
import com.example.petapp.Model.Imagem;

import java.io.File;

public class FormularioActivity extends Activity {

    private FormularioHelper helper;
    private String localArquivoFoto;
    private static final int TIRA_FOTO = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        Intent intent = getIntent();
        final Imagem imagemParaSerAlterado = (Imagem) intent.getSerializableExtra("imagemSelecionado");

        this.helper = new FormularioHelper(this);

        Button post = (Button) findViewById(R.id.post);
        Button cancelar = (Button) findViewById(R.id.cancel);

        if (imagemParaSerAlterado != null) {
            post.setText("Alterar");
            helper.colocaImagemNoFormulario(imagemParaSerAlterado);
        }

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Imagem imagem = helper.pegaImagemDoFormulario();

                ImagemDAO dao = new ImagemDAO(FormularioActivity.this);

                if (imagemParaSerAlterado == null) {
                    dao.insere(imagem);
                } else {
                    imagem.setId(imagemParaSerAlterado.getId());
                    dao.altera(imagem);
                }

                dao.close();

                finish();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView foto = helper.getBotaoImagem();
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irParaImagens = new Intent();
                irParaImagens.setType("image/*");
                irParaImagens.setAction(irParaImagens.ACTION_GET_CONTENT);
                startActivityForResult(irParaImagens.createChooser(irParaImagens, "Selecione uma Imagem"), 123);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == TIRA_FOTO) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imagemSelecionada = data.getData();
                helper.carregaImagem(imagemSelecionada.toString());
            }
        }
    }
}
