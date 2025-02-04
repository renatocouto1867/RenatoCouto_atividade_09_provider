package com.example.renatocouto_atividade_09_provider.ui.cadastrar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.renatocouto_atividade_09_provider.R;
import com.example.renatocouto_atividade_09_provider.auxiliar.Mensagens;
import com.example.renatocouto_atividade_09_provider.databinding.FragmentCadastrarBinding;
import com.example.renatocouto_atividade_09_provider.model.entity.Aluno;


public class CadastrarFragment extends Fragment {

    private FragmentCadastrarBinding binding;
    private Aluno aluno;
    private boolean isEdicao = false;
    private CadastrarViewModel cadastrarViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cadastrarViewModel = new ViewModelProvider(this).get(CadastrarViewModel.class);

        binding = FragmentCadastrarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        configurarBotoes();

        inicializaArguments();


        return root;
    }

    private void monitoraMensagem() {
        cadastrarViewModel.getMensagem().observe(getViewLifecycleOwner(), msResult -> {
            if (msResult.equals("sucesso")) {
                Mensagens.showSucesso(requireView(), getString(R.string.aluno_salvo_com_sucesso));
            }
            if (msResult.equals("erro")) {

                Mensagens.showErro(requireView(), getString(R.string.houve_um_erro_ao_salvar));
            }
        });
    }

    private void configurarBotoes() {
        binding.buttonSalvar.setOnClickListener(view -> salvarAluno());
        binding.buttonLimpar.setOnClickListener(view -> limparCampos());
    }


    private void inicializaArguments() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            aluno = (Aluno) bundle.getSerializable("aluno");
            binding.editNome.setText(aluno.getNome());
            binding.editNota1.setText(String.valueOf(aluno.getNota1()));
            binding.editNota2.setText(String.valueOf(aluno.getNota2()));
            isEdicao = true;
        } else {
            isEdicao = false;
        }
    }

    private void limparCampos() {
        binding.editNome.setText("");
        binding.editNota1.setText("");
        binding.editNota2.setText("");
        binding.editNome.requestFocus();
    }

    private void salvarAluno() {
        String nome = binding.editNome.getText().toString().trim();
        String nota1Str = binding.editNota1.getText().toString().trim();
        String nota2Str = binding.editNota2.getText().toString().trim();

        if (nome.isEmpty() || nota1Str.isEmpty() || nota2Str.isEmpty()) {
            Mensagens.showErro(requireView(), getString(R.string.por_favor_os_campos));
            return;
        }

        double nota1, nota2;

        try {
            nota1 = Double.parseDouble(nota1Str);
            nota2 = Double.parseDouble(nota2Str);
            boolean isNotaValida = validaNota(nota1) && validaNota(nota2);
            if (!isNotaValida) {
                Mensagens.showErro(requireView(), getString(R.string.insira_uma_nota_valida));
                return;
            }


            if (isEdicao) {
                aluno.atualizarNomeNota(nome, nota1, nota2);
                cadastrarViewModel.atualizarAluno(aluno, requireActivity());
            } else cadastrarViewModel.salvarAluno(new Aluno(nome, nota1, nota2), requireActivity());

            limparCampos();
            monitoraMensagem();


        } catch (NumberFormatException e) {
            Mensagens.showErro(requireView(), getString(R.string.insira_uma_nota_valida));
        }

    }

    private boolean validaNota(double nota) {
        return nota >= 0.0 && nota <= 10.0;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}