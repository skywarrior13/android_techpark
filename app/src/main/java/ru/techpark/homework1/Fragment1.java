package ru.techpark.homework1;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class Fragment1 extends Fragment {
    private RecyclerView recyclerView;
    private Button addElemBtn;
    private GridLayoutManager layoutManager;
    private MyDataAdapter adapter;
    private ReportListener reportListener;
    private int spanCount;

    public Fragment1() {
        super();
        adapter = new MyDataAdapter(DataSource.getInstance().getData());
    }

    Fragment1(int size) {
        this();
        DataSource.getInstance().setSize(size);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        reportListener = (ReportListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    MyDataAdapter getAdapter() {
        return adapter;
    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        if (savedInstanceState != null) {
//            int size = savedInstanceState.getInt("SIZE");
//            DataSource.getInstance().setSize(size);
//            adapter.notifyDataSetChanged();
//        }
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment1, container, false);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            spanCount = 3;
        else
            spanCount = 4;

        recyclerView = view.findViewById(R.id.my_recyclerview);
        layoutManager = new GridLayoutManager(getActivity(), spanCount);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        addElemBtn = view.findViewById(R.id.btn_add_elem);
        addElemBtn.setOnClickListener(v -> {
            DataSource.getInstance().addElem();
            Log.d("app", DataSource.getInstance().getData().toString());
            adapter.notifyItemInserted(adapter.getItemCount() - 1);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerView = null;
        addElemBtn = null;
        layoutManager = null;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
            changeSpanCount(4);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
            changeSpanCount(3);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("SIZE", adapter.mData.size());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        reportListener = null;
    }

    private void changeSpanCount(int span) {
        spanCount = span;
        layoutManager.setSpanCount(spanCount);
    }

    class MyDataAdapter extends RecyclerView.Adapter<MyViewHolder> {

        final int NUMBER_TYPE = 0;
        List<DataSource.MyData> mData;

        MyDataAdapter(List<DataSource.MyData> data) {
            mData = data;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == NUMBER_TYPE) {
                View view = LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.list_item, parent, false);
                return new MyViewHolder(view);
            }
            throw new IllegalStateException();
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            DataSource.MyData data = mData.get(position);
            holder.mNumber.setText(String.format(Locale.ENGLISH, "%d", data.mNumber));
            holder.mNumber.setTextColor(data.mColor);
            holder.mNumber.setOnClickListener(v -> reportListener.reportData(data));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        @Override
        public int getItemViewType(int position) {
            return NUMBER_TYPE;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView mNumber;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mNumber = itemView.findViewById(R.id.number);
        }
    }

    interface ReportListener {
        void reportData(DataSource.MyData data);
    }
}
