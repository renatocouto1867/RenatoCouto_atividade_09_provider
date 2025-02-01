package com.example.renatocouto_atividade_09_provider.ui.cadastrar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.lifecycle.ViewModelProvider;

import com.example.renatocouto_atividade_09_provider.databinding.FragmentCadastrarBinding;


public class CadastrarFragment extends Fragment {

    private FragmentCadastrarBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CadastrarViewModel cadastrarViewModel =
                new ViewModelProvider(this).get(CadastrarViewModel.class);

        binding = FragmentCadastrarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}