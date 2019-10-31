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

import java.util.ArrayList;

public class CollectionFragment extends Fragment implements View.OnClickListener {

    private CollectionViewModel collectionViewModel;
    private Optsql optsql;//连接数据库
    TextView input_textView;

    ArrayList<String[]> result = new ArrayList<>();
    String input_word;


    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_collection, container, false);

        optsql = new Optsql(getActivity(), "Notebook.db", null, 1);


        recyclerView = root.findViewById(R.id.recycle_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);



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

    public ArrayList<String[]> getFuzzy(String fuzzy){
        return optsql.selectFuzzy(fuzzy);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.collection_button:
                System.out.println("\nokokkokokkkokokokokokokkokkokokokok\n");
                this.input_word = input_textView.getText().toString();
                System.out.println(input_word + "\n");
                WordAdapter wordAdapter =new WordAdapter(getFuzzy(input_word));
                recyclerView.setAdapter(wordAdapter);

                break;

        }

    }


    public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> {
        private ArrayList<String[]> mWordlist;

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView wordText;

            public ViewHolder(@NonNull View view) {
                super(view);
                wordText = view.findViewById(R.id.text_list);


            }
        }

        public WordAdapter(ArrayList<String[]> wordlist) {
            mWordlist = wordlist;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.wordText.setText(mWordlist.get(position)[0]);
        }

        @Override
        public int getItemCount() {
            return mWordlist.size();
        }
    }
}
