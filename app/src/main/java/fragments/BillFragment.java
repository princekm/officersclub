        package fragments;

        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteException;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.design.widget.Snackbar;
        import android.support.v4.app.Fragment;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ListView;
        import android.widget.Spinner;
        import android.widget.TextView;

        import com.example.officersclub.BillListAdapter;
        import com.example.officersclub.MainActivity;
        import com.example.officersclub.R;
        import com.example.officersclub.db.Bill;
        import com.example.officersclub.db.DBHelper;

        import java.util.ArrayList;
        import java.util.regex.Matcher;
        import java.util.regex.Pattern;


        /**
         * A simple {@link Fragment} subclass.
         * Activities that contain this fragment must implement the
         * {@link BillFragment.OnFragmentInteractionListener} interface
         * to handle interaction events.
         * Use the {@link BillFragment#newInstance} factory method to
         * create an instance of this fragment.
         */

        public class BillFragment extends Fragment {
            // TODO: Rename parameter arguments, choose names that match
            // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
            private static final String ARG_PARAM1 = "param1";
            private static final String ARG_PARAM2 = "param2";

            // TODO: Rename and change types of parameters
            private String mParam1;
            private String mParam2;
            private  BillListAdapter adapter;

            private OnFragmentInteractionListener mListener;


            public BillFragment() {
                // Required empty public constructor
            }

            /**
             * Use this factory method to create a new instance of
             * this fragment using the provided parameters.
             *
             * @param param1 Parameter 1.
             * @param param2 Parameter 2.
             * @return A new instance of fragment BillFragment.
             */
            // TODO: Rename and change types and number of parameters
            public static BillFragment newInstance(String param1, String param2) {
                BillFragment fragment = new BillFragment();
                Bundle args = new Bundle();
                args.putString(ARG_PARAM1, param1);
                args.putString(ARG_PARAM2, param2);
                fragment.setArguments(args);

                return fragment;
            }

            private View view;
            private Spinner months;
            private ListView listView;
            private TextView textView;
            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                if (getArguments() != null) {
                    mParam1 = getArguments().getString(ARG_PARAM1);
                    mParam2 = getArguments().getString(ARG_PARAM2);
                }
            }

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                // Inflate the layout for this fragment
                view= inflater.inflate(R.layout.fragment_bill, container, false);
                listView=(ListView)view.findViewById(R.id.billlist);
                textView=(TextView) view.findViewById(R.id.bill_total);
             adapter= new BillListAdapter(MainActivity.bills,getActivity());

                months=view.findViewById(R.id.months);
                months.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String monthName  = parent.getItemAtPosition(position).toString();


                        if(position!=0)
                        {       adapter.setDataSet(MainActivity.dbHelper.getBillsByDate(monthName));

                        }else
                            adapter.setDataSet(MainActivity.dbHelper.getAllBills());


                        Double total=0.0;
                        for(Bill bill:adapter.getDataSet())
                        {
                            total+=new Double(bill.getAmount());
                        }
                        textView.setText("Total="+new Double(total).toString());
                        listView.setAdapter(adapter);

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });


                return  view;
            }

            // TODO: Rename method, update argument and hook method into UI event
            public void onButtonPressed(Uri uri) {
                if (mListener != null) {
                    mListener.onFragmentInteraction(uri);
                }
            }

            @Override
            public void onAttach(Context context) {
                super.onAttach(context);
                if (context instanceof OnFragmentInteractionListener) {
                    mListener = (OnFragmentInteractionListener) context;
                } else {
                  //  throw new RuntimeException(context.toString()
                    //        + " must implement OnFragmentInteractionListener");
                }
            }

            @Override
            public void onDetach() {
                super.onDetach();
                mListener = null;
            }

            /**
             * This interface must be implemented by activities that contain this
             * fragment to allow an interaction in this fragment to be communicated
             * to the activity and potentially other fragments contained in that
             * activity.
             * <p>
             * See the Android Training lesson <a href=
             * "http://developer.android.com/training/basics/fragments/communicating.html"
             * >Communicating with Other Fragments</a> for more information.
             */
            public interface OnFragmentInteractionListener {
                // TODO: Update argument type and name
                void onFragmentInteraction(Uri uri);
            }


            @Override
            public void onStart() {
                super.onStart();







            }
        }
