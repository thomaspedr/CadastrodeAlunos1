package com.example.cadastro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    private Conexao conexao;
    private SQLiteDatabase banco;

    public AlunoDAO (Context context){
        conexao = new Conexao (context); // conexão com o banco de dados
        banco = conexao.getWritableDatabase();
    }
    public long inserir(Aluno aluno){ // inserir um aluno no banco de dados
        ContentValues values = new ContentValues();
        values.put ("nome", aluno.getNome());
        values.put ("cpf", aluno.getCpf());
        values.put ("telefone", aluno.getTelefone());
        return banco.insert ("aluno", null, values); // insere os valores no banco de dados

    }

    public List<Aluno> obterTodos(){
        List<Aluno> alunos = new ArrayList<>();

        // executa uma consulta no banco de dados para obter todos os registros da tabela "aluno"

        Cursor cursor = banco.query("aluno",new String[]{"id", "nome", "cpf", "telefone"},
                null, null, null, null, null);

        while (cursor.moveToNext()){
            Aluno a = new Aluno ();
            a.setId(cursor.getInt(0));
            a.setNome(cursor.getString(1));
            a.setCpf(cursor.getString(2));
            a.setTelefone(cursor.getString(3));
            alunos.add(a);

        }
        return  alunos;

    }
    // excluir um aluno do banco de dados
    public void exlcuir(Aluno a) {
        banco.delete("aluno", "id = ?", new String[]{a.getId().toString()});

    }
    // atualizar os dados de um aluno no banco de dados
    public void atualizar (Aluno aluno){

        if (aluno != null) {
            ContentValues values = new ContentValues();
            if (aluno.getNome() != null) {
                values.put("nome", aluno.getNome());
            }
            if (aluno.getCpf() != null) {
                values.put("cpf", aluno.getCpf());
            }
            if (aluno.getTelefone() != null) {
                values.put("telefone", aluno.getTelefone());
            }
            if (aluno.getId() != null) {
                banco.update("aluno", values, "id = ?", new String[]{aluno.getId().toString()});
            }
        }




    }


}
