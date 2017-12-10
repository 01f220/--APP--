package com.example.user.taiwaintourplay;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.lang.reflect.Field;

public class game extends AppCompatActivity {

    TextView tv_q,tv_source,tv_count;
    Button btn_A,btn_B,btn_C;
    ImageView imageView;
    RequestQueue requestQueue;
    RequestQueue mQueue=null;
    String right_ans;
    int count=0;
    String[] sQuestion;
    //String sRoot="https://xeusma.000webhostapp.com/YiJhen/";
    String sRoot="http://i-farm.acsite.org/jhen_class/";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        context=this;
        tv_q= (TextView) findViewById(R.id.tv_q);
        tv_source= (TextView) findViewById(R.id.tv_source);
        tv_count= (TextView) findViewById(R.id.tv_count);
        btn_A= (Button) findViewById(R.id.btn_A);
        btn_B= (Button) findViewById(R.id.btn_B);
        btn_C= (Button) findViewById(R.id.btn_C);
        imageView= (ImageView) findViewById(R.id.imageView2);

        btn_A.setOnClickListener(choose);
        btn_B.setOnClickListener(choose);
        btn_C.setOnClickListener(choose);

       int id= (int)(Math.random()*100)+1;
        getQuestion(String.valueOf(id));
    }

    //選擇答案
    Button.OnClickListener choose=new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_A:
                    isright(btn_A.getText().toString());
                    break;
                case R.id.btn_B:
                    isright(btn_B.getText().toString());
                    break;
                case R.id.btn_C:
                    isright(btn_C.getText().toString());
                    break;
            }

        }
    };

    //判斷答對答錯
    private void isright(String s) {
        if(right_ans.equals(s)){
            count++;
            AlertDialog.Builder dia=new AlertDialog.Builder(game.this);
            dia.setTitle("正確");
            tv_count.setText(""+count);
            dia .setMessage("目前答對題數："+count+"\n很好!繼續下一題吧 !!!");
            dia.setCancelable(false);//不可按外部區域關閉
            dia .setPositiveButton("下一題", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int id= (int)(Math.random()*100)+1;
                    getQuestion(String.valueOf(id));
                }
            });
            dia .setNegativeButton("結束遊戲", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent=new Intent();
                    Bundle b=new Bundle();
                    intent.setClass(game.this,gameover.class);
                    b.putString("sAllcount", ""+count);
                    intent.putExtras(b);
                    startActivity(intent);
                    finish();
                }
            });
            dia.show();
        }else{
            AlertDialog.Builder dia=new AlertDialog.Builder(game.this);
            dia.setTitle("錯誤");
            dia .setMessage("只好重新玩囉 888");
            dia.setCancelable(false);//不可按外部區域關閉
            dia .setNegativeButton("結束遊戲", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent=new Intent();
                    Bundle b=new Bundle();
                    intent.setClass(game.this,gameover.class);
                    b.putString("sAllcount", ""+count);
                    intent.putExtras(b);
                    startActivity(intent);
                    finish();
                }
            });
            dia.show();
        }
    }

    //取得問題及選項等資訊
    private void getQuestion(String id){
        String url=sRoot+"getQuestionByVoelly.php?id="+id;
        if (mQueue==null)
            mQueue = Volley.newRequestQueue(context);
        try{
            StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                sQuestion = response.split("－");
                tv_q.setText(sQuestion[0]); //"題目\n"+
                right_ans=sQuestion[1];
                btn_A.setText(sQuestion[2]);
                btn_B.setText(sQuestion[3]);
                btn_C.setText(sQuestion[4]);
                tv_source.setText(sQuestion[5]); //"資料來源\n"+
                Log.d("tour question response",response.toString());
                Log.d("test  question",""+sQuestion.length);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("test  err",""+error.getMessage());
            }
        });
            mQueue.add(stringRequest);
        }catch(Exception e){
            e.printStackTrace();
        }

        //動態取得id十六進位編號
        Resources res=getResources();
        Log.d("tag res",""+res);
        Log.d("tag 2",""+res.getIdentifier("a"+id,"drawable",getPackageName()));
        imageView.setBackgroundResource(res.getIdentifier("a"+id,"drawable",getPackageName()));
    }

    /* 載入線上圖片
    private void image() {
        //  Toast.makeText(MainActivity.this," pic"+id+".jpg", Toast.LENGTH_SHORT).show();
        myurl= "http://xeusma.000webhostapp.com/YiJhen/pic/a"+id+".jpg";//記得不要打127.0.0.1

        myImageRequest=new ImageRequest(myurl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                img.setImageBitmap(response);

            }
        },0,0,ImageView.ScaleType.CENTER,Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        myImageRequest.setTag("mydownload");
        requestQueue.add(myImageRequest);
    }
*/
    //線上取得題號  (不用)
    private void getID(){

        String url=sRoot+"rand_id.php";
        if (mQueue==null)
            mQueue = Volley.newRequestQueue(context);
        try{
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String id=response.toString();
                            getQuestion(id);
                            //Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
                        }//end of onResponse
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("tour", "getID err=" + error.getMessage());
                    error.printStackTrace();
                }
            });
            mQueue.add(stringRequest);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
