package ru.techpark.homework1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements Fragment1.ReportListener {
    Fragment1 fragment1;
    Fragment2 fragment2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment1)
                .commit();
    }

    @Override
    public void reportData(DataSource.MyData data) {
        Bundle bundle = new Bundle();
        bundle.putInt(Fragment2.NUMBER, data.mNumber);
        bundle.putInt(Fragment2.COLOR, data.mColor);

        fragment2.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment2)
                .addToBackStack(null)
                .commit();
    }
}
