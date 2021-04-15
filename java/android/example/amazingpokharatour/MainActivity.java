package android.example.amazingpokharatour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView mountain=(TextView)findViewById(R.id.mountain);
        mountain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mountainIntent = new Intent (MainActivity.this, MountainActivity.class);
                startActivity(mountainIntent);
            }
        });
        TextView temple = (TextView)findViewById(R.id.temple);
        temple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent templeIntent= new Intent (MainActivity.this, TempleActivity.class);
                startActivity(templeIntent);
            }
        });
        TextView lake=(TextView)findViewById(R.id.lake);
        lake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lakeIntent = new Intent (MainActivity.this,LakeActivity.class);
                startActivity(lakeIntent);
            }
        });
        TextView cave=(TextView)findViewById(R.id.cave);
        cave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent caveIntent = new Intent (MainActivity.this, CaveActivity.class);
                startActivity(caveIntent);
            }
        });
        TextView musium=(TextView)findViewById(R.id.musium);
        musium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent musiumIntent = new Intent (MainActivity.this, MusiumActivity.class);
                startActivity(musiumIntent);
            }
        });
        TextView sports=(TextView)findViewById(R.id.sports);
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sportsIntent = new Intent (MainActivity.this, SportsActivity.class);
                startActivity(sportsIntent);
            }
        });
    }
}