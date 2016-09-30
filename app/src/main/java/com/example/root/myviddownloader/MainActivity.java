package com.example.root.myviddownloader;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    String[] arrayDat = new String [20];
    String[] arrayLink = new String [20];
    String[] androArr = {"tela","tela1","tela2"};

    ArrayList<String> listItem = new ArrayList<String>();

    ListView lv;
    TextView tv;
    EditText et;
    Button bt;
    ArrayAdapter<String> adapter;

    String link = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView)findViewById(R.id.listView);
        tv = (TextView)findViewById(R.id.textView);
        et = (EditText)findViewById(R.id.editText);
        bt = (Button)findViewById(R.id.button);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItem);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et.getText().equals("")){
                    Toast.makeText(MainActivity.this, "Url jangan kosong bro", Toast.LENGTH_SHORT).show();
                }else {
                    link = et.getText().toString();
                    new scrap().execute();
                }
            }
        });

        lv.setAdapter(adapter);

        final String linkedan = "https://r2---sn-qxo7sner.googlevideo.com/videoplayback?signature=BF1DDB1CD5A83CADAC4FA7158C08A68F2E5C7794.CEE3A4EB4FE127DAA302BC2DAF05578C158AB07C&id=o-AJ2w8S0E-YBanKUU7kqiiVMUkCLP539UaxksYDSfTVD-&mt=1475251842&upn=me1PyNVBND0&mv=m&ms=au&mm=31&ip=104.197.192.146&mn=sn-qxo7sner&dur=433.841&pl=20&itag=17&expire=1475274308&sparams=dur%2Cei%2Cid%2Cinitcwndbps%2Cip%2Cipbits%2Citag%2Clmt%2Cmime%2Cmm%2Cmn%2Cms%2Cmv%2Cpl%2Crequiressl%2Csource%2Cupn%2Cexpire&requiressl=yes&source=youtube&initcwndbps=9808750&ei=5JHuV6XbEoOwuALY3JmwBA&key=yt6&lmt=1472927721018193&ipbits=0&mime=video%2F3gpp&ratebypass=yes&title=GARUDA+INDONESIA+Business+Class+to+Amsterdam";

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent viewIntent = new Intent(Intent.ACTION_VIEW);
                viewIntent.setData(Uri.parse(arrayLink[position]));
                startActivity(viewIntent);
            }
        });

    }

    public class scrap extends AsyncTask<Void,Void,Void>{

        String title = "";

        @Override
        protected Void doInBackground(Void... params) {

            try{
                Document doc = Jsoup.connect("https://savedeo.com/download?url=" + link ).get();
                Elements div = doc.getElementsByAttribute("data-event");
                title = doc.title();

                int i = 0;

                for (Element llink: div) { // taking information of resolution and video type
                    String attr = llink.attr("data-event");
                    String[] arr = attr.split("\\|");
                    String kataDiambil = arr[1] + " " + arr[2];
                    arrayDat[i] = kataDiambil;
                    i++;
                }

                int j = 0;

                for (Element llink: div) { // taking the link
                    String attr = llink.attr("href");
                    arrayLink[j] = attr;
                    j++;
                }


            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tv.setText(title);
            for (int i = 0; i<arrayDat.length ; i++){
                listItem.add(arrayDat[i]);
            }
            adapter.notifyDataSetChanged();

        }
    }

}