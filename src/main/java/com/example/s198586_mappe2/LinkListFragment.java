/**
 * Gretar Ævarsson
 * gretar80@gmail.com
 * © 2016
 */

package com.example.s198586_mappe2;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class LinkListFragment extends ListFragment {
    // liste
    private List<Kontakt> listKontakter;

    // fragment adapter
    LinkListAdapter adapter;

    // Database handler
    DatabaseHandler db;


    // interface som aktiviteten må kalle
    private KontaktListListener listener;

    public interface KontaktListListener{
        void endreKontakt(int kontaktID);
    }


    public LinkListFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            listener = (KontaktListListener) getActivity();
        }
        catch (ClassCastException e){
            throw new ClassCastException("Feil " + e.getMessage());
        }

    }

    @Override

    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        final ListView listView = (ListView)v.findViewById(android.R.id.list);

        db = new DatabaseHandler(getActivity());
        listKontakter = db.finnAlleKontakter();

        adapter = new LinkListAdapter(getActivity(), db.finnAlleKontakter());

        setListAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // hente ListView elementet
                int kontaktID =  listKontakter.get(position).get_ID();

                // sende kontaktID til main aktiviteten
                listener.endreKontakt(kontaktID);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        listKontakter = db.finnAlleKontakter();
        adapter.oppdaterListe(db.finnAlleKontakter());
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // hente ListView elementet
        Kontakt kontakt = listKontakter.get(position);

        // vis posisjonen
        Toast.makeText(getActivity(),kontakt.getNavn(), Toast.LENGTH_SHORT).show();
    }
}
