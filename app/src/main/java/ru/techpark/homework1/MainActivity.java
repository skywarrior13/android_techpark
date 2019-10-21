package ru.techpark.homework1;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements Fragment1.ReportListener {
    Fragment1 fragment1;
    Fragment2 fragment2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            fragment1 = new Fragment1(savedInstanceState.getInt("SIZE"));
            Log.d("my", "it works");
        } else
            fragment1 = new Fragment1();
        fragment2 = new Fragment2();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment1)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("SIZE", fragment1.getAdapter().mData.size());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void reportData(DataSource.MyData data) {
        Bundle bundle = new Bundle();
        bundle.putInt("NUMBER", data.mNumber);
        bundle.putInt("COLOR", data.mColor);

        fragment2.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment2, "saved_fragment1")
                .addToBackStack(null)
                .commit();
    }
}
