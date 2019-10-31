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
    private Optsql optsql;
    TextView input_textView;
    TextView output_textView;

    //    声明一个RecycleView变量
//    RecyclerView recyclerView;
    ArrayList<String[]> result=new ArrayList<>();
    String input_word;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_collection, container, false);

        optsql=new Optsql(getActivity(), "Notebook.db", null, 1);

//        recyclerView = (RecyclerView) root.findViewById(R.id.recycle_list);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        MyAdapter myAdapter = new MyAdapter();
//
//        recyclerView.setAdapter(myAdapter);


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
            case R.id.collection_button:
                System.out.println("\nokokkokokkkokokokokokokkokkokokookok\n");
                this.input_word = input_textView.getText().toString();
                System.out.println(input_word+"\n");
                result=getFuzzy(this.input_word);
                while(result.isEmpty()){
                    String[] arr=result.get(0);
                    System.out.println(arr[0]+"\n"+arr[1]+"\n"+arr[2]+"\n");
                    result.remove(0);
            }

        }
    }

    //    public setFuzzy(String fuzzy){
//
//    }
    public ArrayList<String[]> getFuzzy(String fuzzy) {
        try{
        return optsql.selectFuzzy(fuzzy);}
        catch (Exception e){
            return null;
        }
    }


//    //    自定义类继承RecycleView.Adapter类作为数据适配器
//    class MyAdapter extends RecyclerView.Adapter {
//
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//
//            MyHolder myHolder = new MyHolder(LayoutInflater.from(getContext()).inflate(R.layout.list, null));//引入自定义列表项的资源文件
//
//
//            return myHolder;
//        }
//
//
//        @Override
//        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//            MyHolder mm = (MyHolder) holder;
//
////            将数据映射到控件中
////            if (getFuzzy(input_word)[position][0] != null) {
////                mm.te1.setText(getFuzzy(input_word)[position][0]);
////            } else {
//                mm.te1.setText("Hhhhhh Bugs");
////            }
//
//
//        }
//
//        @Override
//        public int getItemCount() {
////            if ((getFuzzy(input_word)!=null)) {
////                return getFuzzy(input_word).length;
////            } else
//                return 2;
//        }
//
//        class MyHolder extends RecyclerView.ViewHolder {
//
//            TextView te1;
//
//            public MyHolder(View itemView) {
//                super(itemView);
//
////                实例化子对象，把对象和列表项布局文件中的id绑定
//                te1 = itemView.findViewById(R.id.text_list);
//
//            }
//
//        }
//
//    }
}