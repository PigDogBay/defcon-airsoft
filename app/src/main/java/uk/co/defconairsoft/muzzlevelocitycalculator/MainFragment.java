package uk.co.defconairsoft.muzzlevelocitycalculator;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import uk.co.defconairsoft.muzzlevelocitycalculator.model.LiveAnalysis;
import uk.co.defconairsoft.muzzlevelocitycalculator.model.MainModel;

/**
 * Created by Mark on 04/07/2015.
 */
public class MainFragment extends Fragment implements LiveAnalysis.IAnalysisListener, SeekBar.OnSeekBarChangeListener {
    TextView speedText,statsText;
    SeekBar seekBar;
    MainModel mainModel;

    public MainFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_monitor, container, false);
        wireUpControls(rootView);
        return rootView;
    }

    private void wireUpControls(View view){
        speedText = view.findViewById(R.id.monitorSpeed);
        statsText = view.findViewById(R.id.monitorStats);
        seekBar = view.findViewById(R.id.monitorThreshold);
        seekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            this.mainModel = ((MainActivity)getActivity()).getMainModel();
            this.mainModel.setListener(this);
            seekBar.setProgress(mainModel.getThreshold());
            this.mainModel.start();
        }catch (Exception e){
            speedText.setText("Error");
            statsText.setText("Please restart the app");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        this.mainModel.setListener(null);
        this.mainModel.stop();
    }

    private void modelToView(){
        speedText.setText(String.format("%.1f fps", mainModel.getSpeed()));
        StringBuilder sbuff = new StringBuilder();
        sbuff.append(String.format("Count %d\n",mainModel.getCount()));
        sbuff.append(String.format("Miss %d\n",mainModel.getMiss()));
        sbuff.append(String.format("Measured Time %.3f s\n",mainModel.getTimeBetweenPeaks()));
        sbuff.append(String.format("Previous 1. %.1f fps\n",mainModel.getPrevious()[0]));
        sbuff.append(String.format("Previous 2. %.1f fps\n",mainModel.getPrevious()[1]));
        sbuff.append(String.format("Previous 3. %.1f fps\n",mainModel.getPrevious()[2]));
        sbuff.append(String.format("Average %.1f fps\n",mainModel.getAverage()));

        statsText.setText(sbuff.toString());
    }

    @Override
    public void onPelletFired(final double durationOfFlight) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    mainModel.calculateSpeed(durationOfFlight);
                    modelToView();
                }catch(Exception ignored){}
            }
        });
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser){
            try {
                mainModel.setThreshold(progress);
            }catch(Exception ignored){}
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
