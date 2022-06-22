package com.example.petapp.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.petapp.R;
import com.example.petapp.activities.FormularioActivity;
import com.example.petapp.Model.Imagem;

public class FormularioHelper {
    private CheckBox curtida;
    private EditText comentario;
    private ImageView foto;
    private Imagem imagem;

    public FormularioHelper(FormularioActivity activity) {

        comentario = (EditText) activity.findViewById(R.id.comentario);
        foto = (ImageView) activity.findViewById(R.id.foto);
        imagem = new Imagem();
    }

    public Imagem pegaImagemDoFormulario() {

        imagem.setComentario(comentario.getEditableText().toString());
//        imagem.setCurtida(curtida.isChecked());

        return imagem;
    }

    public void colocaImagemNoFormulario(Imagem imagem) {
        this.imagem = imagem;

        comentario.setText(imagem.getComentario());
        curtida.setChecked(imagem.getCurtida());

        if (imagem.getFoto() != null) {
            this.carregaImagem(imagem.getFoto());
        }
    }

    public ImageView getBotaoImagem() {
        return foto;
    }

    public void carregaImagem(String localArquivoFoto) {
        imagem.setFoto(localArquivoFoto);
        foto.setImageURI(Uri.parse(localArquivoFoto));
    }

}
