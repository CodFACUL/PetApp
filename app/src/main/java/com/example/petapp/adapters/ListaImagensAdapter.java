package com.example.petapp.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.petapp.R;
import com.example.petapp.DAO.ImagemDAO;
import com.example.petapp.activities.FormularioActivity;
import com.example.petapp.Model.Imagem;

import java.util.List;

public class ListaImagensAdapter extends BaseAdapter {

    private final List<Imagem> imagens;
    private final Activity activity;

    public ListaImagensAdapter(Activity activity, List<Imagem> imagens) {
        this.activity = activity;
        this.imagens = imagens;
    }

    public long getItemId(int posicao) {
        return imagens.get(posicao).getId();
    }

    public Object getItem(int posicao) {
        return imagens.get(posicao);
    }

    public int getCount() {
        return imagens.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.item, null);

        Imagem imagem = imagens.get(position);

        TextView nome = (TextView) view.findViewById(R.id.comentario_lista);
        nome.setText(imagem.getComentario());

//        Bitmap bm;
//
//        if (imagem.getFoto() != null) {
//            bm = BitmapFactory.decodeFile(imagem.getFoto());
//        } else {
//            bm = BitmapFactory.decodeResource(activity.getResources(),
//                    R.drawable.noimage);
//        }
//
//        bm = Bitmap.createScaledBitmap(bm, 100, 100, true);
//
        if (imagem.getFoto() != null) {
            ImageView foto = (ImageView) view.findViewById(R.id.foto_postada);
            foto.setImageURI(Uri.parse(imagem.getFoto()));
        }
        return view;
    }
    
}
