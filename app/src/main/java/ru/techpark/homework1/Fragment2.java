package ru.techpark.homework1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Locale;

public class Fragment2 extends Fragment {
    private TextView mNumber;

    final static String NUMBER = "NUMBER";
    final static String COLOR = "COLOR";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mNumber = view.findViewById(R.id.fragment2_number);

        if (getArguments() != null) {
            mNumber.setText(String.format(Locale.ENGLISH, "%d", getArguments().getInt(NUMBER)));
            mNumber.setTextColor(getArguments().getInt(COLOR));
        }
    }
}
