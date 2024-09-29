package com.example.primeiro_trabalho;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Nome: Estêvão Fonseca Lima de Souza
// RA: 249888-1

public class MainActivity extends AppCompatActivity {

    private EditText editTextNome, editTextEmail, editTextIdade, editTextDisciplina, editTextNota1, editTextNota2;
    private TextView textViewErro, textViewResumo;
    private Button buttonEnviar, buttonLimpar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Ajustar a borda da janela
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar componentes
        editTextNome = findViewById(R.id.editTextNome);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextIdade = findViewById(R.id.editTextIdade);
        editTextDisciplina = findViewById(R.id.editTextDisciplina);
        editTextNota1 = findViewById(R.id.editTextNota1);
        editTextNota2 = findViewById(R.id.editTextNota2);
        textViewErro = findViewById(R.id.textViewErro);
        textViewResumo = findViewById(R.id.textViewResumo);
        buttonEnviar = findViewById(R.id.buttonEnviar);
        buttonLimpar = findViewById(R.id.buttonLimpar);

        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarEExibirDados();
            }
        });

        buttonLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limparFormulario();
            }
        });
    }

    private void validarEExibirDados() {
        String nome = editTextNome.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String idadeStr = editTextIdade.getText().toString().trim();
        String disciplina = editTextDisciplina.getText().toString().trim();
        String nota1Str = editTextNota1.getText().toString().trim();
        String nota2Str = editTextNota2.getText().toString().trim();

        StringBuilder erros = new StringBuilder();

        // Validações
        if (nome.isEmpty()) erros.append("O campo de nome está vazio.\n");
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            erros.append("O email é inválido.\n");
        }

        int idade = -1;
        try {
            idade = Integer.parseInt(idadeStr);
            if (idade <= 0) {
                erros.append("A idade deve ser um número positivo.\n");
            }
        } catch (NumberFormatException e) {
            erros.append("A idade deve ser um número válido.\n");
        }

        float nota1 = -1, nota2 = -1;
        try {
            nota1 = Float.parseFloat(nota1Str);
            if (nota1 < 0 || nota1 > 10) {
                erros.append("Nota 1º Bimestre deve estar entre 0 e 10.\n");
            }
        } catch (NumberFormatException e) {
            erros.append("Nota 1º Bimestre deve ser um número válido.\n");
        }

        try {
            nota2 = Float.parseFloat(nota2Str);
            if (nota2 < 0 || nota2 > 10) {
                erros.append("Nota 2º Bimestre deve estar entre 0 e 10.\n");
            }
        } catch (NumberFormatException e) {
            erros.append("Nota 2º Bimestre deve ser um número válido.\n");
        }

        if (erros.length() > 0) {
            textViewErro.setVisibility(View.VISIBLE);
            textViewErro.setText(erros.toString());
            textViewResumo.setVisibility(View.GONE);
        } else {
            textViewErro.setVisibility(View.GONE);
            float media = (nota1 + nota2) / 2;
            String status = media >= 6 ? "Aprovado" : "Reprovado";

            String resumo = "Nome: " + nome + "\n" +
                    "Email: " + email + "\n" +
                    "Idade: " + idade + "\n" +
                    "Disciplina: " + disciplina + "\n" +
                    "Notas 1º e 2º Bimestres: " + nota1 + ", " + nota2 + "\n" +
                    "Média: " + media + "\n" +
                    "Status: " + status;

            textViewResumo.setVisibility(View.VISIBLE);
            textViewResumo.setText(resumo);
        }
    }

    private void limparFormulario() {
        editTextNome.setText("");
        editTextEmail.setText("");
        editTextIdade.setText("");
        editTextDisciplina.setText("");
        editTextNota1.setText("");
        editTextNota2.setText("");
        textViewErro.setVisibility(View.GONE);
        textViewResumo.setVisibility(View.GONE);
    }
}
