package uk.co.defconairsoft.muzzlevelocitycalculator;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import uk.co.defconairsoft.muzzlevelocitycalculator.model.MainModel;

/**
 * Created by Mark on 04/07/2015.
 */
public class MainFragment extends Fragment {

    Button recordToggleBtn;
    TextView resultsText;

    MainModel mainModel;

    public MainFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        wireUpControls(rootView);
        return rootView;
    }

    private void wireUpControls(View view){
        recordToggleBtn = (Button) view.findViewById(R.id.recordBtn);
        recordToggleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecord();
            }
        });
        ((Button)view.findViewById(R.id.playBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playBack();
            }
        });
        resultsText = (TextView)view.findViewById(R.id.resultsText);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mainModel = ((MainActivity)getActivity()).getMainModel();
        modelToView();
    }

    private void playBack() {
        mainModel.playBack();
    }

    private void onRecord() {
        mainModel.toggleRecord();
        modelToView();
    }

    private void modelToView(){
        resultsText.setText(mainModel.getResults());
        String recordText = mainModel.isRecording() ? "STOP":"RECORD";
        recordToggleBtn.setText(recordText);
    }

}
