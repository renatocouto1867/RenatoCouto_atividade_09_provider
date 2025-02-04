package com.example.renatocouto_atividade_09_provider.ui.listarmedia;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.renatocouto_atividade_09_provider.R;
import com.example.renatocouto_atividade_09_provider.databinding.FragmentListarMediasBinding;
import com.example.renatocouto_atividade_09_provider.model.entity.Aluno;

import java.util.ArrayList;
import java.util.List;

public class ListarMediasFragment extends Fragment {

    private FragmentListarMediasBinding binding;
    private ListarMediasViewModel listarMediasViewModel;
    private ItemListarMediasAdapter itemListarMediasAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        listarMediasViewModel = new ViewModelProvider(this).get(ListarMediasViewModel.class);

        binding = FragmentListarMediasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        carregarAlunos(requireActivity());

        return root;
    }

    private void carregarAlunos(Context context) {
        listarMediasViewModel.getAllAluno(context).observe(getViewLifecycleOwner(), alunos -> {
            if (alunos == null || alunos.isEmpty() || alunos.size() < 3) {
                completarListaDeAlunos(context, alunos);
            } else {
                configurarRecyclerView(alunos);
                atualizarProgresso(alunos);
            }
        });
    }

    private void completarListaDeAlunos(Context context, List<Aluno> alunos) {
        List<Aluno> listaAtual = alunos != null ? new ArrayList<>(alunos) : new ArrayList<>();

        List<Aluno> alunosGerados = gerarAluno();

        for (Aluno alunoGerado : alunosGerados) {
            if (listaAtual.size() < 3) {
                boolean alunoJaExiste = listaAtual.stream()
                        .anyMatch(a -> a.getNome().equals(alunoGerado.getNome()));
                if (!alunoJaExiste) {
                    listarMediasViewModel.salvarAluno(context, alunoGerado);
                    listaAtual.add(alunoGerado);
                }
            } else {
                // quando tiver 3 eu interropo o loop
                break;
            }
        }

        listarMediasViewModel.getAllAluno(context).observe(getViewLifecycleOwner(), alunos2 -> {
            configurarRecyclerView(alunos2);
            atualizarProgresso(alunos2);
        });
    }

    private void atualizarProgresso(List<Aluno> alunoList) {
        if (alunoList != null && !alunoList.isEmpty()) {
            exibirProgresso(false);
        } else {
            binding.mediaProgressCircular.setVisibility(View.GONE);
            binding.tvMediaCarregando.setVisibility(View.VISIBLE);
            binding.tvMediaCarregando.setText(getString(R.string.erro_ao_excluir));
        }
    }

    private void exibirProgresso(boolean exibir) {
        binding.mediaProgressCircular.setVisibility(exibir ? View.VISIBLE : View.GONE);
        binding.tvMediaCarregando.setVisibility(exibir ? View.VISIBLE : View.GONE);
    }

    private void configurarRecyclerView(List<Aluno> alunoList) {
        itemListarMediasAdapter = new ItemListarMediasAdapter(alunoList);
        binding.recyclerViewMedia.setAdapter(itemListarMediasAdapter);
        binding.recyclerViewMedia.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    private List<Aluno> gerarAluno() {
        List<Aluno> alunoList = new ArrayList<>();
        alunoList.add(new Aluno("José", 6.3, 7));
        alunoList.add(new Aluno("Maria José", 2.3, 7));
        alunoList.add(new Aluno("Paulo", 6.3, 1.0));
        return alunoList;
    }
}
