package com.example.petapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.petapp.Model.Imagem;

import java.util.ArrayList;
import java.util.List;

public class ImagemDAO extends SQLiteOpenHelper {
    //configurações de versão da base de dados que será criada
    private static final int VERSAO = 1;
    //nome da tabela que será criada e utilizada nos scripts
    private static final String TABELA = "CadastroImagems";
    //nome da base de dados
    private static final String DATABASE = "CadastroBSN";
    //colunas da tabela ser criada no script
    private static final String[] COLUNAS = { "id", "comentario",
            "curtida", "foto" };

    public ImagemDAO(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //comando DDL para criação da tabela SQLite
        String ddl = "CREATE TABLE " + TABELA + " (id INTEGER PRIMARY KEY, "
                + " comentario TEXT, curtida BOOLEAN, foto TEXT);";

        db.execSQL(ddl);

    }

    @Override
    //Esse método é executado automaticamente pelo sistema android, quando ele detectar que a versão do banco de dados mudou
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABELA;
        db.execSQL(sql);
        onCreate(db);
    }

    //método utilizado para inserir um novo cadastro no banco de dados SQLite. Esse método irá receber uma instância do Modelo Imagem por parâmetro.
    public void insere(Imagem imagem) {
        ContentValues values = new ContentValues();

        values.put("comentario", imagem.getComentario());
        values.put("curtida", imagem.getCurtida());
        values.put("foto", imagem.getFoto());

        //A linha abaixo é responsável para solicitar ao Android que salve as informações do imagem. Esse método é possível de ser executado pois a classe extends SQLiteOpenHelper, que é uma superclasse do android que abstrai várias funcionalidade de acesso a dados no banco de dados SQLite.
        getWritableDatabase().insert(TABELA, null, values);
    }
    //método utilizado para listar os Imagems cadastrados na base de dados SQLite. Este método realiza uma consulta na base de dados e devolve uma ArrayList com os imagems recuperados.
    public List<Imagem> getLista() {

        //ArraList criado para o retorno dos dados
        List<Imagem> imagems = new ArrayList<>();

        //no Android é criado um cursor utilizando a superclasse, com a finalidade de buscar os dados da base de dados SQLite
        Cursor c = getWritableDatabase().query(TABELA, COLUNAS, null,
                null, null, null, null);

        //após abrir o cursor, deve-se percorrê-lo para popular o ArrayList que será retornado.
        while (c.moveToNext()) {
            Imagem imagem = new Imagem();

            imagem.setId(c.getLong(0));
            imagem.setComentario(c.getString(1));
            imagem.setCurtida(Boolean.getBoolean(c.getString(2)));
            imagem.setFoto(c.getString(3));

            imagems.add(imagem);
        }

        //após percorrer o cursor, deve-se fechar o mesmo.
        c.close();

        //por fir retorna-se o ArrayList com os dados recuperados
        return imagems;
    }

    //abaixo o método DAO para deletar imagems cadastrados na base de dados.
    public void deletar(Imagem imagem) {
        String[] args = {imagem.getId().toString()};
        getWritableDatabase().delete(TABELA, "id=?", args);
    }

    //método utilizado para realizar alterações nos registros do banco de dados SQLite. Essemétodo irá receber um objeto imagem por parâmetro e então realizar a alteração na base de dados.
    public void altera(Imagem imagem) {

        //Para realizar a alteraçãoao dos dados na base de dados SQLite é necessário
        ContentValues values = new ContentValues();

        values.put("comentario", imagem.getComentario());
        values.put("curtida", imagem.getCurtida());
        values.put("foto", imagem.getFoto());

        String[] args = {imagem.getId().toString()};
        getWritableDatabase().update(TABELA, values, "id=?", args );
    }

    public boolean isImagem(String telefone) {
        Cursor rawQuery = getReadableDatabase().rawQuery(
                "SELECT telefone from " + TABELA + " WHERE telefone = ?",
                new String[] { telefone });

        int total = rawQuery.getCount();
        rawQuery.close();

        return total > 0;
    }

}
