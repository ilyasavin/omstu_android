package savindev.myuniversity.notes;

import android.app.Activity;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import savindev.myuniversity.R;

public class NoteListAdapter extends ArrayAdapter<NoteModel> {

    private final Activity context;
    private final ArrayList<NoteModel> noteModelArrayList;

    public NoteListAdapter(Activity context, ArrayList<NoteModel> noteModelArrayList) {
        super(context, R.layout.notelist,noteModelArrayList);
        this.context=context;
        this.noteModelArrayList=noteModelArrayList;




    }

    public View getView(int position,View view,ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();

        View rowView=inflater.inflate(R.layout.notelist, null, true);

        TextView nameTitle = (TextView) rowView.findViewById(R.id.item);
        TextView textTitle = (TextView) rowView.findViewById(R.id.textitem);
        TextView dateNote = (TextView)rowView.findViewById(R.id.dateTime);
        ImageView priorityLine = (ImageView)rowView.findViewById(R.id.priorityLine);

        switch (noteModelArrayList.get(position).getPriority()){

            case HIGH:
                priorityLine.setImageResource(R.drawable.line_red);
                break;
            case MEDIUM:
                priorityLine.setImageResource(R.drawable.line_orange);
                break;
            case LOW:
                priorityLine.setImageResource(R.drawable.line_yellow);
                break;
        }

        if (noteModelArrayList.get(position).getIsDone() == 1){
            priorityLine.setImageResource(R.drawable.line_green);
            nameTitle.setPaintFlags(nameTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }

        nameTitle.setText(noteModelArrayList.get(position).getName());
        dateNote.setText(noteModelArrayList.get(position).getDate());
        textTitle.setText(noteModelArrayList.get(position).getText());

        return rowView;

    }
}
