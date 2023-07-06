package com.example.cadastro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


public class ListarAlunos extends AppCompatActivity {




    private ListView listview;
    private AlunoDAO dao;
    private List<Aluno> alunos;
    private List<Aluno> alunosFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_alunos);

        // inicializa os componentes da interface

        listview = findViewById(R.id.list_alunos);
        dao = new AlunoDAO(this);
        alunos = dao.obterTodos(); // obtém a lista de alunos do banco de dados
        alunosFiltrados.addAll(alunos);

        // adapter personalizado para exibir os alunos na lista

        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, R.layout.item_aluno, alunosFiltrados) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.item_aluno, parent, false);
                }

                TextView textNome = convertView.findViewById(R.id.textNome);
                TextView textCpfTelefone = convertView.findViewById(R.id.textCpfTelefone);

                Aluno aluno = alunosFiltrados.get(position);
                textNome.setText(aluno.getNome());
                textCpfTelefone.setText("CPF: " + aluno.getCpf() + " - Telefone: " + aluno.getTelefone());

                return convertView;
            }
        };

        listview.setAdapter(adapter);
        registerForContextMenu(listview);



    }
    // método para criar o menu de opções na actionbar
    public boolean onCreateOptionsMenu (Menu menu){

        MenuInflater i = getMenuInflater();
        i.inflate (R.menu.menu_principal, menu);

        // configura o searchview para filtrar a lista de alunos
        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                procurarAluno(s);
                return false;
            }
        });

        return true;
    }
    // criar o menu de contexto ao pressionar e segurar um item da lista
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu,v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.meu_contexto, menu);

    }


    public void procurarAluno (String nome){
        alunosFiltrados.clear();
        for(Aluno a : alunos) {
            if (a.getNome() != null && nome != null && a.getNome().toLowerCase().contains(nome.toLowerCase())){
                alunosFiltrados.add(a);

            }
        }
        listview.invalidateViews();


    }

    public void excluir(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final  Aluno alunoExcluir = alunosFiltrados.get(menuInfo.position);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("deseja excluir?")
                .setNegativeButton("Não", null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alunosFiltrados.remove(alunoExcluir);
                        alunos.remove(alunoExcluir);
                        dao.exlcuir(alunoExcluir);
                        listview.invalidateViews();

                    }
                }).create();
        dialog.show();


    }
    public void cadastrar (MenuItem Item){
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }


    public void atualizar (MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final  Aluno alunoAtualizar = alunosFiltrados.get(menuInfo.position);
        Intent it = new Intent(this, MainActivity.class);
        it.putExtra("aluno", alunoAtualizar);
        startActivity(it);


    }

    @Override
    public  void onResume() {
        super.onResume();
        alunos = dao.obterTodos();
        alunosFiltrados.clear();
        alunosFiltrados.addAll(alunos);
        listview.invalidateViews();


    }

}

