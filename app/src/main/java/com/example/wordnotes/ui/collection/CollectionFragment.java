package com.example.wordnotes.ui.collection;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wordnotes.MainActivity;
import com.example.wordnotes.R;
import com.example.wordnotes.dao.Optsql;

import java.util.ArrayList;

public class CollectionFragment extends Fragment implements View.OnClickListener {

    private CollectionViewModel collectionViewModel;
    private Optsql optsql;//连接数据库

    private AlertDialog.Builder builder;
    private ProgressDialog progressDialog;

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

    public ArrayList<String[]> getFuzzy(String fuzzy) {
        return optsql.selectFuzzy(fuzzy);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.collection_button:
                System.out.println("\nokokkokokkkokokokokokokkokkokokokok\n");
                this.input_word = input_textView.getText().toString();
                System.out.println(input_word + "\n");
                wordAdapter = new WordAdapter(getFuzzy(input_word));
                recyclerView.setAdapter(wordAdapter);

                break;

        }

    }


    public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> {

        private ArrayList<String[]> mWordlist;

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView wordText;
            Button bt_delete;
            Button bt_edit;

            public ViewHolder(@NonNull View view) {
                super(view);
                wordText = view.findViewById(R.id.text_list);
                bt_delete = view.findViewById(R.id.delete_list_button);
                bt_edit = view.findViewById(R.id.edit_list_button);
            }
        }

        public WordAdapter(ArrayList<String[]> wordlist) {
            mWordlist = wordlist;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.list, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            System.out.println("position: " + position);
            final String[] word = mWordlist.get(position);
            holder.bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    optsql.deleteValue(word[0]);
                }
            });
            holder.bt_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    builder = new AlertDialog.Builder(getContext());
//                    builder.setIcon(R.mipmap.ic_launcher);
//                    builder.setTitle("Edit");
//                    builder.setMessage("请修改翻译内容");
                    final EditText editText = new EditText(getActivity());
                    builder = new AlertDialog.Builder(getActivity()).setTitle("输入框dialog").setView(editText)
                            .setPositiveButton("修改", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    optsql.updataValue(word[0],editText.getText().toString());
                                    Toast.makeText(getActivity(), "更新内容为：" + editText.getText().toString()
                                            , Toast.LENGTH_LONG).show();
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //ToDo: 你想做的事情
                                    Toast.makeText(getActivity(), "关闭", Toast.LENGTH_LONG).show();
                                    dialogInterface.dismiss();
                                }
                            });

                    builder.create().show();
                }
            });

            holder.wordText.setText(word[0]);
        }

        @Override
        public int getItemCount() {
            System.out.println(mWordlist.size());
            return mWordlist.size();
        }

        /**
         * 三个按钮的 dialog
         */
        private void showThree() {


        }


    }


}
