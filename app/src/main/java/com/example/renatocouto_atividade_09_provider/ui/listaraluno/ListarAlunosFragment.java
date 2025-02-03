package com.example.renatocouto_atividade_09_provider.ui.listaraluno;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.renatocouto_atividade_09_provider.R;
import com.example.renatocouto_atividade_09_provider.auxiliar.Mensagens;
import com.example.renatocouto_atividade_09_provider.databinding.FragmentListarBinding;
import com.example.renatocouto_atividade_09_provider.model.entity.Aluno;

import java.util.List;


public class ListarAlunosFragment extends Fragment {

    ListarAlunoViewModel listarViewModel;
    NavController navController;
    private FragmentListarBinding binding;
    private ItemListarAlunoAdapter itemListarAlunoAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listarViewModel = new ViewModelProvider(this).get(ListarAlunoViewModel.class);

        binding = FragmentListarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        navController = NavHostFragment.findNavController(this);

        carregarAlunos(requireActivity());

        return root;
    }


    private void carregarAlunos(Context context) {
        listarViewModel.getAllAluno(context).observe(getViewLifecycleOwner(), alunos -> {
            configurarRecyclerView(alunos);
            binding.progressCircular.setVisibility(View.GONE);
            binding.tvCarregando.setText("");
        });

    }

    private void configurarRecyclerView(List<Aluno> alunoList) {
        binding.recyclerViewAluno.setLayoutManager(new LinearLayoutManager(requireContext()));

        itemListarAlunoAdapter = new ItemListarAlunoAdapter(alunoList, new ItemListarAlunoAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(Aluno aluno) {
                editaAluno(aluno);
            }

            @Override
            public void onDeleteClick(Aluno aluno) {
                deleteAluno(aluno);
            }

        });

        binding.recyclerViewAluno.setAdapter(itemListarAlunoAdapter);
    }

    private void editaAluno(Aluno aluno) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("aluno", aluno);
        navController.navigate(R.id.navigation_cadastrar, bundle);
    }


    private void deleteAluno(Aluno aluno) {
        new AlertDialog.Builder(getContext()).setTitle(R.string.confirmar_exclusao)
                .setMessage(getString(R.string.realmente_deseja_deletar) + aluno.getNome() + "?")
                .setPositiveButton(getString(R.string.deletar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listarViewModel.deletarAluno(requireActivity(), aluno);
                        carregarAlunos(requireContext());
                        listarViewModel.getMensagem().observe(getViewLifecycleOwner(), msg -> {
                            if (msg.equals("sucesso")) {
                                Mensagens.showSucesso(requireView(), getString(R.string.aluno_removido));
                            }
                            if (msg.equals("erro")) {
                                Mensagens.showErro(requireView(), getString(R.string.erro_ao_excluir));
                            }
                        });
                    }
                }).setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //não
                        dialog.dismiss();
                    }
                }).create().show();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}