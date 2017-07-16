/**
 * Gretar Ævarsson
 * gretar80@gmail.com
 * © 2016
 */

package com.example.s198586_mappe2;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class LinkListAdapter extends BaseAdapter {

    List<Kontakt> kontakter;
    Context context;
    LayoutInflater layoutInflater;

    public LinkListAdapter(Context context, List<Kontakt> kontakter){
        super();
        layoutInflater = LayoutInflater.from(context);
        this.kontakter = kontakter;
        this.context = context;
    }

    @Override
    public int getCount() {
        return kontakter.size();
    }

    @Override
    public Object getItem(int position) {
        return kontakter.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.linklist_kontakt,parent,false);

            // initialisere
            viewHolder = new ViewHolder();
            viewHolder.kontakt_navn = (TextView) convertView.findViewById(R.id.kontakt_navn);
            viewHolder.kontakt_alder = (TextView) convertView.findViewById(R.id.kontakt_alder);
            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder)convertView.getTag();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        Kontakt kontakt = (Kontakt)getItem(position);
        Calendar iDag = GregorianCalendar.getInstance();
        Calendar bursdag = GregorianCalendar.getInstance();
        Calendar fodselsdag = GregorianCalendar.getInstance();
        bursdag.set(Calendar.YEAR,iDag.get(Calendar.YEAR));
        bursdag.set(Calendar.MONTH,kontakt.getManed());
        bursdag.set(Calendar.DAY_OF_MONTH, kontakt.getDag());
        fodselsdag.set(Calendar.YEAR, kontakt.getAr());
        fodselsdag.set(Calendar.MONTH, kontakt.getManed());
        fodselsdag.set(Calendar.DAY_OF_MONTH, kontakt.getDag());

        int alder;

        if(iDag.after(bursdag)) {
            alder = iDag.get(Calendar.YEAR) - fodselsdag.get(Calendar.YEAR);
            bursdag.add(Calendar.YEAR,1);
        }
        else {
            alder = iDag.get(Calendar.YEAR) - fodselsdag.get(Calendar.YEAR)-1;
        }

        viewHolder.kontakt_navn.setText(kontakt.getNavn());

        viewHolder.kontakt_alder.setText("Blir" + " " +
                (alder+1)+ " " + "den" + " " +
                bursdag.get(Calendar.DAY_OF_MONTH) + "." +
                (bursdag.get(Calendar.MONTH)+1) + "." +
                bursdag.get(Calendar.YEAR));

        if(position % 2 == 0){
            convertView.setBackgroundColor(Color.parseColor("#E6E6E6"));
        }


        return convertView;
    }

    private static class ViewHolder{
        TextView kontakt_navn;
        TextView kontakt_alder;
    }

    public void oppdaterListe(List<Kontakt> kontakter){
        Log.d("ADAPTER", "er i oppdaterListe");
        this.kontakter = kontakter;
        notifyDataSetChanged();
    }
}
