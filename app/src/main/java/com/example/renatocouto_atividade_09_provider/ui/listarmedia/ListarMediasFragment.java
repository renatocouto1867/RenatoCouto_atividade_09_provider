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

import java.util.List;

public class ListarMediasFragment extends Fragment {

    FragmentListarMediasBinding binding;
    ListarMediasViewModel listarMediasViewModel;
    ItemListarMediasAdapter itemListarMediasAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listarMediasViewModel = new ViewModelProvider(this).get(ListarMediasViewModel.class);

        binding = FragmentListarMediasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        carregarAlunos(requireActivity());


        return root;

    }

    private void carregarAlunos(Context context) {
        listarMediasViewModel.getAllAluno(context).observe(getViewLifecycleOwner(), alunos -> {
            configurarRecyclerView(alunos);
            atualizarProgresso(alunos);
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


}
