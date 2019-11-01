package com.example.wordnotes.ui.collection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wordnotes.R;
import com.example.wordnotes.dao.Optsql;

import java.util.ArrayList;

public class CollectionHorizontal extends Fragment implements View.OnClickListener {
    
    private Optsql optsql;//连接数据库
    TextView input_textView;

    ArrayList<String[]> result = new ArrayList<>();
    String input_word;


    RecyclerView recyclerView;
    WordAdapter wordAdapter;

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
                wordAdapter =new CollectionHorizontal.WordAdapter(getFuzzy(input_word));
                recyclerView.setAdapter(wordAdapter);

                break;

        }

    }


    public class WordAdapter extends RecyclerView.Adapter<CollectionHorizontal.WordAdapter.ViewHolder> {

        private ArrayList<String[]> mWordlist;

        class ViewHolder extends RecyclerView.ViewHolder {
            Button wordButton;

            public ViewHolder(@NonNull View view) {
                super(view);
                wordButton = view.findViewById(R.id.button_list);


            }
        }

        public WordAdapter(ArrayList<String[]> wordlist) {
            mWordlist = wordlist;
        }


        @NonNull
        @Override
        public CollectionHorizontal.WordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.list, parent, false);
            CollectionHorizontal.WordAdapter.ViewHolder holder = new CollectionHorizontal.WordAdapter.ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull CollectionHorizontal.WordAdapter.ViewHolder holder, int position) {
            System.out.println("position: "+position);
            String[] word =mWordlist.get(position);
            holder.wordButton.setText(word[0]);
        }

        @Override
        public int getItemCount() {
            System.out.println(mWordlist.size());
            return mWordlist.size();
        }
    }
}
