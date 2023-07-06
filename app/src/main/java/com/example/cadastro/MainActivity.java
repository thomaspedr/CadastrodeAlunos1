package com.example.cadastro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import android.text.TextUtils;

public class MainActivity extends AppCompatActivity {

    private EditText nome;
    private EditText cpf;
    private EditText telefone;

    private AlunoDAO dao;

    private Aluno aluno = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // componentes da interface
        nome = findViewById(R.id.editNome);
        cpf = findViewById(R.id.editCPF);
        telefone = findViewById(R.id.editTelefone);
        dao = new AlunoDAO(this);

        Intent it = getIntent();
        if (it.hasExtra("aluno")) {
            aluno = (Aluno) it.getSerializableExtra("aluno");
            if (aluno != null) {

                // preenche os campos com os dados do aluno
                nome.setText(aluno.getNome());
                cpf.setText(aluno.getCpf());
                telefone.setText(aluno.getTelefone());
            }
        }
    }
    // salvar os dados do aluno
    public void salvar(View view) {
        String nomeText = nome.getText().toString();
        String cpfText = cpf.getText().toString();
        String telefoneText = telefone.getText().toString();

        if (!TextUtils.isEmpty(nomeText) && !TextUtils.isEmpty(cpfText) && !TextUtils.isEmpty(telefoneText)) {
            if (aluno == null) {
                aluno = new Aluno();
            }
            aluno.setNome(nomeText);
            aluno.setCpf(cpfText);
            aluno.setTelefone(telefoneText);

            if (aluno.getId() != null) {

                // atualiza o aluno no banco de dados
                dao.atualizar(aluno);
                Toast.makeText(this, "Aluno atualizado com sucesso", Toast.LENGTH_SHORT).show();
            } else {

                // insere o aluno no banco de dados
                long id = dao.inserir(aluno);
                Toast.makeText(this, "Aluno inserido: " + id, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }
    }
}
