package com.example.root.myviddownloader;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    String[] arrayDat = new String [1];
    String[] androArr = {"tela","tela1","tela2"};

    ArrayList<String> listItem = new ArrayList<String>();

    ListView lv;
    TextView tv;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView)findViewById(R.id.listView);
        tv = (TextView)findViewById(R.id.textView);

        new scrap().execute();

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItem);

        lv.setAdapter(adapter);

        for (int i = 0; i<androArr.length ; i++){
            listItem.add(arrayDat[i]);
        }

    }

    public class scrap extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {

            try{
                Document doc = Jsoup.connect("https://savedeo.com/download?url=https%3A%2F%2Fwww.facebook.com%2FMemeComicIndonesi%2Fvideos%2F824026997722536%2F").get();
                Elements div = doc.getElementsByAttribute("data-event");

                int i = 0;

                for (Element llink: div) {
                    String attr = llink.attr("data-event");
                    String[] arr = attr.split("\\|");
                    String kataDiambil = arr[1] + " " + arr[2];
                    arrayDat[i] = kataDiambil;
                    i++;
                }


            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tv.setText(arrayDat[0]);
        }
    }

}