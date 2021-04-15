package android.example.amazingpokharatour;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
    public WordAdapter(Activity context, ArrayList<Word> words) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0,  words);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_iteam, parent, false);
        }
        Word currentWord = getItem(position);

        TextView place = (TextView)listItemView.findViewById(R.id.place);
        place.setText(currentWord.getplace());

        TextView discription = (TextView)listItemView.findViewById(R.id.description);
        discription.setText(currentWord.getdescription());

        ImageView image = (ImageView) listItemView.findViewById(R.id.image);
        image.setImageResource(currentWord.getimage());

        return listItemView;
    }

}
