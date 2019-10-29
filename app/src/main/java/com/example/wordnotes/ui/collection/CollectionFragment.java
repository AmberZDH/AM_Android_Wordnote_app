package com.example.wordnotes.ui.collection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wordnotes.R;
import com.example.wordnotes.dao.Optsql;

public class CollectionFragment extends Fragment implements View.OnClickListener {

    private CollectionViewModel collectionViewModel;
    private Optsql optsql;
    TextView input_textView;
    TextView output_textView;

    //    声明一个RecycleView变量
    RecyclerView recyclerView;
    String[][] result;
    String input_word;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_collection, container, false);

        //注册RecycleView
        recyclerView = root.findViewById(R.id.recycle_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MyAdapter myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);

        //注册sql
        optsql = new Optsql(getActivity(), "Notebook.db", null, 1);

        //注册 button 控件
        input_textView = root.findViewById(R.id.collection_input);
        Button bt1 = root.findViewById(R.id.collection_button);
        bt1.setOnClickListener(this);

//        collectionViewModel =
//                ViewModelProviders.of(this).get(CollectionViewModel.class);
//        collectionViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_collection:
                this.input_word = input_textView.getText().toString();
                this.result = getFuzzy(this.input_word);

        }
    }

    //    public setFuzzy(String fuzzy){
//
//    }
    public String[][] getFuzzy(String fuzzy) {
        return optsql.selectFuzzy(fuzzy);
    }


    //    自定义类继承RecycleView.Adapter类作为数据适配器
    class MyAdapter extends RecyclerView.Adapter {


        class Myholder extends RecyclerView.ViewHolder {

            TextView text_list;

            public Myholder(@NonNull View itemView) {
                super(itemView);
                this.text_list = (TextView) itemView.findViewById(R.id.text_list);
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Myholder myholder = new Myholder(new TextView(parent.getContext()));

            return myholder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Myholder mm = (Myholder) holder;
            mm.text_list.setText(result[position][0]);
        }

        @Override
        public int getItemCount() {
            return getFuzzy(input_word).length;
        }
    }
}