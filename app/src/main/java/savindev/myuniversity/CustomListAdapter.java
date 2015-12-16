package savindev.myuniversity;

import android.app.Activity;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import android.app.Activity;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> itemname;
    private final ArrayList<String> note_date;
    private final ArrayList<String> tag;


    public CustomListAdapter(Activity context, ArrayList itemname,ArrayList eventdate,ArrayList tagslist) {
        super(context, R.layout.notelist,itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.note_date=eventdate;
        this.tag = tagslist;



    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.notelist, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        TextView dateTitle = (TextView) rowView.findViewById(R.id.dateTime);

        TextView tagsText = (TextView) rowView.findViewById(R.id.texttags);


        txtTitle.setText(itemname.get(position));

        dateTitle.setText(note_date.get(position));

        tagsText.setText(tag.get(position));

        return rowView;

    };
}