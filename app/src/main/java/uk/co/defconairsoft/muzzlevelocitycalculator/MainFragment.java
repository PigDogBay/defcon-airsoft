package uk.co.defconairsoft.muzzlevelocitycalculator;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import uk.co.defconairsoft.muzzlevelocitycalculator.model.AudioMonitor;
import uk.co.defconairsoft.muzzlevelocitycalculator.model.Ballistics;
import uk.co.defconairsoft.muzzlevelocitycalculator.model.LiveAnalysis;
import uk.co.defconairsoft.muzzlevelocitycalculator.model.MainModel;

/**
 * Created by Mark on 04/07/2015.
 */
public class MainFragment extends Fragment implements LiveAnalysis.IAnalysisListener {
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
        resultsText = (TextView)view.findViewById(R.id.resultsText);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mainModel = ((MainActivity)getActivity()).getMainModel();
        this.mainModel.setListener(this);
        this.mainModel.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.mainModel.setListener(null);
        this.mainModel.stop();
    }

    int count =0;
    @Override
    public void onPelletFired(final double durationOfFlight) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                count++;
                double speed = 0d;
                if (durationOfFlight>0.05D) {
                    speed = mainModel.getBallistics().calculateMuzzleVelocity(0D, durationOfFlight);
                    speed = mainModel.getBallistics().convertToFeetPerSecond(speed);
                }
                resultsText.setText(String.format("%d: %.3f s / %.1f fps",count, durationOfFlight,speed));
            }
        });
    }
}
