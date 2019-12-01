package com.example.wordnotes.ui.translation;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.wordnotes.MainActivity;
import com.example.wordnotes.R;
import com.example.wordnotes.dao.MydatabaseHelper;
import com.example.wordnotes.dao.Optsql;
import com.example.wordnotes.utils.News_globaltimes;
import com.example.wordnotes.utils.Translate;

public class TranslationFragment extends Fragment implements View.OnClickListener {

    private TranslationViewModel translationViewModel;
    private String word;
    private Optsql optsql;
//    private MydatabaseHelper dbHelper;

    private AlertDialog.Builder builder;
    private ProgressDialog progressDialog;
    Button bt1;
    Button bt2;
    Button bt3;
    private String fanyi = null;
    private String phrase = null;
    private String sentence = null;

    TextView input_textview;
    TextView ouput_textview;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        translationViewModel =
//                ViewModelProviders.of(this).get(TranslationViewModel.class);
        //        translationViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//
//            }
//        });
        View root = inflater.inflate(R.layout.fragment_translation, container, false);


        input_textview = root.findViewById(R.id.text_translation);
        ouput_textview = root.findViewById(R.id.show_result);
        System.out.println("HHHHHHHHHHHHHHHHH" + input_textview.getText().toString());
        bt1 = root.findViewById(R.id.transition_button);
        bt2 = root.findViewById(R.id.button_collection);
        bt3 = root.findViewById(R.id.help);

        bt2.setOnClickListener(this);
        bt1.setOnClickListener(this);
        bt3.setOnClickListener(this);

//        dbHelper = new MydatabaseHelper(getActivity(), "Notebook.db", null, 1);
        optsql = new Optsql(getActivity(), "Notebook.db", null, 1);

        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.transition_button:
                String input = input_textview.getText().toString();
//                ouput_textview.setText(input);

                //将爬虫结果输出
                setWord(input);
                getWordShowThread();

                //如果单词存入了单词本，收藏On
                if (optsql.selectValues(input_textview.getText().toString()) != null) {
                    bt2.setBackgroundResource(R.drawable.star_on);
                    break;
                }


                if (fanyi == "该词语未查询到翻译") {
                    bt2.setBackgroundResource(R.drawable.star_off);
                    break;
                }
                bt2.setBackgroundResource(R.drawable.star_off);
                break;

            //收藏
            case R.id.button_collection:
                //启动页无查询的时候 防止收藏
                if (fanyi == "该词语未查询到翻译" || input_textview.getText().toString().equals(null)) {
                    bt2.setBackgroundResource(R.drawable.star_off);
                    break;
                }
                //数据库存在该单词时防止收藏
                if (optsql.selectValues(input_textview.getText().toString()) != null) {
                    break;
                }

                optsql.insertValues(input_textview.getText().toString(), fanyi, sentence);

                bt2.setBackgroundResource(R.drawable.star_on);
                fanyi = null;
                phrase = null;
                sentence = null;
                break;

            case R.id.help:
                showTwo();
                break;
        }

    }

    private void setWord(String word) {
        this.word = word;
    }

    private String getWord() {
        return this.word;
    }

    private void getWordShowThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Translate translate = new Translate(getWord());
                showWord(translate);
            }
        }).start();
    }

    private void showWord(final Translate translation) {
        getActivity().runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                fanyi = translation.getTranslation();
                phrase = translation.getPhrase();
                sentence = translation.getExSentence();
                if (fanyi == null) {
                    fanyi = "该词语未查询到翻译";
                    phrase = "无";
                    sentence = "无";
                }
                ouput_textview.setText(getWord() + "\n\n【翻译】\n" + fanyi + "\n【短语】\n" + phrase + "\n【例句】\n" + sentence);
            }
        });
    }

    private void showTwo() {

        builder = new AlertDialog.Builder(getContext()).setIcon(R.mipmap.ic_launcher).setTitle("帮助")
                .setMessage("Wordnote的使用\n" +
                        "支持英汉互译\n" +
                        "支持收藏单词").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ToDo: 你想做的事情
                        Toast.makeText(getContext(), "确定", Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ToDo: 你想做的事情
                        Toast.makeText(getContext(), "关闭", Toast.LENGTH_LONG).show();
                        dialogInterface.dismiss();
                    }
                });
        builder.create().show();
    }
}